from typing import List

from TextDuplicateSearch.DataModels.DuplicateCase import DuplicateCase


def size_length_significance(case_a: DuplicateCase, case_b: DuplicateCase, candidates: List[int]) -> float:
    cand_sum_length = sum([case_a.text_fragments[idx].length for idx in candidates])
    cand_count = len(candidates)

    prev_sign: float = _size_length_func(case_a.count, case_a.sum_length / case_a.count) + \
                       _size_length_func(case_b.count, case_b.sum_length / case_b.count)

    new_sing_1: float = 0
    if case_a.count - cand_count > 0:
        new_sing_1 = _size_length_func(case_a.count - cand_count,
                                       (case_a.sum_length - cand_sum_length) / (case_a.count - cand_count))

    new_sign_2: float = _size_length_func(case_b.count,
                                          (case_b.sum_length + cand_sum_length) / case_b.count)

    return new_sing_1 + new_sign_2 - prev_sign


def _size_length_func(n: int, m: float) -> float:
    return n * (m ** 2)
