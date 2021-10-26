package com.pintailconsultingllc.mockito.examples.mockedstatic;

import org.junit.jupiter.api.DisplayName;
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

    @Test
    @DisplayName("demonstration")
    void DemonstrationTest() {
        assertEquals(expectedNonMockedName, MockedStaticMethodDemo.getName());
        try (MockedStatic<MockedStaticMethodDemo> mockedStatic = mockStatic(MockedStaticMethodDemo.class)) {
            mockedStatic.when(MockedStaticMethodDemo::getName).thenReturn(expectedMockName);
            assertEquals(expectedMockName, MockedStaticMethodDemo.getName());
        }
        assertEquals(expectedNonMockedName, MockedStaticMethodDemo.getName());
    }

}
