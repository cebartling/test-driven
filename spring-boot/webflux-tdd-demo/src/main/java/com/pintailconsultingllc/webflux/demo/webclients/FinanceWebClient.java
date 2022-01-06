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

    private final WebClient webClient;

    @Value("webclients.finance.baseUrl")
    private String financeBaseUrl;

    public FinanceWebClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(financeBaseUrl).build();
    }

    public Mono<FinanceInformationDTO> getFinanceInformationByEmployeeId(String employeeId) {
        final URI uri;
        final String urlString = String.format("%s/%s", financeBaseUrl, employeeId);
        try {
            uri = new URI(urlString);
        } catch (URISyntaxException e) {
            throw new WebClientException(String.format("Unable to create employee financial information URL: %s", urlString), e);
        }

        return webClient
                .get()
                .uri(uri)
                .header("Content-Type", "application/json")
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> Mono.error(new WebClientException("Invalid request for employee financial information.")))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> Mono.error(new WebClientException("Service error occurred.")))
                .bodyToMono(FinanceInformationDTO.class);
    }
}
