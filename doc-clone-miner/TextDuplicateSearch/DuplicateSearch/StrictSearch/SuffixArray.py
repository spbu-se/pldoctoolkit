from typing import List, Tuple, Dict

from TextDuplicateSearch.TextProcessing.Token import Token


def build_from_tokens(tokens: List[Token]) -> Tuple[List[int], List[int]]:
    token_ids: List[int] = [token.id for token in tokens]
    alpha_size = max(token_ids) + 1 if len(token_ids) > 0 else 0
    suffix_array: List[int] = _sa_skew_rec(token_ids, alpha_size)
    lcp_array: List[int] = _build_lcp_array(token_ids, suffix_array)
    return suffix_array, lcp_array


def build_from_string(text: str) -> Tuple[List[int], List[int]]:
    text_int: List[int] = [int(char) for char in text]
    suffix_array: List[int] = _sa_skew_rec(text_int, 256)
    lcp_array: List[int] = _build_lcp_array(text_int, suffix_array)
    return suffix_array, lcp_array


def build_from_array(array: List[int]) -> Tuple[List[int], List[int]]:
    alpha_size = max(array) + 1 if len(array) > 0 else 0
    suffix_array: List[int] = _sa_skew_rec(array, alpha_size)
    lcp_array: List[int] = _build_lcp_array(array, suffix_array)
    return suffix_array, lcp_array


class _Suffix:
    def __init__(self) -> None:
        self.position: int = -1
        self.first_rank: int = -1
        self.second_rank: int = -1


# O(n log^2 n)
# take suffixes sorted on first 2^i chars, split into equivalence classes,
# for each suffix @i take suffix @i+2^i and sort pairs => sorted on first 2^(i+1) chars
def _build_suffix_array(input_array: List[int], alpha_size: int = 0) -> List[int]:
    n: int = len(input_array)
    suffixes: List[_Suffix] = [_Suffix() for _ in range(n)]

    for i in range(n):
        suffixes[i].position = i
        suffixes[i].first_rank = input_array[i]
        suffixes[i].second_rank = input_array[i + 1] if ((i + 1) < n) else -1

    suffixes = sorted(
        suffixes, key=lambda x: (
            x.first_rank, x.second_rank
        )
    )

    current_idx: List[int] = [0] * n
    k: int = 4

    while k < 2 * n:
        new_rank: int = 0
        prev_rank: int = suffixes[0].first_rank
        suffixes[0].first_rank = new_rank
        current_idx[suffixes[0].position] = 0

        for i in range(1, n):
            if (suffixes[i].first_rank == prev_rank and
                    suffixes[i].second_rank == suffixes[i - 1].second_rank):
                suffixes[i].first_rank = new_rank
            else:
                prev_rank = suffixes[i].first_rank
                new_rank += 1
                suffixes[i].first_rank = new_rank
            current_idx[suffixes[i].position] = i

        for i in range(n):
            paired_suffix_pos: int = suffixes[i].position + k // 2
            suffixes[i].second_rank = suffixes[current_idx[paired_suffix_pos]].first_rank \
                if (paired_suffix_pos < n) else -1

        suffixes = sorted(
            suffixes, key=lambda x: (
                x.first_rank, x.second_rank
            )
        )

        k *= 2

    result: List[int] = [suffix.position for suffix in suffixes]

    return result

# O(n)
# Recursive Radix sort suffixes that are i % 3 = 1 and i % 3 = 2 with new alphabet,
# then sort i % 3 = 0 and merge into suffix array.
def _sa_skew_rec(x: List[int], asize: int) -> List[int]:
    if len(x) == 0:
        return []

    SA12: List[int] = [i for i in range(len(x)) if i % 3 != 0]

    SA12 = radix3(x, asize, SA12)
    new_alpha: TRIPLET_DICT = collect_alphabet(x, SA12)
    if len(new_alpha) < len(SA12):
        u: List[int] = build_u(x, new_alpha)

        sa_u: List[int] = _sa_skew_rec(u, len(new_alpha) + 2)

        m = len(sa_u) // 2

        SA12 = [u_idx(i, m) for i in sa_u if i != m]

    SA3 = ([len(x) - 1] if len(x) % 3 == 1 else []) + \
          [i - 1 for i in SA12 if i % 3 == 1]
    SA3 = bucket_sort(x, asize, SA3)

    return merge(x, SA12, SA3)


def safe_idx(x: List[int], i: int) -> int:
    return 0 if i >= len(x) else x[i]


def symbcount(x: List[int], asize: int) -> List[int]:
    counts = [0] * asize
    for c in x:
        counts[c] += 1
    return counts


def cumsum(counts: List[int]) -> List[int]:
    res, acc = [0] * len(counts), 0
    for i, k in enumerate(counts):
        res[i] = acc
        acc += k
    return res


def bucket_sort(x: List[int], asize: int,
                idx: List[int], offset: int = 0) -> List[int]:
    sort_symbs: List[int] = [safe_idx(x, i + offset) for i in idx]
    counts: List[int] = symbcount(sort_symbs, asize)
    buckets: List[int] = cumsum(counts)
    out: List[int] = [-1] * len(idx)
    for i in idx:
        bucket = safe_idx(x, i + offset)
        out[buckets[bucket]] = i
        buckets[bucket] += 1
    return out


def radix3(x: List[int], asize: int, idx: List[int]) -> List[int]:
    idx = bucket_sort(x, asize, idx, 2)
    idx = bucket_sort(x, asize, idx, 1)
    return bucket_sort(x, asize, idx)


TRIPLET = Tuple[int, int, int]
TRIPLET_DICT = Dict[TRIPLET, int]


def triplet(x: List[int], i: int) -> TRIPLET:
    return safe_idx(x, i), safe_idx(x, i + 1), safe_idx(x, i + 2)


def collect_alphabet(x: List[int], idx: List[int]) -> TRIPLET_DICT:
    alpha: Dict[TRIPLET, int] = {}
    for i in idx:
        trip = triplet(x, i)
        if trip not in alpha:
            alpha[trip] = len(alpha) + 2  # +2 to reserve sentinels
    return alpha


def build_u(x: List[int], alpha: TRIPLET_DICT) -> List[int]:
    return [*(alpha[triplet(x, i)] for i in range(1, len(x), 3)),
            1,
            *(alpha[triplet(x, i)] for i in range(2, len(x), 3))]


def u_idx(i: int, m: int) -> int:
    if i < m:
        return 1 + 3 * i
    else:
        return 2 + 3 * (i - m - 1)


def merge(x: List[int], SA12: List[int], SA3: List[int]) -> List[int]:
    ISA: Dict[int, int] = {SA12[i]: i for i in range(len(SA12))}
    SA: List[int] = []
    i: int = 0
    j: int = 0
    while i < len(SA12) and j < len(SA3):
        if less(x, SA12[i], SA3[j], ISA):
            SA.append(SA12[i])
            i += 1
        else:
            SA.append(SA3[j])
            j += 1
    SA.extend(SA12[i:])
    SA.extend(SA3[j:])
    return SA


def less(x: List[int], i: int, j: int, ISA: Dict[int, int]) -> bool:
    a: int = safe_idx(x, i)
    b: int = safe_idx(x, j)
    if a < b:
        return True
    if a > b:
        return False
    if i % 3 != 0 and j % 3 != 0:
        return ISA[i] < ISA[j]

    return less(x, i + 1, j + 1, ISA)


# O(n)
def _build_lcp_array(input_array: List[int], suffix_array: List[int]) -> List[int]:
    n: int = len(input_array)
    result: List[int] = [-1] * n

    inv_suffix: List[int] = [-1] * n
    for idx in range(n):
        inv_suffix[suffix_array[idx]] = idx

    common: int = 0
    for idx in range(1, n):
        if inv_suffix[idx] == 0:
            common = 0
            continue

        prev_idx: int = suffix_array[inv_suffix[idx] - 1]
        while (idx + common < n and
               prev_idx + common < n and
               input_array[idx + common] == input_array[prev_idx + common]):
            common += 1

        result[inv_suffix[idx]] = common
        if common > 0:
            common -= 1

    return result
