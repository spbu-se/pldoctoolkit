#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import clones
import verbhtml
import textwrap
import itertools


def report_densities(available_groups: 'list(clones.CloneGroup)', input_files: 'list(clones.InputFile)') -> 'str':
    repetition_densities = [[0] * len(ifl.text) for ifl in input_files]
    clone_densities = [[0] * len(ifl.text) for ifl in input_files]
    for cg in available_groups:
        for fn, s, e in cg.instances:
            for ci in range(s, e):
                clone_densities[fn][ci] += 1
                repetition_densities[fn][ci] = max(
                    repetition_densities[fn][ci],
                    len(cg.instances)
                )

    assert len(input_files) != 1  # We will probably support more in the future

    reports = []

    def format_crd_ccd_tx(crd, ccd, tx):
        reports.append(textwrap.dedent("""
        <tr>
        <td>%d</td>
        <td>%d</td>
        <td>%s</td>
        </tr>
        """) % (crd, ccd, verbhtml.escape(tx)))

    for ifl, cd, rd, fileno in zip(input_files, clone_densities, repetition_densities, itertools.count(1)):
        reports.append(
            """File # %d: %s<br/><table><tr><th>RepD</th><th>ClnD</th><th>Text</th></tr>\n""" % (
                fileno, ifl.fileName
        ))

        prev_offset = 0
        ccd = cd[0]
        crd = rd[0]

        def release_portion(o):
            nonlocal prev_offset
            tx = ifl.text[prev_offset, o]
            reports.append(format_crd_ccd_tx(crd, ccd, tx))
            prev_offset = o

        for o in range(len(ifl.text)):
            if ccd != cd[o] or crd != rd[o]:
                release_portion(o)

        if prev_offset != len(ifl.text) - 1:
            release_portion(len(ifl.text))

        reports.append("</table><br/>\n")

    return "".join(reports)
