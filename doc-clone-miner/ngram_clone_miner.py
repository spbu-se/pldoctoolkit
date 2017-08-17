import clones
import util
from collections import defaultdict

def mine_clones(infile: clones.InputFile) -> 'list[clones.FuzzyCloneGroup]':
    words, offsets = util.text_to_tokens_offsets(infile.text)
    groupcount = 0

    def j(l: 'list[str]') -> str:
        return ' '.join(l)

    def find_N_words_groups(n: int) -> 'list[clones.FuzzyCloneGroup]':
        nonlocal groupcount
        grp_offsets = defaultdict(lambda : [])
        for o in range(len(words) - n + 1):
            grp_offsets[j(words[o:o+n])].append((o, o+n))

        resulting_groups = []

        for k, v in grp_offsets.items():
            if len(v) >= 2:  # it's a clone group
                groupcount += 1
                fclns = []
                fclntexts = []
                fclnwords = []
                for c in v:
                    bo, eo = offsets[c[0]]
                    fclns.append((0, bo, eo))
                    fclntexts.append(infile.text[bo, eo])
                    fclnwords.append(j(words[c[0]:c[1]]))

                resulting_groups.append(clones.FuzzyCloneGroup(str(groupcount), fclns, fclntexts, fclnwords))

        return resulting_groups

    all_groups = []
    for nr in range(5, 15):
        all_groups += find_N_words_groups(nr)

    return all_groups

def perform(input_file_name: str):
    clones.write_reformatted_sources = False
    clones.checkmarkup = False
    clones.only_generate_for_ui = True

    inputfile = clones.InputFile(input_file_name)
    clones.inputfiles = [inputfile] # to get texts and ratios properly

    fgrps = mine_clones(inputfile)
    clones.initdata([inputfile], fgrps)
