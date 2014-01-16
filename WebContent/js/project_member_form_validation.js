/*
 *      Document    : project_member_form_validation.js
 *      Author      : Dinesh Saini
 *      Created on  : 29/12/2013
 *      Description : java script file for add project member form
 */

var index = -1;
var errorsList = new Array();

/*
 * Validates add project member form details.
 *
 * @returns {Boolean}
 */
function validate_proj_member()
{
    var memberList = $('input[name="selectedMemList"]');
    var rolesList = $('input[name="empRolesTypeList"]');
    var i = 0;
    errorsList = new Array();
    index = -1;
    $('.errListData').remove();

    if (!validate_member(memberList)) {
        index = index + 1;
        errorsList[index] = 'Select one or more members';
    }
    if (!validate_roles(rolesList)) {
        index = index + 1;
        errorsList[index] = 'Select one or more roles';
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

function validate_member(memberList) {
    for (var i = 0; i < memberList.length; i++) {
        if (memberList[i].checked) {
            return true;
        }
    }
    return false;
}

function validate_roles(roleList) {
    for (var i = 0; i < roleList.length; i++) {
        if (roleList[i].checked) {
            return true;
        }
    }
    return false;
}

$(document).ready(function() {
    $('#updateRoles').hide();
});
function updateRoles(id) {
    $('#form' + id).show();
    $('#empRoleType' + id).hide();
    return false;
}
function hideUpdate(id) {
    $('#form' + id).hide();
    $('#empRoleType' + id).show();
    return false;
}
