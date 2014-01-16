/*
 *      Document     : Users.java
 *      Author       : Dinesh Saini
 *      Created on   : 25/11/2013
 *      Description  : Bean class for users.
 */
package com.webaccess.issuetracker.bean;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Dinesh Saini
 */
public class Users {

    private String userId;
    private String password;
    private String empName;
    private int empId;
    private int empStatus;
    private String firstName;
    private String lastName;
    private int empTypId;
    private String phone;
    private String email;
    private boolean flag;
    private String empType;
    private List empTypeList;
    private Timestamp regDate;
    private List empTypeTest;
    private int projectId;
    private String projectName;
    private String prjMmbrshipDate;
    private String identifier;
    private String empRoles;

    /**
     * @author Dinesh Saini
     * @return the userId
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * @author Dinesh Saini
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @author Dinesh Saini
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @author Dinesh Saini
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @author Dinesh Saini
     * @return the empName
     */
    public String getEmpName() {
        return this.empName;
    }

    /**
     * @author Dinesh Saini
     * @param empName the empName to set
     */
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    /**
     * @author Dinesh Saini
     * @return the empId
     */
    public int getEmpId() {
        return this.empId;
    }

    /**
     * @author Dinesh Saini
     * @param empId the empId to set
     */
    public void setEmpId(int empId) {
        this.empId = empId;
    }

    /**
     * @author Dinesh Saini
     * @return the empStatus
     */
    public int getEmpStatus() {
        return this.empStatus;
    }

    /**
     * @author Dinesh Saini
     * @param empStatus the empStatus to set
     */
    public void setEmpStatus(int empStatus) {
        this.empStatus = empStatus;
    }

    /**
     * @author Dinesh Saini
     * @return the firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @author Dinesh Saini
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @author Dinesh Saini
     * @return the lastName
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * @author Dinesh Saini
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @author Dinesh Saini
     * @return int empTypId
     */
    public int getEmpTypId() {
        return this.empTypId;
    }

    /**
     * @author Dinesh Saini
     * @param empTypId the empTypId to set
     */
    public void setEmpTypId(int empTypId) {
        this.empTypId = empTypId;
    }

    /**
     * @author Dinesh Saini
     * @return the phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * @author Dinesh Saini
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @author Dinesh Saini
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @author Dinesh Saini
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @author Dinesh Saini
     * @return the flag
     */
    public boolean isFlag() {
        return this.flag;
    }

    /**
     * @author Dinesh Saini
     * @param flag the flag to set
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * @author Dinesh Saini
     * @return the empType
     */
    public String getEmpType() {
        return this.empType;
    }

    /**
     * @author Dinesh Saini
     * @param empType the empType to set
     */
    public void setEmpType(String empType) {
        this.empType = empType;
    }

    /**
     * @author Dinesh Saini
     * @return the empTypeList
     */
    public List getEmpTypeList() {
        return this.empTypeList;
    }

    /**
     * @author Dinesh Saini
     * @param empTypeList the empTypeList to set
     */
    public void setEmpTypeList(List empTypeList) {
        this.empTypeList = empTypeList;
    }

    /**
     * @author Dinesh Saini
     * @return the regDate
     */
    public Timestamp getRegDate() {
        return this.regDate;
    }

    /**
     * @author Dinesh Saini
     * @param regDate the regDate to set
     */
    public void setRegDate(Timestamp regDate) {
        this.regDate = regDate;
    }

    /**
     * @author Dinesh Saini
     * @return the projectId
     */
    public int getProjectId() {
        return this.projectId;
    }

    /**
     * @author Dinesh Saini
     * @param projectId the projectId to set
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    /**
     * @author Dinesh Saini
     * @return the projectName
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * @author Dinesh Saini
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @author Dinesh Saini
     * @return the prjMmbrshipDate
     */
    public String getPrjMmbrshipDate() {
        return this.prjMmbrshipDate;
    }

    /**
     * @author Dinesh Saini
     * @param prjMmbrshipDate the prjMmbrshipDate to set
     */
    public void setPrjMmbrshipDate(String prjMmbrshipDate) {
        this.prjMmbrshipDate = prjMmbrshipDate;
    }

    /**
     * @author Dinesh Saini
     * @return the Identifier
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     * @author Dinesh Saini
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @author Dinesh Saini
     * @return the empRoles
     */
    public String getEmpRoles() {
        return this.empRoles;
    }

    /**
     * @author Dinesh Saini
     * @param empRoles the empRoles to set
     */
    public void setEmpRoles(String empRoles) {
        this.empRoles = empRoles;
    }

    /**
     * @author Dinesh Saini
     * @return the empTypeTest
     */
    public List getEmpTypeTest() {
        return this.empTypeTest;
    }

    /**
     * @author Dinesh Saini
     * @param empTypeTest the empTypeTest to set
     */
    public void setEmpTypeTest(List empTypeTest) {
        this.empTypeTest = empTypeTest;
    }
}
