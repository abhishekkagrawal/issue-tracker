/*
 *      Document     : IssuesBean.java
 *      Author       : Dinesh Saini
 *      Created on   : 16/12/13
 *      Description  : Bean class for issues.
 */
package com.webaccess.issuetracker.bean;

import java.util.List;

/**
 *
 * @author Dinesh Saini
 */
public class IssuesBean {

    private int trackerId;
    private int projectId;
    private int assigneeId;
    private int assignerId;
    private int estimatedHour;
    private int issueId;
    private int statusId;
    private String loginId;
    private String projectIdentifier;
    private String projectName;
    private List trackersTypeList;
    private List statusTypeList;
    private List priorityTypeList;
    private List prjctTrckrList;
    private String trackerType;
    private String issueSubject;
    private String issueDesc;
    private String priorityType;
    private String statusType;
    private int fileId;
    private String fileName;
    private String insertionDate;
    private String fileLocation;
    private String issueStartDate;
    private String issueDueDate;
    private String issueName;
    private String issueUpdateDate;
    private String issueEndDate;
    private String empName;
    private String issueIdentifier;
    private String assignee;
    private String asigneeLoginId;
    private String assigner;
    private String assignerLoginId;

    /**
     * @return the trackerId
     */
    public int getTrackerId() {
        return this.trackerId;
    }

    /**
     * @param trackerId the trackerId to set
     */
    public void setTrackerId(int trackerId) {
        this.trackerId = trackerId;
    }

    /**
     * @return the projectId
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
     * @return the assigneeId
     */
    public int getAssigneeId() {
        return this.assigneeId;
    }

    /**
     * @param assigneeId the assigneeId to set
     */
    public void setAssigneeId(int assigneeId) {
        this.assigneeId = assigneeId;
    }

    /**
     * @return the assignerId
     */
    public int getAssignerId() {
        return this.assignerId;
    }

    /**
     * @param assignerId the assignerId to set
     */
    public void setAssignerId(int assignerId) {
        this.assignerId = assignerId;
    }

    /**
     * @return the estimatedHour
     */
    public int getEstimatedHour() {
        return this.estimatedHour;
    }

    /**
     * @param estimatedHour the estimatedHour to set
     */
    public void setEstimatedHour(int estimatedHour) {
        this.estimatedHour = estimatedHour;
    }

    /**
     * @return the issueId
     */
    public int getIssueId() {
        return this.issueId;
    }

    /**
     * @param issueId the issueId to set
     */
    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    /**
     * @return the statusId
     */
    public int getStatusId() {
        return this.statusId;
    }

    /**
     * @param statusId the statusId to set
     */
    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    /**
     * @return the loginId
     */
    public String getLoginId() {
        return this.loginId;
    }

    /**
     * @param loginId the loginId to set
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * @return the projectIdentifier
     */
    public String getProjectIdentifier() {
        return this.projectIdentifier;
    }

    /**
     * @param projectIdentifier the projectIdentifier to set
     */
    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
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
     * @return the trackersTypeList
     */
    public List getTrackersTypeList() {
        return this.trackersTypeList;
    }

    /**
     * @param trackersTypeList the trackersTypeList to set
     */
    public void setTrackersTypeList(List trackersTypeList) {
        this.trackersTypeList = trackersTypeList;
    }

    /**
     * @return the statusTypeList
     */
    public List getStatusTypeList() {
        return this.statusTypeList;
    }

    /**
     * @param statusTypeList the statusTypeList to set
     */
    public void setStatusTypeList(List statusTypeList) {
        this.statusTypeList = statusTypeList;
    }

    /**
     * @return the priorityTypeList
     */
    public List getPriorityTypeList() {
        return this.priorityTypeList;
    }

    /**
     * @param priorityTypeList the priorityTypeList to set
     */
    public void setPriorityTypeList(List priorityTypeList) {
        this.priorityTypeList = priorityTypeList;
    }

    /**
     * @return the prjctTrckrList
     */
    public List getPrjctTrckrList() {
        return this.prjctTrckrList;
    }

    /**
     * @param prjctTrckrList the prjctTrckrList to set
     */
    public void setPrjctTrckrList(List prjctTrckrList) {
        this.prjctTrckrList = prjctTrckrList;
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
     * @return the issueSubject
     */
    public String getIssueSubject() {
        return this.issueSubject;
    }

    /**
     * @param issueSubject the issueSubject to set
     */
    public void setIssueSubject(String issueSubject) {
        this.issueSubject = issueSubject;
    }

    /**
     * @return the issueDesc
     */
    public String getIssueDesc() {
        return this.issueDesc;
    }

    /**
     * @param issueDesc the issueDesc to set
     */
    public void setIssueDesc(String issueDesc) {
        this.issueDesc = issueDesc;
    }

    /**
     * @return the priorityType
     */
    public String getPriorityType() {
        return this.priorityType;
    }

    /**
     * @param priorityType the priorityType to set
     */
    public void setPriorityType(String priorityType) {
        this.priorityType = priorityType;
    }

    /**
     * @return the statusType
     */
    public String getStatusType() {
        return this.statusType;
    }

    /**
     * @param statusType the statusType to set
     */
    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    /**
     * @return the fileId
     */
    public int getFileId() {
        return this.fileId;
    }

    /**
     * @param fileId the fileId to set
     */
    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the insertionDate
     */
    public String getInsertionDate() {
        return this.insertionDate;
    }

    /**
     * @param insertionDate the insertionDate to set
     */
    public void setInsertionDate(String insertionDate) {
        this.insertionDate = insertionDate;
    }

    /**
     * @return the fileLocation
     */
    public String getFileLocation() {
        return this.fileLocation;
    }

    /**
     * @param fileLocation the fileLocation to set
     */
    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    /**
     * @return the issueStartDate
     */
    public String getIssueStartDate() {
        return this.issueStartDate;
    }

    /**
     * @param issueStartDate the issueStartDate to set
     */
    public void setIssueStartDate(String issueStartDate) {
        this.issueStartDate = issueStartDate;
    }

    /**
     * @return the issueDueDate
     */
    public String getIssueDueDate() {
        return this.issueDueDate;
    }

    /**
     * @param issueDueDate the issueDueDate to set
     */
    public void setIssueDueDate(String issueDueDate) {
        this.issueDueDate = issueDueDate;
    }

    /**
     * @return the issueName
     */
    public String getIssueName() {
        return this.issueName;
    }

    /**
     * @param issueName the issueName to set
     */
    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    /**
     * @return the issueUpdateDate
     */
    public String getIssueUpdateDate() {
        return this.issueUpdateDate;
    }

    /**
     * @param issueUpdateDate the issueUpdateDate to set
     */
    public void setIssueUpdateDate(String issueUpdateDate) {
        this.issueUpdateDate = issueUpdateDate;
    }

    /**
     * @return the issueEndDate
     */
    public String getIssueEndDate() {
        return this.issueEndDate;
    }

    /**
     * @param issueEndDate the issueEndDate to set
     */
    public void setIssueEndDate(String issueEndDate) {
        this.issueEndDate = issueEndDate;
    }

    /**
     * @return the empName
     */
    public String getEmpName() {
        return this.empName;
    }

    /**
     * @param empName the empName to set
     */
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    /**
     * @return the issueIdentifier
     */
    public String getIssueIdentifier() {
        return this.issueIdentifier;
    }

    /**
     * @param issueIdentifier the issueIdentifier to set
     */
    public void setIssueIdentifier(String issueIdentifier) {
        this.issueIdentifier = issueIdentifier;
    }

    /**
     * @return the assignee
     */
    public String getAssignee() {
        return this.assignee;
    }

    /**
     * @param assignee the assignee to set
     */
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    /**
     * @return the asigneeLoginId
     */
    public String getAsigneeLoginId() {
        return this.asigneeLoginId;
    }

    /**
     * @param asigneeLoginId the asigneeLoginId to set
     */
    public void setAsigneeLoginId(String asigneeLoginId) {
        this.asigneeLoginId = asigneeLoginId;
    }

    /**
     * @return the assigner
     */
    public String getAssigner() {
        return this.assigner;
    }

    /**
     * @param assigner the assigner to set
     */
    public void setAssigner(String assigner) {
        this.assigner = assigner;
    }

    /**
     * @return the assignerLoginId
     */
    public String getAssignerLoginId() {
        return this.assignerLoginId;
    }

    /**
     * @param assignerLoginId the assignerLoginId to set
     */
    public void setAssignerLoginId(String assignerLoginId) {
        this.assignerLoginId = assignerLoginId;
    }

}
