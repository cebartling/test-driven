package com.pintailconsultingllc.demo.services;

import com.datastax.driver.core.utils.UUIDs;
import com.pintailconsultingllc.demo.TestSupport;
import com.pintailconsultingllc.demo.entities.Race;
import com.pintailconsultingllc.demo.repositories.RaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RaceService unit tests")
@Tag(TestSupport.UNIT_TEST)
class RaceServiceTests {

    @Mock
    RaceRepository raceRepositoryMock;

    @InjectMocks
    RaceService raceService;

    @Nested
    @DisplayName("save method")
    class SaveTests {
        Race expectedRace;
        Race capturedRace;
        final UUID expectedUuid = UUIDs.timeBased();
        final String expectedName = "Fat Bike Birkie";
        final String expectedDescription = "Some description";

        @BeforeEach
        public void doBeforeEachTest() {
            expectedRace = Race.builder().id(expectedUuid).name(expectedName).description(expectedDescription).build();
            when(raceRepositoryMock.save(expectedRace)).thenReturn(Mono.just(expectedRace));

            Mono<Race> resultMono = raceService.save(expectedRace);
            StepVerifier.create(resultMono).consumeNextWith(x -> capturedRace = x).verifyComplete();
        }

        @Test
        @DisplayName("should call RaceRepository.save method")
        void verifyCollaborationWithRepository() {
            verify(raceRepositoryMock).save(expectedRace);
        }

        @Test
        @DisplayName("should return the saved race")
        void verifyDirectOutput() {
            assertEquals(expectedRace, capturedRace);
        }
    }
}
