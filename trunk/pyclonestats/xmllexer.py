#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Simple ad-hoc XML lexer based on pygments lexer.
"""

import pygments.token as ptok
from pygments.lexers import XmlLexer
import enum
import bisect

@enum.unique
class IntervalType(enum.IntEnum):
    general = 0
    opentag = 1
    closetag = 2
    emptytag = 3

class XmlInterval(object):
    def __init__(self, int_type, offs, srepr, name = None):
        self.int_type = int_type
        self.offs = offs
        self.srepr = srepr
        self.name = name
        self.end = offs + len(srepr) # non iclusive, e.g. interval = characters [begin, end)
    
    def __len__(self):
        return self.end - self.offs

    def __repr__(self):
        return "[%d-%d): %s `%s' [%s]" % (self.offs, self.end, str(self.int_type), self.name if self.name else '', repr(self.srepr))

class LexException(Exception):
    pass

def _rejoin(lexemes):
    intervals = []
    inopentag = False
    tagname = ""
    intoffs = 0
    intrepr = ""
    for offset, kind, srepr in lexemes:
        # begin of open tag or empty tag
        if not inopentag and kind == ptok.Token.Name.Tag and srepr.startswith("<") and not srepr.startswith("</") and not srepr.startswith("<!"):
            inopentag = True
            tagname = srepr[1:]
            intoffs = offset
            intrepr = srepr

        # end of open tag or empty tag
        elif inopentag:
            intrepr += srepr
            if kind == ptok.Token.Name.Tag:
                inopentag = False
                if srepr == ">":
                    yield XmlInterval(IntervalType.opentag, intoffs, intrepr, tagname)
                elif srepr == "/>":
                    yield XmlInterval(IntervalType.emptytag, intoffs, intrepr, tagname)
                else:
                    raise LexException("Opened tag ends with: " + srepr)

        # closing tag
        elif not inopentag and kind == ptok.Token.Name.Tag and srepr.startswith("</"):
            yield XmlInterval(IntervalType.closetag, offset, srepr, srepr[2:-1])

        # anything
        else:
            yield XmlInterval(IntervalType.general, offset, srepr)

def lex(xmlstring):
    lexer = XmlLexer()
    return list(_rejoin(lexer.get_tokens_unprocessed(xmlstring)))

offs_cache = dict()

def find_covered_intervals(offset, length, all_intervals):
    """returns: [intervals], breaks_first, breaks_last"""

    if not offs_cache.get(id(all_intervals)):
        # This comprehension takes really long => caching. Thanks to stupid bisect which all it is for.
        offs_cache[id(all_intervals)] = [i.offs for i in all_intervals]

    all_offs = offs_cache[id(all_intervals)]
    i1i = bisect.bisect(all_offs, offset) - 1
    i2i = bisect.bisect(all_offs, offset + length - 1) - 1

    i1 = all_intervals[i1i]
    i2 = all_intervals[i2i]

    return all_intervals[i1i:i2i + 1], i1.offs < offset, offset + length < i2.end
    
def get_plain_texts(offset, length, all_intervals):
    covered, broken_1st, broken_last = find_covered_intervals(offset, length, all_intervals)
    # broken 1st and last intervals are ok because we filter them as tags before
    end = offset + length
    text_intervals = [i for i in covered if i.int_type == IntervalType.general]
    text = []
    for ti in covered:
        if ti.int_type == IntervalType.general:
            sr = ti.srepr
            if ti.end > end:
                sr = sr[:end - ti.end]
            if ti.offs < offset:
                sr = sr[offset - ti.offs:]
            text.append(sr)
    return text


# just for testing purposes, TODO: remove it completely
if __name__ == '__main__':
    lexer = XmlLexer()
    s = ''

    with open("tmpFile4CloneFinding.txt", encoding='utf-8') as f: s = f.read()


    ints = list(_rejoin(lexer.get_tokens_unprocessed(s)))

    with open("out.txt", 'w+', encoding='utf-8') as outf:
        for t in ints:
            print(repr(t), file=outf)

    cints, bf, bl = find_covered_intervals(490, 48, ints)
    print(bf, bl)
    for i in cints:
        print(repr(i))
