/*
 *      Document     : ProjectDelegate.java
 *      Author       : Dinesh Saini
 *      Created on   : 29/11/2013
 *      Description  : Delegate class for Add Project.
 */
package com.webaccess.issuetracker.delegate;

import com.webaccess.issuetracker.bean.IssuesBean;
import com.webaccess.issuetracker.bean.ProjectBean;
import com.webaccess.issuetracker.bean.Users;
import com.webaccess.issuetracker.dao.ProjectDao;
import java.util.List;

/**
 *
 * @author Dinesh Saini
 */
public class ProjectDelegate {

    private final ProjectDao projectDao;

    /**
     * @author Dinesh Saini
     * @ Class constructor to initialize property
     */
    public ProjectDelegate() {
        this.projectDao = new ProjectDao();
    }

    /**
     * @author Dinesh Saini
     * @param projId
     * @return List
     */
    public List<ProjectBean> getProjectModuleList(int projId) {
        return this.projectDao.getProjectModuleList(projId);
    }

    /**
     * @author Dinesh Saini
     * @param projectBean
     * @return boolean
     */
    public boolean updateEmpRoleType(ProjectBean projectBean) {
        return this.projectDao.updateEmpRoleType(projectBean);
    }

    /**
     * @author Dinesh Saini
     * @param projectId
     * @return List
     */
    public List<Users> getProjectMembers(int projectId) {
        return this.projectDao.getProjectMembers(projectId);

    }

    /**
     * @author Dinesh Saini
     * @param projectBean
     * @return ProjectBean
     */
    public ProjectBean getProjectMemberList(ProjectBean projectBean) {
        return this.projectDao.getProjectMemberList(projectBean);
    }

    /**
     * @author Dinesh Saini
     * @param projectBean
     * @return List
     */
    public List getAssignedMemberList(ProjectBean projectBean) {
        return this.projectDao.getAssignedMemberList(projectBean);
    }

    /**
     * @author Dinesh Saini
     * @param projectBean
     * @return boolean
     */
    public boolean validateProjectDocTitle(ProjectBean projectBean) {
        return this.projectDao.validateProjectDocTitle(projectBean);
    }

    /**
     * @author Dinesh Saini
     * @param projectBean
     * @return boolean
     */
    public boolean addProjectDocs(ProjectBean projectBean) {
        return this.projectDao.addProjectDocs(projectBean);
    }

    /**
     * @author Dinesh Saini
     * @param projectBean
     * @return boolean
     */
    public boolean addProjMember(ProjectBean projectBean) {
        return this.projectDao.addProjMember(projectBean);
    }

    /**
     * @author Dinesh Saini
     * @param projectBean
     * @return ProjectBean
     */
    public ProjectBean getProjectOverview(ProjectBean projectBean) {
        return this.projectDao.getProjectOverview(projectBean);
    }

    /**
     * @author Dinesh Saini
     * @param projectBean
     * @return int
     */
    public int getProjectId(ProjectBean projectBean) {
        return this.projectDao.getProjectId(projectBean);
    }

    /**
     * @author Dinesh Saini
     * @param empType
     * @param empId
     * @return List
     */
    public List<ProjectBean> getProjectDescription(int empType, int empId) {
        return this.projectDao.getProjectNames(empType, empId);
    }

    /**
     * @author Dinesh Saini
     * @param empType
     * @param empId
     * @return List
     */
    public List<ProjectBean> getClosedProjectNames(int empType, int empId) {
        return this.projectDao.getClosedProjectNames(empType, empId);
    }

    /**
     * @author Dinesh Saini
     * @param projectBean
     * @return boolean
     */
    public boolean addProject(ProjectBean projectBean) {
        return this.projectDao.addProject(projectBean);
    }

    /**
     * @author Dinesh Saini
     * @param projectBean
     * @return boolean
     */
    public boolean validateHomepage(ProjectBean projectBean) {
        return this.projectDao.validateHomePage(projectBean);
    }

    /**
     * @author Dinesh Saini
     * @return List
     */
    public List<String> getTrackerList() {
        return this.projectDao.getTrackerList();
    }

    /**
     * @author Dinesh Saini
     * @return List
     */
    public List<String> getModuleList() {
        return this.projectDao.getModuleList();
    }

    /**
     * @author Dinesh Saini
     * @param projectBean
     * @return boolean
     */
    public boolean validateIdentifier(ProjectBean projectBean) {
        return this.projectDao.validateIdentifier(projectBean);
    }

    /**
     * @author Dinesh Saini
     * @param projectId
     * @return List
     */
    public List getProjDocsDetails(int projectId) {
        return this.projectDao.getProjDocsDetails(projectId);
    }

    /**
     * @author Dinesh Saini
     * @param projectId
     * @return ProjectBean
     */
    public ProjectBean getProjectSettings(int projectId) {
        return this.projectDao.getProjectSettings(projectId);
    }

    /**
     * @author Dinesh Saini
     * @param projectBean
     * @return boolean
     */
    public boolean updateProjectSettings(ProjectBean projectBean) {
        return this.projectDao.updateProjectSettings(projectBean);
    }

    /**
     * @author Dinesh Saini
     * @param projectBean
     * @return ProjectBean
     */
    public ProjectBean getTotalIssueList(ProjectBean projectBean) {
        return this.projectDao.getOpenIssue(projectBean);
    }

    /**
     * @author Dinesh Saini
     * @param projectId
     * @return boolean
     */
    public boolean closeProject(int projectId) {
        return this.projectDao.closeProject(projectId);
    }

    /**
     * @author Dinesh Saini
     * @param projectId
     * @return List
     */
    public List<IssuesBean> getIssueActivityList(int projectId) {
        return this.projectDao.getIssueActivityList(projectId);
    }

    /**
     * @author Dinesh Saini
     * @param empTypeId
     * @param empId
     * @param projectKey
     * @return boolean
     */
    public boolean checkProjectAuthorization(int empTypeId, int empId, String projectKey) {
        return this.projectDao.checkProjectAuthorization(empTypeId, empId, projectKey);
    }
}
