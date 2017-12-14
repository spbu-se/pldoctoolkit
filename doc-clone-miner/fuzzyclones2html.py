#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import logging
import argparse
import os
import sys

import shutil

logging.basicConfig(filename='fuzzyclones2html.log', level=logging.INFO)
logger = logging

def initargs():
    global args
    argpar = argparse.ArgumentParser()
    argpar.add_argument("-fx", "--fuzzy-xml", help="XML with fuzzy clones", type=str, default=None)
    argpar.add_argument("-ndgj", "--neardup-json", help="JSON with near dups", type=str, default=None)
    argpar.add_argument("-sx", "--source-xml", help="Source XML")
    argpar.add_argument("-od", "--output-directory", help="Report output directory")
    argpar.add_argument("-oui", "--only-ui",
                        help="Only generate data needed by standalone [Qt] UI", default="yes")
    args = argpar.parse_args()

def load_fuzzy_groups_xml(logger):
    from lxml import etree
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

def load_near_duplicates_json(logger):
    global args
    import clones
    import util
    import json

    """
    JSON example:
    {
        "groups": [
        {
          "group_id": 1,
          "duplicates": [
            {
              "start_index": 404,
              "end_index": 604,
              "text": "ZEXTERN int ZEXPORT deflateInit OF((z_streamp strm, int level)); Initializes the internal stream state for compression. The fields zalloc, zfree and opaque must be initialized before by the caller."
            },
            {
              "start_index": 8148,
              "end_index": 8358,
              "text": "ZEXTERN int ZEXPORT inflateInit OF((z_streamp strm)); Initializes the internal stream state for decompression. The fields next_in, avail_in, zalloc, zfree and opaque must be initialized before by the caller."
            }
          ]
        },
        {
          "group_id": 2,
          "duplicates": [
            {
              "start_index": 605,
              "end_index": 705,
              "text": "If zalloc and zfree are set to Z_NULL, deflateInit updates them to use default allocation functions."
            },
            {
              "start_index": 8579,
              "end_index": 8679,
              "text": "If zalloc and zfree are set to Z_NULL, inflateInit updates them to use default allocation functions."
            }
          ]
        },
        ...
      ]
    }
    """

    # default required settings for fuzzy groups
    clones.write_reformatted_sources = False
    clones.checkmarkup = False
    clones.only_generate_for_ui = args.only_ui == "yes"

    inputfile = clones.InputFile(args.source_xml)

    with open(args.neardup_json, encoding='utf-8') as ndj:
        fuzzyclonedata = json.load(ndj)

    fgrps = []
    for fgrp in fuzzyclonedata['groups']:
        # here is group
        group_id = fgrp['group_id']
        fclns = []
        fclntexts = []
        fclnwords = []
        for fcln in fgrp['duplicates']:
            si = fcln['start_index']
            ei = fcln['end_index']
            tx = fcln['text']
            fclns.append((0, int(si), int(ei)))
            fclntexts.append(tx)
            fclnwords.append(util.ctokens(tx))

        fgrps.append(clones.FuzzyCloneGroup(group_id, fclns, fclntexts, fclnwords))

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
    if args.fuzzy_xml:
        load_fuzzy_groups_xml(logger)
    elif args.neardup_json:
        load_near_duplicates_json(logger)
    else:
        print("Neither JSON nor XML supplied", file=sys.stderr)
    report(logger)
