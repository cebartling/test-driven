package com.pintailconsultingllc.solidprinciples.bad;

public abstract class HashedPassword {
    String hashedPassword;

    public abstract void generateHash(String password);

    public abstract String unhashPassword();
}
