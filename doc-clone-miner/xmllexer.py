#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Simple ad-hoc XML lexer based on pygments lexer.
"""

import enum
import bisect
import logging
import pygments.token as ptok
from pygments.lexers import XmlLexer

#  TODO change this to Python 3.5+ enum when porting to PyQt 5.7+
@enum.unique
class IntervalType(enum.IntEnum):
    general = 0
    opentag = 1
    closetag = 2
    emptytag = 3
    comment = 4  # only achieved with additional analysis


class XmlInterval(object):
    def __init__(self, int_type, offs, srepr, name=None, srcindex=None):
        self.int_type = int_type
        self.offs = offs
        self.srepr = srepr
        self.srcindex = srcindex # 0-based index among all intervals of source file to find what is before and after
        self.name = name
        self.end = offs + len(srepr)  # non iclusive, e.g. interval = characters [begin, end)

    def __len__(self):
        return self.end - self.offs

    def __repr__(self):
        return "#%s [%d-%d): %s `%s' %s" % (
            str(self.srcindex) if self.srcindex is not None else '?',
            self.offs, self.end, str(self.int_type), self.name if self.name else '', repr(self.srepr))

    def create_opposite_tag(self):
        'Returns "opposite" tag, assuming CDATA markers being tags too'
        # sorce index will be None as those are new intervals to insert into the text
        if self.int_type == IntervalType.opentag:
            srepr = "]]>" if self.name == "<CDATA>" else "</" + self.name + ">"
            return XmlInterval(IntervalType.closetag, -1, srepr, self.name)

        elif self.int_type == IntervalType.closetag:
            srepr = "<![CDATA[" if self.name == "<CDATA>" else "<" + self.name + ">"
            return XmlInterval(IntervalType.opentag, -1, srepr, self.name)

        else:
            return XmlInterval(IntervalType.general -1, "", self.name)

class LexException(Exception):
    pass


class _RejoinError(Exception):
    pass

def _rejoin(lexemes):
    inopentag = False
    tagname = ""
    intoffs = 0
    intrepr = ""
    curic = -1
    def nextic():
        nonlocal curic
        curic += 1
        return curic
    for offset, kind, srepr in lexemes:
        # begin of open tag or empty tag
        if not inopentag and kind == ptok.Token.Name.Tag and srepr.startswith("<") and not srepr.startswith(
                "</") and not srepr.startswith("<!"):
            inopentag = True
            tagname = srepr[1:]
            intoffs = offset
            intrepr = srepr

        # end of open tag or empty tag
        elif inopentag:
            intrepr += srepr
            if kind == ptok.Token.Name.Tag:
                inopentag = False
                if srepr == ">":
                    yield XmlInterval(IntervalType.opentag, intoffs, intrepr, tagname, nextic())
                elif srepr == "/>":
                    yield XmlInterval(IntervalType.emptytag, intoffs, intrepr, tagname, nextic())
                else:
                    raise LexException("Opened tag ends with: " + srepr)

        # closing tag
        elif not inopentag and kind == ptok.Token.Name.Tag and srepr.startswith("</"):
            yield XmlInterval(IntervalType.closetag, offset, srepr, srepr[2:-1], nextic())

        # CDATA. CDATA braces will be threated as open and close tags
        # so 3 tags for this situation!!!
        elif (not inopentag and kind == ptok.Token.Comment.Preproc and
              srepr.startswith("<![CDATA[") and srepr.endswith("]]>")):
            yield XmlInterval(IntervalType.opentag, offset, "<![CDATA[", "<CDATA>", nextic())
            lo = len("<![CDATA[")
            lc = len("]]>")
            yield XmlInterval(IntervalType.general,
                              offset + lo,
                              srepr[lo : -lc],
                              None,
                              nextic()
                              )
            yield XmlInterval(IntervalType.closetag, offset + len(srepr) - lc, "]]>", "<CDATA>", nextic())

        # error
        elif kind == ptok.Token.Error:
            raise _RejoinError("pygments got error (probably on CDATA)")

        # anything
        else:
            scdata = srepr.startswith("<![CDATA[")
            ecdata = srepr.endswith("]]>")
            if (scdata or ecdata) and (scdata != ecdata):
                raise _RejoinError("pygments has threated broken CDATA as usual markup")
            else:
                yield XmlInterval(IntervalType.general, offset, srepr, None, nextic())


def lex(xmlstring):
    """
    LEXs XML string.
    Allows broken CDATA (pygments does not)
    :param xmlstring:
    :return: [XmlInterval]
    """
    lexer = XmlLexer()
    lokens = []
    try:
        tokens = lexer.get_tokens_unprocessed(xmlstring)
        return list(_rejoin(tokens))
    except _RejoinError as re:
        # probably CDATA, will check in depth; hope no such pieces in attributes
        lc = xmlstring.find("]]>")
        lo = xmlstring.find("<![CDATA[")
        rc = xmlstring.rfind("]]>")
        ro = xmlstring.rfind("<![CDATA[")

        if lc >= 0 and (lo == -1 or lo > lc):
            # missing open CDATA
            return lex("<![CDATA[" + xmlstring)[1:]
        elif ro >= 0 and (rc == -1 or rc < ro):
            # missing close CDATA
            return lex(xmlstring + "]]>")[:-1]
    except Exception as e:
        logging.fatal("XML lexing:" + repr(e))

_offs_cache = dict()

def find_covered_intervals(offset, length, all_intervals):
    """returns: [intervals], breaks_first, breaks_last"""

    if not _offs_cache.get(id(all_intervals)):
        # This comprehension takes really long => caching. Thanks to stupid bisect which all it is for.
        _offs_cache[id(all_intervals)] = [i.offs for i in all_intervals]

    all_offs = _offs_cache[id(all_intervals)]
    i1i = bisect.bisect(all_offs, offset) - 1
    i2i = bisect.bisect(all_offs, offset + length - 1) - 1

    i1 = all_intervals[i1i]
    i2 = all_intervals[i2i]

    return all_intervals[i1i:i2i + 1], i1.offs < offset, offset + length < i2.end


def get_texts_and_markups(offset, length, all_intervals):
    """
    :param offset: start character
    :param length: how many
    :param all_intervals: all lexed intervals
    :return: pairs of ("text", IntervalType)
    """
    covered, broken_1st, broken_last = find_covered_intervals(offset, length, all_intervals)
    # broken 1st and last intervals are ok because we filter them as tags before
    end = offset + length
    result = []
    for ti in covered:
        sr = ti.srepr
        if ti.end > end:
            sr = sr[:end - ti.end]
        if ti.offs < offset:
            sr = sr[offset - ti.offs:]
        result.append((sr, ti.int_type))
    return result


def get_plain_texts(offset, length, all_intervals):
    return [rpr for rpr, typ in get_texts_and_markups(offset, length, all_intervals) if typ == IntervalType.general]

def separate_comments(intervals):
    inside_comment = False
    current_comment = ""
    current_comment_offs = 0
    idx = 0
    for i in intervals:
        if not inside_comment:
            if i.int_type == IntervalType.general and not i.name and i.srepr == '<!--':
                current_comment = i.srepr
                current_comment_offs = i.offs
                inside_comment = True
            else:
                yield XmlInterval(i.int_type, i.offs, i.srepr, i.name, idx)
                idx += 1
        else:  # inside comment
            if i.int_type == IntervalType.general and not i.name and i.srepr == '-->':
                current_comment += i.srepr
                inside_comment = False
                yield XmlInterval(IntervalType.comment, current_comment_offs, current_comment, None, idx)
                idx += 1
                current_comment_offs = 0
            else:
                current_comment += i.srepr

# just for testing purposes, TODO: remove it completely
if __name__ == '__main__':
    lexer = XmlLexer()
    s = ''

    # with open("tmpFile4CloneFinding.txt", encoding='utf-8') as f: s = f.read()
    # s = '<tag><![CDATA[cdata with <tag></tag> here]]></tag>'
    # s = '<![CDATA[<t cdata with <tag></tag> here]]></tag>'
    # s = '''very broken]> ]]><![CDATA[<CDATA example'''
    s = '''<programlisting language="xml"><![CDATA[<bean class="org.springframework.amqp.rabbit.core.RabbitTemplate">
    <property name="connectionFactory" ref="rabbitConnectionFactory"/>
    <property name="messageConverter">
        <bean class="org.springframework.amqp.support.'''

    ints = lex(s)

    with open("out.txt", 'w+', encoding='utf-8') as outf:
        for t in ints:
            print(repr(t), file=outf)

    cints, bf, bl = find_covered_intervals(1, 41, ints)
    print(bf, bl)
    for i in cints:
        print(repr(i))


    s = """<xml> <t>  </t><!-- comment1 -->
    <!-- comment2 <t></t> -->
    </xml>"""
    ints = lex(s)
    for i in ints:
        print(repr(i))
    ints = separate_comments(ints)
    for i in ints:
        print(repr(i))
