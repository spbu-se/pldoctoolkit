#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = 'dluciv'


def combine_gruops_par_20140819(available_groups):
    # import multiprocessing
    # pool = multiprocessing.Pool()
    import clones
    import sys

    # participated_groups = set() not used
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

    # print stats
    def pstats():
        import logging
        logging.getLogger("cloneminer.combine.square").info("Source single groups: %d" %(len(available_groups),))
        logging.getLogger("cloneminer.combine.square").info("Single groups: %d" %(len(current_available_groups),))
        logging.getLogger("cloneminer.combine.square").info("Variative groups: %d" %(len(combinations),))
    pstats()

    return combinations, current_available_groups


def combine_groups_n_ext_with_int_tree(available_groups: "list[clones.CloneGroup]"):
    """
    Algorithm:
    AG = available_groups
    AVG = available_variative_groups
    0. AVG += AG, AG := []; interval tree is built
    1. for each G1 in AVG find G2 in AVG which it best combines with (closest non-intersecting)
    2. each successful G1, G2 combination is:
    2.1. ACG -= [G1, G2]
    2.2. VG1 <- [G1, G2]
    2.3. AVG += [VG1]
    2.4. interval tree is modified
    3. if there were any successful combinations in (2), continue from (1), else go ahead
    4. AVG -> AVG, AG
    5. return AVG, AG

    :param available_groups:
    :return:
    """
    import sys
    import itertools
    import logging
    import clones
    from intervaltree import IntervalTree

    ttyn = '\r' if sys.stdout.isatty() else '\n'

    print("Combining groups, %d total..." % len(available_groups))

    # (0)
    avg = set([clones.VariativeElement([cg]) for cg in available_groups])

    def build_interval_tree():
        vg_interval_list = []
        for ve in avg:
            vg_interval_list += ve.get_tree_intervals(expanded=True)
        logging.debug("(re)building interval tree...")
        itree = IntervalTree(vg_interval_list)  # to search who intersects with clone_intervals[i.begin:i.end]
        logging.debug("(re)built interval tree of %d intervals." % (len(vg_interval_list),))
        return itree

    # (1)
    maxdist = clones.infty  # 2000
    cycle = True
    iterations_passed = 0
    while cycle:
        ptotal = 100
        pready = int(100 * (1.0 - 2**(-iterations_passed)))  # very approximately
        print("~ %d / %d = %03.1f%%" % (pready, ptotal, 100.0 * pready / ptotal), end=ttyn, flush=True)

        cycle = False

        vg_intervals = build_interval_tree()  # TODO: why does it crash when used incrementally as in (2)?..

        skip = set()
        tojoin = set()
        for g1 in avg:
            if g1 in skip:
                continue
            probable_g2_intervals = [
                vg_intervals.search(interval.begin, interval.end) for interval in
                g1.get_tree_intervals(expanded=True)
            ]
            probable_g2s = [
                clones.VariativeElement.from_tree_interval(i)
                for i in itertools.chain.from_iterable(probable_g2_intervals)
            ]
            probable_g2_dists = [clones.VariativeElement.distance(g1, g2) for g2 in probable_g2s]
            g2sdists = [
                (g, d)
                for (g, d) in zip(probable_g2s, probable_g2_dists)
                if 0 < d < maxdist and g not in skip
            ]
            if len(g2sdists) > 0:
                best_g2_d = min(g2sdists, key=lambda gd: gd[1])
                tojoin.add((g1, best_g2_d[0]))
                skip.add(g1)
                skip.add(best_g2_d[0])

        # (2)
        for g1, g2 in tojoin:
            cycle = True  # check for (3)

            logging.debug("AVG %d ->" % (len(avg),))
            # (2.1)
            avg.remove(g1)
            avg.remove(g2)
            # (2.2)
            new_ve = g1 + g2
            # (2.3)
            avg.add(new_ve)
            logging.debug("AVG %d <-" % (len(avg),))

            # (2.4)
            # for itvl in g1.get_tree_intervals(expanded=True):
            #     vg_intervals.remove(itvl)
            # for itvl in g2.get_tree_intervals(expanded=True):
            #     vg_intervals.remove(itvl)
            # for itvl in new_ve.get_tree_intervals(expanded=True):
            #     vg_intervals.add(itvl)

        iterations_passed += 1

    # then split unary groups away
    var_groups = []
    uni_groups = []
    for ve in avg:
        if len(ve.clone_groups) > 1:
            var_groups.append(ve)
        else:
            uni_groups.append(ve.clone_groups[0])

    # print stats
    def pstats():
        import collections
        logging.getLogger("cloneminer.combine.n_ext_points").info("Source single groups: %d" %(len(available_groups),))
        logging.getLogger("cloneminer.combine.n_ext_points").info("Single groups: 1 -> %d" %(len(uni_groups),))
        vgs =  collections.defaultdict(lambda : 0)
        for vg in var_groups:
            vgs[len(vg.clone_groups)] += 1
        logging.getLogger("cloneminer.combine.n_ext_points").info("Variative groups:")
        for gc in vgs.keys():
            logging.getLogger("cloneminer.combine.n_ext_points").info(" - %d -> %d" % (gc, vgs[gc]))
    pstats()

    return var_groups, uni_groups
    # return combine_gruops_par_20140819(available_groups) # fallback then -- it is now fake, does noting =)

if __name__ == '__main__':
    pass
