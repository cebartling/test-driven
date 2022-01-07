package com.pintailconsultingllc.webflux.demo.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pintailconsultingllc.webflux.demo.TestSupport;
import com.pintailconsultingllc.webflux.demo.dtos.DepartmentDTO;
import com.pintailconsultingllc.webflux.demo.entities.Department;
import com.pintailconsultingllc.webflux.demo.repositories.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static com.pintailconsultingllc.webflux.demo.TestSupport.DOCKER_NAME_MONGO;
import static com.pintailconsultingllc.webflux.demo.TestSupport.MONGO_EXPOSED_PORT;
import static com.pintailconsultingllc.webflux.demo.TestSupport.PROPERTY_SPRING_DATA_MONGODB_URI;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag(TestSupport.INTEGRATION_TEST)
@DisplayName("Department API integration tests")
@Disabled("Fix issues with MongoDBContainer")
class DepartmentApiIntegrationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse(DOCKER_NAME_MONGO))
            .withExposedPorts(MONGO_EXPOSED_PORT);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add(PROPERTY_SPRING_DATA_MONGODB_URI, mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @LocalServerPort
    int randomServerPort;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DepartmentRepository departmentRepository;

    Department financeDepartment;
    Department engineeringDepartment;
    WebTestClient.ResponseSpec responseSpec;

    @BeforeEach
    public void doBeforeEachTest() {
        final Mono<Void> voidMono = departmentRepository.deleteAll();
        StepVerifier.create(voidMono).expectNext().expectComplete().verify();
        final Mono<Department> mono1 = reactiveMongoTemplate.save(Department.builder().name("Finance").build());
        final Mono<Department> mono2 = reactiveMongoTemplate.save(Department.builder().name("Engineering").build());
        final Flux<Department> departmentsFlux = Flux.concat(mono1, mono2);
        StepVerifier.create(departmentsFlux)
                .consumeNextWith(department -> financeDepartment = department)
                .consumeNextWith(department -> engineeringDepartment = department)
                .expectComplete().verify();
    }

    @Nested
    @DisplayName("GET /departments")
    class GetAllDepartmentsTests {
        String expectedJson;

        @BeforeEach
        public void doBeforeEachTest() throws JsonProcessingException {
            List<DepartmentDTO> departmentDTOs = List.of(
                    new DepartmentDTO(engineeringDepartment),
                    new DepartmentDTO(financeDepartment)
            );
            expectedJson = objectMapper.writeValueAsString(departmentDTOs);
            responseSpec = webTestClient.get()
                    .uri("/departments")
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange();
        }

        @Test
        @DisplayName("should return a HTTP status code of 200 (OK)")
        void verifyStatusCodeTest() {
            responseSpec.expectStatus().isOk();
        }

        @Test
        @DisplayName("should return an appropriate resource representation for the collection of departments")
        void verifyAppropriateResourceRepresentation() {
            responseSpec.expectBody().json(expectedJson);
        }
    }

    @Nested
    @DisplayName("GET /departments/{id}")
    class GetDepartmentByIdTests {
        String expectedJson;

        @BeforeEach
        public void doBeforeEachTest() throws JsonProcessingException {
            expectedJson = objectMapper.writeValueAsString(new DepartmentDTO(engineeringDepartment));
            final String uri = String.format("/departments/%s", engineeringDepartment.getId());
            responseSpec = webTestClient.get()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange();
        }

        @Test
        @DisplayName("should return a HTTP status code of 200 (OK)")
        void verifyStatusCodeTest() {
            responseSpec.expectStatus().isOk();
        }

        @Test
        @DisplayName("should return an appropriate resource representation for the single department")
        void verifyAppropriateResourceRepresentation() {
            responseSpec.expectBody().json(expectedJson);
        }
    }

    @Nested
    @DisplayName("POST /departments")
    class CreateDepartmentTests {
        Department newlyCreatedDepartment;
        final String uri = "/departments";
        final String expectedName = "Manufacturing";

        @BeforeEach
        public void doBeforeEachTest() {
            DepartmentDTO departmentDto = DepartmentDTO.builder().name(expectedName).build();
            responseSpec = webTestClient.post()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(departmentDto), DepartmentDTO.class)
                    .exchange();
            final Query query = Query.query(Criteria.byExample(Department.builder().name(expectedName).build()));
            final Mono<Department> departmentMono = reactiveMongoTemplate.findOne(query, Department.class);
            StepVerifier.create(departmentMono)
                    .consumeNextWith(department -> newlyCreatedDepartment = department)
                    .expectComplete()
                    .verify();
        }

        @Test
        @DisplayName("should return a HTTP status code of 201 (Created)")
        void verifyStatusCodeTest() {
            responseSpec.expectStatus().isCreated();
        }

        @Test
        @DisplayName("should return a URI for the newly created resource in the Location header")
        void verifyAppropriateLocationHeader() {
            final String uriPattern = String.format("/departments/%s", newlyCreatedDepartment.getId());
            responseSpec.expectHeader().valueMatches("location", uriPattern);
        }
    }

    @Nested
    @DisplayName("PUT /departments/{id}")
    class UpdateDepartmentTests {
        final String expectedName = "Mechanical engineering";
        Department updatedDepartment;

        @BeforeEach
        void doBeforeEachTest() {
            final String uri = String.format("/departments/%s", engineeringDepartment.getId());
            DepartmentDTO departmentDto = new DepartmentDTO(engineeringDepartment);
            departmentDto.setName(expectedName);
            responseSpec = webTestClient.put()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(departmentDto), DepartmentDTO.class)
                    .exchange();
            final Query query = Query.query(Criteria.byExample(Department.builder().name(expectedName).build()));
            final Mono<Department> departmentMono = reactiveMongoTemplate.findOne(query, Department.class);
            StepVerifier.create(departmentMono)
                    .consumeNextWith(department -> updatedDepartment = department)
                    .expectComplete()
                    .verify();
        }

        @Test
        @DisplayName("should return a HTTP status code of 204 (No Content)")
        void verifyStatusCodeTest() {
            responseSpec.expectStatus().isNoContent();
        }

        @Test
        @DisplayName("should update the name of the existing department")
        void verifyDepartmentRecordChangeTest() {
            assertAll(
                    () -> assertNotNull(updatedDepartment),
                    () -> assertEquals(engineeringDepartment.getId(), updatedDepartment.getId()),
                    () -> assertEquals(expectedName, updatedDepartment.getName())
            );
        }
    }

    @Nested
    @DisplayName("DELETE /departments/{id}")
    class SoftDeleteDepartmentTests {
        Department deletedDepartment;

        @BeforeEach
        void doBeforeEachTest() {
            final String uri = String.format("/departments/%s", engineeringDepartment.getId());
            responseSpec = webTestClient.delete()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange();
            final Department example = Department.builder().id(engineeringDepartment.getId()).build();
            final Query query = Query.query(Criteria.byExample(example));
            final Mono<Department> departmentMono = reactiveMongoTemplate.findOne(query, Department.class);
            StepVerifier.create(departmentMono)
                    .consumeNextWith(department -> deletedDepartment = department)
                    .expectComplete()
                    .verify();
        }

        @Test
        @DisplayName("should return a HTTP status code of 204 (No Content)")
        void verifyStatusCodeTest() {
            responseSpec.expectStatus().isNoContent();
        }

        @Test
        @DisplayName("should update the name of the existing department")
        void verifyDepartmentRecordChangeTest() {
            assertAll(
                    () -> assertNotNull(deletedDepartment),
                    () -> assertTrue(deletedDepartment.getDeleted())
            );
        }
    }
}
