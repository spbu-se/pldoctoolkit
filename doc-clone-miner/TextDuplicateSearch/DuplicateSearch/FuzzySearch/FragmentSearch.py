from typing import Callable, List, Union

from TextDuplicateSearch.DataModels.DuplicateCase import DuplicateCase
from TextDuplicateSearch.DataModels.DuplicateCollection import DuplicateCollection
from TextDuplicateSearch.DataModels.SearchConfig import SearchConfig
from TextDuplicateSearch.DataModels.TextModel import TextModel
from TextDuplicateSearch.DataModels.TextFragment import TextFragment
from TextDuplicateSearch.DuplicateSearch.DuplicateSearcher import DuplicateSearcher
from TextDuplicateSearch.DuplicateSearch.FuzzySearch.Tools.EditDistance import EditDistance, UkkonenAsm
from TextDuplicateSearch.DuplicateSearch.FuzzySearch.Tools.Hashing import Hashing
from TextDuplicateSearch.DuplicateSearch.DuplicateMerge.MergeFunctions import merge_duplicate_groups
from TextDuplicateSearch.TextProcessing.Token import Token


class FragmentSearch(DuplicateSearcher):
    def __init__(self, search_config: SearchConfig,
                 hashin_func: Callable[[List[Token]], int] = Hashing.simhash_func,
                 editdistance_func: EditDistance = UkkonenAsm(1),
                 ) -> None:

        super().__init__(search_config)

        self.hashin_func: Callable[[List[Token]], int] = hashin_func
        self.editdistance_func: EditDistance = editdistance_func
        self.text_model: TextModel = TextModel([])

        self.duplicates: List[List[int]] = []
        self.visited: List[bool] = []
        self.collection: Union[DuplicateCollection, None] = None

    def find_duplicates(self, text_model: TextModel) -> DuplicateCollection:
        self.text_model = text_model
        text_model.split_equal_fragments(self.config.fragment_size)

        self.duplicates = self._get_duplicates(text_model.parts)
        self.visited = [False for _ in range(len(text_model.parts))]

        if self.config.precise_grouping:
            self.collection = self._precise_grouping()
        else:
            self.collection = self._imprecise_grouping()

        merge_duplicate_groups(self.collection, text_model)
        return self.collection

    # Constructs adjacency list for similar fragments
    def _get_duplicates(self, fragments: List[TextFragment]) -> List[List[int]]:
        duplicates: List[List[int]] = [[] for _ in range(len(fragments))]
        hashes: List[int] = [self.hashin_func(fragment.tokens) for fragment in fragments]

        for i in range(len(fragments)):
            for j in range(i + 1, len(fragments)):
                if Hashing.get_hash_diff(hashes[i], hashes[j]) > self.config.max_hashing_diff:
                    continue

                edit_dist = self.editdistance_func.calculate(fragments[i],
                                                             fragments[j],
                                                             self.config.max_edit_distance)

                if edit_dist <= self.config.max_edit_distance:
                    duplicates[i].append(j)
                    duplicates[j].append(i)

        return duplicates

    # Finds graph components via DFS
    def _imprecise_grouping(self) -> DuplicateCollection:
        result: DuplicateCollection = DuplicateCollection()

        for i in range(len(self.visited)):
            if self.visited[i]:
                continue

            group: List[int] = []
            self._dfs(i, group)
            dup_case: DuplicateCase = DuplicateCase()

            if len(group) < 2:
                continue

            for fragment in group:
                dup_case.add_fragment(self.text_model.parts[fragment])

            result.add_case(dup_case)

        return result

    # Finds all max cliques via Bron–Kerbosch algorithm
    def _precise_grouping(self) -> DuplicateCollection:
        return DuplicateCollection()

    def _dfs(self, current: int, component: List[int]) -> None:
        self.visited[current] = True
        component.append(current)

        for neighbor in self.duplicates[current]:
            if not self.visited[neighbor]:
                self._dfs(neighbor, component)
