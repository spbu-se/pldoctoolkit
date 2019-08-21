/**
 * Allows selects (selection determine by hotkey+click onto the desired rows) several rows(clones)
 * and process it by choosing appropriate  option in context menu. It's for fuzzySearch mode
 * TODO not implemented processing of selected clones
 */

$(window).ready(function () {
    window.saveAsDictSelected = function () {
        //TODO not implemeted python
        qtab.clog('They saved as ditc elems' );
        $('table tr').removeClass(className);
    };
    window.saveAsInfElemsSelected = function () {
        //TODO need to implement logic python
        qtab.clog('They saved as inf elems' );
        $('table tr').removeClass(className);
    };

    const className = 'selectableRow';
    const keyCode = 'KeyZ';//z button
    // class = selectable
    let isKeyDown = false;
    detectKeyDown();
    detectKeyUp();


    $('.table').on('click', 'tr', function () {

        if (!isKeyDown) {
            $('table tr').removeClass(className);
            alertQtab(false, false);
            return;
        }

        let trTag = $(this);
        if (trTag.hasClass(className)) {
            trTag.removeClass(className);
        } else {
            trTag.addClass(className);
        }
        isKeyDown = false; //due to we work in qtab that forcibly change window
    });

    $('.table').on('contextmenu', function () {
        const elems = $(this).find('tr.' + className);
        if (elems.length > 1) {
            alertQtab(true, true);
        } else {
            alertQtab(false, false);
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

