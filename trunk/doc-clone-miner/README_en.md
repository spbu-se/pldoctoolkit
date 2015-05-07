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

* Python 3.4+
* PyQt5
* PyContracts
* pygments
* NumPy

Not exact versions, but preferrably most recent ones

System
------

* `x86` or `x86_64` architecture PC to run Clone Miner, Windows or UN*X
* on UN*Xes - [Wine](https://www.winehq.org/) to run CloneMiner

License
=======

Sources with PyQt mentioned are licensed under GPL v3.
Documentation in tests folder is licensed under the terms of its initial sources licenses.
Everything other is licensed under LGPL v3.
