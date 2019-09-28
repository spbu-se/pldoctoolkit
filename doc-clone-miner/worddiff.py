#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import difflib
import logging
import sys
import util
import archetype_extraction

def _diff_words(words1, words2, diffplus_style='diffplus'):
    # words1 = [util.escape(w.strip()) for w in words1.split(' ') if w.strip() != ""]
    # words2 = [w.strip() for w in words2.split(' ') if w.strip() != ""]
    words1 = util.tokenst(words1)
    words2 = util.tokenst(words2)

    # Timing: The basic Ratcliff-Obershelp algorithm is cubic time in the worst case and quadratic time in the expected case
    # https://docs.python.org/3/library/difflib.html#difflib.SequenceMatcher
    dr = difflib.Differ()
    diff = list(dr.compare(words1, words2))
    def dl2h(w):
        ww = w[2:]
        if   w.startswith('- '):  # disappeared
            return f'<span class="diffminus modeldiffminus">{ww}</span>'
        elif w.startswith('+ '):  # appeared
            return f'<span class="{diffplus_style} modeldiffplus">{ww}</span>'
        elif w.startswith('? '):  # not sure even what it means =)
            return ""
        elif w.startswith('  '):
            return ww
        else:
            return ww
    rw2 = map(dl2h, diff)
    return ' '.join(rw2)

def get_htmls(texts, reference_text=None, from_archetype=False, blue_delta=False):
    """
    :param reference_text: text to compare others to, first text otherwise
    :param texts: space-separated word strings
    :param from_archetype: calculate archetype and diff from it, not from first text
    :return: array of HTML fragmets for each of them with differences from first one
    """

    if from_archetype:
        texts_tokens = tuple(tuple(util.tokenst(s)) for s in texts)
        archetype_tokens = archetype_extraction.best_n_tuples_lcs(texts_tokens)
        logging.info(
            f"\tTotal instance tokens:"
            f"\t{sum(len(tt) for tt in texts_tokens)}"
            f"\tArchetype length:"
            f"\t{len(archetype_tokens)}"
        )
        reference_text = ' '.join(archetype_tokens)
    elif reference_text is None:
        reference_text = texts[0]


    result = []
    for text in texts:
        result.append(_diff_words(
            reference_text, text,
            diffplus_style='nonarchetypical' if from_archetype or blue_delta else 'diffplus'
        ))
    return result

if __name__ == '__main__':
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