#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Range markers for DRL source code
"""

from abc import ABCMeta, abstractmethod
import uuid
import re
import xmllexer
import util
import sys
import logging

class RangeMarker(metaclass=ABCMeta):
    id = None

    @staticmethod
    def newUUID():
        """
        New group UUID
        :return: Monotone growing UUID of type UUID1. MAC is fixed to make it transferable between computers
        """
        # invalid MAC: 33:34:45:79:34:54 https://superuser.com/q/1210887
        node = 0x_33_34_45_79_34_54
        # return group_uuid.uuid4() -- was used before
        return uuid.uuid1(node=node)

    def __init__(self, begin, end):
        self.begin = begin
        self.end = end
        if not RangeMarker.id:
            RangeMarker.id = RangeMarker.newUUID()

    @abstractmethod
    def marker_type(self):
        pass

    @staticmethod
    def forceNewUUID():
        oid = RangeMarker.id
        RangeMarker.id = RangeMarker.newUUID()
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

uuidlen = 36
markerlen = 56

def open_marker_id(str_or_xi):
    text = str_or_xi.srepr if isinstance(str_or_xi, xmllexer.XmlInterval) else str_or_xi
    id = text[5:5+36]
    return id

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

    return intervals

def source_text_to_html(src):
    raise NotImplementedError()  # TODO: try to implement it...

    intervals = find_marked_intervals(src)
    result = ""
    curoff = 0
    for b, e, t in intervals:
        result += util.escapen(src[curoff:b]) + \
            """<span style="color: grey;">""" + \
                  util.escapen(src[b:e]) + \
            """</span>"""
        curoff = e
    result += util.escapen(src[curoff:])
    return result

if __name__ == '__main__':  # tests
    xi = xmllexer.XmlInterval(xmllexer.IntervalType.comment, 100,
                              "<!-- 11111111-2222-3333-4444-555555555555 <=< ACCEPT -->",
                              None)
    print(open_marker_type(xi))
