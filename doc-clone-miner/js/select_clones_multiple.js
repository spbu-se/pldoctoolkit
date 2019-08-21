/**
 * Allows selects (selection determine by hotkey+click onto the desired clone in row) several clones {№}
 * and process it by choosing appropriate  option in context menu
 * TODO not implemented processing of selected clones
 *
 */


$(window).ready(function () {
    window.saveAsDictSelected = function () {
        //TODO not implemeted
        qtab.clog('They saved as ditc elems' );
        removeClassForTrChild(trTag);
    };
    window.saveAsInfElemsSelected = function () {
        //TODO not implemeted
        qtab.clog('They saved as inf elems' );
        removeClassForTrChild(trTag);
    };

    const className = 'selectableClone';
    const keyCode = 'KeyZ';//z button
    // class = selectable
    let isKeyDown = false;
    let trTag = undefined;

    function removeClassForTrChild(tr) {
        tr.find('.' + className).removeClass(className);
    }

    detectKeyDown();
    detectKeyUp();


    $('.table').on('click', 'span.variationclick', function () {

        if (!isKeyDown) {
            if (trTag !== undefined) {
                removeClassForTrChild(trTag)
            }
            trTag = undefined;
            alertQtab(false, false);
            return;
        }

        if (trTag === undefined) {
            trTag = $(this).closest('tr');
        }

        if (!trTag.is($(this).closest('tr'))) {
            removeClassForTrChild(trTag);
            trTag = $(this).closest('tr');
        }

        let span = $(this);

        if (span.hasClass(className)) {
            span.removeClass(className);
        } else {
            span.addClass(className);
        }
        isKeyDown = false; //due to we work in qtab that forcibly change window


    });

    $('.table').on('contextmenu', 'tr', function () {
        if (trTag === undefined) {
            // if no one selected
            alertQtab(false, false);
        } else if (!trTag.is($(this))) {
            alertQtab(false, false);
            removeClassForTrChild(trTag);
        } else {
            alertQtab(!trTag.hasClass('multiple'), true);//#TODO correct need
        }

    });


    function detectKeyDown() {
        $(window).on('keydown', function (e) {
            if (isKeyDown) {
                e.preventDefault();
            } else if (e.code === keyCode) {
                qtab.clog("Keydown");
                isKeyDown = true;
            }
        });
    }

    function detectKeyUp() {
        $(window).on('keyup', function (e) {
            if (!isKeyDown) {
                e.preventDefault();
            } else if (e.code === keyCode) {
                qtab.clog("KeyUp");
                isKeyDown = false;
            }
        });
    }

    function alertQtab(enableDictEntry, enableInfElem) {
        qtab.decide_enable_dict_selected(enableDictEntry);
        qtab.decide_enable_inf_selected(enableInfElem);
        qtab.clog('decide_enable_dict_selected(enableDictEntry)' + enableDictEntry);
        qtab.clog('decide_enable_inf_selected()' + enableInfElem);
    }

});