<%-- Copyright 2014 IssueTracker --%>
<%--
    Document    : userHomePageData
    Author      : Dinesh Saini
    Created on  : 07/01/2014
    Description : JSP page for user homePage
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<s:head />
<body>
	<div id="bodyContent">
		<div id="issuesIndex">
			<h3>Issues reported to me</h3>
			<s:set var="issueListLength" value="%{issueAsigneeLst.size()}" />
			<s:if test="%{#issueListLength == null || #issueListLength == 0}">
                    No record found
                </s:if>
			<s:else>
				<display:table class="issueAssignedTable" uid="issueAssignedTable"
					id="current" name="issueAsigneeLst" pagesize="8" export="false"
					style="width:100%;" requestURI="" cellpadding="5px;"
					cellspacing="5px;">
					<display:setProperty name="paging.banner.onepage" value="Page {0}" />
					<display:column title="#" property="issueId" />
					<display:column href="projectMainAction" paramId="projIdentifier"
						paramProperty="projectIdentifier" title="Project"
						property="projectName" />
					<display:column title="Tracker" property="trackerType" />
					<display:column title="Subject">
						<s:a action="issueOverviewIssueAction">
							<s:param name="issueNum" value="%{#attr.current.issueId}" />
							<s:property value="%{#attr.current.issueSubject}" />
						</s:a>
                            (<s:property
							value="%{#attr.current.statusType}" />)
                        </display:column>
					<display:column title="Reported on" property="insertionDate" />
					<display:setProperty name="paging.banner.placement" value="bottom" />
				</display:table>
			</s:else>
		</div>

		<div id="issueReportedIndex">
			<h3>Issues reported</h3>
			<s:set var="issueAssignerListLength"
				value="%{assignerIssueList.size()}" />
			<s:if
				test="%{#issueAssignerListLength == null || #issueAssignerListLength == 0}">
                    No record found
                </s:if>
			<s:else>
				<display:table class="issueAssignerTable" uid="issueAssigerTable"
					id="current1" name="assignerIssueList" pagesize="8" export="false"
					style="width:100%;" requestURI="" cellpadding="5px;"
					cellspacing="5px;">
					<display:setProperty name="paging.banner.onepage" value="Page {0}" />
					<display:column title="#" property="issueId" />
					<display:column href="projectMainAction" paramId="projIdentifier"
						paramProperty="projectIdentifier" title="Project"
						property="projectName" />
					<display:column title="Tracker" property="trackerType" />
					<display:column title="Subject">
						<s:a action="issueOverviewIssueAction">
							<s:param name="issueNum" value="%{#attr.current1.issueId}" />
							<s:property value="%{#attr.current1.issueSubject}" />
						</s:a>
                            (<s:property
							value="%{#attr.current1.statusType}" />)
                        </display:column>
					<display:column title="Reported on" property="insertionDate" />
					<display:setProperty name="paging.banner.placement" value="bottom" />
				</display:table>
			</s:else>
		</div>
	</div>
</body>
</html>
