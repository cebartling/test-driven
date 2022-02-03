package com.pintailconsultingllc.solidprinciples.good.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Hexidecimal string formatter tests")
class HexidecimalStringFormatterTest {

    HexidecimalStringFormatter formatter;

    @BeforeEach
    public void doBeforeEachTest() {
        formatter = new HexidecimalStringFormatter();
    }

    @Nested
    @DisplayName("format method")
    class FormatTests {

        String expected;
        String actual;
        byte[] byteArray;

        @BeforeEach
        public void doBeforeEachTest() {
            byteArray = "SomeStringAa2\t".getBytes(StandardCharsets.UTF_8);
            StringBuilder hexStringBuilder = new StringBuilder(2 * byteArray.length);
            for (byte b : byteArray) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexStringBuilder.append('0');
                }
                hexStringBuilder.append(hex);
            }
            expected = hexStringBuilder.toString();
            actual = formatter.format(byteArray);
        }

        @Test
        @DisplayName("should properly convert byte array to hexidecimal string with proper padding")
        void verifyDirectOutput() {
            assertEquals(expected, actual);
        }
    }
}
