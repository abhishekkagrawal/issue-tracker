<%-- Copyright 2014 IssueTracker --%>
<%--
    Document   : director
    Created on : Nov 26, 2013, 10:06:34 AM
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
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<noscript>
	For full functionality of this site it is necessary to enable
	JavaScript. Here are the <a href="http://www.enable-javascript.com/"
		target="_blank"> instructions how to enable JavaScript in your web
		browser</a>.
</noscript>
</head>
<s:head />
<body>
	<s:if test="%{#session == null">
		<jsp:forward page="/jsp/Login.jsp" />
	</s:if>
	<s:if test="#session.login !='true'">
		<jsp:forward page="/jsp/Login.jsp" />
	</s:if>

	<s:if test="#session.empType == 'Admin'">
		<script>
            window.location = 'adminDirectorAction.action';
        </script>
	</s:if>

	<s:elseif test="#session.empType =='Project Manager'">
		<script>
            window.location = 'managerDirectorAction.action';
        </script>
	</s:elseif>

	<s:elseif test="#session.empType =='Developer'">
		<script>
            window.location = 'developerDirectorAction.action';
        </script>
	</s:elseif>

	<s:elseif test="#session.empType =='Tester'">
		<script>
            window.location = 'testerDirectorAction.action';
        </script>
	</s:elseif>
</body>
</html>
