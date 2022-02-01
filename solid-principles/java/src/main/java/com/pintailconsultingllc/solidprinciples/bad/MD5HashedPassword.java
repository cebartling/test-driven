package com.pintailconsultingllc.solidprinciples.bad;

public class MD5HashedPassword extends HashedPassword {

    private PasswordUtils passwordUtils;

    private MD5HashedPassword() {
        passwordUtils = new PasswordUtils();
    }

    @Override
    public void generateHash(String password) {
        hashedPassword = passwordUtils.hashPassword(password, HashingType.MD5);
    }

    @Override
    public String unhashPassword() {
        return passwordUtils.unhashPassword(hashedPassword, HashingType.MD5);
    }
}
