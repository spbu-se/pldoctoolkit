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
        background-color: #eaeaea;
    }
    
    tr span.edit_controls {
        display: none;
        cursor: pointer;
        color: red;
        font-weight: bold;
    }
    
    tr.active span.edit_controls {
        display: initial;
    }
    
    span.diffminus {
        color: red;
        text-decoration: line-through;
    }

    span.diffplus {
        color: 	#009030;
        font-weight: bold;
    }

    span.nonarchetypical {
        color: 	blue;
        font-weight: bold;
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
    <iframe style="display: none;" id="queryframe" src="" ></iframe>
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

densitybrowser_template = textwrap.dedent(
    """<!DOCTYPE html>
    <html lang="en">
    <head>
    <meta charset="utf-8">
    <style type="text/css">
    </style>
    <script>
      window.generationTime = ${gentime};
      
      var outdated = function() {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', '/gentime', true);
        xhr.onreadystatechange = function() { // (3)
          if (xhr.readyState != 4) return;
          if (xhr.status != 200) {
            alert(xhr.status + ': ' + xhr.statusText);
          } else {
            var r = xhr.responseText;
            if(parseFloat(r) > window.generationTime) // outdated file
              window.close();
            else
              setTimeout(outdated, 1500);
          }
        
        }
        xhr.send();
      };
      // setTimeout(outdated, 1500); // can't close, let's not use it for now
      
      window.select_source = function(b, e) {
        var w = document.all.densitymap.contentWindow;
        w.selectbyds('' + b);
      }
    </script>
    </head>
    <body style="margin: 0px; padding: 0px;">
    <iframe name="densitymap" src="${abspath}/densitymap.html" style="border: 0; position:absolute; height: 100%; width: calc(100% - 200px);"></iframe>
    <iframe name="heatmap" src="${abspath}/heatmap.html" style="overflow-x: hidden; border: 0; position:absolute; height: 100%; left: calc(100% - 200px); width: 200px;"></iframe>
    </body>
    </html>"""
)

density_map_h1 = textwrap.dedent(
    """<!DOCTYPE html>
        <html lang="en">
        <head>
        <meta charset="utf-8">
        <style type="text/css">
        </style>
        <script>
        
        function selectbyds(ds) {
          var spn = document.querySelector('[data-source-offs="' + ds + '"]');
          // place it in the center
          window.scrollTo(0, spn.offsetTop - (window.innerHeight - spn.offsetHeight) / 2);
          var r = document.createRange();
          r.setStart(spn, 0)
          r.setEndAfter(spn)
          var ws = window.getSelection();
          ws.removeAllRanges();
          ws.addRange(r);
        }
        
        function selctionfuzzysearch(evt) {
          var seltext = window.getSelection().toString();
          var minsim = window.prompt("Similarity threshold", "0.77");
          // alert([minsim, seltext]);
          var nloc = "http://127.0.0.1:49999/fuzzysearch?minsim=" + encodeURIComponent(minsim) +
            "&text=" + encodeURIComponent(seltext);
          document.getElementById("queryframe").src = nloc;
        }
        document.addEventListener("keypress", selctionfuzzysearch, false);
        </script>

        </head>
        <body style="overflow-x: hidden; font-family: monospace;">
        %s
        <div><iframe style="border: 0px; width: 90%%; " id="queryframe" src="" ></iframe></div>
        <a href="http://127.0.0.1:49999/shutdown" target="_blank" >X</a>
        </body>
        </html>"""
)
