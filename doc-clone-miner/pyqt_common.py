#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Misc pyqt functions
"""
import os
from os import getcwd, chdir
from os.path import realpath

from PyQt5 import QtCore, uic


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

