#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# requires:
# - https://pypi.python.org/pypi/PyContracts -- pip install pycontracts
# - numpy

import logging
import os
import sys
import re
import bisect
import xml.etree.ElementTree as ET
import xml.sax as xs
import xml.sax.handler as xsh
import textwrap
import string
import itertools

import numpy

import verbhtml
import xmllexer
import xmlfixup
import semanticfilter

from abc import ABC, abstractmethod

# seems reasonable
# http://stackoverflow.com/questions/3269434/whats-the-most-efficient-way-to-test-two-integer-ranges-for-overlap
import operator

infty = sys.maxsize
clonegroups = [] # type: List[CloneGroup]
allow2offset = True

def is_overlapping(x1, x2, y1, y2):
    return max(x1, y1) <= min(x2, y2)


def initdata(inputfilesiv=[], clonegroupsiv=[]):
    global inputfiles
    global clonegroups
    global blacklist
    global black_descriptor_list
    global whitelist

    inputfiles = inputfilesiv
    clonegroups = clonegroupsiv


def initoptions(args, logger):
    global minlen
    minlen = 0
    if args.minimalclonelength:
        try:
            minlen = int(args.minimalclonelength)
        except:
            logger.error("Expecting integer minimal clone length")

    global exp_clones
    exp_clones = args.exp_clones == 'yes'

    global mingrppow
    mingrppow = 2
    if args.minimalgrouppower:
        try:
            mingrppow = max(2, args.minimalgrouppower.strip())
        except:
            logger.error("Expecting integer minimal group power")

    global checkmarkup
    checkmarkup = args.checkmarkup == 'yes'

    global shrink_broken_markup
    shrink_broken_markup = args.checkmarkup == 'shrink'

    global maximalvariance
    maximalvariance = int(args.maximalvariance)

    global maxvariantdistance
    maxvariantdistance = 100
    if args.findnearby:
        try:
            maxvariantdistance = int(args.findnearby)
        except:
            logger.note("not a number in -nb <...>")

    global checksemanticspresence
    checksemanticspresence = args.check_semantics_presence != 'no'
    global use_nltk_to_check_semantics
    use_nltk_to_check_semantics = args.check_semantics_presence == 'nltk'

    global cm_inclusiveend
    cm_inclusiveend = args.inclusive_end == 'yes'

    global write_reformatted_sources
    write_reformatted_sources = args.write_reformatted_sources == 'yes'

    global only_generate_for_ui
    only_generate_for_ui = args.only_ui == 'yes'

    global blacklist
    blacklist = set()

    # black descriptor list format is file with textual group descriptors
    # it is portable across CloneMiner settings, run iterations, etc, it only depends on input data
    # so it is named black_descriptor_list.txt and located together with Clones.txt

    global black_descriptor_list
    black_descriptor_list = set()

    global whitelist
    whitelist = set()

    if args.blacklist:
        try:
            with open(args.blacklist) as blf:
                bls = blf.read()
                blss = re.split(',| |\r|\n', bls)
                for idf in blss:
                    idf = idf.strip()
                    if idf != '':
                        try:
                            blacklist.add(int(idf))
                        except:
                            logger.warning("blacklist contains non-integer {{%s}}" % idf)
        except IOError:
            logger.warning("Can't load group ID blacklist form %s" % args.blacklist)

    if args.whitelist:
        try:
            with open(args.whitelist) as blf:
                bls = blf.read()
                blss = re.split(',| |\r|\n', bls)
                for idf in blss:
                    idf = idf.strip()
                    if idf != '':
                        try:
                            whitelist.add(int(idf))
                        except:
                            logger.warning("whitelist contains non-integer {{%s}}" % idf)
        except IOError:
            logger.warning("Can't load group ID whitelist form %s" % args.whitelist)


# TODO: this should be enum (requires py 3.4+) and should be robably removed and replaced with xmllexer
class TextZone(object):
    UFO = 0
    ELEM = 1
    TEXT = 2
    SPACE = 3
    URL = 4

    def __init__(self, mode, start, end, content):
        self.mode = mode  # Algol 68 rulez
        self.start = start
        self.end = end
        self.content = content


class XMLZoneMarker(xsh.ContentHandler):
    def getlc(self):
        ln = self._locator.getLineNumber()
        cn = self._locator.getColumnNumber()
        return ln, cn

    def lc2offs(self, lc):
        l, c = lc
        return self.inputfile.anything2offset((l, c + 1))

    def getoffs(self):
        return self.lc2offs(self.getlc())

    def startElement(self, name, attrs):
        self.stack.append((name, self.getoffs()))

    def endElement(self, name):
        elname, start = self.stack.pop()
        self.zones.append(TextZone(TextZone.ELEM, start, self.getoffs(), name))

    def characters(self, content):
        empty = len(content.strip()) == 0
        offs = self.getoffs()

        # content happens to be fucked up but of correct size, improving it from input file
        end = offs + len(content) - 1
        content = self.inputfile[offs:end]

        zn = TextZone(TextZone.SPACE if empty else TextZone.TEXT, offs, offs + len(content) - 1, content)
        self.zones.append(zn)
        if not empty:
            self.textzones.append(zn)
            self.textzoneoffsets.append(offs)
            self.textzoneends.append(offs + len(content))

    def ignorableWhitespace(self, content):
        offs = self.getoffs()
        self.zones.append(TextZone(TextZone.SPACE, offs, offs + len(content) - 1, content))

    def __init__(self, inputfile):
        self.stack = []
        self.inputfile = inputfile
        self.src = inputfile.text
        self.zones = []
        self.rzones = []
        self.urlre = re.compile('((\\w+)://([^"\'<>,\\s]+))')

        self.textzones = []
        self.textzoneoffsets = []
        self.textzoneends = []

    def discoverURLs(self):
        self.urlzones = []
        matches = re.finditer(self.urlre, self.src)

        for m in matches:
            cnt = m.groups()[0]
            ofs = m.start()
            self.urlzones.append(TextZone(TextZone.URL, ofs, ofs + len(cnt) - 1, cnt))

    def discover(self):
        btx = self.src.encode('utf8')
        xs.parseString(btx, self)

        self.zones.sort(key=lambda xz: xz.start)
        self.rzones = sorted(self.zones, key=lambda xz: xz.end)

        self.discoverURLs()


class InputFile(object):
    def __init__(self, fileName):
        global write_reformatted_sources

        self.elementZones = []
        self.fileName = fileName
        self.text = None
        self.offsets = []
        offset = 0
        with open(fileName, encoding='utf-8') as ifs:
            lines = []
            for line in ifs:
                self.offsets.append(offset)
                # lst = line.rstrip() + '\n' # leave leading blanks + add separator (we do not need \r, so no os.linesep)
                lst = line.replace('\r', '').replace('\n',
                                                     '') + '\n'  # leave leading blanks + add separator (we do not need \r, so no os.linesep)
                lst = lst.replace('\t', '    ')  # Clone Miner is very brave to consider TAB equal to 4 spaces
                offset += len(lst)
                lines.append(lst)
            self.offsets.append(offset)
            self.text = "".join(lines)
        if write_reformatted_sources:
            with open(fileName + ".reformatted", 'w+', encoding='utf-8', newline='\n') as ofs:
                ofs.write(self.text)

        # then calculate XML zones
        marker = XMLZoneMarker(self)

        global checkmarkup
        if checkmarkup: # -cmup no and -cmup shrink do not need this
            marker.discover()
            self.zones, self.rzones = marker.zones, marker.rzones
            self.textzoneoffsets = marker.textzoneoffsets
            self.textzoneends = marker.textzoneends
            self.textzones = marker.textzones
        else:
            marker.discoverURLs()
        self.urlzones = marker.urlzones


        # calculate tag coordinates using pygments lexer (hope correctly)
        self.lexintervals = xmllexer.lex(self.text)

    def __getitem__(self, stst):
        if isinstance(stst, slice): # correct slice
            return self.text[stst.start : stst.stop+1]
        else:
            return self.text[stst]

    def __len__(self):
        return len(self.text)

    def offset2linecol(self, offset):
        line = bisect.bisect_left(self.offsets, offset) - 1
        return (line + 1, offset - self.offsets[line] + 1)

    def _linecol2offset(self, linecol):
        """
        Both from 1
        """
        line, col = linecol
        return self.offsets[line - 1] + col - 1

    def anything2linecol(self, coord):
        return coord if isinstance(coord, tuple) else self.offset2linecol(coord)

    def anything2offset(self, coord):
        assert allow2offset
        return self._linecol2offset(coord) if isinstance(coord, tuple) else coord


class InternalException(Exception):
    pass


class InternalOkBreak(InternalException):
    pass


class CloneGroup(ABC):
    def __init__(self, id):
        self.id = id
        self.instances = None

    def text(self, inst=0):
        global inputfiles
        global clonegroups

        fileno, start, end = self.instances[inst]
        return inputfiles[fileno][start:end]

    def html(self, inst=0, allow_space_wrap=False):
        return "<code>" + verbhtml.escapecode(self.text(inst), allow_space_wrap) + "</code>"

    @property
    def textdescriptor(self):
        """
        :return: textual description in form of fileno:begin-end joined by "," ordered by fileno then by begin offset
        """
        return ",".join(
            ["%d:%d-%d" % inst for inst in sorted(self.instances)]  # sorting should work as described above
        )


    def __hash__(self):  # to add to set
        return hash(self.id) ^ 445051238233  # fast, but not very safe, better to only add CloneGroups to sets


    def __eq__(self, other):
        return type(self) == type(other) and self.id == other.id


    def __ne__(self, other):
        return type(self) != type(other) or self.id != other.id


    def totalsymbols(self):
        return sum(ie - ib + 1 for fn, ib, ie in self.instances)

class FuzzyCloneGroup(CloneGroup):
    def __init__(self, id, clones, clonetexts, clonewords):
        super().__init__(id)
        self.instances = clones
        self.instancetexts = clonetexts
        self.instancewords = clonewords


    def text(self, inst=0):
        return self.instancewords[0]

class ExactCloneGroup(CloneGroup):
    def __init__(self, id, ntokens, instances):
        """
        instances -- [ (filenum, (sl, sc), (el, ec)) ]
        self.instances -- [ (filenum, off1, off2) ]
        """
        global inputfiles
        global clonegroups

        super().__init__(id)

        if ntokens == 0 or len(instances) == 0:
            raise Exception("bad args")
        self.ntokens = ntokens

        self.expanded = False
        self.instances = []
        self.significances = []
        self.cloneindicestodel = set()

        for i in instances:
            ifilen, s, e = i
            ifile = inputfiles[ifilen]
            s, e = ifile.anything2offset(s), ifile.anything2offset(e)
            self.instances.append((ifilen, s, e))
            self.significances.append(int(len(instances) * (e - s + 1) ** 2))  # from Kopin diploma

        self.instances.sort(key=operator.itemgetter(0,1))
        # now instances are sorted by file then by appearance

    def textwithcontext(self, inst=0):
        global inputfiles
        global clonegroups

        fileno, start, end = self.instances[inst]
        length = end - start
        ifl = inputfiles[fileno]
        clength = max(min(length, 50), 20)
        cstart = max(0, start - clength)
        cend = min(len(ifl), end + clength)
        return ifl[cstart: start - 1], ifl[start:end], ifl[end + 1: cend]


    def _inst_distance(inst1, inst2):
        file1, start1, end1 = inst1
        file2, start2, end2 = inst2
        if file1 != file2:
            return infty  # 0+1j # LOL

        if (start1 <= start2 < end1 or start1 < end2 <= end1 or
                        start2 <= start1 < end2 or start2 < end1 <= end2):  # within
            return -1
        else:
            return start2 - end1 if start2 >= end1 else start1 - end2

    def sgn(n):
        if n < 0:
            return -1
        elif n > 0:
            return 1
        else:
            return 0

    def _cg_distance(cg1, cg2):
        if len(cg1.instances) != len(cg2.instances):
            return infty

        global maxvariantdistance
        signum = 0

        borderdist = max(maxvariantdistance, len(cg1.text()) + len(cg2.text()))
        # print("borderdist = %d" % borderdist)

        # sort already by file then by offset (see ExactCloneGroup constructor)
        insts1 = cg1.instances
        insts2 = cg2.instances

        dists = []

        for c1i, c2i in zip(insts1, insts2):
            fn1, c1o, _ = c1i
            fn2, c2o, _ = c2i

            d = ExactCloneGroup._inst_distance(c1i, c2i)
            if d > borderdist:
                return infty

            newsignum = c2o - c1o # no need for sgn here... ExactCloneGroup.sgn(c2o - c1o)  # criteria for file only!!!
            if newsignum * signum < 0:
                return infty  # different order
            else:
                signum = newsignum

            dists.append(d)

        # more seldom case, so checking afterwards
        for c1i in insts1:
            for c2i in insts2:
                if ExactCloneGroup._inst_distance(c1i, c2i) < 0:
                    return infty

        m = max(dists) # known to be <= borderdist

        global maximalvariance
        import numpy

        va = numpy.var(dists)
        # print("variance: %f" % va)
        if va > maximalvariance:
            return infty

        return m

    @staticmethod  # was tooooo complicated for multimethod
    def distance(i1, i2):
        if type(i1) != type(i2):
            raise NotImplemented("Different types distance")
        elif type(i1) == ExactCloneGroup:
            return ExactCloneGroup._cg_distance(i1, i2)
        elif type(i1) == tuple:
            return ExactCloneGroup._inst_distance(i1, i2)
        else:
            raise InternalException("What to do with integers here?..")

    def _plain_texts_from_intervals(self, instance_no=0):
        # detecting on instance[0]
        ifilen, so, e = self.instances[instance_no]
        sl = e - so + 1
        return xmllexer.get_plain_texts(so, sl, inputfiles[ifilen].lexintervals)

    def plain_text(self, instance_no=0):
        return ' '.join(self._plain_texts_from_intervals(instance_no))

    def containsNoSemantic(self):
        # print("No sema in: " + self.text())
        hs = semanticfilter.does_have_semantic(self.plain_text(), use_nltk_to_check_semantics)
        return not hs

    def containsNoText(self):
        # print("No text in: " + self.text())
        return len(''.join([s.strip() for s in self._plain_texts_from_intervals()])) == 0

    def containsNoWords(self):
        if self.containsNoText():
            return True
        elif len(semanticfilter.cleanwords(self.plain_text())) == 0:
            return True
        else:
            return False

    def containsNoText_0(self):
        """
        deprecated version
        """
        global inputfiles
        global clonegroups

        # detecting on instance[0]
        try:
            return self._containsNoText
        except AttributeError:
            self._containsNoText = True
            _, s, e = self.instances[0]
            ifile0 = inputfiles[0]

            def saturate(x, low, hi):
                assert low <= hi
                return max(low, min(x, hi))

            # we can always use bisect_left ast text zones do not intersect
            startinfrontof = bisect.bisect_left(ifile0.textzoneoffsets, s)
            endafter = bisect.bisect_right(ifile0.textzoneends, e)

            s1i = saturate(startinfrontof - 1, 0, len(ifile0.textzoneoffsets))
            e1i = saturate(endafter + 1, 0, len(ifile0.textzoneoffsets))

            for tfi in range(s1i, e1i):
                tzb, tze = ifile0.textzoneoffsets[tfi], ifile0.textzoneends[tfi]
                if is_overlapping(s, e, tzb, tze):
                    self._containsNoText = False
                    break

            # if self._containsNoText:
            # print("only markup {{{%s}}}[%s:%s]" % (ifile0[s:e], str(ifile0.anything2linecol(s)), str(ifile0.anything2linecol(e))))

            return self._containsNoText

    def isLessThanAllowed(self):
        global inputfiles
        global clonegroups

        if len(self.instances) < mingrppow:
            return True
        instlen = 0
        for ifl, s, e in self.instances:
            le = e - s + 1
            instlen += le
            if le < minlen:  # too tolerant
                # text = inputfiles[ifl][s:e].strip()
                # if len(text) < minlen:
                # print(s, e, minlen)
                # print("zoz")
                return True
        score = instlen * (1 + 1 / len(self.instances))
        return False

    def breaks_url(self):
        global inputfiles
        global clonegroups

        # also [0]
        try:
            return self._breaksURL
        except AttributeError:
            self._breaksURL = False
            # TODO: bisect!
            _, s, e = self.instances[0]
            ifile0 = inputfiles[0]
            urlse = [z for z in ifile0.urlzones if z.mode == TextZone.URL]
            for u in urlse:
                if u.start < s <= u.end or u.start <= e < u.end:
                    self._breaksURL = True
                    # print("grp %s breaks %s" % (self.text(), ifile0[u.start: u.end]))
                    break
            return self._breaksURL

    def containsBrokenMarkup(self):
        global inputfiles
        global clonegroups

        rampi = "<fakeroot>";
        rampo = "</fakeroot>"
        for ifn, s, e in self.instances:
            ntext = self.text(ifn)
            try:
                ET.fromstring(rampi + ntext + rampo)
                # ok, here it is wrapped in two 'root' tags and then parsed
                # but it still can be "ble><tr><td></td></tr>"
                # parser allows unscreened ">", how to prohibit it?. 
                # so need to analyze it properly
                # TODO!
                """ too weak now
                text = inputfiles[ifn].text

                fgt = ntext.find('>')
                flt = ntext.find('<')

                if fgt >= 0 and (flt > fgt or flt < 0):
                    # search backward...
                    ci = s
                    while ci > 0:
                        ci -= 1
                        if text[ci] == "<":
                            print("Clone: " + ntext)
                            print("Full:  " + text[ci:e+1])
                        elif text[ci] == ">": # >....> seems to be ok
                            return False
                        else:
                            pass

                else:
                    # ok...
                    # logging.info("Good XML " + ntext)
                    return False
                """
                return False

            except ET.ParseError as pe:
                pass
        # logging.info("Bad XML " + self.text())
        return True

    def isBlacklisted(self):
        global inputfiles
        global clonegroups
        global black_descriptor_list

        # logger.debug("self.id in blacklist: %s" % str(self.id in blacklist))
        if len(black_descriptor_list) > 0:
            return self.textdescriptor in black_descriptor_list
        elif len(whitelist) > 0:
            return self.id not in whitelist
        else:
            return self.id in blacklist

    # filtered by:
    by_breaking_url = 0
    by_no_text = 0
    by_too_short = 0
    by_no_semantic = 0
    by_broken_markup = 0

    def isCorrect(self):
        global inputfiles
        global clonegroups

        if self.isBlacklisted():
            # logger.debug("blacklisted")
            return False

        if self.isLessThanAllowed():
            # logger.debug("group is less than allowed")
            ExactCloneGroup.by_too_short += 1
            return False

        if self.containsNoText():
            # logger.debug("no text in group")
            ExactCloneGroup.by_no_text += 1
            return False

        if checksemanticspresence and self.containsNoSemantic():
            # logger.info("no semantic")
            ExactCloneGroup.by_no_semantic += 1
            return False

        if checkmarkup and self.containsBrokenMarkup():
            # logger.info("broken markup")
            ExactCloneGroup.by_broken_markup += 1
            return False

        if self.breaks_url():  # containsBrokenHref(text):
            # logger.debug("broken url")
            ExactCloneGroup.by_breaking_url += 1
            return False

        return True

    def try_expand_clones(self):
        def alnumu(s):
            return s.isalnum() or s == '_'

        tryexp = 0
        lastsepexp = 0
        mingap = min(len(inputfiles[fn]) - last for (fn, first, last) in self.instances)
        expandedwith = ""
        while True:
            if mingap - tryexp <= 1:
                break

            lastsyms = [inputfiles[fn][last + tryexp + 1] for (fn, first, last) in self.instances]

            sls = set(lastsyms)
            if len(sls) == 1: # all are identical
                slsn = lastsyms[0]
                tryexp += 1
                expandedwith += slsn
                if not alnumu(slsn):
                    lastsepexp = tryexp
                if not slsn.isspace():
                    self.expanded = True
            else:
                # if different symbol is alphanumeric for any instance, then we stopped in the middle of the token
                for c in sls:
                    if alnumu(c):
                        tryexp = lastsepexp
                        if tryexp == 0:
                            self.expanded = False
                break

        if self.expanded:
            self.instances = [(fn, first, last + tryexp) for (fn, first, last) in self.instances]

    def try_shrink_broken_markup(self):
        def shrink_broken_instance(ifilen, i1_stoffs, ilastoffs):
            """
            leave tags unbalanced, but truncate clone instances to complete tags
            """
            ifile = inputfiles[ifilen]
            ilen = ilastoffs - i1_stoffs + 1
            shrinked = xmlfixup.shrink_broken_markup_interval(i1_stoffs, ilen, ifile.lexintervals)
            if shrinked:
                i1_stoffs, ilen = shrinked
                ilastoffs = i1_stoffs + ilen - 1
                return ifilen, i1_stoffs, ilastoffs
            else:
                return None

        newinstances = [shrink_broken_instance(ifn, beg, fin) for ifn, beg, fin in self.instances]
        self.instances = [inst for inst in newinstances if inst is not None]
        return len(self.instances)


def loadinputs(logger):
    global inputfiles
    global clonegroups

    # initilize input files
    with open(os.path.join("Input", "InputFiles.txt")) as ifi:
        for line in ifi:
            sl = line.strip()
            if os.name == "posix" and sl.startswith("z:\\"):
                # then clone miner was ran using wine, if it ever was
                # and posix root was marked as z:\
                sl = sl.replace("z:\\", "/").replace("\\", "/")
            if len(sl):
                inputfiles.append(InputFile(sl))

    if os.path.exists(os.path.join("Output", "black_descriptor_list.txt")):
        with open(os.path.join("Output", "black_descriptor_list.txt")) as blst:
            global black_descriptor_list
            for line in blst:
                sline = line.strip()
                if sline:
                    for segm in sline.split(';'):
                        black_descriptor_list.add(segm.strip())

    # process clones and initialize groups & instances
    with open(os.path.join("Output", "Clones.txt")) as clotf:
        grid = None
        ntoks = None
        insts = []
        grpdesc = re.compile('(\\d+);(\\d+);(\\d+)')
        instdesc = re.compile('(\\d+):(\\d+)\.(\\d+)-(\\d+)\.(\\d+)')

        clotx = clotf.readlines()
        counter = 0
        ttyn = '\r' if sys.stdout.isatty() else '\n'

        print("Reading and analyzing input data...")

        onceper = max(1, len(clotx) // 500)
        for line in clotx:
            counter += 1
            if not counter % onceper:
                print("~ %d / %d = %03.1f%%" % (counter, len(clotx), 100.0 * counter / len(clotx)), end=ttyn, flush=True)

            sl = line.strip()
            if sl == '':  # empty line => group end
                if grid is not None:
                    if len(insts):  # all instances can be filtered out due to broken markup
                        gr = ExactCloneGroup(grid, ntoks, insts)
                        if exp_clones:
                            gr.try_expand_clones()

                        # xml fixup here if needed
                        keepit = True
                        if shrink_broken_markup:
                            keepit = gr.try_shrink_broken_markup()

                        # finally append it to list
                        if keepit:
                            clonegroups.append(gr)

                    grid = None
                    ntoks = None
                    insts = []
                    # logger.debug("Pushed group")
                else:
                    pass  # logger.debug("empy line seq")
            else:
                gdm = grpdesc.match(sl)
                if gdm:
                    # logger.debug("Matched group descriptor: " + sl)
                    mg = gdm.groups()
                    grid = int(mg[0])
                    ntoks = int(mg[1])
                else:
                    idm = instdesc.match(sl)

                    global cm_inclusiveend
                    if idm:
                        # logger.debug("Matched clone instance: " + sl)
                        mg = [int(n) for n in idm.groups()]

                        ifilen = mg[0]
                        ifile = inputfiles[ifilen]

                        # cm_inclusiveend is important.
                        # Clone Miner likes to include one symbol of next word into the clone,
                        # or it does not like "]]>" CDATA closing. What does CloneMiner mean exactly?
                        # Who knows?.. So having this setting...
                        endoff = ifile.anything2offset((mg[3], mg[4]))
                        insts.append((
                            mg[0],
                            ifile.anything2offset((mg[1], mg[2])),
                            endoff if cm_inclusiveend else endoff - 1
                        ))
                    else:
                        logger.warning("Garbage in input!!")

        # then everything should be stored in offset form, so disable anything2offset
        global allow2offset
        allow2offset = False

class VariativeElement(object):
    def __init__(self, clone_groups: 'list(CloneGroup)'):
        global inputfiles

        def grorder(group):  # no normal lambdas in Python...
            file0, ost, oen = group.instances[0]  # first clone appearance
            return ost

        self.clone_groups = sorted(clone_groups, key=grorder)
        self.htmlvcolors = itertools.cycle(['yellow', 'lightgreen'])
        self.htmlccolors = itertools.cycle(['hotpink', 'cyan'])

    @property
    def textdescriptor(self):
        """
        groups descriptors joined with ;
        """
        return ";".join([g.textdescriptor for g in self.clone_groups])

    @property
    def size(self):
        return sum([len(g.text()) * len(g.instances) for g in self.clone_groups])

    @property
    def power(self):
        return len(self.clone_groups)

    def getvariations(self, position):
        global inputfiles
        g1 = self.clone_groups[position]
        g2 = self.clone_groups[position + 1]

        result = []

        for ii in range(len(g1.instances)):
            g1file, g1start, g1end = g1.instances[ii]
            g2file, g2start, g2end = g2.instances[ii]

            if g1file != g2file:
                raise InternalException("Different files in variation groups")

            result.append(inputfiles[g1file][g1end + 1: g2start - 1])

        return result

    @property
    def html(self):
        def esc(s):
            if len(s.strip()) == 0:
                return """<span style="font-weight: bold; color: red;">&#x3b5;</span>"""
            else:
                return verbhtml.escapecode(s, allow_space_wrap=True)

        templ = string.Template(textwrap.dedent("""
            <tr class="${cssclass} variative" data-groups="${desc}">
            <!-- <td>${ngrp}</td> -->
            <!-- <td>${grps}</td> -->
            <td class="fxd">${clgr}</td>
            <td class="fxd">${varel}</td>
            <!-- <td>${varnc}</td> -->
            <td class="tka"><tt>${text}</tt></td>
            </tr>"""))

        vtext = ""
        vnc = 0

        startgrp = self.clone_groups[0]
        starts = [s for (fno, s, e) in startgrp.instances]

        if self.power <= 1:  # == 1 in fact
            startends = [e for (fno, s, e) in startgrp.instances]
            vvtext = '<wbr/>'.join([
                """<span class="variationclick" title="%d-%d" data-hlrange="%d-%d" style="font-weight: bold; background-color: %s; cursor: pointer;">{%d}</span>"""
                % (cstart, cend, cstart, cend, clr, no)
                for cstart, cend, clr, no in zip(starts, startends, self.htmlccolors, itertools.count(1))
            ])
            vtext = self.clone_groups[0].html(allow_space_wrap=True) + vvtext
        else:
            endgrp = self.clone_groups[-1]
            ends = [e for (fno, s, e) in endgrp.instances]

            variations = self.getvariations(0)  # may be more in the future

            # was """<pre style="font-weight: bold; color: red; background-color: pink;">||</pre>"""
            vvtext = '<wbr/>'.join([
                (
                    """<code class="variationclick" title="%d-%d" data-hlrange="%d-%d" style="background-color: %s; cursor: pointer;">%s</code>"""
                ) % (hlstart, hlend, hlstart, hlend, clr, esc(t))
                for (hlstart, hlend, clr, t) in zip(starts, ends, self.htmlvcolors, variations)
            ])

            vnc = numpy.var([len(v) for v in variations])

            vtext = self.clone_groups[0].html(allow_space_wrap=True) + vvtext + self.clone_groups[1].html(
                allow_space_wrap=True)

        return templ.substitute(
            cssclass="multiple" if len(self.clone_groups) > 1 else "single",
            ngrp=self.power,
            grps=",".join([str(grp.id) for grp in self.clone_groups]) + ",",
            clgr=len(self.clone_groups[0].instances),
            varel=len(self.clone_groups) - 1,
            varnc=vnc,
            text=vtext,
            desc=self.textdescriptor
        )

    @staticmethod
    def summaryhtml(elements: 'list(VariativeElement)'):
        start = textwrap.dedent("""<!DOCTYPE html>
        <html lang="en">
        <head>
        <meta charset="utf-8">
        <title>Variative elements</title>
        <!-- link href="https://raw.githubusercontent.com/jcubic/jquery.splitter/master/css/jquery.splitter.css" rel="stylesheet"/ -->
        <style type="text/css">
        body
        {
            background-color: #dddddd;
            margin: 0px;
        }
        table
        {
            border-width: 1px 1px 0 0;
            border-spacing: 0;
            border-collapse: collapse;
            border-style: solid;
        }
        
        thead {
            height: 30px;
        }
        thead > tr {
            display: block;
            height: 30px;
        }
        
        tbody {
            background-color: #ffffff;
            display: block;
            overflow-y: scroll;
            /* height: calc(50vh - 30px); handle it with JS =( */ 
        }
        
        td, th
        {
            margin: 0;
            padding: 4px;
            border-width: 0 0 1px 1px;
            border-style: solid;
        }
        th
        {
            vertical-align: bottom;
        }
        td
        {
            vertical-align: top;
        }
        
        th.fxd, td.fxd {
            width: 100px;
        }
        
        div #source {
            overflow-y: scroll;
            background-color: #ffffff;
            border: 1px solid black;
        }
        
        div #table {
            overflow: hidden;
        }
        div #blgd {
            display: none;
        }
        
        span.highlight {
            background-color: lightgreen;
        }
        
        tr.multiple input[data-rel="create_dic"] {
            display: none;
        }
        </style>
        <script src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
        <!-- script src="https://raw.githubusercontent.com/jcubic/jquery.splitter/master/js/jquery.splitter-0.14.0.js"></script --> 
        <script src="interactivity.js"></script>
        </head>
        <body>

        <menu type="context" id="singlemenu">
          <menuitem label="Add to Dictionary" id="single2dict"></menuitem>
          <menuitem label="Create Information Element" id="single2elem"></menuitem>
        </menu>
        <menu type="context" id="multiplemenu">
          <menuitem label="Create Variative Element" id="multiple2elem"></menuitem>
        </menu>

        <div id="content">
        <div id="table">
        <table>
        <thead>
        <tr>
        <!-- <th>Participating groups</th> -->
        <!-- <th>e.g.</th> -->
        <th class="fxd">Num. of clones in group</th>
        <th class="fxd">Num. of extension points</th>
        <!-- <th>Variance of variants</th> -->
        <th class="tka">Candidate text:</th>
        </tr>
        </thead>
        <tbody>""")

        middle = textwrap.dedent("""</tbody></table>
        <div id="blgd">
        Blacklisted group descriptors:
        <textarea style="width:100%; height:100px;" id="black_descriptor_list"></textarea>
        2dict group descriptors:
        <textarea style="width:100%; height:100px;" id="todict_descriptor_list"></textarea>
        2elem group descriptors:
        <textarea style="width:100%; height:100px;" id="toelem_descriptor_list"></textarea>
        </div>
        </div>
        <span id="srclabel" style="height: 30px; vertical-align: bottom;">Source code:</span>
        <div id="source">
        <code>""")

        finish = textwrap.dedent("""
        </code>
        </div>
        </div>
        </body></html>""")

        global only_generate_for_ui
        source = "** generated for standalone UI **" if only_generate_for_ui else verbhtml.escapecode(inputfiles[0].text)

        return start + (os.linesep.join([e.html for e in elements])) + middle + source + finish
