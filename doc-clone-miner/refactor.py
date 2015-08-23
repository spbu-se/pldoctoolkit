#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Refactoring tool for Document Clone Miner.
"""

from contracts import contract

import argparse
import xmllexer
import xmlfixup
import os
import uuid
import logging
import itertools

def initargs():
    global args
    argpar = argparse.ArgumentParser()

    argpar.add_argument("-idf", "--input-drl-file",
                        help="Input DRL file", required=True)
    argpar.add_argument("-odf", "--output-drl-file",
                        help="Output DRL file", required=True)

    argpar.add_argument("-cie", "--create-infelement",
                        help="Create Information Element from candidates")
    argpar.add_argument("-cde", "--create-dictionary-entry",
                        help="Create Dictionary Entry from candidates")

    args = argpar.parse_args()

class CandidateDescriptor(object):
    def __init__(self, description_string):
        group_descriptors = description_string.split(';')
        self.groups = []
        for gd in group_descriptors:
            clone_descriptors = gd.split(',')
            cds = []
            for cd in clone_descriptors:
                fileno, fl = tuple(cd.split(':'))
                first, last = tuple(fl.split('-'))
                cds.append((int(fileno), int(first), int(last) - int(first) + 1)) # file#, offset, length
            self.groups.append(cds)
        self.variative = len(self.groups) > 1

    @contract
    def get_constant_parts(self, src: 'str') -> 'list(str)':
        return [src[g[0][1]: g[0][1] + g[0][2]] for g in self.groups]

    @contract
    def get_variative_parts(self, src: 'str') -> 'list(list(str))':
        gpairs = zip(self.groups[:-1], self.groups[1:])
        result = []
        for lg, rg in gpairs:
            variants = []
            assert len(lg) == len(rg)
            for lc, rc in zip(lg, rg):
                # between left clone end and right clone beginning
                variants.append(src[lc[1] + lc[2]: rc[1]])
            result.append(variants)
        return result

    @contract
    def get_whole_instance_coordinates(self, src: 'str') -> 'list(tuple(int,int))':
        # transpose
        vclones = []
        for c in self.groups[0]:
            vclones.append([])
        for g in self.groups:
            for c, i in zip(g, itertools.count()):
                vclones[i].append(c)

        intervals = []
        for c in vclones:
            gbeg = len(src)
            gend = 0
            for g in c:
                gb = g[1]
                ge = g[1] + g[2]
                if gb < gbeg:
                    gbeg = gb
                if ge > gend:
                    gend = ge
            intervals.append((gbeg, gend))
        return intervals

    @contract
    def get_whole_instances(self, src: 'str') -> 'list(str)':
        icoords = self.get_whole_instance_coordinates(src)
        return [src[i[0]:i[1]] for i in icoords]

@contract
def replace_str_intervals_with(what: 'str', with_what: 'list(str)', intervals: 'list(tuple(int, int))') -> str:
    """
    :param what: source string
    :param with_what: [destination string to insert]
    :param intervals: [(offset, non_inclusive_end)] to insert into resut
    :return: modified string
    """
    ftitls = list(zip(intervals, intervals[1:]))
    keep_coords = [(0, intervals[0][0])] + \
                  [(f[0][1], f[1][0]) for f in ftitls] + \
                  [(intervals[-1][1], len(what))]
    keep = [what[k[0]:k[1]] for k in keep_coords]

    parts = []
    if len(with_what) != len(intervals):
        with_what = itertools.cycle(with_what)

    for k, r in zip(keep, with_what):
        parts += [k, r]
    parts.pop()

    return ''.join(parts)

@contract
def create_reuse_entry(tinput: 'str', clone_desc: 'str', drl_elt_type: 'str') -> 'tuple(str, str)':
    """
    Converts clone instances to dictionary entry references
    :param tinput: input DRL source
    :param clone_desc: clone group descriptor
    :return: tuple of output text and dictionary element source
    """
    cd = CandidateDescriptor(clone_desc)

    drl_elts = {
        "dict": ("""<d:Entry id="%s">%s</d:Entry>""", """<d:DictRef entryid="%s" dictid="doc_clone_finder" />%s"""),
        "infelt": ("""<d:InfElement id="%s">%s</d:InfElement>""", """<d:InfElemRef infelemid="%s">%s</d:InfElemRef>""")
    }
    drl_defs, drl_refs = drl_elts[drl_elt_type]

    if drl_elt_type != 'infelt' and cd.variative:
        logging.fatal("Requested to refactor variative unit, but not information element")
        assert False

    def xs(xel):
        return ''.join([el.srepr for el in xel])

    eid = str(uuid.uuid4())

    whole_instances = cd.get_whole_instances(tinput)
    # outer balancing using outer instance #0
    outer_def_prepend, outer_def_append, outer_ref_prepend, outer_ref_append = \
        xmlfixup.balance_unbalanced_text(xmllexer.lex(whole_instances[0]))

    # prepend and appends for extension points
    vps_pa = []
    vps = cd.get_variative_parts(tinput)
    for vp in vps:
        # balancing using variative part #0
        vps_pa.append(xmlfixup.balance_unbalanced_text(xmllexer.lex(vp[0])))

    # definition
    vardefs = []
    cnt = 0
    for inner_def_prepend, inner_def_append, inner_ref_prepend, inner_ref_append in vps_pa:
        cnt += 1
        vardefs.append(xs(inner_ref_prepend) + ("""<d:Nest id="%s.%d"/>""" % (eid, cnt)) + xs(inner_ref_append))

    condefs = cd.get_constant_parts(tinput)

    bodydef = ''.join([cv[0] + cv[1] for cv in zip(condefs, vardefs + [''])])

    complete_def = drl_defs % (eid, xs(outer_def_prepend) + bodydef + xs(outer_def_append))

    # reference
    vrefs = []
    for vpv in vps:
        replace_nests = []
        cnt = 0
        for vp, pa in zip(vpv, vps_pa):
            cnt += 1
            inner_def_prepend, inner_def_append, inner_ref_prepend, inner_ref_append = pa
            replace_nests.append(
                ("""<d:Replace-Nest nestid="%s.%d">""" % (eid, cnt)) +
                xs(inner_ref_prepend) + vp + xs(inner_ref_append) +
                """</d:Replace-Nest>"""
            )
        vrefs.append(
            xs(outer_ref_prepend) +
            drl_refs % (eid, ''.join(replace_nests)) +
            xs(outer_ref_append)
        )

    if not len(vrefs):
        # non-variative one
        vrefs = [
            xs(outer_ref_prepend) +
            (drl_refs % (eid, '')) +
            xs(outer_ref_append)
        ]

    # instance intervals
    intervals = cd.get_whole_instance_coordinates(tinput)

    restext = replace_str_intervals_with(tinput, vrefs, intervals)

    return restext, complete_def

def insert_inf_elt(src, defn):
    elts = xmllexer.lex(src)
    li = len(elts) - 1
    while li > 0 and elts[li].int_type != xmllexer.IntervalType.closetag:
        li -= 1
    offs = elts[li].offs
    return src[:offs] + os.linesep + defn + os.linesep + src[offs:]

def insert_dict_entry(src, defn):
    elts = xmllexer.lex(src)

    dopentag = os.linesep + """<d:Dictionary id="doc_clone_finder">""" + os.linesep
    dclosetag = os.linesep + "</d:Dictionary>" + os.linesep

    offs = -1
    for e in reversed(elts):
        if e.int_type == xmllexer.IntervalType.opentag and e.srepr == dopentag:
            offs = e.end
            break

    if offs == -1:
        li = len(elts) - 1
        while li > 0 and elts[li].int_type != xmllexer.IntervalType.closetag:
            li -= 1
        offs = elts[li].offs

        return src[:offs] + dopentag + defn + dclosetag + src[offs:]
    else:
        return src[:offs] + os.linesep + defn + src[offs:]

def proceed():
    global args

    with open(args.input_drl_file, 'r', encoding='utf-8') as idf, open(args.output_drl_file, 'w+', encoding='utf-8', newline='\n') as odf:
        ids = idf.read()

        if args.create_dictionary_entry:
            dedesc = args.create_dictionary_entry
            result, entry = create_reuse_entry(ids, dedesc, 'dict')
            odf.write(insert_dict_entry(result, entry))
            # odf.write(result + "**********" + entry)
        elif args.create_infelement:
            iedesc = args.create_infelement
            result, entry = create_reuse_entry(ids, iedesc, 'infelt')
            odf.write(insert_inf_elt(result, entry))
            # odf.write(result + "**********" + entry)

if __name__ == '__main__':
    # print(replace_str_intervals_with("0123456789012345678901234567890123456789", ["<yay>"], [(1,4),(5,10),(15,15)]))
    initargs()
    proceed()
