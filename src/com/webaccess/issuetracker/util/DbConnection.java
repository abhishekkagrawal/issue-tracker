/*
 *      Document     : DbConnection.java
 *      Author       : Dinesh Saini
 *      Created on   : 25/11/2013
 *      Description  : Class estalishes database connection and provide
 *                     connection to DAO class.
 */
package com.webaccess.issuetracker.util;

import com.opensymphony.xwork2.ActionSupport;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class DbConnection establishes .
 *
 * @author Dinesh Saini
 */
public class DbConnection extends ActionSupport {

    private static final org.slf4j.Logger LOGGER = LoggingUtil.getLogger();

    /**
     * Loads the JDBC Driver class and establishes connection to database.
     *
     * @return Connection
     */
    public Connection getConnection() {
        Connection myConnection = null;
        try {
            Class.forName(getText("driver"));
            if (myConnection == null || myConnection.isClosed()) {
                myConnection = (Connection) DriverManager.getConnection(getText("dburl"), getText("dbusername"), getText("dbpassword"));
            }
        } catch (ClassNotFoundException e) {
            LOGGER.error("DbConnection.run", e);
        } catch (SQLException sqle) {
            LOGGER.error("DBUtil.run", sqle);
        }
        return myConnection;
    }

    /**
     * Releases the ResultSet object's database.
     *
     * @param resultSet
     */
    public void freeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException ex) {
            LOGGER.error("DbConnection.run", ex);
        }
    }

    /**
     * Releases statement object's database.
     *
     * @param callableStatement
     */
    public void freeCallableStatement(CallableStatement callableStatement) {
        try {
            if (callableStatement != null) {
                callableStatement.close();
            }
        } catch (SQLException ex) {
            LOGGER.error("DbConnection.run", ex);
        }
    }

    /**
     * Releases statement object's database.
     *
     * @param statement
     */
    public void freePreparedStatement(PreparedStatement statement) {
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException ex) {
            LOGGER.error("DbConnection.run", ex);
        }
    }

    /**
     * Releases connection object's database.
     *
     * @param connection
     */
    public void freeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            LOGGER.error("DbConnection.run", ex);
        }
    }

    /**
     * Returns result set after executing stored procedure.
     *
     * @param conn
     * @param sql
     * @param parameters
     * @return ResultSet
     * @throws SQLException
     */
    public ResultSet executeStoredProcedure(Connection conn, String sql,
            Object... parameters) throws SQLException {
        ResultSet resultSet = null;
        CallableStatement stmt;
        try {
            stmt = conn.prepareCall(sql);
            int ind = 0;
            if (parameters != null) {
                for (Object currPar : parameters) {
                    ind++;
                    stmt.setObject(ind, currPar);
                }
            }
            resultSet = stmt.executeQuery();
        } catch (SQLException e) {
            LOGGER.error("DbConnection.run", e);
        }
        return resultSet;
    }
}
