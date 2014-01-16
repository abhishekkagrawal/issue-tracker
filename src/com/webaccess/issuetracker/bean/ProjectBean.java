/*
 *      Document     : ProjectBean.java
 *      Author       : Dinesh Saini
 *      Created on   : 29/11/2013
 *      Description  : Bean class for Project Details.
 */
package com.webaccess.issuetracker.bean;

import java.util.List;

/**
 * @author Dinesh Saini
 */
public class ProjectBean {

    private int projectId;
    private int empId;
    private int moduleId;
    private int trackerId;
    private int openIssuesCount;
    private int closedIssuesCount;
    private int projDocumentId;
    private String trackerType;
    private String projectName;
    private String description;
    private String identifier;
    private String homePage;
    private int projectStatus;
    private List trackerList;
    private List moduleList;
    private List empRoleTypeList;
    private List slctdPrjMmbrList;
    private String prjctInsrtDate;
    private String projectDocTitle;
    private String prjctDocPath;
    private String prjDocInsrtDate;
    private List managerList;
    private List developerList;
    private List testerList;
    private String moduleType;
    private String actionURL;
    private List projectModuleList;
    private List prjctTrckerList;
    private List<ProjectBean> openIssueList;
    private List<ProjectBean> closedIssueList;

    /**
     * @author Dinesh Saini
     * @return the projectId
     */
    public int getProjectId() {
        return this.projectId;
    }

    /**
     * @author Dinesh Saini
     * @param projectId the projectId to set
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    /**
     * @author Dinesh Saini
     * @return the empId
     */
    public int getEmpId() {
        return this.empId;
    }

    /**
     * @author Dinesh Saini
     * @param empId the empId to set
     */
    public void setEmpId(int empId) {
        this.empId = empId;
    }

    /**
     * @author Dinesh Saini
     * @return the moduleId
     */
    public int getModuleId() {
        return this.moduleId;
    }

    /**
     * @author Dinesh Saini
     * @param moduleId the moduleId to set
     */
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * @author Dinesh Saini
     * @return the trackerId
     */
    public int getTrackerId() {
        return this.trackerId;
    }

    /**
     * @author Dinesh Saini
     * @param trackerId the trackerId to set
     */
    public void setTrackerId(int trackerId) {
        this.trackerId = trackerId;
    }

    /**
     * @author Dinesh Saini
     * @return the openIssuesCount
     */
    public int getOpenIssuesCount() {
        return this.openIssuesCount;
    }

    /**
     * @author Dinesh Saini
     * @param openIssuesCount the openIssuesCount to set
     */
    public void setOpenIssuesCount(int openIssuesCount) {
        this.openIssuesCount = openIssuesCount;
    }

    /**
     * @return the closedIssuesCount
     */
    public int getClosedIssuesCount() {
        return this.closedIssuesCount;
    }

    /**
     * @param closedIssuesCount the closedIssuesCount to set
     */
    public void setClosedIssuesCount(int closedIssuesCount) {
        this.closedIssuesCount = closedIssuesCount;
    }

    /**
     * @return the projDocumentId
     */
    public int getProjDocumentId() {
        return this.projDocumentId;
    }

    /**
     * @param projDocumentId the projDocumentId to set
     */
    public void setProjDocumentId(int projDocumentId) {
        this.projDocumentId = projDocumentId;
    }

    /**
     * @return the trackerType
     */
    public String getTrackerType() {
        return this.trackerType;
    }

    /**
     * @param trackerType the trackerType to set
     */
    public void setTrackerType(String trackerType) {
        this.trackerType = trackerType;
    }

    /**
     * @return the projectName
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
     * @return the description
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
     * @return the identifier
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
     * @return the homePage
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
     * @return the projectStatus
     */
    public int getProjectStatus() {
        return this.projectStatus;
    }

    /**
     * @param projectStatus the projectStatus to set
     */
    public void setProjectStatus(int projectStatus) {
        this.projectStatus = projectStatus;
    }

    /**
     * @return the trackerList
     */
    public List getTrackerList() {
        return this.trackerList;
    }

    /**
     * @param trackerList the trackerList to set
     */
    public void setTrackerList(List trackerList) {
        this.trackerList = trackerList;
    }

    /**
     * @return the moduleList
     */
    public List getModuleList() {
        return this.moduleList;
    }

    /**
     * @param moduleList the moduleList to set
     */
    public void setModuleList(List moduleList) {
        this.moduleList = moduleList;
    }

    /**
     * @return the empRoleTypeList
     */
    public List getEmpRoleTypeList() {
        return this.empRoleTypeList;
    }

    /**
     * @param empRoleTypeList the empRoleTypeList to set
     */
    public void setEmpRoleTypeList(List empRoleTypeList) {
        this.empRoleTypeList = empRoleTypeList;
    }

    /**
     * @return the slctdPrjMmbrList
     */
    public List getSlctdPrjMmbrList() {
        return this.slctdPrjMmbrList;
    }

    /**
     * @param slctdPrjMmbrList the slctdPrjMmbrList to set
     */
    public void setSlctdPrjMmbrList(List slctdPrjMmbrList) {
        this.slctdPrjMmbrList = slctdPrjMmbrList;
    }

    /**
     * @return the prjctInsrtDate
     */
    public String getPrjctInsrtDate() {
        return this.prjctInsrtDate;
    }

    /**
     * @param prjctInsrtDate the prjctInsrtDate to set
     */
    public void setPrjctInsrtDate(String prjctInsrtDate) {
        this.prjctInsrtDate = prjctInsrtDate;
    }

    /**
     * @return the projectDocTitle
     */
    public String getProjectDocTitle() {
        return this.projectDocTitle;
    }

    /**
     * @param projectDocTitle the projectDocTitle to set
     */
    public void setProjectDocTitle(String projectDocTitle) {
        this.projectDocTitle = projectDocTitle;
    }

    /**
     * @return the prjctDocPath
     */
    public String getPrjctDocPath() {
        return this.prjctDocPath;
    }

    /**
     * @param prjctDocPath the prjctDocPath to set
     */
    public void setPrjctDocPath(String prjctDocPath) {
        this.prjctDocPath = prjctDocPath;
    }

    /**
     * @return the prjDocInsrtDate
     */
    public String getPrjDocInsrtDate() {
        return this.prjDocInsrtDate;
    }

    /**
     * @param prjDocInsrtDate the prjDocInsrtDate to set
     */
    public void setPrjDocInsrtDate(String prjDocInsrtDate) {
        this.prjDocInsrtDate = prjDocInsrtDate;
    }

    /**
     * @return the managerList
     */
    public List getManagerList() {
        return this.managerList;
    }

    /**
     * @param managerList the managerList to set
     */
    public void setManagerList(List managerList) {
        this.managerList = managerList;
    }

    /**
     * @return the developerList
     */
    public List getDeveloperList() {
        return this.developerList;
    }

    /**
     * @param developerList the developerList to set
     */
    public void setDeveloperList(List developerList) {
        this.developerList = developerList;
    }

    /**
     * @return the testerList
     */
    public List getTesterList() {
        return this.testerList;
    }

    /**
     * @param testerList the testerList to set
     */
    public void setTesterList(List testerList) {
        this.testerList = testerList;
    }

    /**
     * @return the moduleType
     */
    public String getModuleType() {
        return this.moduleType;
    }

    /**
     * @param moduleType the moduleType to set
     */
    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    /**
     * @return the actionURL
     */
    public String getActionURL() {
        return this.actionURL;
    }

    /**
     * @param actionURL the actionURL to set
     */
    public void setActionURL(String actionURL) {
        this.actionURL = actionURL;
    }

    /**
     * @return the projectModuleList
     */
    public List getProjectModuleList() {
        return this.projectModuleList;
    }

    /**
     * @param projectModuleList the projectModuleList to set
     */
    public void setProjectModuleList(List projectModuleList) {
        this.projectModuleList = projectModuleList;
    }

    /**
     * @return the prjctTrckerList
     */
    public List getPrjctTrckerList() {
        return this.prjctTrckerList;
    }

    /**
     * @param prjctTrckerList the prjctTrckerList to set
     */
    public void setPrjctTrckerList(List prjctTrckerList) {
        this.prjctTrckerList = prjctTrckerList;
    }

    /**
     * @return the openIssueList
     */
    public List<ProjectBean> getOpenIssueList() {
        return this.openIssueList;
    }

    /**
     * @param openIssueList the openIssueList to set
     */
    public void setOpenIssueList(List<ProjectBean> openIssueList) {
        this.openIssueList = openIssueList;
    }

    /**
     * @return the closedIssueList
     */
    public List<ProjectBean> getClosedIssueList() {
        return this.closedIssueList;
    }

    /**
     * @param closedIssueList the closedIssueList to set
     */
    public void setClosedIssueList(List<ProjectBean> closedIssueList) {
        this.closedIssueList = closedIssueList;
    }
}
