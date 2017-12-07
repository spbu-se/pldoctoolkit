from typing import Iterator, List, Set

from nltk.tokenize import sent_tokenize as nltk_sent_tokenize
from nltk.corpus import stopwords
from nltk import word_tokenize as nltk_word_tokenize
from nltk.util import trigrams  # skipgrams(_, n, k); n - deg, k - skip dist

import re


# This can be varied
language = 'english'.lower()
#language = 'russian'.lower()
removeStops = True  # `= set()` for not removing stopwords
puncts = set('.,!?')
default_encodings = ["utf-8", "cp1251"]


# language dispatch
sent_tokenize = lambda text: nltk_sent_tokenize(text, language)
word_tokenize = lambda text: nltk_word_tokenize(text, language)
stopwords = set(stopwords.words(language)) if removeStops else set()
if language == 'russian':
    from nltk.stem.snowball import RussianStemmer as Stemmer
else:
    from nltk.stem.snowball import EnglishStemmer as Stemmer


# Remove unnecessary tokens
def remove_sth(seq: Iterator[str], sth: Set[str]) -> Iterator[str]:
    """ Generic function for removal """
    return filter(lambda x: x not in sth, seq)


def remove_puncts(seq: Iterator[str]) -> Iterator[str]:
    return remove_sth(seq, puncts)


def remove_stops(seq: Iterator[str]) -> Iterator[str]:
    return remove_sth(seq, stopwords)


def wordsToStemmed(sent: Iterator[str]) -> List[str]:
    return [Sentence.stemmer.stem(word) for word in sent]


# Kernel classes
class Sentence:
    stemmer = Stemmer()

    def __init__(self, startIndex: int, endIndex: int, sent: str, start: int, end: int):
        self.startIndex = startIndex
        self.endIndex = endIndex
        self.sent = sent
        self.words = self.sentToWords()
        self.nGrams = list(trigrams(self.words))
        self.start = start
        self.end = end

    def sentToWords(self) -> List[str]:
        # FIXME: remove_stops . remove_puncts ~> remove_sth(_, stops | puncts)
        return wordsToStemmed(
            remove_stops(
                remove_puncts(
                    word_tokenize(self.sent))))


class Text:
    def __init__(self, filename: str):
        self.encoding = None
        self.sents = list(self.fileToSents(filename))

    def fileToSents(self, filename: str) -> List[str]:
        def decode(sth: bytes, codings: List[str] = default_encodings) -> str:
            for coding in codings:
                try:
                    self.encoding = coding
                    return sth.decode(encoding=coding)
                except UnicodeDecodeError:
                    pass
            raise UnicodeDecodeError

        with open(filename, mode='rb') as file:
            text = decode(file.read()).replace('\n', ' ')
            # text = re.sub("\s+", ' ', text)  # "hi     man" ~> "hi man"
            sents = sent_tokenize(text)
            index = 0
            for (num, sent) in enumerate(sents):
                index = text.find(sent, index)
                yield Sentence(num, num, re.sub("\s+", ' ', sent), index, index + len(sent))