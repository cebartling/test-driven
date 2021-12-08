package com.pintailconsultingllc;

import com.pintailconsultingllc.testing.annotations.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Demonstration of assertions")
public class AssertionDemonstrationTests {

    AssertionDemonstration sut;

    @BeforeEach
    public void doBeforeEachTest() {
        sut = new AssertionDemonstration();
    }

    /**
     * Assert that all supplied executables do not throw exceptions. All supplied executables are executed,
     * regardless of success or failure of others.
     * <p>
     * The interesting thing about assertAll is that it always checks all of the assertions that are passed
     * to it, no matter how many fail. If all pass, all is fine. If at least one fails you get a detailed
     * result of all that went wrong. It is best used for asserting a set of properties that belong
     * together.
     */
    @Nested
    @DisplayName("assertAll assertion demonstration")
    class AssertAllTests {

        @UnitTest
        @DisplayName("varargs usage")
        void assert_all_varargs_executables() {
            assertAll("isPrime",
                    () -> assertTrue(sut.isPrime(2)),
                    () -> assertFalse(sut.isPrime(4)),
                    () -> assertFalse(sut.isPrime(2344))
            );
//            assertTrue(sut.isPrime(2));
//            assertFalse(sut.isPrime(4));
//            assertFalse(sut.isPrime(2344));
        }

        @UnitTest
        @DisplayName("collection of executables usage")
        void assert_all_collection_of_executables() {
            assertAll("isPrime", List.of(
                    () -> assertTrue(sut.isPrime(2)),
                    () -> assertFalse(sut.isPrime(4)),
                    () -> assertFalse(sut.isPrime(2344))
            ));
        }

        @UnitTest
        @DisplayName("stream of executables usage")
        void assert_all_stream_of_executables() {
            assertAll("isPrime", Stream.of(
                    () -> assertTrue(sut.isPrime(2)),
                    () -> assertFalse(sut.isPrime(4)),
                    () -> assertFalse(sut.isPrime(2344))
            ));
        }
    }

    /**
     * Asserts that exercising the SUT does not throw an exception. Helpful when you want to be explicit
     * about certain scenarios not throwing exceptions.
     */
    @Nested
    @DisplayName("assertDoesNotThrow")
    class AssertDoesNotThrowTests {

        @UnitTest
        @DisplayName("basic usage")
        void basic_usage() {
            assertDoesNotThrow(() -> sut.doSomethingThatDoesNotThrowException());
        }
    }
}
