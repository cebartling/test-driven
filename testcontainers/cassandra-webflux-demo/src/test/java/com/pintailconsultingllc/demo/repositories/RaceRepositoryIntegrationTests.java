package com.pintailconsultingllc.demo.repositories;

import com.datastax.driver.core.utils.UUIDs;
import com.pintailconsultingllc.demo.CassandraContainerInitializer;
import com.pintailconsultingllc.demo.TestSupport;
import com.pintailconsultingllc.demo.entities.RaceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.UUID;

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
        RaceEntity newRace;

        @BeforeEach
        public void doBeforeEachTest() {
            newRace = new RaceEntity(expectedUuid, expectedName, expectedDescription);

            raceRepository.save(newRace);
        }

        @DisplayName("should save a new race entity to the database")
        @Test
        void verifyCassandra() {
            List<RaceEntity> saved = raceRepository.findAllById(List.of(expectedUuid));
            assertEquals(saved.get(0).getId(), newRace.getId());
        }
    }
}