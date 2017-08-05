#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Misc pyqt functions
"""
import os
from os import getcwd, chdir
from os.path import realpath
import asyncio

from PyQt5 import QtCore, uic, QtWebEngineWidgets, Qt

def ui_class(name):
    return uic.loadUiType(os.path.join(os.path.dirname(os.path.realpath(__file__)), 'qtui', name))[0]


_scriptdir = os.path.dirname(os.path.realpath(__file__))
_scriptname = os.path.basename(os.path.realpath(__file__))

def path2url(path):
    return QtCore.QUrl.fromLocalFile(path).toString()

def url2path(url):
    return QtCore.QUrl(url).toLocalFile()

class pushd_c:
    """
    Inspired by https://gist.github.com/Tatsh/7131812
    """
    cwd = None
    original_dir = None

    def __init__(self, dirname):
        self.cwd = realpath(dirname)

    def __enter__(self):
        self.original_dir = getcwd()
        chdir(self.cwd)
        return self

    def __exit__(self, type, value, tb):
        chdir(self.original_dir)


def adapt_path_2_win(path):
    path = os.path.realpath(path)
    if os.name == 'posix':
        # running clone miner using wine
        # assuming root is mounted to z:\
        return "z:" + path.replace('/', '\\')
    else:
        return path


def adapt_filename_enc_2_win():
    import locale
    loc2wincp = {
        'en': 'ibm437',
        'ru': 'windows-1251'
    }
    loc = locale.getlocale()
    lng = loc[0][:2]
    wincp = loc2wincp[lng]
    return wincp


async def eval_p_js_co(page: QtWebEngineWidgets.QWebEnginePage, js: str):
    fut = asyncio.get_event_loop().create_future()
    def ready(r):
        asyncio.get_event_loop().call_soon_threadsafe(fut.set_result, r)

    page.runJavaScript(js, ready)
    return await fut


def eval_p_js_sync(page: QtWebEngineWidgets.QWebEnginePage, js: str):
    r = asyncio.get_event_loop().run_until_complete(eval_p_js_co(page, js))
    return r


def eval_p_js_faf(page: QtWebEngineWidgets.QWebEnginePage, js: str):
    page.runJavaScript(js)


async def load_p_url_co(page: QtWebEngineWidgets.QWebEnginePage, u: Qt.QUrl):
    fut = asyncio.get_event_loop().create_future()

    def ready(ok: bool):
        page.loadFinished.disconnect()
        asyncio.get_event_loop().call_soon_threadsafe(fut.set_result, ok)

    page.loadFinished.connect(ready)
    page.load(u)
    return await fut


def load_p_url_sync(page: QtWebEngineWidgets.QWebEnginePage, u: Qt.QUrl):
    asyncio.get_event_loop().run_until_complete(load_p_url_co(page, u))