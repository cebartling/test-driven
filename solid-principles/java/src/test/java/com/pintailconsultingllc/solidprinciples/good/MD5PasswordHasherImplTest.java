package com.pintailconsultingllc.solidprinciples.good;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("MD5 password hashing implementation")
class MD5PasswordHasherImplTest {

    PasswordHasher passwordHasher = new MD5PasswordHasherImpl();

    @Nested
    @DisplayName("hashPassword method")
    class HashPasswordTests {

        final String password = "67D8fa6d9&#a6sDF5cxbv78";
        String expectedHashedPassword;
        String actualHashedPassword;

        @BeforeEach
        public void doBeforeEachTest() throws NoSuchAlgorithmException {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            final byte[] digestedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            expectedHashedPassword = bytesToHex(digestedPassword);
            actualHashedPassword = passwordHasher.hashPassword(password);
        }

        @Test
        @DisplayName("should hash the password appropriately")
        void verifyDirectOutputTest() {
            assertEquals(expectedHashedPassword, actualHashedPassword);
        }
    }

    private String bytesToHex(byte[] hashByteArray) {
        StringBuilder hexStringBuilder = new StringBuilder(2 * hashByteArray.length);
        for (byte b : hashByteArray) {
            // Since the parameter is an int, a widening primitive conversion is performed to the byte argument,
            // which involves sign extension. The 8-bit byte, which is signed in Java, is sign-extended to a
            // 32-bit int. To effectively undo this sign extension, one can mask the byte with 0xFF.
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                // Pad with a zero if string is only one character in length.
                hexStringBuilder.append('0');
            }
            hexStringBuilder.append(hex);
        }
        return hexStringBuilder.toString();
    }
}