<%-- Copyright 2014 IssueTracker --%>
<%--
    Document   : projectSetting
    Created on : Dec 20, 2013, 6:14:18 PM
    Author     : Dinesh Saini
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="getText('global.jsp.projectSettings')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<link href="css/AdminMenuItem.css" rel="stylesheet" type="text/css" />
<link href="css/Projects.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script src="js/project_setting_validation.js"></script>
<script src="js/common.js"></script>
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
				<s:property value="#session['projIdentifier']" />
			</h2>
		</div>

		<h3>Settings</h3>
		<div id="errorField" style="border: 2px #ff0000 solid; display: none;">
			<ul class="errList"
				style="color: #ff0000; font-size: 12px; margin: 5px;">
			</ul>
		</div>

		<div id="projectSettingUpdateForm">
			<s:form action="updateProjectSettingsAction">
				<s:if test="hasActionMessages()">
					<div class="welcome">
						<s:actionmessage />
					</div>
				</s:if>
				<s:hidden value="%{projectBean.projectId}" name="projectId" />
				<s:textfield key="projectName" value="%{projectBean.projectName}"
					requiredLabel="true" size="60" cssStyle="margin-bottom:7px;"
					label="Project Name" />
				<s:textarea key="description" value="%{projectBean.description}"
					requiredLabel="true" rows="6" cols="70"
					cssStyle="margin-bottom:7px;" label="Description" />
				<s:textfield key="identifier" value="%{projectBean.identifier}"
					requiredLabel="true" disabled="true" size="40"
					cssStyle="margin-bottom:7px;" label="Identifier" />
				<s:textfield key="homePage" value="%{projectBean.homePage}"
					requiredLabel="true" size="60" cssStyle="margin-bottom:7px;"
					label="HomePage" />
				<s:checkboxlist list="trackerList"
					value="%{projectBean.prjctTrckerList}" requiredLabel="true"
					name="trckrSlctedList" cssStyle="margin-bottom:7px;"
					label="Tracker" />
				<s:checkboxlist list="moduleList"
					value="%{projectBean.projectModuleList}" requiredLabel="true"
					name="moduleSlctdList" cssStyle="margin-bottom:7px;"
					label="Modules" />
				<tr>
					<td><s:submit label="Submit"
							onkeypress="return validateAddProjectForm()"
							onclick="return validateAddProjectForm()" /></td>
				</tr>
			</s:form>
		</div>
	</div>

</body>

</html>
