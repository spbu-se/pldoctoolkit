#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# requires https://pypi.python.org/pypi/PyContracts

import logging
import argparse
from lxml import etree

logging.basicConfig(filename='fuzzyclones2html.log', level=logging.INFO)
logger = logging

def initargs():
    global args
    argpar = argparse.ArgumentParser()
    argpar.add_argument("-fx", "--fuzzy-xml", help="XML with fuzzy clones")
    argpar.add_argument("-sx", "--source-xml", help="Source XML")
    args = argpar.parse_args()

def loadfuzzyinputs(logger):
    global args
    import clones

    # default settings for fuzzy groups
    clones.write_reformatted_sources = True
    clones.checkmarkup = False

    inputfile = clones.InputFile(args.source_xml)

    fuzzyclonedata = etree.parse(args.fuzzy_xml) # type: ElementTree

    fgrps = []
    for fgrp in fuzzyclonedata.xpath('/fuzzygroups/fuzzygroup'):
        # here is group
        fclns = []
        for fcln in fgrp.xpath('./fuzzyclone'):
            fclns.append((0, fcln.attrib['offset'], fcln.attrib['length'] + fcln.attrib['offset']))

        fgrps.append(clones.FuzzyCloneGroup(fgrp.attrib['id'], fclns))

    clones.initdata([inputfile], [fgrps])

if __name__ == '__main__':
    initargs()
    loadfuzzyinputs(logger)
