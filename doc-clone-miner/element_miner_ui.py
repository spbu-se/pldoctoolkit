#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Element miner UI. Licensed under GPL v3 after PyQt5 which is used here.
"""
import argparse
import asyncio
import locale
import os
import ndgmgr
import shutil
import subprocess
import sys
import threading
import traceback

import bottle
from PyQt5 import QtWidgets, QtCore, QtGui
from PyQt5.QtCore import pyqtSignal, QThread, QTimer
from PyQt5.QtGui import QCursor
from PyQt5.QtWebChannel import QWebChannel
from PyQt5.QtWidgets import QAction
from quamash import QEventLoop

import hm_browser_complex
import pyqt_common
import sourcemarkers
import util
from pyqt_common import ui_class, _scriptdir, EMUIApp

hm_bc_i: hm_browser_complex.HMBrowserComplex = None

_scriptdir = os.path.dirname(os.path.realpath(__file__))
_scriptname = os.path.basename(os.path.realpath(__file__))

# optverb = "-v"  # uncomment for debugging
optverb = "-OO"   # uncomment for production


def initargs():
    argpar = argparse.ArgumentParser()
    # Windows one...
    # TODO: run Clone Miner with Wine if needed =)
    # And it is hardcoded not only here (and here it is hardcoded optionally =)).
    argpar.add_argument("-ct", "--clone-tool", help="Full path to clones.exe", default= os.path.join(_scriptdir, "clone_miner", "clones.exe"))
    argpar.add_argument("-fft", "--fuzzy-finder-tool", help="Full path to CloneFinder.exe", default= os.path.join(_scriptdir, "fuzzy_finder", "CloneFinder.exe"))
    argpar.add_argument("-if", "--input-file", help="Input file to analyze")
    argpar.add_argument("-gca", "--group-combining-algorithm", help="Group combining algorithm for Clone Miner",
                        choices=["interval-n-ext", "full-square"], type=str, default="interval-n-ext")
    argpar.add_argument("-faais", "--force-allow-acccept-ignore-save",
                        help="Allow Accept/Ignore/Save in all modes",
                        action='store_true'
                        )
    global clargs
    clargs = argpar.parse_args()

_reheat_timer = QTimer()

@util.excprint
def rebuildAndReloadHM():
    global _reheat_timer
    wt: QThread = app.launch_fh_builder()
    _reheat_timer = QTimer()
    _reheat_timer.setInterval(500)

    def iswtready():
        global _reheat_timer
        try:
            if not wt.isFinished():
                return
            _reheat_timer.stop()
            _reheat_timer = QTimer()
            app.enqueue(app.hm_bc_i.refreshHM)
        except Exception as we:
            print(repr(we), file=sys.stderr)
            traceback.print_stack()

    _reheat_timer.timeout.connect(iswtready)
    _reheat_timer.start()


class ElemBrowserTab(QtWidgets.QWidget, ui_class('element_browser_tab.ui')):
    def __init__(self, parent, uri, stats, src="", fn="", save_fn="", fuzzypattern_matches_shown=False, extra: object=None):
        QtWidgets.QWidget.__init__(self, parent)
        self.setupUi(self)

        self.additionalInfo.setHidden(True)

        self.cbHLDifferences.setVisible(fuzzypattern_matches_shown)
        self.fmAdjustSelection.setVisible(fuzzypattern_matches_shown and extra is not None)

        self.extra = extra
        self.candidate_idx = None
        if fuzzypattern_matches_shown:
            self.variatives = self.extra  # type: List[clones.VariativeElement]


        self.webView.setContextMenuPolicy(QtCore.Qt.CustomContextMenu)
        self.webView.customContextMenuRequested.connect(self.web_context_menu)

        self.menu = QtWidgets.QMenu()
        self.menu_create_ie = self.menu.addAction("Create information element")
        self.menu_create_ie.triggered.connect(lambda: self.feval_js("window.some2elem();"))
        self.menu_create_di = self.menu.addAction("Add dictionary entry")
        self.menu_create_di.triggered.connect(lambda: self.feval_js("window.single2dict();"))

        if save_fn == "":  # NOT fuzzy pattern search scenario
            self.tbSrcCode.setContextMenuPolicy(QtCore.Qt.DefaultContextMenu)
        elif fuzzypattern_matches_shown and extra is not None: # just fuzzy pattern search
            self.lbTableName.setText("Matches found:")

        self.acceptRangeAction = QAction("&Accept", self)
        self.ignoreRangeAction = QAction("&Ignore", self)
        self.newUUIDAction = QAction("&New UUID", self)
        self.saveSourceAction = QAction("&Save", self)
        self.saveSourceAndReheatAction = QAction("&Save and Rebuild Reuse Map", self)
        self.tbSrcCode.addAction(self.acceptRangeAction)
        self.tbSrcCode.addAction(self.ignoreRangeAction)
        self.tbSrcCode.addAction(self.newUUIDAction)
        self.tbSrcCode.addAction(self.saveSourceAction)
        self.tbSrcCode.addAction(self.saveSourceAndReheatAction)

        self.bindEvents()

        if fuzzypattern_matches_shown:
            self.bindSelShortcuts()

        self.textBrowser.setText(stats)
        self.editCoordinateCorrections = dict()

        def loaded(ok: bool):
            async def loaded_co():
                u = self.webView.page().url()
                if u.scheme() == 'about':
                    return util.ready_future()
                else:
                    print("URI loaded:", ok, "with URL:", u)
                    r = await self.aeval_js(pyqt_common.qwcjs(
                        """
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
                        """
                    ))
                    return r
            try:
                self.webChannel.registerObject("qtab", self)
                fu = loaded_co()
                asyncio.get_event_loop().run_until_complete(fu)
            except Exception as e:
                print("Exc:", e, file=sys.stderr)

        self.uri = uri
        # self.webView.settings().setAttribute(QtWebEngineWidgets.QWebEngineSettings.CSSGridLayoutEnabled, True)

        page = self.webView.page() #.addToJavaScriptWindowObject("qtab", self)
        self.webChannel = QWebChannel(page)
        page.setWebChannel(self.webChannel)

        try:
            qf = QtGui.QFont("monospace")
            qf.setStyleHint(QtGui.QFont.Monospace)
            self.tbSrcCode.setFont(qf)
        except Exception as e:
            print("Error setting font: " + str(e))

        self.fn = fn
        self.save_fn = save_fn
        if True:  # save_fn == "":  # NOT fuzzy pattern search scenario
            self.tbSrcCode.setPlainText(src)
        else:  # Not implemented correctly yet...
            self.tbSrcCode.setHtml(sourcemarkers.source_text_to_html(src))

        page.loadFinished.connect(loaded)
        page.load(QtCore.QUrl(uri))

    def close_tab(self):
        self.parent().removeWidget(self)

    def bindEvents(self):
        self.closeButton.clicked.connect(self.close_tab)
        self.btCancelAll.clicked.connect(self.parent().close)
        self.acceptRangeAction.triggered.connect(self.acceptRange)
        self.ignoreRangeAction.triggered.connect(self.ignoreRange)
        self.newUUIDAction.triggered.connect(self.newUUID)
        self.saveSourceAction.triggered.connect(self.saveSource)
        self.saveSourceAndReheatAction.triggered.connect(self.saveSourceAndReheat)
        self.cbHLDifferences.toggled.connect(self.cbHLDifferences_toggled)
        self.pbSSL.clicked.connect(self.pbSSL_t)
        self.pbSSR.clicked.connect(self.pbSSR_t)
        self.pbSEL.clicked.connect(self.pbSEL_t)
        self.pbSER.clicked.connect(self.pbSER_t)
        self.tbSrcCode.selectionChanged.connect(self.srcCodeSelectionSchanged)

    def bindSelShortcuts(self):
        self.ssl_s = QtWidgets.QShortcut(
            QtGui.QKeySequence(QtCore.Qt.Key_Control + QtCore.Qt.Key_Shift + QtCore.Qt.Key_B), self.fmAdjustSelection)
        self.ssr_s = QtWidgets.QShortcut(
            QtGui.QKeySequence(QtCore.Qt.Key_Control + QtCore.Qt.Key_B), self.fmAdjustSelection)
        self.sel_s = QtWidgets.QShortcut(
            QtGui.QKeySequence(QtCore.Qt.Key_Control + QtCore.Qt.Key_E), self.fmAdjustSelection)
        self.ser_s = QtWidgets.QShortcut(
            QtGui.QKeySequence(QtCore.Qt.Key_Control + QtCore.Qt.Key_Shift + QtCore.Qt.Key_E), self.fmAdjustSelection)

        self.ssl_s.activated.connect(self.pbSSL_t)
        self.ssr_s.activated.connect(self.pbSSR_t)
        self.sel_s.activated.connect(self.pbSEL_t)
        self.ser_s.activated.connect(self.pbSER_t)

    @QtCore.pyqtSlot()
    def srcCodeSelectionSchanged(self):
        cursor = self.tbSrcCode.textCursor()
        ss = cursor.selectionStart()
        se = cursor.selectionEnd() - 1

    def update_current_variative(self):
        import clones
        vs = self.variatives
        ix = self.candidate_idx
        if vs is None or ix is None:
            return

        cursor = self.tbSrcCode.textCursor()
        ss = cursor.selectionStart()
        se = cursor.selectionEnd() - 1
        if se <= ss:
            return

        cv = vs[ix]  # type: clones.VariativeElement
        oldgrp = cv.clone_groups[0]
        cf, cb, ce = oldgrp.instances[0] # type: clones.FuzzyCloneGroup
        newgrp = clones.FuzzyCloneGroup(oldgrp.id, [(cf, ss, se)])
        cv.clone_groups[0] = newgrp
        cv.html_idx = ix + 1

        newhtml = cv.html.strip()  # type: str
        upjs = 'window.updatecandidatetr(%d, "%s");' % (ix + 1, newhtml.replace('\r', '\\r').replace('\n', '\\n').replace('"', '\\"'))
        # print("UPJS:")
        # print(upjs)
        self.feval_js(upjs)

    def move_src_selection(self, delta_s, delta_e):
        cursor = self.tbSrcCode.textCursor()
        ss = cursor.selectionStart()
        se = cursor.selectionEnd() - 1
        self.src_select(ss + delta_s, se + delta_e, None)
        try:
            self.update_current_variative()
        except Exception as e:
            print("Current variative update exception:", repr(e), file=sys.stderr)

    @QtCore.pyqtSlot()
    def pbSSL_t(self):
        self.move_src_selection(-1, 0)

    @QtCore.pyqtSlot()
    def pbSSR_t(self):
        self.move_src_selection(1, 0)

    @QtCore.pyqtSlot()
    def pbSEL_t(self):
        self.move_src_selection(0, -1)

    @QtCore.pyqtSlot()
    def pbSER_t(self):
        self.move_src_selection(0, 1)

    @QtCore.pyqtSlot()
    def acceptRange(self):
        cursor = self.tbSrcCode.textCursor()
        se = cursor.selectionStart(), cursor.selectionEnd()
        sm = sourcemarkers.AcceptRangeMarker(se[0], se[1])
        pt = self.tbSrcCode.toPlainText()
        l1 = len(pt)
        pt = sm.apply(pt)
        dl = len(pt) - l1
        self.editCoordinateCorrections[se[0]] = dl
        self.tbSrcCode.setPlainText(pt)
        print("Accept range: " + repr(se))

    @QtCore.pyqtSlot()
    def ignoreRange(self):
        upjs = 'window.updatecandidatetr(%d, "");' % (self.candidate_idx + 1,)
        self.feval_js(upjs)

    def ignoreRange_0(self):
        cursor = self.tbSrcCode.textCursor()
        se = cursor.selectionStart(), cursor.selectionEnd()
        sm = sourcemarkers.IgnoreRangeMarker(se[0], se[1])
        pt = self.tbSrcCode.toPlainText()
        l1 = len(pt)
        pt = sm.apply(pt)
        dl = len(pt) - l1
        self.editCoordinateCorrections[se[0]] = dl
        self.tbSrcCode.setPlainText(pt)
        print("Ignore range: " + repr(se))

    @QtCore.pyqtSlot()
    def newUUID(self):
        sourcemarkers.RangeMarker.forceNewUUID()

    @QtCore.pyqtSlot()
    def saveSource(self):
        with open(self.save_fn, "w", encoding='utf8') as sf:
            sf.write(self.tbSrcCode.toPlainText())

    @QtCore.pyqtSlot()
    def saveSourceAndReheat(self):
        self.newUUID()
        self.saveSource()
        util.save_reformatted_file(self.save_fn)
        if hasattr(app, 'hm_bc_i'):
            app.hm_bc_i.setCursor(QtCore.Qt.WaitCursor)
            win = self
            while win.parentWidget():
                win = win.parentWidget()
            win.hide()
            app.enqueue(app.hm_bc_i.refreshND)
            app.enqueue(rebuildAndReloadHM)

    # No more option to show/hide markup in element browser, always hide it.
    # Markup should be highlighted in the source code.
    # Use git blame to see changes.
    # @QtCore.pyqtSlot(bool)
    # def show_clones_markup_toggled(self, v):
    #     self.eval_js("window.toggleclonebrowsermarkup(%s);" % ('true' if v else 'false',))

    @QtCore.pyqtSlot(bool)
    def cbHLDifferences_toggled(self, v):
        self.feval_js("window.toggleclonebrowserdiffs(%s);" % ('true' if v else 'false',))

    @QtCore.pyqtSlot(bool)
    def enable_dict(self, e):
        self.menu_create_di.setEnabled(e)

    @QtCore.pyqtSlot(QtCore.QPoint)
    def web_context_menu(self, point):
        self.menu.popup(self.webView.mapToGlobal(point))
        self.feval_js("window.decide_enable_dict(%d, %d);" % (point.x(), point.y()))

    @QtCore.pyqtSlot(str, str)
    def inf_dic_descs(self, infdesc, dicdesc):
        print("inf_dic_descs", self, infdesc, dicdesc)
        with open(os.path.join(os.path.dirname(clargs.clone_tool), "Output", "black_descriptor_list.txt"),
                  'w+') as blst:
            print(infdesc, file=blst)
            print(dicdesc, file=blst)

    @QtCore.pyqtSlot(str)
    def refactor_create_inf_elt(self, dicdesc):
        print("refactor_create_inf_elt", dicdesc)
        parms = [sys.executable, optverb, os.path.join(_scriptdir, "refactor.py"),
                 "-idf", self.fn,
                 "-odf", self.fn + ".refactored",
                 "--create-infelement", dicdesc
                 ]
        print("Executing:")
        print(' '.join(parms))
        rc = subprocess.call(parms)
        print("RC:" + str(rc))
        if not rc:
            # self.webView.setContent("<html><head><title/></head><body/></html>")
            global app
            app.exit(rc)

    @QtCore.pyqtSlot(str)
    def refactor_create_dic_entry(self, dicdesc):
        print("refactor_create_dic_entry", dicdesc)
        parms = [sys.executable, optverb, os.path.join(_scriptdir, "refactor.py"),
                 "-idf", self.fn,
                 "-odf", self.fn + ".refactored",
                 "--create-dictionary-entry", dicdesc
                 ]
        print("Executing:")
        print(' '.join(parms))
        rc = subprocess.call(parms)
        print("RC:" + str(rc))
        if not rc:
            # self.webView.setContent("<html><head><title/></head><body/></html>")
            global app
            app.exit(rc)

    def correct_coordinate(self, b, e):
        db = 0
        de = 0
        for k, v in self.editCoordinateCorrections.items():
            if b > k:
                db += v
            if e > k:
                de += v
        return b + db, e + de

    @QtCore.pyqtSlot(int, int, str)
    def src_select(self, start0, finish0, candidate_idx=None):
        start, finish = self.correct_coordinate(start0, finish0)
        c = self.tbSrcCode.textCursor()
        c.setPosition(start)
        c.setPosition(finish + 1, QtGui.QTextCursor.KeepAnchor)
        self.tbSrcCode.setTextCursor(c)
        self.tbSrcCode.setFocus()
        if candidate_idx:
            self.candidate_idx = int(candidate_idx) - 1
        print("change selection", start, finish, self.candidate_idx)

    @QtCore.pyqtSlot(str)
    def src_text(self, txt):
        self.tbSrcCode.setPlainText(txt)

    @QtCore.pyqtSlot(str)
    def clog(self, txt):
        print(txt)

    @QtCore.pyqtSlot(str)
    def eval_js(self, js):
        try:
            pyqt_common.eval_p_js_sync(self.webView.page(), js)
        except Exception as e:
            print("Exception in SYNC eval JS:", repr(e), file=sys.stderr)

    async def aeval_js(self, js):
        return await pyqt_common.eval_p_js_co(self.webView.page(), js)

    @QtCore.pyqtSlot(str)
    def feval_js(self, js):
        try:
            pyqt_common.eval_p_js_faf(self.webView.page(), js)
        except Exception as e:
            print("Exception in FAF eval JS:", repr(e), file=sys.stderr)


class ElemBrowserUI(QtWidgets.QMainWindow, pyqt_common.ui_class('element_browser_window.ui')):
    def __init__(self, parent=None, path=None):
        QtWidgets.QMainWindow.__init__(self, parent)
        self.setupUi(self)
        self.path = path if path else os.path.curdir
        # bindings
        self.bindEvents()

    shouldAddTab = pyqtSignal(str, str, str, str, str, str, bool, object, name='shouldAddTab')

    @QtCore.pyqtSlot(str, str, str, str, str, str, bool, object)
    def addbrTab(self, uri, heading, stats, text, fn, save_fn, fuzzymatch: bool, extra: object):
        # self.hide()

        if fuzzymatch:
            self.setWindowTitle("Near Duplicates")

        ntab = ElemBrowserTab(self, uri, stats, text, fn, save_fn, fuzzypattern_matches_shown=fuzzymatch, extra=extra)
        # Now let's only shw single tab
        while self.browserTabs.count():
            self.browserTabs.removeTab(0)
        self.browserTabs.addTab(ntab, heading if heading else uri)
        self.browserTabs.tabBar().setVisible(self.browserTabs.count() > 1)
        self.show()

    def bindEvents(self):
        self.actionE_xport.triggered.connect(self.exportReport)
        self.shouldAddTab.connect(self.addbrTab)

    @QtCore.pyqtSlot()
    def exportReport(self):
        dlg = QtWidgets.QFileDialog(self)
        sfn = dlg.getSaveFileName(self, 'Save Report to File', self.path, 'HTML files (*.html)')
        if sfn and isinstance(sfn, tuple) and len(sfn[0]):
            fn = sfn[0]
            tab = self.browserTabs.currentWidget() # type: ElemBrowserTab
            htmlpath = pyqt_common.url2path(tab.uri)
            util.save_standalone_html(htmlpath, fn)


class ElemMinerProgressUI(QtWidgets.QDialog, pyqt_common.ui_class('element_miner_progress.ui')):
    progressChanged = QtCore.pyqtSignal(int, int, str)

    @QtCore.pyqtSlot(int, int, str)
    def _change_progress(self, total, current, status):
        self.progressBar.setMaximum(total)
        self.progressBar.setValue(current)
        self.details.setText(status)
        if current == total:
            self.hide()
        else:
            self.show()

    def __init__(self, parent=None):
        QtWidgets.QDialog.__init__(self, parent)
        self.setupUi(self)
        self.progressChanged.connect(self._change_progress)



class SetupDialog(QtWidgets.QDialog, pyqt_common.ui_class('element_miner_settings.ui')):
    def __init__(self, parent=None):
        QtWidgets.QDialog.__init__(self, parent)
        self.setupUi(self)
        self.setFixedSize(self.size())
        self.bindEvents()

        # restricted UI customizations
        if _scriptname == 'duplicate-finder.py':
            self.setWindowTitle("Duplicate Finder")
            self.methodWidget.setHidden(True)
            self.fuzzyHeatSettings.setTitle("Settings")
            self.cbMethod.setCurrentIndex(1)

        if clargs.input_file:
            ifn = os.path.realpath(clargs.input_file)
            self.inFile.setText(ifn.replace("\\", "/"))

    def bindEvents(self):
        self.buttonBox.accepted.connect(self.dialog_ok)
        self.buttonBox.rejected.connect(lambda: sys.exit(0))
        self.btSelectFolder.clicked.connect(self.select_file)
        self.cbMaxVar.stateChanged.connect(self.cbMaxVar_checked)
        self.cbMaxVar_2.stateChanged.connect(self.cbMaxVar_2_checked)
        self.cbMethod.currentIndexChanged.connect(self.methodSelected)
        self.cbOnlyShowNearDuplicates.stateChanged.connect(self.onlyShowNDchanged)
        # self.swDevSettings.clicked.connect(self.showSwDevSettings)
        self.btAdditionalSettings.clicked.connect(self.showAdditionalSettings)

        for slider in [self.slClLen, self.slFfClLen, self.slFfEd, self.slFfHd,
                       self.slClLen_f, self.slGrpMinPow, self.slArchLen]:
            slider.valueChanged.connect(self.slider_moved)

        self.slClMaxLen_f.valueChanged.connect(self.slClMaxLenRotated)

    # @QtCore.pyqtSlot(bool)
    def onlyShowNDchanged(self, val):
        for slider in [self.slClLen_f, self.slClMaxLen_f, self.slGrpMinPow]:
            slider.setEnabled(not val)

    @QtCore.pyqtSlot(int)
    def slClMaxLenRotated(self, value):
        self.lbClMaxLen_f.setText("\u221E" if value > 200 else str(value))

    @QtCore.pyqtSlot(int)
    def methodSelected(self, idx):
        # print("method: ", idx)
        self.analyzerOptions.setCurrentIndex(idx)

    def showAdditionalSettings(self):
        self.swDevSettings.setCurrentIndex(1)

    def cbMaxVar_checked(self, val):
        self.sbMaxVar.setEnabled(val)

    def cbMaxVar_2_checked(self, val):
        self.sbMaxVar_2.setEnabled(val)

    @QtCore.pyqtSlot(int)
    def slider_moved(self, val):
        slName = self.sender().objectName()
        if slName.startswith("sl"):
            lbName = "lb" + slName[2:]
            lbl = getattr(self, lbName, None)
            if lbl:
                lbl.setText(str(val))
            else:
                print("Cant find label for slider: " + slName + " -> " + lbName)
        else:
            print("Wont find label for slider: " + slName)

    @QtCore.pyqtSlot()
    @util.excprint
    def dialog_ok(self):
        import pandoc_importer
        import external_analysis_tools
        methodIdx = self.cbMethod.currentIndex()

        infile = self.inFile.text()
        infile = pandoc_importer.import_file(infile, self.cbToDRL.isChecked())
        pui = ElemMinerProgressUI()

        if methodIdx == 0: # Clone Miner
            numparams = [int(self.lbClLen.text())]
        elif methodIdx == 2:  # Fuzzy Finder
            numparams = [slider.value() for slider in [self.slFfClLen, self.slFfEd, self.slFfHd]]
        elif methodIdx == 1: # Fuzzy Heat
            numparams = [slider.value() for slider in [self.slClLen_f]]
        elif methodIdx == 3:  # Heuristic NGram Finder
            numparams = []
        else:
            raise NotImplementedError("Unknown method: " + methodIdx)

        self.hide()
        pui.show()

        wt = None

        self.timer = QtCore.QTimer()  # preserve from GC
        wait_with_timer = True

        def wait_for_result_show_results():
            global clargs

            if wt.isFinished() or not wait_with_timer:
                self.timer.stop()
                pui.hide()

                if wt.fatal_error:
                    raise Exception("Error in analysis tool!")

                if not hasattr(self, 'elbrui'):
                    self.elbrui = ElemBrowserUI(path=os.path.split(infile)[0])  # preserve from GC... Again...

                self.elbrui.activateWindow()

                if methodIdx == 0 or methodIdx == 1 or methodIdx == 3: # Clone Miner or Fuzzy Heat
                    srcfn = infile + ".reformatted"
                elif methodIdx == 2:  # Fuzzy Finder or Near Duplicates report
                    srcfn = os.path.join(ffworkfolder, os.path.split(infile + ".reformatted")[-1])
                else:
                    raise NotImplementedError("Unknown method: " + methodIdx)

                # read input file
                srctext = ""
                def read_input_file():
                    nonlocal srctext
                    with open(srcfn, encoding='utf-8') as ifh:
                        srctext = ifh.read()
                read_input_file()

                forced_save_fn = infile if clargs.force_allow_acccept_ignore_save else ""

                if methodIdx == 0: # Clone Miner
                    # something sensible later
                    for l, o in zip(numparams, wt.outs):
                        ht = pyqt_common.path2url(
                            os.path.join(os.path.dirname(clargs.clone_tool), "Output", "%03d" % l, "pyvarelements.html"))
                        self.elbrui.addbrTab(ht, str(l), o, srctext, srcfn, forced_save_fn, False, None)
                elif methodIdx == 2 or methodIdx == 3:  # Fuzzy Finder
                    ht = pyqt_common.path2url(os.path.join(ffworkfolder, "pyvarelements.html"))
                    self.elbrui.addbrTab(ht, str(numparams), wt.ffstdoutstderr, srctext, srcfn, forced_save_fn, True, extra=None)
                elif methodIdx == 1 and not self.cbOnlyShowNearDuplicates.checkState():  # Fuzzy Heat Building
                    htp = os.path.join(os.path.dirname(clargs.clone_tool), "Output", "%03d" % numparams[0])
                    serve(infile, srcfn, self.elbrui, htp)  # start server

                    # webbrowser.open_new_tab("http://127.0.0.1:49999/")
                    # then elbrui should wait until user selects fragment to search

                    if not hasattr(app, 'hm_bc_i'):
                        app.hm_bc_i = hm_browser_complex.HMBrowserComplex(app=app)

                    app.hm_bc_i.show()
                    try:
                        app.hm_bc_i.loadHeatMap("http://127.0.0.1:49999/")
                        app.hm_bc_i.buildAndLoadND(infile, htp, self.cbInteractiveCalculateArchetype.isChecked())
                        # then app.hm_bc_i.refresh() will refresh this all when needed
                    except Exception as ee:
                        print(repr(ee), file=sys.stderr)

                elif methodIdx == 1 and self.cbOnlyShowNearDuplicates.checkState():  # Fuzzy Heat Report
                    ht = pyqt_common.path2url(os.path.join(ffworkfolder, "pyvarelements.html"))
                    self.elbrui.addbrTab(ht, str(numparams), "", srctext, srcfn, forced_save_fn, True, extra=None)
                else:
                    raise NotImplementedError("Unknown method: " + methodIdx)

        if hasattr(app, 'reheat'):
            del app.reheat

        if methodIdx == 0: # Clone Miner
            wt = self.launch_with_clone_miner(pui, infile, numparams)
        elif methodIdx == 2:  # Fuzzy Finder
            # wt, ffworkfolder = self.launch_with_fuzzy_finder(pui, infile, numparams)
            wt, ffworkfolder = self.launch_with_ngram_dup_finder(pui, infile, numparams)
        elif methodIdx == 3:  # Heuristic
            # wt, ffworkfolder = self.launch_with_fuzzy_finder(pui, infile, numparams)
            wt, ffworkfolder = external_analysis_tools.run_heuristic_finder_and_report(
                pui, infile, self.cbHeuristicCalculateArchetype.isChecked(), app
            )
        elif methodIdx == 1 and not self.cbOnlyShowNearDuplicates.checkState():  # Fuzzy Heat Building
            def re_launch_fuzzyheat_with_clone_miner():
                nonlocal wt
                self.timer.start(500)
                app.launch_fh_builder = lambda : self.launch_fuzzyheat_with_clone_miner(pui, infile, numparams)
                wt = app.launch_fh_builder()
                wait_for_result_show_results()
            app.reheat = re_launch_fuzzyheat_with_clone_miner
            re_launch_fuzzyheat_with_clone_miner()
        elif methodIdx == 1 and self.cbOnlyShowNearDuplicates.checkState():  # Fuzzy Heat Report
            wait_with_timer = False # call multiple times, will use callbacks
            wt, ffworkfolder = self.launch_fuzzyheat_reporting(pui, infile, wait_for_result_show_results)
        else:
            raise NotImplementedError("Unknown method: " + methodIdx)

        if wait_with_timer:
            self.timer.timeout.connect(wait_for_result_show_results)
            self.timer.start(500)

    def launch_with_fuzzy_finder(self, pui, infile, numparams):
        global elbrui

        ffworkfolder = os.path.join(
            os.path.dirname(infile),
            "_analysis",
            os.path.split(infile)[-1],
            "-".join(map(str, numparams))
        )
        os.makedirs(ffworkfolder, exist_ok=True)
        shutil.copy(infile, ffworkfolder)

        wt = run_fuzzy_finder_thread(pui, infile, numparams, self.cbSrcLang.currentText(), ffworkfolder)
        return wt, ffworkfolder

    def launch_with_ngram_dup_finder(self, pui, infile, numparams):
        global elbrui

        ffworkfolder = os.path.dirname(infile)
        wt = run_ngram_dup_finder_thread(pui, infile, numparams, self.cbSrcLang.currentText(), ffworkfolder)
        return wt, ffworkfolder


    def launch_with_heuristic_ngram_dup_finder(self, pui, infile, numparams):
        # !!! !!!
        global elbrui

        ffworkfolder = os.path.dirname(infile)
        wt = run_ngram_dup_finder_thread(pui, infile, numparams, self.cbSrcLang.currentText(), ffworkfolder)
        return wt, ffworkfolder


    def launch_fuzzyheat_reporting(self, pui, infile, onready):
        wt, ffworkfolder = run_nearduplicate_report_thread(pui, infile, onready)
        return wt, ffworkfolder

    def launch_fuzzyheat_with_clone_miner(self, pui, infile, numparams):
        options = [
            "-wv", "no",
            "-minl", "5",
            "-cmup", "no",
            "-fint", "no",
            "-csp", "no",
        ]

        if self.slClMaxLen_f.value() <= 200:
            options += ['-maxctl', str(self.slClMaxLen_f.value())]

        if self.slGrpMinPow.value() > 2:
            options += ['-mingpow', str(self.slGrpMinPow.value())]

        wt = run_fuzzyheat_with_clone_miner_thread(pui, infile, options, numparams)
        return wt

    def launch_with_clone_miner(self, pui, infile, lengths):
        # easter egg -- maxvar = 0 & unchecked maxvar should avoid it from
        # combining (only single clones, no variations in output)
        # needed for debugging...
        # dovariations = self.cbMaxVar.checkState() or self.sbMaxVar.value() != 0

        dovariations = self.cbCa.currentText() != "None"

        options = [
            # default settings
            "-wv", "yes" if dovariations else "no",
            "-minl", "5",

            # for this Qt UI
            "-wrs", "yes",
            "-oui", "no" # for ui and export
        ]
        if dovariations:
            options += ["-nb", "100"]

        options += ["-cmup", ["no", "yes", "shrink"][self.cbCheckMup.currentIndex()]]
        options += ["-fint", "no" if self.cbAllowInt.checkState() else "yes"]
        options += ["-csp", ["no", "yes", "nltk"][self.cbxCheckSemantics.currentIndex()]]
        # options += ["-gca", clargs.group_combining_algorithm]

        if dovariations:
            if self.cbCa.currentText() == "NEXT16":
                options += ["-gca", "interval-n-ext"]
            else:  # 1EXT14
                options += ["-gca", "full-square"]

            if self.cbMaxVar.checkState():
                options += ["-mr", str(self.sbMaxVar.value())]

            if self.cbMaxVar_2.checkState():
                options += ["-mv", str(self.sbMaxVar_2.value())]

        options += ["-minal", self.lbArchLen.text()]
        options += ["-bvt", str(self.sbMaxDeltaRatio.value() / 100.0)]
        options += ["-pjf", str(self.cbPostJunkFilter.checkState())]

        wt = run_clone_miner_thread(pui, infile, lengths, options)
        return wt

    def select_file(self):
        print("Selecting file...")
        infname = str(QtWidgets.QFileDialog.getOpenFileName(self, "Select File to Analyze")[0])
        self.inFile.setText(infname)

def run_fuzzy_finder_thread(pui, inputfile, numparams, language, workingfolder):
    global clargs, app
    inputfile = inputfile.replace('/', os.sep)

    pui.progressChanged.emit(2, 0, "Preparing...")
    app.processEvents()

    class FuzzyWorkThread(QtCore.QThread):
        def __init__(self):
            QtCore.QThread.__init__(self)
            self.ffstdoutstderr = ""
            self.fatal_error = False

        @util.excprint
        def run(self):
            outdec = locale.getpreferredencoding(False)

            [frgamentsize, maxeditdist, maxhashdist] = numparams
            threads = 1
            inputfilename = os.path.split(inputfile)[-1]

            popen_args = [clargs.fuzzy_finder_tool] + [
                "-document", inputfilename,
                "-frgamentsize", str(frgamentsize),
                "-maxeditdist", str(maxeditdist),
                "-maxhashdist", str(maxhashdist),
                "-threads", str(threads),
                "-language", language
            ]

            if os.name == 'posix': popen_args = ["mono"] + popen_args
            print("Finding fuzzy clones with: " + ' '.join(popen_args))

            pui.progressChanged.emit(2, 0, "Finding fuzzy clones...")
            app.processEvents()

            ffpr = subprocess.Popen(popen_args,
                                                stdout=subprocess.PIPE,
                                                stdin=subprocess.PIPE,
                                                stderr=subprocess.PIPE,
                                                cwd=workingfolder)
            oe = ffpr.communicate(input=b'\n')
            ffrc = ffpr.returncode
            ffstdout = oe[0].decode(outdec)
            ffstderr = oe[1].decode(outdec)
            self.ffstdoutstderr = ffstdout + os.linesep + ffstderr
            if ffrc != 0 or "Exception was thrown:" in ffstderr:
                self.fatal_error = True
                print("Returned: " + str(ffrc))
                print(self.ffstdoutstderr)
                return

            reformattedfilename = inputfilename + '.reformatted'
            fuzzyclonesfilename = inputfilename + '.fuzzyclones.xml'

            pui.progressChanged.emit(2, 1, "Preparing report...")
            app.processEvents()

            popen_args = [
                sys.executable, optverb, os.path.join(_scriptdir, "fuzzyclones2html.py"),
                '-oui', 'no', # gen for ui and export
                '-sx', reformattedfilename,
                '-fx', fuzzyclonesfilename,
                '-od', workingfolder]
            print("Browsing fuzzy clones with: " + ' '.join(popen_args))

            cbpr = subprocess.Popen(popen_args,
                                                stdout=subprocess.PIPE,
                                                stdin=subprocess.PIPE,
                                                stderr=subprocess.STDOUT,
                                                cwd=workingfolder)

            oe = cbpr.communicate(input=b'\n')
            cbrc = cbpr.returncode
            if cbrc != 0:
                self.fatal_error = True
                print("Returned: " + str(cbrc))
                print(oe[0].decode(outdec))

            pui.progressChanged.emit(2, 2, "Done")
            app.processEvents()

    wt = FuzzyWorkThread()
    wt.start()
    return wt


def run_ngram_dup_finder_thread(pui, inputfile, numparams, language, workingfolder):
    global clargs, app
    inputfile = inputfile.replace('/', os.sep)

    pui.progressChanged.emit(2, 0, "Preparing...")
    app.processEvents()

    class NGramWorkThread(QtCore.QThread):
        def __init__(self):
            QtCore.QThread.__init__(self)
            self.ffstdoutstderr = ""
            self.fatal_error = False

        @util.excprint
        def run(self):
            outdec = locale.getpreferredencoding(False)
            inputfilename = os.path.basename(inputfile)
            ndf_py = os.path.join(_scriptdir, 'ngram_duplicate_finder', 'ndf.py')

            reformattedfilename = inputfilename + '.reformatted'
            util.save_reformatted_file(inputfile)

            outputfilename = inputfilename + ".reformatted.groups.json"

            popen_args = [sys.executable, ndf_py, reformattedfilename, language.lower()]

            pui.progressChanged.emit(2, 0, "Finding NGram near duplicates...")
            app.processEvents()

            ffpr = subprocess.Popen(popen_args,
                                                stdout=subprocess.PIPE,
                                                stdin=subprocess.PIPE,
                                                stderr=subprocess.PIPE,
                                                cwd=workingfolder)
            oe = ffpr.communicate(input=b'\n')
            ffrc = ffpr.returncode
            ffstdout = oe[0].decode(outdec)
            ffstderr = oe[1].decode(outdec)
            self.ffstdoutstderr = ffstdout + os.linesep + ffstderr
            if ffrc != 0 or "Exception was thrown:" in ffstderr:
                self.fatal_error = True
                print("Returned: " + str(ffrc))
                print(self.ffstdoutstderr)
                return

            pui.progressChanged.emit(2, 1, "Preparing report...")
            app.processEvents()

            popen_args = [sys.executable, optverb, os.path.join(_scriptdir, "fuzzyclones2html.py"),
                '-oui', 'no', # gen for ui and export
                '-sx', reformattedfilename,
                '-ndgj', outputfilename,
                '-od', workingfolder]
            print("Browsing NGram duplicates with: " + ' '.join(popen_args))

            cbpr = subprocess.Popen(popen_args,
                                                stdout=subprocess.PIPE,
                                                stdin=subprocess.PIPE,
                                                stderr=subprocess.STDOUT,
                                                cwd=workingfolder)

            oe = cbpr.communicate(input=b'\n')
            cbrc = cbpr.returncode
            if cbrc != 0:
                self.fatal_error = True
                print("Returned: " + str(cbrc))
                print(oe[0].decode(outdec))

            pui.progressChanged.emit(2, 2, "Done")
            app.processEvents()

    wt = NGramWorkThread()
    wt.start()
    return wt

def do_fuzzy_pattern_search_CLI(inputfilename, ui, minsim, text, srctext):
    outdir = inputfilename + ".fuzzypattern"
    os.makedirs(outdir, exist_ok=True)
    args = [
        sys.executable, optverb, os.path.join(_scriptdir, "onefuzzyclone2html.py"),
        "-ms", minsim,
        "-pn", text,
        "-id", inputfilename,
        "-od", outdir
    ]
    subprocess.call(args)
    savefilename = inputfilename
    if savefilename.endswith(".reformatted"):
        savefilename = savefilename[:-12]
    else:
        print("WARNING! inputfilename", inputfilename, "does not end with .reformatted")
    ui.shouldAddTab.emit(
        pyqt_common.path2url(os.path.join(outdir, "pyvarelements.html")),
        "Fuzzy Search results", "", srctext, inputfilename,
        savefilename, True, None
    )


def do_fuzzy_pattern_search_API(inputfilename, ui, minsim, pattern, srctext):
    import onefuzzyclone2html
    import clones
    outdir = inputfilename + ".fuzzypattern"
    os.makedirs(outdir, exist_ok=True)
    clones.VariativeElement._html_idx = 0
    # pui = ElemMinerProgressUI()
    # pui.show()
    # pui.progressChanged.emit(1, 3, "Searching for near duplicates...")
    with util.QHourGlass():
        variatives = onefuzzyclone2html.get_variative_elements(
            inputfilename, pattern, outdir,
            minimal_similarity=float(minsim),
            diff_against_pattern=False
        )
    # pui.progressChanged.emit(2, 3, "Creating report...")
    savefilename = inputfilename
    if savefilename.endswith(".reformatted"):
        savefilename = savefilename[:-12]
    else:
        print("WARNING! inputfilename", inputfilename, "does not end with .reformatted")
    ui.shouldAddTab.emit(
        pyqt_common.path2url(os.path.join(outdir, "pyvarelements.html")),
        "Fuzzy Search results", "", srctext, inputfilename,
        savefilename, True, variatives
    )
    # pui.hide()
    # del pui

def serve(input_filename, reformatted_filename, ui, htp):
    import time
    server_start_time = time.time()

    @bottle.route('/fuzzysearch')
    def fuzzysearch():
        msim = bottle.request.query.minsim
        text = bottle.request.query.text
        with open(reformatted_filename, encoding='utf-8') as inf:
            srctext = inf.read()
        sdt = threading.Thread(target=lambda: do_fuzzy_pattern_search_API(reformatted_filename, ui, msim, text, srctext))
        sdt.setDaemon(False)
        sdt.start()

        return "Searching for text <<<%s>>> with min similarity %s..." % (util.escapen(text), msim)

    @bottle.route('/shutdown')
    def shutdown():
        import os
        import signal
        os.kill(os.getpid(), signal.SIGTERM)

    @bottle.route('/')
    def index():
        import html_templates
        import string
        return string.Template(html_templates.densitybrowser_template).substitute({'abspath': '.', 'gentime' : server_start_time})

    @bottle.route("/gentime")
    def gentime():
        return str(server_start_time)

    @bottle.route("/edit/delete_group")
    @util.excprint
    def del_group():
        grp_id = bottle.request.query.grp_id
        print(f"Deleting group <{grp_id}>...")
        ndgmgr.p_delete_group(input_filename, grp_id)
        app.enqueue(rebuildAndReloadHM)
        app.enqueue(app.hm_bc_i.refreshND)

    @bottle.route("/edit/delete_duplicate")
    @util.excprint
    def del_duplicate():
        grp_id = bottle.request.query.grp_id
        dup_index = bottle.request.query.dup_ind
        print(f"Deleting dup <{grp_id}>[{dup_index}]...")
        ndgmgr.p_delete_dup(input_filename, grp_id, int(dup_index))
        app.enqueue(rebuildAndReloadHM) # too slow =)
        app.enqueue(app.hm_bc_i.refreshND)

    @bottle.route("/<url:re:(.*\\.html)>")
    def index(url):
        """
        Static generated files
        """
        with open(os.path.join(htp, url), encoding='utf-8') as ifile:
            return ifile.read()

    def shutwownanother():
        """Shutdown existing server if needed"""
        import urllib.request as r
        try:
            r.urlopen("http://127.0.0.1:49999/shutdown")
            print("Shutting down existing server")
        except Exception as e:
            print("No server was running, also ok")

    if not hasattr(app, 'reheat'):
        shutwownanother()

    # Daemonize bottle so closing app window will also kill it
    st = threading.Thread(target=lambda: bottle.run(host='127.0.0.1', port=49999))
    st.setDaemon(True)
    st.start()

class NearDuplicateWorkThread(QtCore.QThread):
    def __init__(self, pui, inputfile, workfolder, continuation=None):
        QtCore.QThread.__init__(self)
        self.pui = pui
        self.inputfile = inputfile
        self.workfolder = workfolder
        self.fatal_error = False
        def nin():
            print("No continuation initialized")
        self.continuation = continuation if continuation else nin
        self.setup_autoupdate()

    @QtCore.pyqtSlot()
    def timertick(self):
        """
        Check if input file changed and then renew our report
        """
        # print("Autoupdate timer...")
        ninputfiletime = os.path.getmtime(self.inputfile)
        if ninputfiletime > self.inputfiletime:
            self.inputfiletime = ninputfiletime
            self.start()

    def setup_autoupdate(self):
        self.inputfiletime = os.path.getmtime(self.inputfile)
        app.near_duplicate_report_thread = self
        app.near_duplicate_report_timer = QtCore.QTimer(app)
        app.near_duplicate_report_timer.timeout.connect(self.timertick)
        app.near_duplicate_report_timer.setInterval(3000)  # once per 5 seconds
        app.near_duplicate_report_timer.start()

    @util.excprint
    def run(self):
        os.makedirs(self.workfolder, exist_ok=True)
        # shutil.copy(self.inputfile, self.workfolder)
        # ifb = os.path.basename(self.inputfile)

        self.pui.progressChanged.emit(1, 2, "Reporting marked duplicates...")
        app.processEvents()

        popen_args = [
                         sys.executable, pyqt_common.optverb, os.path.join(_scriptdir, "nearduplicates2html.py"),
                         "-sx", self.inputfile,
                         "-od", self.workfolder
                     ]
        print("Reporting with: " + ' '.join(popen_args))
        reppr = subprocess.Popen(popen_args, stdout=subprocess.PIPE)

        oe = reppr.communicate()
        ffrc = reppr.returncode

        self.pui.progressChanged.emit(2, 2, "Done")
        app.processEvents()
        app.enqueue(self.continuation)

class CloneMinerWorkThread(QtCore.QThread):
    do_not_run_anymore = False
    current_run_is_last = False

    def __init__(self, pui, inputfile, lengths, options, last=False):
        super(CloneMinerWorkThread, self).__init__()
        self.pui = pui
        self.inputfile = inputfile
        self.lengths = lengths
        self.options = options

        self.outs = []
        self.fatal_error = False
        CloneMinerWorkThread.current_run_is_last |= last

    @util.excprint
    def run(self):
        cnt = 0
        for l in self.lengths:
            with pyqt_common.pushd_c(os.path.dirname(clargs.clone_tool)):
                def write_inputfiles_txt(posix2win):
                    if posix2win:
                        with open(os.path.join("Input", "InputFiles.txt"), 'wb+') as iftxt:
                            # Clone Miner is non-unicode windows program,
                            # under UN*X it it uses default encoding for specified
                            # language given to wine
                            iftxt.write(
                                pyqt_common.adapt_path_2_win(self.inputfile).encode(pyqt_common.adapt_filename_enc_2_win())
                            )
                    else:
                        with open(os.path.join("Input", "InputFiles.txt"), 'w+') as iftxt:
                            print(pyqt_common.adapt_path_2_win(self.inputfile), end='', file=iftxt)

                cplus = 0
                if not CloneMinerWorkThread.do_not_run_anymore:
                    write_inputfiles_txt(os.name == 'posix')

                    smsg = "Mining clones of >= %d tokens..." % l
                    self.pui.progressChanged.emit(len(self.lengths) * 150, cnt * 150 + cplus, smsg)

                    # run clone miner
                    popen_args = [clargs.clone_tool, str(l), '0', '0']
                    if os.name == 'posix': popen_args = ["wine"] + popen_args
                    print("Mining clones with: " + ' '.join(popen_args))

                    cmpr = subprocess.Popen(popen_args, stdout=subprocess.PIPE, stdin=subprocess.PIPE,
                                                        stderr=subprocess.STDOUT)
                    cmpr.communicate(input=b'\n')
                    cplus = 25

                # rewrite InputFiles.txt again because ...
                write_inputfiles_txt(False)  # ... because clones2html expects utf-8 there =)

                smsg = "Analyzing clones of >= %d tokens..." % l
                self.pui.progressChanged.emit(len(self.lengths) * 150, cnt * 150 + cplus, smsg)

                # run clone stats
                # pyrcom="/cygdrive/d/Python3/python.exe D:/VCSWF/docs.git/myprogs/python/CloneVisualizer/clones2html.py"
                # cline="nice -n 20 $pyrcom -nb 100 -mv 2000 -sd $t $bl -minl 5 -cmup yes -fint no -wv yes -ph $head"
                popen_args = [
                                 sys.executable, optverb, os.path.join(_scriptdir, "clones2html.py"),
                                 "-sd", str(l)
                             ] + self.options
                print("Reporting with: " + ' '.join(popen_args))

                reppr = subprocess.Popen(popen_args, stdout=subprocess.PIPE)
                repout = ""
                repoutc = 0

                while reppr.poll() is None:
                    pcn = 0
                    ln = reppr.stdout.readline().decode('utf-8').strip()
                    if repoutc == 1:
                        repoutc = 2
                        repout += ln
                        cplus = 50
                        smsg = "Combining groups..."
                    elif repoutc == 0 and ln.startswith("=> Ntok"): # stats line
                        repoutc = 1
                        repout += ln + "\n"
                    elif ln.endswith("%"):
                        try:
                            pcn0 = float(ln.split(" ")[-1][:-1])
                            pcn = pcn0 if repoutc else pcn0 / 4
                            pcn = int(pcn)
                        except Exception as e:
                            print("Error analyzing progress output: <<" + ln + ">> -- " + str(e))
                        self.pui.progressChanged.emit(len(self.lengths) * 150, cnt * 150 + pcn + cplus, smsg)

                self.outs.append(repout)

                cnt += 1
                self.pui.progressChanged.emit(len(self.lengths) * 150, cnt * 150, "Done")

        if CloneMinerWorkThread.current_run_is_last:
            CloneMinerWorkThread.do_not_run_anymore = True


def run_clone_miner_thread(pui, inputfile, lengths, options):
    global clargs, app
    inputfile = inputfile.replace('/', os.sep)

    pui.progressChanged.emit(len(lengths), 0, "")
    app.processEvents()


    wt = CloneMinerWorkThread(pui, inputfile, lengths, options)
    wt.start()
    return wt


def run_fuzzyheat_with_clone_miner_thread(pui, inputfile, options, numparams):
    global clargs, app
    inputfile = inputfile.replace('/', os.sep)

    pui.progressChanged.emit(1, 0, "")
    app.processEvents()

    wt = CloneMinerWorkThread(pui, inputfile, numparams, options, last=True)
    wt.start()
    return wt

def run_nearduplicate_report_thread(pui, infile, onready):
    workfolder = infile + ".neardups"

    pui.progressChanged.emit(1, 0, "")
    app.processEvents()

    wt = NearDuplicateWorkThread(pui, infile, workfolder, onready)
    wt.start()
    return wt, workfolder

if __name__ == '__main__':
    global clargs, app
    print("Script Dir = " + _scriptdir)
    initargs()

    app = EMUIApp(sys.argv)
    util.set_asio_el(QEventLoop(app))

    app.setWindowIcon(QtGui.QIcon(QtGui.QPixmap(os.path.join(_scriptdir, 'qtui', 'icon.png'))))

    d = SetupDialog()
    d.show()
    sys.exit(app.exec_())
