package com.pintailconsultingllc.solidprinciples.good.impl;

import com.pintailconsultingllc.solidprinciples.good.PasswordHasher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Password hashing implementation that uses MD5 digest.
 */
public class MD5PasswordHasher extends PasswordHasherSupport implements PasswordHasher {

    @Override
    protected MessageDigest getMessageDigestInstance() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("MD5");
    }
}
