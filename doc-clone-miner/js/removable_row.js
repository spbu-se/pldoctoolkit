/**
 * Allows remove row in a table by click on X in row
 **/

$(window).ready(function () {
    //  Remove table row by click on specified icon X
    $(".table").on("click", ".remove_tr", function () {
        $(this).closest('tr').remove();
    });
});