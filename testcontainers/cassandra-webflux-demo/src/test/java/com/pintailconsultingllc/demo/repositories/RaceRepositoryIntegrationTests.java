package com.pintailconsultingllc.demo.repositories;

import com.pintailconsultingllc.demo.CassandraContainerInitializer;
import com.pintailconsultingllc.demo.TestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataCassandraTest
@ContextConfiguration(initializers = {CassandraContainerInitializer.class})
@DisplayName("RaceRepository integration tests")
@Tag(TestSupport.INTEGRATION_TEST)
class RaceRepositoryIntegrationTests {


    @DisplayName("Creating a new race entity")
    @Test
    void verifyCassandra() {
        assertTrue(true);
    }
}