# -*- coding: utf-8 -*-

import textwrap

summaryhtml_start = textwrap.dedent(
    """<!DOCTYPE html>
    <html lang="en">
    <head>
    <meta charset="utf-8">
    <title>Variative elements</title>
    <!-- link href="https://raw.githubusercontent.com/jcubic/jquery.splitter/master/css/jquery.splitter.css" rel="stylesheet"/ -->
    <style type="text/css">
    menu
    {
        display: none;
    }
    body
    {
        background-color: #dddddd;
        margin: 0px;
        overflow: hidden;
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
        font-family: sans-serif;
    }
    th
    {
        font-weight: normal;
        font-size: 10pt;
        vertical-align: bottom;
    }
    td
    {
        vertical-align: top;
    }

    th.fxd, td.fxd {
        width: 65px;
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

    code.xmlmarkup {
        color: grey;
    }

    code.highlight, span.highlight {
        background-color: blue !important;
        color: white !important;
    }

    tr.multiple input[data-rel="create_dic"] {
        display: none;
    }

    tr.active  {
        background-color: #ffff88;
    }

    span.diffminus {
        color: red;
        text-decoration: line-through;
    }

    span.diffplus {
        color: 	#009933;
    }
    </style>
    <script src="jquery-2.0.3.min.js"></script>
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
    <th class="fxd">${colh0}</th>
    ${colh1}
    ${epts}
    <!-- <th>Variance of variants</th> -->
    ${catexth}
    </tr>
    </thead>
    <tbody>
    """
)

summaryhtml_middle = textwrap.dedent(
    """</tbody></table>
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
    <code>"""
)

summaryhtml_finish = textwrap.dedent(
    """</code>
    </div>
    </div>
    </body></html>"""
)
