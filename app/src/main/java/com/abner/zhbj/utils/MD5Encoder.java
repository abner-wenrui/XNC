package com.abner.zhbj.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Project   com.abner.zhbj.utils
 *
 * @Author Abner
 * Time   2016/10/23.14:11
 */

public class MD5Encoder {
    public static String encode(String string) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] hash = MessageDigest.getInstance("MD5").digest(string.getBytes("utf-8"));
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append(0);
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
