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


def close_marker(gid):
    return f"<!-- {gid} <=< ACCEPT -->"


def delete_grp():
    global content
    grp_id = input_uid("Group ID > ")
    content = content.replace(open_marker(grp_id), '').replace(close_marker(grp_id), '')


def rename_grp():
    global content
    egrp_id = input_uid("Existing group ID > ")
    ngrp_id = input_uid("New group ID > ")
    content = content\
        .replace( open_marker(egrp_id),  open_marker(ngrp_id))\
        .replace(close_marker(egrp_id), close_marker(ngrp_id))


_uuid_re_text = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}"
_open_marker_re = re.compile("<!-- (%s) <=< ACCEPT -->" % (_uuid_re_text,))
def list_ids():
    opens = sorted(set(_open_marker_re.findall(content)))
    for opn in opens:
        print(opn)


def save_file():
    global content
    shutil.copyfile(sys.argv[1], sys.argv[1] + '.bak')
    with open(sys.argv[1], 'w', encoding='utf-8') as ouf: ouf.write(content)


def wtf():
    print("Invalid choice")


def goodbye():
    print("Goodbye")
    sys.exit(0)


menu = [
    goodbye,  # 0
    delete_grp,  # 1
    new_uid, # 2
    rename_grp, # 3
    list_ids,  # 4
    wtf,  # 5
    wtf,  # 6
    wtf,  # 7
    wtf,  # 8
    save_file, # 9
]


if __name__ == '__main__':
    with open(sys.argv[1], 'r', encoding='utf-8') as inf: content = inf.read()

    while True:
        print("Options:")
        print("1. Delete group")
        print("2. Generate new id")
        print("3. Rename group")
        print("4. List group IDs")
        print("9. Save file")
        print("0. Exit")

        choice = int(input("Your choice > "))
        menu[choice]()
