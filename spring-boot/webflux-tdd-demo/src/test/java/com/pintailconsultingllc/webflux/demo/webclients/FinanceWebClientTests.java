package com.pintailconsultingllc.webflux.demo.webclients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.pintailconsultingllc.webflux.demo.TestSupport;
import com.pintailconsultingllc.webflux.demo.dtos.FinanceInformationDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@Tag(TestSupport.UNIT_TEST)
@DisplayName("FinanceWebClient unit tests")
class FinanceWebClientTests {

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String MEDIA_TYPE_APPLICATION_JSON = "application/json";
    public static final int WIRE_MOCK_PORT = 9000;
    private static WireMockServer wireMockServer;

    final private String financeBaseUrl = "http://localhost:9000/api/employees";
    private ObjectMapper objectMapper;
    private FinanceWebClient financeWebClient;
    final private String expectedEmployeeId = "234568";


    @BeforeEach
    public void doBeforeEachTest() {
        this.financeWebClient = new FinanceWebClient(WebClient.builder());
        ReflectionTestUtils.setField(this.financeWebClient, "financeBaseUrl", financeBaseUrl);
        this.objectMapper = new ObjectMapper();
    }

    @BeforeAll
    static void doBeforeAllTestsRun() {
        wireMockServer = new WireMockServer(WIRE_MOCK_PORT);
        wireMockServer.start();
        configureFor("localhost", WIRE_MOCK_PORT);
    }

    @AfterAll
    static void doAfterAllTestsRun() {
        wireMockServer.stop();
    }

    @Nested
    @DisplayName("getFinanceInformationByEmployeeId method")
    class GetFinanceInformationByEmployeeIdTests {

        @Nested
        @DisplayName("successful pathway: Mono emitting DTO returned")
        class SuccessPathwayTests {
            FinanceInformationDTO actual;
            FinanceInformationDTO expected;

            @BeforeEach
            public void doBeforeEachTest() throws JsonProcessingException {
                expected = FinanceInformationDTO.builder()
                        .employeeId(expectedEmployeeId)
                        .federalIncomeTaxesYearToDateInCents(456787)
                        .stateIncomeTaxesYearToDateInCents(125677)
                        .build();
                final String jsonBody = objectMapper.writeValueAsString(expected);
                String url = String.format("%s/%s", "/api/employees", expectedEmployeeId);
                ResponseDefinitionBuilder responseDefBuilder = aResponse()
                        .withStatus(200)
                        .withBody(jsonBody)
                        .withHeader(HEADER_CONTENT_TYPE, MEDIA_TYPE_APPLICATION_JSON);
                MappingBuilder mappingBuilder = get(urlEqualTo(url));
                stubFor(mappingBuilder.willReturn(responseDefBuilder));

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
        @DisplayName("failure pathway: response body is unparse-able")
        class FailurePathwayResponseUnparseableTests {
            Throwable actualError;

            @BeforeEach
            public void doBeforeEachTest() throws JsonProcessingException {
                String url = String.format("%s/%s", "/api/employees", expectedEmployeeId);
                ResponseDefinitionBuilder responseDefBuilder = aResponse()
                        .withStatus(200)
                        .withBody("djhfajdhafhdluhfl")
                        .withHeader(HEADER_CONTENT_TYPE, MEDIA_TYPE_APPLICATION_JSON);
                MappingBuilder mappingBuilder = get(urlEqualTo(url));
                stubFor(mappingBuilder.willReturn(responseDefBuilder));

                Mono<FinanceInformationDTO> resultMono = financeWebClient.getFinanceInformationByEmployeeId(expectedEmployeeId);
                StepVerifier.create(resultMono)
                        .consumeErrorWith(error -> actualError = error)
                        .verify();
            }

            @Test
            @DisplayName("should return an exception for unparse-able employee financial info returned in response")
            void verifyDirectOutputTest() {
                assertInstanceOf(WebClientException.class, actualError);
                assertEquals("Unable to parse the API response.", actualError.getMessage());
            }
        }

        @Nested
        @DisplayName("failure pathway: 4xx HTTP status returned")
        class FailurePathway4xxStatusCodeTests {
            final private String expectedEmployeeId = "2342768";
            private Throwable actualError;

            @BeforeEach
            public void doBeforeEachTest() {
                String url = String.format("%s/%s", "/api/employees", expectedEmployeeId);
                ResponseDefinitionBuilder responseDefBuilder = aResponse()
                        .withStatus(403)
                        .withHeader(HEADER_CONTENT_TYPE, MEDIA_TYPE_APPLICATION_JSON);
                stubFor(get(urlEqualTo(url)).willReturn(responseDefBuilder));

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

            @BeforeEach
            public void doBeforeEachTest() {
                String url = String.format("%s/%s", "/api/employees", expectedEmployeeId);
                ResponseDefinitionBuilder responseDefBuilder = aResponse()
                        .withStatus(503)
                        .withHeader(HEADER_CONTENT_TYPE, MEDIA_TYPE_APPLICATION_JSON);
                stubFor(get(urlEqualTo(url)).willReturn(responseDefBuilder));
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