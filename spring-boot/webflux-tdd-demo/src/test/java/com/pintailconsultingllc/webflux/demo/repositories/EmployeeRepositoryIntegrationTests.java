package com.pintailconsultingllc.webflux.demo.repositories;

import com.pintailconsultingllc.webflux.demo.TestSupport;
import com.pintailconsultingllc.webflux.demo.entities.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@ExtendWith(SpringExtension.class)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@Tag(TestSupport.INTEGRATION_TEST)
@DisplayName("EmployeeRepository integration tests")
class EmployeeRepositoryIntegrationTests {

    public static final String DOCKER_NAME_MONGO = "mongo:5.0.3";

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse(DOCKER_NAME_MONGO))
            .withExposedPorts(27017);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    EmployeeRepository employeeRepository;

    @AfterEach
    void cleanUp() {
        employeeRepository.deleteAll();
    }

    @Nested
    @DisplayName("save method")
    class SaveTests {
        Employee expectedEmployee;
        Employee actualEmployee;
        Mono<Employee> saveMono;

        @BeforeEach
        public void doBeforeEachTest() {
            expectedEmployee = new Employee(null, "Joe Smith", 50000, false);
            saveMono = employeeRepository.save(expectedEmployee);
            StepVerifier.create(saveMono)
                    .consumeNextWith(employee -> actualEmployee = employee)
                    .expectComplete()
                    .verify();
        }

        @Test
        @DisplayName("verify that employee document was persisted")
        void verifySaveMonoTest() {
            assertAll(
                    () -> assertNotNull(actualEmployee.getId()),
                    () -> assertFalse(actualEmployee.isDeleted()),
                    () -> assertEquals(expectedEmployee.getSalary(), actualEmployee.getSalary()),
                    () -> assertEquals(expectedEmployee.getName(), actualEmployee.getName())
            );
        }

        @Test
        @DisplayName("verify record in Mongo database")
        void verifyRecordInDatabaseTest() {
            final Mono<Long> countMono = employeeRepository.count(Example.of(actualEmployee));
            StepVerifier.create(countMono)
                    .expectNext(1L)
                    .expectComplete()
                    .verify();
        }
    }
}
