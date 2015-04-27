#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""Verbatim HTML module"""

import os

def escape(s):
    return s.replace('&','&amp;').replace('<', '&lt;').replace('>', '&gt;') # html.escape(s)

def escapecode(s):
    s = escape(s)
    # s = s.replace('\r', '') # should not be there already
    s = s.replace('\n', '<br/>\n') # not os.linesep to be more precise on length
    s = s.replace(' ', '&nbsp;')
    s = s.replace('\t', '&nbsp;&nbsp;&nbsp;&nbsp;')
    return s
