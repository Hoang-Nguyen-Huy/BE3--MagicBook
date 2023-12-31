/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Dell Latitude 7490
 */
public class Util {

    /*
    *
    *encryptPassword use for encrypting password before save to db
    *encryptPassword use for ecrypting id before assgin to cookie.Value();
     */
    public static String encryptPassword(String password) {
        if (password == null) {
            // Xử lý trường hợp password là null
            return null;
        }

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = messageDigest.digest(password.getBytes());

            // Chuyển đổi byte array thành định dạng hex
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu thuật toán không được hỗ trợ
            return null;
        }

    }

    // Hàm mã hóa tin nhắn
    public static String encrypt(String message) {
        char[] messageChars = message.toCharArray();
        for (int i = 0; i < messageChars.length; i++) {
            messageChars[i] = (char) (messageChars[i] ^ 0xFF); // XOR với 0xFF
        }
        return new String(messageChars);
    }

    // Hàm giải mã tin nhắn
    public static String decrypt(String encryptedMessage) {
        return encrypt(encryptedMessage); // Vì XOR có tính chất đảo ngược
    }

}
