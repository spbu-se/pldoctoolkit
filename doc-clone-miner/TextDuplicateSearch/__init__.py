from typing import List, Type, Union

from TextDuplicateSearch.DataModels.DuplicateCollection import DuplicateCollection
from TextDuplicateSearch.DataModels.TextModel import TextModel
from TextDuplicateSearch.DuplicateSearch.DuplicateSearcher import DuplicateSearcher
from TextDuplicateSearch.DuplicateSearch.StrictSearch.SuffixSearch import SuffixSearch
from TextDuplicateSearch.DuplicateSearch.FuzzySearch.FragmentSearch import FragmentSearch
from TextDuplicateSearch.DuplicateSearch.FuzzySearch.NgramSearch import NgramSearch
from TextDuplicateSearch.DataModels.SearchConfig import SearchConfig
from TextDuplicateSearch.TextProcessing.Token import Token
from TextDuplicateSearch.TextProcessing.Tokenizer import Tokenizer

strict_searchers: List[Type[DuplicateSearcher]] = [SuffixSearch]
fuzzy_searchers: List[Type[DuplicateSearcher]] = [FragmentSearch, NgramSearch]


def strict_search(config: SearchConfig, text_model: Union[TextModel, None] = None, text: str = "") -> DuplicateCollection:
    config.need_text_processing = False
    config.filter_stop_words = False

    if not text_model:
        text_model = process_text(text, config)

    searcher: DuplicateSearcher = strict_searchers[config.searcher_type](config)

    return duplicate_search(searcher, text_model, config)


def fuzzy_search(config: SearchConfig, text_model: Union[TextModel, None] = None, text: str = "") -> DuplicateCollection:
    if not text_model:
        text_model = process_text(text, config)

    searcher: DuplicateSearcher = fuzzy_searchers[config.searcher_type](config)

    return duplicate_search(searcher, text_model, config)


def duplicate_search(searcher: DuplicateSearcher, text_model: TextModel, config: SearchConfig) -> DuplicateCollection:
    duplicates: DuplicateCollection = searcher.find_duplicates(text_model)

    if config.output_file:
        duplicates.output(config.output_file)

    return duplicates


def process_text(text: str, config: SearchConfig) -> TextModel:
    tokenizer: Tokenizer = Tokenizer()

    if not text:
        return tokenizer.create_text_model_file(config)
    else:
        return tokenizer.create_text_model(text, config)


def create_config() -> SearchConfig:
    return SearchConfig()
