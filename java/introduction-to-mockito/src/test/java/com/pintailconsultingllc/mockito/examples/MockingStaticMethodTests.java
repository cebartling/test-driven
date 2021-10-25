package com.pintailconsultingllc.mockito.examples;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
@DisplayName("Mockito support for mocking static methods on classes")
class MockingStaticMethodTests {

    final String expectedNonMockedName = "foobar";
    final String expectedMockName = "barfoo";

    @Test
    @DisplayName("demonstration")
    void DemonstrationTest() {
        assertEquals(expectedNonMockedName, MockingStaticMethodDemo.getName());
        try (MockedStatic<MockingStaticMethodDemo> mockedStatic = mockStatic(MockingStaticMethodDemo.class)) {
            mockedStatic.when(MockingStaticMethodDemo::getName).thenReturn(expectedMockName);
            assertEquals(expectedMockName, MockingStaticMethodDemo.getName());
        }
        assertEquals(expectedNonMockedName, MockingStaticMethodDemo.getName());
    }

}
