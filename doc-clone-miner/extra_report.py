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

def find_hottest_places(rep_dencities: 'list[int]', text: 'str', ifn: 'str', ofn: 'str', nesting="none", lengths: 'list[int]' = [20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500]):
    return

    import logging
    import yaml
    import pattern_near_duplicate_search as pnds
    # import os
    # ifn = os.path.basename(ifn)

    lgr = logging.getLogger("hottest_place")

    b, e = 0, len(rep_dencities)
    if not text:
        text = '?' * len(rep_dencities)

    lengths = sorted(lengths, reverse=True)

    offsets: 'dict[int, int]' = dict()

    if nesting == 'spread':
        rep_dencities = list(rep_dencities)  # clone

    for wl in lengths:
        csum = sum(rep_dencities[b:b + wl])
        bsum = csum
        offsets[wl] = b
        for coff in range(b + 1, e - wl):
            csum = csum - rep_dencities[coff - 1] + rep_dencities[coff - 1 + wl]
            if csum > bsum:
                bsum = csum
                offsets[wl] = coff
        if nesting == 'nest':
            b = offsets[wl]
            e = b + wl
        elif nesting == 'spread':  # not yet ready, requires zeroing out all matching duplicates in the document...
            rep_dencities[offsets[wl]:offsets[wl] + wl] = [0] *  wl
        else:
            pass

    pnds._word_begins = pnds._word_ends = None
    wb = pnds.get_fit_word_borders(text)

    patterns = []
    wpatterns = []
    for l in sorted(offsets.keys()):
        o = offsets[l]
        pb = o
        pe = o+l

        # trim
        while(text[pb].isspace()): pb += 1
        while(text[pe-1].isspace()): pe -= 1

        patterns.append(text[pb:pe])
        pb, pe = pnds.widen_to_whole_words(text, (pb, pe))
        wpatterns.append(text[pb:pe])

    def add_pts_to_file(yfn, pts):
        data = dict()
        try:
            with open(yfn, 'r', encoding='utf-8') as yf:
                ydata = yaml.load(yf)
                if ydata and type(ydata) == dict:  # non-empty file
                    data = ydata
        except Exception as e:
            print("Probably empty/no YAML:", repr(e))

        data[ifn] = pts

        with open(yfn, 'w', encoding='utf-8') as yf:
            yaml.dump(data, yf, indent=2, allow_unicode=True, default_style='|')

    add_pts_to_file(ofn, patterns)
    add_pts_to_file("w." + ofn, wpatterns)
