#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# banyan http://pythonhosted.org/Banyan/ fails to build at least on Win 7 64 with Python 3
# let's do ourselves, it is pleasant
# requires https://pypi.python.org/pypi/PyContracts

from contracts import contract

import contracts

contracts.disable_all()


class IntervalTree(object):
    def center(intervals):
        if len(intervals):
            bs = 0.0
            for s, e in intervals:
                bs += s + e
            return bs / 2.0 / len(intervals)
        else:
            return 0.0

    # seems reasonable
    # http://stackoverflow.com/questions/3269434/whats-the-most-efficient-way-to-test-two-integer-ranges-for-overlap
    @contract
    def are_overlapping(i1: 'tuple(Number, Number)', i2: 'tuple(Number, Number)') -> 'bool':
        s1, e1 = i1;
        s2, e2 = i2
        return max(s1, s2) <= min(e1, e2)

    @contract
    def __init__(self, intervals: 'seq(tuple(Number, Number))'):
        self.center = set()

        ctr = IntervalTree.center(intervals)

        li = []
        ri = []
        for s, e in intervals:
            if s > ctr:  # add to right
                ri.append((s, e))
            elif e < ctr:  # add to left
                li.append((s, e))
            else:
                self.center.add((s, e))

        def istart(itv):
            s, e = itv
            return s

        def iend(itv):
            s, e = itv
            return e

        if len(li):
            self.leftleft = istart(min(li, key=istart))
            self.leftright = iend(max(li, key=iend))
            self.left = IntervalTree(li)
        else:
            self.leftleft = None
            self.leftright = None
            self.left = None

        if len(ri):
            self.rightleft = istart(min(ri, key=istart))
            self.rightright = iend(max(ri, key=iend))
            self.right = IntervalTree(ri)
        else:
            self.rightleft = None
            self.rightright = None
            self.right = None

        if len(self.center):
            self.centerleft = istart(min(self.center, key=istart))
            self.centerright = iend(max(self.center, key=iend))
        else:
            self.centerleft = None
            self.centerright = None

    def _findintersectingintervals(self, interval):
        s, e = interval

        result = []

        if self.left:
            if s <= self.leftright and e >= self.leftleft:
                result += self.left._findintersectingintervals(interval)

        if len(self.center):
            if s <= self.centerright and e >= self.centerleft:
                for i in self.center:
                    if IntervalTree.are_overlapping(interval, i):
                        result.append(i)

        if self.right:
            if s <= self.rightright and e >= self.rightleft:
                result += self.right._findintersectingintervals(interval)

        return result

    @contract
    def findintersecting(self, interval: 'tuple(Number, Number)') -> 'seq(tuple(Number, Number))':
        result = self._findintersectingintervals(interval)

        # sort by centers
        # items are tuples (s, e), so center is sum(s, e)/2
        # but we can bypass /2 for all
        result.sort(key=sum)
        return result


if __name__ == '__main__':
    # some tests
    it = IntervalTree([(1, 2), (2, 5), (0, 6), (10, 20), (8, 9), (22, 23), (28, 28), (29, 30)])

    print(it.findintersecting((2, 4)))
    print(it.findintersecting((7, 15)))
    print(it.findintersecting((15, 20)))
    print(it.findintersecting((21, 30)))
