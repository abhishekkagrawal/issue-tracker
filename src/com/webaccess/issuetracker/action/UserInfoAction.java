/*
 *      Document      : UserInfoAction.java
 *      Author        : Dinesh Saini
 *      Created on    : 29/11/2013
 *      Description   : Action Class for retrieving User Information who is logged
 *                      in currently from database.
 */
package com.webaccess.issuetracker.action;

import com.opensymphony.xwork2.ActionSupport;
import com.webaccess.issuetracker.bean.Users;
import com.webaccess.issuetracker.delegate.UserDelegate;
import com.webaccess.issuetracker.util.DateConvertor;
import com.webaccess.issuetracker.util.LoggingUtil;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 * Action Class for retrieving User Information from database whose session is
 * running currently.
 *
 * @author Dinesh Saini
 *
 */
public class UserInfoAction extends ActionSupport {

    private static final org.slf4j.Logger LOGGER = LoggingUtil.getLogger();
    private String empName;
    private String regDate;
    private String userId;
    private Users users;
    private String phone;
    private String empType;
    private List<Users> empRolesInProjectList;

    /**
     * Retrieves user details from session and checks if session is available or
     * not.
     *
     * @return String
     */
    @Override
    public String execute() {
        HttpSession session;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session == null || session.getAttribute("login") == null) {
                addActionError("Please login first");
                return "login";
            }
            if (session.getAttribute("userName") != null) {
                this.userId = (String) session.getAttribute("userName");
            } else {
                return "login";
            }
        } catch (Exception e) {
            LOGGER.error("UserInfoAction.run", e);
        }
        this.getUserInfo();
        return SUCCESS;
    }

    /**
     * Retrieve user information from database by calling delegate method from
     * delegate class.
     *
     * @return String success if user's record retrieved successfully
     */
    public String getUserInfo() {
        HttpSession session;
        UserDelegate userDelegate;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session != null && session.getAttribute("login") == null) {
                addActionError("Please login first");
                return "login";
            }
            userDelegate = new UserDelegate();
            this.users = new Users();
            this.users.setUserId(getUserId());
            userDelegate.getUserDetails(this.users);
            this.empRolesInProjectList = new ArrayList<>();
            this.empRolesInProjectList = userDelegate.getRolesInProjectsList(this.users.getEmpId());
            this.empName = this.users.getFirstName() + " " + this.users.getLastName();
            this.regDate = DateConvertor.timeStampTo(this.users.getRegDate());
            this.empType = this.users.getEmpType();
            this.phone = this.users.getPhone();
        } catch (Exception e) {
            LOGGER.error("UserInfoAction.run", e);
        }
        return SUCCESS;
    }

    /**
     * Returns user's role in different projects.
     *
     * @return List empRolesInProjectList
     */
    public List<Users> getEmpRolesInProjectList() {
        return this.empRolesInProjectList;
    }

    /**
     * Sets List empRolesInProjectList user's role in different projects
     *
     * @param empRolesInProjectList the empRolesInProjectList to set
     */
    public void setEmpRolesInProjectList(List<Users> empRolesInProjectList) {
        this.empRolesInProjectList = empRolesInProjectList;
    }

    /**
     * @return String phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return String empType
     */
    public String getEmpType() {
        return this.empType;
    }

    /**
     * @param empType the empType to set
     */
    public void setEmpType(String empType) {
        this.empType = empType;
    }

    /**
     * @return String userId
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
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

    /**
     * @return String empName
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
     * @return String regDate
     */
    public String getRegDate() {
        return this.regDate;
    }

    /**
     * @param regDate the regDate to set
     */
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}
