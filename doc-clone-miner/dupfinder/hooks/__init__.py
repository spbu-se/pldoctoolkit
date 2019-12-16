from __future__ import annotations
from abc import ABC, abstractmethod

class ReportHook(ABC):
    @staticmethod
    @abstractmethod
    def transform_nearduplicate_html(html: str)-> str:
        return None
