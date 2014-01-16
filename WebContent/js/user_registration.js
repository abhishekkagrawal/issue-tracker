/*
 *      Document    : user_registration.js
 *      Author      : Dinesh Saini
 *      Created on  : 28/12/2013
 *      Description : Javascript file for user registration validation
 */

var spaceRegex = '^[\\S][\\S]*$';
var numRegExp = '^[0-9]+$';
var nameRegExp = '^[a-zA-Z][a-zA-Z]+$';
var alphaNumeric = '((^[0-9]+[a-z]+)|(^[a-z]+[0-9]+))+[0-9a-z]+$';
var emailRegex = '^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$';
var index = -1;
var errorsList = new Array();

/*
 * Function validateAddUserForm perform client side validation operation
 * for new user registration form.
 * @returns {Boolean}
 */
function validateAddUserForm()
{

    var login = $('input[name="login"]').val();
    var pwd = $('input[name="password"]').val();
    var cpwd = $('input[name="password1"]').val();
    var fName = $('input[name="firstName"]').val();
    var lName = $('input[name="lastName"]').val();
    var email = $('input[name="email"]').val();
    var mobile = $('input[name="mobile"]').val();
    var empType = document.getElementById('addEmployeeAction_employeeCategory');

    errorsList = new Array();
    index = -1;
    $('.errListData').remove();

    (validate_login(login));
    (validate_password(pwd, cpwd));
    (validate_fname(fName));
    (validate_lname(lName));
    (validate_email(email));
    (validate_mobile(mobile));
    (validate_emp_type(empType));

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
 * Function validate_login validates loginId entered by user.
 *
 * return {boolean}
 */
function validate_login(name) {
    if (name === null || name === '') {
        index = index + 1;
        errorsList[index] = 'Login required';
        return false;
    }
    else if (!name.match(spaceRegex)) {
        index = index + 1;
        errorsList[index] = 'Invalid login';
        $('input[name="login"]').val('');
        return false;
    } else {
        return true;
    }
}
/*
 * Function validates password entered by user.
 *
 * return {boolean}
 */
function validate_password(pwd, cpwd)
{
    if (pwd !== cpwd) {
        index = index + 1;
        errorsList[index] = 'Password & confirm password should be same';
        return false;
    }
    if (pwd === null || pwd === '' || cpwd === null || cpwd === '') {
        index = index + 1;
        errorsList[index] = 'Password & confirm passowrd required';
        return false;
    }
    else if (pwd.length !== 6 || !pwd.match(alphaNumeric)) {
        index = index + 1;
        errorsList[index] = 'Password should be of 6 digit with alphanumeric character';
        return false;
    }
    else {
        return true;
    }
}
/*
 * Function validates user's first name.
 *
 * return {boolean}
 */
function validate_fname(name)
{
    if (name === null || name === '') {
        index = index + 1;
        errorsList[index] = 'First name required';
        return false;
    }
    else if (!name.match(nameRegExp)) {
        index = index + 1;
        errorsList[index] = 'Invalid first name';
        return false;
    } else {
        return true;
    }
}
/*
 * Function validates user's last name.
 *
 * return {boolean}
 */
function validate_lname(name)
{
    if (name === null || name === '') {
        index = index + 1;
        errorsList[index] = 'Last name required';
        return false;
    }
    else if (!name.match(nameRegExp)) {
        index = index + 1;
        errorsList[index] = 'Invalid last name';
        return false;
    } else {
        return true;
    }
}
/*
 * Function validates user's email id.
 *
 * return {boolean}
 */
function validate_email(email) {
    if (email === null || email === '') {
        index = index + 1;
        errorsList[index] = 'Email required';
        return false;
    }
    else if (!email.match(emailRegex)) {
        index = index + 1;
        errorsList[index] = 'Invalid email';
        return false;
    } else {
        return true;
    }
}
/*
 * Function validates mobile number entered by user.
 *
 * return {boolean}
 */
function validate_mobile(mobile) {
    if (mobile === null || mobile === '') {
        index = index + 1;
        errorsList[index] = 'Mobile Number required';
        return false;
    }
    else if (!mobile.match(numRegExp)) {
        index = index + 1;
        errorsList[index] = 'Invalid mobile number';
        return false;
    }
    else if (mobile.length !== 10)
    {
        index = index + 1;
        errorsList[index] = 'Mobile number should be 10 digit';
        return false;
    } else {
        return true;
    }
}
/*
 * Function validates emp type selected by user.
 *
 * return {boolean}
 */
function validate_emp_type(empType) {

    if (empType.options[empType.selectedIndex].text === 'Select')
    {
        index = index + 1;
        errorsList[index] = 'Select employee type';
        return false;
    }

}
