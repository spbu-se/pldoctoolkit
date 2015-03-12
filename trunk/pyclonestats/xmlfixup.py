#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Simple ad-hoc XML fixed
"""

import xmllexer

def shrink_broken_markup_interval(offset, length, all_intervals):
    covered, broken_1st, broken_last = xmllexer.find_covered_intervals(offset, length, all_intervals)
    end = offset + length

    if broken_1st:
        if len(covered) < 2:
            return None
        # general text can be broken
        if covered[0].int_type != xmllexer.IntervalType.general:
            offset = covered[1].offs
    if broken_last:
        if len(covered) < 2:
            return None
        # general text can be broken
        if covered[-1].int_type != xmllexer.IntervalType.general:
            end = covered[-2].end
    rlen = end - offset
    if rlen <= 0:
        return None
    return offset, rlen

def balance_unbalanced_text(covered_intervals):
    append = []
    prepend = []

    # going right
    stack = []
    for i in covered_intervals:
        if i.int_type == xmllexer.IntervalType.opentag:
            stack.append(i)
        elif i.int_type == xmllexer.IntervalType.closetag and len(stack):
            stack.pop()

    while len(stack):
        i = stack.pop()
        append.append(xmllexer.XmlInterval(xmllexer.IntervalType.closetag, -1, "</" + i.name + ">", i.name))

    # going left
    stack = []
    back_intervals = list(covered_intervals)
    back_intervals.reverse()
    for i in back_intervals:
        if i.int_type == xmllexer.IntervalType.closetag:
            stack.append(i)
        elif i.int_type == xmllexer.IntervalType.opentag and len(stack):
            stack.pop()

    while len(stack):
        i = stack.pop()
        prepend.append(xmllexer.XmlInterval(xmllexer.IntervalType.opentag, -1, "<" + i.name + ">", i.name))

    prepend.reverse()

    return prepend, append

# just a test
if __name__ == '__main__':
    src = """t0</a>t1</b>t2<c>t3<d>t4"""
    ints = xmllexer.lex(src)
    p, a = balance_unbalanced_text(ints)
    print("".join([pi.srepr for pi in p]))
    print(src)
    print("".join([ai.srepr for ai in a]))
