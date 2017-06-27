#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import shutil
import os

import Levenshtein
import interval as itvl # https://pypi.python.org/pypi/pyinterval

"""Misc utilitary garbage"""


def escape(s):
    return s.replace('&', '&amp;').replace('<', '&lt;').replace('>', '&gt;')  # html.escape(s)

def escapen(s):
    # not os.linesep to be more precise on length
    return escape(s).replace('\n', '<br/>\n')

def escapecode(s, allow_space_wrap=False):
    s = escapen(s)
    # s = s.replace('\r', '') # should not be there already
    if not allow_space_wrap:
        s = s.replace(' ', '&nbsp;')
    s = s.replace('\t', '&nbsp;&nbsp;&nbsp;&nbsp;')
    return s


def write_variative_report(clones, candidates, report_file_name):
    """
    Function to save extra reports
    :param clones: clones module VariativeElement
    :param candidates: list of clones.VariativeElement instances 
    :param report_file_name: HTML file to save everything 
    :return: 
    """

    target_dir = os.path.dirname(report_file_name)
    cohtml = clones.VariativeElement.summaryhtml(candidates, clones.ReportMode.variative)
    with open(report_file_name, 'w', encoding='utf-8') as htmlfile:
        htmlfile.write(cohtml)

    shutil.copyfile(
        os.path.join(os.path.dirname(os.path.abspath(__file__)), 'js', 'interactivity.js'),
        os.path.join(target_dir, "interactivity.js")
    )
    shutil.copyfile(
        os.path.join(os.path.dirname(os.path.abspath(__file__)), 'js', 'jquery-2.0.3.min.js'),
        os.path.join(target_dir, "jquery-2.0.3.min.js")
    )


def transpose(a):
    """
    Thanks, http://stackoverflow.com/a/21444360/539470
    :param a: list of lists (say, lines of a matrix)
    :return: list of lists (say, lines of transposed matrix)
    """
    return [list(x) for x in zip(*a)]

def connected_slices(i: 'itvl.interval') -> 'list[tuple[int, int]]':
    return [(int(t[0][0]), int(t[0][1])) for t in i.components]


def lratio(s1, s2):
    return 1 - Levenshtein.distance(s1, s2) / max(len(s1), len(s2), 1)