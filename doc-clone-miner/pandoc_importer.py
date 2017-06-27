#!/usr/bin/env python3

"""
Module for seamless import different formats via Pandoc
"""

import pypandoc
import re
import util
import textwrap
import shutil

def wrap_file_into_smth(filename: str, escape: bool, format: str):
    re_nt = re.compile("\n[\t ]+", re.MULTILINE)
    re_lf = re.compile("\n\n(\n)+", re.MULTILINE)

    def strip(s):
        crlfr = s.replace("\r\n", "\n").replace("\r", "\n")
        crlfr = re_nt.sub("\n", crlfr)
        crlfr = re_lf.sub("\n\n", crlfr)
        return crlfr

    with open(filename, 'r', encoding='utf-8') as fh:
        plaintext = fh.read()

    if escape:
        escaped = util.escape(strip(plaintext))
    else:
        escaped = strip(plaintext)
    wrapped = format % (escaped,)

    with open(filename, 'w', encoding='utf-8') as fh:
        print(wrapped, file=fh)

def wrap_file_into_xml(filename: str):
    wrap_file_into_smth(
        filename,
        True,
        "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n<plainxml>%s\n</plainxml>"
    )

def wrap_file_into_drl(filename: str):
    wrap_file_into_smth(
        filename,
        False,
        textwrap.dedent(
            """
            <?xml version="1.0" encoding="UTF-8"?>
            <d:DocumentationCore xmlns:d="http://math.spbu.ru/drl" xmlns="http://docbook.org/ns/docbook"><d:InfElement id="root" name="test">
            %s
            </d:InfElement></d:DocumentationCore>
            """
        ),
        unwrap_docbook=True
    )

def wrap_docbook_into_drl(filename: str):
    with open(filename, 'r', encoding='utf-8') as fh:
        docbook = fh.read()

    docbook = docbook.replace(
        '<d:DocumentationCore xmlns:d="http://math.spbu.ru/drl" xmlns="http://docbook.org/ns/docbook">',
        '<d:DocumentationCore xmlns:d="http://math.spbu.ru/drl" xmlns="http://docbook.org/ns/docbook"><d:InfElement id="root" name="test">'
    ).replace(
        '</d:DocumentationCore>',
        '</d:InfElement></d:DocumentationCore>'
    )

    with open(filename, 'w', encoding='utf-8') as fh:
        print(docbook, file=fh)

def import_file(input_file: str, todrl: bool) -> str:
    if todrl:
        formats = {
            '.docx': ('docx', 'docbook', '.drl'),
            '.tex': ('latex', 'docbook', '.drl'),
            '.html': ('html', 'docbook', '.drl'),
            '.md': ('markdown', 'docbook', '.drl'),
            '.xml': ('docbook', 'docbook', '.drl')
        }
    else:
        formats = {
            '.docx': ('docx', 'plain', '.pxml'),
            '.tex': ('latex', 'plain', '.pxml'),
            '.html': ('html', 'plain', '.pxml'),
            '.md': ('markdown', 'plain', '.pxml'),
            '.xml': ('docbook', 'plain', '.pxml')
        }

    isuffix = None
    iformat = None
    oformat = None
    osuffix = None
    for s, (fi, fo, so) in formats.items():
        if input_file.endswith(s):
            isuffix = s
            iformat, oformat, osuffix = fi, fo, so
            break

    if isuffix == None:  # failed to recognize, do nothing to avoid making worse
        return input_file

    output_file = input_file[:-len(isuffix)] + osuffix

    if oformat != iformat:
        pypandoc.convert_file(
            input_file, oformat, format=iformat, outputfile=output_file,
            extra_args=['standalone']
        )
    else:
        shutil.copy(input_file, output_file)

    if osuffix == '.drl' and oformat == 'docbook':
        wrap_docbook_into_drl(output_file)
    else:
        wrap_file_into_xml(output_file)

    return output_file
