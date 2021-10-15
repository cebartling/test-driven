package com.pintailconsultingllc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Demonstration of assertions")
public class AssertionDemonstrationTests {

    /**
     * Assert that all supplied executables do not throw exceptions. All supplied executables are executed,
     * regardless of success or failure of others.
     */
    @Nested
    @DisplayName("assertAll")
    class AssertAllTests {

        AssertionDemonstration sut;

        @BeforeEach
        public void doBeforeEachTest() {
            sut = new AssertionDemonstration();
        }

        @Test
        @DisplayName("varargs usage")
        void assert_all_varargs_executables() {
            assertAll("isPrime",
                    () -> assertTrue(sut.isPrime(2)),
                    () -> assertFalse(sut.isPrime(4)),
                    () -> assertFalse(sut.isPrime(2344))
            );
        }

        @Test
        @DisplayName("collection of executables usage")
        void assert_all_collection_executables() {
            assertAll("isPrime", List.of(
                    () -> assertTrue(sut.isPrime(2)),
                    () -> assertFalse(sut.isPrime(4)),
                    () -> assertFalse(sut.isPrime(2344))
            ));
        }
    }

}
