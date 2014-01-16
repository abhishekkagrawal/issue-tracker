/*
 *      Document    : update_user_info_validation.js
 *      Author      : Dinesh Saini
 *      Created on  : 30/12/2013
 *      Description : java script file for update user info validation
 */

var spaceRegex = '^[\\S][\\S]*$';
var numRegExp = '^[0-9]+$';
var nameRegExp = '^[a-zA-Z][a-zA-Z]+$';
var urlRegex = '^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]';
var emailRegex = '^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$';
var index = -1;
var errorsList = new Array();
var i = 0;

/*
 * Validates user's details.
 *
 * @returns {Boolean}
 */

function validateUpdateUserInfo() {
    var fName = $('input[name="users.firstName"]').val();
    var lName = $('input[name="users.lastName"]').val();
    var email = $('input[name="users.email"]').val();
    var phone = $('input[name="users.phone"]').val();

    errorsList = new Array();
    index = -1;
    $('.errListData').remove();
    (validate_newFirstName(fName));
    (validate_newLastname(lName));
    (validate_newEmail(email));
    (validate_newMobile(phone));

    if (errorsList.length > 0) {
        (function() {
            displayError(errorsList);
        })();
    } else {
        $('#errorField').hide();
        return true;
    }
    return false;
}
/*
 * Validates user's first name.
 *
 * return boolean
 */
function validate_newFirstName(fname)
{
    if (fname === null || fname === '') {
        index = index + 1;
        errorsList[index] = 'First name required';
        return false;
    }
    else if (!fname.match(nameRegExp)) {
        index = index + 1;
        errorsList[index] = 'Invalid first name';
        return false;
    } else {
        return true;
    }
}
/*
 * Validates user's last name.
 *
 * return boolean
 */
function validate_newLastname(lname)
{
    if (lname === null || lname === '') {
        index = index + 1;
        errorsList[index] = 'Last name required';
        return false;
    }
    else if (!lname.match(nameRegExp)) {
        index = index + 1;
        errorsList[index] = 'Invalid last name';
        return false;
    } else {
        return true;
    }
}
/*
 * Validate user's email id.
 *
 * return boolean
 */
function validate_newEmail(newEmail) {
    if (newEmail === null || newEmail === '') {
        index = index + 1;
        errorsList[index] = 'Email required';
        return false;
    }
    else if (!newEmail.match(emailRegex)) {
        index = index + 1;
        errorsList[index] = 'Invalid email';
        return false;
    } else {
        return true;
    }
}
/*
 * Validates user's mobile number.
 *
 * return bolean
 */
function validate_newMobile(newMobile) {
    if (newMobile === null || newMobile === '') {
        index = index + 1;
        errorsList[index] = 'Mobile Number required';
        return false;
    }
    else if (!newMobile.match(numRegExp)) {
        index = index + 1;
        errorsList[index] = 'Invalid mobile number';
        return false;
    }
    else if (newMobile.length !== 10)
    {
        index = index + 1;
        errorsList[index] = 'Mobile number should be 10 digit';
        return false;
    } else {
        return true;
    }
}

