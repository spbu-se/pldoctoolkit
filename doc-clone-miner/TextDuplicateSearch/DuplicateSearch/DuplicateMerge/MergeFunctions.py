import math
from typing import Union, List, Tuple

from TextDuplicateSearch.DataModels.TextModel import TextModel
from TextDuplicateSearch.DataModels.DuplicateCase import DuplicateCase
from TextDuplicateSearch.DataModels.DuplicateCollection import DuplicateCollection
from TextDuplicateSearch.DuplicateSearch.DuplicateMerge.SignificanceFunctions import size_length_significance

significance_diff = size_length_significance


def merge_duplicate_groups(dup_collection: DuplicateCollection, text_model: TextModel) -> None:
    dup_collection.filter_irrelevant()

    for i, case in enumerate(dup_collection.cases):
        for frg in case.text_fragments:
            frg.start.fragment = i
            frg.end.fragment = i

    for i in range(len(dup_collection.cases)):
        while _balance_cases(dup_collection.cases[i], dup_collection, text_model):
            pass

    dup_collection.filter_irrelevant()


def _balance_cases(main_case: DuplicateCase, dup_collection: DuplicateCollection, text_model: TextModel) -> bool:
    if main_case.count == 0:
        return False

    merge_case_right: DuplicateCase
    merge_case_left: DuplicateCase
    candidates_right: List[int]
    candidates_left: List[int]
    sign_right: float
    sign_left: float

    right_group: List[int] = [text_model.tokens[frg.end.idx + 1].fragment if frg.end.idx + 1 < text_model.length else -1 for frg in main_case.text_fragments]
    left_group: List[int] = [text_model.tokens[frg.start.idx - 1].fragment if frg.start.idx - 1 > 0 else -1 for frg in main_case.text_fragments]

    merge_case_right, candidates_right, sign_right = _check_merge(main_case, dup_collection, right_group)
    merge_case_left, candidates_left, sign_left = _check_merge(main_case, dup_collection, left_group)

    if sign_right <= 0 and sign_left <= 0:
        return False

    if sign_right > sign_left:
        for i in range(len(candidates_right)):
            main_case.text_fragments[i].merge_with(merge_case_right.text_fragments[candidates_right[i]])
    
        for frag in [frag for idx, frag in enumerate(merge_case_right.text_fragments) if idx in candidates_right]:
            merge_case_right.remove_fragment(frag)
    else:
        for i in range(len(candidates_left)):
            main_case.text_fragments[i].merge_with(merge_case_left.text_fragments[candidates_left[i]])

        for frag in [frag for idx, frag in enumerate(merge_case_left.text_fragments) if idx in candidates_left]:
            merge_case_left.remove_fragment(frag)

    return True


def _check_merge(main_case: DuplicateCase, dup_collection: DuplicateCollection, group_ids: List[int]) -> Tuple[DuplicateCase, List[int], float]:
    merge_case: Union[DuplicateCase, None] = None

    if group_ids[0] != -1 and group_ids.count(group_ids[0]) == len(group_ids):
        merge_case = dup_collection.cases[group_ids[0]]

    if merge_case is None or merge_case.count == 0:
        return DuplicateCase(), [], -math.inf

    candidates = []
    for i in range(main_case.count):
        for j in range(merge_case.count):
            if main_case.text_fragments[i].is_neighbor(merge_case.text_fragments[j]):
                candidates.append(j)
                break

    sign: float = significance_diff(merge_case, main_case, candidates)

    return merge_case, candidates, sign
