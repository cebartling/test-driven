package com.pintailconsultingllc.demo.repositories;

import com.datastax.driver.core.utils.UUIDs;
import com.pintailconsultingllc.demo.CassandraContainerInitializer;
import com.pintailconsultingllc.demo.DockerTestSupport;
import com.pintailconsultingllc.demo.TestSupport;
import com.pintailconsultingllc.demo.entities.Race;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataCassandraTest
@ContextConfiguration(initializers = {CassandraContainerInitializer.class})
@DisplayName("RaceRepository integration tests")
@Tag(TestSupport.INTEGRATION_TEST)
class RaceRepositoryIntegrationTests extends DockerTestSupport {

    @Autowired
    RaceRepository raceRepository;

    @Autowired
    ReactiveCassandraTemplate reactiveCassandraTemplate;


    final UUID expectedUuid = UUIDs.timeBased();
    final String expectedName = "Fat Bike Birkie";
    final String expectedDescription = "Some description";
    Race newRace;
    Race createdRace;

    @Nested
    @DisplayName("creating a new race")
    class SaveNewRaceTests {

        @BeforeEach
        public void doBeforeEachTest() {
            var truncateMono = reactiveCassandraTemplate.truncate(Race.class);
            StepVerifier.create(truncateMono).verifyComplete();
            newRace = Race.builder().id(expectedUuid).name(expectedName).description(expectedDescription).build();

            final Mono<Race> saveMono = raceRepository.save(newRace);
            StepVerifier.create(saveMono).consumeNextWith(race -> createdRace = race).verifyComplete();
        }

        @DisplayName("should save a new race to the database")
        @Test
        void verifyCreate() {
            assertAll(
                    () -> assertEquals(newRace.getId(), createdRace.getId()),
                    () -> assertEquals(newRace.getName(), createdRace.getName()),
                    () -> assertEquals(newRace.getDescription(), createdRace.getDescription()),
                    () -> assertEquals(newRace.getVersion(), createdRace.getVersion())
            );
        }
    }

    @Nested
    @DisplayName("updating an existing race")
    class UpdateTests {
        Race updatedRace;

        @BeforeEach
        public void doBeforeEachTest() {
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
            createdRace = findById(expectedUuid);
            createdRace.setName(expectedName);
            createdRace.setDescription(expectedDescription);
            final Mono<Race> updatedRaceMono = raceRepository.save(createdRace);
            StepVerifier.create(updatedRaceMono).expectNextCount(1).verifyComplete();
            updatedRace = findById(expectedUuid);
        }

        @DisplayName("should save changes to an existing race to the database")
        @Test
        void verifyUpdate() {
            assertAll(
                    () -> assertEquals(expectedUuid, updatedRace.getId()),
                    () -> assertEquals(expectedName, updatedRace.getName()),
                    () -> assertEquals(expectedDescription, updatedRace.getDescription())
            );
        }
    }

    private Race findById(UUID uuid) {
        AtomicReference<Race> raceAtomicReference = new AtomicReference<>();
        final Mono<Race> existingRaceMono = raceRepository.findById(uuid);
        StepVerifier
                .create(existingRaceMono)
                .consumeNextWith(raceAtomicReference::set)
                .verifyComplete();
        return raceAtomicReference.get();
    }
}