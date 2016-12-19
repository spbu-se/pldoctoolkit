#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import clones
import verbhtml
import textwrap
import itertools
import xmllexer
import string


def report_densities(available_groups: 'list(clones.CloneGroup)', input_files: 'list(clones.InputFile)') -> 'str':
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

    reports_table = []
    reports_map = []
    reports_heat = []

    maxrd = max([max(ifld) for ifld in repetition_densities])
    maxcd = max([max(ifld) for ifld in clone_densities])

    tr_template = string.Template(textwrap.dedent("""
        <tr>
        <td style="background-color: rgb(255, 255, ${rf});">${crd}</td>
        <td style="background-color: rgb(255, 255, ${cbf});">${ccd}</td>
        <td>${tx}</td>
        </tr>
        """
    ))

    span_template = string.Template(textwrap.dedent(
        """<span id="co-${n}-${co}" style="background-color: rgb(${crf}, ${cgf}, ${cbf});" title="Clones coverage: ${rfd}; GRP Power: ${cfd}">${tx}</span>"""
    ))

    heat_template = string.Template(
        """<a href="densitymap.html#co-${n}-${co}" target="densitymap">""" +
        """<div style="position: absolute; top: ${t}px; height: ${h}px; background-color: rgb(${crf}, ${cgf}, ${cbf}); width: 100%"></div>"""
        """</a>"""
    )

    _old_div_b = 0
    def format_crd_ccd_tx(crd, ccd, tx, fn, begofs, endofs):
        nonlocal _old_div_b

        rf = 255 - int(crd / maxrd * 255)
        cf = int(ccd / maxcd * 255)
        if ccd == 0:  # white
            crf = 255
            cgf = 255
            cbf = 255
        else:  # white -> red
            crf = 255
            cgf = 255 - cf
            cbf = 255 - cf

        cfd = "%d%% = %d / %d" % (int(100*ccd/maxcd), ccd, maxcd)
        rfd = "%d%%" % (int(100*crd/maxrd),)

        tr = tr_template.substitute({
            'rf': rf, 'crd': crd, 'cbf': cbf, 'ccd': ccd, 'tx': tx
        })

        span = span_template.substitute({
            'crf': crf, 'cgf': cgf, 'cbf': cbf, 'tx': tx, 'cfd': cfd, 'rfd': rfd, 'co': begofs, 'n': str(fn)
        })

        heatscale = 100
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

        def get_without_markup(ifl, b, e):
            l = e - b  # non-inclusive above
            parts = xmllexer.get_texts_and_markups(b, l, ifl.lexintervals)

            hparts = [
                clones.ExactCloneGroup.two_or_more_spaces_re.sub(" ", clones.ExactCloneGroup.two_or_more_nlines_re.sub("\n", t))
                for t, k in parts if k == xmllexer.IntervalType.general and len(t) and not t.isspace()
                ]

            return verbhtml.escapecode(" ".join(hparts), allow_space_wrap=True)

        def release_portion(o):
            nonlocal prev_offset, ccd, crd
            tx = get_without_markup(ifl, prev_offset, o)
            section = format_crd_ccd_tx(crd, ccd, tx, fileno, prev_offset, o)
            prev_offset = o
            reports_table.append(section[0])
            reports_map.append(section[1])
            reports_heat.append(section[2])

        for o in range(len(ifl.text)):
            if ccd != cd[o] or crd != rd[o]:
                release_portion(o)
                ccd = cd[o]
                crd = rd[o]

        if prev_offset < len(ifl.text):
            release_portion(len(ifl.text))

        reports_table.append("</table><br/>\n")
        reports_map.append("<br/>\n")

    return "".join(reports_table), " ".join(reports_map), "\n".join(reports_heat)
