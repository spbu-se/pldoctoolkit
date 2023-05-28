from typing import List, TextIO

from TextDuplicateSearch.DataModels.DuplicateCase import DuplicateCase


class DuplicateCollection:
    def __init__(self) -> None:
        self.cases: List[DuplicateCase] = []
        # self.tokens: List[Token] = token_array
        
    def add_case(self, duplicate_case: DuplicateCase) -> None:
        self.cases.append(duplicate_case)

    # removes cases with zero or one text fragment
    def filter_irrelevant(self) -> None:
        self.cases = list(filter(lambda case: len(case.text_fragments) > 1, self.cases))

    def output(self, file_name: str) -> None:
        try:
            output_file: TextIO = open(file_name, "w", encoding='utf-8')
            output_file.write(self.__repr__())
            output_file.close()
        except OSError as err:
            print(err)

    def __repr__(self) -> str:
        result: str = ''

        for ID, case in enumerate(self.cases):
            case_string = f'{ID};{case.length()};{case.count}\n'
            for duplicate in case.text_fragments:
                case_string += f'0:{duplicate.start.line}.{duplicate.start.col}-{duplicate.end.line}.{duplicate.end.col}\n'
            
            case_string += '\n'
            result += case_string
                
        return result

    def pretty_print(self) -> None:
        for i in range(len(self.cases)):
            print(f"Case #{i + 1}:")
            for j in range(len(self.cases[i].text_fragments)):
                print(self.cases[i].text_fragments[j])
                if j != len(self.cases[i].text_fragments) - 1:
                    print('|||')

            print('-------------------------------')
