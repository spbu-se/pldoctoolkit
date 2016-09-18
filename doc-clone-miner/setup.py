#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# thanks https://gist.github.com/MrLeeh/23585f7f50005408fc72
# D:\Python34\Lib\site-packages\PyQt5\uic\port_v2\load_plugin.py -- except Exception as e:

from setuptools import setup
import os
import glob
import py2exe

def find_data_files(source,target,patterns):
    """Locates the specified data-files and returns the matches
    in a data_files compatible format.

    source is the root of the source data tree.
        Use '' or '.' for current directory.
    target is the root of the target data tree.
        Use '' or '.' for the distribution directory.
    patterns is a sequence of glob-patterns for the
        files you want to copy.
    """
    if glob.has_magic(source) or glob.has_magic(target):
        raise ValueError("Magic not allowed in src, target")
    ret = {}
    for pattern in patterns:
        pattern = os.path.join(source,pattern)
        for filename in glob.glob(pattern):
            if os.path.isfile(filename):
                targetpath = os.path.join(target,os.path.relpath(filename,source))
                path = os.path.dirname(targetpath)
                ret.setdefault(path,[]).append(filename)
    return sorted(ret.items())

includes = ["sip",
            "PyQt5",
            "PyQt5.QtCore",
            "PyQt5.QtGui",
            "PyQt5.QtWebKit",
            "PyQt5.QtWebKitWidgets",
            "PyQt5.QtNetwork",
            "numpy",
            "pygments",
            "contracts"]

datafiles = [("platforms", ["d:\\Python34\\Lib\\site-packages\\PyQt5\\plugins" +
                            "\\platforms\\qwindows.dll"]),
             ("", [r"c:\windows\system32\MSVCP100.dll",
                   r"c:\windows\system32\MSVCR100.dll"]),
]

datafiles += find_data_files('','',['*.py'])
datafiles += find_data_files('','',['qtui/*', 'js/*', 'clone_miner/*', 'fuzzy_finder/*'])

datafiles += find_data_files('','',['tests/*'])

setup(
    name='doccloneminer',
#    version=VERSION,
#     packages=['doccloneminer'],
    url='https://github.com/spbu-se/pldoctoolkit/tree/master/doc-clone-miner',
    license='GPLv3',
    windows=[{"script": "element_miner_ui.py"}],
    scripts=['element_miner_ui.py'],
    data_files=datafiles,
    install_requires=['numpy>=1.11.0',
                      'pygments>=2.1.3',
                      'pycontracts>=1.7.9'],
    options={
        "py2exe":{
            "skip_archive": True,
            "includes": includes
        }
    }
)