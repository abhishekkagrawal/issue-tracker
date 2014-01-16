/*
 *      Document    : update_issue_validation.js
 *      Author      : Dinesh Saini
 *      Created on  : 30/12/2013
 *      Description : java script validation file for issue update
 */


var spaceRegex = '^[\\S][\\S]*$';
var numRegExp = '^[0-9]+$';
var nameRegExp = '^[a-zA-Z][a-zA-Z]+$';
var index = -1;
var errorsList = new Array();
var trackerList;

/*
 * Validates issue update data entered by user.
 *
 * return {boolean}
 */

function validateUpdateIssueForm(status) {
    var status = status;
    var issueSubject = $('input[name="issueSubject"]').val();
    var description;
    if (status === 1) {
        description = $('#updateIssueIssueAction_description').val();
        assigneeName = document.getElementById('updateIssueIssueAction_assigneeName');
    }
    if (status === 0) {
        description = $('#addNewIssueIssueAction_description').val();
        assigneeName = document.getElementById('addNewIssueIssueAction_assigneeName');
    }
    var issueDocs = $('input[name="issueDocs"]').val();
    var issueStartDate = $('input[name="issueStartDate"]').val();
    var issueEndDate = $('input[name="issueDueDate"]').val();
    var estimatedTime = $('input[name="estimatedTime"]').val();
    errorsList = new Array();
    index = -1;
    $('.errListData').remove();

    (validate_subject(issueSubject));
    (validate_description(description));
    (validate_assignee(assigneeName));
    (validate_dates(issueStartDate, issueEndDate));
    (validate_estimated_time(estimatedTime));
    (validate_issue_docs(issueDocs));

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
 * Validates subject of issue.
 *
 * return {boolean}
 */
function validate_subject(subject) {
    if (subject === null || subject === '')
    {
        index = index + 1;
        errorsList[index] = 'Subject required';
        return false;
    }
    return true;
}
/*
 * Validates description of issue entered by user.
 *
 * return {boolean}
 */
function validate_description(description)
{
    if (description === null || description === '')
    {
        index = index + 1;
        errorsList[index] = 'Description required';
        return false;
    }
}
/*
 * Validates assignee name.
 *
 * return {boolean}
 */
function validate_assignee(assignee) {
    if (assignee.options[assignee.selectedIndex].text === '-Select Assignee-')
    {
        index = index + 1;
        errorsList[index] = 'Select assignee';
        return false;
    }
    return true;
}
/*
 * Validates issue start date and end date.
 *
 * return boolean
 */
function validate_dates(startDate, endDate) {
    if (startDate === null || startDate === '' || endDate === null
            || endDate === '')
    {
        index = index + 1;
        errorsList[index] = 'Start date & Due date required';
        return false;
    }
    else if (startDate > endDate)
    {
        index = index + 1;
        errorsList[index] = 'Start date must before or on due date';
        return false;
    } else {
        return true;
    }
}
/*
 * Validates issue document added by user.
 *
 * return {boolean}
 */
function validate_issue_docs(issueDoc) {
    if (issueDoc === null || issueDoc === '')
    {
        index = index + 1;
        errorsList[index] = 'Issue file required';
        return false;
    }
}
/*
 * Validates estimated time entered by user.
 *
 * return {boolean}
 */
function validate_estimated_time(estimatedHour) {
    if (estimatedHour === null || estimatedHour === '')
    {
        index = index + 1;
        errorsList[index] = 'Estimated time required';
        return false;
    }
    else if (!estimatedHour.match(numRegExp)) {
        index = index + 1;
        errorsList[index] = 'Invalid estimated time ';
        return false;
    } else {
        return true;
    }
}

$(document).ready(function() {
    $('#issueUpdateBox').hide();
});
function showUpdate() {
    $('#issueUpdateBox').show();
}
