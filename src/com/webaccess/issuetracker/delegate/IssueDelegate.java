/*
 *      Document     : IssueDelegate.java
 *      Author       : Dinesh Saini
 *      Created on   : 16/12/13
 *      Description  : delegate class for issue action.
 */
package com.webaccess.issuetracker.delegate;

import com.webaccess.issuetracker.bean.IssuesBean;
import com.webaccess.issuetracker.dao.IssueDao;
import java.util.List;

/**
 *
 * @author Dinesh Saini
 */
public class IssueDelegate {

    private final IssueDao issueDao;

    /**
     * Class constructor to initialize property.
     */
    public IssueDelegate() {
        this.issueDao = new IssueDao();
    }

    /**
     * @param issueId
     * @return List
     */
    public List<IssuesBean> getIssueDocs(int issueId) {
        return this.issueDao.getIssueDocs(issueId);
    }

    /**
     * @param issuesBean
     * @return boolean
     */
    public boolean addNewIssue(IssuesBean issuesBean) {
        return this.issueDao.addNewIssue(issuesBean);
    }

    /**
     * @param issuesBean
     * @return IssueBean issuesBean
     */
    public IssuesBean getFormData(IssuesBean issuesBean) {
        return this.issueDao.getFormData(issuesBean);
    }

    /**
     * @param projectId
     * @return List
     */
    public List getIssueRecords(int projectId) {
        return this.issueDao.getIssueRecords(projectId);
    }

    /**
     * @param issueId
     * @return IssuesBean
     */
    public IssuesBean getIssueOverview(int issueId) {
        return this.issueDao.getIssueOverview(issueId);
    }

    /**
     * @param issueBean
     * @return boolean
     */
    public boolean updateIssue(IssuesBean issueBean) {
        return this.issueDao.updateIssue(issueBean);
    }

    /**
     * @param empId
     * @return List
     */
    public List getUserIssues(int empId) {
        return this.issueDao.getUserIssues(empId);
    }

    /**
     * @param empId
     * @return List
     */
    public List getUserIssuesReported(int empId) {
        return this.issueDao.getUserIssuesReported(empId);
    }

}
