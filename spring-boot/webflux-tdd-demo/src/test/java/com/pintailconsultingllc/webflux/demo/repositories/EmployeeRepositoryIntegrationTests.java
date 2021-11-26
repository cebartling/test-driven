package com.pintailconsultingllc.webflux.demo.repositories;

import com.pintailconsultingllc.webflux.demo.TestSupport;
import com.pintailconsultingllc.webflux.demo.entities.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.pintailconsultingllc.webflux.demo.TestSupport.DOCKER_NAME_MONGO;
import static com.pintailconsultingllc.webflux.demo.TestSupport.MONGO_EXPOSED_PORT;
import static com.pintailconsultingllc.webflux.demo.TestSupport.PROPERTY_SPRING_DATA_MONGODB_URI;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers(disabledWithoutDocker = true)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@Tag(TestSupport.INTEGRATION_TEST)
@DisplayName("EmployeeRepository integration tests")
class EmployeeRepositoryIntegrationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse(DOCKER_NAME_MONGO))
            .withExposedPorts(MONGO_EXPOSED_PORT);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add(PROPERTY_SPRING_DATA_MONGODB_URI, mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    void cleanUp() {
        employeeRepository.deleteAll();
    }

    @Nested
    @DisplayName("save method")
    class SaveMethodTests {
        @Nested
        @DisplayName("saving a new employee")
        class SaveTests {
            Employee newlyCreatedEmployee;
            Employee actualPersistedEmployee;
            Mono<Employee> saveMono;

            @BeforeEach
            public void doBeforeEachTest() {
                newlyCreatedEmployee = new Employee(null, "Joe Smith", 50000, false);
                saveMono = employeeRepository.save(newlyCreatedEmployee);
                StepVerifier.create(saveMono)
                        .consumeNextWith(employee -> actualPersistedEmployee = employee)
                        .expectComplete()
                        .verify();
            }

            @Test
            @DisplayName("verify that employee document was persisted and returned in a Mono")
            void verifySaveMonoTest() {
                assertAll(
                        () -> assertNotNull(actualPersistedEmployee.getId()),
                        () -> assertFalse(actualPersistedEmployee.isDeleted()),
                        () -> assertEquals(newlyCreatedEmployee.getSalary(), actualPersistedEmployee.getSalary()),
                        () -> assertEquals(newlyCreatedEmployee.getName(), actualPersistedEmployee.getName())
                );
            }

            @Test
            @DisplayName("verify the newly persistent employee document in the Mongo database")
            void verifyRecordInDatabaseTest() {
                final Mono<Long> countMono = employeeRepository.count(Example.of(actualPersistedEmployee));
                StepVerifier.create(countMono)
                        .expectNext(1L)
                        .expectComplete()
                        .verify();
            }
        }

        @Nested
        @DisplayName("updating an existing employee")
        class UpdateTests {
            Employee existingEmployee;
            Employee latestVersionEmployee;

            @BeforeEach
            public void doBeforeEachTest() {
                final Mono<Employee> existingEmployeeMono = reactiveMongoTemplate.save(Employee.builder()
                        .deleted(false).salary(100000).name("Jane Seymour").build());
                StepVerifier.create(existingEmployeeMono)
                        .consumeNextWith(employee -> existingEmployee = employee)
                        .expectComplete()
                        .verify();
                existingEmployee.setName("Jannel Seymour");
                existingEmployee.setSalary(110000);
                final Mono<Employee> saveMono = employeeRepository.save(existingEmployee);
                StepVerifier.create(saveMono)
                        .consumeNextWith(employee -> latestVersionEmployee = employee)
                        .expectComplete()
                        .verify();
            }

            @Test
            @DisplayName("verify that employee document was persisted and returned in a Mono")
            void verifySaveMonoTest() {
                assertAll(
                        () -> assertNotNull(latestVersionEmployee.getId()),
                        () -> assertFalse(latestVersionEmployee.isDeleted()),
                        () -> assertEquals(existingEmployee.getSalary(), latestVersionEmployee.getSalary()),
                        () -> assertEquals(existingEmployee.getName(), latestVersionEmployee.getName())
                );
            }

            @Test
            @DisplayName("verify the newly persistent employee document in the Mongo database")
            void verifyRecordInDatabaseTest() {
                final Query idQuery = new Query(Criteria.where("id").is(latestVersionEmployee.getId()));
                final Mono<Long> countMono = reactiveMongoTemplate.count(idQuery, Employee.class);
                StepVerifier.create(countMono)
                        .expectNext(1L)
                        .expectComplete()
                        .verify();
            }
        }
    }
}
