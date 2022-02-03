package com.pintailconsultingllc.solidprinciples.bad;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class has too many responsibilities, violating the Single Responsibility Principle (SRP).
 * This class is responsible for hashing passwords and saving hashed passwords somewhere. These are
 * conflicting responsibilities.
 * <p>
 * Adding a new hashing algorithm forces a change on this class, which violates the Open-Closed Principle (OCP).
 * Classes should be open for extension, but closed for modification.
 */
public class PasswordUtils {

    public String hashPassword(String password, HashingType hashingType) {
        String hashedPassword = null;
        MessageDigest messageDigest = getMessageDigestInstance(hashingType);
        if (messageDigest != null) {
            byte[] encodedHash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            hashedPassword = bytesToHex(encodedHash);
        }
        return hashedPassword;
    }


    public void savePassword(String key, String hashedPassword) {
        // save to the persistent storage
    }

    public void hashAndSavePassword(String key, String password, HashingType hashingType) {
        final String hashedPassword = hashPassword(password, hashingType);
        savePassword(key, hashedPassword);
    }

    private String bytesToHex(byte[] hashByteArray) {
        StringBuilder hexStringBuilder = new StringBuilder(2 * hashByteArray.length);
        for (byte b : hashByteArray) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexStringBuilder.append('0');
            }
            hexStringBuilder.append(hex);
        }
        return hexStringBuilder.toString();
    }

    private MessageDigest getMessageDigestInstance(HashingType hashingType) {
        try {
            if (HashingType.SHA512.equals(hashingType)) {
                return MessageDigest.getInstance("SHA-512");
            } else if (HashingType.SHA256.equals(hashingType)) {
                return MessageDigest.getInstance("SHA-256");
            } else if (HashingType.MD5.equals(hashingType)) {
                return MessageDigest.getInstance("MD5");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
