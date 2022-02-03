package com.pintailconsultingllc.solidprinciples.good.impl;

import com.pintailconsultingllc.solidprinciples.good.PasswordHasher;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA512PasswordHasher extends PasswordHasherSupport implements PasswordHasher {

    @Override
    public String hashPassword(String password) throws NoSuchAlgorithmException {
        final MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        final byte[] digestedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(digestedPassword);
    }
}
