package com.pintailconsultingllc.solidprinciples.good;

import java.security.NoSuchAlgorithmException;

public interface PasswordHasher {

    String hashPassword(String password) throws NoSuchAlgorithmException;
}
