<%-- Copyright 2014 IssueTracker --%>
<%--
    Name : Dinesh Saini
    Date : 25/12/2013
    Purpose : JSP page for user edit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="getText('global.jsp.editUser')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<link href="css/AdminHomePage.css" rel="stylesheet" type="text/css" />
<link href="css/AdminMenuItem.css" rel="stylesheet" type="text/css" />
<link href="css/userIndex.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script src="js/update_user_info_validation.js" type="text/javascript"></script>
<script src="js/common.js" type="text/javascript"></script>
</head>
<s:head />
<body>
	<s:if test="#session==null || #session['login']!='true' ">
		<jsp:forward page="/jsp/Login.jsp" />
	</s:if>
	<s:if test="#session['empTypeId']!=1">
		<jsp:forward page="/page-not-found.jsp" />
	</s:if>

	<%@include file="header.jsp"%>

	<div id="main-menu">
		<s:if test="#session.empType =='Admin'">
			<a href="userRegAction" id="newUserMenuItem">New User</a>
		</s:if>
		<s:a href="" cssStyle="padding-left: 1px; padding-right: 0px;" />
	</div>

	<div id="contentWrapper">
		<h3>Employee Details</h3>
		<div id="errorField"
			style="border: 2px #ff0000 solid; margin: 20px; width: 503px; display: none;">
			<ul class="errList"
				style="color: #ff0000; font-size: 12px; margin: 5px;">
			</ul>
		</div>
		<div id="editUserForm">
			<s:form action="editUserDetailsAction">
				<s:if test="hasActionErrors()">
					<div class="errors">
						<s:actionerror />
					</div>
				</s:if>
				<s:if test="hasActionMessages()">
					<div class="welcome">
						<s:actionmessage />
					</div>
				</s:if>
				<s:textfield key="users.firstName" size="35" label="First Name"
					cssStyle="margin-bottom:7px;" />
				<s:textfield key="users.lastName" size="35" label="Last Name"
					cssStyle="margin-bottom:7px;" />
				<s:textfield key="users.email" size="35" label="Email"
					cssStyle="margin-bottom:7px;" />
				<s:textfield key="users.phone" size="35" label="Mobile"
					cssStyle="margin-bottom:7px;" />
				<s:select list="empStatusTypeList" value="{users.empStatus}"
					name="users.empStatus" label="Status" cssStyle="margin-bottom:7px;" />
				<s:hidden key="users.empId" />
				<s:submit value="Update"
					onkeypress="return validateUpdateUserInfo();"
					onclick="return validateUpdateUserInfo();" />
			</s:form>
		</div>
	</div>
</body>
</html>
