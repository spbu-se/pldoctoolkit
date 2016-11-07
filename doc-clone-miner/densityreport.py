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
        for fn, s, ei in cg.instances:
            eni = ei + 1
            for ci in range(s, eni):
                clone_densities[fn][ci] += 1
                repetition_densities[fn][ci] = max(
                    repetition_densities[fn][ci],
                    len(cg.instances)
                )

    reports = []

    maxrd = max([max(ifld) for ifld in repetition_densities])
    maxcd = max([max(ifld) for ifld in clone_densities])

    def format_crd_ccd_tx(crd, ccd, tx):
        rf = 255 - int(crd / maxrd * 255)
        cf = 255 - int(ccd / maxcd * 255)
        return textwrap.dedent("""
        <tr>
        <td style="background-color: rgb(255, 255, %d);">%d</td>
        <td style="background-color: rgb(255, 255, %d);">%d</td>
        <td>%s</td>
        </tr>
        """) % (rf, crd, cf, ccd, verbhtml.escape(tx))

    for ifl, cd, rd, fileno in zip(input_files, clone_densities, repetition_densities, itertools.count(1)):
        reports.append(
            textwrap.dedent("""File # %d: %s<br/>
            <table>
            <tr><th>RepD</th><th>ClnD</th><th>Text</th></tr>
            """) % (
                fileno, ifl.fileName
        ))

        prev_offset = 0
        ccd = cd[0]
        crd = rd[0]

        def release_portion(o):
            nonlocal prev_offset, ccd, crd
            tx = ifl.text[prev_offset:o]
            prev_offset = o
            # if crd > 0 or ccd > 0:
            reports.append(format_crd_ccd_tx(crd, ccd, tx))

        for o in range(len(ifl.text)):
            if ccd != cd[o] or crd != rd[o]:
                release_portion(o)
                ccd = cd[o]
                crd = rd[o]

        if prev_offset < len(ifl.text):
            release_portion(len(ifl.text))

        reports.append("</table><br/>\n")

    return "".join(reports)
