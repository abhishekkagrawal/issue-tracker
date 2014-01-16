<%-- Copyright 2014 IssueTracker --%>
<%--
    Name : Dinesh Saini
    Date : 07/12/13
    Purpose : JSP page for Tester
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href=css/users.css " rel="stylesheet" type="text/css" />
<link href="css/AdminMenuItem.css" rel="stylesheet" type="text/css" />
<link href="css/usersHomepage.css" rel="stylesheet" type="text/css" />
<title><s:property value="getText('global.jsp.homepage')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
</head>
<body>
	<%@include file="/jsp/header.jsp"%>
	<div id="main-menu">
		<s:a href="" cssStyle="padding-left: 1px; padding-right: 0px;" />
	</div>
	<%@include file="/jsp/userHomePageData.jsp"%>
</body>
</html>
