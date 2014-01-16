/*
 *      Document     : SearchDao.java
 *      Author       : Dinesh Saini
 *      Created on   : 23/12/2013
 *      Description  : DAO class performs search operation in data stored in
 *                     database.
 */
package com.webaccess.issuetracker.dao;

import com.webaccess.issuetracker.bean.IssuesBean;
import com.webaccess.issuetracker.bean.ProjectBean;
import com.webaccess.issuetracker.bean.SearchBean;
import com.webaccess.issuetracker.util.DateConvertor;
import com.webaccess.issuetracker.util.DbConnection;
import com.webaccess.issuetracker.util.LoggingUtil;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class SearchDao performs search operation in data stored in database for
 * application.
 *
 * @author Dinesh Saini
 */
public class SearchDao {

    private static final org.slf4j.Logger LOGGER = LoggingUtil.getLogger();
    private final DbConnection dbConnection;

    /**
     * Class constructor.
     */
    public SearchDao() {
        this.dbConnection = new DbConnection();
    }

    /**
     * Retrieves project searched.
     *
     * @param searchBean
     * @return SearchBean searchBean
     */
    public SearchBean getSearchedProject(SearchBean searchBean) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dbConnection.getConnection();
            List<ProjectBean> projectDetails = new ArrayList<>();
            callableStatement = connection.prepareCall("{CALL `getSearchedProjects`(?,?,?)}");
            callableStatement.setString(1, searchBean.getSearchKey());
            callableStatement.setInt(2, searchBean.getEmpTypeId());
            callableStatement.setInt(3, searchBean.getEmpId());
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ProjectBean projectBean = new ProjectBean();
                projectBean.setProjectId(resultSet.getInt("project_id"));
                projectBean.setProjectName(resultSet.getString("project_name"));
                projectBean.setDescription(resultSet.getString("description"));
                projectBean.setIdentifier(resultSet.getString("identifier"));
                projectBean.setPrjctInsrtDate(DateConvertor.
                        timeStampTo(resultSet.getTimestamp("insertion_time")));
                projectDetails.add(projectBean);
            }
            searchBean.setPrjctSrchedList(projectDetails);
        } catch (SQLException ex) {
            LOGGER.error("SearchDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return searchBean;
    }

    /**
     * Retrieves searched issue.
     *
     * @param searchBean
     * @return SearchBean searchBean
     */
    public SearchBean getSearchedIssue(SearchBean searchBean) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dbConnection.getConnection();
            List<IssuesBean> issueDetails = new ArrayList<>();
            callableStatement = connection.prepareCall("{CALL getSearchedIssues(?,?,?)}");
            callableStatement.setString(1, searchBean.getSearchKey());
            callableStatement.setInt(2, searchBean.getEmpTypeId());
            callableStatement.setInt(3, searchBean.getEmpId());
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                IssuesBean issuesBean = new IssuesBean();
                issuesBean.setIssueDesc(resultSet.getString("description"));
                issuesBean.setIssueId(resultSet.getInt("issue_id"));
                issuesBean.setInsertionDate(DateConvertor.
                        timeStampTo(resultSet.getTimestamp("insertion_time")));
                issuesBean.setProjectId(resultSet.getInt("project_id"));
                issuesBean.setIssueName(resultSet.getString("issue_name"));
                issuesBean.setProjectName(resultSet.getString("project_name"));
                issueDetails.add(issuesBean);
            }
            searchBean.setIssueSearchedList(issueDetails);
        } catch (SQLException ex) {
            LOGGER.error("SearchDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return searchBean;
    }

}
