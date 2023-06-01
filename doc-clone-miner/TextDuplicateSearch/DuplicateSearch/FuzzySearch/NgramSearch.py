from TextDuplicateSearch.DataModels.DuplicateCase import DuplicateCase
from TextDuplicateSearch.DataModels.DuplicateCollection import DuplicateCollection
from TextDuplicateSearch.DataModels.SearchConfig import SearchConfig
from TextDuplicateSearch.DataModels.TextModel import TextModel
from TextDuplicateSearch.DuplicateSearch.DuplicateMerge.MergeFunctions import merge_duplicate_groups
from TextDuplicateSearch.DuplicateSearch.DuplicateSearcher import DuplicateSearcher
from TextDuplicateSearch.DuplicateSearch.FuzzySearch.Tools.NgramSet import NgramSet


class NgramSearch(DuplicateSearcher):
    def __init__(self, search_config: SearchConfig) -> None:
        super().__init__(search_config)
        self.text_model: TextModel = TextModel([])

    def find_duplicates(self, text_model: TextModel) -> DuplicateCollection:
        self.text_model = text_model
        text_model.split_sentences()
        for fragment in text_model.parts:
            fragment.create_ngram_set(self.config.ngram_n)

        result: DuplicateCollection = DuplicateCollection()

        for sentence in text_model.parts:
            if len(sentence.ngram_set.ngrams) == 0:
                continue

            best_overlap: float = 0
            best_case: int = 0

            for i in range(len(result.cases)):
                cur_set: NgramSet = NgramSet(sentence.ngram_set.n)
                cur_set.ngrams = sentence.ngram_set.ngrams.copy()

                for fragment in result.cases[i].text_fragments:
                    cur_set.intersect(fragment.ngram_set)

                overlap: float = len(cur_set.ngrams) / len(sentence.ngram_set.ngrams)
                if overlap > best_overlap:
                    best_overlap = overlap
                    best_case = i

            if best_overlap < self.config.min_overlap:
                new_case: DuplicateCase = DuplicateCase()
                new_case.add_fragment(sentence)
                result.add_case(new_case)
            else:
                result.cases[best_case].add_fragment(sentence)

        merge_duplicate_groups(result, text_model)

        return result
