package com.pintailconsultingllc.solidprinciples.good.impl;

import com.pintailconsultingllc.solidprinciples.good.PasswordHasher;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Password hashing implementation that uses MD5 digest.
 */
public class MD5PasswordHasher extends PasswordHasherSupport implements PasswordHasher {

    @Override
    public String hashPassword(String password) throws NoSuchAlgorithmException {
        final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        final byte[] digestedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(digestedPassword);
    }
}
