package com.pintailconsultingllc.mockito.examples.argumentmatching;

import com.fasterxml.jackson.core.JsonProcessingException;

public class FoobarService {
    public static final String URI_EVENTS = "/api/events";

    private final ExternalApiWebClient externalApiWebClient;

    public FoobarService(final ExternalApiWebClient externalApiWebClient) {
        this.externalApiWebClient = externalApiWebClient;
    }

    public void createEvent(String eventType, String data) {
        final Response<String> response = externalApiWebClient.post(URI_EVENTS, "");
        if (response.getStatusCode() != 201) {
            throw new ApiException(String.format("Unable to create new event of type: %s", eventType));
        }
    }
}
