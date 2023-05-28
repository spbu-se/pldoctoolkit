from TextDuplicateSearch.DataModels.TextModel import TextModel
from TextDuplicateSearch.DataModels.SearchConfig import SearchConfig
from TextDuplicateSearch.DataModels.DuplicateCollection import DuplicateCollection


class DuplicateSearcher:
    def __init__(self, search_config: SearchConfig) -> None:
        self.config = search_config

    def find_duplicates(self, text_model: TextModel) -> DuplicateCollection:
        return DuplicateCollection()
