<%-- Copyright 2014 IssueTracker --%>
<%--
    Name : Dinesh Saini
    Created on : 29/11/2013
    Purpose : JSP page for User Information
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="getText('global.jsp.userDetail')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<link href=css/AdminHomePage.css " media="all" rel="stylesheet"
	type="text/css" />
<link href="css/AdminMenuItem.css" media="all" rel="stylesheet"
	type="text/css" />
<link href="css/userDetails.css" media="all" rel="stylesheet"
	type="text/css" />
</head>
<s:head />
<body>
	<div id="wrapper">
		<%@include file="header.jsp"%>
		<div id="main-menu">
			<s:a href="" cssStyle="padding-left: 1px; padding-right: 0px;" />
		</div>
		<div id="contentWrapper">
			<div id="userInfo">
				<h3>
					<s:property value="empName" />
				</h3>
				<ul>
					<li>Email : <a href="mailto:${users.email}"><s:property
								value="users.email" /></a></li>
					<li>Mobile : <s:property value="phone" /></li>
					<li>Registered on : <s:property value="regDate" /></li>
					<li>Designation : <s:property value="empType" /></li>
				</ul>
			</div>

			<s:if test="#session.empType !='Admin'">
				<div>
					<h4>Projects</h4>
					<ul>
						<s:iterator value="empRolesInProjectList">
							<li><s:a action="projectMainAction">
									<s:param name="projIdentifier" value="identifier" />
									<s:property value="projectName" />
								</s:a>&nbsp; (<s:property value="empRoles" />,&nbsp;<s:property
									value="prjMmbrshipDate" />)</li>
						</s:iterator>
					</ul>
				</div>
			</s:if>
		</div>
	</div>
</body>
</html>
