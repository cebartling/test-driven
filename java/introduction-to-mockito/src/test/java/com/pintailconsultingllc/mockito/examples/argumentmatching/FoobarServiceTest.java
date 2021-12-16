package com.pintailconsultingllc.mockito.examples.argumentmatching;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.pintailconsultingllc.mockito.examples.argumentmatching.FoobarService.URI_EVENTS;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("FoobarService unit tests")
class FoobarServiceTest {

    @Mock
    ExternalApiWebClient externalApiWebClientMock;

    @InjectMocks
    FoobarService service;

    @Nested
    @DisplayName("createEvent method")
    class CreateEventTests {
        static final String EVENT_NAME = "diagnostics-event";
        static final String DATA = "data item";

        @Mock
        Response<String> responseMock;

        @Nested
        @DisplayName("lenient argument matching")
        class LenientArgumentMatchingTests {

            @Nested
            @DisplayName("successful")
            class WhenSuccessfulTests {

                @BeforeEach
                public void doBeforeEachTest() {
                    when(responseMock.getStatusCode()).thenReturn(201);
                    when(externalApiWebClientMock.post(eq(URI_EVENTS), anyString())).thenReturn(responseMock);
                }

                @Test
                @DisplayName("collaboration test: verify ExternalApiWebClient.post invocation")
                void verifyDependencyInvocationTest() throws JsonProcessingException {
                    service.createEvent(EVENT_NAME, DATA);
                    verify(externalApiWebClientMock).post(eq(URI_EVENTS), anyString());
                }

                @Test
                @DisplayName("contract test: verify no ApiException is thrown when response status code is 201")
                void verifyNoApiExceptionThrownTest() {
                    assertDoesNotThrow(() -> service.createEvent(EVENT_NAME, DATA));
                }
            }

            @Nested
            @DisplayName("ApiException thrown")
            class WhenUnsuccessfulTests {

                @BeforeEach
                public void doBeforeEachTest() {
                    when(responseMock.getStatusCode()).thenReturn(400);
                    when(externalApiWebClientMock.post(eq(URI_EVENTS), anyString())).thenReturn(responseMock);
                }

                @Test
                @DisplayName("contract test: verify ApiException is thrown when response status code is not 201")
                void verifyNoApiExceptionThrownTest() {
                    assertThrows(ApiException.class, () -> {
                        service.createEvent(EVENT_NAME, DATA);
                    });
                }
            }
        }
    }
}
