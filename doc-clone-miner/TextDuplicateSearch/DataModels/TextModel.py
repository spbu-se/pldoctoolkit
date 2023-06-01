from typing import TextIO, List

from TextDuplicateSearch.DataModels.TextFragment import TextFragment
from TextDuplicateSearch.TextProcessing.Token import Token


class TextModel:
    def __init__(self, tokens: List[Token], alpha_size: int = 0) -> None:
        self.text: str = ""
        self.tokens: List[Token] = tokens
        self.length: int = len(tokens)
        self.parts: List[TextFragment] = []
        self.alpha_size: int = alpha_size

    def split_equal_fragments(self, part_len: int) -> None:
        self.parts = []

        num: int = len(self.tokens) // part_len if len(self.tokens) % part_len == 0 else len(self.tokens) // part_len + 1

        for i in range(0, num - 1):
            self.parts.append(TextFragment(self.tokens[i * part_len: (i + 1) * part_len]))
            self.parts[-1].idx = i

        self.parts.append(TextFragment(self.tokens[(num - 1) * part_len: None]))

    def split_sentences(self) -> None:
        self.parts = []
        tmp_tokens: List[Token] = []

        for token in self.tokens:
            if token.sentence_start:
                tmp_tokens = []

            tmp_tokens.append(token)

            if token.sentence_end:
                self.parts.append(TextFragment(tmp_tokens))
