package com.pintailconsultingllc.demo.repositories;

import com.datastax.driver.core.utils.UUIDs;
import com.pintailconsultingllc.demo.CassandraContainerInitializer;
import com.pintailconsultingllc.demo.TestSupport;
import com.pintailconsultingllc.demo.entities.Race;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataCassandraTest
@ContextConfiguration(initializers = {CassandraContainerInitializer.class})
@DisplayName("RaceRepository integration tests")
@Tag(TestSupport.INTEGRATION_TEST)
class RaceRepositoryIntegrationTests {

    @Autowired
    RaceRepository raceRepository;

    final UUID expectedUuid = UUIDs.timeBased();
    final String expectedName = "Fat Bike Birkie";
    final String expectedDescription = "Some description";
    Race newRace;
    Race createdRace;

    @Nested
    @DisplayName("creating a new race")
    class SaveTests {

        @BeforeEach
        public void doBeforeEachTest() {
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
            newRace = Race.builder().id(expectedUuid).name(expectedName).description(expectedDescription).build();

            final Mono<Race> saveMono = raceRepository.save(newRace);
            StepVerifier.create(saveMono).consumeNextWith(race -> createdRace = race).verifyComplete();

            createdRace.setDescription("Update to the description");
            final Mono<Race> save2Mono = raceRepository.save(createdRace);
            StepVerifier.create(save2Mono).consumeNextWith(race -> updatedRace = race).verifyComplete();
        }

        @DisplayName("should save changes to an existing race to the database")
        @Test
        void verifyUpdate() {
            assertAll(
                    () -> assertEquals(createdRace.getId(), updatedRace.getId()),
                    () -> assertEquals(createdRace.getName(), updatedRace.getName()),
                    () -> assertEquals(createdRace.getDescription(), updatedRace.getDescription()),
                    () -> assertEquals(createdRace.getVersion(), updatedRace.getVersion())
            );
        }
    }

}