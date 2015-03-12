#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Element miner UI. Licensed under GPL v3 after PyQt5 which is used here.
"""

import sys
import os
from PyQt5 import QtCore, QtGui, QtWidgets, uic

import argparse
import subprocess
import time

def path2url(path):
    return QtCore.QUrl.fromLocalFile(path).toString()

# inspired by https://gist.github.com/Tatsh/7131812 
from os import chdir, getcwd
from os.path import realpath
class pushd_c:
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

def initargs():
    argpar = argparse.ArgumentParser()
    # Windows one...
    # TODO: run Clone Miner with Wine if needed =)
    # And it is hardcoded not only here (and here it is hardcoded optionally =)).
    argpar.add_argument("-ct", "--clone-tool", help="Full path to clones.exe", default=r"d:\clone_tool\clones.exe")
    argpar.add_argument("-if", "--input-file", help="Input file to analyze")
    global clargs
    clargs = argpar.parse_args()

def ui_class(name):
    return uic.loadUiType(os.path.join(os.path.dirname(os.path.realpath(__file__)), 'qtui', name))[0]

class ElemBrowserTab(QtWidgets.QWidget, ui_class('element_browser_tab.ui')):
    def __init__(self, parent, uri, stats):
        QtWidgets.QWidget.__init__(self, parent)
        self.setupUi(self)
        self.bindEvents()
        self.textBrowser.setText(stats)

        def loaded(ok):
            print("URI loaded: " + str(ok))
            self.webView.page().mainFrame().addToJavaScriptWindowObject("qtab", self)
            # self.eval_js("qtab.inf_dic_descs('123', '456');") test Python -> JS -> Python -- works ok, great!
            self.eval_js("window.adaptToQWebView();")

        self.webView.loadFinished.connect(loaded)
        self.webView.load(QtCore.QUrl(uri))

    def close_tab(self):
        self.parent().removeWidget(self)

    def bindEvents(self):
        self.closeButton.clicked.connect(self.close_tab)

    @QtCore.pyqtSlot(str, str)
    def inf_dic_descs(self, infdesc, dicdesc):
        print("inf_dic_descs", self, infdesc, dicdesc)
        with open(os.path.join(os.path.dirname(clargs.clone_tool), "Output", "black_descriptor_list.txt"), 'w+') as blst:
            print(infdesc, file=blst)
            print(dicdesc, file=blst)

    @QtCore.pyqtSlot(str)
    def eval_js(self, js):
        self.webView.page().mainFrame().evaluateJavaScript(js)

class ElemBrowserUI(QtWidgets.QMainWindow, ui_class('element_browser_window.ui')):
    def __init__(self, parent=None):
        QtWidgets.QMainWindow.__init__(self, parent)
        self.setupUi(self)

    def addbrTab(self, uri, heading, stats):
        ntab = ElemBrowserTab(self, uri, stats)
        self.browserTabs.addTab(ntab, heading if heading else uri)

class ElemMinerProgressUI(QtWidgets.QMainWindow, ui_class('element_miner_progress.ui')):
    progressChanged = QtCore.pyqtSignal(int, int, str)

    @QtCore.pyqtSlot(int, int, str)
    def _change_progress(self, total, current, status):
        self.progressBar.setMaximum(total)
        self.progressBar.setValue(current)
        self.details.setText(status)

    def __init__(self, parent=None):
        QtWidgets.QMainWindow.__init__(self, parent)
        self.setupUi(self)
        self.progressChanged.connect(self._change_progress)

class SetupDialog(QtWidgets.QDialog, ui_class('element_miner_settings.ui')):
    def __init__(self, parent=None):
        QtWidgets.QDialog.__init__(self, parent)
        self.setupUi(self)
        self.bindEvents()
        if clargs.input_file:
            ifn = os.path.realpath(clargs.input_file)
            self.inFile.setText(ifn.replace("\\", "/"))

    def bindEvents(self):
        self.accepted.connect(self.dialog_ok)
        self.btSelectFolder.clicked.connect(self.select_file)

    def dialog_ok(self):
        global elbrui

        infile = self.inFile.text()
        lengths = []
        for lii in range(self.cloneSizes.count()):
            li = self.cloneSizes.item(lii)
            if(li.checkState() != 0): lengths.append(int(li.text()))

        pui = ElemMinerProgressUI()
        self.hide()
        pui.show()

        options = [
            "-wv", "yes",
            # bl
            "-minl", "5"
        ]

        options += ["-cmup", ["no", "yes", "shrink"][self.cbCheckMup.currentIndex()]]
        options += ["-fint", "no"  if self.cbAllowInt.checkState() else "yes"]
        options += ["-csp", "yes"  if self.cbCheckSemantics.checkState() else "no"]

        if self.cbMinDist.checkState():
            options += ["-nb", str(self.sbMinDist.value())]
        if self.cbMaxVar.checkState():
            options += ["-mv", str(self.sbMaxVar.value())]

        wt = run_clone_miner_thread(pui, infile, lengths, options)

        self.timer = QtCore.QTimer() # preserve from GC
        def wait_for_result_show_results():
            if wt.isFinished():
                self.timer.stop()
                self.elbrui = ElemBrowserUI() # preserve from GC... Again...
                self.elbrui.show()
                pui.hide()
            
                # something sensible later
                for l, o in zip(lengths, wt.outs):
                    ht = path2url(os.path.join(os.path.dirname(clargs.clone_tool), "Output", "%03d" % l, "pyvarelements.html"))
                    self.elbrui.addbrTab(ht, str(l), o)

        self.timer.timeout.connect(wait_for_result_show_results)
        self.timer.start(500)

    def select_file(self):
        print("Selecting file...")
        infname = str(QtWidgets.QFileDialog.getOpenFileName(self, "Select File to Analyze")[0])
        self.inFile.setText(infname)

def run_clone_miner_thread(pui, inputfile, lengths, options):
    global clargs, app
    inputfile = inputfile.replace('/', os.sep)

    pui.progressChanged.emit(len(lengths), 0, "")
    app.processEvents()

    class WorkThread(QtCore.QThread):
        def __init__(self):
            QtCore.QThread.__init__(self)
            self.outs = []
 
        def run(self):
            cnt = 0
            for l in lengths:
                with pushd_c(os.path.dirname(clargs.clone_tool)):
                    with open(os.path.join("Input", "InputFiles.txt"), 'w+') as iftxt: print(inputfile, end='', file=iftxt)
        
                    pui.progressChanged.emit(len(lengths), cnt, "Processing clones of >= %d tokens" % l)
        
                    # run clone miner
                    cmpr = subprocess.Popen([clargs.clone_tool, str(l), '0', '0'], stdout=subprocess.PIPE, stdin=subprocess.PIPE, stderr=subprocess.STDOUT)
                    cmpr.communicate(input=b'\n')
                    cmrc = cmpr.returncode
        
                    # run clone stats
                    # pyrcom="/cygdrive/d/Python3/python.exe D:/VCSWF/docs.git/myprogs/python/CloneVisualizer/clones2html.py"
                    # cline="nice -n 20 $pyrcom -nb 100 -mv 2000 -sd $t $bl -minl 5 -cmup yes -fint no -wv yes -ph $head"
                    popen_args = [
                        sys.executable, os.path.join(scriptdir, "clones2html.py"),
                        "-sd", str(l)
                    ] + options
                    print("Reporting with: " + ' '.join(popen_args))

                    reppr = subprocess.Popen(popen_args, stdout=subprocess.PIPE)
                    repout = reppr.stdout.readline().decode('utf-8').strip() + '\n' + reppr.stdout.readline().decode('utf-8').strip()

                    while reppr.poll() is None:
                        ln = reppr.stdout.readline().decode('utf-8').strip()
                        pui.progressChanged.emit(len(lengths), cnt, ln)
                    reprc = reppr.returncode

                    self.outs.append(repout)
        
                    cnt += 1
                    pui.progressChanged.emit(len(lengths), cnt, "Done")

    wt = WorkThread()
    wt.start()
    return wt

if __name__ == '__main__':
    global scriptdir
    scriptdir = os.path.dirname(os.path.realpath(__file__))
    print("Script Dir = " + scriptdir)
    initargs()
    global app
    app = QtWidgets.QApplication(sys.argv)

    d = SetupDialog()
    d.show()
    sys.exit(app.exec_())
