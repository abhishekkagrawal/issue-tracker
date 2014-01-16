<%-- Copyright 2014 IssueTracker --%>
<%--
    Document   : page-not-found
    Created on : Dec 31, 2013, 10:44:03 AM
    Author     : Dinesh Saini
    Purpose : JSP page for page not found error
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="getText('global.jsp.404')" /></title>
<link rel="shortcut icon" href=images/icon_Issue.jpg " />
<link href="css/AdminMenuItem.css" rel="stylesheet" type="text/css" />
<link href="css/Header.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="topMenuBar">
		<div id="leadMenu">
			<s:a href="" cssStyle="padding-left: 1px; padding-right: 0px;" />
		</div>
	</div>
	<div id="header">
		<h2>Issue Tracker</h2>
		<div id="search"></div>
	</div>
	<div id="main-menu">
		<s:a href=""
			cssStyle="line-height:10px;  padding-left: 1px; padding-right: 044px;" />
	</div>
	<div id="contentWrapper">
		<h3>Page not found</h3>
	</div>
</body>
</html>
