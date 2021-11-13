package com.pintailconsultingllc.webflux.demo.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pintailconsultingllc.webflux.demo.TestSupport;
import com.pintailconsultingllc.webflux.demo.dtos.DepartmentDTO;
import com.pintailconsultingllc.webflux.demo.entities.Department;
import com.pintailconsultingllc.webflux.demo.repositories.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
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

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag(TestSupport.INTEGRATION_TEST)
@DisplayName("Department API integration tests")
class DepartmentApiIntegrationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse(DOCKER_NAME_MONGO))
            .withExposedPorts(27017);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

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
        final Mono<Department> mono1 = departmentRepository.save(new Department(null, "Finance", false));
        final Mono<Department> mono2 = departmentRepository.save(new Department(null, "Engineering", false));
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
}
