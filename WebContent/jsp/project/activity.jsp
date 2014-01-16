<%-- Copyright 2014 IssueTracker --%>
<%--
    Document   : activity
    Created on : Dec 24, 2013, 8:39:53 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="getText('global.jsp.activity')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<link href="css/Projects.css" rel="stylesheet" type="text/css" />
<link href="css/search.css" rel="stylesheet" type="text/css" />
</head>
<s:head />
<body>
	<s:if test="%{#session ==null}">
		<jsp:forward page="../Login.jsp" />
	</s:if>
	<s:if test="#session['login']!='true'">
		<jsp:forward page="../Login.jsp" />
	</s:if>

	<%@include file="../header.jsp"%>
	<%@include file="projectMainMenu.jsp"%>

	<div id="contentWrapper">
		<div id="projIdentifier">
			<h2>
				<s:property value="#session['projIdentifier']" />
			</h2>
		</div>

		<div id="issuesIndex">

			<s:set var="issueListLength" value="%{issueActivityList.size()}" />
			<s:if test="%{#issueListLength == null || #issueListLength == 0}">
                    No recent activity
                </s:if>
			<s:else>
				<h3>Activity</h3>
				<s:iterator value="issueActivityList">
					<div class="issueView">
						<span class="pt"> <s:if
								test="statusType in {'Closed','Rejected'} ">
								<img src="images/Issue Closed.png" width="16" height="16"
									alt="Issue Closed" />
							</s:if> <s:else>
								<img src="images/Issue new.png" width="16" height="16"
									alt="Issue new" />
							</s:else> <s:a action="issueOverviewIssueAction">
								<s:param name="issueNum" value="issueId" />
								<s:param name="issueProjectId" value="projectId" />
								<s:property value="IssueName" />
							</s:a>
						</span> <br>
						<s:if test="%{issueDesc != null}">
							<span class="description"> &nbsp;&nbsp;&nbsp;&nbsp;<s:property
									value="issueDesc" />
							</span>
							<br>
						</s:if>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<s:property value="insertionDate" />
					</div>
				</s:iterator>
			</s:else>

		</div>

	</div>
</body>
</html>
