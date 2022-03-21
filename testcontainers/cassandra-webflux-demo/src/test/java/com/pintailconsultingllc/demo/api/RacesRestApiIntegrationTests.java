package com.pintailconsultingllc.demo.api;

import com.datastax.driver.core.utils.UUIDs;
import com.pintailconsultingllc.demo.CassandraContainerInitializer;
import com.pintailconsultingllc.demo.TestSupport;
import com.pintailconsultingllc.demo.dtos.RaceDTO;
import com.pintailconsultingllc.demo.entities.Race;
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
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

    @Autowired
    ReactiveCassandraTemplate reactiveCassandraTemplate;

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
            var truncateMono = reactiveCassandraTemplate.truncate(Race.class);
            StepVerifier.create(truncateMono).verifyComplete();
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
            responseEntity = testRestTemplate.exchange(url, HttpMethod.POST, request, Void.class);
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

        @Test
        @DisplayName("should not return a resource representation in the response entity-body")
        void verifyNoBodyTest() {
            assertFalse(responseEntity.hasBody());
        }

        @Test
        @DisplayName("should create a new race entity in the Cassandra database")
        void verifyDatabaseTest() {
            final Mono<Race> raceMono = raceRepository.findById(expectedUuid);
            StepVerifier.create(raceMono)
                    .consumeNextWith(x -> {
                        assertEquals(expectedUuid, x.getId());
                        assertEquals(expectedName, x.getName());
                        assertEquals(expectedDescription, x.getDescription());
                    })
                    .verifyComplete();
        }

    }

    @Nested
    @DisplayName("PUT /api/races/{id}")
    class UpdateExistingRaceTests {
        ResponseEntity<Void> responseEntity;
        RaceDTO expectedRaceDTO;
        final UUID expectedUuid = UUIDs.timeBased();
        final String expectedName = "Fat Bike Birkie";
        final String expectedDescription = "Some description";

        @BeforeEach
        public void doBeforeEachTest() throws URISyntaxException {
            final Race seededRace = Race.builder()
                    .id(expectedUuid)
                    .name("Seeley Pass Big Fat")
                    .description("Initial description")
                    .build();
            var truncateAndInsert = reactiveCassandraTemplate
                    .truncate(Race.class)
                    .thenMany(Flux.just(seededRace))
                    .flatMap(reactiveCassandraTemplate::insert);
            StepVerifier.create(truncateAndInsert).expectNextCount(1).verifyComplete();
            final URI url = new URI(String.format("http://localhost:%d/api/races/%s", randomServerPort, expectedUuid));
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            expectedRaceDTO = RaceDTO.builder()
                    .id(expectedUuid)
                    .name(expectedName)
                    .description(expectedDescription)
                    .build();
            HttpEntity<RaceDTO> request = new HttpEntity<>(expectedRaceDTO, headers);

            // Execute the system under test (SUT) via TestRestTemplate component
            responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, request, Void.class);
        }

        @Test
        @DisplayName("should return a response entity with a status of 204 (No content)")
        void verifyResponseEntityTest() {
            assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("should not return a resource representation in the response entity-body")
        void verifyNoBodyTest() {
            assertFalse(responseEntity.hasBody());
        }

        @Test
        @DisplayName("should update the existing race entity in the Cassandra database")
        void verifyDatabaseTest() {
            final String cql = String.format("SELECT * FROM races WHERE id = %s", expectedUuid);
            final Mono<Race> raceMono = reactiveCassandraTemplate.selectOne(cql, Race.class);
            StepVerifier.create(raceMono)
                    .consumeNextWith(x -> {
                        assertEquals(expectedUuid, x.getId());
                        assertEquals(expectedName, x.getName());
                        assertEquals(expectedDescription, x.getDescription());
                    })
                    .verifyComplete();
        }

    }
}
