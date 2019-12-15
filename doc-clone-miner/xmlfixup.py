#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Simple ad-hoc XML fixed
"""

import input_lexer


def shrink_broken_markup_interval(offset, length, all_intervals):
    covered, broken_1st, broken_last = input_lexer.find_covered_intervals(offset, length, all_intervals)
    end = offset + length

    if broken_1st:
        if covered[0].int_type != input_lexer.IntervalType.general:
            # general text can be broken
            if len(covered) < 2:
                return None
            else:
                offset = covered[1].offs
    if broken_last:
        if covered[-1].int_type != input_lexer.IntervalType.general:
            # general text can be broken
            if len(covered) < 2:
                return None
            else:
                end = covered[-2].end
    rlen = end - offset
    if rlen <= 0:
        return None
    return offset, rlen


def balance_unbalanced_text(covered_intervals):
    """
    :param covered_intervals: [xmllexer.XmlInterval] -- correct unbalanced markup
    :return: ([intervals to prepend to body], [intervals to append to body], [intervals to prepend ref ref], [intervals to append to ref])
    """

    elem_append = []
    elem_prepend = []

    ref_append = []
    ref_prepend = []

    # going right
    stack = []
    for i in covered_intervals:
        if i.int_type == input_lexer.IntervalType.opentag:
            stack.append(i)
        elif i.int_type == input_lexer.IntervalType.closetag and len(stack):
            stack.pop()

    for i in stack:
        elem_append.insert(0, i.create_opposite_tag())
        ref_append.append(i)

    # going left
    stack = []
    back_intervals = list(covered_intervals)
    back_intervals.reverse()
    for i in back_intervals:
        if i.int_type == input_lexer.IntervalType.closetag:
            stack.append(i)
        elif i.int_type == input_lexer.IntervalType.opentag and len(stack):
            stack.pop()

    for i in stack:
        elem_prepend.append(i.create_opposite_tag())
        ref_prepend.insert(0, i)

    return elem_prepend, elem_append, ref_prepend, ref_append

# just a test
if __name__ == '__main__':
    src = """t0</a>t1</b>t2<c>t3<d>t4"""
    ints = input_lexer.lex_xml(src)
    p, a, rp, ra = balance_unbalanced_text(ints)
    print("".join([pi.srepr for pi in p]))
    print(src)
    print("".join([ai.srepr for ai in a]))
    print("============")
    print("".join([pi.srepr for pi in rp]) + "<REF/>" + "".join([pi.srepr for pi in ra]))
