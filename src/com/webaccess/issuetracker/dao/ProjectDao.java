/*
 *      Document     : ProjectDao.java
 *      Author       : Dinesh Saini
 *      Created on   : 29/11/2013
 *      Description  : DAO class to perform database operations related to
 *                     Project like adding new project, managing project member.
 */
package com.webaccess.issuetracker.dao;

import com.webaccess.issuetracker.bean.IssuesBean;
import com.webaccess.issuetracker.bean.ProjectBean;
import com.webaccess.issuetracker.bean.Users;
import com.webaccess.issuetracker.util.DateConvertor;
import com.webaccess.issuetracker.util.DbConnection;
import com.webaccess.issuetracker.util.LoggingUtil;
import com.webaccess.issuetracker.util.StringToListConvertor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Class performs database operations related to Project like adding new
 * project, managing project member etc.
 *
 * @author Dinesh Saini
 */
public class ProjectDao {

    private static final org.slf4j.Logger LOGGER = LoggingUtil.getLogger();
    private final DbConnection dbConnection;

    /**
     * Class constructor to initializes variables.
     */
    public ProjectDao() {
        this.dbConnection = new DbConnection();
    }

    /**
     * Retrieves module list for project from database.
     *
     * @param projId
     * @return List moduleList
     */
    public List<ProjectBean> getProjectModuleList(int projId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        List<ProjectBean> moduleList = null;
        try {
            moduleList = new ArrayList<>();
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getProjectModuleList(?)}");
            callableStatement.setInt(1, projId);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ProjectBean projectBean = new ProjectBean();
                projectBean.setModuleType(resultSet.getString("menu_name"));
                projectBean.setActionURL(resultSet.getString("action_url"));
                projectBean.setModuleId(resultSet.getInt("menu_id"));
                moduleList.add(projectBean);
            }
        } catch (SQLException e) {
            LOGGER.error("ProjectDao.run", e);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return moduleList;
    }

    /**
     * Updates employee role type in projects.
     *
     * @param projectBean1
     * @return boolean
     */
    public boolean updateEmpRoleType(ProjectBean projectBean1) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        CallableStatement callableStatement = null;
        boolean status = false;
        try {
            connection = this.dbConnection.getConnection();
            List roleList;
            roleList = projectBean1.getEmpRoleTypeList();
            String sql = "delete from project_member where project_id=? "
                    + "and emp_id=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, projectBean1.getProjectId());
            preparedStatement.setInt(2, projectBean1.getEmpId());
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount > 0) {
                String sql1 = "{CALL updateProjectMember(?,?,?)}";
                callableStatement = connection.prepareCall(sql1);
                for (int i = 0; i < roleList.size(); i = i + 1) {
                    callableStatement.setInt(1, projectBean1.getProjectId());
                    callableStatement.setInt(2, projectBean1.getEmpId());
                    callableStatement.setString(3, roleList.get(i).toString());
                    callableStatement.executeUpdate();
                }
            }
            status = true;
        } catch (SQLException e) {
            LOGGER.error("ProjectDao.run", e);
        } finally {
            this.dbConnection.freePreparedStatement(preparedStatement);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return status;
    }

    /**
     * Retrieves project members from database for particular project.
     *
     * @param projectId
     * @return List projMemberList
     */
    public List<Users> getProjectMembers(int projectId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        List<Users> projMemberList = new ArrayList<>();
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getIssueAsigneeList(?)}");
            callableStatement.setInt(1, projectId);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Users users = new Users();
                users.setEmpId(resultSet.getInt("emp_id"));
                users.setEmpName(resultSet.getString("empName"));
                users.setEmpType(resultSet.getString("emp_type"));
                users.setUserId(resultSet.getString("user_name"));
                projMemberList.add(users);
            }
        } catch (SQLException e) {
            LOGGER.error("ProjectDao.run", e);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return projMemberList;
    }

    /**
     * Retrieves employee list that are member of a project.
     *
     * @param projectBean
     * @return List assignedList
     */
    public List getAssignedMemberList(ProjectBean projectBean) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        List<Users> assignedList = new ArrayList<>();
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getAssignedMember(?)}");
            callableStatement.setInt(1, projectBean.getProjectId());
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Users users = new Users();
                users.setEmpId(resultSet.getInt("emp_id"));
                users.setEmpName(resultSet.getString("emp_name"));
                users.setEmpType(resultSet.getString("emp_type"));
                users.setEmpTypeTest(StringToListConvertor.stringToListConvertor(resultSet.getString("emp_type")));
                users.setEmail(resultSet.getString("email"));
                assignedList.add(users);
            }
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return assignedList;
    }

    /**
     * Validates project document title.
     *
     * @param projectBean1
     * @return boolean
     */
    public boolean validateProjectDocTitle(ProjectBean projectBean1) {
        Connection connection = null;
        boolean flag = true;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getProjectDocs()}");
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                if (projectBean1.getProjectDocTitle().equals(resultSet.getString("doc_name"))) {
                    flag = false;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return flag;
    }

    /**
     * Adds project member.
     *
     * @param projectBean
     * @return boolean
     */
    public boolean addProjMember(ProjectBean projectBean) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        try {
            connection = this.dbConnection.getConnection();
            List roleTypeList = projectBean.getEmpRoleTypeList();
            List selectedEmpList = projectBean.getSlctdPrjMmbrList();
            int projectId = projectBean.getProjectId();
            callableStatement = connection.prepareCall("{CALL addProjectMember(?,?,?)}");
            statement = connection.prepareStatement("SELECT * FROM project_member pm JOIN emp_type et ON et.emp_type_id=pm.emp_type_id WHERE pm.project_id=? AND pm.emp_id=? AND et.emp_type =?;");
            for (int i = 0; i < selectedEmpList.size(); i = i + 1) {
                for (int j = 0; j < roleTypeList.size(); j = j + 1) {
                    int empId = Integer.parseInt(selectedEmpList.get(i).toString());
                    String roleType = roleTypeList.get(j).toString();
                    statement.setInt(1, projectId);
                    statement.setInt(2, empId);
                    statement.setString(3, roleType);
                    resultSet = statement.executeQuery();
                    if (resultSet != null && !resultSet.next()) {
                        callableStatement.setInt(1, projectId);
                        callableStatement.setInt(2, empId);
                        callableStatement.setString(3, roleType);
                        int count = callableStatement.executeUpdate();
                        if (count == 0) {
                            flag = false;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freePreparedStatement(statement);
            this.dbConnection.freeConnection(connection);
        }
        return flag;
    }

    /**
     * Retrieves projectId from database.
     *
     * @param projectBean1
     * @return int projectId
     */
    public int getProjectId(ProjectBean projectBean1) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        int projectId = 0;
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getProjectDetails()}");
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("identifier").equals(projectBean1.getIdentifier())) {
                    projectId = resultSet.getInt("project_id");
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } catch (Exception e) {
            LOGGER.error("ProjectDao.run", e);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return projectId;
    }

    /**
     * Retrieves project member list from database.
     *
     * @param projectBean1
     * @return ProjectBean projectBean1
     */
    public ProjectBean getProjectMemberList(ProjectBean projectBean1) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dbConnection.getConnection();
            List<Users> managerList = new ArrayList<>();
            List<Users> developerList = new ArrayList<>();
            List<Users> testerList = new ArrayList<>();
            callableStatement = connection.prepareCall("{CALL getProjectMembers(?)}");
            callableStatement.setInt(1, projectBean1.getProjectId());
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Users users = new Users();
                Users users1 = new Users();
                Users users2 = new Users();
                String empType = resultSet.getString("emp_type");
                if ("Project Manager".equals(empType)) {
                    users.setEmpId(resultSet.getInt("emp_id"));
                    users.setEmpName(resultSet.getString("empName"));
                    users.setEmpType(resultSet.getString("emp_type"));
                    users.setUserId(resultSet.getString("user_name"));
                    managerList.add(users);
                }
                if ("Developer".equals(empType)) {
                    users1.setEmpId(resultSet.getInt("emp_id"));
                    users1.setEmpName(resultSet.getString("empName"));
                    users1.setEmpType(resultSet.getString("emp_type"));
                    users1.setUserId(resultSet.getString("user_name"));
                    developerList.add(users1);
                }
                if ("Tester".equals(empType)) {
                    users2.setEmpId(resultSet.getInt("emp_id"));
                    users2.setEmpName(resultSet.getString("empName"));
                    users2.setEmpType(resultSet.getString("emp_type"));
                    users2.setUserId(resultSet.getString("user_name"));
                    testerList.add(users2);
                }
                projectBean1.setManagerList(managerList);
                projectBean1.setDeveloperList(developerList);
                projectBean1.setTesterList(testerList);
            }
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return projectBean1;
    }

    /**
     * Retrieves project overview details.
     *
     * @param projectBean1
     * @return ProjectBean projectBean1
     */
    public ProjectBean getProjectOverview(ProjectBean projectBean1) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getProjectDetails()}");
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt("project_id") == projectBean1.getProjectId()) {
                    projectBean1.setDescription(resultSet.getString("description"));
                    projectBean1.setProjectName(resultSet.getString("project_name"));
                    projectBean1.setIdentifier(resultSet.getString("identifier"));
                    projectBean1.setHomePage(resultSet.getString("homepage"));
                    projectBean1.setProjectStatus(resultSet.getInt("isActive"));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return projectBean1;
    }

    /**
     * Sets bean variable and returns list of bean reference variables.
     *
     * @param resultSet
     * @return List projectDescriptionList
     */
    public List<ProjectBean> getProjectBeanList(ResultSet resultSet) {
        ProjectBean projectBean;
        List<ProjectBean> projectDescriptionList = null;
        try {
            projectDescriptionList = new ArrayList<>();
            while (resultSet.next()) {
                projectBean = new ProjectBean();
                projectBean.setProjectId(resultSet.getInt("project_id"));
                projectBean.setProjectName(resultSet.getString("project_name"));
                projectBean.setDescription(resultSet.getString("description"));
                projectBean.setIdentifier(resultSet.getString("identifier"));
                projectBean.setHomePage(resultSet.getString("homepage"));
                projectBean.setPrjctInsrtDate(DateConvertor.
                        timeStampTo(resultSet.getTimestamp("insertion_time")));
                projectDescriptionList.add(projectBean);
            }
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        }
        return projectDescriptionList;
    }

    /**
     * Retrieves project list for users.
     *
     * @param empType
     * @param empId
     * @return List projectDescriptionList
     */
    public List<ProjectBean> getProjectNames(int empType, int empId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        List<ProjectBean> projectDescriptionList = new ArrayList<>();
        try {
            connection = this.dbConnection.getConnection();
            String sql = "{CALL getOpenProjectDetails(?,?)}";
            Object parameters[] = new Object[]{empType, empId};
            resultSet = this.dbConnection.executeStoredProcedure(connection, sql, parameters);
            projectDescriptionList = this.getProjectBeanList(resultSet);
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return projectDescriptionList;
    }

    /**
     * Retrieves closed project names.
     *
     * @param empType
     * @param empId
     * @return List closedProjectDescriptionList
     */
    public List<ProjectBean> getClosedProjectNames(int empType, int empId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        List<ProjectBean> closedProjectDescriptionList = new ArrayList<>();
        try {
            connection = this.dbConnection.getConnection();
            String sql = "{CALL getClosedProjectList(?,?)}";
            Object parameters[] = new Object[]{empType, empId};
            resultSet = this.dbConnection.executeStoredProcedure(connection, sql, parameters);
            closedProjectDescriptionList = this.getProjectBeanList(resultSet);
        } catch (SQLException e) {
            LOGGER.error("ProjectDao.run", e);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return closedProjectDescriptionList;
    }

    /**
     * Adds project in database.
     *
     * @param projectBean1
     * @return boolean
     */
    public boolean addProject(ProjectBean projectBean1) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        boolean status = false;
        List trackerList;
        List moduleList;
        try {
            connection = this.dbConnection.getConnection();
            trackerList = projectBean1.getTrackerList();
            moduleList = projectBean1.getModuleList();
            callableStatement = connection.prepareCall("{CALL addProject(?,?,?,?,?)}");
            callableStatement.setString(1, projectBean1.getProjectName());
            callableStatement.setString(2, projectBean1.getDescription());
            callableStatement.setString(3, projectBean1.getIdentifier());
            callableStatement.setString(4, projectBean1.getHomePage());
            callableStatement.registerOutParameter(5, Types.BIGINT);
            int rowCount = callableStatement.executeUpdate();
            int id = callableStatement.getInt(5);
            if (rowCount > 0) {
                callableStatement = connection.prepareCall("{CALL mapProjectTrackers(?,?)}");
                for (int i = 0; i < trackerList.size(); i = i + 1) {
                    callableStatement.setInt(1, id);
                    callableStatement.setString(2, (String) trackerList.get(i));
                    callableStatement.executeUpdate();
                }
                callableStatement = connection.prepareCall("{CALL mapProjectModules(?,?)}");
                for (int i = 0; i < moduleList.size(); i = i + 1) {
                    callableStatement.setInt(1, id);
                    callableStatement.setString(2, (String) moduleList.get(i));
                    callableStatement.executeUpdate();
                }
                status = true;
            }
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } finally {
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return status;
    }

    /**
     * Validates home page URL from database.
     *
     * @param projectBean1
     * @return boolean
     */
    public boolean validateHomePage(ProjectBean projectBean1) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        boolean status = false;
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getProjectDetails()}");
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("homepage").equals(projectBean1.getHomePage())) {
                    status = true;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return status;
    }

    /**
     * Retrieves module list from database.
     *
     * @return List moduleList
     */
    public List<String> getModuleList() {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        List<String> moduleList = new ArrayList<>();
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getModuleDetails()}");
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                moduleList.add(resultSet.getString("module_name"));
            }
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return moduleList;
    }

    /**
     * Retrieves tracker list from database.
     *
     * @return List trackerList
     */
    public List<String> getTrackerList() {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        List<String> trackerList = new ArrayList<>();
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getTrackerDetails()}");
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                trackerList.add(resultSet.getString("tracker_type"));
            }
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }

        return trackerList;
    }

    /**
     * Validates identifier from database for duplicate entry.
     *
     * @param projectBean1
     * @return boolean
     */
    public boolean validateIdentifier(ProjectBean projectBean1) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        boolean status = false;
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getProjectDetails()}");
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("identifier").equals(projectBean1.getIdentifier())) {
                    status = true;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return status;
    }

    /**
     * Adds project documents.
     *
     * @param projectBean
     * @return boolean
     */
    public boolean addProjectDocs(ProjectBean projectBean) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        boolean flag = false;
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL addProjectDocs(?,?,?,?)}");
            callableStatement.setString(1, projectBean.getProjectDocTitle());
            callableStatement.setString(2, projectBean.getPrjctDocPath());
            callableStatement.setInt(3, projectBean.getProjectId());
            callableStatement.setString(4, projectBean.getDescription());
            int rowCount = callableStatement.executeUpdate();
            if (rowCount > 0) {
                flag = true;
            }
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } finally {
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return flag;
    }

    /**
     * Retrieves project document list from database.
     *
     * @param projectId
     * @return List projDocsDetailsList
     */
    public List getProjDocsDetails(int projectId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        List<ProjectBean> projDocsDetailsList = new ArrayList<>();
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getProjDocumentDetails()}");
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                if (projectId == resultSet.getInt("project_id")) {
                    ProjectBean projectBean = new ProjectBean();
                    projectBean.setProjDocumentId(resultSet.getInt("doc_id"));
                    projectBean.setProjectDocTitle(resultSet.getString("doc_name"));
                    projectBean.setDescription(resultSet.getString("description"));
                    projectBean.setPrjctDocPath(resultSet.getString("location_path"));
                    projectBean.setPrjDocInsrtDate(DateConvertor.timeStampTo(resultSet.getTimestamp("insertion_time")));
                    projDocsDetailsList.add(projectBean);
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return projDocsDetailsList;
    }

    /**
     * Retrieves project settings from database.
     *
     * @param projectId
     * @return ProjectBean projectBean
     */
    public ProjectBean getProjectSettings(int projectId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ProjectBean projectBean = new ProjectBean();
        ResultSet resultSet = null;
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getProjectSettings(?)}");
            callableStatement.setInt(1, projectId);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                projectBean.setProjectId(resultSet.getInt("project_id"));
                projectBean.setProjectName(resultSet.getString("project_name"));
                projectBean.setDescription(resultSet.getString("description"));
                projectBean.setIdentifier(resultSet.getString("identifier"));
                projectBean.setHomePage(resultSet.getString("homepage"));
                projectBean.setProjectModuleList(StringToListConvertor.stringToListConvertor(resultSet.getString("module_list")));
                projectBean.setPrjctTrckerList(StringToListConvertor.stringToListConvertor(resultSet.getString("tracker_list")));
            }
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return projectBean;
    }

    /**
     * Updates project settings.
     *
     * @param projectBean1
     * @return boolean
     */
    public boolean updateProjectSettings(ProjectBean projectBean1) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        PreparedStatement preparedStatement = null;
        boolean flag = false;
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL updateProjectSettings(?,?,?,?)}");
            callableStatement.setInt(1, projectBean1.getProjectId());
            callableStatement.setString(2, projectBean1.getProjectName());
            callableStatement.setString(3, projectBean1.getDescription());
            callableStatement.setString(4, projectBean1.getHomePage());
            int rwCount = callableStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM `project_module_map` WHERE project_id=?;");
            preparedStatement.setInt(1, projectBean1.getProjectId());
            preparedStatement.executeUpdate();
            callableStatement = connection.prepareCall("{CALL mapProjectModules(?,?)}");
            for (int i = 0; i < projectBean1.getModuleList().size(); i = i + 1) {
                callableStatement.setInt(1, projectBean1.getProjectId());
                callableStatement.setString(2, projectBean1.getModuleList().get(i).toString());
                callableStatement.executeUpdate();
            }
            preparedStatement = connection.prepareStatement("DELETE FROM `project_tracker_map` WHERE project_id=?;");
            preparedStatement.setInt(1, projectBean1.getProjectId());
            preparedStatement.executeUpdate();
            callableStatement = connection.prepareCall("{CALL mapProjectTrackers(?,?)}");
            for (int i = 0; i < projectBean1.getTrackerList().size(); i = i + 1) {
                callableStatement.setInt(1, projectBean1.getProjectId());
                callableStatement.setString(2, projectBean1.getTrackerList().get(i).toString());
                callableStatement.executeUpdate();
            }
            flag = true;
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } finally {
            this.dbConnection.freePreparedStatement(preparedStatement);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return flag;
    }

    /**
     * Retrieves open issues from database.
     *
     * @param projectBean1
     * @return ProjectBean projectBean1
     */
    public ProjectBean getOpenIssue(ProjectBean projectBean1) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dbConnection.getConnection();
            List<ProjectBean> openIssueList = new ArrayList<>();
            List<ProjectBean> closedIssueList = new ArrayList<>();
            callableStatement = connection.prepareCall("{CALL `getOpenIssues`(?)}");
            callableStatement.setInt(1, projectBean1.getProjectId());
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ProjectBean projectBean = new ProjectBean();
                projectBean.setTrackerId(resultSet.getInt("tracker_id"));
                projectBean.setTrackerType(resultSet.getString("tracker_type"));
                projectBean.setOpenIssuesCount(resultSet.getInt("open_issue"));
                openIssueList.add(projectBean);
            }
            callableStatement = connection.prepareCall("{CALL `getTotalIssues`(?)}");
            callableStatement.setInt(1, projectBean1.getProjectId());
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ProjectBean projectBean = new ProjectBean();
                projectBean.setTrackerId(resultSet.getInt("tracker_id"));
                projectBean.setTrackerType(resultSet.getString("tracker_type"));
                projectBean.setClosedIssuesCount(resultSet.getInt("closed_issues"));
                closedIssueList.add(projectBean);
            }
            projectBean1.setOpenIssueList(openIssueList);
            projectBean1.setClosedIssueList(closedIssueList);
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return projectBean1;
    }

    /**
     * Closes running project.
     *
     * @param projectId
     * @return boolean
     */
    public boolean closeProject(int projectId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        boolean flag = false;
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL closeProject(?)}");
            callableStatement.setInt(1, projectId);
            int rwCount = callableStatement.executeUpdate();
            flag = true;
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } finally {
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }

        return flag;
    }

    /**
     * Retrieves issue history from database.
     *
     * @param ptojectId
     * @return List issueDetails
     */
    public List<IssuesBean> getIssueActivityList(int ptojectId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        List<IssuesBean> issueDetails = new ArrayList<>();
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL getIssueActivityList(?)}");
            callableStatement.setInt(1, ptojectId);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                IssuesBean issuesBean = new IssuesBean();
                issuesBean.setIssueId(resultSet.getInt("issue_id"));
                issuesBean.setIssueName(resultSet.getString("issue_name"));
                issuesBean.setIssueSubject(resultSet.getString("subject"));
                issuesBean.setIssueDesc(resultSet.getString("description"));
                issuesBean.setStatusType(resultSet.getString("status_type"));
                issuesBean.setTrackerType(resultSet.getString("tracker_type"));
                issuesBean.setInsertionDate(DateConvertor.
                        timeStampTo(resultSet.getTimestamp("insertion_time")));
                issuesBean.setProjectId(resultSet.getInt("project_id"));
                issuesBean.setProjectIdentifier(resultSet.getString("identifier"));
                issuesBean.setProjectName(resultSet.getString("project_name"));
                issueDetails.add(issuesBean);
            }
        } catch (SQLException e) {
            LOGGER.error("ProjectDao.run", e);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return issueDetails;
    }

    /**
     * Checks project authorization.
     *
     * @param empTypeId
     * @param empId
     * @param projIdentifier
     * @return boolean
     */
    public boolean checkProjectAuthorization(int empTypeId, int empId, String projIdentifier) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        try {
            connection = this.dbConnection.getConnection();
            callableStatement = connection.prepareCall("{CALL validateProjectMember(?,?,?)}");
            callableStatement.setInt(1, empTypeId);
            callableStatement.setInt(2, empId);
            callableStatement.setString(3, projIdentifier);
            resultSet = callableStatement.executeQuery();
            if (resultSet.last()) {
                flag = true;
            }
        } catch (SQLException ex) {
            LOGGER.error("ProjectDao.run", ex);
        } finally {
            this.dbConnection.freeResultSet(resultSet);
            this.dbConnection.freeCallableStatement(callableStatement);
            this.dbConnection.freeConnection(connection);
        }
        return flag;
    }

}
