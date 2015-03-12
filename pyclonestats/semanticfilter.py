#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Simple clone semantic filter
"""

import os

meaningless_strings = None
meaningless_sets = None

def initialize():
    global meaningless_strings
    global meaningless_sets
    with open(os.path.join(os.path.dirname(os.path.realpath(__file__)), "semanticfilter.txt"), encoding='utf-8') as sf:
        meaningless_strings = [l.strip() for l in sf.readlines() if len(l.strip()) and not l.startswith("#")]
        meaningless_sets = [set(ms.split()) for ms in meaningless_strings]

if not meaningless_sets: initialize()

def wordset(s):
    # cleanup text
    sw = s.split()

    def cleanup(word):
        # spaces and register
        sw1 = word.strip().lower()

        # punctuation & digits
        sw2 = ''.join([c for c in sw1 if c.isalpha()])
        return sw2

    cleaned_words = [w for w in map(cleanup, sw) if w != ""]

    return set(cleaned_words)

def does_have_semantic(s):
    # print("Checking semantic of " + repr(s))
    ws = wordset(s)
    for ms in meaningless_sets:
        if ws.issubset(ms):
            # print("Dummy")
            return False
    # print("Meaningful")
    return True

# just a test
if __name__ == '__main__':
    tests = [
    'is a', 'is this', 'is that', 'is the', 'I am', 'he is',
    'thi2s, \tis', """"""
    ]
    for s in tests:
        print(s, does_have_semantic(s))
