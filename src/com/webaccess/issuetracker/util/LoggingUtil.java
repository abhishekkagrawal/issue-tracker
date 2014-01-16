/*
 *      Document     : LoggingUtil.java
 *      Author       : Dinesh Saini
 *      Created on   : 30/12/2013
 *      Description  : Logging util class for logging application exception
 *                     and errors.
 */
package com.webaccess.issuetracker.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class LoggingUtil is Utility class for Logging error, exceptions and
 * information.
 *
 * @author Dinesh Saini
 */
public final class LoggingUtil {

    private LoggingUtil() {
    }

    /**
     * Retrieves a logger name.
     *
     * @return logger for Logging operation
     */
    public static Logger getLogger() {
        return LoggerFactory.getLogger(LoggingUtil.class.getName());
    }

    /**
     * Retrieves a logger named according to the value of the loggerName
     * parameter.
     *
     * @param loggerName The name of the logger to retrieve.
     * @return logger for Logging operation
     */
    public static Logger getLogger(String loggerName) {
        return LoggerFactory.getLogger(loggerName);
    }
}
