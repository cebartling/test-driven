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


    public void savePassword(String hashedPassword) {
        //save to the db
    }

    public void hashAndSavePassword(String password, HashingType hashingType) {
        final String hashedPassword = hashPassword(password, hashingType);
        savePassword(hashedPassword);
    }

    public String unhashPassword(String hashedPassword, HashingType hashingType) {
        String password = null;
        if (HashingType.SHA3_256.equals(hashingType)) {
            throw new UnsupportedOperationException("Cannot decrypt SHA3-256 hashing.");
        } else if (HashingType.SHA256.equals(hashingType)) {
            throw new UnsupportedOperationException("Cannot decrypt SHA-256 hashing.");
        } else if (HashingType.MD5.equals(hashingType)) {
            hashedPassword = "hashed with MD5";
        }
        return hashedPassword;
    }

    private String bytesToHex(byte[] hash) {
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

    private MessageDigest getMessageDigestInstance(HashingType hashingType) {
        try {
            if (HashingType.SHA3_256.equals(hashingType)) {
                return MessageDigest.getInstance("SHA3-256");
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
