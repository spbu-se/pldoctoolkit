#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import argparse
import difflib
import math
import re
import bisect
import itertools
import time
import logging
from multiprocessing import Pool
import csv
from operator import itemgetter

# Различные оптимизации
import sys

optimize_fit_cutoff = False  # Не доказано, и скорее всего вообще неправда, а ведь помогает =(
optimize_distant_jump = True
optimize_stage1_by_words = True
optimize_stage2_by_words = True
optimize_stage2_similar_strings = True
optimize_stage2_by_left_border = False

optimize_stage2_length_borders = False
optimize_smart_removal = False

optimize_stage3_union = True
_s_logger = None


def glog():
    global _s_logger
    if not _s_logger:
        _s_logger = logging.getLogger("pattern_search.provable")
        ch = logging.StreamHandler()
        fh = logging.FileHandler("pattern_search.provable.log")
        ch.setLevel(logging.INFO)
        fh.setLevel(logging.INFO)
        _s_logger.addHandler(ch)
        _s_logger.addHandler(fh)
    return _s_logger


# Немного бинарного поиска
def find_closest_le_index(a: 'list', b: 'int') -> 'int':
    return bisect.bisect_left(a, b + 1) - 1


def find_closest_ge_index(a: 'list', b: 'int') -> 'int':
    return bisect.bisect_right(a, b - 1)


def find_closest_le(a: 'list', b: 'int') -> 'int':
    # try:
    return a[find_closest_le_index(a, b)]
    # except Exception:
    #     print(a, b)
    #     return a[0]


def find_closest_ge(a: 'list', b: 'int') -> 'int':
    # try:
    return a[find_closest_ge_index(a, b)]
    # except Exception:
    #    print(a, b)
    #    return a[-1]


def max_d_di_ws(pattern: str, similarity: float) -> 'tuple[int,int]':
    max_d_di = int(math.ceil(
        len(pattern) * (1 - similarity ** 2) * (1 + 1 / similarity)))  # WARNING!!! Округление вверх!!! Не доказано!!!
    win_size = int(math.ceil(len(pattern) / similarity))  # WARNING!!! Округление вверх!!! Не доказано!!!
    return max_d_di, win_size


_wre = re.compile(r'\w+')

_di_distance_cache: 'dict[tuple[str,str],int]' = dict()


def _sm_distance(s1: str, s2: str) -> int:
    """A bit faster than Lowenstein =)"""
    blocks = difflib.SequenceMatcher(a=s1, b=s2).get_matching_blocks()
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


_word_begins: 'list[int]' = None
_word_ends: 'list[int]' = None
_nsre = re.compile(r"[\S]+", re.UNICODE | re.MULTILINE)


# _s_ns_re = re.compile(r'\s\S')
# _ns_s_re = re.compile(r'\S\s')
def get_fit_word_borders(document: 'str') -> 'list[tuple[int,int]]':
    global _word_begins, _word_ends
    if not _word_begins or not _word_ends:
        _fit_word_borders = [wm.span() for wm in _nsre.finditer(document)]
        [_word_begins, _word_ends] = zip(*_fit_word_borders)
        _word_begins = list(_word_begins)
        _word_ends = list(_word_ends)  # [we - 1 for we in _word_ends] ??

        if _word_begins[0] != 0:
            _word_begins = [0] + _word_begins
            _word_ends = [1] + _word_ends
        if _word_ends[-1] != len(document):
            _word_begins = _word_begins + [len(document) - 1]
            _word_ends = _word_ends + [len(document)]

    return _word_begins, _word_ends


def widen_to_whole_words(document: 'str', area: 'tuple[int,int]') -> 'tuple[int, int]':
    wbs, wes = get_fit_word_borders(document)
    p0, p1 = area
    return (find_closest_le(wbs, p0), find_closest_ge(wes, p1))


def di_similarity(s1: str, s2: str) -> float:
    blocks = difflib.SequenceMatcher(a=s1, b=s2).get_matching_blocks()
    common = sum(size for (a, b, size) in blocks)
    return 2.0 * common / (len(s1) + len(s2))


def get_fuzzy_match_areas(document: 'str', pattern: 'str', similarity: 'float') -> 'list[tuple[int,int]]':
    max_d_di, win_size = max_d_di_ws(pattern, similarity)
    word_offsets, wes = get_fit_word_borders(document)

    offsets = []

    print("Searching...")
    cnt = 0

    word_offsets = [wo for wo in word_offsets if wo < len(document) - win_size]

    next_min_offset = 0
    for offset in word_offsets if optimize_stage1_by_words else range(len(document) - win_size):
        if offset >= next_min_offset or not optimize_distant_jump:
            piece = document[offset:offset + win_size]
            # Если идти по словам, то тут как раз место для проверки по simhash. Но это если по словам.
            # По символам ожидаемо почти все срабатывания у SimHash ложные.
            ddi = di_distance(pattern, piece)
            if ddi <= max_d_di:
                offsets.append((offset, offset + win_size))
            else:
                next_min_offset = offset + (ddi - max_d_di) // 2
                # next_min_offset = find_closest_le(word_offsets, next_min_offset)

        cnt += 1
        if cnt % 1000 == 0:
            print(cnt, '/', len(word_offsets) if optimize_stage1_by_words else len(document) - len(pattern))
    return offsets


def _nsre_c_alnum(s: 'str') -> 'Bool':
    return not s.isspace()
    # return s.isalnum() or s == '_' or s == "'"


_stage2_left_border: int = 0


def fit_candidate(document: 'str', pattern: 'str', similarity: 'float',
                  candidate: 'tuple[int,int]') -> 'tuple[int,int]|NoneType':
    global _stage2_left_border

    p0, p1 = candidate
    wl = p1 - p0

    wbs, wes = get_fit_word_borders(document)
    # local sets are faster to check with
    wbs = wbs[find_closest_le_index(wbs, p0):find_closest_ge_index(wbs, p1)]
    wes = wes[find_closest_le_index(wes, p0):find_closest_ge_index(wes, p1)]
    # make them sets
    swbs = set(wbs)
    swes = set(wes)

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

    min_l = int(math.floor(len(pattern) * similarity))  # !!! Округление вниз!!! Не доказано!!!

    min_d_di = di_distance(pattern, document[p0:p1], cache=True)
    min_peak = candidate
    curr_d_di = min_d_di

    # s = document[p0:p1]
    # print("--->", min_d_di, candidate, s)

    lengths = list(range(wl, min_l - 1, -1))
    if optimize_stage2_length_borders:
        sort_list_around_value(lengths, len(pattern))

    skept = 0

    for l in lengths:
        if optimize_stage2_length_borders and (l < len(pattern) - curr_d_di or l > len(pattern) + curr_d_di):
            skept += 1
            continue

        time_to_give_up = True

        if not (optimize_stage2_by_left_border and not optimize_stage2_length_borders):
            o = 0
        else:
            o = max(0, _stage2_left_border - p0 + wl - l + 1)

        min_l_d_di = sys.maxsize
        min_l_peak = None
        while o <= wl - l:
            # if optimize_stage2_by_words:
            #    real_p0_o = find_closest_le(wbs, p0 + o)
            #    real_p0_o_l = find_closest_ge(wes, p0 + o + l) - real_p0_o
            # else:
            real_p0_o, real_p0_o_l = p0 + o, p0 + o + l

            if optimize_stage2_by_words and (not real_p0_o in swbs or not real_p0_o_l in swes):
                o += 1
                continue
            else:
                curr_d_di = di_distance(pattern, document[real_p0_o: real_p0_o_l], cache=True)

            if curr_d_di < min_l_d_di:
                min_l_d_di = curr_d_di
                min_l_peak = real_p0_o, real_p0_o_l
                time_to_give_up = False
                o += 1
            elif curr_d_di <= min_l_d_di + 1 or not optimize_distant_jump:
                o += 1
            else:
                o += (curr_d_di - min_l_d_di) // 2

        if time_to_give_up and optimize_fit_cutoff:
            # !!! Если эта длина окна не дала эффекта, значит дальнейшее уменьшение бессмысленно.
            # Не доказано, и очень возможно вообще неправда!!!
            break

        if min_l_d_di < min_d_di:
            min_d_di = min_l_d_di
            min_peak = min_l_peak

    # s = document[min_peak[0]:min_peak[1]]
    # print("<---", min_d_di, min_peak, s)

    _stage2_left_border = p0

    return min_peak
    # return find_closest_le(wbs, min_peak[0]), find_closest_ge(wes, min_peak[1])


def fit_candidates(document: 'str', pattern: 'str', similarity: 'float',
                   candidates: 'list[tuple[int,int]]') -> 'list[tuple[int,int]]':
    global _stage2_left_border
    _stage2_left_border = 0

    _fit_results: 'dict[str,tuple[int,int]]' = {}

    print("Fitting...")
    fit = []

    if optimize_stage2_by_words:
        candidates = [widen_to_whole_words(document, candidate) for candidate in candidates]

    pool = Pool(processes=4)
    print("_________________")
    chunks = get_candidates_chunks(candidates, 4)
    jobs = [pool.apply_async(process_chunk, (_fit_results, chunk, document, pattern, similarity)) for chunk in chunks]
    for job in jobs:
        for c in job.get():
            fit.append(c)

    pool.close()

    return fit


def process_chunk(_fit_results: 'dict[str,tuple[int,int]]', candidates: 'list[tuple[int,int]]',
                  document: 'str', pattern: 'str', similarity: 'float') -> 'list[tuple[int,int]]':
    fit = []
    for c in candidates:
        if optimize_stage2_similar_strings:
            if document[c[0]:c[1]] in _fit_results:
                o, l = _fit_results[document[c[0]:c[1]]]
                better = c[0] + o, c[0] + o + l
            else:
                better = fit_candidate(document, pattern, similarity, c)
                _fit_results[document[c[0]:c[1]]] = better[0] - c[0], better[1] - better[0]
        else:
            better = fit_candidate(document, pattern, similarity, c)
        if better:
            fit.append(better)
    return fit


def get_candidates_chunks(candidates: 'list[tuple[int,int]]', n: 'int') -> 'list[list[tuple[int,int]]]':
    cand_length = len(candidates)
    chunk_size = int(cand_length / n)
    start, end = 0, chunk_size
    chunk_list = []
    for i in range(n - 1):
        chunk_list.append(candidates[start:end])
        start, end = end, end + chunk_size

    chunk_list.append(candidates[start:cand_length])
    return chunk_list


def transit_intersections(intervals: 'list[tuple[int,int]]') -> 'list[list[tuple[int,int]]]':
    # print("Transiting intersections...")

    intervals = list(set(intervals))
    intervals.sort()

    # 1. linear overlapping clustering
    clusters: 'list[list[tuple[int,int]]]' = []
    cluster_queue = []
    cluster_e = -sys.maxsize

    def release_cluster():
        nonlocal cluster_queue
        if len(cluster_queue):
            clusters.append(cluster_queue)
            cluster_queue = []

    for b, e in intervals:
        if b > cluster_e:  # new cluster
            release_cluster()
        cluster_e = max(e, cluster_e)
        cluster_queue.append((b, e))

    release_cluster()

    return clusters


def select_best_of_overlapping(document: 'str', candidates: 'list[tuple[int,int]]', pattern: 'str',
                               remove_insides: 'bool' = True) -> 'tuple[int,int]':
    if remove_insides:
        candidatess = set(candidates)
        for (b1, e1), (b2, e2) in itertools.product(candidates, candidates):
            if (b1, e1) != (b2, e2) and b2 >= b1 and e2 <= e1:
                candidatess.discard((b2, e2))
        candidates = list(candidatess)

    best_c = None
    best_d = sys.maxsize
    for cb, ce in candidates:
        new_d = di_distance(document[cb:ce], pattern, cache=True)
        if new_d < best_d or (new_d == best_d and ce - cb > best_c[1] - best_c[0]):
            best_d = new_d
            best_c = cb, ce

    return best_c

def last_cleanup_stage(document: 'str', candidates: 'list[tuple[int,int]]', pattern: 'str',
                       remove_insides: 'bool' = True) -> 'list[tuple[int,int]]':
    print("Joining overlapping and removing redundant")

    clusters = transit_intersections(candidates)
    result = [select_best_of_overlapping(document, cl, pattern, remove_insides=remove_insides) for cl in clusters]

    return result

def has_disjoint_elements(candidates: 'list[tuple[int,int]]', b: 'int', e:'int') -> 'bool':
    min_right, max_left = e, b
    for candidate in candidates:
        max_left = max(max_left, candidate[0])
        min_right = min(min_right, candidate[1])
    return max_left > min_right

def get_word_right_space(document: 'str', index: 'int') -> 'int':
    space = math.ceil(index)
    while space >= 0 and not re.match("\s", document[space]):
        space += 1
    return space

def join_overlapping_candidates(document: 'str', similarity: 'float', pattern: 'str',
                                candidates: 'list[tuple[int,int]]',
                                maxlength: 'int') -> 'list[tuple[int,int]]':
    print("Joining overlapping...", end='')
    l0 = len(candidates)

    def intersects(t1: 'tuple[int, int]', t2: 'tuple[int, int]') -> 'bool':
        b1, e1 = t1
        b2, e2 = t2
        return b1 <= b2 <= e1 or b1 <= e2 <= e1

    cluster_state = {}
    for c in candidates:
        cluster_state[c] = [c]

    something_changed = True
    # for i in range(sys.maxsize):
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
                break
        # if !something_changed:
        #     break

    # then cancel too big zones
    final_candidates = []
    csk = list(cluster_state.keys())

    for k in csk:
        b, e = k
        if e - b > maxlength:
            print(cluster_state[k])
            space = get_word_right_space(document, (e - b) / 2)
            if has_disjoint_elements(cluster_state[k], b, e):
                f = fit_candidate(document, pattern, similarity, (b, b + space))
                s = fit_candidate(document, pattern, similarity, (b + space + 1, e))
                final_candidates.append(f)
                final_candidates.append(s)
            else:
                final_candidates.append(k)

            del cluster_state[k]

    # and add joint candidates
    for k in cluster_state.keys():
        final_candidates.append(k)

    # print(" %d -> %d." % (l0, len(final_candidates)))
    return final_candidates


def search(document: str, pattern: str, similarity: float, optimize_size: bool = True, unify_whitespaces: bool = True,
           remove_insides: bool = True) -> 'list[tuple[int,int]]':
    glog().info("|D| = %d; |p| = %d; k = %f; p = '%s'" % (
        len(document), len(pattern), similarity, pattern
    ))
    t1 = time.time()

    global _word_begins, _word_ends
    _word_begins = None
    _word_ends = None

    if unify_whitespaces:
        document = re.sub('\s', ' ', document)
        pattern = re.sub('\s', ' ', pattern)

    global optimize_stage1_by_words, optimize_stage2_by_words
    if not optimize_size:
        optimize_stage1_by_words = False
        optimize_stage2_by_words = False

    w1 = get_fuzzy_match_areas(document, pattern, similarity)
    w2 = fit_candidates(document, pattern, similarity, w1)
    if optimize_size:
        if optimize_stage3_union:
            maxjountlength = 2 * len(pattern) * similarity
            w3 = join_overlapping_candidates(document, similarity, pattern, w2, maxjountlength)
        else:
            w3 = last_cleanup_stage(document, w2, pattern, remove_insides=remove_insides)


    else:
        w3 = w2

    t2 = time.time()
    glog().info("Search took %d seconds and returned %d matches" % (int(t2 - t1), len(w3)))

    return w3


def main():
    global optimize_fit_cutoff, optimize_distant_jump, optimize_stage1_by_words, optimize_stage2_by_words, optimize_stage2_length_borders
    optimize_fit_cutoff = False
    optimize_stage1_by_words = True

    optimize_distant_jump = True
    optimize_stage2_by_words = True
    optimize_stage2_length_borders = False  # Only gives only a bit
    optimize_stage3_union = True

    bassett = 0.15
    apr = argparse.ArgumentParser()
    apr.add_argument('--document', type=str)
    apr.add_argument('--pattern', type=str)
    apr.add_argument('--similarity', type=float, default=1 / (1 + bassett))
    apr.add_argument('--optimize-size', type=bool, default=True)
    apr.add_argument('--unify-whitespaces', type=bool, default=True)
    args = apr.parse_args()

    with open(args.document, encoding='utf-8') as docfile:
        doc_text = docfile.read()

    # performance
    similarity = str(args.similarity)
    doc_name = str(args.document).split("\\")[-1].replace(".pxml", "")
    doc_size = len(doc_text)
    pattern_size = len(str(args.pattern))
    filename = str(similarity.replace(".", ""))
    t1 = time.time()

    # candidatess = get_fuzzy_match_areas(doc_text, args.pattern, args.similarity)
    # fit = fit_candidates(doc_text, args.pattern, args.similarity, candidatess)
    # nipeaks = remove_redundant_candidates(fit)

    glog().info("D = '%s'" % (args.document))
    nipeaks = search(doc_text, args.pattern, args.similarity, optimize_size=args.optimize_size,
                     unify_whitespaces=args.unify_whitespaces)

    t2 = int(time.time() - t1)

    for peak in nipeaks:
        print(repr(peak), doc_text[peak[0]:peak[1]])

    with open(file=filename + ".txt", mode='a', encoding='utf-8') as res:
        res.write("\nPATTERN: " + str(args.pattern))
        for peak in nipeaks:
            res.write("\n" + str(repr(peak)) + " " + str(doc_text[peak[0]:peak[1]]))

    with open(filename + ".csv", 'a', encoding='utf-8') as scsv:
        wtr = csv.writer(scsv, lineterminator='\n')
        wtr.writerow([doc_size, pattern_size, len(nipeaks), similarity, t2, doc_name])

    for peak in nipeaks:
        print(repr(peak), doc_text[peak[0]:peak[1]])


if __name__ == '__main__':
    main()
