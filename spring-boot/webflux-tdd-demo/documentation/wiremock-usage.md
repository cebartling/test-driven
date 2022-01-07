# WireMock usage

[WebClient](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-client) is an interface representing the main entry point for performing web requests. It was created as part of the Spring Web Reactive module and is a reactive, non-blocking solution that works over the HTTP/1.1 protocol. When attempting to unit test WebClient usage, it is tempting to try and mock out all the different objects that are involved in an HTTP invocation from this object instance. Unfortunately, that approach is not favored. Instead, your unit tests for WebClient usage should use [WireMock](http://wiremock.org/) to provide an in-process mock HTTP endpoint for the WebClient to connect to. Yeah, it definitely feels like an integration test, but it's much cleaner than the mock object approach with something like Mockito.

## Example

This first code sample shows a JUnit test suite class that maintains a single `WireMockServer` instance for the lifetime of the test suite. Therefore, we use `@BeforeAll` and `@AfterAll` annotations on `static` methods to manage the single instance maintained in a `static` variable on the class object.


```java
class MyWebClientTests {

    private static final int WIRE_MOCK_PORT = 9000;
    private static WireMockServer wireMockServer;

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

    // The rest of the JUnit test suite class omitted for clarity
}
```

Within a test, the `WireMockServer` can be controlled with the `stubFor`, `aResponse`, `get` and `urlEqualTo` methods, statically imported from the `com.github.tomakehurst.wiremock.client.WireMock` class. In this example, an API endpoint for the URI `/api/employees/234568` is created and the response status, headers, and body are stubbed from the test. The `ResponseDefinitionBuilder` and `MappingBuilder` types are from WireMock and are required for building mock endpoints.


```java
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
```

