const resiziblePointers = "  <div class='resizers'>\n" +
    "    <div class='resizer top-left'></div>\n" +
    "    <div class='resizer top-right'></div>\n" +
    "    <div class='resizer bottom-left'></div>\n" +
    "    <div class='resizer bottom-right'></div>\n" +
    "  </div>";

/**
 * Binds nodes to click event to highlight source text
 * @param nodes
 */
function bindOnNodeClicked(nodes) {
    nodes.on("click", function () {
        let elem = $(this);
        let key = 'hl_range_' + elem.attr('id');
        let range = $('[' + key + ']').attr(key);
        let data = {
            'type': 'highlightRange',
            'data': {
                'range': range
            }
        };
        // notify parent window aka navigation_page
        window.parent.postMessage(data, "*");
    });
}

/**
 * shows tooltip with showing differences between archetype and this node
 * @param nodes
 * @param tooltip
 */
function bindOnMouseEnterNodes(nodes, tooltip) {
    nodes.on('mouseenter', function () {
        let key = 'diff_' + $(this).attr('id');//
        let html_to_display = $('#' + key).html() + resiziblePointers;
        showTooltip(tooltip, d3.event, html_to_display)
    });
}

function bindOnMouseLeaveNodes(nodes, tooltip) {
    nodes.on('mouseleave', function () {
        hideTooltip(tooltip);
    });
}

/**
 * shows tooltip with showing differences between these nodes
 * @param edges
 * @param tooltip
 */
function bindOnMouseEnterEdges(edges, tooltip) {
    edges.on('mouseenter', function () {
        let key = 'diff_' + $(this).attr('id');
        let html_to_display = $('#' + key).html() + resiziblePointers;
        showTooltip(tooltip, d3.event, html_to_display);
    })
}

function bindOnMouseLeaveEdges(edges, tooltip) {
    edges.on('mouseleave', function () {
        hideTooltip(tooltip);
    });
}

function bindOnMouseEnterTooltipEdge(tooltip) {
    onMouseEnterTooltip(tooltip);
}

function bindOnMouseLeaveTooltipEdge(tooltip) {
    onMouseLeaveTooltip(tooltip);
}

function bindOnMouseLeaveTooltipNode(tooltip) {
    onMouseLeaveTooltip(tooltip);
}

function bindOnMouseEnterTooltipNode(tooltip) {
    onMouseEnterTooltip(tooltip);
}


function showTooltip(tooltip, evt, text) {
    //stop with immediately finish transition and clear queue
    tooltip.stop(true, true);
    tooltip.html(text);
    //aka initial width of tooltip always 300
    tooltip.css({
        "left": evt.pageX + 15 + 'px',
        "top": evt.pageY + 15 + 'px',
        "height": "",
        "width": "300px"
    });
    tooltip.fadeIn('slow');
}


function hideTooltip(tooltip) {
    tooltip.fadeOut('slow');
}

function onMouseEnterTooltip(tooltip) {
    tooltip.on("mouseenter", function () {
        $(this).stop();
        $(this).fadeIn('slow');
    });
}

function onMouseLeaveTooltip(tooltip) {
    tooltip.on("mouseleave", function () {
        $(this).stop();
        $(this).fadeOut('slow');
    });
}


let shouldIgnoreDrag = false;// due to overlapping mouse down when drag and resize

function makeResizableDiv(div) {

    const element = div.get(0);
    let min_size = 20, origWidth = 0, origHeight = 0, origX = 0, origY = 0, origMouseX = 0, origMouseY = 0;

    div.on('mousedown', '.resizers .resizer', function (e) {
        e.preventDefault();
        origWidth = parseFloat(div.css('width').replace('px', ''));
        origHeight = parseFloat(div.css('height').replace('px', ''));
        origX = element.getBoundingClientRect().left;
        origY = element.getBoundingClientRect().top;
        origMouseX = e.pageX;
        origMouseY = e.pageY;
        $(window).on('mousemove', resize);
        $(window).on('mouseup', stopResize);

        let curResizer = $(this);

        function resize(e) {
            if (curResizer.hasClass('bottom-right')) {
                const width = origWidth + (e.pageX - origMouseX);
                const height = origHeight + (e.pageY - origMouseY);
                if (width > min_size) {
                    element.style.width = width + 'px'
                }
                if (height > min_size) {
                    element.style.height = height + 'px'
                }
            } else if (curResizer.hasClass('bottom-left')) {

                const height = origHeight + (e.pageY - origMouseY);
                const width = origWidth - (e.pageX - origMouseX);
                if (height > min_size) {
                    element.style.height = height + 'px'
                }
                if (width > min_size) {
                    element.style.width = width + 'px';
                    element.style.left = origX + (e.pageX - origMouseX) + 'px'
                }
            } else if (curResizer.hasClass('top-right')) {

                const width = origWidth + (e.pageX - origMouseX);
                const height = origHeight - (e.pageY - origMouseY);
                if (width > min_size) {
                    element.style.width = width + 'px';
                }
                if (height > min_size) {
                    element.style.height = height + 'px';
                    element.style.top = origY + (e.pageY - origMouseY) + 'px'
                }

            } else {

                const width = origWidth - (e.pageX - origMouseX);
                const height = origHeight - (e.pageY - origMouseY);
                if (width > min_size) {
                    element.style.width = width + 'px';
                    element.style.left = origX + (e.pageX - origMouseX) + 'px'
                }
                if (height > min_size) {
                    element.style.height = height + 'px';
                    element.style.top = origY + (e.pageY - origMouseY) + 'px'
                }
            }
        }

        function stopResize() {
            $(window).off('mousemove', resize);
        }

    });

    div.on('mouseenter', '.resizers .resizer', function (e) {
        shouldIgnoreDrag = true;
    });

    div.on('mouseleave', '.resizers .resizer', function (e) {
        shouldIgnoreDrag = false;
    });

}

function makeElementDraggable(elt) {
    let deltaX = 0, deltaY = 0, initX = 0, initY = 0;
    elt.on('mousedown', dragMouseDown);

    function dragMouseDown(e) {
        e.preventDefault();
        if (e.which !== 1) return; // when not left click
        initX = e.clientX;
        initY = e.clientY;
        elt.on('mouseup', closeDragElement);
        elt.on('mousemove', elementDrag);
    }

    function elementDrag(e) {
        e.preventDefault();
        if (shouldIgnoreDrag) {
            closeDragElement();
        }
        //  new cursor position:
        deltaX = initX - e.clientX;
        deltaY = initY - e.clientY;
        initX = e.clientX;
        initY = e.clientY;
        // set the  new position:

        //not working,probably bug:
        // elt.css({
        //     'top' : (elt.css('offsetTop')  -  deltaY) +'px',
        //     'left': (elt.css('offsetLeft') - deltaX) + 'px'
        // });
        elt.css({
            'top': (elt.get(0).offsetTop - deltaY) + 'px',
            'left': (elt.get(0).offsetLeft - deltaX) + 'px'
        });

    }

    function closeDragElement() {
        elt.off('mousemove', elementDrag);
        elt.off('mouseup', closeDragElement);
    }
}
