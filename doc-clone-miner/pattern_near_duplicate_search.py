#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import argparse
import difflib
import math
import re
import bisect
import itertools

# Различные оптимизации
optimize_fit_cutoff = False  # Не доказано, и скорее всего вообще неправда, а ведь помогает =(
optimize_distant_jump = True
optimize_stage1_word_offsets = True
optimize_stage2_by_words = True
optimize_stage2_length_borders = False


# Немного бинарного поиска
def find_closest_le_index(a: 'list', b: 'int') -> 'int':
    return bisect.bisect_left(a, b + 1) - 1


def find_closest_ge_index(a: 'list', b: 'int') -> 'int':
    return bisect.bisect_right(a, b - 1)


def find_closest_le(a: 'list', b: 'int') -> 'int':
    return a[find_closest_le_index(a, b)]


def find_closest_ge(a: 'list', b: 'int') -> 'int':
    return a[find_closest_ge_index(a, b)]


def max_d_di_ws(pattern: str, similarity: float) -> 'tuple[int,int]':
    max_d_di = int(math.floor(len(pattern) * (1 - similarity ** 2) * (1 + 1 / similarity)))  # WARNING!!! Округление вниз!!! Не доказано!!!
    win_size = int(math.floor(len(pattern) / similarity))  # WARNING!!! Округление вниз!!! Не доказано!!!
    return max_d_di, win_size

_wre = re.compile(r'\w+')


_di_distance_cache: 'dict[tuple[str,str],int]' = dict()
def _sm_distance(s1: str, s2: str) -> int:
    """A bit faster than Lowenstein =)"""
    blocks = difflib.SequenceMatcher(a = s1, b = s2).get_matching_blocks()
    common = sum(size for (a, b, size) in blocks)
    dist = len(s1) + len(s2) - 2 * common
    return dist


def di_distance(s1: str, s2: str, cache=False) -> int:
    if cache:
        if s1 > s2:
            s1, s2 = s2, s1
        if (s1, s2) in _di_distance_cache:
            return _di_distance_cache[s1, s2]
        else:
            v = _sm_distance(s1, s2)
            _di_distance_cache[s1, s2] = v
            return v
    else:
        return _sm_distance(s1, s2)

def di_similarity(s1: str, s2: str) -> float:
    blocks = difflib.SequenceMatcher(a = s1, b = s2).get_matching_blocks()
    common = sum(size for (a, b, size) in blocks)
    return 2.0 * common / (len(s1) + len(s2))

def get_fuzzy_match_areas(document: 'str', pattern: 'str', similarity: 'float') -> 'list[tuple[int,int]]':
    max_d_di, win_size = max_d_di_ws(pattern, similarity)

    offsets = []

    print("Searching...")
    cnt = 0

    word_offsets = [wm.start(0) for wm in  _wre.finditer(document)]
    word_offsets = [wo for wo in word_offsets if wo < len(document) - len(pattern)]

    next_min_offset = 0
    for offset in word_offsets if optimize_stage1_word_offsets else range(len(document) - len(pattern)):
        if offset >= next_min_offset or not optimize_distant_jump:
            piece = document[offset:offset+win_size]
            # Если идти по словам, то тут как раз место для проверки по simhash. Но это если по словам.
            # По символам ожидаемо почти все срабатывания у SimHash ложные.
            ddi = di_distance(pattern, piece)
            if ddi <= max_d_di:
                offsets.append((offset, offset + win_size))
            else:
                next_min_offset = offset + (ddi - max_d_di) // 2

        cnt += 1
        if cnt % 1000 == 0:
            print(cnt, '/', len(word_offsets) if optimize_stage1_word_offsets else len(document) - len(pattern))
    return offsets

_word_begins: 'list[int]' = None
_word_ends:   'list[int]' = None
_nsre = re.compile(r'\S+')
# _s_ns_re = re.compile(r'\s\S')
# _ns_s_re = re.compile(r'\S\s')
def get_fit_word_borders(document: 'str') -> 'list[tuple[int,int]]':
    global _word_begins, _word_ends
    if not _word_begins:
        _fit_word_borders = [wm.span() for wm in  _nsre.finditer(document)]
        [_word_begins, _word_ends] = zip(*_fit_word_borders)
    return _word_begins, _word_ends

def fit_candidate(document: 'str', pattern: 'str', similarity: 'float', candidate: 'tuple[int,int]') -> 'tuple[int,int]':
    wbs, wes = get_fit_word_borders(document)

    def sort_list_around_value(values: 'list[int]', center):
        """
        :param values: input values list
        :param center: closer to what do we want to start
        :return: sorted list

        Example:
        >>> sort_list_around_value([1,2,3,4,5,6,7,8,9,10], 5)
        [5, 6, 4, 7, 3, 8, 2, 9, 1, 10]
        """
        values.sort(key=lambda v: abs(v - 0.125 - center))


    p0, p1 = candidate
    if optimize_stage2_by_words:
        p0 = find_closest_le(wbs, p0)
        p1 = find_closest_ge(wes, p1)
        candidate = p0, p1

    wl = p1 - p0

    min_l = int(math.ceil(len(pattern) * similarity))   #!!! Округление вверх!!! Не доказано!!!

    min_d_di = di_distance(pattern, document[p0:p1])
    min_peak = candidate
    curr_d_di = di_distance(pattern, document[p0: p1], cache=True)

    lengths = list(range(wl, min_l-1, -1))
    if optimize_stage2_length_borders:
        sort_list_around_value(lengths, len(pattern))

    skept = 0
    for l in lengths:
        if optimize_stage2_length_borders and (l < len(pattern) - curr_d_di or l > len(pattern) + curr_d_di):
            skept += 1
            continue

        time_to_give_up = True
        o = 0
        while o <= wl - l:
            # if optimize_stage2_by_words:
            #    real_p0_o = find_closest_le(wbs, p0 + o)
            #    real_p0_o_l = find_closest_ge(wes, p0 + o + l) - real_p0_o
            # else:
            real_p0_o, real_p0_o_l = p0 + o, p0 + o + l

            # Когда мы усушку делаем по словам, то для не-границ-слов просто пропускаем и ничего не считаем
            # А в противном случае зато считаем
            if not optimize_stage2_by_words or \
                    (document[real_p0_o - 1].isspace() and not document[real_p0_o].isspace() and \
                    not document[real_p0_o_l].isspace() and document[real_p0_o_l + 1].isspace()):
                curr_d_di = di_distance(pattern, document[real_p0_o: real_p0_o_l], cache=True)

            if curr_d_di < min_d_di:
                min_d_di = curr_d_di
                min_peak = real_p0_o, real_p0_o_l
                time_to_give_up = False
                o += 1
            elif curr_d_di <= min_d_di + 1 or not optimize_distant_jump:
                o += 1
            else:
                o += (curr_d_di - min_d_di) // 2

        if time_to_give_up and optimize_fit_cutoff:
            # !!! Если эта длина окна не дала эффекта, значит дальнейшее уменьшение бессмысленно.
            # Не доказано, и очень возможно вообще неправда!!!
            break

    return find_closest_le(wbs, min_peak[0]), find_closest_ge(wes, min_peak[1])


def fit_candidates(document: 'str', pattern: 'str', similarity: 'float', candidates: 'list[tuple[int,int]]') -> 'list[tuple[int,int]]':
    print("Fitting...")
    cnt = 0
    fit = []

    for c in candidates:
        fit.append(fit_candidate(document, pattern, similarity, c))
        cnt += 1
        if cnt % 10 == 0:
            print(cnt, '/', len(candidates))

    return fit


def remove_redundant_candidates(candidates: 'list[tuple[int,int]]') -> 'list[tuple[int,int]]':
    print("Removing redundant...")
    speaks = set(candidates)  # at least filter out similar ones
    toremove = set()
    for p1b, p1e in speaks:
        for p2b, p2e in speaks:
            if (p1b != p2b or p1e != p2e) and p1b <= p2b and p2e <= p1e:
                toremove.add((p2b, p2e))

    speaks.difference_update(toremove)
    lspeaks = list(speaks)
    lspeaks.sort(key=lambda sp: sp[0])
    return lspeaks


def join_overlapping_candidates(document: 'str', candidates: 'list[tuple[int,int]]', similarity: 'float', pattern: 'str') -> 'list[tuple[int,int]]':
    print("Joining overlapping...", end='')
    maxlength =  2 * len(pattern) * similarity # Округление вниз
    l0 = len(candidates)
    def intersects(t1: 'tuple[int, int]', t2: 'tuple[int, int]') -> 'bool':
        b1, e1 = t1
        b2, e2 = t2
        return b1 <= b2 <= e1 or b1 <= e2 <= e1

    cluster_state = {}
    for c in candidates:
        cluster_state[c] = [c]

    something_changed = True
    while something_changed:
        something_changed = False
        csk = list(cluster_state.keys())
        for zone1, zone2 in itertools.product(csk, csk):
            if zone1 != zone2 and intersects(zone1, zone2):
                candidates = cluster_state[zone1] + cluster_state[zone2]
                b = min(zone1[0], zone2[0])
                e = max(zone1[1], zone2[1])
                del cluster_state[zone1]
                del cluster_state[zone2]
                cluster_state[(b, e)] = candidates
                something_changed = True
                break  # for -> while

    final_candidates = []

    if False:  # we do not do so now
        # then cancel too big zones
        csk = list(cluster_state.keys())
        for k in csk:
            b, e = k
            if e - b > maxlength:
                final_candidates += cluster_state[k]
                del cluster_state[k]

        # and add joint candidates
        for k in cluster_state.keys():
            final_candidates.append(k)


    if True:  # 3rd phase from PhD -- only take one of overlapping
        csk = list(cluster_state.keys())
        for k in csk:
            candidates = cluster_state[k]
            best_candidate = candidates[0]
            best_distance = di_distance(document[best_candidate[0]:best_candidate[1]], pattern, cache=True)
            for cb, ce in candidates:
                if di_distance(document[cb:ce], pattern, cache=True) < best_distance:
                    best_candidate = cb, ce
            final_candidates.append(best_candidate)

    print(" %d -> %d." %(l0, len(final_candidates)))
    return final_candidates


def search(document: str, pattern: str, similarity: float) -> 'list[tuple[int,int]]':
    w1 = get_fuzzy_match_areas(document, pattern, similarity)
    w2 = fit_candidates(document, pattern, similarity, w1)
    w3 = remove_redundant_candidates(w2)
    w3j = join_overlapping_candidates(document, w3, similarity, pattern)
    return w3j


def main():
    global optimize_fit_cutoff, optimize_distant_jump, optimize_stage1_word_offsets, optimize_stage2_by_words, optimize_stage2_length_borders
    optimize_fit_cutoff = False
    optimize_stage1_word_offsets = False

    optimize_distant_jump = True
    optimize_stage2_by_words = True
    optimize_stage2_length_borders = False  # Only gives only a bit
    bassett = 0.15
    apr = argparse.ArgumentParser()
    apr.add_argument('--document', type=str)
    apr.add_argument('--pattern', type=str)
    apr.add_argument('--similarity', type=float, default=1/(1 + bassett))
    args = apr.parse_args()

    with open(args.document, encoding='utf-8') as docfile:
        doc_text = docfile.read()

    # candidatess = get_fuzzy_match_areas(doc_text, args.pattern, args.similarity)
    # fit = fit_candidates(doc_text, args.pattern, args.similarity, candidatess)
    # nipeaks = remove_redundant_candidates(fit)
    nipeaks = search(doc_text, args.pattern, args.similarity)

    for peak in nipeaks:
        print(repr(peak), doc_text[peak[0]:peak[1]])


if __name__ == '__main__':
    main()
