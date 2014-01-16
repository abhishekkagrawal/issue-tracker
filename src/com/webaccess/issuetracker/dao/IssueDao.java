/*
 *      Document     : IssueDao.java
 *      Author       : Dinesh Saini
 *      Created on   : 16/12/2013
 *      Description  : DAO class to perform operations regarding issues.
 */
package com.webaccess.issuetracker.dao;

import com.webaccess.issuetracker.bean.IssuesBean;
import com.webaccess.issuetracker.util.DateConvertor;
import com.webaccess.issuetracker.util.DbConnection;
import com.webaccess.issuetracker.util.LoggingUtil;
import com.webaccess.issuetracker.util.StringToListConvertor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Class IssueDao performs database operations related to issues.
 *
 * @author Dinesh Saini
 */
public class IssueDao {

    private static final org.slf4j.Logger LOGGER = LoggingUtil.getLogger();
    private final DbConnection dbConnection;

    /**
     * Class constructor.
     */
    public IssueDao() {
        this.dbConnection = new DbConnection();
    }

    /**
     * Retrieve issue documents list from database.
     *
     * @param issueId
     * @return List docList
     */
    public List<IssuesBean> getIssueDocs(int issueId) {
        List<IssuesBean> docList = null;
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        IssuesBean issuesBean;
        try {
            docList = new ArrayList<>();
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getIssueFiles(?)}");
            callableStatement.setInt(1, issueId);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                issuesBean = new IssuesBean();
                issuesBean.setFileId(resultSet.getInt("file_id"));
                issuesBean.setFileName(resultSet.getString("file_name"));
                issuesBean.setFileLocation(resultSet.getString("location_path"));
                issuesBean.setInsertionDate(DateConvertor.timeStampTo(
                        resultSet.getTimestamp("insertion_time")));
                issuesBean.setLoginId(resultSet.getString("user_name"));
                issuesBean.setEmpName(resultSet.getString("empName"));
                docList.add(issuesBean);
            }
        } catch (SQLException ex) {
            LOGGER.error("IssueDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return docList;
    }

    /**
     * Retrieve issue overview details from database.
     *
     * @param issueId
     * @return IssuesBean issuesBean
     */
    public IssuesBean getIssueOverview(int issueId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        IssuesBean issuesBean = null;
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getIssueOverview(?)}");
            callableStatement.setInt(1, issueId);
            resultSet = callableStatement.executeQuery();
            issuesBean = new IssuesBean();
            while (resultSet.next()) {
                issuesBean.setIssueId(resultSet.getInt("issue_id"));
                issuesBean.setTrackerType(resultSet.getString("tracker_type"));
                issuesBean.setIssueIdentifier(resultSet.getString("issue"));
                issuesBean.setIssueSubject(resultSet.getString("subject"));
                issuesBean.setIssueDesc(resultSet.getString("description"));
                issuesBean.setAssigneeId(resultSet.getInt("assignee_id"));
                issuesBean.setAssignee(resultSet.getString("assignee"));
                issuesBean.setAssignerId(resultSet.getInt("assigner_id"));
                issuesBean.setAssigner(resultSet.getString("reporter"));
                issuesBean.setPriorityType(resultSet.getString("priority_type"));
                issuesBean.setStatusType(resultSet.getString("status_type"));
                issuesBean.setProjectId(resultSet.getInt("project_id"));
                issuesBean.setProjectIdentifier(resultSet.getString("identifier"));
                issuesBean.setIssueStartDate(resultSet.getString("start_date"));
                issuesBean.setIssueDueDate(resultSet.getString("due_date"));
                issuesBean.setIssueEndDate(resultSet.getString("end_date"));
                issuesBean.setIssueUpdateDate(DateConvertor.timeStampTo(
                        resultSet.getTimestamp("insertion_time")));
                issuesBean.setAsigneeLoginId(resultSet.getString("assigneeLoginId"));
                issuesBean.setAssignerLoginId(resultSet.getString("assignerLoginId"));
                issuesBean.setEstimatedHour(resultSet.getInt("estimated_hour"));
            }
        } catch (SQLException ex) {
            LOGGER.error("IssueDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return issuesBean;
    }

    /**
     * Function executes stored procedure which perform update operation.
     *
     * @param conn
     * @param sql
     * @param parameters
     * @return true if update operation success or false
     * @throws SQLException
     */
    public boolean executeUpdateSP(Connection conn, String sql,
            Object... parameters) throws SQLException {
        boolean flag = false;
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
            stmt.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            LOGGER.error("IssueDao.run", e);
        }
        return flag;
    }

    /**
     * Performs add issue operation in database reported by employee.
     *
     * @param issuesBean1
     * @return boolean
     */
    public boolean addNewIssue(IssuesBean issuesBean1) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        boolean flag = false;
        try {
            String sql = "{CALL addNewIssue(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            connection = this.dbConnection.getConnection();
            Object parameters[] = new Object[]{
                issuesBean1.getTrackerType(), issuesBean1.getIssueSubject(),
                issuesBean1.getIssueDesc(), issuesBean1.getPriorityType(),
                issuesBean1.getProjectId(), issuesBean1.getAssigneeId(),
                issuesBean1.getIssueStartDate(), issuesBean1.getIssueDueDate(),
                issuesBean1.getEstimatedHour(), issuesBean1.getStatusType(),
                issuesBean1.getFileName(), issuesBean1.getFileLocation(),
                issuesBean1.getLoginId()
            };
            if (this.executeUpdateSP(connection, sql, parameters)) {
                flag = true;
            }
        } catch (SQLException ex) {
            LOGGER.error("IssueDao.run", ex);
        } finally {
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return flag;
    }

    /**
     * Updates issue details like issue assignee, due date, issue subject and
     * description.
     *
     * @param issuesBean1
     * @return boolean
     */
    public boolean updateIssue(IssuesBean issuesBean1) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        boolean flag = false;
        try {
            String sql = "{CALL updateIssueDetails(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            connection = this.dbConnection.getConnection();
            Object parameters[] = new Object[]{
                issuesBean1.getTrackerType(), issuesBean1.getIssueSubject(),
                issuesBean1.getIssueDesc(), issuesBean1.getPriorityType(),
                issuesBean1.getIssueId(), issuesBean1.getAssigneeId(),
                issuesBean1.getIssueStartDate(), issuesBean1.getIssueDueDate(),
                issuesBean1.getEstimatedHour(), issuesBean1.getStatusType(),
                issuesBean1.getFileName(), issuesBean1.getFileLocation(),
                issuesBean1.getLoginId()
            };
            if (this.executeUpdateSP(connection, sql, parameters)) {
                flag = true;
            }
        } catch (SQLException ex) {
            LOGGER.error("IssueDao.run", ex);
        } finally {
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return flag;
    }

    /**
     * Retrieve issue properties from database.
     *
     * @param issuesBean1
     * @return IssueBean issuesBean1
     */
    public IssuesBean getFormData(IssuesBean issuesBean1) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dbConnection.getConnection();
            String projectTrackers = "";
            List statusTypeList = new ArrayList();
            List priorityTypeList = new ArrayList();
            callableStatement = connection.prepareCall("{CALL getProjectTrackersList(?)}");
            callableStatement.setInt(1, issuesBean1.getProjectId());
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                projectTrackers = resultSet.getString("tracker_list");
            }
            callableStatement = connection.prepareCall("{CALL getStatusDetails()}");
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                statusTypeList.add(resultSet.getString("status_type"));
            }
            callableStatement = connection.prepareCall("{CALL getPriorityTypeList()}");
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                priorityTypeList.add(resultSet.getString("priority_type"));
            }
            issuesBean1.setStatusTypeList(statusTypeList);
            issuesBean1.setPrjctTrckrList(StringToListConvertor.stringToListConvertor(projectTrackers));
            issuesBean1.setPriorityTypeList(priorityTypeList);
        } catch (SQLException ex) {
            LOGGER.error("IssueDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return issuesBean1;
    }

    /**
     * Retrieves all issue from database.
     *
     * @param projectId
     * @return List issueRecords
     */
    public List<IssuesBean> getIssueRecords(int projectId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        List<IssuesBean> issueRecords = new ArrayList<>();
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getIssueRecords(?)}");
            callableStatement.setInt(1, projectId);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                IssuesBean issuesBean = new IssuesBean();
                issuesBean.setIssueId(resultSet.getInt("issue_id"));
                issuesBean.setTrackerId(resultSet.getInt("tracker_id"));
                issuesBean.setIssueSubject(resultSet.getString("subject"));
                issuesBean.setProjectId(resultSet.getInt("priority_id"));
                issuesBean.setPriorityType(resultSet.getString("priority_type"));
                issuesBean.setStatusId(resultSet.getInt("status_id"));
                issuesBean.setStatusType(resultSet.getString("status_type"));
                issuesBean.setProjectId(resultSet.getInt("project_id"));
                Timestamp updateDate = resultSet.getTimestamp("last_modified_time");
                if (updateDate == null) {
                    updateDate = resultSet.getTimestamp("insertion_time");
                }
                issuesBean.setIssueUpdateDate(DateConvertor.timeStampTo(updateDate));
                issuesBean.setTrackerType(resultSet.getString("tracker_type"));
                issuesBean.setAssigneeId(resultSet.getInt("assignee_id"));
                issuesBean.setLoginId(resultSet.getString("user_name"));
                issuesBean.setEmpName(resultSet.getString("emp_name"));
                issueRecords.add(issuesBean);
            }
        } catch (SQLException ex) {
            LOGGER.error("IssueDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return issueRecords;
    }

    /**
     * Retrieves issues assigned to particular user.
     *
     * @param empId
     * @return List issueRecords
     */
    public List<IssuesBean> getUserIssues(int empId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        List<IssuesBean> issueRecords = new ArrayList<>();
        try {
            Object parameters[] = new Object[]{empId};
            String sql = "{CALL getUserIssues(?)}";
            connection = this.dbConnection.getConnection();
            resultSet = this.dbConnection.executeStoredProcedure(
                    connection, sql, parameters);
            issueRecords = this.getIssueBeanList(resultSet);
        } catch (SQLException ex) {
            LOGGER.error("IssueDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return issueRecords;
    }

    /**
     * Retrieve list of issue reported by users.
     *
     * @param empId
     * @return List issueRecords
     */
    public List<IssuesBean> getUserIssuesReported(int empId) {
        CallableStatement callableStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        List<IssuesBean> issueRecords = new ArrayList<>();
        try {
            connection = this.dbConnection.getConnection();
            String sql = "{CALL getUserIssueReported(?)}";
            Object parameters[] = new Object[]{empId};
            resultSet = this.dbConnection.executeStoredProcedure(connection, sql, parameters);
            issueRecords = this.getIssueBeanList(resultSet);
        } catch (SQLException ex) {
            LOGGER.error("IssueDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return issueRecords;
    }

    /**
     * Create list of bean object having values from resultSet parameter.
     *
     * @param resultSet
     * @return List issueRecords
     */
    public List<IssuesBean> getIssueBeanList(ResultSet resultSet) {
        List<IssuesBean> issueRecords = null;
        try {
            issueRecords = new ArrayList<>();
            while (resultSet.next()) {
                IssuesBean issuesBean = new IssuesBean();
                issuesBean.setIssueId(resultSet.getInt("issue_id"));
                issuesBean.setIssueName(resultSet.getString("issue_name"));
                issuesBean.setTrackerId(resultSet.getInt("tracker_id"));
                issuesBean.setIssueSubject(resultSet.getString("subject"));
                issuesBean.setStatusId(resultSet.getInt("status_id"));
                issuesBean.setStatusType(resultSet.getString("status_type"));
                issuesBean.setProjectIdentifier(resultSet.getString("identifier"));
                issuesBean.setProjectName(resultSet.getString("project_name"));
                issuesBean.setInsertionDate(DateConvertor.
                        timeStampTo(resultSet.getTimestamp("insertion_time")));
                issuesBean.setTrackerType(resultSet.getString("tracker_type"));
                issuesBean.setIssueDesc(resultSet.getString("description"));
                issueRecords.add(issuesBean);
            }
        } catch (SQLException e) {
            LOGGER.error("IssueDao.run", e);
        }
        return issueRecords;
    }
}
