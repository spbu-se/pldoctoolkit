#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Range markers for DRL source code
"""

from abc import ABCMeta, abstractmethod
import uuid


class RangeMarker(metaclass=ABCMeta):
    id = None

    def __init__(self, begin, end):
        self.begin = begin
        self.end = end
        # self.id = uuid.uuid4()
        if not RangeMarker.id:
            RangeMarker.id = uuid.uuid4()

    @abstractmethod
    def markertype(self):
        pass

    def apply(self, text):
        tt = self.markertype()
        u = str(RangeMarker.id)
        return """%s<!-- %s <=< %s -->%s<!-- %s >=> %s -->%s""" % (
            text[:self.begin], u, tt,
            text[self.begin:self.end],
            tt, u, text[self.end:])

class AcceptRangeMarker(RangeMarker):
    def __init__(self, begin, end):
        super(AcceptRangeMarker, self).__init__(begin, end)

    def markertype(self):
        return "ACCEPT"

class IgnoreRangeMarker(RangeMarker):
    def __init__(self, begin, end):
        super(IgnoreRangeMarker, self).__init__(begin, end)

    def markertype(self):
        return "IGNORE"

def insertMarker(text, marker):
    return text
