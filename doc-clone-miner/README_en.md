Documentation Refactoring Toolkit -- standalone clone mining and
document refactoring tool within DocLine project.

Overview
========

Documentation Refactoring Toolkit is a lightweight tool which can be
launched (using `element_miner_ui.py` main script) directly from source code
repository working folder without complicated DocLine setup.
System requirements are also simple enough, see 'Requirements' below.

Just after launching, it will display clone searching settings window.

User can specify:

* minimal detected clone length
* the way of XML markup checking and correction
* additional clone searching options:
    * (dis)allow clone intersections
    * enable and set upper bound of variative fragment size variance when
      looking for clones with extension point
    * thow away common language phrases carrying no semantic
*  source DRL file to analyze

After pressing 'OK', the tool works for some time, then it suggests the list
of сandidate clones to create DRL information elements and dictionary entries.
When launching from DocLine (Eclipse), refactoring results will e returned to
the source editor. When launching standalone, refactored file will be saved
as `<source file>.reformatterd.refactored`.

Examples / Tests
----------------

`tests/documentation` folder contains documentation of several open source
projects. To simplify the analysis, all documentation source files are
assembled together into the single per-project XML file.

Requirements
============

Python
------

* [Python 3.4.x](https://www.python.org/downloads/release/python-344/)
* [PyQt5 5.5.x](https://sourceforge.net/projects/pyqt/files/PyQt5/PyQt-5.5.1/)
* [LXML](https://pypi.python.org/pypi/lxml/3.6.0)
* [Python-Levenshtein](http://www.lfd.uci.edu/~gohlke/pythonlibs/#python-levenshtein):
  * On Windows, update Pip: `python -m pip install --upgrade pip`
  * Download either `python_Levenshtein-0.12.0-cp34-cp34m-win32.whl` or `python_Levenshtein-0.12.0-cp34-cp34m-win_amd64.whl`,
    which fits your Python
  * `pip install <your_package>.whl`
* PyContracts, pygments, NumPy, intervaltree, bottle, pyinterval — `pip install PyContracts pygments NumPy intervaltree bottle pyinterval`

Package versions -- most recent ones compatible with Python 3.4.

*Note: newer PyQt and Python can also be used in case your distribution contains `QtWebkit` as in PyQt 5.5.x*

System
------

* `x86` or `x86_64` architecture PC to run Clone Miner, Windows or UN*X
* on Windows:
    * [.NET Framework 4.5](https://www.microsoft.com/ru-ru/download/details.aspx?id=30653) to run Fuzzy Finder
* on UN*Xes:
    * [Wine](https://www.winehq.org/) to run Clone Miner
    * [Mono](http://www.mono-project.com/) 4.4.0+ to run Fuzzy Finder


License
=======

Sources with PyQt mentioned are licensed under GPL v3.
Documentation in tests folder is licensed under the terms of its initial sources licenses.
Everything other is licensed under LGPL v3.

Portable Distribution
=====================

With `build_dist/dcm_pack_windows.sh` you can create SFX archive.
This script is dedicated to use wih Windows and contains settings in itself.
Contact us to get actual portable distribution.
