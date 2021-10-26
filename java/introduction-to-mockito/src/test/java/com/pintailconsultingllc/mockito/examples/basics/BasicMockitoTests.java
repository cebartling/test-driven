package com.pintailconsultingllc.mockito.examples.basics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Basic Mockito example tests")
public class BasicMockitoTests {


    @Nested
    @DisplayName("demonstrating different ways to create mocks and inject them into the SUT")
    class CreatingMocks {

        String actual;
        final String expectedResult = "A result";

        @Nested
        @DisplayName("using the mock() method")
        class StaticMockMethodTests {
            BasicMockitoDependency dependency;
            BasicMockito sut;

            @BeforeEach
            public void doBeforeEachTest() {
                dependency = mock(BasicMockitoDependency.class);
                when(dependency.doSomethingDelegated()).thenReturn(expectedResult);
                // Manually controlling dependency injection here.
                sut = new BasicMockito(dependency);
                actual = sut.doSomething();
            }

            @Test
            @DisplayName("verify interaction with the dependency")
            void verifyMockInteraction() {
                verify(dependency).doSomethingDelegated();
            }

            @Test
            @DisplayName("verify that the returned value is appropriate")
            void VerifyDirectOutputTest() {
                assertEquals(expectedResult, actual);
            }

        }

        @Nested
        @DisplayName("using the @Mock and @InjectMocks annotations")
        class MockAndInjectMocksAnnotationsTests {

            @Mock
            BasicMockitoDependency dependency;

            /**
             * Let Mockito handle the construction of the SUT and dependency injection for us!
             */
            @InjectMocks
            BasicMockito sut;

            @BeforeEach
            public void doBeforeEachTest() {
                when(dependency.doSomethingDelegated()).thenReturn(expectedResult);
                actual = sut.doSomething();
            }

            @Test
            @DisplayName("verify interaction with the dependency")
            void verifyMockInteraction() {
                verify(dependency).doSomethingDelegated();
            }

            @Test
            @DisplayName("verify that the returned value is appropriate")
            void VerifyDirectOutputTest() {
                assertEquals(expectedResult, actual);
            }
        }
    }

    @Nested
    @DisplayName("demonstrating void method stubbing and interaction verification")
    class VoidMethodStubbing {
        @Mock
        BasicMockitoDependency dependency;

        @InjectMocks
        BasicMockito sut;

        final String expectedEventName = "foobar";

        @BeforeEach
        public void doBeforeEachTest() {
            doNothing().when(dependency).fireEvent(expectedEventName);
            sut.doSomethingAsync(expectedEventName);
        }

        @Test
        @DisplayName("verify fireEvent interaction with the dependency")
        void verifyMockInteraction() {
            verify(dependency).fireEvent(expectedEventName);
        }

    }
}
