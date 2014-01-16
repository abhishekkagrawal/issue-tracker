<%-- Copyright 2014 IssueTracker --%>
<%--
    Name : Dinesh Saini
    Date : 04/12/2013
    Purpose : JSP page for Assiging project to member
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="disp" uri="http://displaytag.sf.net"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="getText('global.jsp.assignMember')" /></title>
<link rel="shortcut icon" href="images/icon_Issue.jpg" />
<link href="css/AdminHomePage.css" rel="stylesheet" type="text/css" />
<link href="css/AdminMenuItem.css" rel="stylesheet" type="text/css" />
<link href="css/Projects.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script src="js/project_member_form_validation.js"
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

	<div id="bodyContent">
		<div id="projIdentifier">
			<h2>
				<s:property value="#session['projIdentifier']" />
			</h2>
		</div>

		<div id="restContent">
			<div id="assignedMemberTable">
				<table cellspacing="5px" cellpadding="5px" style="width: 68%">
					<caption>
						<strong>Project members</strong>
					</caption>
					<thead>
						<th scope="col">Name</th>
						<th scope="col">Roles</th>
						<th scope="col">Email</th>
					</thead>
					<s:iterator value="asgndMmbrList" id="current">
						<tr>
							<td style="width: 20%;"><s:property value="empName" /></td>
							<td style="width: 40%">
								<div id="empRoleType${empId}">
									<s:property value="empType" />
									<br />
								</div> <s:if
									test="#session.empType =='Admin' || #session.empType == 'Project Manager'">
									<div class="roleTypeform">
										<s:form action="updateEmpRoleAction" cssStyle="display:none"
											id="form%{#attr.current.empId}" theme="simple">
											<s:hidden value="%{#attr.current.empId}" name="empId" />
											<s:checkboxlist value="empTypeTest" list="empTypList"
												theme="simple" name="updateEmpRoleList" />
											<s:submit value="Update" theme="simple" />
											<input type="button" value="Cancel"
												onkeypress="return(hideUpdate(<s:property value="empId"/>));"
												onclick="return(hideUpdate(<s:property value="empId"/>));" />
										</s:form>
									</div>
								</s:if>
							</td>
							<td><a href="mailto:${email}"> <s:property value="email" />
							</a></td>
							<s:if test="%{#session.projectStatus == 1}">
								<s:if
									test="#session.empType =='Admin' || #session.empType == 'Project Manager'">
									<td><a onkeypress=""
										href="return(updateRoles(<s:property value="empId"/>, '<s:property value="empType"/>'));"
										onclick="return(updateRoles(<s:property value="empId"/>, '<s:property value="empType"/>'));"
										id="<s:property value="empId"/>"> <strong> Edit </strong>
									</a></td>
								</s:if>
							</s:if>
						</tr>
					</s:iterator>
				</table>
			</div>
			<s:if test="%{#session.projectStatus == 1}">
				<s:if
					test="#session.empType =='Admin' || #session.empType == 'Project Manager'">
					<div id="assigneeMember">
						<fieldset id="fieldset">
							<legend> New Member </legend>
							<s:form action="newAssignMemberAction">
								<div id="assigneeMemberList">
									<disp:table class="disTable" uid="current" id="current"
										name="newAsgnMmbrLst" pagesize="10" export="false"
										requestURI="" cellpadding="2px;" cellspacing="2px;">
										<disp:setProperty name="paging.banner.onepage"
											value="Page {0}" />
										<disp:column>
											<s:checkbox value="0" fieldValue="%{#attr.current.empId}"
												name="selectedMemList" theme="simple" />
										</disp:column>
										<disp:column property="empName" title="Employee Name" />
										<disp:setProperty name="paging.banner.placement"
											value="bottom" />
									</disp:table>
									<br>
									<s:if test="hasActionErrors()">
										<div class="errors">
											<s:actionerror />
										</div>
									</s:if>
									<div id="errorField"
										style="border: 2px #ff0000 solid; display: none;">
										<ul class="errList"
											style="color: #ff0000; font-size: 12px; margin: 5px;">
										</ul>
									</div>
									<div id="roleTypeForm">
										<s:label value="Roles:" />
										<br>
										<s:checkboxlist list="empTypList" name="empRolesTypeList" />
										<s:submit value="Add"
											onkeypress="return validate_proj_member();"
											onclick="return validate_proj_member();"
											cssStyle="margin-top:10px; align:center;" />
									</div>
								</div>
							</s:form>
						</fieldset>
					</div>
				</s:if>
			</s:if>
		</div>

		<div id="closeProject">
			<s:if test="#session.empType =='Admin'">
				<s:a action="userRegAction">New User</s:a>
			</s:if>
		</div>
	</div>

</body>
</html>
