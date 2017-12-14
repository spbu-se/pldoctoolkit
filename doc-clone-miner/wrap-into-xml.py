#!/usr/bin/env python

import re
import sys

def esc(s):
  """Minimal XML escape"""
  return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")

def cesc(s):
  return s.replace("]]>", "]]]><![CDATA[]>")

re_nt = re.compile("\n[\t ]+", re.MULTILINE)
re_lf = re.compile("\n\n(\n)+", re.MULTILINE)

def strip(s):
  crlfr = s.replace("\r\n", "\n").replace("\r", "\n")
  crlfr = re_nt.sub("\n", crlfr)
  crlfr = re_lf.sub("\n\n", crlfr)
  return crlfr

def print_help():
  print("wrap-into-xml.py escape|cdata <input.txt >output.xml", file=sys.stderr)

plaintext = sys.stdin.buffer.read().decode('utf8')
wrapped = ''

if len(sys.argv) < 2:
  print_help()
elif sys.argv[1] == 'escape':
  escaped = esc(strip(plaintext))
  wrapped = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n<plainxml>" + escaped + "\n</plainxml>"
elif sys.argv[1] == 'cdata':
  escaped = cesc(strip(plaintext))
  wrapped = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n<plainxml><![CDATA[\n" + escaped + "\n]]></plainxml>"
else:
  print_help()

sys.stdout.buffer.write(wrapped.encode('utf8'))
