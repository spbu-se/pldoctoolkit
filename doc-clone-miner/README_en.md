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

* 64 bit [Python 3.7.x](https://www.python.org/downloads/)
* PyQt, QuaMash, LXML, PyContracts, pygments, NumPy, intervaltree, bottle, pyinterval, pypandoc, chardet — `pip install -r py_requirements.txt`
* Optional:
    * Cython — `pip install Cython`. If this library is not installed, then the near duplicate search will run slower
    * PyInterval — `pip install  pyinterval`, it will probably require sideloading crlibm for Windows
    
System
------

* `x86_64` architecture PC to run Clone Miner, Windows or UN*X
* [Pandoc](http://pandoc.org/)
* [JRE 10+](https://java.com/) to run Heuristic Finder
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
