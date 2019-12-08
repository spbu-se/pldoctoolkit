#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import difflib
import logging
import sys
import util
import archetype_extraction

def _diff_words(words1: 'Iterable[str]', words2: 'Iterable[str]', diffplus_style='diffplus'):
    # words1 = [util.escape(w.strip()) for w in words1.split(' ') if w.strip() != ""]
    # words2 = [w.strip() for w in words2.split(' ') if w.strip() != ""]
    words1 = util.tokenst(words1, punctsep=True)
    words2 = util.tokenst(words2, punctsep=True)

    # Timing: The basic Ratcliff-Obershelp algorithm is cubic time in the worst case and quadratic time in the expected case
    # https://docs.python.org/3/library/difflib.html#difflib.SequenceMatcher
    dr = difflib.Differ()
    diff = list(dr.compare(words1, words2))
    def dl2h(w):
        ww = w[2:]
        only_punctuation = bool(util._r_pre.fullmatch(ww))
        sep = '' if only_punctuation else ' '
        if   w.startswith('- '):  # disappeared
            return f'{sep}<span class="diffminus modeldiffminus">{ww}</span>'
        elif w.startswith('+ '):  # appeared
            return f'{sep}<span class="{diffplus_style} modeldiffplus">{ww}</span>'
        elif w.startswith('? '):  # not sure even what it means =)
            return sep
        elif w.startswith('  '):
            return f'{sep}{ww}'
        else:
            return ww
    rw2 = map(dl2h, diff)
    return ''.join(rw2)

def get_htmls(texts, reference_text=None, from_archetype=False, blue_delta=False):
    """
    :param reference_text: text to compare others to, first text otherwise
    :param texts: space-separated word strings
    :param from_archetype: calculate archetype and diff from it, not from first text
    :return: array of HTML fragmets for each of them with differences from first one
    """

    if from_archetype:
        if reference_text:
            return archetype_extraction.get_fuzzy_htmls(texts + [reference_text])[:-1]
        else:
            return archetype_extraction.get_fuzzy_htmls(texts)
    else:
        if reference_text is None:
            reference_text = util.tokenst(texts[0], punctsep=True)
        result = []
        for text in texts:
            result.append(_diff_words(
                reference_text, util.tokenst(text, punctsep=True),
                diffplus_style='nonarchetypical' if blue_delta else 'diffplus'
            ))
        return result


if __name__ == '__main__':

    tks1 = (
        ('thrown', '.', '@param', 'expecteds', 'byte', 'array', 'with', 'expected', 'values', '.', '@param', 'actuals', 'byte', 'array', 'with', 'actual', 'values'),
        ('thrown', 'with', 'the', 'given', 'message', '.', '@param', 'message', 'the', 'identifying', 'message', 'for', 'the', 'AssertionError', '(', 'null', 'okay', ')', '@param', 'expecteds', 'char', 'array', 'with', 'expected', 'values', '.', '@param', 'actuals', 'char', 'array', 'with', 'actual', 'values'),
        ('thrown', '.', 'If', 'expected', 'and', 'actual', 'are', 'null', ',', 'they', 'are', 'considered', 'equal', '.', '@param', 'expecteds', 'boolean', 'array', 'with', 'expected', 'values', '.', '@param', 'actuals', 'boolean', 'array', 'with', 'expected', 'values', '.'),
        ('thrown', '.', '@param', 'expecteds', 'char', 'array', 'with', 'expected', 'values', '.', '@param', 'actuals', 'char', 'array', 'with', 'actual', 'values'),
        ('thrown', '.', '@param', 'expecteds', 'short', 'array', 'with', 'expected', 'values', '.', '@param', 'actuals', 'short', 'array', 'with', 'actual', 'values'),
        ('thrown', '.', '@param', 'expecteds', 'int', 'array', 'with', 'expected', 'values', '.', '@param', 'actuals', 'int', 'array', 'with', 'actual', 'values'),
        ('thrown', '.', '@param', 'expecteds', 'long', 'array', 'with', 'expected', 'values', '.', '@param', 'actuals', 'long', 'array', 'with', 'actual', 'values'),
        ('thrown', '.', '@param', 'expecteds', 'double', 'array', 'with', 'expected', 'values', '.', '@param', 'actuals', 'double', 'array', 'with', 'actual', 'values', '@param', 'delta', 'the', 'maximum', 'delta', 'between', 'expecteds', '[', 'i', ']', 'and', 'actuals', '[', 'i', ']', 'for', 'which', 'both', 'numbers', 'are', 'still', 'considered', 'equal', '.'),
        ('thrown', '.', '@param', 'expecteds', 'float', 'array', 'with', 'expected', 'values', '.', '@param', 'actuals', 'float', 'array', 'with', 'actual', 'values', '@param', 'delta', 'the', 'maximum', 'delta', 'between', 'expecteds', '[', 'i', ']', 'and', 'actuals', '[', 'i', ']', 'for', 'which', 'both', 'numbers', 'are', 'still', 'considered', 'equal', '.'),
        ('thrown', '.', '@param', 'expected', 'expected', 'long', 'value', '.', '@param', 'actual', 'actual', 'long', 'value'),
        ('thrown', 'with', 'the', 'given', 'message', '.', '@param', 'message', 'the', 'identifying', 'message', 'for', 'the', 'AssertionError', '(', 'null', 'okay', ')', '@param', 'expected', 'long', 'expected', 'value', '.', '@param', 'actual', 'long', 'actual', 'value')
    )

    # hs = get_htmls([' '.join(t) for t in tks1], from_archetype=True)
    # for h in hs:
    #     print(h)
    # quit(0)

    if len(sys.argv) > 1:
        print(
            "Okay, assuming you gave me a file and I will tokenize it, and calculate how many tokens does it contain:"
        )
        with open(sys.argv[1], "r", encoding='utf-8') as ifl:
            txt = ifl.read()
            print(len(util.tokens(txt)))
    else:
        print("Testing")
        texts = [
            "One two three four six seven eight ten eleven thirteen",
            "One two three five six seven eight nine ten eleven twelve thirteen",
            "One two three five he seven me she nine ten eleven twelve thirteen"
        ]
        print(get_htmls(texts))