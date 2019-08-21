#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import asyncio
import os
import re
import shutil
import sys
import functools

# import Levenshtein  # not used any more
import traceback

try:
    import interval as itvl  # https://pypi.python.org/pypi/pyinterval
except Exception as e:
    print("pyinterval not installed. No coverage reports will be available", file=sys.stderr)


def copy_required_libs_to(outdir):
    import settings
    """
     Copies all required common js scripts and css files (libs) to specified output directory 
    :param outdir: where to copy files
    :return:
    """
    shutil.copyfile(
        os.path.join(settings.DIST_DIR, 'jquery', 'jquery-3.4.1.min.js'),
        os.path.join(outdir, "jquery-3.4.1.min.js")
    )
    shutil.copyfile(
        os.path.join(settings.DIST_DIR, 'popper', 'popper.js'),
        os.path.join(outdir, 'popper.js')
    )
    shutil.copyfile(
        os.path.join(settings.DIST_DIR, 'bootstrap', 'js', 'bootstrap.min.js'),
        os.path.join(outdir, 'bootstrap.min.js')
    )
    shutil.copyfile(
        os.path.join(settings.DIST_DIR, 'bootstrap', 'css', 'bootstrap.min.css'),
        os.path.join(outdir, 'bootstrap.min.css')
    )


def copy_required_files_to(outdir):
    import settings
    """
    Copies all required js scripts and css files to specified output directory 
    :param outdir: where to copy files
    :return:
    """
    copytree(settings.JS_DIR, outdir)
    shutil.copyfile(
        os.path.join(settings.TEMPLATE_DIR, 'summary', 'summary_html.css'),
        os.path.join(outdir, 'summary_html.css')
    )


"""Misc utilitary garbage"""


def escape(s):
    return s.replace('&', '&amp;').replace('<', '&lt;').replace('>', '&gt;')  # html.escape(s)


def escapen(s):
    # not os.linesep to be more precise on length
    return escape(s).replace('\n', '<br/>\n')


def escapecode(s, allow_space_wrap=False):
    s = escapen(s)
    # s = s.replace('\r', '') # should not be there already
    if not allow_space_wrap:
        s = s.replace(' ', '&nbsp;')
    s = s.replace('\t', '&nbsp;&nbsp;&nbsp;&nbsp;')
    return s


def copytree(src, dst, exclude=None):
    """
    Recursive copy
    :param src:
    :param dst:
    :param exclude:
    :return:
    """

    if not os.path.exists(dst):
        os.makedirs(dst)
    for item in os.listdir(src):
        s = os.path.join(src, item)
        d = os.path.join(dst, item)
        if exclude is not None and item in exclude:
            continue
        if os.path.isdir(s):
            copytree(s, d)
        else:
            if not os.path.exists(d) or os.stat(s).st_mtime - os.stat(d).st_mtime > 1:
                shutil.copy2(s, d)


def write_variative_report(clones, candidates, report_file_name):
    """
    Function to save extra reports
    :param clones: clones module VariativeElement
    :param candidates: list of clones.VariativeElement instances 
    :param report_file_name: HTML file to save everything 
    :return: 
    """

    target_dir = os.path.dirname(report_file_name)
    cohtml = clones.VariativeElement.summaryhtml(candidates, clones.ReportMode.variative)
    with open(report_file_name, 'w', encoding='utf-8') as htmlfile:
        htmlfile.write(cohtml)

    copy_required_files_to(target_dir)
    copy_required_libs_to(target_dir)


def transpose(a):
    """
    Thanks, http://stackoverflow.com/a/21444360/539470
    :param a: list of lists (say, lines of a matrix)
    :return: list of lists (say, lines of transposed matrix)
    """
    return [list(x) for x in zip(*a)]


def connected_slices(i: 'itvl.interval') -> 'list[tuple[int, int]]':
    return [(int(t[0][0]), int(t[0][1])) for t in i.components]


def lratio(s1, s2):
    """Deprecated"""
    import Levenshtein
    raise DeprecationWarning("This function should not be used anymore")
    return 1 - Levenshtein.distance(s1, s2) / max(len(s1), len(s2), 1)


def diratio(s1, s2):
    try:
        import faster_pattern_near_duplicate_search
        return faster_pattern_near_duplicate_search.di_similarity(s1, s2)
    except (ImportError, ModuleNotFoundError):
        import pattern_near_duplicate_search
        return pattern_near_duplicate_search.di_similarity(s1, s2)


_wre = re.compile(r"\w+", re.UNICODE)


def tokens(text):
    r = []
    for m in _wre.finditer(text):
        s = m.start()
        e = m.end()
        r.append((s, e, text[s:e]))
    return r


def text_to_tokens_offsets(src: str) -> 'tuple[list[str], list[tuple[int, int]]]':
    str_offs = [(src[fi.start():fi.end()], (fi.start(), fi.end())) for fi in _wre.finditer(src)]
    return tuple([list(t) for t in zip(*str_offs)])


def tokenst(text):
    return [s for b, e, s in tokens(text)]


def ctokens(text):
    return ' '.join(tokenst(text))


def save_reformatted_file(fileName):
    lines = []
    with open(fileName, encoding='utf-8') as ifs:
        for line in ifs:
            # lst = line.rstrip() + '\n' # leave leading blanks + add separator (we do not need \r, so no os.linesep)
            lst = line.replace('\r', '').replace('\n',
                                                 '') + '\n'  # leave leading blanks + add separator (we do not need \r, so no os.linesep)
            lst = lst.replace('\t', '    ')  # Clone Miner is very brave to consider TAB equal to 4 spaces
            lines.append(lst)
    text = "".join(lines)
    with open(fileName + ".reformatted", 'w+', encoding='utf-8', newline='\n') as ofs:
        ofs.write(text)


def save_standalone_html(source_html, target_html):
    with open(source_html, encoding='utf-8') as htmlsrc:
        htmlcontent = htmlsrc.read()

        def replace_ref_with_script(match):  # want normal lambdas here...
            jsfilename = os.path.join(
                os.path.dirname(source_html),  # html directory
                match.group(1) + ".js"
            )
            with open(jsfilename) as jsfile:
                js = jsfile.read()
                return """<script>%s</script>""" % js

        htmlcontent = re.sub("""<script src="(.*)\\.js"></script>""", replace_ref_with_script, htmlcontent)
        with open(target_html, "w", encoding='utf-8') as ofl:
            ofl.write(htmlcontent)


# asyncio stuff

def ready_future(result=None):
    fut = asyncio.get_event_loop().create_future()
    fut.set_result(result)
    return fut


def set_asio_el(loop):
    asyncio.set_event_loop(loop)


def fire_and_forget(task, *args, **kwargs):
    """Start and forget async task as https://stackoverflow.com/a/37344956/539470 shows"""
    loop = asyncio.get_event_loop()
    if callable(task):
        return loop.run_in_executor(None, task, *args, **kwargs)
    else:
        raise TypeError('Task must be a callable')


def excprint(function):
    """
    A decorator that wraps the passed in function and logs
    exceptions should one occur
    """

    @functools.wraps(function)
    def wrapper(*args, **kwargs):
        try:
            return function(*args, **kwargs)
        except Exception as e:
            # log the exception
            exc_info = sys.exc_info()
            fe = traceback.format_exception(*exc_info)
            print(
                ("!!EXCEPTION in function <<%s>>, args: (%s), kwargs: (%s)" + os.linesep + "%s") % (
                    function.__name__, str(args), str(kwargs), os.linesep.join(fe)
                ),
                file=sys.stderr
            )
            # re-raise the exception
            raise e

    return wrapper
