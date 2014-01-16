<%-- Copyright 2014 IssueTracker --%>
<%--
    Name : Dinesh Saini
    Created on : 29/11/2013
    Purpose : JSP page for adding new project

--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="getText('global.jsp.newProject')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<link href="css/AdminHomePage.css" rel="stylesheet" type="text/css" />
<link href="css/AddProject.css" rel="stylesheet" type="text/css" />
<link href="css/AdminMenuItem.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/new_project_form_validation.js"></script>
<script src="js/common.js" type="text/javascript"></script>
</head>
<s:head />
<body>
	<s:if test="#session==null || #session['login']!='true' ">
		<jsp:forward page="/jsp/Login.jsp" />
	</s:if>
	<s:if test="#session['empTypeId']!=1">
		<jsp:forward page="/page-not-found.jsp" />
	</s:if>
	<%@include file="header.jsp"%>
	<div id="main-menu">
		<s:if test="#session.empType =='Admin'">
			<a href="addProjectFormAction">New Project</a>
		</s:if>
		<s:a href="" cssStyle="padding-left: 1px; padding-right: 0px;" />
	</div>

	<div id="wrapper">
		<div id="addProjectFormBody">
			<h3>Add Project</h3>

			<div id="errorField"
				style="border: 2px #ff0000 solid; display: none;">
				<ul class="errList"
					style="color: #ff0000; font-size: 12px; margin: 5px;">
				</ul>
			</div>

			<div id="addProjectForm">
				<s:form action="addProjectAction">
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
					<s:textfield key="projectBean.projectName" id="projectName"
						onkeyup="sync()" requiredLabel="true" size="60"
						cssStyle="margin-bottom:7px;" label="Project Name" />
					<s:textarea key="projectBean.description" rows="6" cols="70"
						requiredLabel="true" cssStyle="margin-bottom:7px;"
						label="Description" />
					<s:textfield key="projectBean.identifier" id="identifier"
						requiredLabel="true" size="40" cssStyle="margin-bottom:7px;"
						label="Identifier" />
					<s:textfield key="projectBean.homePage" size="60"
						requiredLabel="true" cssStyle="margin-bottom:7px;"
						label="HomePage" />
					<s:checkboxlist list="trackerList" name="trackerSelected"
						requiredLabel="true" cssStyle="margin-bottom:7px;" label="Tracker" />
					<s:checkboxlist list="moduleList" name="slctdModuleList"
						requiredLabel="true" cssStyle="margin-bottom:35px;"
						label="Modules" />
					<tr>
						<td align="center" colspan="2"><s:submit label="Submit"
								theme="simple" onkeypress="return validateAddProjectForm();"
								onclick="return validateAddProjectForm();" />&nbsp;&nbsp;&nbsp;&nbsp;
							<s:reset value="Reset" theme="simple" /></td>
					</tr>
				</s:form>
			</div>
		</div>
	</div>
</body>

</html>
