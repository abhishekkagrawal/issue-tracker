/*
 *      Document     : MD5Encryptor.java
 *      Author       : Dinesh Saini
 *      Created on   : 25/11/2013
 *      Description  : MD5 Encryptor class for passord encryption.
 */
package com.webaccess.issuetracker.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class MD5Encryptor is utility class for application to perform MD5 encryption
 * for validating user.
 *
 * @author Dinesh Saini
 */
public final class MD5Encryptor {

    private static final org.slf4j.Logger LOGGER = LoggingUtil.getLogger();

    private MD5Encryptor() {
    }

    /**
     * @author Dinesh Saini
     * @param input
     * @return md5 encrypted String
     */
    public static String md5(String input) {
        String md5Encripted = null;
        try {
            if (null == input) {
                return null;
            }
            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");

            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());

            //Converts message digest value in base 16 (hex)
            md5Encripted = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("MD5Encryptor.run", e);
        } catch (Exception e) {
            LOGGER.error("MD5Encryptor.run", e);
        }
        return md5Encripted;
    }
}
