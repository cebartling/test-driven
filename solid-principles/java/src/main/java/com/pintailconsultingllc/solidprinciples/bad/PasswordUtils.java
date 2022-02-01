package com.pintailconsultingllc.solidprinciples.bad;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class has too many responsibilities, violating the Single Responsibility Principle (SRP).
 * This class is responsible for hashing passwords and saving hashed passwords somewhere. These are
 * conflicting responsibilities.
 * <p>
 * This class also violates the Open-Closed Principle (OCP). Classes should be open for extension,
 * closed for modification. Adding a new hashing algorithm forces a change on this class.
 */
public class PasswordUtils {

    public void hashAndSavePassword(String password, HashingType hashingType) {
        final String hashedPassword = hashPassword(password, hashingType);
        savePassword(hashedPassword);
    }

    public String hashPassword(String password, HashingType hashingType) {
        String hashedPassword = null;
        if (HashingType.SHA3_256.equals(hashingType)) {
            MessageDigest messageDigest;
            try {
                messageDigest = MessageDigest.getInstance("SHA3-256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            byte[] encodedHash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            hashedPassword = bytesToHex(encodedHash);
        } else if (HashingType.SHA256.equals(hashingType)) {
            MessageDigest messageDigest;
            try {
                messageDigest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            byte[] encodedHash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            hashedPassword = bytesToHex(encodedHash);
        } else if (HashingType.MD5.equals(hashingType)) {
            MessageDigest messageDigest;
            try {
                messageDigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            byte[] encodedHash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            hashedPassword = bytesToHex(encodedHash);
        }
        return hashedPassword;
    }

    public void savePassword(String hashedPassword) {
        //save to the db
    }

    public String unhashPassword(String hashedPassword, HashingType hashingType) {
        String password = null;
        if (HashingType.SHA256.equals(hashingType)) {
            throw new UnsupportedOperationException("Cannot decrypt SHA256 hashing.");
        } else if (HashingType.MD5.equals(hashingType)) {
            hashedPassword = "hashed with MD5";
        }
        return hashedPassword;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
