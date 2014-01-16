<%-- Copyright 2014 IssueTracker --%>
<%--
    Name : Dinesh Saini
    Date : 23/12/2013
    Purpose : JSP page for searching functionality
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="getText('global.jsp.search')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<link href="css/AdminMenuItem.css" rel="stylesheet" type="text/css" />
<link href="css/search.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script>
            $(document).ready(function() {
                $('#search').hide();
            });
        </script>
</head>
<s:head />
<body>
	<s:if test="%{#session ==null}">
		<jsp:forward page="jsp/Login.jsp" />
	</s:if>

	<s:if test="#session['login']!='true'">
		<jsp:forward page="jsp/Login.jsp" />
	</s:if>
	<div id="wrapper">
		<%@include file="../jsp/header.jsp"%>
		<div id="main-menu">
			<s:a href="" cssStyle="padding-left: 1px; padding-right: 0px;" />
		</div>
	</div>

	<div id="contentWrapper">
		<h3>Search</h3>
		<div id="searchFrom">
			<s:form action="searchAction">
				<s:textfield key="search" cssStyle="margin:0px 10px 5px 15px;"
					label="Search" />
				<s:checkboxlist list="searchCheckBox" name="searchChecked"
					value="searchChecked" />
				<tr>
					<td><s:submit theme="simple" onclick="searchAction"
							onkeypress="searchAction" /></td>
				</tr>
			</s:form>
		</div>
		<div id="CategoryContent">
			<div id="projectIndex">
				<s:set var="projectListLength"
					value="%{searchBean.prjctSrchedList.size()}" />
				<s:if
					test="%{#projectListLength == null || #projectListLength == 0}">
				</s:if>
				<s:else>
					<s:iterator value="searchBean.prjctSrchedList">
						<div class="projectView">
							<img src="images/projects.png" width="16" height="16"
								alt="projects" />
							<s:property value="projectName" />
							-
							<s:a action="projectMainAction">
								<s:param name="projIdentifier" value="identifier" />Project: <s:property
									value="projectName" />
							</s:a>
							<br> &nbsp;&nbsp;&nbsp;&nbsp;<span class="description">
								<s:property value="description" />
							</span><br> &nbsp;&nbsp;&nbsp;&nbsp;
							<s:property value="prjctInsrtDate" />
						</div>
					</s:iterator>
				</s:else>
			</div>

			<div id="issuesIndex">
				<s:set var="issueListLength"
					value="%{searchBean.issueSearchedList.size()}" />
				<s:if test="%{#issueListLength == null || #issueListLength == 0}">
					<s:if
						test="%{#projectListLength == null || #projectListLength == 0}">
                            No record found
                        </s:if>
				</s:if>
				<s:else>
					<s:iterator value="searchBean.issueSearchedList">
						<div class="issueView">
							<span class="pt"> <s:if
									test="statusType in {'Closed','Rejected'} ">
									<img src="images/Issue Closed.png" width="16" height="16"
										alt="Issue Closed" />
								</s:if> <s:else>
									<img src="images/Issue new.png" width="16" height="16"
										alt="Issue new" />
								</s:else> <s:property value="projectName" /> - <s:a
									action="issueOverviewIssueAction">
									<s:param name="issueNum" value="issueId" />
									<s:param name="issueProjectId" value="projectId" />
									<s:property value="IssueName" />
								</s:a>
							</span> <br> <span class="description">
								&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="issueDesc" />
							</span> <br> &nbsp;&nbsp;&nbsp;&nbsp;
							<s:property value="insertionDate" />
						</div>
					</s:iterator>
				</s:else>

			</div>
		</div>
	</div>

</body>
</html>
