import pattern_near_duplicate_search as pnds
import csv
import time
import argparse

def main():
    bassett = 0.15
    apr = argparse.ArgumentParser()
    apr.add_argument('--document', type=str)
    apr.add_argument('--pattern', type=str)
    apr.add_argument('--similarity', type=float, default=1 / (1 + bassett))
    apr.add_argument('--optimize-size', type=bool, default=True)
    apr.add_argument('--unify-whitespaces', type=bool, default=True)
    args = apr.parse_args()

    with open(args.document, encoding='utf-8') as docfile:
        doc_text = docfile.read()

    # performance
    similarity = str(args.similarity)
    doc_name = str(args.document).split("\\")[-1].replace(".pxml", "")
    doc_size = len(doc_text)
    pattern_size = len(str(args.pattern))
    filename = str(similarity.replace(".", ""))
    t1 = time.time()

    # candidatess = get_fuzzy_match_areas(doc_text, args.pattern, args.similarity)
    # fit = fit_candidates(doc_text, args.pattern, args.similarity, candidatess)
    # nipeaks = remove_redundant_candidates(fit)

    nipeaks = pnds.search(doc_text, args.pattern, args.similarity, optimize_size=args.optimize_size,
                          unify_whitespaces=args.unify_whitespaces)

    t2 = int(time.time() - t1)

    for peak in nipeaks:
        print(repr(peak), doc_text[peak[0]:peak[1]])

    with open(file=filename + ".txt", mode='a', encoding='utf-8') as res:
        res.write("\nPATTERN: " + str(args.pattern))
        for peak in nipeaks:
            res.write("\n" + str(repr(peak)) + " " + str(doc_text[peak[0]:peak[1]]))

    with open(filename + ".csv", 'a', encoding='utf-8') as scsv:
        wtr = csv.writer(scsv, lineterminator='\n')
        wtr.writerow([doc_size, pattern_size, len(nipeaks), similarity, t2, doc_name])

    for peak in nipeaks:
        print(repr(peak), doc_text[peak[0]:peak[1]])

if __name__ == '__main__':
    main()