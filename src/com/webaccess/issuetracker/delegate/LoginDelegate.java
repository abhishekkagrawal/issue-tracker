/*
 *      Document     : LoginDelegate.java
 *      Author       : Dinesh Saini
 *      Created on   : 25/11/2013
 *      Description  : Delegate class for Login DAO.
 */
package com.webaccess.issuetracker.delegate;

import com.webaccess.issuetracker.bean.Users;
import com.webaccess.issuetracker.dao.LoginDao;

/**
 * @author Dinesh Saini
 */
public class LoginDelegate {

    private final LoginDao loginDao;

    /**
     * @author Dinesh Saini
     * @ Class constructor
     */
    public LoginDelegate() {
        this.loginDao = new LoginDao();
    }

    /**
     * @author Dinesh Saini
     * @param userBean
     * @return Users
     */
    public Users checkCredential(Users userBean) {
        return this.loginDao.checkCredential(userBean);
    }

    /**
     * @author Dinesh Saini
     * @param users
     * @return Users
     */
    public Users getEmpType(Users users) {
        return this.loginDao.getEmpType(users);
    }

}
