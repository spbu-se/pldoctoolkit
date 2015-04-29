#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# requires https://pypi.python.org/pypi/PyContracts

import logging

logging.basicConfig(filename='clones2html.log', level=logging.INFO)
global logger
# logger = logging.Logger('clones.reporter')
logger = logging  # use it this way

import traceback
import os
import sys
import cgi
import string
import collections
import argparse
import shutil
import time
import errno

import intertree
import clones

clones.initdata()


def initargs():
    argpar = argparse.ArgumentParser()
    argpar.add_argument("-nb", "--findnearby",
                        help="Find clones nearby each other, specify maximal distance (if clones theirselves are shorter)")
    argpar.add_argument("-wv", "--writevariations", help="Detect and write clone variations")
    argpar.add_argument("-sd", "--subdir", help="Subdir for output")
    argpar.add_argument("-bl", "--blacklist", help="Group ID's (as Clone Miner prints) to throw away")
    argpar.add_argument("-wl", "--whitelist",
                        help="Group ID's (as Clone Miner prints) to keep (consider others blacklisted)")
    argpar.add_argument("-minl", "--minimalclonelength", help="Minimal clone length in symbols. Default = 0")
    argpar.add_argument("-mino", "--minimalgrouppower", help="Minimal count of clones in group. Default = 2")
    argpar.add_argument("-cmup", "--checkmarkup",
                        help="Allow (no) Filter (yes) and Shrink-fix (shrink) groups, containing broken markup",
                        default='no')
    argpar.add_argument("-fint", "--filterintersections", help="Filter clone intersections")
    argpar.add_argument("-ph", "--printheader", help="Print header for stats line")
    argpar.add_argument("-mv", "--maximalvariance",
                        help="Maximal variance of variative insertion sizes, defaults to 2000", default=2000)
    argpar.add_argument("-csp", "--check-semantics-presence", help="Filter clones without textual semantics",
                        default="no")
    argpar.add_argument("-ie", "--inclusive-end",
                        help="Clone Miner last clone symbol is assumed to be included in the clone", default="no")
    argpar.add_argument("-wrs", "--write-reformatted-sources",
                        help="Write source file with tabs replaced with 4 spaces and UNIX line ends", default="yes")
    argpar.add_argument("-oui", "--only-ui",
                        help="Only generate data needed by standalone [Qt] UI", default="no")
    argpar.add_argument("-ecl", "--exp-clones",
                        help="Try to expand clones (Clone Miner skips last tokens sometimes)", default="yes")

    args = argpar.parse_args()

    global nearby
    nearby = None
    if args.findnearby:
        nearby = int(args.findnearby)

    global printheader
    printheader = True
    if args.printheader:
        printheader &= (args.printheader == 'yes')

    global writevariations
    writevariations = False
    if args.writevariations:
        writevariations |= (args.writevariations == 'yes')

    global subdir
    subdir = ""
    if args.subdir:
        subdir = args.subdir
        try:
            subdir = "%03d" % int(subdir)
        except:
            logger.warning("subdir " + subdir + " is not a number")

    global filterintersections
    filterintersections = True
    if args.filterintersections and args.filterintersections == 'no':
        filterintersections = False

    clones.initoptions(args, logger)

## ------------

initargs()

ufilecnt = 0
ufiletm = int(time.monotonic() * 100) % 1000000


def ufilepr():
    global ufilecnt
    ufilecnt += 1
    return "%06d_%04d" % (ufiletm, ufilecnt)


def mkdir_p(path):
    try:
        os.makedirs(path)
    except OSError as exc:  # Python >2.5
        if exc.errno == errno.EEXIST and os.path.isdir(path):
            pass
        else:
            raise


def dispersion(a):
    n = len(a)
    if not n: return 0
    s = sum(a)
    s2 = sum([x * x for x in a])
    return (s2 - s * s / n) / n


def uniqpairs(indexable, filter=None):
    l = len(indexable)
    total = l * (l + 1) // 2
    now = total
    dispstep = (total // 400) + 1
    # print("Combining total %d" % total)
    if filter is None:  # dup to speedup
        for i in range(0, l):
            for j in range(0, i):
                now -= 1
                if now % dispstep == 0:
                    print("%d/%d = %02d%% left...    " % (now, total, int(100 * now / total)), end='\r')
                yield i, j
    else:
        for i in range(0, l):
            for j in range(0, i):
                now -= 1
                if now % dispstep == 0:
                    print("%d/%d = %02d%% left...    " % (now, total, int(100 * now / total)), end='\r')
                if filter(i, j):
                    yield i, j


clones.loadinputs(logger)
# ------

# !!!
# Filter clone groups here
# !!!
allcglen = len(clones.clonegroups)

# how many of them are unbalanced
def countunbalanced():
    import xmllexer
    import xmlfixup

    unbalancedcg = 0
    brokenmarkup = 0
    for cg in clones.clonegroups:
        bmp = False
        (ifn, ib, ie) = cg.instances[0]
        ifl = clones.inputfiles[ifn]
        il = ie - ib + 1
        covint, bf, bl = xmllexer.find_covered_intervals(ib, il, ifl.lexintervals)

        if bf and covint[0].int_type != xmllexer.IntervalType.general \
                or bl and covint[-1].int_type != xmllexer.IntervalType.general:
            brokenmarkup += 1
            bmp = True

        if not bmp:
            pb, ab, pr, ar = xmlfixup.balance_unbalanced_text(covint)
            if len(pb) != 0 or len(ab) != 0:
                unbalancedcg += 1

    return brokenmarkup, unbalancedcg

def totalsymbolsingroups(groups):
    """
    To measure reuse potential
    """
    return sum(g.totalsymbols() for g in groups)

# ----------------------------------------------------
# brokenmarkup, unbalancedcg = countunbalanced()
#

# expanded = sum(1 for kg in clones.clonegroups if kg.expanded)
# print("Total expanded groups: %d" % (expanded, ))

clones.clonegroups = [kg for kg in clones.clonegroups if kg.isCorrect()]

# by_no_semantic, by_no_text, by_too_short, burl =\
# clones.CloneGroup.by_no_semantic, clones.CloneGroup.by_no_text, clones.CloneGroup.by_too_short, clones.CloneGroup.by_breaking_url

# print(
#     ("Total: %d, broken markup: %d, balancing markup: %d, total correct: %d; " +
#      "no semantic: %d, no text: %d, <5: %d, burl: %d filtered by sema ank mkup or <5: %d") % (
#      allcglen, brokenmarkup, unbalancedcg, len(clones.clonegroups),
#      by_no_semantic, by_no_text, by_too_short, burl,
#      allcglen - by_no_semantic - by_no_text - by_too_short))
# print("<5: %.1f, no text %.1f, no sense %.1f, totl: %.1f" % (100 * by_too_short / allcglen, 100 * by_no_text / allcglen, 100 * by_no_semantic / allcglen,
#                                                 100 * (by_too_short + by_no_text + by_no_semantic) / allcglen))

# print("Filtered %d of %d groups, good = %d" % (allcglen - goodcglen, allcglen, goodcglen))

# print("Total symbols in intersecting groups: %d" % (totalsymbolsingroups(clones.clonegroups),))
# -----------------------------------------------------

# filter self-intersections
if filterintersections:
    # here we again consider same input file

    logging.info("filtering intersecting")
    allclones = {}
    for g in clones.clonegroups:
        for ii in range(len(g.instances)):
            # fn, cis, cie = g.instances[ii]
            civ = g.significances[ii]
            _, istart, iend = g.instances[ii]
            allclones[(istart, iend)] = (g, ii, civ)

    # now smartass intervaltree to speedup intersection search
    it = intertree.IntervalTree(list(allclones.keys()))

    totalclones = len(allclones)
    passed = 0
    progressstep = 1 + (totalclones // 200)  # +1 to not divide by zero

    for c1k in allclones.keys():
        passed += 1
        if passed % progressstep == 0:
            print("Checking clones: %d/%d = %02d%%  " % (passed, totalclones, int(100 * passed / totalclones)),
                  end='\r')

        g1, c1n, c1v = allclones[c1k]
        if c1n in g1.cloneindicestodel:
            continue

        intersections = it.findintersecting(c1k)

        for c2k in intersections:
            if c2k == c1k:  # else we will delete all clones =)
                continue

            g2, c2n, c2v = allclones[c2k]
            if c2n in g2.cloneindicestodel:
                continue

            if c1v <= c2v:
                g1.cloneindicestodel.add(c1n)
                break
            else:
                g2.cloneindicestodel.add(c2n)

    logging.info("marked clones for removal")

    for g in clones.clonegroups:
        iinds = range(len(g.instances))
        g.instances = [inst for inst, iind in zip(g.instances, iinds) if iind not in g.cloneindicestodel]
        g.cloneindicestodel = set()
        del g.significances

    logging.info("removed clones")
    logging.info("now having %d groups" % len(clones.clonegroups))

    clones.clonegroups = [g for g in clones.clonegroups if len(g.instances) > 1]

    logging.info("after removing intersections having %d groups" % len(clones.clonegroups))

    # print("Total symbols in non-intersecting groups: %d" % (totalsymbolsingroups(clones.clonegroups),))


    """
    # considered tested
    # let's test if there are no intersections
    allclo = []
    for g in clones.clonegroups:
        for _, s, e in g.instances:
            allclo.append((s,e))

    allclp = uniqpairs(allclo)
    ovlps = False
    for i1, i2 in allclp:
        s1, e1 = allclo[i1]
        s2, e2 = allclo[i2]
        if clones.is_overlapping(s1, e1, s2, e2):
            ovlps = True
            print ("OVERLAPS! " + str(((s1, e1), (s2, e2))))

    print("checked overlapping, ovlps = %s" % str(ovlps))
    """

goodcglen = len(clones.clonegroups)
logging.info("Allcglen = %d, doodcglen = %d" % (allcglen, goodcglen))

htmlt = string.Template("""<html>
<head>
<title>Clone groups</title>
<style type="text/css">
table
{
    border-width: 0 0 1px 1px;
    border-spacing: 0;
    border-collapse: collapse;
    border-style: solid;
}
td, th
{
    margin: 0;
    padding: 4px;
    border-width: 1px 1px 0 0;
    border-style: solid;
}
</style>
</head>
<script src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
<body>
<p>
Files:<br/>
${filenames}
</p>
<p>
Avg tokens in clone: ${avgtok}
</p>
<p>
Dirty groups: ${dirtygrp}
</p>
Clone groups:
<table>
<thead><tr><th>X</th><th>G</th><th>R</th><th>#</th><th>ID</th><th>#Tokens</th><th>Occurs</th><th>{{</th><th>Text</th><th>}}</th></tr></thead>
<tbody>${rows}</tbody>
</table>

<form>
Garbage Clones
<br/>
<input type="text" id="bads" cols="40" rows="5"></input>
<br/>

Glossary Clones
<br/>
<input type="text" id="glos" cols="40" rows="5"></input>
<br/>

Req Clones
<br/>
<input type="text" id="reqs" cols="40" rows="5"></input>
<br/>

<input type="button" id="idlists" value="Update Lists"></input>
</form>

<script>

$$('#idlists').click(
function(){
  var bads = $$('#bads');
  var reqs = $$('#reqs');
  var glos = $$('#glos');
  bads.val('');
  reqs.val('');
  glos.val('');

  $$("input.cx:checked").each(function(idx) {
    var id = $$(this).attr('id').replace('x_', '');
    bads.val(bads.val() + ',' + id)
  });

  $$("input.cg:checked").each(function(idx) {
    console.log("Called");
    var id = $$(this).attr('id').replace('g_', '');
    glos.val(glos.val() + ',' + id)
  });

  $$("input.cr:checked").each(function(idx) {
    var id = $$(this).attr('id').replace('r_', '');
    reqs.val(reqs.val() + ',' + id)
  });

});

</script>

</body>
</html>""")

trt = string.Template("""<tr bgcolor="${bgcolor}">
<td><input type="checkbox" value="0" id="x_${id}" class="cx"></input></td>
<td><input type="checkbox" value="0" id="g_${id}" class="cg"></input></td>
<td><input type="checkbox" value="0" id="r_${id}" class="cr"></input></td>
<td>${no}</td><td>${id}</td><td>${nt}</td><td>${occurs}</td>
<td><tt>${ltext}</tt></td>
<td><tt>${text}</tt></td>
<td><tt>${rtext}</tt></td>
</tr>""")

trows = []

ntoksdistribution = collections.defaultdict(lambda: 0)
instancelengths = []

no = 0
for g in clones.clonegroups:
    occurs = collections.defaultdict(lambda: 0)
    coct = g.isCorrect()
    if coct:
        no += 1
    for i in g.instances:
        occurs[i[0]] += 1
        ntoksdistribution[g.ntokens] += 1
        instancelengths.append(g.ntokens)
    # octx = "<br/>".join(["%d: %d" % (k, occurs[k]) for k in occurs])
    octx = str(occurs[0])
    lt, ct, rt = tuple(map(lambda t: cgi.escape(t).replace('\r', '').replace('\n', '&#10;'), g.textwithcontext()))
    trows.append(
        trt.substitute(id=g.id, nt=g.ntokens, occurs=octx,
                       ltext=lt, text=ct, rtext=rt,
                       bgcolor="white" if g.isCorrect() else "yellow", no=no if coct else '/'
                       )
    )

ntoksavg = sum(instancelengths) / len(instancelengths) if len(instancelengths) else 0
ninstanceavg = len(instancelengths) / len(clones.clonegroups) if len(clones.clonegroups) else 0

html = htmlt.substitute(
    filenames="<br/>".join([i.fileName for i in clones.inputfiles]),
    avgtok=str(ntoksavg),
    rows=os.linesep.join(trows),
    dirtygrp=allcglen
)

if printheader:
    print("=> Ntok, Total groups, Total ok grp, E(cl. length), D(cl. length), E(inst/grp):")

print("%s, %5d, %5d, %.3f, %.4f, %.4f" % (
    subdir, allcglen, len(clones.clonegroups), ntoksavg, dispersion(instancelengths), ninstanceavg))

mkdir_p(os.path.join("Output", subdir))
with open(os.path.join("Output", subdir, "pygroups.html"), 'w', encoding='utf-8') as htmlfile:
    htmlfile.write(html)


# measure distances

def findnearby201312():
    mkdir_p(os.path.join("Output", subdir, "variations"))

    # clones.clonegroups = [ kg for kg in clones.clonegroups if kg.isCorrect() ]

    print("Measuring distances...")

    global nearby
    minborderdist = nearby

    def sgn(n):
        if n < 0:
            return -1
        elif n > 0:
            return 1
        else:
            return 0

    dists = {}

    print("Combibibg groups...")
    uniqp = list([gp for gp in uniqpairs(clones.clonegroups, lambda i, j: len(clones.clonegroups[i].instances) == len(
        clones.clonegroups[j].instances))])
    ttu = len(uniqp)

    print("Filtering %d group combinations..." % ttu)

    participatedgroups = set()

    def c1gc2gdist(g1g2):
        cg1, cg2 = g1g2
        cg1, cg2 = clones.clonegroups[cg1], clones.clonegroups[cg2]
        return clones.CloneGroup.distance(cg1, cg2)

    # sequential
    for g1g2 in uniqp:
        if ttu % 5000 == 0:
            print("%d group pairs left" % ttu, end="       \r", flush=True)
        ttu -= 1

        d = c1gc2gdist(g1g2)
        if 0 <= d < clones.infty:
            dists[g1g2] = d
            participatedgroups.add(g1g2[0])
            participatedgroups.add(g1g2[1])

    """
    # Parallel. Does not work =)
    print("In parellel...")
    with cunfu.ThreadPoolExecutor() as exc:
        parresults = exc.map(c1gc2gdist, uniqp)

    print("Joining threads...")
    parresults = list(parresults)

    print("Filtering distances...")
    for k, v in zip(uniqp, parresults):
        if not v is None:
            dists[k] = v
    """

    print("Participated %d groups" % len(participatedgroups))

    with open(os.path.join("Output", subdir, "nearby.txt"), 'w') as nearby:
        for gp in dists:
            cg1 = clones.clonegroups[gp[0]]
            cg2 = clones.clonegroups[gp[1]]
            nearby.write("=============================\n")
            nearby.write(cg1.text() + '\n')
            nearby.write("---- ^^ %d ^^   | | not farther than %d chars from | | vv %d vv ----\n" % (
                len(cg1.instances), dists[gp], len(cg2.instances)))
            nearby.write(cg2.text() + '\n\n')
        nearby.flush()

    logger.info("Done.")


def combine_gruops():
    import combine_grp

    # sort descending
    available_groups = sorted(clones.clonegroups,
                              key=lambda gr: len(gr.text()),
                              reverse=True)

    if nearby:
        combinations, remaining_groups = combine_grp.combine_gruops_par_20140819(available_groups)
    else:
        combinations, remaining_groups = [], available_groups

    # print("Offered clones -- total: %d, single: %d, variative: %d" % (len(remaining_groups) + len(combinations), len(remaining_groups), len(combinations)))

    combinations += [clones.VariativeElement([gr]) for gr in remaining_groups]
    combinations.sort(key=lambda ve: ve.size, reverse=True)

    cohtml = clones.VariativeElement.summaryhtml(combinations)
    with open(os.path.join("Output", subdir, "pyvarelements.html"), 'w', encoding='utf-8') as htmlfile:
        htmlfile.write(cohtml)

    shutil.copyfile(
        os.path.join(os.path.dirname(os.path.abspath(__file__)), 'js', 'interactivity.js'),
        os.path.join("Output", subdir, "interactivity.js")
    )


# findnearby201312()
combine_gruops()
