package com.pintailconsultingllc.mockito.examples.argumentmatching;

public interface ExternalApiWebClient {
    Response<String> post(String uri, String jsonEntityBody);
}
