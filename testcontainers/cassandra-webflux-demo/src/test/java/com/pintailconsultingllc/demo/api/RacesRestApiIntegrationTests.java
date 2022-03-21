package com.pintailconsultingllc.demo.api;

import com.datastax.driver.core.utils.UUIDs;
import com.pintailconsultingllc.demo.CassandraContainerInitializer;
import com.pintailconsultingllc.demo.TestSupport;
import com.pintailconsultingllc.demo.dtos.RaceDTO;
import com.pintailconsultingllc.demo.repositories.RaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This integration test demonstrates how to exercise a REST API endpoint using
 * {@link org.springframework.boot.test.web.client.TestRestTemplate TestRestTemplate}. The test is annotated with
 * {@link org.springframework.boot.test.context.SpringBootTest @SpringBootTest} annotation, using the random
 * port configuration for the web environment. That will start the Spring Boot app and bootstrap the application
 * context.
 * <p>
 * The {@link com.pintailconsultingllc.demo.CassandraContainerInitializer} component manages the Apache Cassandra
 * database via Testcontainers and Docker. This initializer is then configured for the test suite class via the
 * {@link org.springframework.test.context.ContextConfiguration @ContextConfiguration} annotation. Each test is
 * responsible for seeding data into the database for successful execution of the test. Database repository
 * components are used for this data seeding task. Spring will autowire repository components into your tests.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {CassandraContainerInitializer.class})
//@EnableAutoConfiguration(exclude = ReactiveSecurityAutoConfiguration.class)
@DisplayName("/api/races REST API integration tests")
@Tag(TestSupport.INTEGRATION_TEST)
public class RacesRestApiIntegrationTests {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    RaceRepository raceRepository;

    @Nested
    @DisplayName("POST /api/races")
    class CreateNewRaceTests {
        ResponseEntity<Void> responseEntity;
        RaceDTO expectedRaceDTO;
        final UUID expectedUuid = UUIDs.timeBased();
        final String expectedName = "Fat Bike Birkie";
        final String expectedDescription = "Some description";

        @BeforeEach
        public void doBeforeEachTest() throws URISyntaxException {
            raceRepository.deleteAll();
            final URI url = new URI(String.format("http://localhost:%d/api/races", randomServerPort));
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            expectedRaceDTO = RaceDTO.builder()
                    .id(expectedUuid)
                    .name(expectedName)
                    .description(expectedDescription)
                    .build();
            HttpEntity<RaceDTO> request = new HttpEntity<>(expectedRaceDTO, headers);

            // Execute the system under test (SUT) via TestRestTemplate component
            responseEntity = testRestTemplate.postForEntity(url, request, Void.class);
        }

        @Test
        @DisplayName("should return a response entity with a status of 201 (Created)")
        void verifyResponseEntityTest() {
            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("should return the URI for the newly created resource in the Location header")
        void verifyLocationHeader() {
            final URI location = responseEntity.getHeaders().getLocation();
            String expectedLocation = String.format("/api/races/%s", expectedUuid);
            assertEquals(expectedLocation, location.toString());
        }
    }
}
