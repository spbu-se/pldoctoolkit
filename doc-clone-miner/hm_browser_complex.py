#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Heatmap browser complex
"""
from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtCore import pyqtSignal
from PyQt5.QtWebChannel import QWebChannel
from PyQt5.QtWidgets import QAction
from quamash import QEventLoop

import util
from pyqt_common import *

_scriptdir = os.path.dirname(os.path.realpath(__file__))
_scriptname = os.path.basename(os.path.realpath(__file__))

class HMBrowserComplex(QtWidgets.QWidget, ui_class('hm_browser_window.ui')):
    def __init__(self, parent):
        super(HMBrowserComplex, self).__init__(parent=parent)

    shouldLoadRepetitions = pyqtSignal(str, name='shouldLoadRepetitions')
    shouldLoadHeatMap = pyqtSignal(str, name='shouldLoadHeatMap')

    @QtCore.pyqtSlot(str)
    def loadRepetitions(self, url):
        pass

    @QtCore.pyqtSlot(str)
    def loadHeatMap(self, url):
        pass

    def bindEvents(self):
        self.shouldAddTab.connect(self.addbrTab)
