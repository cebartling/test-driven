package com.pintailconsultingllc.demo.controllers;

import com.datastax.driver.core.utils.UUIDs;
import com.pintailconsultingllc.demo.TestSupport;
import com.pintailconsultingllc.demo.dtos.RaceDTO;
import com.pintailconsultingllc.demo.entities.Race;
import com.pintailconsultingllc.demo.repositories.RaceRepository;
import com.pintailconsultingllc.demo.services.RaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = RaceController.class,
        excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
@DisplayName("RaceService unit tests")
@Tag(TestSupport.UNIT_TEST)
class RaceControllerTests {

    @MockBean
    RaceRepository raceRepositoryMock;

    @MockBean
    RaceService raceServiceMock;

    @Autowired
    WebTestClient webTestClient;

    WebTestClient.ResponseSpec responseSpec;


    @Nested
    @DisplayName("create method")
    class CreateNewRaceTests {
        RaceDTO expectedRaceDTO;
        Race expectedRace;

        @BeforeEach
        public void doBeforeEachTest() {
            final UUID expectedUuid = UUIDs.timeBased();
            final String expectedName = "Fat Bike Birkie";
            final String expectedDescription = "Some description";
            expectedRaceDTO = RaceDTO.builder().name(expectedName).description(expectedDescription).build();
            expectedRace = Race.builder().id(expectedUuid).name(expectedName).description(expectedDescription).build();
            when(raceServiceMock.save(any(Race.class))).thenReturn(Mono.just(expectedRace));

            responseSpec = webTestClient.post()
                    .uri("/api/races")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(expectedRaceDTO), RaceDTO.class)
                    .exchange();
        }

        @Test
        @DisplayName("should return a status of 201 (Created)")
        void verifyResponseStatusCodeTest() {
            responseSpec.expectStatus().isCreated();
        }

        @Test
        @DisplayName("should call RaceService.save method")
        void verifyCollaborationWithService() {
            verify(raceServiceMock).save(any(Race.class));
        }

        @Test
        @DisplayName("should return the URI for the newly created resource in the Location header")
        void verifyLocationHeader() {
            String expectedLocation = String.format("/api/races/%s", expectedRace.getId());
            responseSpec.expectHeader().location(expectedLocation);
        }

        @Test
        @DisplayName("should not return a resource representation in the response entity-body")
        void verifyNoBodyTest() {
            responseSpec.expectBody().isEmpty();
        }
    }
}