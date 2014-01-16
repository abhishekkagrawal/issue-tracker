/*
 *      Document     : DateConvertor.java
 *      Author       : Dinesh Saini
 *      Created on   : 16/12/13
 *      Description  : Java class for conversion of date formats.
 */
package com.webaccess.issuetracker.util;

import com.opensymphony.xwork2.ActionSupport;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Converts date format from TimeStamp or Date.
 *
 * @author Dinesh Saini
 */
public final class DateConvertor extends ActionSupport {

    private static final org.slf4j.Logger LOGGER = LoggingUtil.getLogger();
    private final String ds = getText("driver");

    private DateConvertor() {
    }

    /**
     * Converts Timestamp to YYYY-MM-dd hh:mm:ss a format.
     *
     * @param timestamp
     * @return String formatted date
     */
    public static String timeStampTo(Timestamp timestamp) {
        Date date = null;
        SimpleDateFormat sdf = null;
        try {
            date = Timestamp.valueOf(timestamp.toString());
            sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss a", Locale.ENGLISH);
        } catch (Exception e) {
            LOGGER.error("DateConvertor.run", e);
        }
        return sdf.format(date);
    }

    /**
     * @author Dinesh Saini
     * @param date
     * @return String date in YYYY-MM-DD format
     */
    public static String dateToSimple(Date date) {
        SimpleDateFormat sdf = null;
        try {
            sdf = new SimpleDateFormat("YYYY-MM-dd", Locale.ENGLISH);
        } catch (Exception e) {
            LOGGER.error("DateConvertor.run", e);
        }
        return sdf.format(date);
    }
}
