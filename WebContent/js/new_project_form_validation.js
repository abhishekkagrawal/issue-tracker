/*
 *      Document    : user_registration.js
 *      Author      : Dinesh Saini
 *      Created on  : 29/12/2013
 *      Description : java script file for new project form validation
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
 * Validates new project data entered by user.
 *
 * @returns {Boolean}
 */
function validateAddProjectForm() {
    var projectName = $('input[name="projectBean.projectName"]').val();
    var projectDesc = $('#addProjectAction_projectBean_description').val();
    var identifier = $('input[name="projectBean.identifier"]').val();
    var homePage = $('input[name="projectBean.homePage"]').val();
    var tracker = $('input[name="trackerSelected"]');
    var moduleList = $('input[name="slctdModuleList"]');
    errorsList = new Array();
    index = -1;
    $('.errListData').remove();

    (validateNewPrjctName(projectName));
    (validateNewDesc(projectDesc));
    (validateIdentifier(identifier));
    (validateHomepage(homePage));
    if (!validateTracker(tracker)) {
        index = index + 1;
        errorsList[index] = 'Select one or more tracker';
    }
    if (!validateMmodule(moduleList)) {
        index = index + 1;
        errorsList[index] = 'Select one or more module';
    }

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
 * Validates new project Name.
 *
 * return boolean
 */
function validateNewPrjctName(pName) {
    if (pName === null || pName === '') {
        index = index + 1;
        errorsList[index] = 'Project name required';
        return false;
    }
}
/*
 * Validates new project description.
 */
function validateNewDesc(newDescrp) {
    if (newDescrp === null || newDescrp === '') {
        index = index + 1;
        errorsList[index] = 'Description required';
        return false;
    }
}
/*
 * Validates new project identifier.
 *
 * return boolean.
 */

function validateIdentifier(newIdentrifier)
{
    if (newIdentrifier === null || newIdentrifier === '')
    {
        index = index + 1;
        errorsList[index] = 'Identifier required';
        return false;
    }
    else if (!newIdentrifier.match(spaceRegex)) {
        index = index + 1;
        errorsList[index] = 'Invalid identifier';
        return false;
    }
    else {
        return true;
    }
}
/*
 * Validates new project homePage URL.
 *
 * return boolean
 */
function validateHomepage(newHomepage)
{
    if (newHomepage === null || newHomepage === '')
    {
        index = index + 1;
        errorsList[index] = 'Homepage required';
        return false;
    }
    else if (!newHomepage.match(urlRegex)) {
        index = index + 1;
        errorsList[index] = 'Invalid homepage';
        return false;
    }
    else {
        return true;
    }
}
/*
 * Validates new project tracker type.
 *
 * return boolean
 */
function validateTracker(list) {
    for (i = 0; i < list.length; i++) {
        if (list[i].checked) {
            return true;
        }
    }
    return false;
}
/*
 * Validates new project module list.
 *
 * return boolean
 */
function validateMmodule(list) {
    for (i = 0; i < list.length; i++) {
        if (list[i].checked) {
            return true;
        }
    }
    return false;
}

function sync()
{
    identifier.value = projectName.value.toString().replace(/\s+/g, '-').toLowerCase();
}
