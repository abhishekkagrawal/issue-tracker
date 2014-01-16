<%-- Copyright 2014 IssueTracker --%>
<%--
    Name : Dinesh Saini
    Created on : 29/11/2013
    Purpose : JSP page for Project related tasks
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="getText('global.jsp.projectIndex')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<link href="css/AdminHomePage.css" rel="stylesheet" type="text/css" />
<link href="css/Projects.css" rel="stylesheet" type="text/css" />
<link href="css/AdminMenuItem.css" rel="stylesheet" type="text/css" />
</head>
<s:head />
<body>
	<s:if test="#session == null">
		<jsp:forward page="/jsp/Login.jsp" />
	</s:if>
	<s:if test="#session['login']!='true'">
		<jsp:forward page="/jsp/Login.jsp" />
	</s:if>
	<%@include file="header.jsp"%>
	<div id="main-menu">
		<s:if test="#session.empType =='Admin'">
			<a id="newProjectMenu" href="addProjectFormAction">New Project</a>
		</s:if>
		<s:a href="" cssStyle="padding-left: 1px; padding-right: 0px;" />
	</div>

	<div id="wrapper">
		<div id="bodyContent">
			<s:if test="hasActionErrors()">
				<div class="errors">
					<s:actionerror />
				</div>
			</s:if>
			<div id="currentProjWrapper">
				<h3>Current Projects</h3>
				<div id="currentProject">
					<ul class="projectIndex">
						<s:iterator value="crntPrjDscList">
							<li class="root"><img src="images/projects.png" width="16"
								height="16" alt="projects" />&nbsp;&nbsp;<s:a
									action="projectMainAction">
									<s:param name="projIdentifier" value="identifier" />
									<s:property value="identifier" />
								</s:a></li>
							<li class="desc"><s:property value="description" /></li>
							<li class="iDate"><s:property value="prjctInsrtDate" /></li>
							<br>
						</s:iterator>
					</ul>
				</div>
			</div>

			<s:if test="%{clsdPrjDscList.isEmpty()}">

			</s:if>
			<s:else>
				<div id="closedProjWrapper">
					<h3>Closed Projects</h3>
					<div id="closedProject">
						<ul class="projectIndex">
							<s:iterator value="clsdPrjDscList">
								<li class="root"><img src="images/projects.png" width="16"
									height="16" alt="projects" />&nbsp;&nbsp;<s:a
										action="projectMainAction">
										<s:param name="projIdentifier" value="identifier" />
										<s:property value="identifier" />
									</s:a></li>
								<li class="desc"><s:property value="description" /></li>
								<li class="iDate"><s:property value="prjctInsrtDate" /></li>
								<br>
							</s:iterator>
						</ul>
					</div>
				</div>
			</s:else>
		</div>

	</div>
</body>
</html>
