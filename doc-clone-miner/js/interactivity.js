var doX = function(s) {
	// according to python html.escape
	var escaped = s.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');

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


$.fn.highlightRange = function(start, end) {
    var e = $(this); //= document.getElementById($(this).attr('id')); // I don't know why... but $(this) don't want to work today :-/
    var offset = start;
    var length = end - start + 1;
    var el = e.get(0);
    var unhtml = window.source0; // unescape it to make offsets actual
    var vre = doX(unhtml.substr(0,offset)) +
           '<span class="highlight">' +
           doX(unhtml.substr(offset, length)) +
           "</span>" +
           doX(unhtml.substr(offset + length));
    el.innerHTML = vre;

    return $(this);
}

$.fn.lowlight = function() {
    $(this).html(window.source0);
    return $(this);
}

$.fn.scrollXY = function(scx, scy) {
    var th = $(this);
    var e = th.get(0);
    e.scrollLeft = scx;
    e.scrollTop = scy;
    return th;
}

$( document ).ready(function() {
  /*
  $('input[data-functionality="create-inf-el"][type="checkbox"]').change(function() {
    if($(this).is(":checked")) {
      $(this).attr("disabled", true);
      var datagroups = $(this).attr("data-groups");
      var bdl = $('#black_descriptor_list');
      bdl.html(bdl.html() + "\n" + datagroups);
      $(this).parent().parent().css( "background-color", "lightgrey" );
    }
  });
  */

  var curtr = undefined;
  $('.variative').mouseenter(function() {
    curtr = $(this);
  });

  $('.multiple').attr('contextmenu',  'multiplemenu');
  $('.single').attr('contextmenu',  'singlemenu');

  var commoncontext = function(ctr) {
    ctr.css( "background-color", "lightgrey").removeAttr('contextmenu');
    var datagroups = ctr.attr("data-groups");
    var bdl = $('#black_descriptor_list');
    bdl.html(bdl.html() + datagroups + "\n");
    return datagroups;
  };

  var saveInfDicts = function() {
    var te = $('#toelem_descriptor_list').html();
    var td = $('#todict_descriptor_list').html();
    if(qtab) {
      qtab.inf_dic_descs(te, td);
    } else {
      console.log(te, td);
    }
  }

  $("#single2dict, tr.single > td > input[data-rel='create_dic']").click(function() {
    console.log("#single2dict");
    var dg = commoncontext(curtr);
    var targ = $('#todict_descriptor_list');
    targ.html(targ.html() + dg + "\n");
    $("td.qwebview_only[data-groups='"+dg+"']").empty();
    saveInfDicts();
  });
  $("#single2elem, tr.single > td > input[data-rel='create_inf']").click(function() {
    console.log("#single2elem");
    var dg = commoncontext(curtr);
    var targ = $('#toelem_descriptor_list');
    targ.html(targ.html() + dg + "\n");
    $("td.qwebview_only[data-groups='"+dg+"']").empty();
    saveInfDicts();
  });
  $("#multiple2elem, tr.multiple > td > input[data-rel='create_inf']").click(function() {
    console.log("#multiple2elem");
    var dg = commoncontext(curtr);
    var targ = $('#toelem_descriptor_list');
    targ.html(targ.html() + dg + "\n");
    $("td.qwebview_only[data-groups='"+dg+"']").empty();
    saveInfDicts();
  });

  $('code.variationclick').click(function() {
    var hlrange = $(this).attr("data-hlrange").split('-');
    var hls = +hlrange[0];
    var hle = +hlrange[1];
    var src = $('div#source code');
    src.lowlight();
    src.highlightRange(hls, hle);

    src.parent().scrollXY(0, 0); // to calculate $('span.highlight').offset().top later properly
    src.parent().scrollXY(0, $('span.highlight').offset().top)
  });

  window.source0 = $('div#source code').get(0).textContent;  // unescapes


  window.adaptToQWebView = function() {
    console.log("Adapting to QWebView!");
    $("#removeforqwebview").remove();
  }

  // window.adaptToQWebView(); // uncomment to debug in browser
});
