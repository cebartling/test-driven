package com.pintailconsultingllc.mockito.examples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Basic Mockito example tests")
public class BasicMockitoTests {

    public static final String EXPECTED_RESULT = "A result";

    @Nested
    @DisplayName("doSomething() method")
    class Tests {

        @Nested
        @DisplayName("using the mock() method")
        class StaticMockMethodTests {

            BasicMockitoDependency dependency;
            BasicMockito sut;

            @BeforeEach
            public void doBeforeEachTest() {
                dependency = mock(BasicMockitoDependency.class);
                when(dependency.doSomethingDelegated()).thenReturn(EXPECTED_RESULT);
                sut = new BasicMockito(dependency);
                sut.doSomething();
            }

            @Test
            @DisplayName("verify interaction with the dependency")
            void verifyMockInteraction() {
                verify(dependency).doSomethingDelegated();
            }
        }

        @Nested
        @DisplayName("using the @Mock and @InjectMocks annotations")
        class MockAndInjectMocksAnnotationsTests {

            @Mock
            BasicMockitoDependency dependency;

            @InjectMocks
            BasicMockito sut;

            @BeforeEach
            public void doBeforeEachTest() {
                when(dependency.doSomethingDelegated()).thenReturn(EXPECTED_RESULT);
                sut.doSomething();
            }

            @Test
            @DisplayName("verify interaction with the dependency")
            void verifyMockInteraction() {
                verify(dependency).doSomethingDelegated();
            }
        }
    }
}
