#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# requires https://pypi.python.org/pypi/PyContracts

import logging
import argparse
import os

import shutil
from lxml import etree

logging.basicConfig(filename='fuzzyclones2html.log', level=logging.INFO)
logger = logging

def initargs():
    global args
    argpar = argparse.ArgumentParser()
    argpar.add_argument("-fx", "--fuzzy-xml", help="XML with fuzzy clones")
    argpar.add_argument("-sx", "--source-xml", help="Source XML")
    argpar.add_argument("-od", "--output-directory", help="Report output directory")
    argpar.add_argument("-oui", "--only-ui",
                        help="Only generate data needed by standalone [Qt] UI", default="yes")
    args = argpar.parse_args()

def loadfuzzyinputs(logger):
    global args
    import clones

    # default required settings for fuzzy groups
    clones.write_reformatted_sources = False
    clones.checkmarkup = False
    clones.only_generate_for_ui = args.only_ui == "yes"

    inputfile = clones.InputFile(args.source_xml)

    fuzzyclonedata = etree.parse(args.fuzzy_xml) # type: ElementTree

    fgrps = []
    for fgrp in fuzzyclonedata.xpath('/fuzzygroups/fuzzygroup'):
        # here is group
        fclns = []
        fclntexts = []
        fclnwords = []
        for fcln in fgrp.xpath('./fuzzyclone'):
            fclns.append((0, int(fcln.attrib['offset']), int(fcln.attrib['length']) + int(fcln.attrib['offset'])))
            fclntexts.append(fcln.xpath('./sourcetext')[0].text)
            fclnwords.append(fcln.xpath('./sourcewords')[0].text)

        fgrps.append(clones.FuzzyCloneGroup(fgrp.attrib['id'], fclns, fclntexts, fclnwords))

    clones.initdata([inputfile], fgrps)

def report(logger):
    global args
    import clones

    fuzzygroups = [clones.VariativeElement([cg]) for cg in clones.clonegroups]
    cohtml = clones.VariativeElement.summaryhtml(fuzzygroups, clones.ReportMode.fuzzyclones)

    outdir = args.output_directory
    with open(os.path.join(outdir, "pyvarelements.html"), 'w', encoding='utf-8') as htmlfile:
        htmlfile.write(cohtml)

    shutil.copyfile(
        os.path.join(os.path.dirname(os.path.abspath(__file__)), 'js', 'interactivity.js'),
        os.path.join(outdir, "interactivity.js")
    )
    shutil.copyfile(
        os.path.join(os.path.dirname(os.path.abspath(__file__)), 'js', 'jquery-2.0.3.min.js'),
        os.path.join(outdir, "jquery-2.0.3.min.js")
    )

if __name__ == '__main__':
    initargs()
    loadfuzzyinputs(logger)
    report(logger)
