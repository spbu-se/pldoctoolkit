/**
 *
 * Script  allows remove selected clone in a group by clicking on X above {№}
 *
 */


const removeSelectedCloneEvent = 'removeSelectedClone';


$(window).ready(function () {

    function emitCustomEvent(eventName) {
        $(window).trigger(eventName);
    }

    let currentSpan = undefined;

    function initSup() {
        //inject X to all span elems {} to end
        $('span.variationclick').append('<sup class="removeCloneSup" style="font-size: x-small" data-toggle="tooltip"' +
            ' title="Remove clone from group" data-placement="auto">&#10005;</sup>');
        $('.table').on('mouseover', '.removeCloneSup', function () {
            $(this).css('font-size', 'small');
        });
        $('.table').on('mouseleave', '.removeCloneSup', function () {
            $(this).css('font-size', 'x-small');
        });

        $('.table').on('click', ".removeCloneSup", function (e) {
            currentSpan = $(this).closest('span');
            emitCustomEvent(removeSelectedCloneEvent);
        });

    }

    initSup();

    $(window).on(removeSelectedCloneEvent, function (e) {
        if (currentSpan === undefined) {
            return;
        }

        let span = currentSpan;
        let range = span.attr('data-hlrange').split('-');
        let trTag = span.closest('tr');
        modifyRowTag(parseInt(range[0]), parseInt(range[1]), trTag.attr('data-groups'), trTag);
        deleteRelatedElems(span.attr('data-hlrange'), trTag);

        if (trTag.find('.variationclick').length === 0) {
            //if no more then remove row
            trTag.remove();
        }


    });

    function modifyRowTag(beg, end, data_groups, trTag) {
        function getIndexOfBeg() {
            const begs = data_groups.split(';')[0].split(',').map(function (x) {
                return x.split(':')[1].split('-')[0];
            });
            return begs.indexOf(beg.toString());
        }

        const indexOfRemovingElement = getIndexOfBeg();
        let arhetypes = data_groups.split(';');// split on arhectypes of 0:beg1-end1,0:beg2-end2,...
        let newDataGroups = arhetypes.flatMap(function (list) {
            let newArr = list.split(',');
            newArr.splice(indexOfRemovingElement, 1);
            return newArr.join(',');
        }).join(';');
        trTag.attr('data-groups', newDataGroups);
        //#TODO should we also update power of group?
        //trTag.first.second(strTag.first.text)
    }

    function deleteRelatedElems(range, trTag) {
        const selector = '[data-hlrange=' + range + ']';
        let relatedElems = trTag.find(selector);
        relatedElems.remove();
    }

    $("tbody").on("contextmenu", "tr td tt span.variationclick, tr td tt code.variationclick", function (e) {
        //e.preventDefault(); //stop  default behaviour
        //e.stopPropagation();// stop bubbling TODO one is extra or even redundant
        currentSpan = $(e.target);
        //emitCustomEvent(removeSelectedCloneEvent); testing purposes
    })

});
