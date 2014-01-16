<%-- Copyright 2014 IssueTracker --%>
<%--
    Name : Dinesh Saini
    Date : 17/12/2013
    Purpose : JSP page for showing issue reported
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://displaytag.sf.net" prefix="disp"%>
<%@taglib uri="http://displaytag.sf.net/el" prefix="displ"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/addIssue.css" rel="stylesheet" type="text/css" />
<title><s:property value="getText('global.jsp.projectIndex')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/issueIndexSearch.js"></script>
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

	<div id="contentWrapper">
		<div id="projIdentifier">
			<h2>
				<s:property value="#session['projIdentifier']" />
			</h2>
		</div>
		<h3>Issues</h3>
		<s:if test="%{issueIndexRecords.isEmpty()}">
                No issue reported
            </s:if>
		<s:else>
			<div id="issueIndex">
				<disp:table class="disTable" sort="list" id="model" uid="models"
					name="issueIndexRecords" pagesize="10" export="true"
					style="width:100%; margin-bottom:20px;" requestURI=""
					cellpadding="5px;" cellspacing="0px;">
					<disp:setProperty name="basic.empty.showtable" value="false" />
					<disp:setProperty name="basic.msg.empty_list_row"
						value="No issue found" />
					<disp:setProperty name="paging.banner.onepage" value="Page {0}" />
					<disp:header>
						<thead id="sModel">
							<tr>
								<th scope="col"><input type="text" value="IssueNo"
									name="issueId" class="search_init" style="width: 65px;" /></th>
								<th scope="col"><input type="text" value="Tracker"
									name="tracker" class="search_init" style="width: 65px;" /></th>
								<th scope="col"><input hidden="true" style="width: 100px;"
									class="search_init" /></th>
								<th scope="col"><input style="display: none;"
									class="search_init" /></th>
								<th scope="col"><input type="text" value="Subject"
									name="subject" class="search_init" style="width: 150px;" /></th>
								<th scope="col"><input type="text" value="Assignee"
									name="Assignee" class="search_init" /></th>
								<th scope="col"><input style="display: none;"
									class="search_init" /></th>
							</tr>
						</thead>
					</disp:header>
					<disp:column property="issueId" title="Issue Id" sortable="true"
						style="width:15px;text-align:center;" />
					<disp:column property="trackerType" title="Tracker" sortable="true"
						style="text-align:center;" />
					<disp:column property="statusType" title="Status" sortable="true"
						style="text-align:center;" />
					<disp:column property="priorityType" title="Priority"
						sortable="true" style="text-align:center;" />
					<disp:column href="issueOverviewIssueAction.action"
						paramId="issueNum" paramProperty="issueId" property="issueSubject"
						title="Subject" style="width:40%;text-align:center;"
						sortable="true" />
					<disp:column href="usersDetailAction.action" paramId="userId"
						sortable="true" paramProperty="loginId" title="Assignee"
						property="empName" style="width:20%;text-align:center;" />
					<disp:column property="issueUpdateDate" title="Updated"
						sortable="true" style="text-align:center;" />
					<disp:setProperty name="paging.banner.placement" value="bottom" />
				</disp:table>
			</div>
		</s:else>
	</div>
</body>
</html>
