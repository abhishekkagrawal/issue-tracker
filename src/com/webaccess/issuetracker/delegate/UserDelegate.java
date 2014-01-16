/*
 *      Document     : UserDelegate.java
 *      Author       : Dinesh Saini
 *      Created on   : 27/11/2013
 *      Description  : Delegate class for Userdao.
 */
package com.webaccess.issuetracker.delegate;

import com.webaccess.issuetracker.bean.Users;
import com.webaccess.issuetracker.dao.UserDao;
import java.util.List;

/**
 *
 * @author Dinesh Saini
 */
public class UserDelegate {

    private final UserDao userDao;

    /**
     * @author Dinesh Saini
     * @ Class constructor to initialize properties
     */
    public UserDelegate() {
        this.userDao = new UserDao();
    }

    /**
     * @author Dinesh Saini
     * @param empId
     * @return List
     */
    public List<Users> getRolesInProjectsList(int empId) {
        return this.userDao.getRolesInProjectsList(empId);
    }

    /**
     * @author Dinesh Saini
     * @param projectId
     * @return List
     */
    public List<Users> newAssignMemberList(int projectId) {
        return this.userDao.newAssignMemberList(projectId);
    }

    /**
     * @author Dinesh Saini
     * @param users
     * @return List
     */
    public List<Users> getUserInfoForIndex(Users users) {
        return this.userDao.getUserInfoForIndex(users);
    }

    /**
     * @author Dinesh Saini
     * @param users
     * @return boolean
     */
    public boolean updateUsers(Users users) {
        return this.userDao.updateUsers(users);
    }

    /**
     * @author Dinesh Saini
     * @param users
     * @return Users
     */
    public Users getUserDetails(Users users) {
        return this.userDao.getUserDetails(users);
    }

    /**
     * @author Dinesh Saini
     * @param user
     * @return boolean
     */
    public boolean addUsers(Users user) {
        return this.userDao.addUsers(user);
    }

    /**
     * @author Dinesh Saini
     * @param users
     * @return boolean
     */
    public boolean validateEmail(Users users) {
        return this.userDao.validateEmail(users);
    }

    /**
     * @author Dinesh Saini
     * @param user
     * @return boolean
     */
    public boolean validateLoginId(Users user) {
        return this.userDao.validateLoginId(user);
    }

    /**
     * @author Dinesh Saini
     * @return List
     */
    public List<String> getEmpTypeList() {
        return this.userDao.getEmpTypeList();
    }

    /**
     * @author Dinesh Saini
     * @return List
     */
    public List<String> getProjMemberType() {
        return this.userDao.getProjMemberType();
    }

    /**
     * @author Dinesh Saini
     * @param users
     * @return boolean
     */
    public boolean editUser(Users users) {
        return this.userDao.editUser(users);
    }
}
