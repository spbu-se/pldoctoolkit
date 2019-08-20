var doX = function(s) {
    // according to python html.escape
    var escaped = s.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');

    // \r should not already appear
    var furtherescaped = escaped.replace(/\n/g, '<br>\n').replace(/\ /g, '&nbsp;').replace(/\t/g, '&nbsp;&nbsp;&nbsp;&nbsp;');
    return furtherescaped;
}

var unX = function(s) {
    // just opposite to doX
    var unfurther = s.replace(/&nbsp;&nbsp;&nbsp;&nbsp;/g, '\t').replace(/&nbsp;/g, ' ').replace(/<br>\n/g, '\n');
    var unx = unfurther.replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/&amp;/g, '&');
    return unx;
}

// http://blog.stevenlevithan.com/archives/faster-than-innerhtml
var replaceHtml = function (el, html) {
    var oldEl = el;
    var newEl = oldEl.cloneNode(false);
    newEl.innerHTML = html;
    oldEl.parentNode.replaceChild(newEl, oldEl);
};

$.fn.highlightRange = function(start, end, candidate_idx) {
    var e = $(this); //= document.getElementById($(this).attr('id')); // I don't know why... but $(this) don't want to work today :-/
    var offset = start;
    var length = end - start + 1;
    var el = e.get(0);
    var unhtml = window.source0; // unescape it to make offsets actual
    var vre = doX(unhtml.substr(0, offset)) +
        '<span class="highlight">' +
        doX(unhtml.substr(offset, length)) +
        "</span>" +
        doX(unhtml.substr(offset + length));

    replaceHtml(el, vre);
}

$.fn.lowlight = function() {
    var el = $(this).get(0);
    replaceHtml(el, window.source0);
}

$.fn.scrollXY = function(scx, scy) {
    var th = $(this);
    var e = th.get(0);
    e.scrollLeft = scx;
    e.scrollTop = scy;
    return th;
}

window.doc_ready = function() {

    var clog = function(msg) {
        if(window.qtab)
            qtab.clog(msg);
        else
            console.log(msg);
    };

    var highlightRange = function(hls, hle, candidate_idx) {
        if (window.qtab) {
            qtab.src_select(hls, hle, candidate_idx);
        } else {
            $('div#source code').highlightRange(hls, hle, candidate_idx);
        }
    };

    var lowlight = function() {
        if (window.qtab) {
            qtab.src_select(0, 0);
        } else {
            $('div#source code').lowlight();
        }
    };

    var curtr = undefined;
    $('.variative').mouseenter(function() {
        if(!window.qtab) curtr = $(this);
    });

    $('.multiple').attr('contextmenu', 'multiplemenu');
    $('.single').attr('contextmenu', 'singlemenu');

    var commoncontext = function(ctr) {
        ctr.css("background-color", "lightgrey").removeAttr('contextmenu');
        var datagroups = ctr.attr("data-groups");
        var bdl = $('#black_descriptor_list');
        bdl.html(bdl.html() + datagroups + "\n");
        return datagroups;
    };

    var saveInfDicts = function() {
        var te = $('#toelem_descriptor_list').html();
        var td = $('#todict_descriptor_list').html();
        if (window.qtab) {
            qtab.inf_dic_descs(te, td);
        } else {
            clog([te, td].toString());
        }
    }

    window.decide_enable_dict = function(x, y) {
        clog("Context menu on: (" + x + "," + y + ")")
        var elt = document.elementFromPoint(x, y);
        var tr = $(elt).closest('tr.variative');
        curtr = tr;
        if(tr.hasClass("single")) {
            clog("... single...");
            qtab.enable_dict(true);
        } else {
            clog("... multiple...");
            qtab.enable_dict(false);
        }
    }

    window.single2dict = function() {
        clog("#single2dict");
        var dg = commoncontext(curtr);
        var targ = $('#todict_descriptor_list');
        targ.html(targ.html() + dg + "\n");
        saveInfDicts();
        if(window.qtab) {
            window.qtab.refactor_create_dic_entry(dg)
        }
    };

    var single2elem = function() {
        clog("#single2elem");
        var dg = commoncontext(curtr);
        var targ = $('#toelem_descriptor_list');
        targ.html(targ.html() + dg + "\n");
        saveInfDicts();
        if(window.qtab) {
            window.qtab.refactor_create_inf_elt(dg)
        }
    };

    var multiple2elem = function() {
        clog("#multiple2elem");
        var dg = commoncontext(curtr);
        var targ = $('#toelem_descriptor_list');
        targ.html(targ.html() + dg + "\n");
        saveInfDicts();
        if(window.qtab) {
            window.qtab.refactor_create_inf_elt(dg)
        }
    };

    window.some2elem = function() {
        if (curtr.hasClass("single"))
            single2elem();
        else
            multiple2elem();
    }

    window.mupvisible = true;
    window.toggleclonebrowsermarkup = function(newval) {
        clonebrowsermarkup = $("code.xmlmarkup");
        window.mupvisible = !window.mupvisible;
        if(window.mupvisible)
            clonebrowsermarkup.show();
        else
            clonebrowsermarkup.hide();
    }

    window.toggleclonebrowserdiffs = function(newval) {
        if(newval) {
            $("span.modeldiffplus").addClass("diffplus");
            $("span.modeldiffminus").show();
        } else {
            $("span.modeldiffplus").removeClass("diffplus");
            $("span.modeldiffminus").hide();
        }
    }

    window.updatecandidatetr = function(data_idx, outer_html) {
        var elt = $('tr[data-idx="' + data_idx +'"]');
        elt.replaceWith(outer_html);
        $('.variationclick').unbind('click');
        $('.variationclick').click(window.vclicker);
    }

    $("#single2dict").click(single2dict);
    $("#single2elem").click(single2elem);
    $("#multiple2elem").click(multiple2elem);

    window.vclicker = function() {
        $('.variationclick').removeClass('highlight');
        $(this).addClass('highlight');

        $('tr.variative').removeClass("active");
        $(this).closest('tr').addClass("active");

        var hlrange = $(this).attr("data-hlrange").split('-');
        var hls = +hlrange[0];
        var hle = +hlrange[1];
        var candidate_idx = $(this).closest('tr').attr("data-idx");
        var src = $('div#source code');
        // lowlight();
        highlightRange(hls, hle, candidate_idx);
        src = $('div#source code');

        if (!window.qtab) {
            src.parent().scrollXY(0, 0); // to calculate $('span.highlight').offset().top later properly
            var sho = $('#source span.highlight').offset();
            var shot = sho.top; var shol = sho.left;
            var so = $('#source').offset();
            var sot = so.top; var sol = so.left;
            src.parent().scrollXY(shol-sol, shot - sot);
        }

        // fuzzy tricks
        var tt = $(this).parent();
        var codes = $(tt).find("code.fuzzycode");
        var ct = $(this).text();
        if(codes.length > 0) {
            var links = $(tt).find("span.variationclick");
            var idx = -1;
            links.each(function(nidx){
                if($(this).text() == ct)
                    idx = nidx;
            });
            codes.each(function(nidx){
                if(nidx == idx)
                    $(this).show();
                else
                    $(this).hide();
            });
        }
    };
    $('.variationclick').click(window.vclicker);

    window.source0 = $('div#source code').get(0).textContent; // unescapes

    window.adaptToQWebView = function() {
        clog("Adapting to QWebView!");
        $("#removeforqwebview").remove();
        // window.qtab.src_text(window.source0) // Qt will load source itself
        $('menu').remove();
        $('span#srclabel').remove();
        $('div#source code').remove();
        $('div#source').remove();
        $('div#blgd').remove();
        $('body').css({
            'overflow': 'hidden'
        });
        correct_sizes();
    }

    var correct_sizes = function() {

        var wh = window.innerHeight;
        var thh = $('div#table thead').height();

        if (window.qtab) {
            $("div#table").height(wh);
        } else {
            $("html").height(wh);
            var sh = $('span#srclabel').height();
            $("div#table").height((wh-sh) * 0.6);
            $("div#source").height((wh-sh) * 0.4);
        }
        $("tbody").height($("div#table").height() - thh);
    };

    $(window).resize(correct_sizes);
    correct_sizes();

    if(window.qtab)
        window.adaptToQWebView();
};

$(document).ready(function() {
    try {
        window.doc_ready();
        makeTableSortable();
    } catch (e) {
        alert(e);
    }
});

function makeTableSortable() {
    let table = $('table');
    $('thead tr th')
        .each(function () {
            let tableHeaderColumn = $(this);
            let columnIndex = tableHeaderColumn.index();
            let inverse = false;
            tableHeaderColumn.css({
                'cursor': 's-resize'
            });
            tableHeaderColumn.click(function () {

                table.find('td').filter(function () {
                    return $(this).index() === columnIndex;

                }).sortElements(function (a, b) {
                    return parseInt($.text(a)) > parseInt($.text(b)) ?
                        inverse ? -1 : 1
                        : inverse ? 1 : -1;

                }, function () {
                    // parentNode is the element we want to move
                    return this.parentNode;

                });
                if (inverse === false) {
                    tableHeaderColumn.css({
                        'cursor': 'n-resize'
                    });
                } else {
                    tableHeaderColumn.css({
                        'cursor': 's-resize'
                    });
                }
                inverse = !inverse;
            });
        });
}


/**
 * jQuery.fn.sortElements
 * --------------
 * @author James Padolsey (http://james.padolsey.com)
 * @version 0.11
 * @updated 18-MAR-2010
 * --------------
 * @param Function comparator:
 *   Exactly the same behaviour as [1,2,3].sort(comparator)
 *
 * @param Function getSortable
 *   A function that should return the element that is
 *   to be sorted. The comparator will run on the
 *   current collection, but you may want the actual
 *   resulting sort to occur on a parent or another
 *   associated element.
 *
 *   E.g. $('td').sortElements(comparator, function(){
 *      return this.parentNode;
 *   })
 *
 *   The <td>'s parent (<tr>) will be sorted instead
 *   of the <td> itself.
 */
jQuery.fn.sortElements = (function () {

    let sort = [].sort;

    return function (comparator, getSortable) {

        getSortable = getSortable || function () {
            return this;
        };

        let placements = this.map(function () {

            let sortElement = getSortable.call(this),
                parentNode = sortElement.parentNode,

                // Since the element itself will change position, we have
                // to have some way of storing it's original position in
                // the DOM. The easiest way is to have a 'flag' node:
                nextSibling = parentNode.insertBefore(
                    document.createTextNode(''),
                    sortElement.nextSibling
                );

            return function () {

                if (parentNode === this) {
                    throw new Error(
                        "You can't sort elements if any one is a descendant of another."
                    );
                }

                // Insert before flag:
                parentNode.insertBefore(this, nextSibling);
                // Remove flag:
                parentNode.removeChild(nextSibling);

            };

        });

        return sort.call(this, comparator).each(function (i) {
            placements[i].call(getSortable.call(this));
        });

    };

})();
