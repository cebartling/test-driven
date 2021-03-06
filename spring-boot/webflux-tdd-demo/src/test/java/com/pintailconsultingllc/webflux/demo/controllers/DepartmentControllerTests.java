package com.pintailconsultingllc.webflux.demo.controllers;

import com.pintailconsultingllc.webflux.demo.TestSupport;
import com.pintailconsultingllc.webflux.demo.dtos.DepartmentDTO;
import com.pintailconsultingllc.webflux.demo.entities.Department;
import com.pintailconsultingllc.webflux.demo.repositories.DepartmentRepository;
import com.pintailconsultingllc.webflux.demo.services.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = DepartmentController.class,
        excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
@Tag(TestSupport.UNIT_TEST)
@DisplayName("DepartmentController unit tests")
class DepartmentControllerTests {

    @MockBean
    DepartmentRepository departmentRepository;

    @MockBean
    DepartmentService departmentService;

    @Autowired
    WebTestClient webTestClient;

    WebTestClient.ResponseSpec responseSpec;

    Department department1;
    Department department2;
    DepartmentDTO departmentDTO;
    final static BigInteger ID_1 = new BigInteger("200");
    final static BigInteger ID_2 = new BigInteger("201");

    @BeforeEach
    public void doBeforeEachTest() {
        department1 = Department.builder().id(ID_1).name("Finance").build();
        department2 = Department.builder().id(ID_2).name("Human resources").build();
        departmentDTO = new DepartmentDTO(department1);
    }

    @Nested
    @DisplayName("GET /departments")
    class GetAllDepartmentsTests {

        @BeforeEach
        public void doBeforeEachTest() {
            when(departmentRepository.findAll()).thenReturn(Flux.just(department1, department2));
            responseSpec = webTestClient.get()
                    .uri("/departments")
                    .exchange();
        }

        @Test
        @DisplayName("should return a status of 200 (OK)")
        void verifyHttpStatusCodeIs200() {
            responseSpec.expectStatus().isOk();
        }

        @Test
        @DisplayName("should invoke findAll method on the department repository to retrieve all departments")
        void verifyFindAllCollaboration() {
            verify(departmentRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("should return an appropriate resource representation for all departments")
        void verifyAppropriateResourceRepresentation() {
            responseSpec.expectBody()
                    .jsonPath("$[0].id").isEqualTo(department1.getId())
                    .jsonPath("$[0].name").isEqualTo(department1.getName())
                    .jsonPath("$[1].id").isEqualTo(department2.getId())
                    .jsonPath("$[1].name").isEqualTo(department2.getName());
        }
    }

    @Nested
    @DisplayName("GET /departments/{id}")
    class GetDepartmentByIdTests {


        @Nested
        @DisplayName("when the department is found in the database matching the ID")
        class WhenDepartmentIsFoundTests {
            @BeforeEach
            public void doBeforeEachTest() {
                when(departmentRepository.findById(ID_1)).thenReturn(Mono.just(department1));
                responseSpec = webTestClient.get()
                        .uri(String.format("/departments/%d", ID_1))
                        .exchange();
            }

            @Test
            @DisplayName("should return a status of 200 (OK)")
            void verifyHttpStatusCodeIs200() {
                responseSpec.expectStatus().isOk();
            }

            @Test
            @DisplayName("should invoke findById method on the department repository to retrieve specific department by its ID")
            void verifyFindAllCollaboration() {
                verify(departmentRepository, times(1)).findById(ID_1);
            }

            @Test
            @DisplayName("should return an appropriate resource representation for the department")
            void verifyAppropriateResourceRepresentation() {
                responseSpec.expectBody()
                        .jsonPath("$.id").isEqualTo(department1.getId())
                        .jsonPath("$.name").isEqualTo(department1.getName());
            }
        }

        @Nested
        @DisplayName("when no department is found in the database to match the ID")
        class WhenNothingIsFoundTests {
            @BeforeEach
            public void doBeforeEachTest() {
                when(departmentRepository.findById(ID_1)).thenReturn(Mono.empty());
                responseSpec = webTestClient.get()
                        .uri(String.format("/departments/%d", ID_1))
                        .exchange();
            }

            @Test
            @DisplayName("should return a status of 404 (Not Found)")
            void verifyHttpStatusCodeIs404() {
                responseSpec.expectStatus().isNotFound();
            }

            @Test
            @DisplayName("should invoke findById method on the department repository to retrieve specific department by its ID")
            void verifyFindAllCollaboration() {
                verify(departmentRepository, times(1)).findById(ID_1);
            }

            @Test
            @DisplayName("should not return a resource representation in the response entity-body")
            void verifyNoBodyTest() {
                responseSpec.expectBody().isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("POST /departments")
    class CreateNewDepartmentTests {

        @BeforeEach
        public void doBeforeEachTest() {
            when(departmentService.create(departmentDTO)).thenReturn(Mono.just(department1));
            responseSpec = webTestClient.post()
                    .uri("/departments")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(departmentDTO), DepartmentDTO.class)
                    .exchange();
        }

        @Test
        @DisplayName("should return a status of 201 (Created)")
        void verifyResponseStatusCodeTest() {
            responseSpec.expectStatus().isCreated();
        }

        @Test
        @DisplayName("should invoke DepartmentService.create method, creating a new department")
        void verifyCreateInvocationOnDepartmentServiceTest() {
            verify(departmentService).create(any(DepartmentDTO.class));
        }

        @Test
        @DisplayName("should return the URI for the newly created resource in the Location header")
        void verifyLocationHeader() {
            String expectedLocation = String.format("/departments/%d", department1.getId());
            responseSpec.expectHeader().location(expectedLocation);
        }

        @Test
        @DisplayName("should not return a resource representation in the response entity-body")
        void verifyNoBodyTest() {
            responseSpec.expectBody().isEmpty();
        }
    }

    @Nested
    @DisplayName("PUT /departments/{id}")
    class UpdateExistingDepartmentTests {

        @Nested
        @DisplayName("when department is found")
        class WhenDepartmentIsFoundTests {
            @BeforeEach
            public void doBeforeEachTest() {
                when(departmentService.update(department1.getId(), departmentDTO)).thenReturn(Mono.just(department1));
                responseSpec = webTestClient.put()
                        .uri(String.format("/departments/%d", department1.getId()))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(departmentDTO), DepartmentDTO.class)
                        .exchange();
            }

            @Test
            @DisplayName("should return a status of 204 (No Content)")
            void verifyResponseStatusCodeTest() {
                responseSpec.expectStatus().isNoContent();
            }

            @Test
            @DisplayName("should invoke DepartmentService.update method, updating an existing department")
            void verifyUpdateInvocationOnDepartmentServiceTest() {
                verify(departmentService).update(department1.getId(), departmentDTO);
            }

            @Test
            @DisplayName("should not return a resource representation in the response entity-body")
            void verifyNoBodyTest() {
                responseSpec.expectBody().isEmpty();
            }
        }

        @Nested
        @DisplayName("when department is not found")
        class WhenDepartmentIsNotFoundTests {
            @BeforeEach
            public void doBeforeEachTest() {
                when(departmentService.update(department1.getId(), departmentDTO)).thenReturn(Mono.empty());
                responseSpec = webTestClient.put()
                        .uri(String.format("/departments/%d", department1.getId()))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(departmentDTO), DepartmentDTO.class)
                        .exchange();
            }

            @Test
            @DisplayName("should return a status of 404 (Not Found)")
            void verifyResponseStatusCodeTest() {
                responseSpec.expectStatus().isNotFound();
            }

            @Test
            @DisplayName("should invoke DepartmentService.update method")
            void verifyUpdateInvocationOnDepartmentServiceTest() {
                verify(departmentService).update(department1.getId(), departmentDTO);
            }

            @Test
            @DisplayName("should not return a resource representation in the response entity-body")
            void verifyNoBodyTest() {
                responseSpec.expectBody().isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("DELETE /departments/{id}")
    class DeleteDepartmentTests {

        @Nested
        @DisplayName("when department is found")
        class WhenDepartmentIsFoundTests {
            @BeforeEach
            public void doBeforeEachTest() {
                when(departmentService.delete(department1.getId())).thenReturn(Mono.just(department1));
                responseSpec = webTestClient.delete()
                        .uri(String.format("/departments/%d", department1.getId()))
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange();
            }

            @Test
            @DisplayName("should return a status of 204 (No Content)")
            void verifyResponseStatusCodeTest() {
                responseSpec.expectStatus().isNoContent();
            }

            @Test
            @DisplayName("should invoke DepartmentService.delete method, soft-deleting an existing department")
            void verifyDeleteInvocationOnDepartmentServiceTest() {
                verify(departmentService).delete(department1.getId());
            }

            @Test
            @DisplayName("should not return a resource representation in the response entity-body")
            void verifyNoBodyTest() {
                responseSpec.expectBody().isEmpty();
            }
        }

        @Nested
        @DisplayName("when department is not found")
        class WhenDepartmentIsNotFoundTests {
            @BeforeEach
            public void doBeforeEachTest() {
                when(departmentService.delete(department1.getId())).thenReturn(Mono.empty());
                responseSpec = webTestClient.delete()
                        .uri(String.format("/departments/%d", department1.getId()))
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange();
            }

            @Test
            @DisplayName("should return a status of 404 (Not Found)")
            void verifyResponseStatusCodeTest() {
                responseSpec.expectStatus().isNotFound();
            }

            @Test
            @DisplayName("should invoke DepartmentService.delete method")
            void verifyDeleteInvocationOnDepartmentServiceTest() {
                verify(departmentService).delete(department1.getId());
            }

            @Test
            @DisplayName("should not return a resource representation in the response entity-body")
            void verifyNoBodyTest() {
                responseSpec.expectBody().isEmpty();
            }
        }
    }
}
