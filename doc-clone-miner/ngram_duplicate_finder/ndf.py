from simpleAPI2 import *
import json
import sys

class Group:
    def __init__(self, y, z):
        self.nGrams = y
        self.sents = z


def intersect(sent1, sent2):
    return [s for s in sent1 if s in sent2]


def unite(sent1, sent2):
    return Sentence(sent1.startIndex, sent2.endIndex, sent1.sent + " " + sent2.sent, sent1.start, sent2.end)


def add_sent(group, sentence, use, idx):
    use[idx] = True
    group.nGrams += sentence.nGrams
    group.sents.append(sentence)


def get_overlap(group, sentence):
    cur_intersect = intersect(sentence.nGrams, group.nGrams)
    cur_overlap = len(cur_intersect) / len(sentence.nGrams)
    return cur_overlap


def set_bool(group, val, use):
    for sentence in group.sents:
        set_sent_bool(sentence, val, use)


def set_sent_bool(sentence, val, use):
    for k in range(sentence.startIndex, sentence.endIndex + 1):
        use[k] = val


name = sys.argv[1]

text = Text(name)
sents = text.sents
classes = []
was = [False for i in range(len(sents))]

for (i, curSent) in enumerate(sents):
    print("i now is: " + str(i) + " out of " + str(len(sents)))
    if not was[i]:
        was[i] = True

        curClass = Group([], [])

        for j in range(i, len(sents)):
            newSent = sents[j]
            if len(newSent.nGrams) == 0:
                continue

            if len(curClass.nGrams) == 0 or get_overlap(curClass, newSent) > 0.5:
                add_sent(curClass, newSent, was, j)

        while len(curClass.sents) > 1:
            newClass = Group([], [])

            for sent in curClass.sents:
                index = sent.endIndex + 1
                if not (index == len(sents) or was[index]):
                    newSent = unite(sent, sents[index])

                    if len(newClass.nGrams) == 0 or get_overlap(newClass, newSent) > 0.5:
                        add_sent(newClass, newSent, was, index)
                        continue

                set_sent_bool(sent, False, was)

            if len(newClass.sents) < 2:
                set_bool(newClass, False, was)
                break

            curClass = newClass

        classes.append(curClass)
        set_bool(curClass, True, was)

cur = 0
jsonArr = []
with open(name + " result.txt", "w", encoding=text.encoding) as file:
    for curClass in classes:
        # print("print")
        if len(curClass.sents) < 2:
            continue
        cur += 1
        file.write("========================= CLASS #%d =============================\n" % cur)
        file.write('\n'.join(
            ["(%d) <%d> {%d} [%d]: %s" % (sent.startIndex, sent.endIndex, sent.start, sent.end, sent.sent) for sent in
             curClass.sents]))
        file.write("\n*****************************************************************\n")

        curJson = {'group_id': cur}
        ar = []
        for sent in curClass.sents:
            data = {'start_index': sent.start, 'end_index': sent.end, 'text': sent.sent}
            ar.append(data)
        curJson['duplicates'] = ar

        jsonArr.append(curJson)

jsonRes = {'groups': jsonArr}
js = json.dumps(jsonRes, ensure_ascii=False)

with open(name + ".groups.json", "w", encoding=text.encoding) as file:
    file.write(js)
