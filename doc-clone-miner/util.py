#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import asyncio
import os
import re
import shutil
import sys
import functools
import contextlib
import psutil
import string
import yaml

# import Levenshtein  # not used any more
import traceback

import PyQt5.QtCore
from PyQt5.QtWidgets import QApplication

_scriptdir = os.path.dirname(os.path.realpath(__file__))
_scriptname = os.path.basename(os.path.realpath(__file__))


try:
    import interval as itvl  # https://pypi.python.org/pypi/pyinterval
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
    """Deprecated"""
    # import Levenshtein
    raise DeprecationWarning("This function should not be used anymore")
    # return 1 - Levenshtein.distance(s1, s2) / max(len(s1), len(s2), 1)


def diratio(s1, s2):
    try:
        import faster_pattern_near_duplicate_search
        return faster_pattern_near_duplicate_search.di_similarity(s1, s2)
    except (ImportError, ModuleNotFoundError):
        import pattern_near_duplicate_search
        return pattern_near_duplicate_search.di_similarity(s1, s2)


# _wre = re.compile(r"\w+", re.UNICODE)
_wre = re.compile(r"\S+", re.UNICODE)

_s_punctuation = string.punctuation.replace('@', '')
_pre = "".join("\\" + c for c in _s_punctuation)
_r_pre = re.compile(r"[" + _pre + r"]+")
_npre = "".join("^\\" + c for c in _s_punctuation)
_wpre = re.compile(r"([" + _pre + r"])|([" + _npre + r"^\s]+)", re.UNICODE)

def tokens(text, punctsep=False):
    r = []
    for m in (_wpre if punctsep else _wre).finditer(text):
        s = m.start()
        e = m.end()
        r.append((s, e, text[s:e]))
    return r


def text_to_tokens_offsets(src: str) -> 'tuple[list[str], list[tuple[int, int]]]':
    str_offs = [(src[fi.start():fi.end()], (fi.start(), fi.end())) for fi in _wre.finditer(src)]
    return tuple([list(t) for t in zip(*str_offs)])


def tokenst(text, punctsep=False):
    return [s for b, e, s in tokens(text, punctsep)]


def ctokens(text, punctsep=False):
    return ' '.join(tokenst(text, punctsep))


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

def save_standalone_html(source_html, target_html):
    with open(source_html, encoding='utf-8') as htmlsrc:
        htmlcontent = htmlsrc.read()

        def replace_ref_with_script(match):  # want normal lambdas here...
            jsfilename = os.path.join(
                os.path.dirname(source_html),  # html directory
                match.group(1) + ".js"
            )
            with open(jsfilename) as jsfile:
                js = jsfile.read()
                return """<script>%s</script>""" % js

        htmlcontent = re.sub("""<script src="(.*)\\.js"></script>""", replace_ref_with_script, htmlcontent)
        with open(target_html, "w", encoding='utf-8') as ofl:
            ofl.write(htmlcontent)


# asyncio stuff

def ready_future(result=None):
    fut = asyncio.get_event_loop().create_future()
    fut.set_result(result)
    return fut


def set_asio_el(loop):
    asyncio.set_event_loop(loop)


def fire_and_forget(task, *args, **kwargs):
    """Start and forget async task as https://stackoverflow.com/a/37344956/539470 shows"""
    loop = asyncio.get_event_loop()
    if callable(task):
        return loop.run_in_executor(None, task, *args, **kwargs)
    else:
        raise TypeError('Task must be a callable')


def excprint(function):
    """
    A decorator that wraps the passed in function and logs
    exceptions should one occur
    """

    @functools.wraps(function)
    def wrapper(*args, **kwargs):
        try:
            return function(*args, **kwargs)
        except Exception as e:
            # log the exception
            exc_info = sys.exc_info()
            fe = traceback.format_exception(*exc_info)
            print(
                ("!!EXCEPTION in function <<%s>>, args: (%s), kwargs: (%s)" + os.linesep + "%s") % (
                    function.__name__, str(args), str(kwargs), os.linesep.join(fe)
                ),
                file=sys.stderr
            )
            # re-raise the exception
            raise e

    return wrapper

class QHourGlass(contextlib.AbstractContextManager, contextlib.AbstractAsyncContextManager):
    def __init__(self, lower_priority = True):
        super(contextlib.AbstractContextManager, self).__init__()
        super(contextlib.AbstractAsyncContextManager, self).__init__()
        self._p = psutil.Process()

    def _lower_p(self):
        n = self._p.nice()
        self._n = n if type(n) == int else n.value  # Very unclear behaviour on Unix vs Windows
        self._p.nice(psutil.BELOW_NORMAL_PRIORITY_CLASS if psutil.WINDOWS else 19)

    def _normal_p(self):
        self._p.nice(self._n)

    def __enter__(self):
        self._lower_p()
        QApplication.setOverrideCursor(PyQt5.QtCore.Qt.WaitCursor)

    def __exit__(self, exc_type, exc_val, exc_tb):
        if exc_type:
            print("Exception while houglass:", exc_type, exc_val)
        QApplication.restoreOverrideCursor()
        self._normal_p()

    def __aenter__(self):
        self._lower_p()
        QApplication.setOverrideCursor(PyQt5.QtCore.Qt.WaitCursor)

    def __aexit__(self, exc_type, exc_val, exc_tb):
        if exc_type:
            print("Exception while houglass:", exc_type, exc_val)
        QApplication.restoreOverrideCursor()
        self._normal_p()


@functools.lru_cache()
def cfg()-> 'Union[Dict[Hashable, Any], List, None]':
    """Readonly config"""
    with open(os.path.join(_scriptdir, "settings.yml"), 'r', encoding='utf-8') as ys:
        return yaml.load(ys)
