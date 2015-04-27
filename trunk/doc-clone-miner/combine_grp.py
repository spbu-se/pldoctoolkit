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
                    d = clones.CloneGroup.distance(g1, g2)
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


if __name__ == '__main__':
    pass
