/*
 *      Document    : login_form.js
 *      Author      : Dinesh Saini
 *      Created on  : 29/12/2013
 *      Description : Login page validator file
 */

/*
 * Validates user's login credential.
 *
 * @returns {Boolean}
 */
function validateLogin()
{
    var login = $('input[name="userId"]').val();
    var pwd = $('input[name="password"]').val();
    var loginErrorList = new Array();
    if (login === null || login === '' || pwd === null || pwd === '')
    {
        loginErrorList.push('Login & Password required');
        if (loginErrorList.length > 0) {
            $('#errorField').show();
        }
        $('.errListData').remove();
        $('.errors').remove();

        for (var i = 0; i < loginErrorList.length; i++)
        {
            var errText = loginErrorList[i];
            $('.errList').append('<li class="errListData">' + errText + '</li>');
        }
        return false;
    }
    return true;
}


