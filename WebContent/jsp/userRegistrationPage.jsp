<%-- Copyright 2014 IssueTracker --%>
<%--
    Name : Dinesh Saini
    Date : 26/11/2013
    Purpose : User's registration page for Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="getText('global.jsp.userReg')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<link href="css/AdminHomePage.css" rel="stylesheet" type="text/css" />
<link href="css/userReg.css" media="all" rel="stylesheet"
	type="text/css" />
<link href="css/AdminMenuItem.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script src="js/user_registration.js" type="text/javascript"></script>
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
	<%@include file="/jsp/header.jsp"%>
	<div id="main-menu">
		<s:if test="#session.empType =='Admin'">
			<a href="userRegAction">New User</a>
		</s:if>
		<s:a href="" cssStyle="padding-left: 1px; padding-right: 0px;" />
	</div>
	<div id="wrapper">
		<div id="RegistrationPage">
			<div class="userRegFormBody">
				<h3>Register</h3>
				<div id="errorField" style="display: none;">
					<ul class="errList" style="margin: 5px; font-size: 12px;">
					</ul>
				</div>

				<div id="userRegForm">
					<s:form action="addEmployeeAction" name="userRegistration">
						<s:if test="hasActionMessages()">
							<div class="welcome">
								<s:actionmessage />
							</div>
						</s:if>

						<s:if test="hasActionErrors()">
							<div class="errors">
								<s:actionerror />
							</div>
						</s:if>

						<tr>
							<td colspan="2"
								style="padding: 10px; font-size: 10px; color: #999999; text-align: center;">(
								* denotes mandatory fields )</td>
						</tr>
						<s:textfield key="login" label="LoginId" requiredLabel="true"
							cssStyle="margin:0px 0px 7px 7px;" />
						<s:password key="password" label="Password" requiredLabel="true"
							cssStyle="margin:0px 0px 7px 7px;" />
						<tr>
							<td></td>
							<td style="font-size: 10px; color: #999999;">( Password
								should contains alphabet, number having 6 digit )</td>
						</tr>
						<s:password key="password1" label="Confirm Password"
							requiredLabel="true" cssStyle="margin:0px 0px 7px 7px;" />
						<s:textfield key="firstName" label="First Name"
							requiredLabel="true" cssStyle="margin:0px 0px 7px 7px;" />
						<s:textfield key="lastName" label="Last Name" requiredLabel="true"
							cssStyle="margin:0px 0px 7px 7px;" />

						<s:textfield key="email" label="Email" requiredLabel="true"
							cssStyle="margin:0px 0px 7px 7px;" />
						<s:textfield key="mobile" label="Mobile" requiredLabel="true"
							cssStyle="margin:0px 0px 7px 7px;" />
						<s:select list="empTypList" name="employeeCategory"
							requiredLabel="true" label="Employee Type" headerKey="-1"
							headerValue="Select" cssStyle="margin:0px 0px 7px 7px;" />
						<tr>
							<td style="text-align: right;"><s:submit theme="simple"
									onkeypress="return validateAddUserForm();"
									onclick="return validateAddUserForm();" /></td>
							<td style="text-align: center;"><s:reset theme="simple" /></td>
						</tr>
					</s:form>
				</div>
			</div>
		</div>

	</div>


</body>
</html>
