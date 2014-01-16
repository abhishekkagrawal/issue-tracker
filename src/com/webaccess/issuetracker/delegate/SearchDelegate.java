/*
 *      Document     : SearchDelegate.java
 *      Author       : Dinesh Saini
 *      Created on   : 23/12/2013
 *      Description  : Search delegate for searching projects and issues.
 */
package com.webaccess.issuetracker.delegate;

import com.webaccess.issuetracker.bean.SearchBean;
import com.webaccess.issuetracker.dao.SearchDao;

/**
 *
 * @author Dinesh Saini
 */
public class SearchDelegate {

    private final SearchDao searchDao;

    /**
     * @author Dinesh Saini
     * @ Class constructor to initialize
     */
    public SearchDelegate() {
        this.searchDao = new SearchDao();
    }

    /**
     * @author Dinesh Saini
     * @param searchBean
     * @return SearchBean
     */
    public SearchBean getProjectSearched(SearchBean searchBean) {
        return this.searchDao.getSearchedProject(searchBean);
    }

    /**
     * @author Dinesh Saini
     * @param searchBean
     * @return SearchBean
     */
    public SearchBean getIssuesSearched(SearchBean searchBean) {
        return this.searchDao.getSearchedIssue(searchBean);
    }

}
