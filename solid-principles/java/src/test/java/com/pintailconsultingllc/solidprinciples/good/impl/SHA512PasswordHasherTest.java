package com.pintailconsultingllc.solidprinciples.good.impl;

import com.pintailconsultingllc.solidprinciples.good.PasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("SHA-512 password hashing implementation")
class SHA512PasswordHasherTest {

    PasswordHasher passwordHasher = new SHA512PasswordHasher();

    @Nested
    @DisplayName("hashPassword method")
    class HashPasswordTests {

        final String password = "67D8fa6d9&#a6sDF5cxbv78";
        String expectedHashedPassword;
        String actualHashedPassword;
        HexidecimalStringFormatter hexidecimalStringFormatter;

        @BeforeEach
        public void doBeforeEachTest() throws NoSuchAlgorithmException {
            hexidecimalStringFormatter = new HexidecimalStringFormatter();
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            final byte[] digestedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            expectedHashedPassword = hexidecimalStringFormatter.format(digestedPassword);
            actualHashedPassword = passwordHasher.hashPassword(password);
        }

        @Test
        @DisplayName("should hash the password appropriately")
        void verifyDirectOutputTest() {
            assertEquals(expectedHashedPassword, actualHashedPassword);
        }
    }
}
