from __future__ import annotations

from typing import Tuple, Any


class Token:
    def __init__(self, token_text: str, position: Tuple[int, int], idx: int, offset: int) -> None:
        self.text: str = token_text
        self.line: int = position[0]
        self.col: int = position[1]
        self.idx: int = idx
        self.offset: int = offset

        self.processed: str = ""
        self.id: int = -1
        self.sentence_start: bool = False
        self.sentence_end: bool = False
        self.fragment: int = -1

    def __str__(self) -> str:
        return self.text

    def __repr__(self) -> str:
        return f'{self.text}:{self.id}'

    def __eq__(self, other: Any) -> bool:
        if isinstance(other, Token):
            return self.id == other.id
        return False

    @staticmethod
    def empty() -> Token:
        return Token("", (-1, -1), -1, -1)
