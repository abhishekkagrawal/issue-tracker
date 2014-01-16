/*
 *      Document     : LoginDao.java
 *      Author       : Dinesh Saini
 *      Created on   : 25/11/2013
 *      Description  : Class performs operation for login.
 */
package com.webaccess.issuetracker.dao;

import com.webaccess.issuetracker.bean.Users;
import com.webaccess.issuetracker.util.DbConnection;
import com.webaccess.issuetracker.util.LoggingUtil;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class performs operation for login user and validating their userId password.
 *
 * @author Dinesh Saini
 */
public class LoginDao {

    private static final org.slf4j.Logger LOGGER = LoggingUtil.getLogger();
    private final DbConnection dbConnection;

    /**
     * Class constructor.
     */
    public LoginDao() {
        this.dbConnection = new DbConnection();
    }

    /**
     * Check login credentials.
     *
     * @param userBean
     * @return Users userBean
     */
    public Users checkCredential(Users userBean) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dbConnection.getConnection();
            boolean flag = true;
            callableStatement = connection.prepareCall("{CALL getUserDetails()}");
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                String userId = resultSet.getString("user_name");
                String password = resultSet.getString("password");
                int empStatus = resultSet.getInt("isActive");
                if (userBean.getUserId().equals(userId) && userBean.getPassword().equals(password) && empStatus != 0) {
                    userBean.setEmpId(resultSet.getInt("emp_id"));
                    userBean.setFirstName(resultSet.getString("first_name"));
                    userBean.setLastName(resultSet.getString("last_name"));
                    userBean.setEmpTypId(resultSet.getInt("emp_type_id"));
                    userBean.setPhone(resultSet.getString("phone"));
                    userBean.setEmail(resultSet.getString("email"));
                    userBean.setRegDate(resultSet.getTimestamp("insertion_time"));
                    userBean.setFlag(flag);
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("LoginDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return userBean;
    }

    /**
     * Retrieves employee type from database.
     *
     * @param users
     * @return Users users
     */
    public Users getEmpType(Users users) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getEmpType()}");
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                if (users.getEmpTypId() == resultSet.getInt("emp_type_id")) {
                    users.setEmpType(resultSet.getString("emp_type"));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("LoginDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return users;
    }
}
