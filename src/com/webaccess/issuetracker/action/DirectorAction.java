/*
 *      Document     : DirectorAction.java
 *      Author       : Dinesh Saini
 *      Created on   : 30/12/2013
 *      Description  : action class redirects users to their homepage according
 *                    to their designation
 */
package com.webaccess.issuetracker.action;

import com.opensymphony.xwork2.ActionSupport;
import com.webaccess.issuetracker.bean.IssuesBean;
import com.webaccess.issuetracker.delegate.IssueDelegate;
import com.webaccess.issuetracker.util.LoggingUtil;
import com.webaccess.issuetracker.util.SessionValidator;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

/**
 * Director class for redirecting user's to their home page according to their
 * designation.
 *
 * @author Dinesh Saini
 */
public class DirectorAction extends ActionSupport {

    private static final Logger LOGGER = LoggingUtil.getLogger();
    private int empId;
    private List<IssuesBean> issueAsigneeLst;
    private List<IssuesBean> assignerIssueList;

    /**
     * Redirect developer to their home page and retrieve issues reported by
     * them and assigned to them.
     *
     * @return String
     */
    public String developerRedirect() {
        try {
            if (!SessionValidator.validateSession()) {
                addActionError("please login first");
                return "login";
            }
            this.setEmpId(SessionValidator.getEmployeeId());
            this.getUserHomeData();
        } catch (NumberFormatException e) {
            LOGGER.error("DirectorAction.run", e);
        }
        return "success";
    }

    /**
     * Redirect tester to their home page and retrieve issues reported by them
     * and assigned to them.
     *
     * @return String
     */
    public String testerRedirect() {
        try {
            if (!SessionValidator.validateSession()) {
                addActionError("please login first");
                return "login";
            }
            this.setEmpId(SessionValidator.getEmployeeId());
            this.getUserHomeData();
        } catch (NumberFormatException e) {
            LOGGER.error("DirectorAction.run", e);
        }
        return "success";
    }

    /**
     * Redirect Manager to their home page and retrieve issues reported by them
     * and assigned to them.
     *
     * @return String
     */
    public String managerRedirect() {
        try {
            if (!SessionValidator.validateSession()) {
                addActionError("please login first");
                return "login";
            }
            this.setEmpId(SessionValidator.getEmployeeId());
            this.getUserHomeData();
        } catch (NumberFormatException e) {
            LOGGER.error("DirectorAction.run", e);
        }
        return "success";
    }

    /**
     * Redirect admin to their homePage.
     *
     * @return String
     */
    public String adminRedirect() {
        try {
            if (!SessionValidator.validateSession()) {
                addActionError("please login first");
                return "login";
            }
        } catch (NumberFormatException e) {
            LOGGER.error("DirectorAction.run", e);
        }
        return "success";
    }

    /**
     * Retrieves user home page data from database.
     */
    public void getUserHomeData() {
        IssueDelegate issueDelegate;
        try {
            issueDelegate = new IssueDelegate();
            this.setIssueAsigneeLst(new ArrayList<IssuesBean>());
            this.setIssueAsigneeLst((List<IssuesBean>) issueDelegate.
                    getUserIssues(this.getEmpId()));
            this.setAssignerIssueList(new ArrayList<IssuesBean>());
            this.setAssignerIssueList((List<IssuesBean>) issueDelegate.
                    getUserIssuesReported(this.getEmpId()));
        } catch (Exception e) {
            LOGGER.error("DirectorAction.run", e);
        }

    }

    /**
     * @return the empId
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
     * @return the issueAsigneeLst
     */
    public List<IssuesBean> getIssueAsigneeLst() {
        return this.issueAsigneeLst;
    }

    /**
     * @param issueAsigneeLst the issueAsigneeLst to set
     */
    public void setIssueAsigneeLst(List<IssuesBean> issueAsigneeLst) {
        this.issueAsigneeLst = issueAsigneeLst;
    }

    /**
     * @return the assignerIssueList
     */
    public List<IssuesBean> getAssignerIssueList() {
        return this.assignerIssueList;
    }

    /**
     * @param assignerIssueList the assignerIssueList to set
     */
    public void setAssignerIssueList(List<IssuesBean> assignerIssueList) {
        this.assignerIssueList = assignerIssueList;
    }

}
