#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Simple clone semantic filter
"""

import os
import re

meaningless_strings = None
meaningless_sets = None


def initialize():
    global meaningless_strings
    global meaningless_sets
    with open(os.path.join(os.path.dirname(os.path.realpath(__file__)), "semanticfilter.txt"), encoding='utf-8') as sf:
        meaningless_strings = [l.strip() for l in sf.readlines() if len(l.strip()) and not l.startswith("#")]
        meaningless_sets = [set(ms.split()) for ms in meaningless_strings]


if not meaningless_sets: initialize()

_splitre = re.compile(r"[\w']+", re.UNICODE)

def cleanwords(s, usere = False):
    # cleanup text
    sw = re.findall(_splitre, s) if usere else s.split()

    def cleanup(word):
        # spaces and register
        sw1 = word.strip().lower()

        # punctuation & digits
        sw2 = ''.join([c for c in sw1 if c.isalpha()])
        return sw2

    cleaned_words = [w for w in map(cleanup, sw) if w != ""]
    
    return cleaned_words


#condition for the patterns, I will think how I can improve it. Which patterns should we filter.
def does_pattern(fword, sword):
    if ((fword == 'MD') and (sword == 'VB') or (fword == 'MD') and (sword == 'DT')  or (fword == 'MD') and (sword == 'WDT')):
        return True

def does_have_semantic(s, use_nltk=False):
    cw = cleanwords(s)
    if use_nltk:
        import nltk
        tagged = nltk.pos_tag(cw)
        for i in range(0, len(tagged) - 1): #in tagged:
            if (does_pattern(tagged[i][1], tagged[i + 1][1])):
                print(tagged[i][0], tagged[i + 1][0])
                return False
    else:
        ws = set(cw)
        for ms in meaningless_sets:
            if ws.issubset(ms):
                return False
    return True
    
# just a test
if __name__ == '__main__':
    tests = [
        'is a', 'is this', 'is that', 'is the', 'I am', 'he is',
        'thi2s, \tis', """"""
    ]
    #print(meaningless_sets)
    #print('\n')
    
    for s in tests:
        print(s, does_have_semantic(s))
