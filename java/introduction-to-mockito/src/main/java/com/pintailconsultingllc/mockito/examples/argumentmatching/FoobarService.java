package com.pintailconsultingllc.mockito.examples.argumentmatching;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class FoobarService {
    public static final String URI_EVENTS = "/api/events";

    private final ExternalApiWebClient externalApiWebClient;

    public FoobarService(final ExternalApiWebClient externalApiWebClient) {
        this.externalApiWebClient = externalApiWebClient;
    }

    public void createEvent(String eventType, String data) throws JsonProcessingException {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        final EventDTO eventDTO = EventDTO.builder()
                .eventType(eventType)
                .eventData(data)
                .eventTimestamp(ZonedDateTime.now().format(formatter))
                .build();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonString = objectMapper.writeValueAsString(eventDTO);
        final Response<String> response = externalApiWebClient.post(URI_EVENTS, jsonString);
        if (response.getStatusCode() != 201) {
            throw new ApiException(String.format("Unable to create new event of type: %s", eventType));
        }
    }
}
