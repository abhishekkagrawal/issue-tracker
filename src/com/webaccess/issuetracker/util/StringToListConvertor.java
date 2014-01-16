/*
 *      Document     : StringToListConvertor.java
 *      Author       : Dinesh Saini
 *      Created on   : 21/12/2013
 *      Description  : Utility class for converting string to list.
 */
package com.webaccess.issuetracker.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Class StringToListConvertor converts string to list separated by comma(,).
 *
 * @author Dinesh Saini
 */
public final class StringToListConvertor {

    private static List resultantList;
    private static final org.slf4j.Logger LOGGER = LoggingUtil.getLogger();

    private StringToListConvertor() {
    }

    /**
     * @author Dinesh Saini
     * @param str
     * @return List converted from String
     */
    public static List stringToListConvertor(String str) {
        try {
            resultantList = new ArrayList();
            String[] arr = str.split(",");
            for (int i = 0; i < arr.length; i = i + 1) {
                resultantList.add(arr[i]);
            }
        } catch (Exception e) {
            LOGGER.error("StringToListConvertor.run", e);
        }
        return resultantList;
    }

}
