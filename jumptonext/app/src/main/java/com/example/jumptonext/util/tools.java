package com.example.jumptonext.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class tools {
    public static String getMd5(String input) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(input.getBytes());
            BigInteger mHashInt = new BigInteger(1, mDigest.digest());
            return String.format("%1$032X", mHashInt);
        } catch (NoSuchAlgorithmException lException) {
            throw new RuntimeException(lException);
        }
    }
}
