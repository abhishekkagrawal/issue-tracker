/*
 *      Document    : issueIndexSearch.js
 *      Name        : Dinesh Saini
 *      Created on  : 02/01/2013
 *      Description : java script file for issue index search
 */


var confirmed = false;
$(function() {
    /* Search functionality */
    var oTable = $('#models').dataTable({
        'bPaginate': false,
        'bLengthChange': false,
        'bFilter': true,
        'bSort': false,
        'bInfo': false,
        'bAutoWidth': false,
        'bStateSave': false
    });
    $('thead input').keyup(function() {
        /* Filter on the column (the index) of this element */
        oTable.fnFilter(this.value, $('thead input').index(this));
    });

    $('thead input').focus(function() {
        if (this.className === 'search_init') {
            this.className = '';
            this.value = '';
        }
    });

    $('thead input').blur(function() {
        if (this.value === '') {
            this.className = 'search_init';
            this.value = asInitVals[$('thead input').index(this)];
        }
    });
});
