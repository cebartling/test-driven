package com.pintailconsultingllc.webflux.demo.webclients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.pintailconsultingllc.webflux.demo.TestSupport;
import com.pintailconsultingllc.webflux.demo.dtos.FinanceInformationDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.core.codec.DecodingException;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.TimeoutException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@Tag(TestSupport.UNIT_TEST)
@DisplayName("FinanceWebClient unit tests")
class FinanceWebClientTests {

    static final String HEADER_CONTENT_TYPE = "Content-Type";
    static final String MEDIA_TYPE_APPLICATION_JSON = "application/json";
    static final int WIRE_MOCK_PORT = 9000;
    static final String STATE_RETRY_SUCCESS = "retry_success";
    static final String STATE_RETRY_FAILURE = "retry_failure";
    static final String STATE_STARTED = Scenario.STARTED;
    static final String RETRY_SCENARIO = "Retry Scenario";
    static WireMockServer wireMockServer;

    ObjectMapper objectMapper;
    FinanceWebClient financeWebClient;
    final String expectedEmployeeId = "234568";
    final Long timeoutInMilliseconds = 800L;

    @BeforeEach
    public void doBeforeEachTest() {
        this.financeWebClient = new FinanceWebClient(WebClient.builder());
        String financeBaseUrl = "http://localhost:9000/api/employees";
        ReflectionTestUtils.setField(this.financeWebClient, "financeBaseUrl", financeBaseUrl);
        ReflectionTestUtils.setField(this.financeWebClient, "timeoutInMilliseconds", timeoutInMilliseconds);
        this.objectMapper = new ObjectMapper();
    }

    @BeforeAll
    static void doBeforeAllTestsRun() {
        wireMockServer = new WireMockServer(options()
                .port(WIRE_MOCK_PORT)
                .notifier(new ConsoleNotifier(true)));
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
        UrlPattern urlPattern;

        @BeforeEach
        public void doBeforeEachTest() {
            final String url = String.format("%s/%s", "/api/employees", expectedEmployeeId);
            urlPattern = urlEqualTo(url);
        }

        @Nested
        @DisplayName("successful pathways")
        class SuccessPathwaysTests {
            @Nested
            @DisplayName("Mono emitting DTO returned, no retries")
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
                    WireMock.resetAllRequests();
                    ResponseDefinitionBuilder responseDefBuilder = aResponse()
                            .withStatus(200)
                            .withBody(jsonBody)
                            .withHeader(HEADER_CONTENT_TYPE, MEDIA_TYPE_APPLICATION_JSON);
                    MappingBuilder mappingBuilder = get(urlPattern);
                    stubFor(mappingBuilder.willReturn(responseDefBuilder));

                    Mono<FinanceInformationDTO> resultMono = financeWebClient.getFinanceInformationByEmployeeId(expectedEmployeeId);
                    StepVerifier.create(resultMono)
                            .consumeNextWith(consumer -> actual = consumer)
                            .verifyComplete();
                }

                @Test
                @DisplayName("should invoke GET /api/employees/{employeeId}")
                void verifyWireMockInvocationTest() {
                    WireMock.verify(getRequestedFor(urlPattern));
                }

                @Test
                @DisplayName("should return a valid FinanceInformationDTO instance from the external API endpoint")
                void verifyDirectOutputTest() {
                    assertEquals(expected, actual);
                }
            }

            @Nested
            @DisplayName("Mono emitting DTO returned after two unsuccessful HTTP invocation retries")
            class SuccessPathwayUsingRetryLogicTests {
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

                    WireMock.resetAllRequests();
                    ResponseDefinitionBuilder serviceErrorResponseDefBuilder = aResponse()
                            .withStatus(503)
                            .withBody(jsonBody)
                            .withHeader(HEADER_CONTENT_TYPE, MEDIA_TYPE_APPLICATION_JSON);
                    ResponseDefinitionBuilder successResponseDefBuilder = aResponse()
                            .withStatus(200)
                            .withBody(jsonBody)
                            .withHeader(HEADER_CONTENT_TYPE, MEDIA_TYPE_APPLICATION_JSON);

                    stubFor(get(urlPattern)
                            .inScenario(RETRY_SCENARIO)
                            .whenScenarioStateIs(STATE_STARTED)
                            .willReturn(serviceErrorResponseDefBuilder)
                            .willSetStateTo(STATE_RETRY_FAILURE));
                    stubFor(get(urlPattern)
                            .inScenario(RETRY_SCENARIO)
                            .whenScenarioStateIs(STATE_RETRY_FAILURE)
                            .willReturn(serviceErrorResponseDefBuilder)
                            .willSetStateTo(STATE_RETRY_SUCCESS));
                    stubFor(get(urlPattern)
                            .inScenario(RETRY_SCENARIO)
                            .whenScenarioStateIs(STATE_RETRY_SUCCESS)
                            .willReturn(successResponseDefBuilder));

                    Mono<FinanceInformationDTO> resultMono = financeWebClient.getFinanceInformationByEmployeeId(expectedEmployeeId);
                    StepVerifier.create(resultMono)
                            .consumeNextWith(consumer -> actual = consumer)
                            .verifyComplete();
                }

                @Test
                @DisplayName("should invoke GET /api/employees/{employeeId}")
                void verifyWireMockInvocationTest() {
                    WireMock.verify(getRequestedFor(urlPattern));
                }

                @Test
                @DisplayName("should return a valid FinanceInformationDTO instance from the external API endpoint")
                void verifyDirectOutputTest() {
                    assertEquals(expected, actual);
                }
            }
        }

        @Nested
        @DisplayName("failure pathways")
        class FailurePathwayTests {
            @Nested
            @DisplayName("when response body is unparse-able")
            class FailurePathwayResponseUnparseableTests {
                Throwable actualError;

                @BeforeEach
                public void doBeforeEachTest() {
                    WireMock.resetAllRequests();
                    ResponseDefinitionBuilder responseDefBuilder = aResponse()
                            .withStatus(200)
                            .withBody("djhfajdhafhdluhfl")
                            .withHeader(HEADER_CONTENT_TYPE, MEDIA_TYPE_APPLICATION_JSON);
                    MappingBuilder mappingBuilder = get(urlPattern);
                    stubFor(mappingBuilder.willReturn(responseDefBuilder));

                    Mono<FinanceInformationDTO> resultMono = financeWebClient.getFinanceInformationByEmployeeId(expectedEmployeeId);
                    StepVerifier.create(resultMono)
                            .consumeErrorWith(error -> actualError = error)
                            .verify();
                }

                @Test
                @DisplayName("should invoke GET /api/employees/{employeeId}")
                void verifyWireMockInvocationTest() {
                    WireMock.verify(getRequestedFor(urlPattern));
                }

                @Test
                @DisplayName("should return an exception for unparse-able employee financial info returned in response")
                void verifyDirectOutputTest() {
                    assertInstanceOf(DecodingException.class, actualError);
                }
            }

            @Nested
            @DisplayName("when 4xx HTTP status returned")
            class FailurePathway4xxStatusCodeTests {
                private Throwable actualError;

                @BeforeEach
                public void doBeforeEachTest() {
                    WireMock.resetAllRequests();
                    ResponseDefinitionBuilder clientErrorResponseDefBuilder = aResponse()
                            .withStatus(403)
                            .withHeader(HEADER_CONTENT_TYPE, MEDIA_TYPE_APPLICATION_JSON);
                    stubFor(get(urlPattern).willReturn(clientErrorResponseDefBuilder));

                    Mono<FinanceInformationDTO> resultMono = financeWebClient.getFinanceInformationByEmployeeId(expectedEmployeeId);
                    StepVerifier.create(resultMono)
                            .consumeErrorWith(error -> actualError = error)
                            .verify();
                }

                @Test
                @DisplayName("should invoke GET /api/employees/{employeeId}")
                void verifyWireMockInvocationTest() {
                    WireMock.verify(getRequestedFor(urlPattern));
                }

                @Test
                @DisplayName("should return an exception for invalid request for employee financial information")
                void verifyDirectOutputTest() {
                    assertInstanceOf(WebClientException.class, actualError);
                    assertEquals("Invalid request for employee financial information: HTTP status code 403",
                            actualError.getMessage());
                }
            }

            @Nested
            @DisplayName("when 5xx HTTP status returned and exhausting all retry attempts")
            class FailurePathway5xxStatusCodeTests {
                private Throwable actualError;

                @BeforeEach
                public void doBeforeEachTest() {
                    WireMock.resetAllRequests();
                    ResponseDefinitionBuilder serviceErrorResponseDefBuilder = aResponse()
                            .withStatus(503)
                            .withHeader(HEADER_CONTENT_TYPE, MEDIA_TYPE_APPLICATION_JSON);
                    stubFor(get(urlPattern).willReturn(serviceErrorResponseDefBuilder));

                    Mono<FinanceInformationDTO> resultMono = financeWebClient.getFinanceInformationByEmployeeId(expectedEmployeeId);
                    StepVerifier.create(resultMono)
                            .consumeErrorWith(error -> actualError = error)
                            .verify();
                }

                @Test
                @DisplayName("should invoke GET /api/employees/{employeeId}")
                void verifyWireMockInvocationTest() {
                    WireMock.verify(4, getRequestedFor(urlPattern));
                }

                @Test
                @DisplayName("should return an exception signaling the exhaustion of retry attempts")
                void verifyDirectOutputTest() {
                    // The reactor.core.Exceptions.RetryExhaustedException is not a public type, so you can't actually
                    // refer to it in our code to do type checking. Crazy!
                    assertEquals("reactor.core.Exceptions.RetryExhaustedException", actualError.getClass().getCanonicalName());
                    assertEquals("Retries exhausted: 3/3", actualError.getMessage());
                }
            }

            @Nested
            @DisplayName("when requests time out")
            class RequestTimeoutTests {
                private Throwable actualError;

                @BeforeEach
                public void doBeforeEachTest() throws JsonProcessingException {
                    WireMock.resetAllRequests();
                    FinanceInformationDTO financeInformationDTO = FinanceInformationDTO.builder()
                            .employeeId(expectedEmployeeId)
                            .federalIncomeTaxesYearToDateInCents(456787)
                            .stateIncomeTaxesYearToDateInCents(125677)
                            .build();
                    final String jsonBody = objectMapper.writeValueAsString(financeInformationDTO);
                    WireMock.resetAllRequests();
                    final Integer delayInMilliseconds = Math.toIntExact((timeoutInMilliseconds + 200L));
                    ResponseDefinitionBuilder responseDefBuilder = aResponse()
                            .withFixedDelay(delayInMilliseconds)
                            .withStatus(200)
                            .withBody(jsonBody)
                            .withHeader(HEADER_CONTENT_TYPE, MEDIA_TYPE_APPLICATION_JSON);
                    stubFor(get(urlPattern).willReturn(responseDefBuilder));

                    Mono<FinanceInformationDTO> resultMono = financeWebClient.getFinanceInformationByEmployeeId(expectedEmployeeId);
                    StepVerifier.create(resultMono)
                            .consumeErrorWith(error -> actualError = error)
                            .verify();
                }

                @Test
                @DisplayName("should invoke GET /api/employees/{employeeId} exactly once")
                void verifyWireMockInvocationTest() {
                    WireMock.verify(1, getRequestedFor(urlPattern));
                }

                @Test
                @DisplayName("should error out with a timeout exception")
                void verifyDirectOutputTest() {
                    assertInstanceOf(TimeoutException.class, actualError);
                }
            }

        }
    }
}