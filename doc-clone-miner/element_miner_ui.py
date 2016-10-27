#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Element miner UI. Licensed under GPL v3 after PyQt5 which is used here.
"""
import locale
import sys
import os
import argparse
import subprocess
import re

import shutil
from PyQt5 import QtCore, QtGui, QtWidgets, QtWebKit, uic

scriptdir = os.path.dirname(os.path.realpath(__file__))

def path2url(path):
    return QtCore.QUrl.fromLocalFile(path).toString()

def url2path(url):
    return QtCore.QUrl(url).toLocalFile()

# inspired by https://gist.github.com/Tatsh/7131812 
from os import chdir, getcwd
from os.path import realpath

# optverb = "-v"  # uncomment for debugging
optverb = "-OO"   # uncomment for production

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


def adapt_path_2_win(path):
    path = os.path.realpath(path)
    if os.name == 'posix':
        # running clone miner using wine
        # assuming root is mounted to z:\
        return "z:" + path.replace('/', '\\')
    else:
        return path


def initargs():
    argpar = argparse.ArgumentParser()
    # Windows one...
    # TODO: run Clone Miner with Wine if needed =)
    # And it is hardcoded not only here (and here it is hardcoded optionally =)).
    argpar.add_argument("-ct", "--clone-tool", help="Full path to clones.exe", default= os.path.join(scriptdir, "clone_miner", "clones.exe"))
    argpar.add_argument("-fft", "--fuzzy-finder-tool", help="Full path to CloneFinder.exe", default= os.path.join(scriptdir, "fuzzy_finder", "CloneFinder.exe"))
    argpar.add_argument("-if", "--input-file", help="Input file to analyze")
    global clargs
    clargs = argpar.parse_args()


def ui_class(name):
    return uic.loadUiType(os.path.join(os.path.dirname(os.path.realpath(__file__)), 'qtui', name))[0]

class ElemBrowserTab(QtWidgets.QWidget, ui_class('element_browser_tab.ui')):
    def __init__(self, parent, uri, stats, src="", fn=""):
        QtWidgets.QWidget.__init__(self, parent)
        self.setupUi(self)

        self.additionalInfo.setHidden(True)

        self.webView.setContextMenuPolicy(QtCore.Qt.CustomContextMenu)
        self.webView.customContextMenuRequested.connect(self.web_context_menu)

        self.menu = QtWidgets.QMenu()
        self.menu_create_ie = self.menu.addAction("Create information element")
        self.menu_create_ie.triggered.connect(lambda: self.eval_js("window.some2elem();"))
        self.menu_create_di = self.menu.addAction("Add dictionary entry")
        self.menu_create_di.triggered.connect(lambda: self.eval_js("window.single2dict();"))

        self.bindEvents()
        self.textBrowser.setText(stats)

        def loaded(ok):
            print("URI loaded: " + str(ok))
            self.webView.page().mainFrame().addToJavaScriptWindowObject("qtab", self)
            # self.eval_js("qtab.inf_dic_descs('123', '456');") test Python -> JS -> Python -- works ok, great!
            self.eval_js("window.adaptToQWebView();")

        self.uri = uri
        self.webView.settings().setAttribute(QtWebKit.QWebSettings.CSSGridLayoutEnabled, True)
        self.webView.loadFinished.connect(loaded)
        self.webView.load(QtCore.QUrl(uri))

        try:
            qf = QtGui.QFont("monospace")
            qf.setStyleHint(QtGui.QFont.Monospace)
            self.tbSrcCode.setFont(qf)
        except Exception as e:
            print("Error setting font: " + str(e))

        self.fn = fn
        self.tbSrcCode.setPlainText(src)

    def close_tab(self):
        self.parent().removeWidget(self)

    def bindEvents(self):
        self.closeButton.clicked.connect(self.close_tab)
        self.showClonesMarkup.toggled.connect(self.show_clones_markup_toggled)

    @QtCore.pyqtSlot(bool)
    def show_clones_markup_toggled(self, v):
        self.eval_js("window.toggleclonebrowsermarkup(%s);" % ('true' if v else 'false',))

    @QtCore.pyqtSlot(bool)
    def enable_dict(self, e):
        self.menu_create_di.setEnabled(e)

    @QtCore.pyqtSlot(QtCore.QPoint)
    def web_context_menu(self, point):
        self.eval_js("window.decide_enable_dict(%d, %d);" % (point.x(), point.y()))
        self.menu.popup(self.webView.mapToGlobal(point))

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
        parms = [sys.executable, optverb, os.path.join(scriptdir, "refactor.py"),
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
        parms = [sys.executable, optverb, os.path.join(scriptdir, "refactor.py"),
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

    @QtCore.pyqtSlot(int, int)
    def src_select(self, start, finish):
        c = self.tbSrcCode.textCursor()
        c.setPosition(start)
        c.setPosition(finish + 1, QtGui.QTextCursor.KeepAnchor)
        self.tbSrcCode.setTextCursor(c)
        self.tbSrcCode.setFocus()
        print("change selection", start, finish)

    @QtCore.pyqtSlot(str)
    def src_text(self, txt):
        self.tbSrcCode.setPlainText(txt)

    @QtCore.pyqtSlot(str)
    def clog(self, txt):
        print(txt)


    @QtCore.pyqtSlot(str)
    def eval_js(self, js):
        self.webView.page().mainFrame().evaluateJavaScript(js)


class ElemBrowserUI(QtWidgets.QMainWindow, ui_class('element_browser_window.ui')):
    def __init__(self, parent=None, path=None):
        QtWidgets.QMainWindow.__init__(self, parent)
        self.setupUi(self)
        self.path = path if path else os.path.curdir
        self.bindEvents()

    def addbrTab(self, uri, heading, stats, text = "", fn = ""):
        ntab = ElemBrowserTab(self, uri, stats, text, fn)
        self.browserTabs.addTab(ntab, heading if heading else uri)
        self.browserTabs.tabBar().setVisible(self.browserTabs.count() > 1)

    def bindEvents(self):
        self.actionE_xport.triggered.connect(self.exportReport)

    @QtCore.pyqtSlot()
    def exportReport(self):
        dlg = QtWidgets.QFileDialog(self)
        sfn = dlg.getSaveFileName(self, 'Save Report to File', self.path, 'HTML files (*.html)')
        if sfn and isinstance(sfn, tuple) and len(sfn[0]):
            fn = sfn[0]
            tab = self.browserTabs.currentWidget() # type: ElemBrowserTab
            htmlpath = url2path(tab.uri)
            with open(htmlpath, encoding='utf-8') as htmlsrc:
                htmlcontent = htmlsrc.read()

                def replace_ref_with_script(match): # want normal lambdas here...
                    jsfilename = os.path.join(
                        os.path.split(htmlpath)[0], # html dorectory
                        match.group(1) + ".js"
                    )
                    with open(jsfilename) as jsfile:
                        js = jsfile.read()
                        return """<script>%s</script>""" % js

                htmlcontent = re.sub("""<script src="(.*)\\.js"></script>""", replace_ref_with_script, htmlcontent)
                with open(fn, "w", encoding='utf-8') as ofl:
                    ofl.write(htmlcontent)

class ElemMinerProgressUI(QtWidgets.QDialog, ui_class('element_miner_progress.ui')):
    progressChanged = QtCore.pyqtSignal(int, int, str)

    @QtCore.pyqtSlot(int, int, str)
    def _change_progress(self, total, current, status):
        self.progressBar.setMaximum(total)
        self.progressBar.setValue(current)
        self.details.setText(status)

    def __init__(self, parent=None):
        QtWidgets.QDialog.__init__(self, parent)
        self.setupUi(self)
        self.progressChanged.connect(self._change_progress)


class SetupDialog(QtWidgets.QDialog, ui_class('element_miner_settings.ui')):
    def __init__(self, parent=None):
        QtWidgets.QDialog.__init__(self, parent)
        self.setupUi(self)
        self.setFixedSize(self.size())
        self.bindEvents()
        if clargs.input_file:
            ifn = os.path.realpath(clargs.input_file)
            self.inFile.setText(ifn.replace("\\", "/"))

    def bindEvents(self):
        self.buttonBox.accepted.connect(self.dialog_ok)
        self.buttonBox.rejected.connect(lambda: sys.exit(0))
        self.btSelectFolder.clicked.connect(self.select_file)
        self.cbMaxVar.stateChanged.connect(self.cbMaxVar_checked)
        self.cbMethod.currentIndexChanged.connect(self.methodSelected)

        for slider in [self.slClLen, self.slFfClLen, self.slFfEd, self.slFfHd]:
            slider.valueChanged.connect(self.slider_moved)

    @QtCore.pyqtSlot(int)
    def methodSelected(self, idx):
        # print("method: ", idx)
        self.analyzerOptions.setCurrentIndex(idx)

    def cbMaxVar_checked(self, val):
        self.sbMaxVar.setEnabled(val)

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

    def dialog_ok(self):
        infile = self.inFile.text()
        pui = ElemMinerProgressUI()

        methodIdx = self.cbMethod.currentIndex()
        if methodIdx == 0: # Clone Miner
            numparams = [int(self.lbClLen.text())]
        elif methodIdx == 1:  # Fuzzy Finder
            numparams = [slider.value() for slider in [self.slFfClLen, self.slFfEd, self.slFfHd]]
        else:
            raise NotImplementedError("Unknown method: " + methodIdx)

        self.hide()
        pui.show()

        wt = None
        if methodIdx == 0: # Clone Miner
            wt = self.launch_with_clone_miner(pui, infile, numparams)
        elif methodIdx == 1:  # Fuzzy Finder
            wt, ffworkfolder = self.launch_with_fuzzy_finder(pui, infile, numparams)
        else:
            raise NotImplementedError("Unknown method: " + methodIdx)

        self.timer = QtCore.QTimer()  # preserve from GC

        def wait_for_result_show_results():
            if wt.isFinished():
                self.timer.stop()
                pui.hide()

                if wt.fatal_error:
                    raise Exception("Error in analysis tool!")

                self.elbrui = ElemBrowserUI(path=os.path.split(infile)[0])  # preserve from GC... Again...
                self.elbrui.show()

                if methodIdx == 0: # Clone Miner
                    srcfn = infile + ".reformatted"
                elif methodIdx == 1:  # Fuzzy Finder
                    srcfn = os.path.join(ffworkfolder, os.path.split(infile + ".reformatted")[-1])
                else:
                    raise NotImplementedError("Unknown method: " + methodIdx)

                # read input file
                srctext = ""
                with open(srcfn, encoding='utf-8') as ifh:
                    srctext = ifh.read()

                if methodIdx == 0: # Clone Miner
                    # something sensible later
                    for l, o in zip(numparams, wt.outs):
                        ht = path2url(
                            os.path.join(os.path.dirname(clargs.clone_tool), "Output", "%03d" % l, "pyvarelements.html"))
                        self.elbrui.addbrTab(ht, str(l), o, srctext, srcfn)
                elif methodIdx == 1: # Fuzzy Finder
                    ht = path2url(os.path.join(ffworkfolder, "pyvarelements.html"))
                    self.elbrui.addbrTab(ht, str(numparams), wt.ffstdoutstderr, srctext, srcfn)
                else:
                    raise NotImplementedError("Unknown method: " + methodIdx)

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

    def launch_with_clone_miner(self, pui, infile, lengths):
        global elbrui

        # easter egg -- maxvar = 0 & unchecked maxvar should avoid it from
        # combining (only single clones, no variations in output)
        # needed for debugging...
        dovariations = self.cbMaxVar.checkState() or self.sbMaxVar.value() != 0

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

        if dovariations and self.cbMaxVar.checkState():
            options += ["-mv", str(self.sbMaxVar.value())]

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
                sys.executable, optverb, os.path.join(scriptdir, "fuzzyclones2html.py"),
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

def run_clone_miner_thread(pui, inputfile, lengths, options):
    global clargs, app
    inputfile = inputfile.replace('/', os.sep)

    pui.progressChanged.emit(len(lengths), 0, "")
    app.processEvents()

    class WorkThread(QtCore.QThread):
        def __init__(self):
            QtCore.QThread.__init__(self)
            self.outs = []
            self.fatal_error = False

        def run(self):
            cnt = 0
            for l in lengths:
                with pushd_c(os.path.dirname(clargs.clone_tool)):
                    with open(os.path.join("Input", "InputFiles.txt"), 'w+') as iftxt:
                        print(adapt_path_2_win(inputfile), end='', file=iftxt)

                    cplus = 0
                    smsg = "Mining clones of >= %d tokens..." % l
                    pui.progressChanged.emit(len(lengths) * 150, cnt * 150 + cplus, smsg)

                    # run clone miner
                    popen_args = [clargs.clone_tool, str(l), '0', '0']
                    if os.name == 'posix': popen_args = ["wine"] + popen_args
                    print("Mining clones with: " + ' '.join(popen_args))

                    cmpr = subprocess.Popen(popen_args, stdout=subprocess.PIPE, stdin=subprocess.PIPE,
                                            stderr=subprocess.STDOUT)
                    cmpr.communicate(input=b'\n')
                    cmrc = cmpr.returncode

                    cplus = 25
                    smsg = "Analyzing clones of >= %d tokens..." % l
                    pui.progressChanged.emit(len(lengths) * 150, cnt * 150 + cplus, smsg)

                    # run clone stats
                    # pyrcom="/cygdrive/d/Python3/python.exe D:/VCSWF/docs.git/myprogs/python/CloneVisualizer/clones2html.py"
                    # cline="nice -n 20 $pyrcom -nb 100 -mv 2000 -sd $t $bl -minl 5 -cmup yes -fint no -wv yes -ph $head"
                    popen_args = [
                                     sys.executable, optverb, os.path.join(scriptdir, "clones2html.py"),
                                     "-sd", str(l)
                                 ] + options
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
                            pui.progressChanged.emit(len(lengths) * 150, cnt * 150 + pcn + cplus, smsg)
                    reprc = reppr.returncode

                    self.outs.append(repout)

                    cnt += 1
                    pui.progressChanged.emit(len(lengths) * 150, cnt * 150, "Done")

    wt = WorkThread()
    wt.start()
    return wt


if __name__ == '__main__':
    print("Script Dir = " + scriptdir)
    initargs()
    global app
    app = QtWidgets.QApplication(sys.argv)
    app.setWindowIcon(QtGui.QIcon(QtGui.QPixmap(os.path.join(scriptdir, 'qtui', 'icon.png'))))

    d = SetupDialog()
    d.show()
    sys.exit(app.exec_())
