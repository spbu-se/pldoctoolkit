#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# requires https://pypi.python.org/pypi/PyContracts

import logging

logging.basicConfig(filename='clones2html.log', level=logging.INFO)
global logger
# logger = logging.Logger('clones.reporter')
logger = logging  # use it this way

import textwrap
import os
import sys
import cgi
import string
import collections
import argparse
import shutil
import time
import errno
import csv

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
    argpar.add_argument("-csp", "--check-semantics-presence", help="Filter clones without textual semantics (no, yes, nltk)",
                        default="no")
    argpar.add_argument("-ie", "--inclusive-end",
                        help="Clone Miner last clone symbol is assumed to be included in the clone", default="no")
    argpar.add_argument("-wrs", "--write-reformatted-sources",
                        help="Write source file with tabs replaced with 4 spaces and UNIX line ends", default="yes")
    argpar.add_argument("-oui", "--only-ui",
                        help="Only generate data needed by standalone [Qt] UI", default="no")
    argpar.add_argument("-ecl", "--exp-clones",
                        help="Try to expand clones (Clone Miner skips last tokens sometimes)", default="yes")
    argpar.add_argument("-mxcsvgt", "--max-csv-group-tokens", type=int,
                        help="Max tokens of group in CSV report", default=3)
    argpar.add_argument("-mncsvgi", "--min-csv-group-instances", type=int,
                        help="Min clones in group in CSV report", default=2)
    argpar.add_argument("-gca", "--group-combining-algorithm",
                        choices=["interval-n-ext", "full-square"],
                        help="Group combining algorithm", type=str, default="interval-n-ext")
    argpar.add_argument("-maxctl", "--max-clone-token-length", type=int, help="MAX clone length inn tokens",
                        default=sys.maxsize)
    argpar.add_argument("-mingpow", "--min-group-power", type=int, help="MIN clone group power in clones",
                        default=2)

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

    global max_csv_group_tokens
    max_csv_group_tokens = args.max_csv_group_tokens

    global min_csv_group_instances
    min_csv_group_instances = args.min_csv_group_instances

    global group_combining_algorithm_name
    group_combining_algorithm_name = args.group_combining_algorithm

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
# clones.ExactCloneGroup.by_no_semantic, clones.ExactCloneGroup.by_no_text, clones.ExactCloneGroup.by_too_short, clones.ExactCloneGroup.by_breaking_url

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
    logging.info("REUSE AMOUNT STATS:")
    tot_length = 0
    reuse_length = 0
    for ifl in clones.inputfiles:
        tot_length += len(ifl.text)
    for g in clones.clonegroups:
        for f, b, e in g.instances:
            reuse_length += e - b
    logging.info("Total: " + str(tot_length) + " Reusable: " + str(reuse_length) + " AMOUNT: " + str(reuse_length/tot_length))

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


def log_short_repetitions(maxtokens, minrepetitions):
    import semanticfilter

    numf = lambda num: (("%d" if type(num) is int else "%0.20f") % num).replace(',', '.')
    numl = lambda vl: list(map(numf, list(vl)))

    with open(os.path.join("Output", subdir, "shortterms.csv"), 'w', encoding='utf-8') as csvfile:
        fwtr = csv.writer(csvfile, delimiter=';', quotechar='"', quoting=csv.QUOTE_MINIMAL, lineterminator='\n')

        fwtr.writerow(['Total groups:', numf(len(clones.clonegroups))])
        fwtr.writerow(['N tokens', 'Occurs times', 'Common Phrase', 'Words', 'Plain Text', 'Text'])

        for cg in clones.clonegroups:
            if cg.ntokens <= maxtokens and len(cg.instances) >= minrepetitions and not cg.containsNoWords():
                words = ' '.join(semanticfilter.cleanwords(cg.plain_text(), True))
                fwtr.writerow([
                    numf(cg.ntokens),
                    numf(len(cg.instances)),
                    numf(int(cg.containsNoSemantic())),
                    words,
                    cg.plain_text().replace('\r', '').replace('\n', ' '),
                    cg.text().strip().replace('\r', '').replace('\n', ' ')
                ])

def combine_gruops():
    import combine_grp

    # sort descending
    available_groups = sorted(clones.clonegroups,
                              key=lambda gr: len(gr.text()),
                              reverse=True)

    group_combinators = {
        "full-square": combine_grp.combine_gruops_par_20140819,
        "interval-n-ext": combine_grp.combine_groups_n_ext_with_int_tree, # default
        # TODO: "interval-2-ext": findnearby201312 # first try, 2013 -- port or delete it
    }

    group_combinator = group_combinators[group_combining_algorithm_name]

    if nearby:
        combinations, remaining_groups = group_combinator(available_groups)
    else:
        combinations, remaining_groups = [], available_groups

    # print("Offered clones -- total: %d, single: %d, variative: %d" % (len(remaining_groups) + len(combinations), len(remaining_groups), len(combinations)))

    combinations += [clones.VariativeElement([gr]) for gr in remaining_groups]
    combinations.sort(key=lambda ve: ve.size, reverse=True)

    cohtml = clones.VariativeElement.summaryhtml(combinations, False)
    with open(os.path.join("Output", subdir, "pyvarelements.html"), 'w', encoding='utf-8') as htmlfile:
        htmlfile.write(cohtml)

    shutil.copyfile(
        os.path.join(os.path.dirname(os.path.abspath(__file__)), 'js', 'interactivity.js'),
        os.path.join("Output", subdir, "interactivity.js")
    )
    shutil.copyfile(
        os.path.join(os.path.dirname(os.path.abspath(__file__)), 'js', 'jquery-2.0.3.min.js'),
        os.path.join("Output", subdir, "jquery-2.0.3.min.js")
    )


def write_density_report():
    with \
        open(os.path.join("Output", subdir, "densityreport.html"), 'w', encoding='utf-8') as density_table_file,\
        open(os.path.join("Output", subdir, "densitymap.html"), 'w', encoding='utf-8') as density_map_file,\
        open(os.path.join("Output", subdir, "heatmap.html"), 'w', encoding='utf-8') as heat_map_file,\
        open(os.path.join("Output", subdir, "densitybrowser.html"), 'w', encoding='utf-8') as density_browser_file:

        import densityreport
        dr = densityreport.report_densities(clones.clonegroups, clones.inputfiles)
        h0 = textwrap.dedent("""<!DOCTYPE html>
        <html lang="en">
        <head>
        <meta charset="utf-8">
        <style type="text/css">
        table
        {
            border-color: grey;
            border-width: 1px 1px 0 0;
            border-spacing: 0;
            border-collapse: collapse;
            border-style: solid;
        }
        td, th
        {
            border-color: grey;
            margin: 0;
            padding: 4px;
            border-width: 0 0 1px 1px;
            border-style: solid;
            font-family: sans-serif;
        }
        </style>
        </head>
        <body>
        %s
        </body>
        </html>""") % (dr[0],)

        h1 = textwrap.dedent("""<!DOCTYPE html>
        <html lang="en">
        <head>
        <meta charset="utf-8">
        <style type="text/css">
        </style>
        <script>
        function selctionfuzzysearch(evt) {
          var seltext = window.getSelection().toString();
          var minsim = window.prompt("Minimal similarity", "0.5");
          // alert([minsim, seltext]);
          var nloc = "http://127.0.0.1:49999/fuzzysearch?minsim=" + encodeURIComponent(minsim) +
            "&text=" + encodeURIComponent(seltext);
          window.location = nloc;
        }
        document.addEventListener("keypress", selctionfuzzysearch, false);
        </script>

        </head>
        <body style="overflow-x: hidden;">
        %s
        </body>
        </html>""") % (dr[1],)

        h3 = textwrap.dedent("""<!DOCTYPE html>
        <html lang="en">
        <head>
        <meta charset="utf-8">
        <style type="text/css">
        </style>
        </head>
        <body style="overflow-x: hidden;">
        %s
        </body>
        </html>""") % (dr[2],)

        density_table_file.write(h0)
        density_map_file.write(h1)
        heat_map_file.write(h3)

        density_browser_file.write(textwrap.dedent(
            """<!DOCTYPE html>
            <html lang="en">
            <head>
            <meta charset="utf-8">
            <style type="text/css">
            </style>
            </head>
            <body style="margin: 0px; padding: 0px;">
            <iframe name="densitymap" src="densitymap.html" style="border: 0; position:absolute; height: 100%; width: calc(100% - 200px);"></iframe>
            <iframe name="heatmap" src="heatmap.html" style="overflow-x: hidden; border: 0; position:absolute; height: 100%; left: calc(100% - 200px); width: 200px;"></iframe>
            </body>
            </html>"""
        ))

if __name__ == '__main__':  #
    if max_csv_group_tokens > 0 and min_csv_group_instances > 0:
        log_short_repetitions(max_csv_group_tokens, min_csv_group_instances)

    write_density_report()
    combine_gruops()
