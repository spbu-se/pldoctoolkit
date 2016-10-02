#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = 'dluciv'

def combine_gruops_par_20140819(available_groups):
    # import multiprocessing
    # pool = multiprocessing.Pool()
    import clones
    import sys

    participated_groups = set()
    combinations = []

    print("Combining groups, %d total..." % len(available_groups))
    pcounter = 0

    available_groups = set(available_groups)
    ptotal = len(available_groups)
    current_available_groups = set(available_groups)

    ttyn = '\r' if sys.stdout.isatty() else '\n'

    for g1 in available_groups:
        if g1 in current_available_groups:
            best_g2 = None
            best_dist = clones.infty
            for g2 in current_available_groups:
                if g2 != g1:
                    d = clones.ExactCloneGroup.distance(g1, g2)
                    if d < best_dist:
                        best_dist = d
                        best_g2 = g2
            if best_dist != clones.infty:
                combinations.append(clones.VariativeElement([g1, best_g2]))
                current_available_groups.discard(g1)
                current_available_groups.discard(g2)

        pcounter += 1
        print("~ %d / %d = %03.1f%%" % (pcounter, ptotal, 100.0 * pcounter / ptotal), end=ttyn, flush=True)

    return combinations, current_available_groups

def combine_groups_n_ext_with_int_tree(available_groups: "list[clones.CloneGroup]"):
    import sys
    import clones
    import itertools
    from intervaltree import Interval, IntervalTree
    ttyn = '\r' if sys.stdout.isatty() else '\n'

    group_intervals = []
    for g in available_groups:
        for (fileno, start, end), instno in zip(g.instances, itertools.count(0)):
            # data is file no, group reference and index in the group
            # extend them twice
            l = end - start
            start -= l // 2
            end += l // 2
            group_intervals.append(Interval(start, end, (fileno, g, instno)))

    clone_intervals = IntervalTree(group_intervals)
    # then to test, search who intersects with clone_intervals[i.begin:i.end]

    return combine_gruops_par_20140819(available_groups) # fallback then -- it is now fake, does noting =)

if __name__ == '__main__':
    pass
