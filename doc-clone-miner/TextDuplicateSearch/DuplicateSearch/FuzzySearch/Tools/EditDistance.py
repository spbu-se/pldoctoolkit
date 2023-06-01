from typing import Dict, List, Tuple
import math

from TextDuplicateSearch.DataModels.TextFragment import TextFragment


class EditDistance:
    def __init__(self, cost: int):
        self.edit_costs: Dict[str, float] = {
            "delete": cost,
            "insert": cost,
            "substitute": cost,
            "transpose": cost
        }

    def define_costs(self, *, delete: float = -1, insert: float = -1, substitute: float = -1,
                     transpose: float = -1) -> None:
        if delete != -1:
            self.edit_costs["delete"] = delete
        if insert != -1:
            self.edit_costs["insert"] = insert
        if substitute != -1:
            self.edit_costs["substitute"] = substitute
        if transpose != -1:
            self.edit_costs["transpose"] = transpose

    def calculate(self, frg_a: TextFragment, frg_b: TextFragment, threshold: float = 0) -> float:
        return 0


# Calculates Levenshtein distance:
#   - edit operations: deletions, insertions, substitutions
#   - complexity: O(t * min(M, N)), where M,N - fragment lengths, t - threshold
class UkkonenAsm(EditDistance):
    def __init__(self, cost: int):
        super().__init__(cost)

    def calculate(self, frg_a: TextFragment, frg_b: TextFragment, threshold: float = -1) -> float:
        if frg_a.length == 0 and frg_b.length == 0:
            return 0

        swapped: bool = False
        if frg_a.length > frg_b.length:
            frg_a, frg_b = frg_b, frg_a
            self.define_costs(insert=self.edit_costs["delete"],
                              delete=self.edit_costs["insert"])
            swapped = True

        if threshold == -1:
            threshold = frg_a.length * self.edit_costs["delete"] + \
                        frg_b.length * self.edit_costs["insert"]

        c_min: float = min(self.edit_costs["delete"], self.edit_costs["insert"])
        m: int = frg_a.length
        n: int = frg_b.length
        dist: Dict[Tuple[int, int], float] = {}
        p: int = math.floor((threshold / c_min - math.fabs(n - m)) / 2)

        if threshold / c_min < math.fabs(n - m):
            return math.inf

        for i in range(0, m + 1):
            for j in range(max(0, i - p), (min(n, i + (n - m) + p)) + 1):
                val_sub: float = dist[(i - 1, j - 1)] if (i - 1, j - 1) in dist else math.inf
                val_del: float = dist[(i - 1, j)] if (i - 1, j) in dist else math.inf
                val_ins: float = dist[(i, j - 1)] if (i, j - 1) in dist else math.inf

                if i == 0:
                    dist[(i, j)] = j * self.edit_costs["insert"]
                    continue
                elif j == 0:
                    dist[(i, j)] = i * self.edit_costs["delete"]
                    continue
                elif frg_a.tokens[i - 1] != frg_b.tokens[j - 1]:
                    val_sub += self.edit_costs["substitute"]

                dist[(i, j)] = min(val_sub,
                                   val_del + self.edit_costs["delete"],
                                   val_ins + self.edit_costs["insert"])

        if swapped:
            self.define_costs(insert=self.edit_costs["delete"],
                              delete=self.edit_costs["insert"])

        return dist[(m, n)]


# Calculates Damerau-Levenshtein distance:
#   - edit operations: deletions, insertions, substitutions, transposition
#   - complexity: O(M * N), where M,N - fragment lengths
class DamerauLevenshtein(EditDistance):
    def __init__(self, cost: int):
        super().__init__(cost)

    def calculate(self, frg_a: TextFragment, frg_b: TextFragment, threshold: float = 0) -> float:
        dist_a: Dict[int, int] = {}
        n: int = len(frg_a.tokens)
        m: int = len(frg_b.tokens)
        max_dist: int = n + m

        dist: List[List[float]] = [[max_dist for _ in range(m + 2)] for _ in range(n + 2)]
        dist[n][m] = 0
        for i in range(n + 1):
            dist[i][0] = i * self.edit_costs["delete"]
        for j in range(m + 1):
            dist[0][j] = j * self.edit_costs["insert"]

        for i in range(1, n + 1):
            dist_b: int = 0
            for j in range(1, m + 1):
                k: int = dist_a[frg_b.tokens[j - 1].id] if frg_b.tokens[j - 1].id in dist_a else 0
                l: int = dist_b

                sub_cost: float = self.edit_costs["substitute"]
                trans_cost = self.edit_costs["transpose"] + \
                             (i - k - 1) * self.edit_costs["delete"] + \
                             (j - l - 1) * self.edit_costs["insert"]

                if frg_a.tokens[i - 1].id == frg_b.tokens[j - 1].id:
                    dist_b = j
                    sub_cost = 0

                dist[i][j] = min(dist[i - 1][j - 1] + sub_cost,
                                 dist[i][j - 1] + self.edit_costs["insert"],
                                 dist[i - 1][j] + self.edit_costs["delete"],
                                 dist[k - 1][l - 1] + trans_cost)

            dist_a[frg_a.tokens[i - 1].id] = i

        return dist[n][m]
