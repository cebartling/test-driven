package com.pintailconsultingllc.webflux.demo.repositories;

import com.pintailconsultingllc.webflux.demo.TestSupport;
import com.pintailconsultingllc.webflux.demo.entities.Department;
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

@Testcontainers
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@Tag(TestSupport.INTEGRATION_TEST)
@DisplayName("DepartmentRepository integration tests")
class DepartmentRepositoryIntegrationTests {

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
    private DepartmentRepository departmentRepository;

    @AfterEach
    void cleanUp() {
        departmentRepository.deleteAll();
    }

    @Nested
    @DisplayName("save method")
    class SaveMethodTests {
        @Nested
        @DisplayName("saving a new department")
        class SaveTests {
            Department newlyCreatedDepartment;
            Department actualPersistedDepartment;
            Mono<Department> saveMono;

            @BeforeEach
            public void doBeforeEachTest() {
                newlyCreatedDepartment = new Department(null, "Engineering",  false);
                saveMono = departmentRepository.save(newlyCreatedDepartment);
                StepVerifier.create(saveMono)
                        .consumeNextWith(department -> actualPersistedDepartment = department)
                        .expectComplete()
                        .verify();
            }

            @Test
            @DisplayName("verify that department document was persisted and returned in a Mono")
            void verifySaveMonoTest() {
                assertAll(
                        () -> assertNotNull(actualPersistedDepartment.getId()),
                        () -> assertFalse(actualPersistedDepartment.getDeleted()),
                        () -> assertEquals(newlyCreatedDepartment.getName(), actualPersistedDepartment.getName())
                );
            }

            @Test
            @DisplayName("verify the newly persistent department document in the Mongo database")
            void verifyRecordInDatabaseTest() {
                final Mono<Long> countMono = departmentRepository.count(Example.of(actualPersistedDepartment));
                StepVerifier.create(countMono)
                        .expectNext(1L)
                        .expectComplete()
                        .verify();
            }
        }

        @Nested
        @DisplayName("updating an existing department")
        class UpdateTests {
            Department existingDepartment;
            Department latestVersionDepartment;

            @BeforeEach
            public void doBeforeEachTest() {
                final Mono<Department> existingDepartmentMono = reactiveMongoTemplate.save(Department.builder()
                        .deleted(false).name("Engineering").build());
                StepVerifier.create(existingDepartmentMono)
                        .consumeNextWith(department -> existingDepartment = department)
                        .expectComplete()
                        .verify();
                existingDepartment.setName("Mechanical engineering");
                final Mono<Department> saveMono = departmentRepository.save(existingDepartment);
                StepVerifier.create(saveMono)
                        .consumeNextWith(department -> latestVersionDepartment = department)
                        .expectComplete()
                        .verify();
            }

            @Test
            @DisplayName("verify that department document was persisted and returned in a Mono")
            void verifySaveMonoTest() {
                assertAll(
                        () -> assertNotNull(latestVersionDepartment.getId()),
                        () -> assertFalse(latestVersionDepartment.getDeleted()),
                        () -> assertEquals(existingDepartment.getName(), latestVersionDepartment.getName())
                );
            }

            @Test
            @DisplayName("verify the newly persistent department document in the Mongo database")
            void verifyRecordInDatabaseTest() {
                final Query idQuery = new Query(Criteria.where("id").is(latestVersionDepartment.getId()));
                final Mono<Long> countMono = reactiveMongoTemplate.count(idQuery, Department.class);
                StepVerifier.create(countMono)
                        .expectNext(1L)
                        .expectComplete()
                        .verify();
            }
        }
    }
}
