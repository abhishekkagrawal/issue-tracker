/*
 *      Document    : project_setting_validation.js
 *      Author      : Dinesh Saini
 *      Created on  : 30/12/2013
 *      Description : edit project setting form validation file
 */

var spaceRegex = '^[\\S][\\S]*$';
var numRegExp = '^[0-9]+$';
var nameRegExp = '^[a-zA-Z][a-zA-Z]+$';
var urlRegex = '^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]';
var emailRegex = '^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$';
var index = -1;
var errorsList = new Array();

/*
 * Validates add project form data.
 *
 * @returns {Boolean}
 */
function validateAddProjectForm() {
    var projectName = $('input[name="projectName"]').val();
    var projectDesc = $('#updateProjectSettingsAction_description').val();
    var homePage = $('input[name="homePage"]').val();
    var tracker = $('input[name="trckrSlctedList"]');
    var moduleList = $('input[name="moduleSlctdList"]');

    errorsList = new Array();
    index = -1;
    $('.errListData').remove();
    var i = 0;

    (validate_project_name(projectName));
    (validate_description(projectDesc));
    (validate_homepage(homePage));
    if (!validate_module(moduleList)) {
        index = index + 1;
        errorsList[index] = 'Select one or more module';
    }
    if (!validate_tracker(tracker)) {
        index = index + 1;
        errorsList[index] = 'Select one or more tracker';
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

function validate_project_name(name) {
    if (name === null || name === '') {
        index = index + 1;
        errorsList[index] = 'Project name required';
        return false;
    }
}

function validate_description(desc) {
    if (desc === null || desc === '') {
        index = index + 1;
        errorsList[index] = 'Description required';
        return false;
    }
}

function validate_homepage(homePage)
{
    if (homePage === null || homePage === '')
    {
        index = index + 1;
        errorsList[index] = 'Homepage required';
        return false;
    }
    else if (!homePage.match(urlRegex)) {
        index = index + 1;
        errorsList[index] = 'Invalid homepage';
        return false;
    } else {
        return true;
    }
}
function validate_tracker(trackerList) {
    for (var i = 0; i < trackerList.length; i++) {
        if (trackerList[i].checked) {
            return true;
        }
    }
    return false;
}

function validate_module(moduleList) {
    for (var i = 0; i < moduleList.length; i++) {
        if (moduleList[i].checked) {
            return true;
        }
    }
    return false;
}
