/*
 *      Document    : UpdateUserInfoAction.java
 *      Author      : Dinesh Saini
 *      Date        : 02/12/2013
 *      Description : Action class for updating user information
 */
package com.webaccess.issuetracker.action;

import com.opensymphony.xwork2.ActionSupport;
import com.webaccess.issuetracker.bean.Users;
import com.webaccess.issuetracker.delegate.UserDelegate;
import com.webaccess.issuetracker.util.LoggingUtil;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 * Updates user's profile information.
 *
 * @author Dinesh Saini
 */
public class UpdateUserInfoAction extends ActionSupport {

    private static final String NUM_REGEX = "^[0-9]+$";
    private static final String NAME_REGEX = "^[a-zA-Z][a-zA-Z ]+$";
    private static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    private static final org.slf4j.Logger LOGGER = LoggingUtil.getLogger();
    private Users users;

    /**
     * Gets user data from database to update.
     *
     * @return String
     */
    public String getFormData() {
        HttpSession session;
        UserDelegate userDelegate;
        String userId = null;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session == null || session.getAttribute("login") == null) {
                addActionError("Please login first");
                return "login";
            }
            if (session.getAttribute("userName") != null) {
                userId = (String) session.getAttribute("userName");
            }
            userDelegate = new UserDelegate();
            this.users = new Users();
            this.users.setUserId(userId);
            userDelegate.getUserDetails(this.users);
        } catch (Exception e) {
            LOGGER.error("UpdateUserInfoAction.run", e);
        }
        return SUCCESS;
    }

    /**
     * Validates user's details for update.
     *
     * @author Dinesh Saini
     */
    public void validator() {
        try {
            if (this.users.getFirstName().length() == 0) {
                addFieldError("users.firstName", "First name Required");
            } else if (!this.users.getFirstName().matches(UpdateUserInfoAction.NAME_REGEX)) {
                addFieldError("users.firstName", "Invalid first name");
            }
            if (this.users.getLastName().length() == 0) {
                addFieldError("users.lastName", "Last name required");
            } else if (!this.users.getLastName().matches(UpdateUserInfoAction.NAME_REGEX)) {
                addFieldError("users.lastName", "Invalid last name");
            }
            if (this.users.getEmail().length() == 0) {
                addFieldError("users.email", "Email required");
            } else if (!this.users.getEmail().matches(UpdateUserInfoAction.EMAIL_REGEX)) {
                addFieldError("users.email", "Check email format");
            }
            if (this.users.getPhone().length() == 0) {
                addFieldError("users.phone", "Mobile num required");
            } else {
                if (this.users.getPhone().length() != 10) {
                    addFieldError("users.phone", "Mobile number length should be 10");
                } else if (!this.users.getPhone().matches(UpdateUserInfoAction.NUM_REGEX)) {
                    addFieldError("users.phone", "Check number format");
                }
            }
        } catch (Exception e) {
            LOGGER.error("UpdateUserInfoAction.run", e);
        }
    }

    /**
     * Perform update operation user's details.
     *
     * @return String success if update succeed else error
     */
    public String updateUserInfo() {
        UserDelegate userDelegate;
        String flag = "error";
        HttpSession session;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session == null || session.getAttribute("login") == null) {
                addActionError("Please login first");
                return "login";
            }
            this.validator();
            if (!hasFieldErrors()) {
                userDelegate = new UserDelegate();
                if (!userDelegate.validateEmail(this.users)) {
                    if (userDelegate.updateUsers(this.users)) {
                        addActionMessage("User update success");
                        flag = "success";
                    } else {
                        flag = "error";
                        addActionError("User update failed");
                    }
                } else {
                    flag = "error";
                    addFieldError("users.email", "Email already exist");
                }
            }
        } catch (Exception e) {
            LOGGER.error("UpdateUserInfoAction.run", e);
        }
        return flag;
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

}
