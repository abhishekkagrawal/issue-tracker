/*
 * Name : Dinesh Saini
 * Date : 29/12/2013
 * Purpose : java script validation file for add new issue
 */

var spaceRegex = '^[\\S][\\S]*$';
var numRegExp = '^[0-9]+$';
var nameRegExp = '^[a-zA-Z][a-zA-Z]+$';
var index = -1;
var errorsList = new Array();
var trackerList;

function validateAddIssueForm() {

    var issueSubject = $('input[name="issueSubject"]').val();
    var description = $('#addNewIssueIssueAction_description').val();
    var assigneeName = document.getElementById('addNewIssueIssueAction_assigneeName');
    var issueDocs = $('input[name="issueDocs"]').val();
    var issueStartDate = $('input[name="issueStartDate"]').val();
    var issueEndDate = $('input[name="issueDueDate"]').val();
    var estimatedTime = $('input[name="estimatedTime"]').val();
    var i = 0;
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
        $('#errorField').show();
        $('.errorMessage').remove();
        var all = document.getElementsByClassName('errorLabel');
        for (i = 0; i < all.length; i++) {
            all[i].style.color = '#000000';
        }
        for (i = 0; i < errorsList.length; i++)
        {
            var errText = errorsList[i];
            $('.errList').append('<li class="errListData">' + errText + '</li>');
        }
    }
    else {
        $('#errorField').hide();
        return true;
    }
    return false;
}

function validate_subject(subject) {
    if (subject === null || subject === '')
    {
        index = index + 1;
        errorsList[index] = 'Subject required';
        return false;
    }
    return true;
}
function validate_description(description)
{
    if (description === null || description === '')
    {
        index = index + 1;
        errorsList[index] = 'Description required';
        return false;
    }
}
function validate_assignee(assignee) {
    if (assignee.options[assignee.selectedIndex].text === '-Select Assignee-')
    {
        index = index + 1;
        errorsList[index] = 'Select assignee';
        return false;
    }

    return true;
}
function validate_dates(startDate, endDate) {
    if (startDate === null || startDate === '' || endDate === null || endDate === '')
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
function validate_issue_docs(issueDoc) {
    if (issueDoc === null || issueDoc === '')
    {
        index = index + 1;
        errorsList[index] = 'Issue file required';
        return false;
    }
}
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
