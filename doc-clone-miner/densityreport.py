#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import clones
import util
import textwrap
import itertools
import xmllexer
import string
import sourcemarkers
import re

def report_densities(available_groups: 'list(clones.CloneGroup)', input_files: 'list(clones.InputFile)') -> 'str':
    heatscale = 100

    repetition_densities = [[0] * len(ifl.text) for ifl in input_files]
    clone_densities = [[0] * len(ifl.text) for ifl in input_files]
    for cg in available_groups:
        for fn, s, ei in cg.instances:
            eni = ei + 1
            for ci in range(s, eni):
                clone_densities[fn][ci] += 1
                repetition_densities[fn][ci] = max(
                    repetition_densities[fn][ci],
                    len(cg.instances)
                )

    # blind markers
    marker_ss =\
        "("+\
        r"""(<!-- [\da-f]{8}-[\da-f]{4}-[\da-f]{4}-[\da-f]{4}-[\da-f]{12} <=< (ACCEPT|IGNORE) -->)""" +\
        "|" +\
        r"""(<!-- (ACCEPT|IGNORE) >=> [\da-f]{8}-[\da-f]{4}-[\da-f]{4}-[\da-f]{4}-[\da-f]{12} -->)""" +\
        ")"

    marker_sr = re.compile(marker_ss)
    marker_se = re.compile(util.escape(marker_ss))

    # cleanup repetitions on markers
    for ifl, ifn in zip(input_files, itertools.count()):
        for m in marker_sr.finditer(ifl.text):
            l = len(m.groups()[0])
            o = m.start()
            e = o + l
            z = [0] * l
            clone_densities[ifn][o:e] = z
            repetition_densities[ifn][o:e] = z


    reports_table = []
    reports_map = []
    reports_heat = []

    maxrd = max([max(ifld) for ifld in repetition_densities])
    maxcd = max([max(ifld) for ifld in clone_densities])

    maxrd = max(maxrd, 1)
    maxcd = max(maxcd, 1)

    tr_template = string.Template(textwrap.dedent("""
        <tr>
        <td style="background-color: rgb(255, 255, ${rf});">${crd}</td>
        <td style="background-color: rgb(255, 255, ${cbf});">${ccd}</td>
        <td>${tx}</td>
        </tr>
        """
    ))

    span_template = string.Template(textwrap.dedent(
        """<span id="co-${n}-${co}" style="background-color: rgb(${crf}, ${cgf}, ${cbf});" data-ignore-title="Clones coverage: ${rfd}; GRP Power: ${cfd}">${tx}</span>"""
    ))

    heat_template = string.Template(
        """<a href="densitymap.html#co-${n}-${co}" target="densitymap">""" +
        """<div style="position: absolute; top: ${t}px; height: ${h}px; background-color: rgb(${crf}, ${cgf}, ${cbf}); width: 100%"></div>"""
        """</a>"""
    )

    _old_div_b = 0
    currently_in_selectable = False
    def format_crd_ccd_tx(crd, ccd, tx, fn, begofs, endofs):
        nonlocal _old_div_b, currently_in_selectable

        rf = 255 - int(crd / maxrd * 255)
        cf = int(ccd / maxcd * 255)
        if ccd == -2:  # IGNORE, yellow
            crf = 255
            cgf = 255
            cbf = 0
        elif ccd == -1:  # ACCEPT, cyan
            crf = 0
            cgf = 255
            cbf = 255
        elif ccd == 0:  # density 0, white
            crf = 255
            cgf = 255
            cbf = 255
        else:  # density > 0, white -> red
            crf = 255
            cgf = 255 - cf
            cbf = 255 - cf

        cfd = "%d%% = %d / %d" % (int(100*ccd/maxcd), ccd, maxcd)
        rfd = "%d%%" % (int(100*crd/maxrd),)

        tr = tr_template.substitute({
            'rf': rf, 'crd': crd, 'cbf': cbf, 'ccd': ccd, 'tx': tx
        })

        span = span_template.substitute({
            'crf': crf, 'cgf': cgf, 'cbf': cbf, 'tx': tx, 'cfd': cfd, 'rfd': rfd, 'co': begofs, 'n': str(fn),
        })

        # In case when it is one of known near-duplicates, make it
        # easily distinguishable and selactable to highlight it.
        if ccd == -1 and not currently_in_selectable:
            # entering selectable
            currently_in_selectable = True
            span = ('<!-- <<S --><span data-source-offs="%d">' % begofs) + span
        if ccd != -1 and currently_in_selectable:
            # leaving selectable
            currently_in_selectable = False
            span = '</span><!-- S>> -->' + span

        divh = (endofs - begofs) // heatscale
        divt = begofs // heatscale
        divb = endofs // heatscale

        if divh > 0 or divb > _old_div_b:
            heat = heat_template.substitute({
                'crf': crf, 'cgf': cgf, 'cbf': cbf, 'h': str(max(1, divh)),
                'co': str(begofs), 'n': str(fn), 't': str(begofs // heatscale)
            })
        else:
            heat = ""  # save space in heatmap file

        _old_div_b = divb

        return tr, span, heat

    for ifl, cd, rd, fileno in zip(input_files, clone_densities, repetition_densities, itertools.count(1)):
        reports_table.append(
            textwrap.dedent("""File # %d: %s<br/>
            <table>
            <tr><th>RepD</th><th>ClnD</th><th>Text</th></tr>
            """) % (
                fileno, ifl.fileName
        ))

        reports_map.append(
            textwrap.dedent("""File # %d: %s<br/>
            """) % (
                fileno, ifl.fileName
        ))

        prev_offset = 0
        ccd = cd[0]
        crd = rd[0]

        separated_intervals = list(xmllexer.separate_comments(ifl.lexintervals))

        def set_clone_density_to(xmlinterval, value):
            be = xmlinterval.offs
            en = xmlinterval.end
            cd[be:en] = [value] * (en-be)

        current_ignore_mode = None
        for si in separated_intervals:
            if si.int_type == xmllexer.IntervalType.comment:
                omt = sourcemarkers.open_marker_type(si)
                cmt = sourcemarkers.close_marker_type(si)
                if omt or cmt:
                    set_clone_density_to(si, 0)
                if omt:
                    current_ignore_mode = omt
                elif cmt:
                    current_ignore_mode = None
            else:  # between or outside comments
                if current_ignore_mode == 'ACCEPT':
                    set_clone_density_to(si, -1)
                elif current_ignore_mode == 'IGNORE':
                    set_clone_density_to(si, -2)

        def get_without_markup(b, e):
            l = e - b  # non-inclusive above

            parts = xmllexer.get_texts_and_markups(b, l, separated_intervals)

            hparts = [
                clones.ExactCloneGroup.two_or_more_spaces_re.sub(" ", clones.ExactCloneGroup.two_or_more_nlines_re.sub("\n", t))
                for t, k in parts
                if k in {xmllexer.IntervalType.general, xmllexer.IntervalType.comment} and len(t) and not t.isspace()
                ]

            return util.escapecode(" ".join(hparts), allow_space_wrap=True)

        def release_portion(o):
            nonlocal prev_offset, ccd, crd
            tx = get_without_markup(prev_offset, o)
            section = format_crd_ccd_tx(crd, ccd, tx, fileno, prev_offset, o)
            prev_offset = o
            reports_table.append(section[0])
            reports_map.append(section[1])
            reports_heat.append(section[2])

        # Heatmap >= 500 px =>
        heatscale = min(100, max(len(ifl.text) // 500, 2))

        for o in range(len(ifl.text)):
            if ccd != cd[o] or crd != rd[o]:
                release_portion(o)
                ccd = cd[o]
                crd = rd[o]

        if prev_offset < len(ifl.text):
            release_portion(len(ifl.text))

        reports_table.append("</table><br/>\n")
        reports_map.append("<br/>\n")

    jreports_map = marker_se.sub(r"""<span style="color: lightgray; font-size:x-small;">\1</span>""", " ".join(reports_map))

    return "".join(reports_table), jreports_map, "\n".join(reports_heat)
