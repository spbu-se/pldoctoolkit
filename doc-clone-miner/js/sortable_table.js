/**
 * Makes table sortable by all columns
 */

$(window).ready(function () {
    makeTableSortable();

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


});


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
