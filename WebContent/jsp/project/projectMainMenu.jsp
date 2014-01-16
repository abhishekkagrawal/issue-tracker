<%-- Copyright 2014 IssueTracker --%>
<%--
    Document   : projectMainMenu
    Created on : Dec 5, 2013, 1:53:59 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<link href="css/AdminMenuItem.css" rel="stylesheet" type="text/css" />
<link href="css/AdminHomePage.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="main-menu">
		<a href="projectOverviewAction">Overview</a>
		<s:if
			test="#session.empType =='Admin' || #session.empType == 'Project Manager'">
			<a href="assignMemberAction">Project Members</a>
		</s:if>

		<s:iterator value="projectModuleList">
			<s:if test="%{#session.projectStatus == 1}">
				<a href="<s:property value="actionURL"/>"><s:property
						value="moduleType" /></a>
			</s:if>
			<s:else>
				<s:if test="%{moduleType != 'New issue'}">
					<a href="<s:property value="actionURL"/>"><s:property
							value="moduleType" /></a>
				</s:if>
			</s:else>

		</s:iterator>

		<s:if
			test="#session.empType =='Admin' || #session.empType == 'Project Manager'">
			<s:if test="#session.projectStatus != 0">
				<a href="projectSettingAction">Settings</a>
			</s:if>
		</s:if>

	</div>
</body>
</html>
