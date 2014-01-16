<%-- Copyright 2014 IssueTracker --%>
<%--
    Name : Dinesh Saini
    Date : 17/12/2013
    Purpose : JSP page for Overview of Issues
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-dojo-tags" prefix="sx"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/addIssue.css" rel="stylesheet" type="text/css" />
<title><s:property value="getText('global.jsp.issue')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/update_issue_validation.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<sx:head />
</head>
<s:head />
<body>
	<s:if test="%{#session ==null">
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
				<s:property value="issueBean.projectIdentifier" />
			</h2>
		</div>
		<s:if test="hasActionMessages()">
			<div class="welcome">
				<s:actionmessage />
			</div>
		</s:if>
		<s:if test="hasActionErrors()">
			<div class="errors">
				<script>
                        showUpdate();
                    </script>
			</div>
		</s:if>

		<s:property value="updateFlag" />
		<div id="issueOverview">
			<div id="issueHeading">
				<h2>
					<s:property value="issueBean.issueIdentifier" />
				</h2>
			</div>
			<div id="overviewBody">
				<s:if test="%{#session.projectStatus != 0}">
					<s:if test="#session.empType !='Admin'">
						<p style="float: right;">
							<s:a href="#updateBox" onkeypress="showUpdate()"
								onclick="showUpdate()">
								<img src="images/Issue update.png" width="16" height="14"
									alt="Issue update" /> Update</s:a>
						</p>
					</s:if>
				</s:if>
				<h3>
					<s:property value="issueBean.issueSubject" />
				</h3>
				<p>
					Added by
					<s:a action="usersDetailAction">
						<s:param name="userId" value="issueBean.assignerLoginId" />
						<s:property value="issueBean.assigner" />
					</s:a>
					on
					<s:property value="issueBean.issueUpdateDate" />
				</p>
				<table class="attributes">
					<caption>
						<strong> Issues overview </strong>
					</caption>
					<tbody>
						<tr>
							<th scope="row">Status:</th>
							<td><s:property value="issueBean.statusType" /></td>
							<th scope="row">Start Date:</th>
							<td><s:property value="issueBean.issueStartDate" /></td>
						</tr>
						<tr>
							<th scope="row">Priority:</th>
							<td><s:property value="issueBean.priorityType" /></td>
							<th scope="row">Due Date:</th>
							<td><s:property value="issueBean.issueDueDate" /></td>
						</tr>
						<tr>
							<th scope="row">Assignee:</th>
							<td><s:a action="usersDetailAction">
									<s:param name="userId" value="issueBean.assignerLoginId" />
									<s:property value="issueBean.assignee" />
								</s:a></td>
							<th scope="row">Estimated hour:</th>
							<td><s:property value="issueBean.estimatedHour" /></td>
						</tr>
						<tr>
							<th scope="row">End date:</th>
							<td><s:if test="issueBean.issueEndDate != null">
									<s:property value="issueBean.issueEndDate" />
								</s:if> <s:else>
                                        -----
                                    </s:else></td>
						</tr>
					</tbody>
				</table>
				<hr>
				<p>
					<strong>Description</strong>
				</p>
				<p>
					<s:property value="issueBean.issueDesc" />
				</p>
				<s:iterator value="issueDocList">
					<p>
						<a href="${fileLocation}"> <img src="images/file.png"
							width="16" height="16" alt="file" /> <s:property value="fileName" /></a>
						&nbsp;&nbsp;
						<s:property value="empName" />
						,
						<s:property value="insertionDate" />
					</p>
				</s:iterator>
			</div>
		</div>

		<div id="issueUpdateBox">
			<h4 id="updateBox">Update</h4>
			<div id="errorField"
				style="border: 2px #ff0000 solid; display: none;">
				<ul class="errList"
					style="color: #ff0000; font-size: 12px; margin: 5px;">
				</ul>
			</div>
			<div id="addIssueForm">

				<s:form action="updateIssueIssueAction"
					enctype="multipart/form-data" method="post">
					<s:hidden value="%{issueBean.issueId}" name="issueNum" />
					<s:hidden value="%{issueBean.projectId}" name="issueProjectId" />
					<s:select list="trackerTypeList" requiredLabel="true"
						value="{issueBean.trackerType}" label="Tracker" name="trackerType"
						cssStyle="margin-bottom:5px;" />
					<s:textfield key="issueSubject" requiredLabel="true"
						value="%{issueBean.issueSubject}" label="Subject"
						cssStyle="margin-bottom:5px;" />
					<s:textarea key="description" requiredLabel="true"
						value="%{issueBean.issueDesc}" rows="6" cols="70"
						label="Description" cssStyle="margin-bottom:5px;" />
					<s:select list="statusTypeList" requiredLabel="true"
						value="{issueBean.statusType}" name="statusType" label="Status"
						cssStyle="margin-bottom:5px;" />
					<s:select list="priorityTypeList" requiredLabel="true"
						value="{issueBean.priorityType}" name="priorityType"
						label="Priority" cssStyle="margin-bottom:5px;" />
					<s:select list="projectMembers" requiredLabel="true"
						value="{issueBean.assigneeId}" name="assigneeName"
						headerValue="-Select Assignee-" headerKey="-1" listValue="empName"
						listKey="empId" label="Assignee" cssStyle="margin-bottom:5px;" />
					<sx:datetimepicker name="issueStartDate"
						label="Start Date(dd-MMM-yyyy)" displayFormat="dd-MMM-yyyy"
						value="issueBean.issueStartDate" cssStyle="margin-bottom:5px;" />
					<sx:datetimepicker name="issueDueDate"
						label="Due Date (dd-MMM-yyyy)" displayFormat="dd-MMM-yyyy"
						value="issueBean.issueDueDate" cssStyle="margin-bottom:5px;" />
					<s:textfield name="estimatedTime" requiredLabel="true"
						value="%{issueBean.estimatedHour}" label="Estimated Time(Hours)"
						cssStyle="margin-bottom:15px;" />
					<s:file name="issueDocs" label="File" requiredLabel="true"
						cssStyle="margin-bottom:5px;" />
					<tr>
						<td style="text-align: left;"><s:submit theme="simple"
								onkeypress="return validateUpdateIssueForm(1);"
								onclick="return validateUpdateIssueForm(1);" /></td>
					</tr>
				</s:form>
			</div>
		</div>

	</div>

</body>
</html>
