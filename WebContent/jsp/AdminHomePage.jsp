<%-- Copyright 2014 IssueTracker --%>
<%--
    Document    : AdminHomePage.jsp
    Author      : Dinesh Saini
    Created on  : Nov 25, 2013
    Description : JSP page for Admin Homepage
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="getText('global.jsp.homepage')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<link href="css/AdminHomePage.css" rel="stylesheet" type="text/css" />
<link href="css/AdminMenuItem.css" rel="stylesheet" type="text/css" />
</head>
<s:head />
<body>
	<s:if test="#session==null || #session['login']!='true' ">
		<jsp:forward page="/jsp/Login.jsp" />
	</s:if>
	<s:if test="#session['empTypeId']!=1">
		<jsp:forward page="/page-not-found.jsp" />
	</s:if>
	<%@include file="/jsp/header.jsp"%>
	<div id="main-menu">
		<s:a href="" cssStyle="padding-left: 1px; padding-right: 0px;" />
	</div>

	<div id="wrapper">
		<div id="shortcutAdmin">
			<ul>
				<li><a href="userRegAction"> Add new User </a></li>
				<li><a href="addProjectFormAction"> Add new project </a></li>
				<li><a href="projectsAction"> All projects </a></li>
			</ul>
		</div>
	</div>
</body>

</html>
