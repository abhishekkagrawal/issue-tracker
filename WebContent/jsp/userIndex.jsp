<%-- Copyright 2014 IssueTracker --%>
<%--
    Name : Dinesh Saini
    Date : 03/12/2013
    Purpose : JSP page for showing user index page
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="getText('global.jsp.userIndex')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<link href="css/AdminHomePage.css" rel="stylesheet" type="text/css" />
<link href="css/AdminMenuItem.css" rel="stylesheet" type="text/css" />
<link href="css/userIndex.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
</head>
<s:head />
<body>
	<s:if test="%{#session ==null}">
		<jsp:forward page="Login.jsp" />
	</s:if>

	<s:if test="#session['login']!='true'">
		<jsp:forward page="../jsp/Login.jsp" />
	</s:if>

	<div id="content">
		<%@include file="header.jsp"%>

		<div id="main-menu">
			<s:if test="#session.empType =='Admin'">
				<a href="userRegAction" id="newUserMenuItem">New User</a>
			</s:if>

			<s:a href="" cssStyle="padding-left: 1px; padding-right: 0px;" />
		</div>
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
		<div id="indexWrapper">
			<h3>Employee Details</h3>
			<div id="searchEmployee">
				<s:form action="userSearchIndexAction" theme="simple">
					<tr>
						<td><strong> <s:label value="Employee Name:"
									cssStyle="margin-right:15px;" />
						</strong></td>
						<td><s:textfield key="empName" cssStyle="margin-right:15px;" />
						</td>
						<td><s:submit value="Search" /></td>
					</tr>
				</s:form>
			</div>
			<div id="userIndexTable">
				<s:set var="counter" value="1" />
				<display:table name="userIndexInfoList" id="current" sort="list"
					pagesize="10" export="false" requestURI="" cellpadding="2px;"
					cellspacing="2px;">
					<display:setProperty name="paging.banner.onepage" value="Page {0}" />
					<display:setProperty name="basic.msg.empty_list"
						value="No user found" />
					<display:setProperty name="basic.empty.showtable" value="false" />
					<display:column title="S. No.">
						<s:property value="%{#counter}" />
					</display:column>
					<display:column property="empName" title="Emp name" sortable="true" />
					<display:column property="userId" title="User name" />
					<display:column property="phone" title="Mobile" />
					<display:column title="Email">
						<a href="mailto:${email}"> <s:property
								value="%{#attr.current.email}" />
						</a>
					</display:column>
					<display:column href="showEditUserAction" paramId="userId"
						paramProperty="userId">&nbsp; Edit &nbsp;</display:column>
					<display:setProperty name="paging.banner.placement" value="bottom" />
					<s:set var="counter" value="%{#counter+1}" />
				</display:table>
			</div>
		</div>
	</div>

</body>
</html>
