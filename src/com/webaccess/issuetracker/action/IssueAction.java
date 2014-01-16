/*
 *      Document     : IssueAction.java
 *      Author       : Dinesh Saini
 *      Created on   : 16/12/2013
 *      Description  : Action class for managing issues.
 */
package com.webaccess.issuetracker.action;

import com.opensymphony.xwork2.ActionSupport;
import com.webaccess.issuetracker.bean.IssuesBean;
import com.webaccess.issuetracker.bean.ProjectBean;
import com.webaccess.issuetracker.bean.Users;
import com.webaccess.issuetracker.delegate.IssueDelegate;
import com.webaccess.issuetracker.delegate.ProjectDelegate;
import com.webaccess.issuetracker.util.DateConvertor;
import com.webaccess.issuetracker.util.LoggingUtil;
import com.webaccess.issuetracker.util.SessionValidator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Class IssueAction manages issues in any project. Perform operations like
 * reporting issues, updating issues, assigning issues, closing issues etc.
 *
 * @author Dinesh Saini
 */
public class IssueAction extends ActionSupport implements ServletRequestAware {

    private static final String NUM_REGEX = "^[0-9]+$";
    private static final org.slf4j.Logger LOGGER = LoggingUtil.getLogger();
    private HttpServletRequest servletRequest;
    private IssuesBean issueBean;
    private ProjectBean projectBean;
    private List trackerTypeList;
    private List statusTypeList;
    private List priorityTypeList;
    private String trackerType;
    private String statusType;
    private String priorityType;
    private int issueProjectId;
    private int issueNum;
    private List<Users> projectMembers;
    private Date issueStartDate;
    private Date issueDueDate;
    private String issueSubject;
    private String description;
    private String assigneeName;
    private String estimatedTime;
    private String loginId;
    private File issueDocs;
    private String issueDocsFileName;
    private List<IssuesBean> issueIndexRecords;
    private List<IssuesBean> issueDocList;
    private String updateFlag;
    private Users users;
    private List<ProjectBean> projectModuleList = new ArrayList<>();
    private int empId;
    private int empTypeId;

    /**
     * Retrieves issues data from database for reporting new issue form.
     *
     * @return String
     */
    public String getFormData() {
        HttpSession session;
        IssueDelegate issueDelegate;
        ProjectDelegate projectDelegate;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session == null || session.getAttribute("login") == null) {
                return "login";
            }
            issueDelegate = new IssueDelegate();
            this.issueBean = new IssuesBean();
            projectDelegate = new ProjectDelegate();
            this.issueBean = issueDelegate.getIssueOverview(this.issueNum);
            if (session.getAttribute("projId") == null) {
                addActionError("Select project first");
                return "projectIndex";
            } else {
                this.issueProjectId = Integer.parseInt(session.getAttribute("projId").toString());
            }
            this.projectModuleList = projectDelegate.getProjectModuleList(this.issueProjectId);
            this.projectMembers = projectDelegate.getProjectMembers(this.issueProjectId);
            this.issueBean.setProjectId(this.issueProjectId);
            issueDelegate.getFormData(this.issueBean);
            this.trackerTypeList = new ArrayList();
            this.trackerTypeList = this.issueBean.getPrjctTrckrList();
            this.statusTypeList = this.issueBean.getStatusTypeList();
            this.priorityTypeList = this.issueBean.getPriorityTypeList();
        } catch (NumberFormatException e) {
            LOGGER.debug("IssueAction.run", e);
        }
        return "success";
    }

    /**
     *
     * Validates new issue details entered by user.
     *
     */
    public void addIssueDataValidator() {
        try {
            if (getIssueSubject().length() == 0) {
                addFieldError("issueSubject", "Issue subject required");
            }
            if (getDescription().length() == 0) {
                addFieldError("description", "Description required");
            }
            if (getIssueDocs() == null) {
                addFieldError("issueDocs", "Document required");
            }
            if (this.issueStartDate == null) {
                addFieldError("issueStartDate", "Start date required");
            }
            if (this.issueDueDate == null) {
                addFieldError("issueDueDate", "Start date required");
            }
            if (this.issueStartDate != null && this.issueDueDate != null) {
                if (this.issueStartDate.after(this.issueDueDate)) {
                    addFieldError("issueStartDate", "Start date should be before or on due date");
                }
            }
            if (this.estimatedTime.length() != 0) {
                if (!this.estimatedTime.matches(IssueAction.NUM_REGEX)) {
                    addFieldError("estimatedTime", "Invalid hour");
                }
            } else {
                addFieldError("estimatedTime", "Time required");
            }
        } catch (NumberFormatException e) {
            LOGGER.debug("IssueAction.run", e);
        }
    }

    /**
     * Performs report new issue operation in a project.
     *
     * @return String
     */
    public String addNewIssue() {
        HttpSession session;
        IssueDelegate issueDelegate;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute("login") == null || session.getAttribute("userName") == null) {
                return "login";
            } else {
                this.loginId = session.getAttribute("userName").toString();
            }
            if (session.getAttribute("projId") != null) {
                this.issueProjectId = Integer.parseInt(session.getAttribute("projId").toString());
            }
            this.addIssueDataValidator();
            if (!hasFieldErrors()) {
                String realPath = this.servletRequest.getSession().getServletContext().getRealPath("/");
                String newFileName = getIssueSubject() + "."
                        + FilenameUtils.getExtension(this.issueDocsFileName);
                String contextPath = getText("issuesDocumentLocation") + newFileName;
                String targetFilePath = realPath.replace("\\", "/") + contextPath;
                File targetFile = new File(targetFilePath);
                this.issueBean = new IssuesBean();
                this.issueBean.setTrackerType(this.trackerType);
                this.issueBean.setIssueSubject(this.issueSubject);
                this.issueBean.setIssueDesc(this.description);
                this.issueBean.setPriorityType(this.priorityType);
                this.issueBean.setProjectId(this.issueProjectId);
                this.issueBean.setAssigneeId(Integer.parseInt(this.assigneeName));
                this.issueBean.setIssueStartDate(DateConvertor.dateToSimple(this.issueStartDate));
                this.issueBean.setIssueDueDate(DateConvertor.dateToSimple(this.issueDueDate));
                this.issueBean.setEstimatedHour(Integer.parseInt(this.estimatedTime));
                this.issueBean.setStatusType(this.statusType);
                this.issueBean.setFileName(newFileName);
                this.issueBean.setFileLocation(contextPath);
                this.issueBean.setLoginId(this.loginId);
                issueDelegate = new IssueDelegate();
                if (issueDelegate.addNewIssue(this.issueBean)) {
                    addActionMessage("issue Added Successfully");
                    FileUtils.copyFile(this.issueDocs, targetFile);
                }
            }
        } catch (NumberFormatException e) {
            LOGGER.debug("IssueAction.run", e);
        } catch (IOException ex) {
            addActionError("Issue document add failed");
            LOGGER.debug("IssueAction.run", ex);
        }
        this.getFormData();
        return SUCCESS;
    }

    /**
     * Retrieves issues list from database for issue index page.
     *
     * @return String
     */
    public String issueIndex() {
        HttpSession session;
        IssueDelegate issueDelegate;
        ProjectDelegate projectDelegate;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session != null && session.getAttribute("login") == null) {
                return "login";
            }
            if (session.getAttribute("projId") != null) {
                this.issueProjectId = Integer.parseInt(session.getAttribute("projId").toString());
            }
            issueDelegate = new IssueDelegate();
            this.issueIndexRecords = issueDelegate.getIssueRecords(this.issueProjectId);
            projectDelegate = new ProjectDelegate();
            this.projectModuleList = projectDelegate.getProjectModuleList(this.issueProjectId);
        } catch (NumberFormatException e) {
            LOGGER.debug("IssueAction.run", e);
        }
        return "index";
    }

    /**
     * Retrieves a issue overview data from database.
     *
     * @return String
     */
    public String issueOverview() {
        HttpSession session;
        IssueDelegate issueDelegate;
        ProjectDelegate projectDelegate;
        String returnFlag = "";
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (!SessionValidator.validateSession()) {
                returnFlag = "login";
            }
            issueDelegate = new IssueDelegate();
            this.issueBean = new IssuesBean();
            projectDelegate = new ProjectDelegate();
            this.issueBean = issueDelegate.getIssueOverview(this.issueNum);
            this.empId = Integer.parseInt(session.getAttribute("empId").toString());
            this.empTypeId = Integer.parseInt(session.getAttribute("empTypeId").toString());
            if (!projectDelegate.checkProjectAuthorization(this.empTypeId, this.empId, this.issueBean.getProjectIdentifier())) {
                returnFlag = "unAuthorized";
            }
            if (this.issueBean.getProjectId() == 0) {
                addActionError("Record not found");
                returnFlag = "404Error";
            } else {
                this.issueProjectId = this.issueBean.getProjectId();
                this.issueDocList = new ArrayList<>();
                this.issueDocList = issueDelegate.getIssueDocs(this.issueNum);
                projectDelegate = new ProjectDelegate();
                this.projectModuleList = projectDelegate.getProjectModuleList(this.issueProjectId);
            }
            this.projectBean = new ProjectBean();
            this.projectBean.setProjectId(this.issueProjectId);
            projectDelegate.getProjectOverview(this.projectBean);
            session = ServletActionContext.getRequest().getSession(false);
            session.setAttribute("projId", this.issueProjectId);
            session.setAttribute("projIdentifier", this.issueBean.getProjectIdentifier());
            session.setAttribute("projectStatus", this.projectBean.getProjectStatus());
            returnFlag = "overview";
        } catch (NumberFormatException e) {
            LOGGER.debug("IssueAction.run", e);
        }
        this.getTodayDate();
        this.getFormData();
        return returnFlag;
    }

    /**
     * Performs issue update operation.
     *
     * @return String
     */
    public String updateIssue() {
        HttpSession session;
        IssueDelegate issueDelegate;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute("login") == null || session.getAttribute("userName") == null) {
                return "login";
            } else {
                this.loginId = session.getAttribute("userName").toString();
            }
            this.addIssueDataValidator();
            if (!hasFieldErrors()) {
                String realPath = this.servletRequest.getSession().getServletContext().getRealPath("/");
                String newFileName = getIssueSubject() + "."
                        + FilenameUtils.getExtension(this.issueDocsFileName);
                String contextPath = getText("issuesDocumentLocation")
                        + newFileName;
                String targetFilePath = realPath.replace("\\", "/") + contextPath;
                File targetFile = new File(targetFilePath);
                this.issueBean = new IssuesBean();
                this.issueBean.setIssueId(this.issueNum);
                this.issueBean.setTrackerType(this.trackerType);
                this.issueBean.setIssueSubject(this.issueSubject);
                this.issueBean.setIssueDesc(this.description);
                this.issueBean.setPriorityType(this.priorityType);
                this.issueBean.setStatusType(this.statusType);
                this.issueBean.setAssigneeId(Integer.parseInt(this.assigneeName));
                this.issueBean.setIssueStartDate(DateConvertor.dateToSimple(this.issueStartDate));
                this.issueBean.setIssueDueDate(DateConvertor.dateToSimple(this.issueDueDate));
                this.issueBean.setEstimatedHour(Integer.parseInt(this.estimatedTime));
                if (this.issueDocs != null) {
                    this.issueBean.setFileName(newFileName);
                    this.issueBean.setFileLocation(contextPath);
                }
                this.issueBean.setLoginId(this.loginId);
                issueDelegate = new IssueDelegate();
                if (issueDelegate.updateIssue(this.issueBean)) {
                    addActionMessage("issue updated Successfully");
                    if (this.issueDocs != null) {
                        FileUtils.copyFile(this.issueDocs, targetFile);
                    }
                }
            }
        } catch (NumberFormatException | IOException e) {
            LOGGER.error("IssueAction.run", e);
        }
        this.getFormData();
        this.issueOverview();
        return "overview";
    }

    /**
     * Returns Users object type variable.
     *
     * @return Users
     */
    public Users getUsers() {
        return this.users;
    }

    /**
     *
     * @param users
     * @purpose sets users property of type Users
     */
    public void setUsers(Users users) {
        this.users = users;
    }

    /**
     * @return int
     */
    public int getIssueProjectId() {
        return this.issueProjectId;
    }

    /**
     * @param issueProjectId
     * @purpose sets issueProjectId property
     */
    public void setIssueProjectId(int issueProjectId) {
        this.issueProjectId = issueProjectId;
    }

    /**
     * @return List
     */
    public List<ProjectBean> getProjectModuleList() {
        return this.projectModuleList;
    }

    /**
     * @param projectModuleList
     * @purpose sets property list projectModuleList
     */
    public void setProjectModuleList(List<ProjectBean> projectModuleList) {
        this.projectModuleList = projectModuleList;
    }

    /**
     * @return Date
     * @purpose returns todays date
     */
    public Date getTodayDate() {
        return new Date();
    }

    /**
     * @return String
     */
    public String getUpdateFlag() {
        return this.updateFlag;
    }

    /**
     * @param updateFlag
     * @purpose sets property updateFlag
     */
    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag;
    }

    /**
     * @return List
     */
    public List<IssuesBean> getIssueDocList() {
        return this.issueDocList;
    }

    /**
     * @param issueDocList
     * @purpose sets property List issueDocList
     */
    public void setIssueDocList(List<IssuesBean> issueDocList) {
        this.issueDocList = issueDocList;
    }

    /**
     * @return String
     */
    public String getLoginId() {
        return this.loginId;
    }

    /**
     * @param loginId
     * @purpose sets property loginId
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * @return List
     */
    public List<IssuesBean> getIssueIndexRecords() {
        return this.issueIndexRecords;
    }

    /**
     * @param issueIndexRecords
     * @purpose sets property List issueIndexRecords
     */
    public void setIssueIndexRecords(List<IssuesBean> issueIndexRecords) {
        this.issueIndexRecords = issueIndexRecords;
    }

    /**
     * @return int
     */
    public int getIssueNum() {
        return this.issueNum;
    }

    /**
     * @param issueNum
     * @purpose sets property issueNum
     */
    public void setIssueNum(int issueNum) {
        this.issueNum = issueNum;
    }

    /**
     * @return String
     */
    public String getIssueDocsFileName() {
        return this.issueDocsFileName;
    }

    /**
     * @param issueDocsFileName
     * @purpose sets property issueDocsFileName
     */
    public void setIssueDocsFileName(String issueDocsFileName) {
        this.issueDocsFileName = issueDocsFileName;
    }

    /**
     * @return Date
     */
    public Date getIssueDueDate() {
        return this.issueDueDate;
    }

    /**
     * @param issueDueDate
     * @purpose sets issueDueDate
     */
    public void setIssueDueDate(Date issueDueDate) {
        this.issueDueDate = issueDueDate;
    }

    /**
     * @return File
     */
    public File getIssueDocs() {
        return this.issueDocs;
    }

    /**
     * @param issueDoc
     * @purpose sets property issueDocs
     */
    public void setIssueDocs(File issueDoc) {
        this.issueDocs = issueDoc;
    }

    /**
     * @return String estimatedTime
     */
    public String getEstimatedTime() {
        return this.estimatedTime;
    }

    /**
     * @param estimatedTime
     * @purpose sets property estimatedTime
     */
    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    /**
     * @return String assigneeName
     */
    public String getAssigneeName() {
        return this.assigneeName;
    }

    /**
     * @param assigneeName
     * @purpose sets property assigneeName
     */
    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    /**
     * @return String priorityType
     */
    public String getPriorityType() {
        return this.priorityType;
    }

    /**
     * @param priorityType
     * @purpose sets property priorityType
     */
    public void setPriorityType(String priorityType) {
        this.priorityType = priorityType;
    }

    /**
     * @return String description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description
     * @purpose sets property description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return String issueSubject
     */
    public String getIssueSubject() {
        return this.issueSubject;
    }

    /**
     * @param issueSubject
     * @purpose sets property issueSubject
     */
    public void setIssueSubject(String issueSubject) {
        this.issueSubject = issueSubject;
    }

    /**
     * @return Date issueStartDate
     */
    public Date getIssueStartDate() {
        return this.issueStartDate;
    }

    /**
     * @param issueStartDate
     * @purpose sets property issueStartDate
     */
    public void setIssueStartDate(Date issueStartDate) {
        this.issueStartDate = issueStartDate;
    }

    /**
     * @return List projectMembers of type Users
     */
    public List<Users> getProjectMembers() {
        return this.projectMembers;
    }

    /**
     * @param projectMembers
     * @purpose sets property projectMembers
     *
     */
    public void setProjectMembers(List<Users> projectMembers) {
        this.projectMembers = projectMembers;
    }

    /**
     * @return List priorityTypeList
     */
    public List getPriorityTypeList() {
        return this.priorityTypeList;
    }

    /**
     * @param priorityTypeList
     * @purpose sets property priorityTypeList
     */
    public void setPriorityTypeList(List priorityTypeList) {
        this.priorityTypeList = priorityTypeList;
    }

    /**
     * @return issueBean of type IssuesBean
     */
    public IssuesBean getIssueBean() {
        return this.issueBean;
    }

    /**
     * @param issueBean
     * @purpose sets property issueBean
     */
    public void setIssueBean(IssuesBean issueBean) {
        this.issueBean = issueBean;
    }

    /**
     * @return String trackerType
     */
    public String getTrackerType() {
        return this.trackerType;
    }

    /**
     * @param trackerType
     * @purpose sets property trackerType
     */
    public void setTrackerType(String trackerType) {
        this.trackerType = trackerType;
    }

    /**
     * @return List trackerTypeList
     */
    public List getTrackerTypeList() {
        return this.trackerTypeList;
    }

    /**
     * @param trackerTypeList
     * @purpose sets property trackerTypeList
     */
    public void setTrackerTypeList(List trackerTypeList) {
        this.trackerTypeList = trackerTypeList;
    }

    /**
     * @return statusTypeList
     */
    public List getStatusTypeList() {
        return this.statusTypeList;
    }

    /**
     * @param statusTypeList
     * @purpose sets property statusTypeList
     */
    public void setStatusTypeList(List statusTypeList) {
        this.statusTypeList = statusTypeList;
    }

    /**
     * @return statusType
     */
    public String getStatusType() {
        return this.statusType;
    }

    /**
     * @param statusType
     * @purpose sets property statusType
     */
    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    /**
     * @param hsr
     * @purpose sets property servletRequest
     */
    public void setServletRequest(HttpServletRequest hsr) {
        this.servletRequest = hsr;
    }

}
