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

    @Nested
    @DisplayName("save method")
    class SaveTests {
        final UUID expectedUuid = UUIDs.timeBased();
        final String expectedName = "Fat Bike Birkie";
        final String expectedDescription = "Some description";
        Race newRace;
        Race savedRace;

        @BeforeEach
        public void doBeforeEachTest() {
            newRace = new Race(expectedUuid, expectedName, expectedDescription);

            final Mono<Race> saveMono = raceRepository.save(newRace);
            StepVerifier.create(saveMono).consumeNextWith(race -> savedRace = race).verifyComplete();
        }

        @DisplayName("should save a new race entity to the database")
        @Test
        void verifyCassandra() {
            assertAll(
                    () -> assertEquals(newRace.getId(), savedRace.getId()),
                    () -> assertEquals(newRace.getName(), savedRace.getName()),
                    () -> assertEquals(newRace.getDescription(), savedRace.getDescription()),
                    () -> assertEquals(newRace.getVersion(), savedRace.getVersion())
            );
        }
    }
}