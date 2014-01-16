/*
 *      Document     : SearchBean.java
 *      Author       : Dinesh Saini
 *      Created on   : 23/12/2013
 *      Description  : Bean class for searching issues and projects.
 */
package com.webaccess.issuetracker.bean;

import java.util.List;

/**
 *
 * @author Dinesh Saini
 */
public class SearchBean {

    private List<ProjectBean> prjctSrchedList;
    private List<IssuesBean> issueSearchedList;
    private String searchKey;
    private int empId;
    private int empTypeId;

    /**
     * @author Dinesh Saini
     * @return the prjctSrchedList
     */
    public List<ProjectBean> getPrjctSrchedList() {
        return this.prjctSrchedList;
    }

    /**
     * @author Dinesh Saini
     * @param prjctSrchedList the prjctSrchedList to set
     */
    public void setPrjctSrchedList(List<ProjectBean> prjctSrchedList) {
        this.prjctSrchedList = prjctSrchedList;
    }

    /**
     * @author Dinesh Saini
     * @return the issueSearchedList
     */
    public List<IssuesBean> getIssueSearchedList() {
        return this.issueSearchedList;
    }

    /**
     * @author Dinesh Saini
     * @param issueSearchedList the issueSearchedList to set
     */
    public void setIssueSearchedList(List<IssuesBean> issueSearchedList) {
        this.issueSearchedList = issueSearchedList;
    }

    /**
     * @author Dinesh Saini
     * @return the searchKey
     */
    public String getSearchKey() {
        return this.searchKey;
    }

    /**
     * @author Dinesh Saini
     * @param searchKey the searchKey to set
     */
    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
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
     * @return the empTypeId
     */
    public int getEmpTypeId() {
        return this.empTypeId;
    }

    /**
     * @author Dinesh Saini
     * @param empTypeId the empTypeId to set
     */
    public void setEmpTypeId(int empTypeId) {
        this.empTypeId = empTypeId;
    }

}
