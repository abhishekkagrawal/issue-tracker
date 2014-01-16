<%-- Copyright 2014 IssueTracker --%>
<%--
    Document   : Login
    Created on : Nov 25, 2013, 11:08:20 AM
    Author     : Dinesh Saini
    Purpose    : JSP page for logging in
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="getText('global.jsp.login')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<style>
.label {
	color: white;
	font-weight: bolder;
}
</style>
<link href="css/Login.css" media="all" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script src="js/login_form.js" type="text/javascript"></script>
</head>
<s:head />
<body>
	<div id="formBody">
		<h1 align="center">Issue Tracker</h1>
		<div id="form">
			<s:form action="LoginAction">
				<s:if test="hasActionErrors()">
					<div class="errors" style="margin: 0 auto;">
						<s:actionerror />
					</div>
				</s:if>
				<div id="errorField" style="color: red; display: none;">
					<ul class="errList">
					</ul>
				</div>
				<s:textfield key="userId" label="UserName" requiredLabel="true" />
				<s:password key="password" label="Password" requiredLabel="true" />
				<s:submit value="Login" onkeypress="return validateLogin();"
					onclick="return validateLogin();" />
			</s:form>
		</div>
	</div>
</body>

</html>
