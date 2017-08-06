#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Heatmap browser complex
"""
import sys
import traceback

import subprocess
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
    def __init__(self, parent=None, app: pyqt_common.EMUIApp=None):
        super(HMBrowserComplex, self).__init__(parent=parent)
        self.setupUi(self)  # method of ui_class

        self.app = app
        self.hm_page: QtWebEngineWidgets.QWebEnginePage = self.wvHeatMap.page()
        self.rp_page: QtWebEngineWidgets.QWebEnginePage = self.wvFuzzyRepetitions.page()

        self.bindEvents()

    @QtCore.pyqtSlot(str)
    def clog(self, txt):
        print("JAVASCRIPT:", txt)

    @QtCore.pyqtSlot(int, int, str)
    def src_select(self, start0, finish0, candidate_idx=None):
        print("SRC selection <-", start0, finish0, candidate_idx)
        self.hm_page.runJavaScript("alert([%d, %d]);" % (start0, finish0))

    def loadRepetitions(self, url: str):
        """
        Failed to make it work with asyncio, lets try to do it by any means...
        :param url: URL
        :param app: EMUIApp
        :return: Nothing
        """
        # TODO: make use of pyqt_common.load_p_url_then_run_js_co !!!
        def loaded(ok: bool):
            try:
                if self.rp_page.url() != Qt.QUrl(url):
                    print("Blank")
                elif not ok:
                    print("Something went wrong")
                else:  # loaded url, everything ok
                    self.rp_page.loadFinished.disconnect()
                    self.hmbc.registerObject('qtab', self)
                    # right now -- fire and forget
                    self.rp_page.runJavaScript(
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
                        """)
                    )
            except Exception as e:
                print(repr(e))
                traceback.print_stack()

        self.hmbc = QWebChannel(self.rp_page)
        self.rp_page.setWebChannel(self.hmbc)

        self.rp_page.loadFinished.connect(loaded)
        self.rp_page.load(Qt.QUrl(url))

    def loadHeatMap(self, url: str):
        self.hm_url = url

        async def doit():
            return await pyqt_common.load_p_url_co(self.hm_page, Qt.QUrl(url))
        try:
            asyncio.get_event_loop().run_until_complete(doit())
        except Exception as e:
            print(repr(e))
            traceback.print_stack()

    def nd2html(self, inputfile, workfolder):
        self.app.processEvents()

        popen_args = [
                         sys.executable, '-OO', os.path.join(_scriptdir, "nearduplicates2html.py"),
                         "-sx", inputfile,
                         "-od", workfolder
                     ]
        print("Reporting with: " + ' '.join(popen_args))
        reppr = subprocess.Popen(popen_args, stdout=subprocess.PIPE)

        oe = reppr.communicate()
        ffrc = reppr.returncode

        self.app.processEvents()

    def loadND(self, inputfile, workfolder):
        self.inputfile = inputfile
        self.workfolder = workfolder

        self.nd2html(inputfile, workfolder)
        pve = os.path.join(workfolder, "pyvarelements.html")
        self.loadRepetitions(pyqt_common.path2url(pve))

    def refresh(self):
        self.loadHeatMap(self.hm_url)
        self.loadND(self.inputfile, self.workfolder)

    def bindEvents(self):
        # self.shouldLoadHeatMap.connect(self.loadHeatMap)
        # self.shouldLoadRepetitions.connect(self.loadRepetitions)
        pass
