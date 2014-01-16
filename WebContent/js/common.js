/*
 *      Document    : common.js
 *      Author      : Dinesh Saini
 *      Created on  : 15/01/2014
 *      Description : Javascript file for writing error on JSP page
 */

function displayError(errorsList) {
    $('#errorField').show();
    $('.errorMessage').remove();
    for (var i = 0; i < errorsList.length; i++)
    {
        var errText = errorsList[i];
        $('.errList').append('<li class="errListData">' + errText + '</li>');
    }
    return false;
}