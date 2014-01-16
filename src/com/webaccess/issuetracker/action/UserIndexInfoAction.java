/*
 *      Document     : UserIndexInfoAction.java
 *      Author       : Dinesh Saini
 *      Created on   : 03/12/2013
 *      Description  : Action class for getting User index information.
 */
package com.webaccess.issuetracker.action;

import com.opensymphony.xwork2.ActionSupport;
import com.webaccess.issuetracker.bean.Users;
import com.webaccess.issuetracker.delegate.UserDelegate;
import com.webaccess.issuetracker.util.LoggingUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class UserIndexInfoAction retrieves user list from database currently active
 * in organization.
 *
 * @author Dinesh Saini
 */
public class UserIndexInfoAction extends ActionSupport {

    private static final String NUM_REGEX = "^[0-9]+$";
    private static final String NAME_REGEX = "^[a-zA-Z][a-zA-Z ]+$";
    private static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    private static final org.slf4j.Logger LOGGER = LoggingUtil.getLogger();
    private List<Users> userIndexInfoList;
    private String empName = "";
    private Users users;
    private int empId;
    private String userId;
    private Map<Integer, String> empStatusTypeList;
    private String empStatus;

    /**
     * @author Dinesh Saini
     */
    public UserIndexInfoAction() {
        this.empStatusTypeList = new HashMap<>();
        this.empStatusTypeList.put(0, "De-Active");
        this.empStatusTypeList.put(1, "Active");
    }

    /**
     * Retrieve employee records from database and also perform search operation
     * on users index.
     *
     * @return String status
     */
    public String getSearchUserInfo() {
        UserDelegate userDelegate;
        try {
            userDelegate = new UserDelegate();
            this.users = new Users();
            this.users.setEmpName(this.empName);
            this.userIndexInfoList = userDelegate.getUserInfoForIndex(this.users);
        } catch (Exception e) {
            LOGGER.error("UserIndexInfoAction", e);
        }
        return "success";
    }

    /**
     * @author Dinesh Saini
     * @return String status
     */
    public String showEditUser() {
        UserDelegate userDelegate = null;
        try {
            userDelegate = new UserDelegate();
            this.users = new Users();
            this.users.setUserId(this.userId);
            userDelegate.getUserDetails(this.users);
        } catch (Exception e) {
            LOGGER.error("UserIndexInfoAction", e);
        }
        return SUCCESS;
    }

    /**
     * @author Dinesh Saini
     * @purpose to validate manage user details
     */
    public void validator() {
        try {
            if (this.users.getFirstName().length() == 0) {
                addFieldError("users.firstName", "First Name Required");
            } else if (!this.users.getFirstName().matches(UserIndexInfoAction.NAME_REGEX)) {
                addFieldError("users.firstName", "Invalid First Name");
            }
            if (this.users.getLastName().length() == 0) {
                addFieldError("users.lastName", "Last Name Required");
            } else if (!this.users.getLastName().matches(UserIndexInfoAction.NAME_REGEX)) {
                addFieldError("users.lastName", "Invalid Last Name");
            }
            if (this.users.getEmail().length() == 0) {
                addFieldError("users.email", "Email Required");
            } else if (!this.users.getEmail().matches(UserIndexInfoAction.EMAIL_REGEX)) {
                addFieldError("users.email", "Check Email Format");
            }
            if (this.users.getPhone().length() == 0) {
                addFieldError("users.phone", "Mobile Num Required");
            } else {
                if (!this.users.getPhone().matches(UserIndexInfoAction.NUM_REGEX)) {
                    addFieldError("users.phone", "Check Number Format");
                } else if (this.users.getPhone().length() != 10) {
                    addFieldError("users.phone", "Mobile Number Length should be 10");
                }
            }
        } catch (Exception e) {
            LOGGER.error("UserIndexInfoAction", e);
        }
    }

    /**
     * @author Dinesh Saini
     * @return String status
     * @purpose perform manage users
     */
    public String editUserDetails() {
        UserDelegate userDelegate;
        this.validator();
        try {
            if (hasFieldErrors()) {
                return "error";
            } else {
                userDelegate = new UserDelegate();
                userDelegate.editUser(this.users);
                addActionMessage("Edit user success");
            }
        } catch (Exception e) {
            LOGGER.error("UserIndexInfoAction", e);
        }

        return SUCCESS;
    }

    /**
     * @author Dinesh Saini
     * @return Map empStatusTypeList
     */
    public Map getEmpStatusTypeList() {
        return this.empStatusTypeList;
    }

    /**
     * @author Dinesh Saini
     * @param empStatusTypeList the empStatusTypeList to set
     */
    public void setEmpStatusTypeList(Map empStatusTypeList) {
        this.empStatusTypeList = empStatusTypeList;
    }

    /**
     * @author Dinesh Saini
     * @return Users users
     */
    public Users getUsers() {
        return this.users;
    }

    /**
     * @author Dinesh Saini
     * @param users the users to set
     */
    public void setUsers(Users users) {
        this.users = users;
    }

    /**
     * @author Dinesh Saini
     * @return String empStatus
     */
    public String getEmpStatusType() {
        return this.empStatus;
    }

    /**
     * @author Dinesh Saini
     * @param empStatusType the empStatus to set
     */
    public void setEmpStatusType(String empStatusType) {
        this.empStatus = empStatusType;
    }

    /**
     * @author Dinesh Saini
     * @return String userId
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * @author Dinesh Saini
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @author Dinesh Saini
     * @return int empId
     */
    public int getEmpId() {
        return this.empId;
    }

    /**
     * @author Dinesh Saini
     * @param empId the empId to set
     */
    public void setEmpId(int empId) {
        this.empId = empId;
    }

    /**
     * @author Dinesh Saini
     * @return List userIndexInfoList
     */
    public List<Users> getUserIndexInfoList() {
        return this.userIndexInfoList;
    }

    /**
     * @author Dinesh Saini
     * @param userIndexInfoList the userIndexInfoList to set
     */
    public void setUserIndexInfoList(List<Users> userIndexInfoList) {
        this.userIndexInfoList = userIndexInfoList;
    }

    /**
     * @author Dinesh Saini
     * @return String empName
     */
    public String getEmpName() {
        return this.empName;
    }

    /**
     * @author Dinesh Saini
     * @param empName the empName to set
     */
    public void setEmpName(String empName) {
        this.empName = empName;
    }
}
