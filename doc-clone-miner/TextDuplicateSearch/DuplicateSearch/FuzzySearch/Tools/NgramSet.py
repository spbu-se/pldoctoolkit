from __future__ import annotations
from typing import Set, Tuple, List
from nltk import ngrams # type: ignore

from TextDuplicateSearch.TextProcessing.Token import Token


class NgramSet:
    def __init__(self, n: int) -> None:
        self.n: int = n
        self.ngrams: Set[Tuple[str, ...]] = set()

    def add_fragment(self, fragment: List[Token]) -> None:
        text = [token.processed for token in fragment]
        for ngram in ngrams(text, self.n):
            self.ngrams.add(ngram)

    def intersect(self, ngram_set: NgramSet) -> None:
        if self.n != ngram_set.n:
            return

        self.ngrams = self.ngrams & ngram_set.ngrams

    @staticmethod
    def create_ngram_set(fragment: List[Token], n: int) -> NgramSet:
        result: NgramSet = NgramSet(n)
        result.add_fragment(fragment)
        return result
