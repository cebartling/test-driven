package com.pintailconsultingllc.mockito.examples.mockedstatic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

/**
 * This test suite demonstrates using Mockito's MockedStatic and mockStatic facilities for intercepting
 * static method invocations and supplying mock implementations of these methods.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Mockito support for mocking static methods on classes")
class MockedStaticMethodDemoTests {

    final String expectedNonMockedName = MockedStaticMethodDemo.NAME;
    final String expectedMockName = "barfoo";

    @Nested
    @DisplayName("using a try with resources block")
    class TryWithResourcesTests {
        @Test
        @DisplayName("demonstration")
        void DemonstrationTest() {
            assertEquals(expectedNonMockedName, MockedStaticMethodDemo.getName());
            try (MockedStatic<MockedStaticMethodDemo> mockedStatic = mockStatic(MockedStaticMethodDemo.class)) {
                mockedStatic.when(MockedStaticMethodDemo::getName).thenReturn(expectedMockName);
                final String actual = MockedStaticMethodDemo.getName();
                assertEquals(expectedMockName, actual);
                mockedStatic.verify(MockedStaticMethodDemo::getName);
            }
            assertEquals(expectedNonMockedName, MockedStaticMethodDemo.getName());
        }
    }

    @Nested
    @DisplayName("using beforeEach and afterEach to control static method mocking")
    class UsingJunit5LifecycleMethodsTests {
        MockedStatic<MockedStaticMethodDemo> mockedStatic;
        String actual;

        @BeforeEach
        public void doBeforeEachTest() {
            mockedStatic = mockStatic(MockedStaticMethodDemo.class);
            mockedStatic.when(MockedStaticMethodDemo::getName).thenReturn(expectedMockName);
            actual = MockedStaticMethodDemo.getName();
        }

        @AfterEach
        public void doAfterEachTest() {
            if (mockedStatic != null && !mockedStatic.isClosed()) {
                mockedStatic.close();
            }
        }

        @Test
        @DisplayName("should return barfoo")
        void verifyResultTest() {
            assertEquals(expectedMockName, actual);
        }

        @Test
        @DisplayName("verify interaction")
        void verifyInteractionTest() {
            mockedStatic.verify(MockedStaticMethodDemo::getName);
        }
    }
}
