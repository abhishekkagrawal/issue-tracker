/*
 *      Document     : UserRegFormAction.java
 *      Author       : Dinesh Saini
 *      Created on   : 27/11/2013
 *      Description  : Action class for new User Registration.
 */
package com.webaccess.issuetracker.action;

import com.opensymphony.xwork2.ActionSupport;
import com.webaccess.issuetracker.delegate.UserDelegate;
import com.webaccess.issuetracker.util.LoggingUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Class perform add user operation to add a new user in the organization.
 *
 * @author Dinesh Saini
 */
public class UserRegFormAction extends ActionSupport {

    private static final org.slf4j.Logger LOGGER = LoggingUtil.getLogger();
    private List<String> empTypList;

    /**
     * Retrieves employee role type list from database.
     *
     * @author Dinesh Saini
     * @return String status
     */
    @Override
    public String execute() {
        UserDelegate addUserDelegate;
        try {
            addUserDelegate = new UserDelegate();
            this.empTypList = new ArrayList<>();
            this.empTypList = addUserDelegate.getEmpTypeList();
        } catch (Exception e) {
            LOGGER.error("UserRegFormAction", e);
        }
        return SUCCESS;
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
}
