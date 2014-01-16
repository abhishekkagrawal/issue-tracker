<%-- Copyright 2014 IssueTracker --%>
<%--
    Name : Dinesh Saini
    Date : 26/11/2013
    Purpose : Header JSP file for all Pages
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<link href="css/Header.css" media="all" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="topMenuBar">
		<div id="leadMenu">
			<a href="directorAction">Home</a> <a href="projectsAction">Projects</a>
			<s:if test="#session.empType =='Admin'">
				<a href="userIndexAction">Users</a>
			</s:if>
		</div>

		<div id="MyAccount">
			<span id="topText">Logged in as </span> <a href="userDetailsAction">
				<s:property value="#session['userName']" />
			</a> <a href="updateUserAction">My Account</a> <a href="LogoutAction">Sign
				out</a>
		</div>
	</div>

	<div id="header">
		<h2>Issue Tracker</h2>
		<div id="search">
			<s:form action="searchAction">
				<s:textfield key="search" label="Search" />
			</s:form>
		</div>
	</div>
</body>
</html>