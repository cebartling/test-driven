package com.pintailconsultingllc.solidprinciples.good.impl;

import com.pintailconsultingllc.solidprinciples.good.PasswordHasher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256PasswordHasher extends PasswordHasherSupport implements PasswordHasher {

    @Override
    protected MessageDigest getMessageDigestInstance() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-256");
    }
}
