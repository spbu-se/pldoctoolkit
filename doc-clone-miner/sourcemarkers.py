#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Range markers for DRL source code
"""

from abc import ABCMeta, abstractmethod
import uuid
import re
import xmllexer
import verbhtml
import sys
import logging

class RangeMarker(metaclass=ABCMeta):
    id = None

    def __init__(self, begin, end):
        self.begin = begin
        self.end = end
        if not RangeMarker.id:
            RangeMarker.id = uuid.uuid4()

    @abstractmethod
    def marker_type(self):
        pass

    def forceNewUUID():
        oid = RangeMarker.id
        RangeMarker.id = uuid.uuid4()
        print("UUID: %s -> %s" % (str(oid), str(RangeMarker.id)), file=sys.stderr)

    def apply(self, text, qtCursor=None):
        tt = self.marker_type()
        u = str(RangeMarker.id)
        if qtCursor:
            col = "cyan" if self.marker_type() == "ACCEPT" else "yellow"
            qtCursor.insertHtml(
                """<span style="background-color: %s">&lt;!-- %s &lt;=&lt; %s --&gt;<b>%s</b>&lt;!-- %s &gt;=&gt; %s --&gt;</span>""" % (
                col, u, tt,
                text[self.begin:self.end],
                tt, u))
            return None
        else:
            return """%s<!-- %s <=< %s -->%s<!-- %s >=> %s -->%s""" % (
                text[:self.begin], u, tt,
                text[self.begin:self.end],
                tt, u, text[self.end:])

class AcceptRangeMarker(RangeMarker):
    def __init__(self, begin, end):
        super(AcceptRangeMarker, self).__init__(begin, end)

    def marker_type(self):
        return "ACCEPT"

class IgnoreRangeMarker(RangeMarker):
    def __init__(self, begin, end):
        super(IgnoreRangeMarker, self).__init__(begin, end)

    def marker_type(self):
        return "IGNORE"

# http://stackoverflow.com/a/12843265/539470
_uuid_re_text = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}"
_open_marker_re = re.compile("<!-- %s <=< (ACCEPT|IGNORE) -->" % (_uuid_re_text,))
_close_marker_re = re.compile("<!-- (ACCEPT|IGNORE) >=> %s -->" % (_uuid_re_text,))


def open_marker_type(str_or_xi):
    text = str_or_xi.srepr if isinstance(str_or_xi, xmllexer.XmlInterval) else str_or_xi
    if _open_marker_re.match(text):
        for m in _open_marker_re.finditer(text):
            return m.groups(1)[0]  # ACCEPT|IGNORE
    return None


def close_marker_type(str_or_xi):
    text = str_or_xi.srepr if isinstance(str_or_xi, xmllexer.XmlInterval) else str_or_xi
    if _close_marker_re.match(text):
        for m in _close_marker_re.finditer(text):
            return m.groups(1)[0]  # ACCEPT|IGNORE
    return None

def find_marked_intervals(src):
    obes = [m.span() for m in  _open_marker_re.finditer(src)]
    cbes = [m.span() for m in  _close_marker_re.finditer(src)]

    if len(obes) != len(cbes):
        print(
            "Source contains", len(obes), "opening markers and", len(cbes), "closing",
            file=sys.stderr)
        return []

    intervals = []
    for (ob, oe), (cb, ce) in zip(obes, cbes):
        mt = open_marker_type(src[ob:oe])
        if mt != close_marker_type(src[cb:ce]):
            print("UNBALANCED MARKERS: <=<=<", src[ob:ce], ">=>=>", file=sys.stderr)
            continue
        intervals.append((ob, ce, mt))

    # statistics output
    markerlen = 56
    src_wo_mcoms_len = len(src) - len(intervals) * 2 * markerlen
    marked_len = sum([e - b for b, e, t in intervals if t == 'ACCEPT'])
    msg = "Source length: %d, marked for reuse: %d, reuse ratio: %.1f%%" % (src_wo_mcoms_len, marked_len, 100 * marked_len / src_wo_mcoms_len)
    logging.getLogger("fuzzyheat.sourcemarkers").info(msg)

    return intervals

def source_text_to_html(src):
    raise NotImplementedError()  # TODO: try to implement it...

    intervals = find_marked_intervals(src)
    result = ""
    curoff = 0
    for b, e, t in intervals:
        result += verbhtml.escapen(src[curoff:b]) + \
            """<span style="color: grey;">""" + \
            verbhtml.escapen(src[b:e]) + \
            """</span>"""
        curoff = e
    result += verbhtml.escapen(src[curoff:])
    return result

if __name__ == '__main__':  # tests
    xi = xmllexer.XmlInterval(xmllexer.IntervalType.comment, 100,
                              "<!-- 11111111-2222-3333-4444-555555555555 <=< ACCEPT -->",
                              None)
    print(open_marker_type(xi))
