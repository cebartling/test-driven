package com.pintailconsultingllc;

import com.pintailconsultingllc.exceptions.ApiException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Covid19Api {

    public static final String HEADER_X_RAPIDAPI_HOST = "x-rapidapi-host";
    public static final String HEADER_X_RAPIDAPI_KEY = "x-rapidapi-key";
    public static final String HTTP_METHOD = "GET";
    public static final String ENV_VAR_RAPID_API_KEY = "RapidApiKey";
    public static final String RAPIDAPI_HOST = "covid-19-data.p.rapidapi.com";

    public HttpResponse<String> dataByCountryCode(String countryCode) {
        String urlString = String.format("https://covid-19-data.p.rapidapi.com/country/code?code=%s", countryCode);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .header(HEADER_X_RAPIDAPI_HOST, RAPIDAPI_HOST)
                .header(HEADER_X_RAPIDAPI_KEY, System.getenv(ENV_VAR_RAPID_API_KEY))
                .method(HTTP_METHOD, HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new ApiException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException(e);
        }
        return response;
    }
}
