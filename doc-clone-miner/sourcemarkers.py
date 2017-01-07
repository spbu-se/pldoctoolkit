#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Range markers for DRL source code
"""

from abc import ABCMeta, abstractmethod
import uuid
import re
import xmllexer

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

    def apply(self, text):
        tt = self.marker_type()
        u = str(RangeMarker.id)
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

if __name__ == '__main__':  # tests
    xi = xmllexer.XmlInterval(xmllexer.IntervalType.comment, 100,
                              "<!-- 11111111-2222-3333-4444-555555555555 <=< ACCEPT -->",
                              None)
    print(open_marker_type(xi))
