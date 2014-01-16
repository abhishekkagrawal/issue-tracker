/*
 *      Document     : SessionValidator.java
 *      Author       : Dinesh Saini
 *      Created on   : 13/01/2014
 *      Description  : Utility class for session validation
 */
package com.webaccess.issuetracker.util;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;

/**
 * Class SessionValidator checks if user session is available or not.
 *
 * @author Dinesh Saini
 */
public final class SessionValidator {

    private static final Logger LOGGER = LoggingUtil.getLogger();

    /**
     * Function performs the validation operation that check is session is
     * available or not.
     *
     * @return true if session is available or else
     */
    public static boolean validateSession() {
        boolean flag = true;
        try {
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session == null || session.getAttribute("login") == null
                    || session.getAttribute("empId") == null) {
                flag = false;
            }
        } catch (Exception e) {
            LOGGER.error("SessionValidator.run", e);
        }
        return flag;
    }

    /**
     * Function returns projectId from session
     *
     * @return int projectId
     */
    public static int getSessionProjectId() {
        int projectId = 0;
        try {
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute("projId") != null) {
                projectId = Integer.parseInt(session.getAttribute("projId").toString());
            }
        } catch (NumberFormatException e) {
            LOGGER.error("SessionValidator.run", e);
        }

        return projectId;
    }

    /**
     * Gets employee id from session.
     *
     * @return in employeeId
     */
    public static int getEmployeeId() {
        int employeeId = 0;
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            if (session.getAttribute("empId") != null) {
                employeeId = Integer.parseInt(session.getAttribute("empId").toString());
            }
        } catch (NumberFormatException e) {
            LOGGER.error("SessionValidator.run", e);
        }

        return employeeId;
    }

}
