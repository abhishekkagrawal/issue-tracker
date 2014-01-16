<%-- Copyright 2014 IssueTracker --%>
<%--
    Name : Dinesh Saini
    Date : 05/12/2013
    Purpose : JSP page for adding project Documents
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="getText('global.jsp.document')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<link href="css/AdminHomePage.css" rel="stylesheet" type="text/css" />
<link href="css/Projects.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script src="js/add_project_document_validation.js"
	type="text/javascript"></script>
<script src="js/common.js" type="text/javascript"></script>
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
		<div id="errorField"
			style="border: 2px #ff0000 solid; width: 820px; display: none;">
			<ul class="errList"
				style="color: #ff0000; font-size: 12px; margin: 5px;">
			</ul>
		</div>
		<s:if test="%{#session.projectStatus != 0}">
			<div id="newDocumentContent">
				<h3>New documents</h3>
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
				<s:form action="addProjDocumentAction" method="post"
					enctype="multipart/form-data">
					<s:textfield key="docTitle" label="Title"
						cssStyle="margin-bottom:5px;" size="55" />
					<s:textarea key="docDesciption" rows="10" cols="55"
						cssStyle="margin-bottom:5px;" label="Description" />
					<s:file name="prjDoc" label="File" cssStyle="margin-bottom:5px;" />
					<tr>
						<td><s:submit value="Add" theme="simple"
								onkeypress="return validateProjDocsForm();"
								onclick="return validateProjDocsForm();" /></td>
					</tr>
				</s:form>
			</div>
		</s:if>

		<div id="showDocument">
			<h3>Documentation</h3>
			<s:if test="%{prjDocDtlList.isEmpty()}">
                    No documents added
                </s:if>
			<s:else>
				<ul>
					<s:iterator value="prjDocDtlList">
						<p>
						<li><strong> <a href="${prjctDocPath}"> <s:property
										value="projectDocTitle" />
							</a>
						</strong></li>
						<s:property value="description" />
						<br>
						<s:property value="prjDocInsrtDate" />
						</p>
					</s:iterator>
				</ul>
			</s:else>
		</div>

	</div>
</body>
</html>
