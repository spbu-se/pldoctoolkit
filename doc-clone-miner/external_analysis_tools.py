#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import locale
import subprocess
import sys

import util
import os
from PyQt5 import QtCore

_scriptdir = os.path.dirname(os.path.realpath(__file__))
_scriptname = os.path.basename(os.path.realpath(__file__))

def run_heuristic_finder_and_report(pui: 'QtWidgets.QDialog', infile: 'str', app: 'EMUIApp') -> 'tuple[QtCore.QThread, str]':
    pui.progressChanged.emit(2, 0, "Preparing...")
    app.processEvents()

    infile = infile.replace('/', os.sep)
    infile = os.path.realpath(infile)
    wf = os.path.dirname(infile)
    infile_reformatted = infile + '.reformatted'
    util.save_reformatted_file(infile)
    outputfilename = os.path.join(_scriptdir, 'heuristic_finder', "out.json")

    class HeuristicWorkThread(QtCore.QThread):
        def __init__(self):
            super().__init__()
            self.wf = wf
            self.fatal_error = False


        def run(self):
            ffpr = subprocess.Popen([
                'java', '-jar',
                os.path.join(_scriptdir, 'heuristic_finder', 'NLPCloneDetector.jar'),
                infile_reformatted
            ], cwd=os.path.join(_scriptdir, 'heuristic_finder'))
            oe = ffpr.communicate(input=b'\n')
            if ffpr.returncode != 0:
                stde = oe[1].decode(locale.getpreferredencoding())
                self.fatal_error = True
                print("Returned: ", str(ffpr.returncode))
                print(stde)
                return

            pui.progressChanged.emit(2, 1, "Preparing report...")
            app.processEvents()

            popen_args = [sys.executable, '-OO', os.path.join(_scriptdir, "fuzzyclones2html.py"),
                '-oui', 'no', # gen for ui and export
                '-ndgj', outputfilename,
                '-sx', infile_reformatted,
                '-od', wf]
            print("Browsing NGram duplicates with: " + ' '.join(popen_args))

            cbpr = subprocess.Popen(popen_args,
                                                stdout=subprocess.PIPE,
                                                stdin=subprocess.PIPE,
                                                stderr=subprocess.STDOUT,
                                                cwd=wf)

            oe = cbpr.communicate(input=b'\n')
            cbrc = cbpr.returncode
            if cbrc != 0:
                self.fatal_error = True
                print("Returned: " + str(cbrc))
                print(oe[0].decode(locale.getpreferredencoding()))

            pui.progressChanged.emit(2, 2, "Done")
            app.processEvents()

    wt = HeuristicWorkThread()
    wt.start()
    return wt, wf

if __name__ == '__main__':
    raise Exception("This is not an entry point")