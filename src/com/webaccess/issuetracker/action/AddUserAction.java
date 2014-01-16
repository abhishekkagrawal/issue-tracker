/*
 *      Document     : AddUserAction.java
 *      Author       : Dinesh Saini
 *      Created on   : 27/11/2013
 *      Description  : Action Class to perform add user operation in the
 *                     organization.
 */
package com.webaccess.issuetracker.action;

import com.opensymphony.xwork2.ActionSupport;
import com.webaccess.issuetracker.bean.Users;
import com.webaccess.issuetracker.delegate.UserDelegate;
import com.webaccess.issuetracker.util.LoggingUtil;
import com.webaccess.issuetracker.util.MD5Encryptor;
import com.webaccess.issuetracker.util.SessionValidator;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;

/**
 * Class AddUserAction perform add user operation in organization.
 *
 * @author Dinesh Saini
 */
public class AddUserAction extends ActionSupport {

    private static final String SPACE_REGEX = "^[\\S][\\S]*$";
    private static final String NUM_REGEX = "^[0-9]+$";
    private static final String NAME_REGEX = "^[a-zA-Z][a-zA-Z]+$";
    private static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@"
            + "([\\w]+\\.)+[\\w]+[\\w]$";
    private static final Logger LOGGER = LoggingUtil.getLogger();
    private String login;
    private String password;
    private String password1;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String employeeCategory;
    private List empTypList;

    private Users users;

    /**
     * Class constructor to initializes variable.
     */
    public AddUserAction() {
        UserDelegate addUserDelegate;
        try {
            addUserDelegate = new UserDelegate();
            this.empTypList = new ArrayList<>();
            this.empTypList = addUserDelegate.getEmpTypeList();
        } catch (Exception e) {
            LOGGER.error("AddUserAction.run", e);
        }
    }

    /**
     * Validate new user details.
     */
    @Override
    public void validate() {
        HttpSession session;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if ("Admin".equals(session.getAttribute("empType"))) {
                if (getLogin().length() == 0) {
                    addFieldError("login", "LoginId required");
                } else if (!getLogin().matches(AddUserAction.SPACE_REGEX)) {
                    addFieldError("login", "LoginId is invalid");
                }
                if (getPassword().length() == 0) {
                    addFieldError("password", "Password required");
                }
                if (getPassword1().length() == 0) {
                    addFieldError("password1", "Password required");
                }
                if (!getPassword().equals(getPassword1())) {
                    addFieldError("password1", "Password not matched");
                }
                if (getFirstName().length() == 0) {
                    addFieldError("firstName", "First name required");
                } else if (!getFirstName().matches(AddUserAction.NAME_REGEX)) {
                    addFieldError("firstName", "Invalid first name");
                }
                if (getLastName().length() == 0) {
                    addFieldError("lastName", "Last name required");
                } else if (!getLastName().matches(AddUserAction.NAME_REGEX)) {
                    addFieldError("lastName", "Invalid last name");
                }
                if (getEmail().length() == 0) {
                    addFieldError("email", "Email required");
                } else if (!getEmail().matches(AddUserAction.EMAIL_REGEX)) {
                    addFieldError("email", "Check email format");
                }
                if (getMobile().length() == 0) {
                    addFieldError("mobile", "Mobile num required");
                } else {
                    if (getMobile().length() != 10) {
                        addFieldError("mobile", "Mobile number length should be"
                                + " 10");
                    } else if (!getMobile().matches(AddUserAction.NUM_REGEX)) {
                        addFieldError("mobile", "Check number format");
                    }
                }
                if (!this.empTypList.contains(getEmployeeCategory())) {
                    addFieldError("employeeCategory", "Invalid employee type");
                }
            } else {
                addActionError("You are not authorize to access this page");
            }
        } catch (Exception e) {
            LOGGER.error("AddUserAction.run", e);
        }
    }

    /**
     * Performs add user operation.
     *
     * @return String
     */
    public String addUser() {
        UserDelegate addUserDelegate;
        String status = "error";
        try {
            if (!SessionValidator.validateSession()) {
                addActionError("please login first");
                return "login";
            }
            if (!hasFieldErrors() && !hasActionErrors()) {
                this.users = new Users();
                this.users.setUserId(getLogin());
                this.users.setEmail(getEmail());
                this.users.setPassword(MD5Encryptor.md5(getPassword()));
                this.users.setFirstName(getFirstName());
                this.users.setLastName(getLastName());
                this.users.setPhone(getMobile());
                this.users.setEmpType(getEmployeeCategory());
                addUserDelegate = new UserDelegate();
                if (!addUserDelegate.validateLoginId(this.users)) {
                    if (!addUserDelegate.validateEmail(this.users)) {
                        if (addUserDelegate.addUsers(this.users)) {
                            addActionMessage("User Added Successfully");
                            status = "success";
                            this.login = "";
                            this.password = "";
                            this.firstName = "";
                            this.lastName = "";
                            this.email = "";
                            this.mobile = "";
                        } else {
                            status = "error";
                            addActionError("User Addition failed");
                        }
                    } else {
                        status = "error";
                        addFieldError("email", "Email already exist");
                    }
                } else {
                    status = "error";
                    addFieldError("login", "LoginId already exist");
                }
            }
        } catch (Exception e) {
            LOGGER.error("AddUserAction.run", e);
        }
        return status;
    }

    /**
     * @return String mobile
     */
    public String getMobile() {
        return this.mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return String login
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * @param login sets property login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return String password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return property password1
     */
    public String getPassword1() {
        return this.password1;
    }

    /**
     * @param password1 the password1 to set
     */
    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    /**
     * @return String firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return String lastName
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return String email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String employeeCategory
     */
    public String getEmployeeCategory() {
        return this.employeeCategory;
    }

    /**
     * @param employeeCategory the employeeCategory to set
     */
    public void setEmployeeCategory(String employeeCategory) {
        this.employeeCategory = employeeCategory;
    }

    /**
     * @return List empTypList
     */
    public List getEmpTypList() {
        return this.empTypList;
    }

    /**
     * @param empTypList the empTypeList to set
     */
    public void setEmpTypList(List empTypList) {
        this.empTypList = empTypList;
    }

}
