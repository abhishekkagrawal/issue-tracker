<%-- Copyright 2014 IssueTracker --%>
<%--
    Name : Dinesh Saini
    Date : 03/12/2013
    Purpose : JSP page for showing project overview
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="getText('global.jsp.project')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<link href="css/AdminMenuItem.css" rel="stylesheet" type="text/css" />
<link href="css/AdminHomePage.css" rel="stylesheet" type="text/css" />
<link href="css/Projects.css" rel="stylesheet" type="text/css" />
</head>
<s:head />
<body>
	<s:if test="%{#session ==null">
		<jsp:forward page="../Login.jsp" />
	</s:if>

	<s:if test="#session['login']!='true'">
		<jsp:forward page="../Login.jsp" />
	</s:if>

	<%@include file="../header.jsp"%>
	<%@include file="projectMainMenu.jsp"%>

	<s:if test="hasActionMessages()">
		<div class="welcome">
			<s:actionmessage />
		</div>
	</s:if>
	<div id="contentWrapper">
		<div id="projIdentifier">
			<h2>
				<s:property value="#session['projIdentifier']" />
			</h2>
		</div>
		<div id="projDescription">
			<p>
				<s:property value="projectBean.description" />
			</p>
			<s:if
				test="%{projectBean.homePage != null && projectBean.homePage != ''}">
				<ul>
					<li><s:label value="HomePage:"></s:label> <a
						href="${projectBean.homePage}"> <s:property
								value="projectBean.homePage" />
					</a></li>
				</ul>
			</s:if>
		</div>
		<div id="closeProject">
			<s:if test="%{projectBean.projectStatus != 0}">
				<s:if test="#session.empType =='Admin'">
					<s:a action="closeProjectAction">
                            Close project
                        </s:a>
				</s:if>
			</s:if>
			<s:else>
				<strong> Project closed </strong>
			</s:else>
		</div>
		<div id="projectMember">
			<h4>
				<img src="images/group.png" width="16" height="16" alt="group" />
				Members
				<s:if
					test="#session.empType =='Admin' || #session.empType == 'Project Manager'">
					<s:if test="%{projectBean.projectStatus != 0}">
						<span style="float: right; font-weight: normal;"> <s:a
								action="assignMemberAction">
                                    Add/Edit
                                </s:a>
						</span>
					</s:if>
				</s:if>
			</h4>
			<s:if
				test="%{managerList==null && developerList==null && testerList==null}">
				<p>No members</p>
			</s:if>
			<s:else>
				<s:if test="%{managerList.isEmpty()}">
				</s:if>
				<s:else>
					<strong> Manager:</strong>
					<s:iterator value="managerList" status="stat">
						<s:a action="usersDetailAction">
							<s:param name="userId" value="userId" />
							<s:property value="empName" />
						</s:a>
						<s:if test="!#stat.last">,</s:if>
					</s:iterator>
				</s:else>
				<br>

				<s:if test="%{developerList.isEmpty()}">
				</s:if>
				<s:else>
					<strong> Developer:</strong>
					<s:iterator value="developerList" status="stat">
						<s:a action="usersDetailAction">
							<s:param name="userId" value="userId" />
							<s:property value="empName" />
						</s:a>
						<s:if test="!#stat.last">,</s:if>
					</s:iterator>
					<br>
				</s:else>

				<s:if test="%{testerList.isEmpty()}">
				</s:if>
				<s:else>
					<strong> Tester:</strong>
					<s:iterator value="testerList" status="stat">
						<s:a action="usersDetailAction">
							<s:param name="userId" value="userId" />
							<s:property value="empName" />
						</s:a>
						<s:if test="!#stat.last">,</s:if>
					</s:iterator>
				</s:else>
			</s:else>
		</div>

		<div id="projectIssues">
			<h4>Issue Tracking</h4>
			<ul>
				<s:iterator value="projectBean.openIssueList">
					<li><s:property value="trackerType" />: <s:property
							value="OpenIssuesCount" /> open</li>
				</s:iterator>
			</ul>
			<s:if test="projectBean.openIssueList.isEmpty">
				<p>No issue reported</p>
			</s:if>
			<s:else>
				<p>
					<a href="issueIndexIssueAction"> View all issues </a>
				</p>
			</s:else>
		</div>

	</div>

</body>
</html>
