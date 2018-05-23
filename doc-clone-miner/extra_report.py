#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import util

def perform(clones: 'module', candidates: 'list[clones.VariativeElement]', lgr: 'logging.Logger', **kwargs):
    """
    Function to construct extra reports
    :param clones: clones module VariativeElement
    :param candidates: list of clones.VariativeElement instances 
    :param lgr: logger to log =)
    :param kwargs: different parameters
    :return: nothing
    """
    lgr.info("==== Extra report ====")

    pass

def find_hottest_place(rep_dencities: 'list[int]', text: 'str'=None, lengths: 'list[int]' = [20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500], borders = None):
    pass
    import logging
    lgr = logging.getLogger("hottest_place")

    if not borders:
        borders = (0, len(rep_dencities))
    if not text:
        text = '?' * len(rep_dencities)

    lengths = sorted(lengths, reverse=True)

    offsets: 'dict[int, int]' = dict()

    b, e = borders
    for wl in lengths:
        csum = sum(rep_dencities[b:b + wl])
        offsets[wl] = 0
        for coff in range(b + 1, e - wl):
            nsum = csum - rep_dencities[coff - 1] + rep_dencities[coff - 1 + wl]
            if nsum > csum:
                offsets[wl] = coff
        # Questionable:
        b = offsets[wl]
        e = b + wl

    for l in offsets:
        o = offsets[l]
        p = text[o, o+l]
        lgr.info("%03d -> %s" % (l, p))

