<%-- Copyright 2014 IssueTracker --%>
<%--
    Document    : index.jsp
    Created on  : Nov 25, 2013, 2:59:02 PM
    Author      : Dinesh Saini
    Description : page redirects user to login page.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script>
	$(document).ready(function() {
		window.location = 'LoginPage.action';
	});
</script>
<noscript>
	For full functionality of this site it is necessary to enable
	JavaScript. Here are the <a href="http://www.enable-javascript.com/"
		target="_blank"> instructions how to enable JavaScript in your web
		browser</a>.
</noscript>
</head>
<body>
	<h4>Loading.....</h4>
</body>
</html>
