#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import difflib
import verbhtml

def _diff_words(words1, words2):
    words1 = [verbhtml.escape(w.strip()) for w in words1.split(' ') if w.strip() != ""]
    words2 = [w.strip() for w in words2.split(' ') if w.strip() != ""]
    # Timing: The basic Ratcliff-Obershelp algorithm is cubic time in the worst case and quadratic time in the expected case
    # https://docs.python.org/3/library/difflib.html#difflib.SequenceMatcher
    dr = difflib.Differ()
    diff = list(dr.compare(words1, words2))
    def dl2h(w):
        ww = w[2:]
        if   w.startswith('- '):
            return '<span style="background-color: LightSalmon;">%s</span>' % ww
        elif w.startswith('+ '):
            return '<span style="background-color: GreenYellow;">%s</span>' % ww
        elif w.startswith('? '):
            return '<span style="background-color: blue;">%s</span>' % ww
        elif w.startswith('  '):
            return ww
        else:
            return ww
    rw2 = map(dl2h, diff)
    return ' '.join(rw2)

def get_htmls(texts):
    """
    :param texts: space-separated word strings
    :return: array of HTML fragmets for each of them with differences from first one
    """
    result = [verbhtml.escape(texts[0])]
    for text in texts[1:]:
        result.append(_diff_words(texts[0], text))
    return result

if __name__ == '__main__':
    print("Testing")
    texts = [
        "One two three four six seven eight ten eleven thirteen",
        "One two three five six seven eight nine ten eleven twelve thirteen",
        "One two three five he seven me she nine ten eleven twelve thirteen"
    ]
    print(get_htmls(texts))