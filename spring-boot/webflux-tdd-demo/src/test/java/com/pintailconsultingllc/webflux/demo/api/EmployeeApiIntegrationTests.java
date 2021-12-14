package com.pintailconsultingllc.webflux.demo.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pintailconsultingllc.webflux.demo.TestSupport;
import com.pintailconsultingllc.webflux.demo.dtos.EmployeeDTO;
import com.pintailconsultingllc.webflux.demo.entities.Employee;
import com.pintailconsultingllc.webflux.demo.repositories.EmployeeRepository;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag(TestSupport.INTEGRATION_TEST)
@DisplayName("Employee API integration tests")
@Disabled
class EmployeeApiIntegrationTests {

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
    EmployeeRepository employeeRepository;

    Employee employee1;
    Employee employee2;
    WebTestClient.ResponseSpec responseSpec;

    @BeforeEach
    public void doBeforeEachTest() {
        final Mono<Void> voidMono = employeeRepository.deleteAll();
        StepVerifier.create(voidMono).expectNext().expectComplete().verify();
        final Mono<Employee> mono1 = reactiveMongoTemplate.save(Employee.builder().name("Joe Doe").salary(56000).build());
        final Mono<Employee> mono2 = reactiveMongoTemplate.save(Employee.builder().name("Jane Doe").salary(64000).build());
        final Flux<Employee> employeesFlux = Flux.concat(mono1, mono2);
        StepVerifier.create(employeesFlux)
                .consumeNextWith(employee -> this.employee1 = employee)
                .consumeNextWith(employee -> this.employee2 = employee)
                .expectComplete().verify();
    }

    @Nested
    @DisplayName("GET /employees")
    class GetAllEmployeesTests {
        String expectedJson;

        @BeforeEach
        public void doBeforeEachTest() throws JsonProcessingException {
            List<EmployeeDTO> employeeDTOs = List.of(
                    new EmployeeDTO(employee2),
                    new EmployeeDTO(employee1)
            );
            expectedJson = objectMapper.writeValueAsString(employeeDTOs);
            responseSpec = webTestClient.get()
                    .uri("/employees")
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange();
        }

        @Test
        @DisplayName("should return a HTTP status code of 200 (OK)")
        void verifyStatusCodeTest() {
            responseSpec.expectStatus().isOk();
        }

        @Test
        @DisplayName("should return an appropriate resource representation for the collection of employees")
        void verifyAppropriateResourceRepresentation() {
            responseSpec.expectBody().json(expectedJson);
        }
    }

    @Nested
    @DisplayName("GET /employees/{id}")
    class GetEmployeeByIdTests {
        String expectedJson;

        @BeforeEach
        public void doBeforeEachTest() throws JsonProcessingException {
            expectedJson = objectMapper.writeValueAsString(new EmployeeDTO(employee2));
            final String uri = String.format("/employees/%s", employee2.getId());
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
        @DisplayName("should return an appropriate resource representation for the single employee")
        void verifyAppropriateResourceRepresentation() {
            responseSpec.expectBody().json(expectedJson);
        }
    }

    @Nested
    @DisplayName("POST /employees")
    class CreateEmployeeTests {
        Employee newlyCreatedEmployee;
        final String uri = "/employees";
        final String expectedName = "Jane Doe-Rama";
        final Integer expectedSalary = 72000;

        @BeforeEach
        public void doBeforeEachTest() {
            EmployeeDTO employeeDto = EmployeeDTO.builder().name(expectedName).salary(expectedSalary).build();
            responseSpec = webTestClient.post()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(employeeDto), EmployeeDTO.class)
                    .exchange();
            final Employee example = Employee.builder().name(expectedName).salary(expectedSalary).build();
            final Query query = Query.query(Criteria.byExample(example));
            final Mono<Employee> employeeMono = reactiveMongoTemplate.findOne(query, Employee.class);
            StepVerifier.create(employeeMono)
                    .consumeNextWith(employee -> newlyCreatedEmployee = employee)
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
            final String uriPattern = String.format("/employees/%s", newlyCreatedEmployee.getId());
            responseSpec.expectHeader().valueMatches("location", uriPattern);
        }
    }

    @Nested
    @DisplayName("PUT /employees/{id}")
    class UpdateEmployeeTests {
        final String expectedName = "Jane Doe-Buck";
        final Integer expectedSalary = 96000;
        Employee updatedEmployee;

        @BeforeEach
        void doBeforeEachTest() {
            final String uri = String.format("/employees/%s", employee2.getId());
            EmployeeDTO employeeDto = new EmployeeDTO(employee2);
            employeeDto.setName(expectedName);
            employeeDto.setSalary(expectedSalary);
            responseSpec = webTestClient.put()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(employeeDto), EmployeeDTO.class)
                    .exchange();
            final Employee example = Employee.builder().id(employee2.getId()).build();
            final Query query = Query.query(Criteria.byExample(example));
            final Mono<Employee> employeeMono = reactiveMongoTemplate.findOne(query, Employee.class);
            StepVerifier.create(employeeMono)
                    .consumeNextWith(employee -> updatedEmployee = employee)
                    .expectComplete()
                    .verify();
        }

        @Test
        @DisplayName("should return a HTTP status code of 204 (No Content)")
        void verifyStatusCodeTest() {
            responseSpec.expectStatus().isNoContent();
        }

        @Test
        @DisplayName("should update the existing employee")
        void verifyEmployeeRecordChangeTest() {
            assertAll(
                    () -> assertNotNull(updatedEmployee),
                    () -> assertEquals(employee2.getId(), updatedEmployee.getId()),
                    () -> assertFalse(updatedEmployee.getDeleted()),
                    () -> assertEquals(expectedSalary, updatedEmployee.getSalary()),
                    () -> assertEquals(expectedName, updatedEmployee.getName())
            );
        }
    }

    @Nested
    @DisplayName("DELETE /employees/{id}")
    class SoftDeleteEmployeeTests {
        Employee deletedEmployee;

        @BeforeEach
        void doBeforeEachTest() {
            final String uri = String.format("/employees/%s", employee2.getId());
            responseSpec = webTestClient.delete()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange();
            final Employee example = Employee.builder().id(employee2.getId()).build();
            final Query query = Query.query(Criteria.byExample(example));
            final Mono<Employee> employeeMono = reactiveMongoTemplate.findOne(query, Employee.class);
            StepVerifier.create(employeeMono)
                    .consumeNextWith(employee -> deletedEmployee = employee)
                    .expectComplete()
                    .verify();
        }

        @Test
        @DisplayName("should return a HTTP status code of 204 (No Content)")
        void verifyStatusCodeTest() {
            responseSpec.expectStatus().isNoContent();
        }

        @Test
        @DisplayName("should update the name of the existing employee")
        void verifyEmployeeRecordChangeTest() {
            assertAll(
                    () -> assertNotNull(deletedEmployee),
                    () -> assertTrue(deletedEmployee.getDeleted())
            );
        }
    }
}
