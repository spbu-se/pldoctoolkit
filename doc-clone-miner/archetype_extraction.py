#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import sys
import functools
import difflib
import re
import itertools
import util
import warnings

# testing
import unittest
import cProfile
from pstats import Stats

__author__ = "Victor Dolotov, Dmitry Luciv"
__copyright__ = "Copyright 2018+, The DocLine Project"
__credits__ = ["Victor Dolotov", "Dmitry Luciv"]
__license__ = "LGPL"
__version__ = "1.0.1"
__maintainer__ = "Dmitry Luciv"
__email__ = "yogiman1996@gmail.com"  # Victor Dolotov
__status__ = "Alpha"


def archetype_search(group):
    warnings.warn("Unverified", category=DeprecationWarning, stacklevel=15)

    # Форматирование повторов
    work_group = group.copy()
    for i in range(len(work_group)):
        work_group[i] = work_group[i].lower()
        work_group[i] = re.sub(r'(\W*)(\w+)(\W*)', r'\1 \2 \3', work_group[i])
        work_group[i] = re.sub(r'([^\s\w])([^\s\w])', r'\1 \2', work_group[i])
        work_group[i] = re.sub(r'\s+', r' ', work_group[i])
        work_group[i] = re.sub(r'^ | $', r'', work_group[i])
    indexes = [0]
    for i in range(1, len(work_group)):
        if work_group[i - 1] != work_group[i]:
            indexes.append(i)
    #

    # Исключение точных групп и повторов
    work_group = [work_group[i] for i in indexes]
    num_dup = len(work_group)
    if num_dup == 1:
        return [[(0, len(dup))] for dup in group]
    #

    # Получение перестановок
    max_num_perms = max(120, num_dup + 1)
    M = 0
    num_perms = 1
    while M <= num_dup and num_perms < max_num_perms:
        num_perms *= num_dup - M
        M += 1
    M -= 1
    work_group = [dup.split(' ') for dup in work_group]
    perms = list(itertools.permutations(work_group, M))
    #

    # Поиск архетипа
    archetype = []
    for perm in perms:
        archetype_p = []
        if M == 1:
            for j in range(num_dup):
                if not perm.__contains__(work_group[j]):
                    archetype_p = alg(perm[0], work_group[j])
                    break
        else:
            archetype_p = alg(perm[0], perm[1])
        for j in range(2, M):
            archetype_p = alg(archetype_p, perm[j])
            if len(archetype_p) == 0:
                break
        for j in range(num_dup):
            if not perm.__contains__(work_group[j]):
                archetype_p = alg(archetype_p, work_group[j])
            if len(archetype_p) == 0:
                break
        if (len(archetype) < len(archetype_p)) | (
                (len(archetype) == len(archetype_p)) & (len(archetype.__str__()) < len(archetype_p.__str__()))):
            archetype = archetype_p

        # SequenceMatcher
        if M == 1:
            for j in range(num_dup):
                if not perm.__contains__(work_group[j]):
                    archetype_p = diffalg(perm[0], work_group[j])
                    break
        else:
            archetype_p = diffalg(perm[0], perm[1])
        for j in range(2, M):
            archetype_p = diffalg(archetype_p, perm[j])
            if len(archetype_p) == 0:
                break
        for j in range(num_dup):
            if not perm.__contains__(work_group[j]):
                archetype_p = diffalg(archetype_p, work_group[j])
            if len(archetype_p) == 0:
                break
        if (len(archetype) < len(archetype_p)) | (
                (len(archetype) == len(archetype_p)) & (len(archetype.__str__()) < len(archetype_p.__str__()))):
            archetype = archetype_p
        #
    #

    # Преобразование результата
    result = [[] for i in range(num_dup)]
    start = [0] * num_dup
    end = [0] * num_dup
    current_index = [0] * num_dup
    gr = [group[i].lower() for i in indexes]
    for i in range(len(work_group)):
        while work_group[i][current_index[i]] != archetype[0]:
            word = re.sub(r'([.?+*[\]\\^${}|()])', r'\\\1', work_group[i][current_index[i]])
            start[i] = re.search(word, gr[i][end[i]:]).end() + end[i]
            end[i] = start[i]
            current_index[i] += 1
        word = re.sub(r'([.?+*[\]\\^${}|()])', r'\\\1', work_group[i][current_index[i]])
        start[i] = re.search(word, gr[i][end[i]:]).start() + end[i]
        end[i] += re.search(word, gr[i][end[i]:]).end()
        current_index[i] += 1
    is_arc = True
    for arc in range(1, len(archetype)):
        for i in range(len(work_group)):
            if work_group[i][current_index[i]] != archetype[arc]:
                is_arc = False
                break
        for i in range(len(work_group)):
            if is_arc:
                word = re.sub(r'([.?+*[\]\\^${}|()])', r'\\\1', work_group[i][current_index[i]])
                end[i] += re.search(word, gr[i][end[i]:]).end()
                current_index[i] += 1
            else:
                result[i].append((start[i], end[i]))
                while work_group[i][current_index[i]] != archetype[arc]:
                    word = re.sub(r'([.?+*[\]\\^${}|()])', r'\\\1', work_group[i][current_index[i]])
                    start[i] = re.search(word, gr[i][end[i]:]).end() + end[i]
                    end[i] = start[i]
                    current_index[i] += 1
                word = re.sub(r'([.?+*[\]\\^${}|()])', r'\\\1', work_group[i][current_index[i]])
                start[i] = re.search(word, gr[i][end[i]:]).start() + end[i]
                end[i] += re.search(word, gr[i][end[i]:]).end()
                current_index[i] += 1
        is_arc = True
    for i in range(len(work_group)):
        result[i].append((start[i], end[i]))

    if num_dup < len(group):
        res = [[] for i in range(len(group))]
        for i in range(len(indexes)):
            res[indexes[i]] = result[i]
        for i in range(len(res)):
            if not res[i]:
                res[i] = res[i - 1]
        result = res
    #
    return result


def alg(duplicate_a, duplicate_b):
    warnings.warn("Unverified", category=DeprecationWarning, stacklevel=15)

    mulct = -1
    num_words = max(len(duplicate_a), len(duplicate_b))

    matrix_score = [[0] * (len(duplicate_a) + 1) for i in range(len(duplicate_b) + 1)]
    for i in range(1, len(duplicate_a) + 1):
        matrix_score[0][i] = mulct * i
    for i in range(1, len(duplicate_b) + 1):
        matrix_score[i][0] = mulct * i
    for i in range(1, len(duplicate_b) + 1):
        for j in range(1, len(duplicate_a) + 1):
            diag = 0
            if duplicate_a[j - 1] == duplicate_b[i - 1]:
                diag = 10
            M = matrix_score[i - 1][j - 1] + num_words * diag * len(duplicate_b[i - 1])
            D = matrix_score[i - 1][j] + mulct
            I = matrix_score[i][j - 1] + mulct
            matrix_score[i][j] = max(M, D, I)

    res = []
    i = len(duplicate_b)
    j = len(duplicate_a)
    while i > 0 and j > 0:
        score = matrix_score[i][j]
        score_left = matrix_score[i][j - 1]
        score_up = matrix_score[i - 1][j]
        if score == score_up + mulct:
            i = i - 1
        else:
            if score == score_left + mulct:
                j = j - 1
            else:
                if duplicate_a[j - 1] == duplicate_b[i - 1]:
                    res.append(duplicate_a[j - 1])
                i = i - 1
                j = j - 1
    res.reverse()
    return res


def diffalg(a, b):
    seq = difflib.SequenceMatcher()
    seq.set_seqs(a, b)
    string = []
    for block in seq.get_matching_blocks():
        ai = block[0]
        if block[2] != 0:
            for i in range(block[2]):
                string.append(a[ai + i])
    return string


def permutations_first_volatile(rank: 'int', n_cover: 'int' = 3):
    """
    Generate subset of all possible permutations with first values changing more frequently
    :param rank: Permutations are tuples of [0, ..., rank)
    :param n_cover: number of first permutation elements to take all possible values
    :return: iterable of permutations
    """
    if n_cover > rank:
        n_cover = rank

    initial = list(reversed(range(rank)))
    initial_s = set(initial)

    for c in itertools.combinations(initial, n_cover):
        pf = tuple(reversed(c))
        yield pf + tuple(initial_s.difference(pf))

@functools.lru_cache(maxsize=None)
def two_tuples_lcs(w1: 'tuple[str]', w2: 'tuple[str]') -> 'tuple[str]':
    sm = difflib.SequenceMatcher(None, w1, w2, False)
    matches = sm.get_matching_blocks()
    return tuple(itertools.chain(*[ w1[match.a: match.a + match.size] for match in matches ]))


@functools.lru_cache(maxsize=None)
def possible_n_tuples_lcs(ws: 'iterable[tuple[str]]') -> 'tuple[str]':
    # operator.reduce is a kind of foldl
    return functools.reduce(two_tuples_lcs, ws)

@functools.lru_cache(maxsize=None)
def best_n_tuples_lcs(strings: 'tuple[tuple[str]]') -> 'tuple[str]':
    strings = tuple(set(strings))
    best_archetype = ()
    best_archetype_len = 0
    for p in permutations_first_volatile(len(strings)):
        permuted_strings = tuple(strings[i] for i in p)
        archetype = possible_n_tuples_lcs(permuted_strings)
        archetype_len = len(' '.join(archetype))
        if archetype_len > best_archetype_len:
            best_archetype = archetype
            best_archetype_len = archetype_len
    return best_archetype


def calculate_archetype_occurrences(fuzzy_clone_texts: 'iterable[str]') -> 'list[list[tuple[int, int]]]':
    """It is wrong... totally wrong..."""

    def itokens(text):
        return tuple(
            (n, b, e, s)
            for n, (b, e, s)
            in zip(itertools.count(), util.tokens(text))
        )

    itoken_lists = [itokens(text) for text in fuzzy_clone_texts]
    token_text_tuples = tuple(tuple(s for n, b, e, s in token_list) for token_list in itoken_lists)
    token_text_lists = [ [t for t in ttu] for ttu in token_text_tuples]

    # Get archetype tokens
    archetype_tokens = best_n_tuples_lcs(token_text_tuples)

    if len(archetype_tokens) == 0:  # No archetype detected!
        return [], True, True

    need_fake_before = False
    need_fake_after  = False
    for itl in itoken_lists:
        n, b, e, s = itl[0]
        if s != archetype_tokens[0]:
            need_fake_before = True
        n, b, e, s = itl[-1]
        if s != archetype_tokens[-1]:
            need_fake_after = True

    # group_indices = []
    group_offsets = []
    cur_end = [0] * len(fuzzy_clone_texts)
    cur_begin = [-1] * len(fuzzy_clone_texts)

    def start_group():
        nonlocal cur_begin
        cur_begin = next_indices_vector

    def release_group():
        group_offsets.append([
            (itl[b][1], itl[e][2])
            for b, e, itl in zip(cur_begin, cur_end, itoken_lists)
        ])

    # Идём фронтом по всем строкам

    next_indices_vector = [
        ttl.index(archetype_tokens[0], cti) for ttl, cti in zip(token_text_lists, cur_end)
    ]
    start_group()

    for ar_tok, tok_i in zip(archetype_tokens[1:], itertools.count()):

        cur_end = next_indices_vector
        next_indices_vector = [
            ttl.index(ar_tok, cti) for ttl, cti in zip(token_text_lists, cur_end)
        ]
        skip = max(n - c for n, c in zip(next_indices_vector, cur_end)) > 1

        if skip:
            release_group()
            start_group()

    cur_end = next_indices_vector
    release_group()

    return group_offsets, need_fake_before, need_fake_after

def get_variative_element(clones: 'module', group: 'clones.FuzzyCloneGroup' ) -> 'clones.VariativeElement':
    try:
        # Dolotov's detector invocation
        # archetype: 'list[list[tuple[int, int]]]' = archetype_search(group.instancetexts)
        # group_archetypes = list(map(list, zip(*archetype)))  # transpose
        # Add fake groups in beginngings and ends if archetype does not touch frontiers
        # need_fake_before = False
        # need_fake_after = False
        # for bb, be in group_archetypes[0]:
        #     if bb != 0:
        #         need_fake_before = True
        # for (eb, ee), fb, fe in zip(group_archetypes[-1], fuzzy_offsets, fuzzy_ends):
        #     if ee != fe - fb:
        #         need_fake_after = True

        # not ready yet =(
        group_archetypes, need_fake_before, need_fake_after = \
            calculate_archetype_occurrences(group.instancetexts)

        fuzzy_offsets = [b for n, b, e in group.instances]
        fuzzy_ends = [e for n, b, e in group.instances]

        groups = [
            clones.ExactCloneGroup(0, 1, [
                (
                    0,  # file №
                    o + b, o + e -1 # from Dolotov =)
                )
                for ((b, e), o) in zip(ginsts, fuzzy_offsets)
            ]) for ginsts in group_archetypes
        ]

        if need_fake_before:
            groups.insert(0,
                clones.ExactCloneGroup(0, 1, [
                    (0, o, o - 1) for o in fuzzy_offsets
                ])
            )

        if need_fake_after:
            groups.append(
                clones.ExactCloneGroup(0, 1, [
                    (0, e + 1, e) for e in fuzzy_ends
                ])
            )

        return clones.VariativeElement(groups, group.group_uuid)
    except Exception as e:
        import sys
        print(f"ADE: {e}", file=sys.stderr)
        return clones.VariativeElement([group])  # fallback if archetype not detected


def get_html(clones: 'module', group, archetype):
    source = ''
    delimiter = '\n\n' + '-' * 200 + '\n\n'
    for dup in group:
        source += dup + delimiter
    source = source[:len(source) - len(delimiter)]
    file = open('Archetype\Duplicates.txt', "w", encoding='utf-8')
    file.write(source)
    file.close()
    clones.inputfiles = [clones.InputFile('Archetype\Duplicates.txt')]
    CloneGroups = [clones.ExactCloneGroup(0, 1, [(0, (0, 0), (0, 0))]) for i in range(len(archetype[0]))]
    for clone_group in CloneGroups:
        clone_group.instances = [0] * len(archetype)
    start = 0
    file = open('Archetype\Variable_points.txt', "w", encoding='utf-8')
    for i in range(len(archetype)):
        for j in range(len(archetype[i])):
            if j != len(archetype[i]) - 1:
                file.write(group[i][archetype[i][j][1]:archetype[i][j + 1][0]] + '\n')
            CloneGroups[j].instances[i] = (0, start + archetype[i][j][0], start + archetype[i][j][1] - 1)
        start += len(group[i]) + len(delimiter)
        file.write(delimiter[2:len(delimiter) - 1])
    file.close()
    var = clones.VariativeElement(CloneGroups)
    import util
    util.write_variative_report(clones, [var], r'Archetype\archetype.html')


class TestStringMethods(unittest.TestCase):
    def __init__(self, t):
        super().__init__(t)  # what is t here? =)
        self.w1 = ("ab", "xx", "cd",       "ee", "ff", "gh", "ij", "kl")
        self.w2 = ("ab",       "34", "yy", "ee", "ff", "78", "ij", "kl")
        self.w3 = ("ab", "xx", "34", "yy", "ee", "ЯЙ", "78", "ij", "kl")

    def setUp(self):
        return
        """init each test"""
        self.pr = cProfile.Profile()
        self.pr.enable()
        print("\n<<<---")

    def tearDown(self):
        return
        """finish any test"""
        p = Stats(self.pr)
        p.strip_dirs()
        p.sort_stats('cumtime')
        p.print_stats()
        print("\n--->>>")

    def test_001_small_common(self):
        a = archetype_search([
            "Я пошёл позавчера за хлебом",
            "Я пошёл вчера за хлебом",
            "Я пошёл сегодня за хлебом",
            "Я пойду завтра за хлебом"
        ])
        print(a)
        self.assertEqual(
            a,
            [[(0, 1), (18, 27)], [(0, 1), (14, 23)], [(0, 1), (16, 25)], [(0, 1), (15, 24)]],
            "Small common failed"
        )

    def test_002_small_common_lists(self):
        a = archetype_search([
            " ".join(["Я", "пошёл", "позавчера", "за", "хлебом"]),
            " ".join(["Я", "пошёл", "вчера", "за", "хлебом"]),
            " ".join(["Я", "пошёл", "сегодня", "за", "хлебом"]),
            " ".join(["Я", "пойду", "завтра", "за", "хлебом"])
        ])
        self.assertEqual(
            a,
            ([[(0, 60), (0, 56), (0, 58)], [(52, 110), (48, 106), (50, 108)]], False, False)
        )

    def test_003_perm_order(self):
        self.assertEqual(
            [(2, 3, 4, 0, 1), (1, 3, 4, 0, 2), (0, 3, 4, 1, 2), (1, 2, 4, 0, 3), (0, 2, 4, 1, 3), (0, 1, 4, 2, 3),
             (1, 2, 3, 0, 4), (0, 2, 3, 1, 4), (0, 1, 3, 2, 4), (0, 1, 2, 3, 4)],
            list(permutations_first_volatile(5))
        )

    def test_004_two_tuples_lcs(self):
        self.assertEqual(
            two_tuples_lcs(self.w1, self.w2),
            ('ab', 'ee', 'ff', 'ij', 'kl')
        )

    def test_004_best_n_tuples_lcs(self):
        self.assertEqual(
            best_n_tuples_lcs((self.w1, self.w2, self.w3)),
            ('ab', 'ee', 'ij', 'kl')
        )

    def test_005_calculate_archetype_occurences(self):
        w1 = " ".join(self.w1)
        w2 = " ".join(self.w2)
        w3 = " ".join(self.w3)
        # print(calculate_archetype_occurrences([w1, w2, w3]))
        self.assertEqual(
            calculate_archetype_occurrences([w1, w2, w3]),
            ([[(0, 5), (0, 5), (0, 5)], [(9, 14), (9, 14), (12, 17)], [(18, 23), (18, 23), (21, 26)]], False, False)
        )

    def test_006_archetype_from_LKD_2013(self):
        s1 = """This function is called whenever the initialization function of a real
object is called.

When the real object"""
        s2 = """This function is called whenever the activation function of a real
object is called.

When the real object"""
        s3 = """This function is called whenever the deactivation function of a real
object is called.

When the real object"""
        print(calculate_archetype_occurrences([s1, s2, s3]))

    def test_007_archetype_from_JUnit_4(self):
        testdata = [
            'Verify that your code throws an exception that is an\ninstance of specific type.',
            'Verify that your code throws an exception whose message contains\na specific text.',
            'The ExpectedException rule allows you to verify that your code\nthrows a specific exception.'
        ]
        d, b, e = calculate_archetype_occurrences(testdata)
        self.assertTrue(b)
        self.assertTrue(e)


if __name__ == '__main__':
    unittest.main()
    # raise Exception("This is not an entry point")
