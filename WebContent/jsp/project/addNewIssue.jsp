<%-- Copyright 2014 IssueTracker --%>
<%--
    Name : Dinesh Saini
    Date : 16/12/2013
    Purpose : JSP page for adding new issue
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-dojo-tags" prefix="sx"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="getText('global.jsp.newIssue')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<link href="css/AdminHomePage.css" rel="stylesheet" type="text/css" />
<link href="css/addIssue.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script src="js/update_issue_validation.js" type="text/javascript"></script>
<sx:head />
</head>
<s:head />
<body>
	<s:if test="%{#session == null">
		<jsp:forward page="../Login.jsp" />
	</s:if>
	<s:if test="#session['login']!='true'">
		<jsp:forward page="../Login.jsp" />
	</s:if>
	<%@include file="../header.jsp"%>
	<%@include file="projectMainMenu.jsp"%>
	<div id="contentWrapper">
		<div id="projIdentifier">
			<h2>
				<s:property value="#session['projIdentifier']" />
			</h2>
		</div>
		<h3>New issue</h3>
		<div id="errorField" style="border: 2px #ff0000 solid; display: none;">
			<ul class="errList"
				style="color: #ff0000; font-size: 12px; margin: 5px;">
			</ul>
		</div>
		<div id="addIssueForm">
			<s:if test="hasActionMessages()">
				<div class="welcome">
					<s:actionmessage />
				</div>
			</s:if>
			<s:if test="hasActionErrors()">
				<div class="errors">
					<s:actionerror />
				</div>
			</s:if>
			<s:form action="addNewIssueIssueAction" enctype="multipart/form-data"
				method="post">
				<s:select list="trackerTypeList" requiredLabel="true"
					name="trackerType" label="Tracker" cssStyle="margin-bottom:5px;" />
				<s:textfield key="issueSubject" label="Subject" requiredLabel="true"
					cssStyle="margin-bottom:5px;" />
				<s:textarea key="description" rows="6" cols="70"
					requiredLabel="true" label="Description"
					cssStyle="margin-bottom:5px;" />
				<s:select list="statusTypeList" requiredLabel="true"
					name="statusType" label="Status" cssStyle="margin-bottom:5px;" />
				<s:select list="priorityTypeList" requiredLabel="true"
					name="priorityType" label="Priority" cssStyle="margin-bottom:5px;" />
				<s:select list="projectMembers" name="assigneeName"
					requiredLabel="true" headerValue="-Select Assignee-" headerKey="-1"
					listValue="empName" listKey="empId" label="Assignee"
					cssStyle="margin-bottom:5px;" />
				<s:file name="issueDocs" label="File" requiredLabel="true"
					cssStyle="margin-bottom:5px;" />
				<sx:datetimepicker name="issueStartDate"
					label="Start Date(dd-MMM-yyyy)" startDate="%{2000-01-01}"
					displayFormat="dd-MMM-yyyy" value="todayDate"
					cssStyle="margin-bottom:5px;" />
				<sx:datetimepicker name="issueDueDate" label="Due Date(dd-MMM-yyyy)"
					startDate="%{2000-01-01}" displayFormat="dd-MMM-yyyy"
					value="todayDate" cssStyle="margin-bottom:5px;" />
				<s:textfield name="estimatedTime" requiredLabel="true"
					label="Estimated time(HH)" cssStyle="margin-bottom:15px;" />
				<tr>
					<td style="text-align: left;"><s:submit theme="simple"
							onkeypress="return validateUpdateIssueForm(0);"
							onclick="return validateUpdateIssueForm(0);" /></td>
				</tr>
			</s:form>
		</div>
	</div>
</body>
</html>