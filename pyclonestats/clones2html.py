#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import os
import logging
import cgi
import string
import collections
import argparse
import operator
import time
import errno
import intertree

import clones
clones.initdata()


def initargs():
    argpar = argparse.ArgumentParser()
    argpar.add_argument("-nb", "--findnearby", help="Find clones nearby each other")
    argpar.add_argument("-wv", "--writevariations", help="Detect and write clone variations")
    argpar.add_argument("-sd", "--subdir", help="Subdir for output")
    argpar.add_argument("-bl", "--blacklist", help="Group ID's (as Clone Miner prints) to throw away")
    argpar.add_argument("-minl", "--minimalclonelength", help="Minimal clone length in symbols. Default = 0")
    argpar.add_argument("-mino", "--minimalgrouppower", help="Minimal count of clones in group. Default = 2")
    argpar.add_argument("-cmup", "--checkmarkup", help="Filter groups, containing broken markup")
    argpar.add_argument("-fint", "--filterintersections", help="Filter clone intersections")
    argpar.add_argument("-ph", "--printheader", help="Print header for stats line")
    args = argpar.parse_args()

    logging.basicConfig(filename='clones2htlm.log', level=logging.ERROR)
    global logger
    # logger = logging.Logger('clones.reporter')
    logger = logging # use it this way

    global nearby
    nearby = True
    if args.findnearby:
        nearby &= (args.findnearby == 'yes')

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
ufiletm  = int(time.monotonic() * 100) % 1000000


def ufilepr():
    global ufilecnt
    ufilecnt += 1
    return "%06d_%04d" % (ufiletm, ufilecnt)


def mkdir_p(path):
    try:
        os.makedirs(path)
    except OSError as exc: # Python >2.5
        if exc.errno == errno.EEXIST and os.path.isdir(path):
            pass
        else:
            raise


def dispersion(a):
    n = len(a)
    if not n: return 0
    s = sum(a)
    s2 = sum([x*x for x in a])
    return (s2 - s*s/n)/n


def uniqpairs(indexable, filter = None):
    l = len(indexable)
    total = l * (l + 1) // 2
    now = total
    dispstep = (total // 400) + 1
    # print("Combining total %d" % total)
    if filter is None: # dup to speedup
        for i in range(0, l):
            for j in range(0, i):
                now -= 1
                if now % dispstep == 0:
                    print("%d/%d = %02d%% left...    " % (now, total, int(100*now/total)), end='\r')
                yield i, j
    else:
        for i in range(0, l):
            for j in range(0, i):
                now -= 1
                if now % dispstep == 0:
                    print("%d/%d = %02d%% left...    " % (now, total, int(100*now/total)), end='\r')
                if filter(i, j):
                    yield i, j


clones.loadinputs(logger)

# ------      

# !!!
# Filter clone groups here
# !!!
allcglen = len(clones.clonegroups)
clones.clonegroups = [ kg for kg in clones.clonegroups if kg.isCorrect() ]

# print("Filtered %d of %d groups, good = %d" % (allcglen - goodcglen, allcglen, goodcglen))

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
    progressstep = 1 + (totalclones // 200) # +1 to not divide by zero

    for c1k in allclones.keys():
        passed += 1
        if passed % progressstep == 0:
            print("Checking clones: %d/%d = %02d%%  " % (passed, totalclones, int(100*passed/totalclones)), end='\r')

        g1, c1n, c1v = allclones[c1k]
        if c1n in g1.cloneindicestodel:
            continue

        intersections = it.findintersecting(c1k)

        for c2k in intersections:
            if c2k == c1k: # else we will delete all clones =)
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
<thead><tr><th>X</th><th>G</th><th>R</th><th>#</th><th>ID</th><th>#Tokens</th><th>Occurs</th><th>Text</th></tr></thead>
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
<td>${no}</td><td>${id}</td><td>${nt}</td><td>${occurs}</td><td><tt>${text}</tt></td></tr>""")

trows = []

ntoksdistribution = collections.defaultdict(lambda : 0)
instancelengths = []

no = 0
for g in clones.clonegroups:
    occurs = collections.defaultdict(lambda : 0)
    coct = g.isCorrect()
    if coct:
        no += 1
    for i in g.instances:
        occurs[i[0]] += 1
        ntoksdistribution[g.ntokens] += 1
        instancelengths.append(g.ntokens)
#    octx = "<br/>".join(["%d: %d" % (k, occurs[k]) for k in occurs])
    octx = str(occurs[0])
    trows.append(
        trt.substitute(id = g.id, nt = g.ntokens, occurs = octx,
                       text = cgi.escape(g.text()).replace('\r','').replace('\n', '&#10;'),
                       bgcolor = "white" if g.isCorrect() else "yellow", no = no if coct else '/'
        )
    )

ntoksavg = sum(instancelengths)/len(instancelengths) if len(instancelengths) else 0
ninstanceavg = len(instancelengths)/len(clones.clonegroups) if len(clones.clonegroups) else 0

html = htmlt.substitute(
    filenames = "<br/>".join([i.fileName for i in clones.inputfiles]),
    avgtok = str(ntoksavg),
    rows = os.linesep.join(trows),
    dirtygrp = allcglen
)

if printheader:
    print("=> Ntok, Total groups, Total ok grp, E(cl. length), D(cl. length), E(inst/grp):")

print("%s, %5d, %5d, %.3f, %.4f, %.4f" % (subdir, allcglen, len(clones.clonegroups), ntoksavg, dispersion(instancelengths), ninstanceavg))

mkdir_p(os.path.join("Output", subdir))
with open(os.path.join("Output", subdir, "pygroups.html"), 'w', encoding='utf-8') as htmlfile:
    htmlfile.write(html)


# measure distances

def findnearby():
    mkdir_p(os.path.join("Output", subdir, "variations"))

    # clones.clonegroups = [ kg for kg in clones.clonegroups if kg.isCorrect() ]

    print("Measuring distances...")

    # borderdist = 100

    def sgn(n):
        if n < 0:
            return -1
        elif n > 0:
            return 1
        else:
            return 0

    dists = {}

    print("Combibibg groups...")
    uniqp = list([gp for gp in uniqpairs(clones.clonegroups, lambda i, j : len(clones.clonegroups[i].instances) == len(clones.clonegroups[j].instances))])
    ttu = len(uniqp)

    print("Filtering %d group combinations..." % ttu)

    def c1gc2gdist(g1g2):
        cg1, cg2 = g1g2
        maxdist = 0
        try:
            signum = 0

            borderdist = len(clones.clonegroups[cg1].text()) + len(clones.clonegroups[cg2].text())

            # sort by offset
            insts1 = sorted(clones.clonegroups[cg1].instances, key = operator.itemgetter(1))
            insts2 = sorted(clones.clonegroups[cg2].instances, key = operator.itemgetter(1))

            maxdist = 0
            for c1i, c2i in zip(insts1, insts2):
                _, c1o, _ = c1i
                _, c2o, _ = c2i


                newsignum = sgn(c2o-c1o) # criteria for 1 file only!!!
                if newsignum * signum >= 0:
                    signum = newsignum
                else:
                    raise Exception("Clones of two groups are combined in different order")

                maxdist = max(clones.clonegroups.CloneGroup.distance(c1i, c2i), maxdist)

                if maxdist > borderdist:
                    raise Exception("No, those groups are not neighbours")
            # if everything is ok

            if writevariations:
                pfx = ufilepr()
                cic = 0
                for c1i, c2i in zip(insts1, insts2):
                    cic += 1
                    _, c1b, c1e = c1i # TODO: same file only
                    _, c2b, c2e = c2i
                    with open(os.path.join("Output", subdir, "variations", pfx + "_" + str(cic) + ".txt"), "w") as insts:
                        insts.write(clones.inputfiles[0][min(c1b, c2b):max(c1e, c2e)])
                        insts.flush()
            
            return maxdist
        except Exception as e:
            # traceback.print_exc()
            return None

    # sequential
    for g1g2 in uniqp:
        if ttu % 5000 == 0:
            print("%d group pairs left" % ttu)
        ttu -= 1

        d = c1gc2gdist(g1g2)
        if not d is None:
            dists[g1g2] = d

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

    with open(os.path.join("Output", subdir, "nearby.txt"), 'w') as nearby:
        for gp in dists:
            cg1 = clones.clonegroups[gp[0]]
            cg2 = clones.clonegroups[gp[1]]
            nearby.write("=============================\n")
            nearby.write(cg1.text() + '\n')
            nearby.write("---- ^^ %d ^^   | | not farther than %d chars from | | vv %d vv ----\n" % (len(cg1.instances), dists[gp], len(cg2.instances)))
            nearby.write(cg2.text() + '\n\n')
        nearby.flush()

    logger.info("Done.")

if nearby:
    findnearby()
