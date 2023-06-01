from __future__ import annotations
from typing import List

from TextDuplicateSearch.DuplicateSearch.FuzzySearch.Tools.NgramSet import NgramSet
from TextDuplicateSearch.TextProcessing.Token import Token


class TextFragment:
    def __init__(self, token_list: List[Token]) -> None:
        self.tokens: List[Token] = token_list
        self.ngram_set: NgramSet = NgramSet(0)
        self.start: Token
        self.end: Token
        self.length: int
        self.idx: int
        self.hash: int

        self._update_params()

    def _update_params(self) -> None:
        self.start = self.tokens[0] if len(self.tokens) > 0 else Token.empty()
        self.end = self.tokens[-1] if len(self.tokens) > 0 else Token.empty()
        self.length = len(self.tokens)
        self.idx = -1
        self.hash = 0

    def merge_with(self, fragment: TextFragment) -> None:
        if not self.is_neighbor(fragment):
            return

        if self.is_after(fragment):
            self.tokens = fragment.tokens + self.tokens
            self._update_params()
            return

        if self.is_before(fragment):
            self.tokens = self.tokens + fragment.tokens
            self._update_params()
            return

    def is_neighbor(self, fragment: TextFragment) -> bool:
        return self.start.idx == fragment.end.idx + 1 or self.end.idx + 1 == fragment.start.idx

    def is_after(self, fragment: TextFragment) -> bool:
        return fragment.end.idx < self.start.idx

    def is_before(self, fragment: TextFragment) -> bool:
        return self.end.idx < fragment.start.idx

    def create_ngram_set(self, n: int) -> None:
        self.ngram_set = NgramSet.create_ngram_set(self.tokens, n)

    def __str__(self) -> str:
        result: str = ''
        for i in range(len(self.tokens)):
            result += self.tokens[i].text + ' '

        return result

    def __repr__(self) -> str:
        result: str = ''
        for i in range(len(self.tokens)):
            result += self.tokens[i].text + ' '

        return result

    def __lt__(self, other: TextFragment) -> bool:
        return self.end.idx < other.start.idx
