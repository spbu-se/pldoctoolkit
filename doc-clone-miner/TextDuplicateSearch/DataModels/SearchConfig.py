from dataclasses import dataclass, field


@dataclass
class SearchConfig:
    # general
    file_encoding: str = "utf-8"
    input_file: str = ""
    output_file: str = ""
    searcher_type: int = 0

    # text processing
    need_text_processing: bool = True
    filter_stop_words: bool = True
    stop_words_file: str = ""
    classes_file: str = ""

    # strict search
    min_dup_length: int = 3

    # fragment search
    fragment_size: int = 20
    max_hashing_diff: int = 8
    max_edit_distance: int = 2
    precise_grouping: bool = False

    # ngram search
    ngram_n: int = 3
    min_overlap: float = 0.5
