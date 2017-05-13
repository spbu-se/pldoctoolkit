#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import shutil
import os

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


def write_variative_report(clones, candidates, lgr, report_file_name):
    """
    Function to save extra reports
    :param clones: clones module VariativeElement
    :param candidates: list of clones.VariativeElement instances 
    :param lgr: logger to log =)
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

