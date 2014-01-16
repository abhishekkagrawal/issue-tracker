<%-- Copyright 2014 IssueTracker --%>
<%--
    Name : Dinesh Saini
    Date : 07/12/13
    Purpose : JSP page for Project Managers
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="getText('global.jsp.homepage')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<link href="css/AdminHomePage.css" rel="stylesheet" type="text/css" />
<link href="css/AdminMenuItem.css" rel="stylesheet" type="text/css" />
<link href="css/usersHomepage.css" rel="stylesheet" type="text/css" />
</head>
<s:head />
<body>
	<%@include file="/jsp/header.jsp"%>
	<div id="main-menu">
		<s:a href="" cssStyle="padding-left: 1px; padding-right: 0px;" />
	</div>
	<%@include file="/jsp/userHomePageData.jsp"%>
</body>
</html>
