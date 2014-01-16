/*
 *      Document     : AddProjectAction.java
 *      Author       : Dinesh Saini
 *      Created on   : 29/11/2013
 *      Description  : Action class for adding new project.
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
 * Class AddProjectAction performs add new project operation.
 *
 * @author Dinesh Saini
 */
public class AddProjectAction extends ActionSupport {

    private static final String SPACE_REGEX = "^[\\S][\\S]*$";
    private static final String URL_REGEX = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private static final Logger LOGGER = LoggingUtil.getLogger();
    private List<String> trackerList;
    private List<String> moduleList;
    private List trackerSelected;
    private List slctdModuleList;
    private ProjectBean projectBean;

    /**
     * Class constructor to initialize properties.
     */
    public AddProjectAction() {
        ProjectDelegate projectDelegate;
        try {
            this.trackerList = new ArrayList<>();
            this.moduleList = new ArrayList<>();
            projectDelegate = new ProjectDelegate();
            this.trackerList = projectDelegate.getTrackerList();
            this.moduleList = projectDelegate.getModuleList();
        } catch (Exception e) {
            LOGGER.error("AddProjectAction.run", e);
        }
    }

    /**
     * Default implementation of ActionSupport.
     *
     * @return String
     */
    @Override
    public String execute() {
        return SUCCESS;
    }

    /**
     * Validates new project details.
     *
     * @author Dinesh Saini
     */
    public void validator() {
        HttpSession session;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute("empType") != null) {
                if ("Admin".equals(session.getAttribute("empType"))) {
                    if (this.projectBean.getProjectName().length() == 0) {
                        addFieldError("projectName", "Project name required");
                    }
                    if (this.projectBean.getDescription().length() == 0) {
                        addFieldError("description", "Description required");
                    }
                    if (this.projectBean.getIdentifier().length() == 0) {
                        addFieldError("identifier", "Identifier required");
                    } else if (!this.projectBean.getIdentifier().matches(AddProjectAction.SPACE_REGEX)) {
                        addFieldError("identifier", "Invalid identifier");
                    }
                    if (this.projectBean.getHomePage().length() == 0) {
                        addFieldError("homePage", "HomePage required");
                    } else if (!this.projectBean.getHomePage().matches(AddProjectAction.URL_REGEX)) {
                        addFieldError("homePage", "Invalid url");
                    }
                    if (getTrackerSelected().isEmpty()) {
                        addFieldError("trackerSelected", "One or more tracker required");
                    }
                    if (getSlctdModuleList().isEmpty()) {
                        addFieldError("moduleSlctdList", "One or more module required");
                    }
                } else {
                    addActionError("You are not authorize to access this page");
                }
            }
        } catch (Exception e) {
            LOGGER.error("AddProjectAction.run", e);
        }
    }

    /**
     * Adds new project.
     *
     * @return String success if project added else error
     */
    public String addProject() {
        String status = "error";
        ProjectDelegate projectDelegate;
        try {
            if (!SessionValidator.validateSession()) {
                addActionError("please login first");
                return "login";
            }
            this.validator();
            if (!hasFieldErrors() && !hasActionErrors()) {
                this.getProjectBean().setTrackerList(getTrackerSelected());
                this.getProjectBean().setModuleList(getSlctdModuleList());
                projectDelegate = new ProjectDelegate();
                if (projectDelegate.validateIdentifier(this.getProjectBean())) {
                    status = "error";
                    addFieldError("projectBean.identifier",
                            "Identifier not available");
                } else {
                    if (projectDelegate.validateHomepage(this.getProjectBean())) {
                        status = "error";
                        addFieldError("projectBean.homePage",
                                "Homepage url not available");
                    } else {
                        if (projectDelegate.addProject(this.getProjectBean())) {
                            addActionMessage("Add Project Success");
                            status = "success";
                            this.projectBean.setProjectName("");
                            this.projectBean.setDescription("");
                            this.projectBean.setIdentifier("");
                            this.projectBean.setHomePage("");
                        } else {
                            status = "error";
                            addActionError("Add project failed");
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("AddProjectAction.run", e);
        }
        return status;
    }

    /**
     * @return List of selected modules for new project
     */
    public List getSlctdModuleList() {
        return this.slctdModuleList;
    }

    /**
     * @param slctdModuleList the slctdModuleList to set
     */
    public void setSlctdModuleList(List slctdModuleList) {
        this.slctdModuleList = slctdModuleList;
    }

    /**
     * @return List moduleList
     */
    public List<String> getModuleList() {
        return this.moduleList;
    }

    /**
     * @param moduleList
     */
    public void setModuleList(List<String> moduleList) {
        this.moduleList = moduleList;
    }

    /**
     * @return list the trackers to set
     */
    public List<String> getTrackerList() {
        return this.trackerList;
    }

    /**
     * @param trackerList the trackerList to select
     */
    public void setTrackerList(List<String> trackerList) {
        this.trackerList = trackerList;
    }

    /**
     * @return list trackerSelected
     */
    public List getTrackerSelected() {
        return this.trackerSelected;
    }

    /**
     * @param trackerSelected the trackerSelected to set
     */
    public void setTrackerSelected(List trackerSelected) {
        this.trackerSelected = trackerSelected;
    }

    /**
     * @return the projectBean
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
