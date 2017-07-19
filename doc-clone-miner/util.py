#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import re
import shutil
import sys

import Levenshtein

import asyncio
import PyQt5.Qt
import os

try:
    import interval as itvl # https://pypi.python.org/pypi/pyinterval
except Exception as e:
    print("pyinterval not installed. No coverage reports will be available", file=sys.stderr)


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


_wre = re.compile(r"\w+", re.UNICODE)
def tokens(text):
    r = []
    for m in _wre.finditer(text):
        s = m.start()
        e = m.end()
        r.append((s, e, text[s:e]))
    return r

def tokenst(text):
    return [s for b, e, s in tokens(text)]

def ctokens(text):
    return ' '.join(tokenst(text))

def save_reformatted_file(fileName):
    lines = []
    with open(fileName, encoding='utf-8') as ifs:
        for line in ifs:
            # lst = line.rstrip() + '\n' # leave leading blanks + add separator (we do not need \r, so no os.linesep)
            lst = line.replace('\r', '').replace('\n',
                                                 '') + '\n'  # leave leading blanks + add separator (we do not need \r, so no os.linesep)
            lst = lst.replace('\t', '    ')  # Clone Miner is very brave to consider TAB equal to 4 spaces
            lines.append(lst)
    text = "".join(lines)
    with open(fileName + ".reformatted", 'w+', encoding='utf-8', newline='\n') as ofs:
       ofs.write(text)


def qwcjs(plus = None):
    with open(os.path.join(os.path.dirname(os.path.realpath(__file__)), "js", "qwebchannel.js"), encoding='utf-8') as qwcjsf:
        return qwcjsf.read() + ('\n' + plus if plus else "")

asio_el: asyncio.AbstractEventLoop = None

async def eval_p_js_co(page: PyQt5.QtWebEngineWidgets.QWebEnginePage, js: str):
    fut = asio_el.create_future()
    def ready(r):
        asio_el.call_soon_threadsafe(fut.set_result, r)

    page.runJavaScript(js, ready)
    return await fut


def eval_p_js_sync(page: PyQt5.QtWebEngineWidgets.QWebEnginePage, js: str):
    r = asio_el.run_until_complete(eval_p_js_co(page, js))
    return r


async def load_p_url_co(page: PyQt5.QtWebEngineWidgets.QWebEnginePage, u: PyQt5.Qt.QUrl):
    fut = asio_el.create_future()

    def ready(ok: bool):
        page.loadFinished.disconnect()
        asio_el.call_soon_threadsafe(fut.set_result, ok)

    page.loadFinished.connect(ready)
    page.load(u)
    return await fut


def load_p_url_sync(page: PyQt5.QtWebEngineWidgets.QWebEnginePage, u: PyQt5.Qt.QUrl):
    asio_el.run_until_complete(load_p_url_co(page, u))

def set_asio_el(loop):
    global asio_el
    asio_el = loop
    asyncio.set_event_loop(loop)