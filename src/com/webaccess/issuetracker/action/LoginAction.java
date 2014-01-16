/*
 *      Document     : LoginAction.java
 *      Author       : Dinesh Saini
 *      Created on   : 25/11/2013
 *      Description  : Action class performs user login operation in web
 *                     application.
 */
package com.webaccess.issuetracker.action;

import com.opensymphony.xwork2.ActionSupport;
import com.webaccess.issuetracker.bean.Users;
import com.webaccess.issuetracker.delegate.LoginDelegate;
import com.webaccess.issuetracker.util.LoggingUtil;
import com.webaccess.issuetracker.util.MD5Encryptor;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author Dinesh Saini
 */
public class LoginAction extends ActionSupport implements SessionAware {

    private static final org.slf4j.Logger LOGGER = LoggingUtil.getLogger();
    private String userId;
    private String password;
    private Map sessionMap;
    private String empType;

    /**
     * Redirect user to login page.
     *
     * @return String
     */
    @Override
    public String execute() {
        return SUCCESS;
    }

    /**
     * Validates login credential.
     */
    @Override
    public void validate() {
        Users userBean;
        LoginDelegate loginDelegate;
        try {
            String encrptPwd = MD5Encryptor.md5(this.password);
            userBean = new Users();
            userBean.setUserId(getUserId());
            userBean.setPassword(encrptPwd);
            if (getUserId() != null && getPassword() != null) {
                if (getUserId().length() == 0) {
                    addFieldError("userId", "User Name is required");
                }
                if (getPassword().length() == 0) {
                    addFieldError("password", "password required");
                }
                if (getUserId().length() != 0 && getPassword().length() != 0) {
                    loginDelegate = new LoginDelegate();
                    loginDelegate.checkCredential(userBean);
                    if (userBean.isFlag()) {
                        loginDelegate.getEmpType(userBean);
                        this.empType = userBean.getEmpType();
                        this.sessionMap.put("login", "true");
                        this.sessionMap.put("empType", this.empType);
                        this.sessionMap.put("userName", userBean.getUserId());
                        this.sessionMap.put("empTypeId", userBean.getEmpTypId());
                        this.sessionMap.put("empId", userBean.getEmpId());
                    } else {
                        addActionError("Invalid User or Password!");
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("LoginAction.run", e);
        }
    }

    /**
     * @return String empType
     */
    public String getEmpType() {
        return this.empType;
    }

    /**
     * @param empType
     * @purpose sets property empType
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
     * @param userId
     * @purpose sets property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     * @param map the sessionMap to set
     */
    @Override
    public void setSession(Map<String, Object> map) {
        this.sessionMap = map;
    }
}
