/*
 *      Document     : LogOutAction.java
 *      Author       : Dinesh Saini
 *      Created on   : 26/11/2013
 *      Description  : Logout action Class for users logout
 */
package com.webaccess.issuetracker.action;

import com.opensymphony.xwork2.ActionSupport;
import com.webaccess.issuetracker.util.LoggingUtil;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 * Class LogOutAction invalidates user's session and remove users data from
 * database.
 *
 * @author Dinesh Saini
 */
public class LogOutAction extends ActionSupport {

    private static final org.slf4j.Logger LOGGER = LoggingUtil.getLogger();

    /**
     * Invalidates session or logout user.
     *
     * @return String
     */
    @Override
    public String execute() {
        HttpSession session;
        try {
            session = ServletActionContext.getRequest().getSession(false);
            if (session == null || session.getAttribute("login") == null) {
                addActionError("You are already log out");
                return "login";
            }
            session.removeAttribute("login");
            session.removeAttribute("empType");
            session.invalidate();
        } catch (Exception e) {
            LOGGER.debug("LogOutAction.run", e);
        }
        return SUCCESS;
    }
}
