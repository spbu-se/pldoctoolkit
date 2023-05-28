import re
import string

from nltk.stem import PorterStemmer, WordNetLemmatizer  # type: ignore
from nltk.tokenize import LineTokenizer, WordPunctTokenizer, sent_tokenize  # type: ignore
from nltk.corpus import stopwords  # type: ignore
from typing import List, Dict, Tuple, TextIO, Iterator

from TextDuplicateSearch.DataModels.TextModel import TextModel
from TextDuplicateSearch.TextProcessing.Token import Token
from TextDuplicateSearch.DataModels.SearchConfig import SearchConfig

TOKEN_ID_START: int = 2


class Tokenizer:
    def __init__(self) -> None:
        self.token_id: Dict[str, int] = {}
        self.next_id: int = TOKEN_ID_START
        self.stemmer: PorterStemmer = PorterStemmer()
        self.lemmatizer: WordNetLemmatizer = WordNetLemmatizer()
        self.stop_words: List[str] = stopwords.words("english")

    def tokenize(self, input_string: str, search_config: SearchConfig) -> List[Token]:
        # setup
        if search_config.classes_file:
            self._load_token_classes(search_config)

        if search_config.filter_stop_words:
            if search_config.stop_words_file:
                self._load_stop_words(search_config.stop_words_file)
            else:
                self.stop_words = stopwords.words("english")

        # different tokenization for different parameters
        original: str = input_string
        sentences: Iterator[str] = sent_tokenize(input_string)
        input_string = re.sub('[{}]'.format(re.escape(string.punctuation)), ' ', input_string)
        input_string = '\n'.join(['' if line.isspace() else line for line in input_string.splitlines()])

        lines: Iterator[Tuple[int, int]] = LineTokenizer(blanklines='discard').span_tokenize(input_string)
        row: List[Tuple[int, int]] = [span for span in lines]

        columns: Iterator[Tuple[int, int]] = WordPunctTokenizer().span_tokenize(input_string)
        col: List[int] = [span[0] for span in columns]

        # calculating token coordinates in text
        result: List[Token] = []
        line_id: int = 0
        line_offset: int = 0
        token_idx: int = -1
        cur_idx: int = -1
        search_start: int = 0
        sentence_start: bool
        words: List[str]

        for sentence in sentences:
            sentence = re.sub('[{}]'.format(re.escape(string.punctuation)), ' ', sentence)
            words = WordPunctTokenizer().tokenize(sentence)
            sentence_start = True

            for word in words:
                cur_idx += 1

                if row[line_id][1] < col[cur_idx]:
                    line_id += 1
                    line_offset += row[line_id][0] - row[line_id - 1][1] - 1

                if word.lower() in self.stop_words:
                    continue

                offset: int = original.find(word, search_start)
                offset = offset if offset != -1 else col[cur_idx]
                search_start = offset + len(word)
                token_position: Tuple[int, int] = (line_id + line_offset + 1, col[cur_idx] - row[line_id][0] + 1)
                token_idx += 1
                cur_token: Token = Token(word, token_position, token_idx, offset)
                self._set_token_id(cur_token, search_config.need_text_processing)

                if sentence_start:
                    cur_token.sentence_start = True
                    sentence_start = False

                result.append(cur_token)

            if not sentence_start:
                result[-1].sentence_end = True

        return result

    def tokenize_file(self, search_config: SearchConfig) -> List[Token]:
        input_file: TextIO = open(search_config.input_file, encoding=search_config.file_encoding)
        text: str = input_file.read()
        input_file.close()
        return self.tokenize(text, search_config)

    def create_text_model(self, input_string: str, search_config: SearchConfig) -> TextModel:
        tokens: List[Token] = self.tokenize(input_string, search_config)
        return TextModel(tokens, self.next_id)

    def create_text_model_file(self, search_config: SearchConfig) -> TextModel:
        tokens: List[Token] = self.tokenize_file(search_config)
        return TextModel(tokens, self.next_id)

    def reset(self) -> None:
        self.token_id = {}
        self.next_id = TOKEN_ID_START

    def _load_stop_words(self, file_path: str) -> None:
        try:
            file: TextIO = open(file_path, 'r')
            self.stop_words = file.readlines()
            file.close()
        except OSError as err:
            print(err)
            self.stop_words = []

    def _load_token_classes(self, config: SearchConfig) -> None:
        try:
            with open(config.classes_file, 'r') as cf:
                classes: List[str] = re.sub('\n+', '', cf.read()).split(';')[:-1]

            for cls in classes:
                line: str = re.sub("[\\s\t]+", ' ', cls)
                for word in line.split():
                    if config.need_text_processing:
                        word = self._process_text(word)
                    self.token_id[word] = self.next_id

                self.next_id += 1

        except OSError as err:
            print(err)

    def _set_token_id(self, token: Token, need_processing: bool) -> None:
        if need_processing:
            token.processed = self._process_text(token.text)
        else:
            token.processed = token.text

        if token.processed not in self.token_id:
            self.token_id[token.processed] = self.next_id
            self.next_id += 1

        token.id = self.token_id[token.processed]

    def _process_text(self, text: str) -> str:
        processed: str = text.lower()
        processed = self.lemmatizer.lemmatize(processed)
        processed = self.stemmer.stem(processed)
        return processed
