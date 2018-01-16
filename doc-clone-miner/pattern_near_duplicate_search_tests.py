#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import unittest
from textwrap import dedent
import pattern_near_duplicate_search as pnds

class TestStringMethods(unittest.TestCase):
    def __init__(self, t):
        super().__init__(t)  # what is t here? =)

        self.lipsum = dedent("""
        Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's
        standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make
        a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting,
        remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing
        Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions
        of Lorem Ipsum.
        """)

        self.p = dedent("""
        @exception SWTException <ul>
        <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
        <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
        </ul>
        """)
        self.d = self.lipsum + self.p + self.lipsum + self.p + self.lipsum

        self.p = self.p.strip()
        self.sim = 0.77

    def test_009_bench_pattern_time_LKD(self):
        import time
        with open("tests/documentation/Heat_Map/4_first/Linux_Kernel/Linux_Kernel_Documentation.cxml", encoding='utf-8') as df: d = df.read()
        p = dedent(
            """
            This documentation is distributed in the hope that it will be
            useful, but WITHOUT ANY WARRANTY; without even the implied
            warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
            See the GNU General Public License for more details.
            
            You should have received a copy of the GNU General Public
            License along with this documentation; if not, write to the Free
            Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
            MA 02111-1307 USA
            
            For more details see the file COPYING in the source
            distribution of Linux.
            """).strip()
        l = []
        for kp in range(55, 101, 5):
            k = kp / 100.0
            t1 = time.time()
            fnds = pnds.search(d, p, k)
            t2 = time.time()
            r = "%0.2f;%d;%d " %(k, t2-t1, len(fnds))
            l.append(r)
            print(r)

        print("k;t;|R|")
        for e in l:
            print(e)

    def test_010_bench_pattern_time_Eclipse(self):
        import time
        with open("tests/documentation/Heat_Map/References/Eclipse_SWT/Eclipse.cxml", encoding='utf-8') as df: d = df.read()
        # p = "@exception SWTException" # opt: ? -> 1255
        # p = "@exception SWTException <ul> <li>ERROR_WIDGET_DISPOSED - if the receiver has been"  # opt: 10685 -> 1094
        # p = "<li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver" # opt: 13056 -> 1012
        p = dedent(
            """
            @exception SWTException <ul>
            <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
            <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
            </ul>
            """).strip()
        # k; t; |R|
        # 0.55;3412;1229 --- 2809 =)
        l = []
        for kp in range(55, 101, 5):
            k = kp / 100.0
            t1 = time.time()
            fnds = pnds.search(d, p, k)
            t2 = time.time()
            r = "%0.2f;%d;%d " %(k, t2-t1, len(fnds))
            l.append(r)
            print(r)


        print("k;t;|R|")
        for e in l:
            print(e)



    def test_020_bench_pattern_time(self):
        import time
        with open("tests/documentation/Heat_Map/References/PostgreSQL_9.6.1_SQL_Reference/PostgreSQL_9.6.1_SQL_Reference.cxml", encoding='utf-8') as df: d = df.read()
        p = "you must also be a direct or indirect member of the new owning role, and that role must have CREATE privilege on the table's schema."
        # k=0.57, t=31, |R|=17
        # k=0.62, t=27, |R|=17
        # k=0.67, t=27, |R|=17
        # k=0.72, t=27, |R|=17
        # k=0.77, t=27, |R|=17
        # k=0.82, t=27, |R|=17
        # k=0.87, t=28, |R|=17
        # k=0.92, t=27, |R|=17
        # k=0.97, t=27, |R|=17
        # p = "you must also be a direct or indirect member of the new owning role"
        # k=0.57, t=20, |R|=19
        # k=0.62, t=20, |R|=19
        # k=0.67, t=20, |R|=19
        # k=0.72, t=20, |R|=19
        # k=0.77, t=20, |R|=19
        # k=0.82, t=20, |R|=19
        # k=0.87, t=20, |R|=19
        # k=0.92, t=20, |R|=19
        # k=0.97, t=20, |R|=19

        l = []
        for kp in range(57, 101, 5):
            k = kp / 100.0
            t1 = time.time()
            fnds = pnds.search(d, p, k, unify_whitespaces=True)
            t2 = time.time()
            l.append("k=%0.2f, t=%d, |R|=%d " %(k, t2-t1, len(fnds)))

        for e in l:
            print(e)

    def test_1_eclipse_max(self):
        with open("tests/documentation/Heat_Map/References/Eclipse_SWT/Eclipse.cxml", encoding='utf-8') as df: d = df.read()
        # p = "@exception SWTException" # opt: ? -> 1255
        # p = "@exception SWTException <ul> <li>ERROR_WIDGET_DISPOSED - if the receiver has been"  # opt: 10685 -> 1094
        p = "<li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver" # opt: 13056 -> 1012
        fnds = pnds.search(d, p, 0.87)
        print(len(fnds))



    def test_1_psql_fitting(self):
        p = dedent(
            """
            To alter the owner, you
            must also be a direct or indirect member of the new owning role, and
            that role must have CREATE privilege on the table's schema. (These
            restrictions enforce that altering the owner doesn't do anything you
            couldn't do by dropping and recreating the table. However, a superuser
            can alter ownership of any table anyway.)
            """).strip()
        with open("tests/documentation/Heat_Map/References/PostgreSQL_9.6.1_SQL_Reference/PostgreSQL_9.6.1_SQL_Reference.cxml", encoding='utf-8') as df: d = df.read()
        fnds = pnds.search(d, p, 0.77, unify_whitespaces=True)
        print(len(fnds))
        for fb,fe in fnds:
            print("<<<" + d[fb:fe] + ">>>")


    def test_a_smoke_search_spl(self):
        d = "w1 w2 w3 w4 w5 \n A B C D E1 F G H I w6 w7 \n w8 w9 A B C \n D E2 F G H I \n w10 w11 w12 w13"
        p = "A B C D E1 F G H I"
        fnds = pnds.search(d, p, self.sim)
        for fb,fe in fnds:
            print("<<<" + d[fb:fe] + ">>>")

    def test_smoke_fit_word_borders(self):
        pnds.get_fit_word_borders(self.d)

    def test_smoke_non_opt_search(self):
        pnds.search(self.d, self.p, self.sim, optimize_size=False)

    def test_smoke_search_p(self):
        fnds = pnds.search(self.d, self.p, self.sim)
        for fb,fe in fnds:
            print("<<<" + self.d[fb:fe] + ">>>")

if __name__ == '__main__':
    unittest.main()
