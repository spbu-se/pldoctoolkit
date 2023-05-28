from typing import List

from TextDuplicateSearch.DataModels.TextFragment import TextFragment


class DuplicateCase:
    def __init__(self) -> None:
        self.count: int = 0
        self.text_fragments: List[TextFragment] = []
        self.sum_length: int = 0

    def add_fragment(self, fragment: TextFragment) -> None:
        self.text_fragments.append(fragment)
        self.text_fragments.sort(key=lambda frg: frg.start.idx)
        self.count += 1
        self.sum_length += fragment.length

    def remove_fragment(self, fragment: TextFragment) -> None:
        if fragment in self.text_fragments:
            self.text_fragments.remove(fragment)
            self.count -= 1
            self.sum_length -= fragment.length

    def length(self) -> int:
        return min([fragment.length for fragment in self.text_fragments]) if len(self.text_fragments) > 0 else 0

    def reset(self) -> None:
        self.count = 0
        self.sum_length = 0
        self.text_fragments = []
