#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import sys
import shutil
import uuid
import re

content: str = None

def new_uid():
    node = 0x_33_34_45_79_34_54
    # return group_uuid.uuid4() -- was used before
    print(str(uuid.uuid1(node=node)))


def input_uid(prompt):
    u = None
    while u == None:
        try:
            nu = input(prompt)
            u = uuid.UUID(nu)
        except Exception as e:
            print("Wrong UUID?.. May be try again?.. Error:", e)
    return str(u)


def open_marker(gid):
    return f"<!-- ACCEPT >=> {gid} -->"

def replace_nth(text: str, existing: str, desired: str, n: int):
    where = text.find(existing)
    if where < 0:
        return text
    for _ in range(n):
        where = text.find(existing, where + 1)
        if where < 0:
            return text

    return text[:where] + desired + text[where + len(existing):]

def close_marker(gid):
    return f"<!-- {gid} <=< ACCEPT -->"


def delete_grp(text: str, grp_id: str):
    return text.replace(open_marker(grp_id), '').replace(close_marker(grp_id), '')

def delete_dup(text: str, grp_id: str, index: int):
    text = replace_nth(text, open_marker(grp_id),  '', index)
    text = replace_nth(text, close_marker(grp_id), '', index)
    return text

def interactive_delete_grp():
    global content
    grp_id = input_uid("Group ID > ")
    content = delete_grp(content, grp_id)

def rename_grp(text: str, old_id: str, new_id: str):
    return text \
        .replace(open_marker(old_id), open_marker(new_id)) \
        .replace(close_marker(old_id), close_marker(new_id))

def interactive_rename_grp():
    global content
    egrp_id = input_uid("Existing group ID > ")
    ngrp_id = input_uid("New group ID > ")
    content = rename_grp(content, egrp_id, ngrp_id)


_uuid_re_text = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}"
_open_marker_re = re.compile("<!-- (%s) <=< ACCEPT -->" % (_uuid_re_text,))
def list_ids():
    opens = sorted(set(_open_marker_re.findall(content)))
    for opn in opens:
        print(opn)

def _p_load_file(filename: str):
    global content
    with open(filename, 'r', encoding='utf-8') as inf: content = inf.read()

def _p_save_file(filename: str):
    global content
    with open(filename, 'w', encoding='utf-8') as ouf: ouf.write(content)

def save_file():
    global content
    shutil.copyfile(sys.argv[1], sys.argv[1] + '.bak')
    _p_save_file(sys.argv[1])

def wtf():
    print("Invalid choice")


def goodbye():
    print("Goodbye")
    sys.exit(0)


menu = [
    goodbye,  # 0
    interactive_delete_grp,  # 1
    new_uid, # 2
    interactive_rename_grp, # 3
    list_ids,  # 4
    wtf,  # 5
    wtf,  # 6
    wtf,  # 7
    wtf,  # 8
    save_file, # 9
]

def p_delete_group(filename: str, group_id: str):
    global content
    _p_load_file(filename)
    content = delete_grp(content, group_id)
    _p_save_file(filename)

def p_delete_dup(filename: str, group_id: str, index: int):
    global content
    _p_load_file(filename)
    content = delete_dup(content, group_id, index)
    _p_save_file(filename)

if __name__ == '__main__':
    _p_load_file(sys.argv[1])

    while True:
        print("Hint: you may try to open http://127.0.0.1:49999/ in browser while running DupFinder interactively")
        print()
        print("Options:")
        print("1. Delete group")
        print("2. Generate new id")
        print("3. Rename group")
        print("4. List group IDs")
        print("9. Save file")
        print("0. Exit")

        choice = int(input("Your choice > "))
        menu[choice]()
