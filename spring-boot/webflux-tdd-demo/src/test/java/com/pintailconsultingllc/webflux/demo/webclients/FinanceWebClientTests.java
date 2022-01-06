package com.pintailconsultingllc.webflux.demo.webclients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.pintailconsultingllc.webflux.demo.TestSupport;
import com.pintailconsultingllc.webflux.demo.dtos.FinanceInformationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.notFound;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.serviceUnavailable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@Tag(TestSupport.UNIT_TEST)
@WireMockTest(httpPort = 9000)
@DisplayName("FinanceWebClient unit tests")
class FinanceWebClientTests {

    private WireMockRuntimeInfo wireMockRuntimeInfo;
    final private String financeBaseUrl = "http://localhost:9000/api/employees";
    private ObjectMapper objectMapper;
    private FinanceWebClient financeWebClient;

    @BeforeEach
    public void doBeforeEachTest(WireMockRuntimeInfo wireMockRuntimeInfo) {
        this.financeWebClient = new FinanceWebClient(WebClient.builder());
        ReflectionTestUtils.setField(this.financeWebClient, "financeBaseUrl", financeBaseUrl);
        this.wireMockRuntimeInfo = wireMockRuntimeInfo;
        this.objectMapper = new ObjectMapper();
    }

    @Nested
    @DisplayName("getFinanceInformationByEmployeeId method")
    class GetFinanceInformationByEmployeeIdTests {

        @Nested
        @DisplayName("successful pathway: Mono emitting DTO returned")
        class SuccessPathwayTests {
            final private String expectedEmployeeId = "234568";
            FinanceInformationDTO actual;
            FinanceInformationDTO expected;
            private MappingBuilder mappingBuilder;

            @BeforeEach
            public void doBeforeEachTest() throws JsonProcessingException {
                expected = FinanceInformationDTO.builder()
                        .employeeId(expectedEmployeeId)
                        .federalIncomeTaxesYearToDateInCents(456787)
                        .stateIncomeTaxesYearToDateInCents(125677)
                        .build();
                mappingBuilder = get(String.format("%s/%s", "/api/employees", expectedEmployeeId));
                final String jsonBody = objectMapper.writeValueAsString(expected);
                WireMock wireMock = wireMockRuntimeInfo.getWireMock();
                wireMock.register(mappingBuilder.willReturn(okJson(jsonBody)));
                Mono<FinanceInformationDTO> resultMono = financeWebClient.getFinanceInformationByEmployeeId(expectedEmployeeId);
                StepVerifier.create(resultMono)
                        .consumeNextWith(consumer -> actual = consumer)
                        .verifyComplete();
            }

            @Test
            @DisplayName("should return a valid FinanceInformationDTO instance from the external API endpoint")
            void verifyDirectOutputTest() {
                assertEquals(expected, actual);
            }
        }

        @Nested
        @DisplayName("failure pathway: 4xx HTTP status returned")
        class FailurePathway4xxStatusCodeTests {
            final private String expectedEmployeeId = "2342768";
            private Throwable actualError;
            private MappingBuilder mappingBuilder;

            @BeforeEach
            public void doBeforeEachTest() throws JsonProcessingException {
                mappingBuilder = get(String.format("%s/%s", "/api/employees", expectedEmployeeId));
                WireMock wireMock = wireMockRuntimeInfo.getWireMock();
                wireMock.register(mappingBuilder.willReturn(notFound()));
                Mono<FinanceInformationDTO> resultMono = financeWebClient.getFinanceInformationByEmployeeId(expectedEmployeeId);
                StepVerifier.create(resultMono)
                        .consumeErrorWith(error -> actualError = error)
                        .verify();
            }

            @Test
            @DisplayName("should return an exception for invalid request for employee financial information")
            void verifyDirectOutputTest() {
                assertInstanceOf(WebClientException.class, actualError);
                assertEquals("Invalid request for employee financial information.", actualError.getMessage());
            }
        }

        @Nested
        @DisplayName("failure pathway: 5xx HTTP status returned")
        class FailurePathway5xxStatusCodeTests {
            final private String expectedEmployeeId = "2342755";
            private Throwable actualError;
            private MappingBuilder mappingBuilder;

            @BeforeEach
            public void doBeforeEachTest() throws JsonProcessingException {
                mappingBuilder = get(String.format("%s/%s", "/api/employees", expectedEmployeeId));
                WireMock wireMock = wireMockRuntimeInfo.getWireMock();
                wireMock.register(mappingBuilder.willReturn(serviceUnavailable()));
                Mono<FinanceInformationDTO> resultMono = financeWebClient.getFinanceInformationByEmployeeId(expectedEmployeeId);
                StepVerifier.create(resultMono)
                        .consumeErrorWith(error -> actualError = error)
                        .verify();
            }

            @Test
            @DisplayName("should return an exception for a service error")
            void verifyDirectOutputTest() {
                assertInstanceOf(WebClientException.class, actualError);
                assertEquals("Service error occurred.", actualError.getMessage());
            }
        }
    }
}