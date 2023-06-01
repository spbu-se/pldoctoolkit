import string
import hashlib
from typing import List, Dict

from TextDuplicateSearch.TextProcessing.Token import Token


class Hashing:
    _signature_mapping: Dict[str, int] = {ch: 1 << (8 - (ord(ch) - ord('a')) // 3) for ch in string.ascii_lowercase[:24]}
    _hash_size: int = 32

    @staticmethod
    def signature_hash_func(tokens: List[Token]) -> int:
        result: int = 0
        for token in tokens:
            if token.processed[0] in Hashing._signature_mapping:
                result = result | Hashing._signature_mapping[token.processed[0]]
            else:
                result = result | 1

        return result

    @staticmethod
    def simhash_func(tokens: List[Token]) -> int:
        truncate_mask: int = 2 ** Hashing._hash_size - 1
        simhash: List[int] = [0] * Hashing._hash_size

        ptr: int
        features: List[int] = [int(hashlib.md5(token.processed.encode('utf-8')).hexdigest(), 16) & truncate_mask for token in tokens]
        for feature in features:
            ptr = 2 ** (Hashing._hash_size - 1)
            idx: int = Hashing._hash_size - 1
            while ptr > 0:
                if feature & ptr != 0:
                    simhash[idx] += 1
                else:
                    simhash[idx] -= 1

                idx -= 1
                ptr >>= 1

        simhash = [1 if val > 0 else 0 for val in simhash]
        result: int = 0
        ptr = 2 ** (Hashing._hash_size - 1)
        for val in simhash:
            if val == 1:
                result += ptr

            ptr >>= 1

        return result

    @staticmethod
    def get_hash_diff(hash_a: int, hash_b: int) -> int:
        return bin(hash_a ^ hash_b).count('1')
