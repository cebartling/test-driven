package com.pintailconsultingllc.mockito.examples.argumentmatching;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.pintailconsultingllc.mockito.examples.argumentmatching.FoobarService.URI_EVENTS;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
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

        @Nested
        @DisplayName("strict argument matching")
        class StrictArgumentMatchingTests {
            MockedStatic<ZonedDateTime> zonedDateTimeMockedStatic;
            ZonedDateTime eventTimestamp;
            DateTimeFormatter formatter;
            String jsonString;

            @BeforeEach
            public void doBeforeEachTest() throws JsonProcessingException {
                eventTimestamp = ZonedDateTime.now();
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
                final EventDTO eventDTO = EventDTO.builder()
                        .eventType(EVENT_NAME)
                        .eventData(DATA)
                        .eventTimestamp(eventTimestamp.format(formatter))
                        .build();
                final ObjectMapper objectMapper = new ObjectMapper();
                jsonString = objectMapper.writeValueAsString(eventDTO);
                zonedDateTimeMockedStatic = mockStatic(ZonedDateTime.class);
                zonedDateTimeMockedStatic.when(ZonedDateTime::now).thenReturn(eventTimestamp);
                when(externalApiWebClientMock.post(URI_EVENTS, jsonString)).thenReturn(responseMock);
            }

            @AfterEach
            public void doAfterEachTest() {
                if (zonedDateTimeMockedStatic != null && !zonedDateTimeMockedStatic.isClosed()) {
                    zonedDateTimeMockedStatic.close();
                }
            }

            @Nested
            @DisplayName("successful")
            class WhenSuccessfulTests {

                @BeforeEach
                public void doBeforeEachTest() {
                    when(responseMock.getStatusCode()).thenReturn(201);
                }

                @Test
                @DisplayName("collaboration test: verify ExternalApiWebClient.post invocation")
                void verifyDependencyInvocationTest() throws JsonProcessingException {
                    service.createEvent(EVENT_NAME, DATA);
                    verify(externalApiWebClientMock).post(URI_EVENTS, jsonString);
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
                MockedStatic<ZonedDateTime> zonedDateTimeMockedStatic;

                @BeforeEach
                public void doBeforeEachTest() {
                    when(responseMock.getStatusCode()).thenReturn(400);
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
