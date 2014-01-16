/*
 *      Document    : add_project_document_validation.js
 *      Author      : Dinesh Saini
 *      Created on  : 30/12/2013
 *      Description : java script file for validating project documents
 */

var spaceRegex = '^[\\S][\\S]*$';
var numRegExp = '^[0-9]+$';
var nameRegExp = '^[a-zA-Z][a-zA-Z]+$';
var docRegex = '^[a-zA-Z][a-zA-Z0-9 ._&@$=#%]+$';
var index = -1;
var errorsList = new Array();
var trackerList;
/*
 * Validates project add document form data.
 *
 * @returns {Boolean}
 */
function validateProjDocsForm() {
    var docTitle = $('input[name="docTitle"]').val();
    var docDescription = $('#addProjDocumentAction_docDesciption').val();
    var issueDocs = $('input[name="projectDoc"]').val();
    errorsList = new Array();
    index = -1;
    $('.errListData').remove();
    (validate_doc_title(docTitle));
    (valdate_desc(docDescription));
    (validate_proj_docs(issueDocs));

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
 * Validates project document title.
 *
 * return boolean
 */
function validate_doc_title(title)
{
    if (title === null || title === '')
    {
        index = index + 1;
        errorsList[index] = 'Title required';
        return false;
    } else if (!title.match(docRegex))
    {
        index = index + 1;
        errorsList[index] = 'Invalid document title';
        return false;
    } else {
        return true;
    }
}
/*
 * Validates project document description.
 *
 * return boolean
 */
function valdate_desc(description)
{
    if (description === null || description === '')
    {
        index = index + 1;
        errorsList[index] = 'Description required';
        return false;
    }
    return true;
}
/*
 * Validates project document.
 *
 * return boolean
 */
function validate_proj_docs(document)
{
    if (document === null || document === '')
    {
        index = index + 1;
        errorsList[index] = 'Document required';
        return false;
    }
    return true;
}
