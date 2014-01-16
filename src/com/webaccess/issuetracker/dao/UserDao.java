/*
 *      Document     : UserDao.java
 *      Author       : Dinesh Saini
 *      Created on   : 27/11/2013
 *      Description  : DAO class to perform operations related to employees.
 */
package com.webaccess.issuetracker.dao;

import com.webaccess.issuetracker.bean.Users;
import com.webaccess.issuetracker.util.DbConnection;
import com.webaccess.issuetracker.util.LoggingUtil;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Class UserDao performs operations related to employee like add new user,
 * removing users etc.
 *
 * @author Dinesh Saini
 */
public class UserDao {

    private static final org.slf4j.Logger LOGGER = LoggingUtil.getLogger();
    private final DbConnection dbConnection;

    /**
     * Class constructor.
     */
    public UserDao() {
        this.dbConnection = new DbConnection();
    }

    /**
     * Retrieves users member type in project.
     *
     * @param empId
     * @return List list
     */
    public List<Users> getRolesInProjectsList(int empId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        Users users = null;
        List<Users> list = new ArrayList<>();
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getRolesInProjects(?)}");
            callableStatement.setInt(1, empId);
            resultSet = callableStatement.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd", Locale.ENGLISH);
            while (resultSet.next()) {
                users = new Users();
                users.setEmpId(resultSet.getInt("emp_id"));
                users.setProjectName(resultSet.getString("project_name"));
                users.setPrjMmbrshipDate(sdf.format(Timestamp.valueOf(resultSet.getString("insertion_time"))));
                users.setIdentifier(resultSet.getString("identifier"));
                users.setEmpRoles((resultSet.getString("empRoles")).replace(",", ", "));
                list.add(users);
            }
        } catch (SQLException ex) {
            LOGGER.error("UserDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return list;
    }

    /**
     * Retrieves users list to add in project.
     *
     * @param projectId
     * @return List memberList
     */
    public List<Users> newAssignMemberList(int projectId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        Users users;
        List<Users> memberList = new ArrayList<>();
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getNewAssignMember(?)}");
            callableStatement.setInt(1, projectId);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                users = new Users();
                users.setEmpName(resultSet.getString("empName"));
                users.setEmpId(resultSet.getInt("emp_id"));
                memberList.add(users);
            }
        } catch (SQLException ex) {
            LOGGER.error("UserDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return memberList;
    }

    /**
     * Searches users in database.
     *
     * @param users1
     * @return List list
     */
    public List<Users> getUserInfoForIndex(Users users1) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        Users users;
        List<Users> list = new ArrayList<>();
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getUserDetails()}");
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                String empName = resultSet.getString("first_name") + " "
                        + resultSet.getString("last_name");
                if (empName.toLowerCase(Locale.ENGLISH).
                        contains(users1.getEmpName().toLowerCase(Locale.ENGLISH))) {
                    users = new Users();
                    users.setEmpName(empName);
                    users.setEmpId(resultSet.getInt("emp_id"));
                    users.setUserId(resultSet.getString("user_name"));
                    users.setEmail(resultSet.getString("email"));
                    users.setPhone(resultSet.getString("phone"));
                    list.add(users);
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("UserDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return list;
    }

    /**
     * Updates user's information.
     *
     * @param user
     * @return boolean
     */
    public boolean updateUsers(Users user) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        boolean flag = false;
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL updateUserDetails(?,?,?,?,?)}");
            callableStatement.setInt(1, user.getEmpId());
            callableStatement.setString(2, user.getFirstName());
            callableStatement.setString(3, user.getLastName());
            callableStatement.setString(4, user.getEmail());
            callableStatement.setString(5, user.getPhone());
            int rowCount = callableStatement.executeUpdate();
            if (rowCount > 0) {
                flag = true;
            }
        } catch (SQLException ex) {
            LOGGER.error("UserDao.run", ex);
        } finally {
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return flag;
    }

    /**
     * Retrieves user details.
     *
     * @param users
     * @return Users users
     */
    public Users getUserDetails(Users users) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getUserInfo(?)}");
            callableStatement.setString(1, users.getUserId());
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                users.setFirstName(resultSet.getString("first_name"));
                users.setLastName(resultSet.getString("last_name"));
                users.setEmpName(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                users.setPhone(resultSet.getString("phone"));
                users.setEmail(resultSet.getString("email"));
                users.setEmpType(resultSet.getString("emp_type"));
                users.setEmpId(resultSet.getInt("emp_id"));
                users.setRegDate(resultSet.getTimestamp("insertion_time"));
                users.setEmpStatus(resultSet.getInt("isActive"));
            }
        } catch (SQLException ex) {
            LOGGER.error("UserDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return users;
    }

    /**
     * Validates email for duplicate entry.
     *
     * @param users
     * @return boolean
     */
    public boolean validateEmail(Users users) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getUserDetails()}");
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("email").equals(users.getEmail()) & resultSet.getInt("emp_id") != users.getEmpId()) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("UserDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return false;
    }

    /**
     * Validates login id for duplicate entry.
     *
     * @param user
     * @return boolean
     */
    public boolean validateLoginId(Users user) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getUserDetails()}");
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("user_name").equals(user.getUserId())) {
                    return true;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("UserDao.run", e);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return false;
    }

    /**
     * Retrieves employee type list from database.
     *
     * @return List getEmpTypeList
     */
    public List<String> getEmpTypeList() {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        List<String> getEmpTypeList = null;
        try {
            getEmpTypeList = new ArrayList<>();
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getEmpType()}");
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                getEmpTypeList.add(resultSet.getString("emp_type"));
            }
        } catch (SQLException ex) {
            LOGGER.error("UserDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return getEmpTypeList;
    }

    /**
     * Performs add user operation.
     *
     * @param user
     * @return boolean
     */
    public boolean addUsers(Users user) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        boolean flag = false;
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL addUser(?,?,?,?,?,?,?)}");
            callableStatement.setString(1, user.getFirstName());
            callableStatement.setString(2, user.getLastName());
            callableStatement.setString(3, user.getEmpType());
            callableStatement.setString(4, user.getUserId());
            callableStatement.setString(5, user.getPassword());
            callableStatement.setString(6, user.getPhone());
            callableStatement.setString(7, user.getEmail());
            callableStatement.executeUpdate();
            flag = true;
        } catch (SQLException ex) {
            LOGGER.error("UserDao.run", ex);
        } finally {
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return flag;
    }

    /**
     * Retrieves type of role of user in project.
     *
     * @return List list
     */
    public List<String> getProjMemberType() {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        List<String> list = new ArrayList<>();
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getProjMemberTypeList()}");
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("emp_type"));
            }
        } catch (SQLException e) {
            LOGGER.error("UserDao.run", e);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return list;
    }

    /**
     * Manages user details.
     *
     * @param users
     * @return boolean
     */
    public boolean editUser(Users users) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        boolean flag = false;
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL editUser(?,?,?,?,?,?)}");
            callableStatement.setInt(1, users.getEmpId());
            callableStatement.setInt(2, users.getEmpStatus());
            callableStatement.setString(3, users.getFirstName());
            callableStatement.setString(4, users.getLastName());
            callableStatement.setString(5, users.getEmail());
            callableStatement.setString(6, users.getPhone());
            int rowCount = callableStatement.executeUpdate();
            if (rowCount > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            LOGGER.error("UserDao.run", e);
        } finally {
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return flag;
    }

}
