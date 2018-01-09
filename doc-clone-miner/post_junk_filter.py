#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import logging
import time
import itertools
from intervaltree import IntervalTree

__author__ = 'dluciv'


def post_junk_filter(dgroups: 'list[clones.VariativeElement]') -> 'list[clones.VariativeElement]':
    import clones
    logger = logging.getLogger("cloneminer.combine.postjunkfilter")

    beforesize = len(dgroups)

    def build_interval_tree(dgroups) -> IntervalTree:
        ndg_interval_list = []
        for ndg in dgroups:
            ndg_interval_list += ndg.get_tree_intervals(expanded=False, archetype_consolidated=True)
        logger.debug("(re)building interval tree...")
        itree = IntervalTree(ndg_interval_list)  # to search who intersects with clone_intervals[i.begin:i.end]
        logger.debug("(re)built interval tree of %d intervals." % (len(ndg_interval_list),))
        return itree

    t1 = time.time()
    # 1. Remove small exact groups
    for g in list(dgroups):  # clone it
        if g.g_power == 1 and g.c_power <= 2 and g.archetype_length_in_human_readable_words() <= 6:
            dgroups.remove(g)

    # 2. Remove self-intersections of exact groups with all groups
    itr = build_interval_tree(dgroups)
    edgroups = [g for g in dgroups if g.g_power == 1]
    todel = set()
    for g1 in edgroups:
        if g1 in todel:
            continue

        probable_g2_intervals = [
            itr.search(interval.begin, interval.end) for interval in
            g1.get_tree_intervals(expanded=False, archetype_consolidated=True)
        ]
        probable_g2_intervals = itertools.chain.from_iterable(probable_g2_intervals)  # flatten
        probable_g2s = set([
            clones.VariativeElement.from_tree_interval(i)
            for i in probable_g2_intervals
        ])
        for g2 in probable_g2s:
            if g2 == g1 or g2.g_power > 1 or g2 in todel:
                continue

            if g1.archetype_length_in_symbols() <= g2.archetype_length_in_symbols():
                todel.add(g1)

    for dg in todel:
        dgroups.remove(dg)

    t2 = time.time()
    aftersize = len(dgroups)
    logger.info("Post-Junk-Filtering removed %d of %d groups in %0.2f sec." % (beforesize-aftersize, beforesize, t2-t1))

    return list(dgroups)