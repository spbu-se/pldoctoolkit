#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Heatmap browser complex
"""
import sys
import traceback

from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtCore import pyqtSignal, pyqtSlot
from PyQt5.QtWebChannel import QWebChannel
from PyQt5.QtWidgets import QAction
import pyqt_common

from pyqt_common import *

_scriptdir = os.path.dirname(os.path.realpath(__file__))
_scriptname = os.path.basename(os.path.realpath(__file__))

class HMBrowserComplex(QtWidgets.QMainWindow, ui_class('hm_browser_window.ui')):
    """
    Control objects:
      - wvHeatMap --- heat map webview
      - wvFuzzyRepetitions --- repetitions webview
    """
    def __init__(self, parent=None):
        super(HMBrowserComplex, self).__init__(parent=parent)
        self.setupUi(self)  # method of ui_class

        self.hm_page: QtWebEngineWidgets.QWebEnginePage = self.wvHeatMap.page()
        self.rp_page: QtWebEngineWidgets.QWebEnginePage = self.wvFuzzyRepetitions.page()

        self.bindEvents()

    shouldLoadRepetitions: pyqtSignal  = pyqtSignal(str, name='shouldLoadRepetitions')
    shouldLoadHeatMap: pyqtSignal = pyqtSignal(str, name='shouldLoadHeatMap')

    @pyqtSlot(str)
    def loadRepetitions(self, url: str):
        async def doit():
            return await pyqt_common.load_p_url_then_run_js_co(
                self.rp_page, Qt.QUrl(url),
                pyqt_common.qwcjs("""
                try {
                    window.qwc = new QWebChannel(qt.webChannelTransport, function (channel) {
                        try {
                            window.qtab = channel.objects.qtab;
                            window.adaptToQWebView();
                        } catch (ie) {
                            alert(window.qtab);
                            alert(ie);
                        }
                    });
                } catch (e) {
                    alert(e);
                }
                """),
                {'qtab': self}
            )
        try:
            asyncio.get_event_loop().run_until_complete(doit())
        except Exception as e:
            print(repr(e))
            traceback.print_stack()

    @pyqtSlot(str)
    def loadHeatMap(self, url: str):
        async def doit():
            return await pyqt_common.load_p_url_co(self.hm_page, Qt.QUrl(url))
        try:
            asyncio.get_event_loop().run_until_complete(doit())
        except Exception as e:
            print(repr(e))
            traceback.print_stack()

    def setup_autorefresh(self, check_path: str):
        print("Autorefresh not implemented yet", file=sys.stderr)
        pass

    def bindEvents(self):
        self.shouldLoadHeatMap.connect(self.loadHeatMap)
        self.shouldLoadRepetitions.connect(self.loadRepetitions)
