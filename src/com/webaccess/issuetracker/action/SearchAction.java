/*
 *      Document     : SearchAction.java
 *      Author       : Dinesh Saini
 *      Created on   : 23/12/2013
 *      Description  : Action class for searching in all issues and projects with
 *                     fileter options.
 */
package com.webaccess.issuetracker.action;

import com.opensymphony.xwork2.ActionSupport;
import com.webaccess.issuetracker.bean.SearchBean;
import com.webaccess.issuetracker.delegate.SearchDelegate;
import com.webaccess.issuetracker.util.LoggingUtil;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 * Class SearchAction performs search operations in all projects and issues.
 *
 * @author Dinesh Saini
 */
public class SearchAction extends ActionSupport {

    private static final org.slf4j.Logger LOGGER = LoggingUtil.getLogger();
    private static List searchCheckBox;
    private String search;
    private SearchBean searchBean;
    private List searchChecked;

    static {
        SearchAction.searchCheckBox = new ArrayList();
        SearchAction.searchCheckBox.add("Project");
        SearchAction.searchCheckBox.add("Issues");
    }
    private int empId;
    private int empTypeId;

    /**
     * Class constructor to initialize variable.
     *
     */
    public SearchAction() {
        try {
            this.searchChecked = new ArrayList();
            this.searchChecked.add("Project");
            this.searchChecked.add("Issues");
        } catch (Exception e) {
            LOGGER.error("SearchAction.run", e);
        }
    }

    /**
     * Perform search operation in all projects and issues.
     *
     * @return String
     */
    @Override
    public String execute() {
        HttpSession session;
        SearchDelegate searchDelegate;
        try {
            session = ServletActionContext.getRequest().getSession();
            if (session.getAttribute("login") == null || session.getAttribute("empId") == null) {
                addActionError("Please login first");
                return "login";
            }
            this.empId = Integer.parseInt(session.getAttribute("empId").toString());
            this.empTypeId = Integer.parseInt(session.getAttribute("empTypeId").toString());
            searchDelegate = new SearchDelegate();
            this.searchBean = new SearchBean();
            this.searchBean.setSearchKey(this.search);
            this.searchBean.setEmpId(this.empId);
            this.searchBean.setEmpTypeId(this.empTypeId);
            if (this.search != null && this.search.length() != 0) {
                if (this.searchChecked.contains("Issues")) {
                    this.searchBean = searchDelegate.getIssuesSearched(this.searchBean);
                }
                if (this.searchChecked.contains("Project")) {
                    this.searchBean = searchDelegate.getProjectSearched(this.searchBean);
                }
            }
        } catch (NumberFormatException e) {
            LOGGER.error("SearchAction.run", e);
        } catch (Exception e) {
            LOGGER.error("SearchAction.run", e);
        }
        return SUCCESS;
    }

    /**
     * @return int empId
     */
    public int getEmpId() {
        return this.empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(int empId) {
        this.empId = empId;
    }

    /**
     * @return int empTypeId
     */
    public int getEmpTypeId() {
        return this.empTypeId;
    }

    /**
     * @param empTypeId the empTypeId to set
     */
    public void setEmpTypeId(int empTypeId) {
        this.empTypeId = empTypeId;
    }

    /**
     * @return List searchCheckBox
     */
    public List getSearchCheckBox() {
        return SearchAction.searchCheckBox;
    }

    /**
     * @param searchCheckBox the searchCheckBox to set
     */
    public void setSearchCheckBox(List searchCheckBox) {
        SearchAction.searchCheckBox = searchCheckBox;
    }

    /**
     * @return List searchChecked
     */
    public List getSearchChecked() {
        return this.searchChecked;
    }

    /**
     * @param searchChecked the searchChecked to set
     */
    public void setSearchChecked(List searchChecked) {
        this.searchChecked = searchChecked;
    }

    /**
     * @return String search
     */
    public String getSearch() {
        return this.search;
    }

    /**
     * @param search the search to set
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * @return SearchBean searchBean
     */
    public SearchBean getSearchBean() {
        return this.searchBean;
    }

    /**
     * @param searchBean the searchBean to set
     */
    public void setSearchBean(SearchBean searchBean) {
        this.searchBean = searchBean;
    }

}
