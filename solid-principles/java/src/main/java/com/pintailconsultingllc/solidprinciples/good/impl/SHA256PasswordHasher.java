package com.pintailconsultingllc.solidprinciples.good.impl;

import com.pintailconsultingllc.solidprinciples.good.PasswordHasher;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256PasswordHasher implements PasswordHasher {

    @Override
    public String hashPassword(String password) throws NoSuchAlgorithmException {
        final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        final byte[] digestedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(digestedPassword);
    }

    private String bytesToHex(byte[] hashByteArray) {
        StringBuilder hexStringBuilder = new StringBuilder(2 * hashByteArray.length);
        for (byte b : hashByteArray) {
            // Since the parameter is an int, a widening primitive conversion is performed to the byte argument,
            // which involves sign extension. The 8-bit byte, which is signed in Java, is sign-extended to a
            // 32-bit int. To effectively undo this sign extension, mask the byte with 0xFF.
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                // Pad with a zero if string is only one character in length.
                hexStringBuilder.append('0');
            }
            hexStringBuilder.append(hex);
        }
        return hexStringBuilder.toString();
    }
}
