package com.pintailconsultingllc.webflux.demo.webclients;

import com.pintailconsultingllc.webflux.demo.dtos.FinanceInformationDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class FinanceWebClient {

    public static final String APPLICATION_JSON = "application/json";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_ACCEPTS = "Accepts";
    private final WebClient webClient;

    @Value("webclients.finance.baseUrl")
    private String financeBaseUrl;

    public FinanceWebClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(financeBaseUrl).build();
    }

    /**
     * Retrieve employee financial information from external API.
     *
     * @param employeeId A string identifier for the specific employee in the external API system.
     * @return A Mono emitting a single FinanceInformationDTO object instance.
     */
    public Mono<FinanceInformationDTO> getFinanceInformationByEmployeeId(String employeeId) {
        return webClient
                .get()
                .uri(createUri(employeeId))
                .header(HEADER_CONTENT_TYPE, APPLICATION_JSON)
                .header(HEADER_ACCEPTS, APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> Mono.error(new WebClientException("Invalid request for employee financial information.")))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> Mono.error(new WebClientException("Service error occurred.")))
                .bodyToMono(FinanceInformationDTO.class)
                .onErrorResume(throwable -> {
                    if (throwable instanceof WebClientException) {
                        return Mono.error(throwable);
                    } else {
                        return Mono.error(new WebClientException("Unable to parse the API response."));
                    }
                });
    }

    private URI createUri(String employeeId) {
        final URI uri;
        final String urlString = String.format("%s/%s", financeBaseUrl, employeeId);
        try {
            uri = new URI(urlString);
        } catch (URISyntaxException e) {
            throw new WebClientException(String.format("Unable to create employee financial information URL: %s", urlString), e);
        }
        return uri;
    }
}
