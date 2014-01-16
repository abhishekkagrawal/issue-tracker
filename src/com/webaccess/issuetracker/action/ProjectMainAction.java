/*
 *      Document     : ProjectMainAction.java
 *      Author       : Dinesh Saini
 *      Created on   : 03/12/2013
 *      Description  : Action class to get a project's overview from database
 */
package com.webaccess.issuetracker.action;

import com.opensymphony.xwork2.ActionSupport;
import com.webaccess.issuetracker.bean.IssuesBean;
import com.webaccess.issuetracker.bean.ProjectBean;
import com.webaccess.issuetracker.bean.Users;
import com.webaccess.issuetracker.delegate.ProjectDelegate;
import com.webaccess.issuetracker.delegate.UserDelegate;
import com.webaccess.issuetracker.util.LoggingUtil;
import com.webaccess.issuetracker.util.SessionValidator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Class perform operation related to project i.e. adding project, retrieving
 * project reviews, closed project reviews, current running projects, to modify
 * project setting like managing project's type of trackers, to manage project
 * feature like document records, activity, issue tracking, to close any
 * project, to manage project members etc.
 *
 *
 * @author Dinesh Saini
 */
public class ProjectMainAction extends ActionSupport implements
        ServletRequestAware {

    private static final String URL_REGEX = "^(https?|ftp|file)://[-a-zA-Z0-9+&"
            + "@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private static final String DOC_REGEX = "^[a-zA-Z][a-zA-Z0-9 ._&@$=#%]+$";
    private static final org.slf4j.Logger LOGGER = LoggingUtil.getLogger();
    private String projIdentifier;
    private ProjectBean projectBean;
    private int projectId;
    private String projectName;
    private String identifier;
    private String description;
    private String homePage;
    private List<Users> newAsgnMmbrLst;
    private Users users;
    private List<String> empTypList;
    private List asgndMmbrList;
    private List empRolesTypeList;
    private List selectedMemList = new ArrayList();
    private String docTitle;
    private String docDesciption;
    private File prjDoc;
    private String prjDocContentType;
    private String prjDocFileName;
    private HttpServletRequest servletRequest;
    private List prjDocDtlList;
    private int empId;
    private int empTypeId;
    private List updateEmpRoleList;
    private List<Users> managerList = new ArrayList<>();
    private List<Users> developerList = new ArrayList<>();
    private List<Users> testerList = new ArrayList<>();
    private List<ProjectBean> projectModuleList = new ArrayList<>();
    private List<String> trackerList;
    private List<String> moduleList;
    private List trckrSlctedList = new ArrayList();
    private List moduleSlctdList = new ArrayList();
    private List<IssuesBean> issueActivityList = new ArrayList<>();

    /**
     * Validates a user to view any project overview.
     *
     * @return String status
     */
    public String getProjectOverviewData() {
        HttpSession session;
        ProjectDelegate projectDelegate;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute("login") == null
                    || session.getAttribute("empId") == null) {
                addActionError("Please login first");
                return "login";
            }
            this.empId = Integer.parseInt(session.getAttribute("empId").
                    toString());
            this.empTypeId = Integer.parseInt(session.getAttribute("empTypeId").
                    toString());
            projectDelegate = new ProjectDelegate();
            if (!projectDelegate.checkProjectAuthorization(this.empTypeId,
                    this.empId, this.projIdentifier)) {
                return "unAuthorized";
            }
            session.setAttribute("projIdentifier", this.projIdentifier);
        } catch (NumberFormatException e) {
            LOGGER.error("ProjectMainAction", e);
        }
        this.getOverviewData();
        return SUCCESS;
    }

    /**
     * Retrieve a project's overview data from database. Overview data include
     * project member, project description, project URL, project issues etc.
     *
     * @author Dinesh Saini
     * @return String
     */
    public String getOverviewData() {
        HttpSession session;
        ProjectDelegate projectDelegate;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session == null || session.getAttribute("login") == null) {
                addActionError("Please login first");
                return "login";
            }
            this.projIdentifier = session.getAttribute("projIdentifier").toString();
            this.projectBean = new ProjectBean();
            this.projectBean.setIdentifier(this.projIdentifier);
            projectDelegate = new ProjectDelegate();
            this.projectId = projectDelegate.getProjectId(this.projectBean);
            this.projectBean.setProjectId(this.projectId);
            projectDelegate.getProjectOverview(this.projectBean);
            projectDelegate.getProjectMemberList(this.projectBean);

            this.managerList = this.projectBean.getManagerList();
            this.developerList = this.projectBean.getDeveloperList();
            this.testerList = this.projectBean.getTesterList();
            session.setAttribute("projId", this.projectId);
            session.setAttribute("projectStatus", this.projectBean.getProjectStatus());
            this.projectModuleList = projectDelegate.getProjectModuleList(this.projectId);
            projectDelegate.getTotalIssueList(this.projectBean);
        } catch (Exception e) {
            LOGGER.error("ProjectMainAction", e);
        }
        return SUCCESS;
    }

    /**
     * Retrieves projects members according to their designation in project i.e.
     * Project managers, Developers and Testes from database.
     *
     * @author Dinesh Saini
     * @return String
     */
    public String assignMember() {
        HttpSession session;
        ProjectDelegate projectDelegate;
        UserDelegate userDelegate;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session == null || session.getAttribute("login") == null) {
                addActionError("Please login first");
                return "login";
            }
            int projId = Integer.parseInt(session.getAttribute("projId").toString());
            this.projectBean = new ProjectBean();
            this.newAsgnMmbrLst = new ArrayList<>();
            userDelegate = new UserDelegate();
            this.newAsgnMmbrLst = userDelegate.newAssignMemberList(projId);
            this.projectBean.setProjectId(projId);
            this.asgndMmbrList = new ArrayList<>();
            projectDelegate = new ProjectDelegate();
            this.asgndMmbrList = projectDelegate.getAssignedMemberList(this.projectBean);
            this.empTypList = new ArrayList<>();
            this.empTypList = userDelegate.getProjMemberType();
            this.projectModuleList = projectDelegate.getProjectModuleList(projId);
        } catch (NumberFormatException e) {
            LOGGER.error("ProjectMainAction", e);
        }
        return SUCCESS;
    }

    /**
     * To manage any project members i.e. adding a new member, removing any
     * employee from project, to change designation of a member in a project.
     *
     * @author Dinesh Saini
     * @return String
     */
    public String newAssignMember() {
        HttpSession session;
        ProjectDelegate projectDelegate;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session == null || session.getAttribute("login") == null) {
                addActionError("Please login first");
                return "login";
            }
            if (this.selectedMemList.isEmpty()) {
                addActionError("Select employee first");
            } else {
                if (this.empRolesTypeList.isEmpty()) {
                    addActionError("Select both employee & role type");
                } else {
                    this.projectBean = new ProjectBean();
                    int projId = Integer.parseInt(session.getAttribute("projId")
                            .toString());
                    this.projectBean.setProjectId(projId);
                    this.projectBean.setEmpRoleTypeList(this.empRolesTypeList);
                    this.projectBean.setSlctdPrjMmbrList(this.selectedMemList);
                    projectDelegate = new ProjectDelegate();
                    projectDelegate.addProjMember(this.projectBean);
                    this.projectModuleList = projectDelegate.
                            getProjectModuleList(projId);
                }
            }
        } catch (NumberFormatException e) {
            LOGGER.error("ProjectMainAction", e);
        }
        this.assignMember();
        return SUCCESS;
    }

    /**
     * Retrieves any project documents from database by calling delegate method.
     *
     * @author Dinesh Saini
     * @return String status
     */
    public String projectDocument() {
        HttpSession session;
        ProjectDelegate projectDelegate;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session == null || session.getAttribute("login") == null) {
                addActionError("Please login first");
                return "login";
            }
            int projId = Integer.parseInt(session.getAttribute("projId").
                    toString());
            projectDelegate = new ProjectDelegate();
            this.prjDocDtlList = new ArrayList<>();
            this.prjDocDtlList = projectDelegate.getProjDocsDetails(projId);
            this.projectModuleList = projectDelegate.getProjectModuleList(projId);
        } catch (NumberFormatException e) {
            LOGGER.error("ProjectMainAction", e);
        }
        return SUCCESS;
    }

    /**
     * validates new document details that user is going to add.
     *
     * @author Dinesh Saini
     */
    public void docDetailsValidator() {
        try {
            if (getDocTitle().isEmpty()) {
                addFieldError("docTitle", "Document Title Required");
            } else if (!getDocTitle().matches(ProjectMainAction.DOC_REGEX)) {
                addFieldError("docTitle", "Invalid doc title");
            }
            if (getDocDesciption().isEmpty()) {
                addFieldError("docDesciption", "Description Required");
            }
            if (getPrjDocFileName() == null) {
                addFieldError("projectDoc", "Document Required");
            }
        } catch (Exception e) {
            LOGGER.error("ProjectMainAction", e);
        }
    }

    /**
     * Function add new documents in a project.
     *
     * @author Dinesh Saini
     * @return String
     */
    public String addProjDocument() {
        HttpSession session;
        ProjectDelegate projectDelegate;
        this.docDetailsValidator();
        try {
            if (!hasFieldErrors()) {
                String realPath = this.servletRequest.getSession().
                        getServletContext().getRealPath("/");
                String contextPath = getText("projectDocumentPath")
                        + this.docTitle + "."
                        + FilenameUtils.getExtension(this.prjDocFileName);
                String targetPath = realPath + contextPath;
                String targetFilePath = targetPath.replace("\\", "/");
                File targetFile = new File(targetFilePath);
                String fileName = this.docTitle + "."
                        + FilenameUtils.getExtension(this.prjDocFileName);
                session = ServletActionContext.getRequest().getSession(false);
                if (session == null || session.getAttribute("login") == null) {
                    addActionError("Please login first");
                    return "login";
                }
                int projId = Integer.parseInt(session.getAttribute("projId").
                        toString());
                projectDelegate = new ProjectDelegate();
                this.projectBean = new ProjectBean();
                this.projectBean.setProjectDocTitle(fileName);
                this.projectBean.setPrjctDocPath(contextPath);
                this.projectBean.setProjectId(projId);
                this.projectBean.setDescription(this.docDesciption);
                this.projectModuleList
                        = projectDelegate.getProjectModuleList(projId);
                if (projectDelegate.validateProjectDocTitle(this.projectBean)) {
                    if (projectDelegate.addProjectDocs(this.projectBean)) {
                        FileUtils.copyFile(this.prjDoc, targetFile);
                        addActionMessage("Document Added Successfully");
                        this.docTitle = "";
                        this.docDesciption = "";
                    } else {
                        addActionError("Document not added");
                    }
                } else {
                    addFieldError("docTitle", "File Title already exist");
                }
            }
        } catch (NumberFormatException | IOException e) {
            LOGGER.error("ProjectMainAction.run", e);
        }
        this.projectDocument();
        return SUCCESS;
    }

    /**
     * Manages employee roles in a project.
     *
     * @author Dinesh Saini
     * @return String
     */
    public String updateEmpRole() {
        ProjectDelegate projectDelegate;
        HttpSession session;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session == null || session.getAttribute("login") == null) {
                addActionError("Please login first");
                return "login";
            }
            int projId = Integer.parseInt(session.getAttribute("projId").toString());
            this.projectBean = new ProjectBean();
            this.projectBean.setProjectId(projId);
            this.projectBean.setEmpRoleTypeList(this.updateEmpRoleList);
            this.projectBean.setEmpId(this.empId);
            projectDelegate = new ProjectDelegate();
            projectDelegate.updateEmpRoleType(this.projectBean);
            this.projectModuleList = projectDelegate.getProjectModuleList(projId);
        } catch (NumberFormatException e) {
            LOGGER.error("ProjectMainAction", e);
        }
        return "update";
    }

    /**
     * Retrieves project setting or functionalities. Settings and
     * functionalities includes managing tracker types for a project and modules
     * for a project.
     *
     * @author Dinesh Saini
     * @return String
     */
    public String getProjectSettings() {
        HttpSession session;
        ProjectDelegate projectDelegate;
        int projId;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session == null || session.getAttribute("login") == null) {
                addActionError("Please login first");
                return "login";
            }
            if (session.getAttribute("projId") == null) {
                addActionError("Please login first");
                return "login";
            } else {
                projId = Integer.parseInt(session.getAttribute("projId").
                        toString());
            }
            projectDelegate = new ProjectDelegate();
            this.projectModuleList = projectDelegate.getProjectModuleList(projId);
            this.trackerList = new ArrayList<>();
            this.moduleList = new ArrayList<>();
            projectDelegate = new ProjectDelegate();
            this.trackerList = projectDelegate.getTrackerList();
            this.moduleList = projectDelegate.getModuleList();
            this.projectBean = new ProjectBean();
            this.projectBean = projectDelegate.getProjectSettings(projId);
        } catch (NumberFormatException e) {
            LOGGER.error("ProjectMainAction", e);
        }
        return SUCCESS;
    }

    /**
     * Validates project settings admin going to change.
     *
     * @author Dinesh Saini
     */
    public void projectSettingValidator() {
        HttpSession session;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute("empType") != null) {
                if ("Admin".equals(session.getAttribute("empType"))) {
                    if (this.projectName.length() == 0) {
                        addFieldError("projectName", "Project name required");
                    }
                    if (this.description.length() == 0) {
                        addFieldError("description", "Description required");
                    }
                    if (this.homePage.length() == 0) {
                        addFieldError("homePage", "HomePage required");
                    } else if (!getHomePage().matches(ProjectMainAction.URL_REGEX)) {
                        addFieldError("homePage", "Invalid url");
                    }
                    if (this.trckrSlctedList.isEmpty()) {
                        addFieldError("trckrSlctedList", "One or more tracker required");
                    }
                    if (this.moduleSlctdList.isEmpty()) {
                        addFieldError("moduleSlctdList", "One or more module required");
                    }
                } else {
                    addActionError("You are not authorize to access this page");
                }
            }
        } catch (Exception e) {
            LOGGER.error("ProjectMainAction", e);
        }
    }

    /**
     * Perform update operation on project settings.
     *
     * @author Dinesh Saini
     * @return String
     */
    public String updateProjectSettings() {
        HttpSession session;
        ProjectDelegate projectDelegate;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session == null || session.getAttribute("login") == null) {
                addActionError("Please login first");
                return "login";
            }
            if (!"Admin".equals(session.getAttribute("empType"))) {
                return "unAuthorized";
            }
            this.projectSettingValidator();
            if (!hasActionErrors() && !hasFieldErrors()) {
                this.projectBean = new ProjectBean();
                this.projectBean.setProjectId(this.projectId);
                this.projectBean.setProjectName(this.projectName);
                this.projectBean.setDescription(this.description);
                this.projectBean.setHomePage(this.homePage);
                this.projectBean.setTrackerList(this.trckrSlctedList);
                this.projectBean.setModuleList(this.moduleSlctdList);
                projectDelegate = new ProjectDelegate();
                if (projectDelegate.updateProjectSettings(this.projectBean)) {
                    addActionMessage("Update Success");
                }
            }
        } catch (Exception e) {
            LOGGER.error("ProjectMainAction", e);
        }
        this.getProjectSettings();
        return "success";
    }

    /**
     * Performs close operation on running projects.
     *
     * @author Dinesh Saini
     * @return String
     */
    public String closeProject() {
        ProjectDelegate projectDelegate;
        try {
            if (!SessionValidator.validateSession()) {
                addActionError("Please login first");
                return "login";
            }
            this.projectId = SessionValidator.getSessionProjectId();
            projectDelegate = new ProjectDelegate();
            if (projectDelegate.closeProject(this.projectId)) {
                addActionMessage("Project closed successfully");
            }
        } catch (NumberFormatException e) {
            LOGGER.error("ProjectMainAction", e);
        }
        this.getOverviewData();
        return "success";
    }

    /**
     * Retrieves project's recent activity like issue updates.
     *
     * @author Dinesh Saini
     * @return String
     */
    public String projectActivity() {
        ProjectDelegate projectDelegate;
        try {
            if (!SessionValidator.validateSession()) {
                addActionError("Please login first");
                return "login";
            }
            this.projectId = SessionValidator.getSessionProjectId();
            projectDelegate = new ProjectDelegate();
            this.projectModuleList = projectDelegate.getProjectModuleList(this.projectId);
            this.issueActivityList = projectDelegate.getIssueActivityList(this.projectId);
        } catch (NumberFormatException e) {
            LOGGER.error("ProjectMainAction", e);
        }
        return "success";
    }

    /**
     * @return int empTypeId
     */
    public int getEmpTypeId() {
        return this.empTypeId;
    }

    /**
     * @param empTypeId the empTypeId to set
     */
    public void setEmpTypeId(int empTypeId) {
        this.empTypeId = empTypeId;
    }

    /**
     * @return List issueActivityList
     */
    public List<IssuesBean> getIssueActivityList() {
        return this.issueActivityList;
    }

    /**
     * @param issueActivityList the issueActivityList to set
     */
    public void setIssueActivityList(List<IssuesBean> issueActivityList) {
        this.issueActivityList = issueActivityList;
    }

    /**
     * @return List trckrSlctedList
     */
    public List getTrckrSlctedList() {
        return this.trckrSlctedList;
    }

    /**
     * @param trckrSlctedList the trckrSlctedList to set
     */
    public void setTrckrSlctedList(List trckrSlctedList) {
        this.trckrSlctedList = trckrSlctedList;
    }

    /**
     * @return List moduleSlctdList
     */
    public List getModuleSlctdList() {
        return this.moduleSlctdList;
    }

    /**
     * @param moduleSlctdList the moduleSlctdList to set
     */
    public void setModuleSlctdList(List moduleSlctdList) {
        this.moduleSlctdList = moduleSlctdList;
    }

    /**
     * @return String identifier
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return String homePage
     */
    public String getHomePage() {
        return this.homePage;
    }

    /**
     * @param homePage the homePage to set
     */
    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    /**
     * @return String description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return String projectName
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return List trackerList
     */
    public List<String> getTrackerList() {
        return this.trackerList;
    }

    /**
     * @param trackerList the trackerList to set
     */
    public void setTrackerList(List<String> trackerList) {
        this.trackerList = trackerList;
    }

    /**
     * @return List moduleList
     */
    public List<String> getModuleList() {
        return this.moduleList;
    }

    /**
     * @param moduleList the moduleList to set
     */
    public void setModuleList(List<String> moduleList) {
        this.moduleList = moduleList;
    }

    /**
     * @return List projectModuleList
     */
    public List<ProjectBean> getProjectModuleList() {
        return this.projectModuleList;
    }

    /**
     * @param projectModuleList the projectModuleList to set
     */
    public void setProjectModuleList(List<ProjectBean> projectModuleList) {
        this.projectModuleList = projectModuleList;
    }

    /**
     * @return int empId
     */
    public int getEmpId() {
        return this.empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(int empId) {
        this.empId = empId;
    }

    /**
     * @return List updateEmpRoleList
     */
    public List getUpdateEmpRoleList() {
        return this.updateEmpRoleList;
    }

    /**
     * @param updateEmpRoleList the updateEmpRoleList to set
     */
    public void setUpdateEmpRoleList(List updateEmpRoleList) {
        this.updateEmpRoleList = updateEmpRoleList;
    }

    /**
     * @return List developerList
     */
    public List<Users> getDeveloperList() {
        return this.developerList;
    }

    /**
     * @param developerList the developerList to set
     */
    public void setDeveloperList(List<Users> developerList) {
        this.developerList = developerList;
    }

    /**
     * @return List testerList
     */
    public List<Users> getTesterList() {
        return this.testerList;
    }

    /**
     * @param testerList the testerList to set
     */
    public void setTesterList(List<Users> testerList) {
        this.testerList = testerList;
    }

    /**
     * @return List prjDocDtlList
     */
    public List getPrjDocDtlList() {
        return this.prjDocDtlList;
    }

    /**
     * @param prjDocDtlList the prjDocDtlList to set
     */
    public void setPrjDocDtlList(List prjDocDtlList) {
        this.prjDocDtlList = prjDocDtlList;
    }

    /**
     * @return List asgndMmbrList
     */
    public List getAsgndMmbrList() {
        return this.asgndMmbrList;
    }

    /**
     * @param asgndMmbrList the asgndMmbrList to set
     */
    public void setAsgndMmbrList(List asgndMmbrList) {
        this.asgndMmbrList = asgndMmbrList;
    }

    /**
     * @return List selectedMemList
     */
    public List getSelectedMemList() {
        return this.selectedMemList;
    }

    /**
     * @param selectedMemList the selectedMemList to set
     */
    public void setSelectedMemList(List selectedMemList) {
        this.selectedMemList = selectedMemList;
    }

    /**
     * @return List managerList
     */
    public List<Users> getManagerList() {
        return this.managerList;
    }

    /**
     * @param managerList the managerList to set
     */
    public void setManagerList(List<Users> managerList) {
        this.managerList = managerList;
    }

    /**
     * @return String docTitle
     */
    public String getDocTitle() {
        return this.docTitle;
    }

    /**
     * @param docTitle the docTitle to set
     */
    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    /**
     * @return String prjDocFileName
     */
    public String getPrjDocFileName() {
        return this.prjDocFileName;
    }

    /**
     * @param prjDocFileName the prjDocFileName to set
     */
    public void setPrjDocFileName(String prjDocFileName) {
        this.prjDocFileName = prjDocFileName;
    }

    /**
     * @return String prjDocContentType
     */
    public String getPrjDocContentType() {
        return this.prjDocContentType;
    }

    /**
     * @param prjDocContentType the prjDocContentType to set
     */
    public void setPrjDocContentType(String prjDocContentType) {
        this.prjDocContentType = prjDocContentType;
    }

    /**
     * @return String docDesciption
     */
    public String getDocDesciption() {
        return this.docDesciption;
    }

    /**
     * @param docDesciption the docDesciption to set
     */
    public void setDocDesciption(String docDesciption) {
        this.docDesciption = docDesciption;
    }

    /**
     * @return File prjDoc
     */
    public File getPrjDoc() {
        return this.prjDoc;
    }

    /**
     * @param prjDoc the prjDoc to set
     */
    public void setPrjDoc(File prjDoc) {
        this.prjDoc = prjDoc;
    }

    /**
     * @return List empRolesTypeList
     */
    public List getEmpRolesTypeList() {
        return this.empRolesTypeList;
    }

    /**
     * @param empRolesTypeList the empRolesTypeList to set
     */
    public void setEmpRolesTypeList(List empRolesTypeList) {
        this.empRolesTypeList = empRolesTypeList;
    }

    /**
     * @return List empTypList
     */
    public List<String> getEmpTypList() {
        return this.empTypList;
    }

    /**
     * @param empTypList the EmpTypList to set
     */
    public void setEmpTypList(List<String> empTypList) {
        this.empTypList = empTypList;
    }

    /**
     * @return List newAsgnMmbrLst
     */
    public List<Users> getNewAsgnMmbrLst() {
        return this.newAsgnMmbrLst;
    }

    /**
     * @param newAsgnMmbrLst the newAsgnMmbrLst to set
     */
    public void setNewAsgnMmbrLst(List<Users> newAsgnMmbrLst) {
        this.newAsgnMmbrLst = newAsgnMmbrLst;
    }

    /**
     * @return String projIdentifier
     */
    public String getProjIdentifier() {
        return this.projIdentifier;
    }

    /**
     * @return Users users
     */
    public Users getUsers() {
        return this.users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(Users users) {
        this.users = users;
    }

    /**
     * @param projIdentifier the projIdentifier to set
     */
    public void setProjIdentifier(String projIdentifier) {
        this.projIdentifier = projIdentifier;
    }

    /**
     * @return ProjectBean projectBean
     */
    public ProjectBean getProjectBean() {
        return this.projectBean;
    }

    /**
     * @param projectBean the projectBean to set
     */
    public void setProjectBean(ProjectBean projectBean) {
        this.projectBean = projectBean;
    }

    /**
     * @return int projectId
     */
    public int getProjectId() {
        return this.projectId;
    }

    /**
     * @param projectId the projectId to set
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    /**
     * @param hsr the servletRequest to set
     */
    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.servletRequest = hsr;
    }
}
