package com.pintailconsultingllc.solidprinciples.good.impl;

import com.pintailconsultingllc.solidprinciples.good.PasswordHasher;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

abstract class PasswordHasherSupport implements PasswordHasher {

    private HexidecimalStringFormatter hexidecimalStringFormatter;

    PasswordHasherSupport() {
        hexidecimalStringFormatter = new HexidecimalStringFormatter();
    }

    @Override
    public String hashPassword(String password) throws NoSuchAlgorithmException {
        final MessageDigest messageDigest = getMessageDigestInstance();
        final byte[] digestedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        return hexidecimalStringFormatter.format(digestedPassword);
    }

    /**
     * Implementations will return the appropriate message digest.
     *
     * @return A java.security.MessageDigest instance.
     */
    protected abstract MessageDigest getMessageDigestInstance() throws NoSuchAlgorithmException;

}
