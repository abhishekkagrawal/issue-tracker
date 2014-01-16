/*
 *      Document     : GetProjectDataAction.java
 *      Author       : Dinesh Saini
 *      Created on   : 02/11/2013
 *      Description  : Action class retrieves closed and open projects in the
 *                     organization.
 */
package com.webaccess.issuetracker.action;

import com.opensymphony.xwork2.ActionSupport;
import com.webaccess.issuetracker.bean.ProjectBean;
import com.webaccess.issuetracker.delegate.ProjectDelegate;
import com.webaccess.issuetracker.util.LoggingUtil;
import com.webaccess.issuetracker.util.SessionValidator;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;

/**
 * Class GetProjectDataAction retrieves open projects list and closed project
 * list for projects index page.
 *
 * @author Dinesh Saini
 */
public class GetProjectDataAction extends ActionSupport {

    private static final Logger LOGGER = LoggingUtil.getLogger();
    private ProjectBean projectBean;
    private List<ProjectBean> crntPrjDscList;
    private List<ProjectBean> clsdPrjDscList;

    /**
     * Retrieves project lists from database.
     *
     * @return String
     */
    @Override
    public String execute() {
        HttpSession session;
        ProjectDelegate projectDelegate;
        int empId;
        int empTypeId;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (!SessionValidator.validateSession()) {
                addActionError("please login first");
                return "login";
            }
            empId = Integer.parseInt(session.getAttribute("empId").
                    toString());
            empTypeId = Integer.parseInt(session.getAttribute("empTypeId").
                    toString());
            this.crntPrjDscList = new ArrayList<>();
            this.clsdPrjDscList = new ArrayList<>();
            projectDelegate = new ProjectDelegate();
            this.crntPrjDscList = projectDelegate.getProjectDescription(empTypeId, empId);
            this.clsdPrjDscList = projectDelegate.getClosedProjectNames(empTypeId, empId);
        } catch (NumberFormatException e) {
            LOGGER.debug("GetProjectDataAction.run", e);
        }
        return SUCCESS;
    }

    /**
     * @return List
     */
    public List<ProjectBean> getClsdPrjDscList() {
        return this.clsdPrjDscList;
    }

    /**
     * @param clsdPrjDscList the clsdPrjDscList to set
     */
    public void setClsdPrjDscList(List<ProjectBean> clsdPrjDscList) {
        this.clsdPrjDscList = clsdPrjDscList;
    }

    /**
     * @return List crntPrjDscList
     */
    public List<ProjectBean> getCrntPrjDscList() {
        return this.crntPrjDscList;
    }

    /**
     * @param crntPrjDscList the crntPrjDscList to set
     */
    public void setCrntPrjDscList(List<ProjectBean> crntPrjDscList) {
        this.crntPrjDscList = crntPrjDscList;
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

}
