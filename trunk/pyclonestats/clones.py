#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import os
import sys
import re
import bisect
import xml.etree.ElementTree as ET
import xml.sax as xs
import xml.sax.handler as xsh


# seems reasonable
# http://stackoverflow.com/questions/3269434/whats-the-most-efficient-way-to-test-two-integer-ranges-for-overlap
def is_overlapping(x1,x2, y1,y2):
    return max(x1,y1) <= min(x2,y2)


def initdata(inputfilesiv = [], clonegroupsiv = []):
    global inputfiles
    global clonegroups
    global blacklist

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

    global mingrppow
    mingrppow = 2
    if args.minimalgrouppower:
        try:
            mingrppow = max(2, args.minimalgrouppower.strip())
        except:
            logger.error("Expecting integer minimal group power")

    global checkmarkup
    checkmarkup = True
    if args.checkmarkup and args.checkmarkup == 'no':
        checkmarkup = False

    global blacklist
    blacklist = set()
    
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


class TextZone(object):
    UFO = 0
    ELEM = 1
    TEXT = 2
    SPACE = 3
    URL = 4

    def __init__(self, mode, start, end, content):
        self.mode = mode # Algol 68 rulez
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
        return self.inputfile.anything2offset((l, c+1))

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

        zn = TextZone(TextZone.SPACE if empty else TextZone.TEXT, offs, offs + len(content)-1, content)
        self.zones.append(zn)
        if not empty:
            self.textzones.append(zn)
            self.textzoneoffsets.append(offs)
            self.textzoneends.append(offs + len(content))
        
    def ignorableWhitespace(self, content):
        offs = self.getoffs()
        self.zones.append(TextZone(TextZone.SPACE, offs, offs + len(content)-1, content))

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
            self.urlzones.append(TextZone(TextZone.URL, ofs, ofs + len(cnt)-1, cnt))

    def discover(self):
        btx = self.src.encode('utf8')
        xs.parseString(btx, self)

        self.zones.sort(key = lambda xz : xz.start)
        self.rzones = sorted(self.zones, key = lambda xz : xz.end)

        self.discoverURLs()
        

class InputFile(object):

    def __init__(self, fileName):
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
                lst = line.replace('\r', '').replace('\n', '') + '\n' # leave leading blanks + add separator (we do not need \r, so no os.linesep)
                lst = lst.replace('\t', '    ') # Clone Miner is very brave to consider TAB equal to 4 spaces
                offset += len(lst)
                lines.append(lst)
            self.offsets.append(offset)
            self.text = "".join(lines)
        # then calculate XML zones
        marker = XMLZoneMarker(self)
        marker.discover()
        self.zones, self.rzones = marker.zones, marker.rzones
        self.urlzones = marker.urlzones

        self.textzoneoffsets = marker.textzoneoffsets
        self.textzoneends = marker.textzoneends
        self.textzones = marker.textzones
    
    def _getsinglechar(self, coord):
        o = self.anything2offset(coord)
        return self.text[o]

    def __getitem__(self, stst):
        """
        left  from (1.1) when (line, col)
        right inclusive when (line, col)
        
        """
        if not isinstance(stst, slice):
            return self._getsinglechar(stst)
        else:
            start = self.anything2offset(stst.start)
            end = self.anything2offset(stst.stop)
            return self.text[start:end + 1]

    def offset2linecol(self, offset):
        line = bisect.bisect_left(self.offsets, offset) - 1
        return (line + 1, offset - self.offsets[line] + 1)

    def linecol2offset(self, linecol):
        """
        Both from 1
        """
        line, col = linecol
        return self.offsets[line - 1] + col - 1

    def anything2linecol(self, coord):
        return coord if isinstance(coord, tuple) else self.offset2linecol(coord) 

    def anything2offset(self, coord):
        return self.linecol2offset(coord) if isinstance(coord, tuple) else coord

class CloneGroup(object):
    def __init__(self, id, ntokens, instances):
        """
        instances -- [ (filenum, (sl, sc), (el, ec)) ]
        self.instances -- [ (filenum, off1, off2) ]
        """
        global inputfiles
        global clonegroups

        if ntokens == 0 or len(instances) == 0:
            raise Exception("bad args")
        self.id = id
        self.ntokens = ntokens

        self.instances = []
        self.significances = []
        self.cloneindicestodel = set()

        for i in instances:
            ifilen, s, e = i
            ifile = inputfiles[ifilen]
            s, e = ifile.anything2offset(s), ifile.anything2offset(e)
            self.instances.append((ifilen, s, e))
            self.significances.append(int(len(instances)*(e-s+1)**2)) # from Kopin diploma

    def text(self, inst = 0):
        global inputfiles
        global clonegroups

        fileno, start, end = self.instances[inst]
        return inputfiles[fileno][start:end]

    @classmethod
    def distance(clgrcl, inst1, inst2):
        global inputfiles

        file1, start1, end1 = inst1
        file2, start2, end2 = inst2
        if file1 != file2:
            return sys.maxsize # 0+1j # LOL

        file1 = inputfiles[file1]
        file2 = inputfiles[file2]
        start1 = file1.anything2offset(start1)
        end1 = file1.anything2offset(end1)
        start2 = file1.anything2offset(start2)
        end2 = file1.anything2offset(end2)

        if (start1 <= start2 < end1 or start1 < end2 <= end1 or
            start2 <= start1 < end2 or start2 < end1 <= end2): # within
            return -1
        else:
            return start2 - end1 if start2 >= end1 else start1 - end2

    def containsNoText(self):
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
            endafter     =   bisect.bisect_right(ifile0.textzoneends, e)

            s1i = saturate(startinfrontof - 1, 0, len(ifile0.textzoneoffsets))
            e1i = saturate(endafter + 1, 0, len(ifile0.textzoneoffsets))

            for tfi in range(s1i, e1i):
                tzb, tze = ifile0.textzoneoffsets[tfi], ifile0.textzoneends[tfi]
                if is_overlapping(s, e, tzb, tze):
                    self._containsNoText = False
                    break

            # if self._containsNoText:
            #     print("only markup {{{%s}}}[%s:%s]" % (ifile0[s:e], str(ifile0.anything2linecol(s)), str(ifile0.anything2linecol(e))))

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
            if le < minlen: # too tolerant
            # text = inputfiles[ifl][s:e].strip()
            # if len(text) < minlen:
                # print(s, e, minlen)
                # print("zoz")
                return True
        score = instlen * (1 + 1/len(self.instances))
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

        rampi = "<fakeroot>"; rampo = "</fakeroot>"
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

        # logger.debug("self.id in blacklist: %s" % str(self.id in blacklist))
        return self.id in blacklist

    def isCorrect(self):
        global inputfiles
        global clonegroups

        if self.isBlacklisted():
            # logger.debug("blacklisted")
            return False

        # check if whitespace
        for i in range(0, len(self.instances)):
            if len(self.text(i).strip()) == 0:
                # logger.debug("group has empty clone")
                return False

        if self.isLessThanAllowed():
            # logger.debug("group is less than allowed")
            return False

        if self.breaks_url(): #containsBrokenHref(text):
            # logger.debug("broken url")
            return False

        if self.containsNoText():
            # logger.debug("no text in group")
            return False

        if checkmarkup and self.containsBrokenMarkup():
            # logger.info("broken markup")
            return False

        return True


def loadinputs(logger):
    global inputfiles
    global clonegroups

    # initilize input files
    with open(os.path.join("Input", "InputFiles.txt")) as ifi:
        for line in ifi:
            sl = line.strip()
            if len(sl):
                inputfiles.append(InputFile(sl))

    # process clones and initialize groups & instances
    with open(os.path.join("Output", "Clones.txt")) as clotx:
        grid = None
        ntoks = None
        insts = []
        grpdesc = re.compile('(\\d+);(\\d+);(\\d+)')
        instdesc = re.compile('(\\d+):(\\d+)\.(\\d+)-(\\d+)\.(\\d+)')
        for line in clotx:
            sl = line.strip()
            if sl == '': # empty line => group end
                if grid is not None:
                    clonegroups.append(CloneGroup(grid, ntoks, insts))
                    grid = None
                    ntoks = None
                    insts = []
                    # logger.debug("Pushed group")
                else:
                    pass # logger.debug("empy line seq")
            else:
                gdm = grpdesc.match(sl)
                if gdm:
                    # logger.debug("Matched group descriptor: " + sl)
                    mg = gdm.groups()
                    grid = int(mg[0])
                    ntoks = int(mg[1])
                else:
                    idm = instdesc.match(sl)

                    if idm:
                        # logger.debug("Matched clone instance: " + sl)
                        mg = [int(n) for n in idm.groups()]
                        insts.append((mg[0], (mg[1], mg[2]), (mg[3], mg[4])))
                    else:
                        logger.warning("Garbage in input!!")
