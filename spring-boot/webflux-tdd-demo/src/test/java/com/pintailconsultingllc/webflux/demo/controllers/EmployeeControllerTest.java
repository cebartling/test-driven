package com.pintailconsultingllc.webflux.demo.controllers;

import com.pintailconsultingllc.webflux.demo.TestSupport;
import com.pintailconsultingllc.webflux.demo.dtos.EmployeeDTO;
import com.pintailconsultingllc.webflux.demo.entities.Employee;
import com.pintailconsultingllc.webflux.demo.repositories.EmployeeRepository;
import com.pintailconsultingllc.webflux.demo.services.EmployeeService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class,
        excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
@Tag(TestSupport.UNIT_TEST)
@DisplayName("EmployeeController unit tests")
class EmployeeControllerTest {

    @MockBean
    EmployeeRepository employeeRepository;

    @MockBean
    EmployeeService employeeService;

    @Autowired
    WebTestClient webTestClient;

    WebTestClient.ResponseSpec responseSpec;

    @Nested
    @DisplayName("GET /employees/{id} specifications")
    class GetEmployeeByIdSpecifications {
        @Nested
        @DisplayName("when employee is found")
        class WhenFoundTests {
            Employee employee;

            @BeforeEach
            void doBeforeEachSpec() {
                employee = Employee.builder()
                        .id(100)
                        .name("Test Employee 1")
                        .salary(1000)
                        .build();
                when(employeeRepository.findById(100)).thenReturn(Mono.just(employee));
                responseSpec = webTestClient.get().uri("/employees/{id}", 100).exchange();
            }

            @Test
            @DisplayName("should invoke findById method on employee repository")
            void verifyFindByIdCollaboration() {
                verify(employeeRepository, times(1)).findById(100);
            }

            @Test
            @DisplayName("should return a status of 200 (OK)")
            void verifyHttpStatusCodeIs200() {
                responseSpec.expectStatus().isOk();
            }

            @Test
            @DisplayName("should return an appropriate resource representation for an employee")
            void verifyAppropriateResourceRepresentation() {
                responseSpec.expectBody()
                        .jsonPath("$.id").isEqualTo(employee.getId())
                        .jsonPath("$.name").isEqualTo(employee.getName())
                        .jsonPath("$.salary").isEqualTo(employee.getSalary());
            }
        }

        @Nested
        @DisplayName("when employee is not found")
        class WhenNotFoundTests {
            @BeforeEach
            void doBeforeEachSpec() {
                when(employeeRepository.findById(100)).thenReturn(Mono.empty());
                responseSpec = webTestClient.get().uri("/employees/{id}", 100).exchange();
            }

            @Test
            @DisplayName("should invoke findById method on employee repository")
            void verifyFindByIdCollaboration() {
                verify(employeeRepository, times(1)).findById(100);
            }

            @Test
            @DisplayName("should return a status of 401 (Not Found)")
            void verifyHttpStatusCodeIsNotFound() {
                responseSpec.expectStatus().isNotFound();
            }
        }
    }

    @Nested
    @DisplayName("GET /employees specifications")
    class GetAllEmployeesSpecifications {
        Employee employee1;
        Employee employee2;

        @BeforeEach
        void doBeforeEachSpec() {
            employee1 = Employee.builder()
                    .id(100)
                    .name("Test Employee 1")
                    .salary(1000)
                    .build();
            employee2 = Employee.builder()
                    .id(101)
                    .name("Test Employee 2")
                    .salary(2000)
                    .build();
            when(employeeRepository.findAll()).thenReturn(Flux.just(employee1, employee2));
            responseSpec = webTestClient.get()
                    .uri("/employees")
                    .exchange();
        }

        @Test
        @DisplayName("should invoke findAll method on employee repository")
        void verifyFindAllCollaboration() {
            verify(employeeRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("should return a status of 200 (OK)")
        void verifyHttpStatusCodeIs200() {
            responseSpec.expectStatus().isOk();
        }

        @Test
        @DisplayName("should return an appropriate resource representation for all employees")
        void verifyAppropriateResourceRepresentation() {
            responseSpec.expectBody()
                    .jsonPath("$[0].id").isEqualTo(employee1.getId())
                    .jsonPath("$[0].name").isEqualTo(employee1.getName())
                    .jsonPath("$[0].salary").isEqualTo(employee1.getSalary())
                    .jsonPath("$[1].id").isEqualTo(employee2.getId())
                    .jsonPath("$[1].name").isEqualTo(employee2.getName())
                    .jsonPath("$[1].salary").isEqualTo(employee2.getSalary());
        }
    }

    @Nested
    @DisplayName("POST /employees specifications")
    class CreateEmployeeSpecifications {
        EmployeeDTO employeeDTO;
        Employee employee;

        @BeforeEach
        void doBeforeEachSpec() {
            employeeDTO = EmployeeDTO.builder()
                    .name("Test Employee 1")
                    .salary(1000)
                    .build();
            employee = new Employee(employeeDTO);
            employee.setId(101);
            when(employeeService.create(any(EmployeeDTO.class))).thenReturn(Mono.just(employee));
            responseSpec = webTestClient.post()
                    .uri("/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(Mono.just(employeeDTO), EmployeeDTO.class)
                    .exchange();
        }

        @Test
        @DisplayName("should invoke EmployeeService.create method")
        public void verifyCreateInvocationOnEmployeeServiceTest() {
            verify(employeeService).create(any(EmployeeDTO.class));
        }

        @Test
        @DisplayName("should return a status of 201 (Created)")
        void verifyHttpStatusCodeIs201() {
            responseSpec.expectStatus().isCreated();
        }

        @Test
        @DisplayName("should return the URI for the newly created resource in the Location header")
        void verifyLocationHeader() {
            responseSpec.expectHeader().location(String.format("/employees/%d", employee.getId()));
        }

        @Test
        @DisplayName("should not return a resource representation in the response entity-body")
        public void verifyNoBodyTest() {
            responseSpec.expectBody().isEmpty();
        }
    }

    @Nested
    @DisplayName("PUT /employee specifications")
    class UpdateEmployeeTests {
        @Nested
        @DisplayName("when employee is found")
        class WhenEmployeeIsFoundTests {
            EmployeeDTO employeeDTO;
            Employee employee;
            int id = 2094;

            @BeforeEach
            void doBeforeEachSpec() {
                employeeDTO = EmployeeDTO.builder()
                        .id(id)
                        .name("Test Employee 1")
                        .salary(1000)
                        .build();
                employee = new Employee(employeeDTO);
                when(employeeService.update(id, employeeDTO)).thenReturn(Mono.just(employee));
                responseSpec = webTestClient.put()
                        .uri(String.format("/employees/%d", id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .body(Mono.just(employeeDTO), EmployeeDTO.class)
                        .exchange();
            }

            @Test
            @DisplayName("should invoke EmployeeService.update method")
            public void verifyUpdateInvocationOnEmployeeServiceTest() {
                verify(employeeService).update(id, employeeDTO);
            }

            @Test
            @DisplayName("should return a status of 204 (No Content)")
            void verifyHttpStatusCodeIs204() {
                responseSpec.expectStatus().isNoContent();
            }

            @Test
            @DisplayName("should not return a resource representation in the response entity-body")
            public void verifyNoBodyTest() {
                responseSpec.expectBody().isEmpty();
            }
        }

        @Nested
        @DisplayName("when employee is not found")
        class WhenEmployeeIsNotFoundTests {
            EmployeeDTO employeeDTO;
            int id = 2094;

            @BeforeEach
            void doBeforeEachSpec() {
                employeeDTO = EmployeeDTO.builder()
                        .id(id)
                        .name("Test Employee 1")
                        .salary(1000)
                        .build();
                when(employeeService.update(id, employeeDTO)).thenReturn(Mono.empty());
                responseSpec = webTestClient.put()
                        .uri(String.format("/employees/%d", id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .body(Mono.just(employeeDTO), EmployeeDTO.class)
                        .exchange();
            }

            @Test
            @DisplayName("should invoke EmployeeService.update method")
            public void verifyUpdateInvocationOnEmployeeServiceTest() {
                verify(employeeService).update(id, employeeDTO);
            }

            @Test
            @DisplayName("should return a status of 401 (Not Found)")
            void verifyHttpStatusCodeIsNotFound() {
                responseSpec.expectStatus().isNotFound();
            }

            @Test
            @DisplayName("should not return a resource representation in the response entity-body")
            public void verifyNoBodyTest() {
                responseSpec.expectBody().isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("DELETE /employee/{id} specifications")
    class DeleteEmployeeTests {
        @Nested
        @DisplayName("when the employee is found")
        class WhenEmployeeIsFoundTests {
            int id = 2094;
            Employee employee;

            @BeforeEach
            void doBeforeEachSpec() {
                employee = Employee.builder()
                        .id(id)
                        .name("Test Employee 1")
                        .salary(1000)
                        .build();
                when(employeeService.delete(id)).thenReturn(Mono.just(employee));
                responseSpec = webTestClient.delete()
                        .uri(String.format("/employees/%d", id))
                        .exchange();
            }

            @Test
            @DisplayName("should invoke EmployeeService.delete method")
            public void verifyDeleteInvocationOnEmployeeServiceTest() {
                verify(employeeService).delete(id);
            }

            @Test
            @DisplayName("should return a status of 204 (No Content)")
            void verifyHttpStatusCodeIs204() {
                responseSpec.expectStatus().isNoContent();
            }

            @Test
            @DisplayName("should not return a resource representation in the response entity-body")
            public void verifyNoBodyTest() {
                responseSpec.expectBody().isEmpty();
            }
        }

        @Nested
        @DisplayName("when the employee is not found")
        class WhenEmployeeIsNotFoundTests {
            int id = 2094;

            @BeforeEach
            void doBeforeEachSpec() {
                when(employeeService.delete(id)).thenReturn(Mono.empty());
                responseSpec = webTestClient.delete()
                        .uri(String.format("/employees/%d", id))
                        .exchange();
            }

            @Test
            @DisplayName("should invoke EmployeeService.delete method")
            public void verifyDeleteInvocationOnEmployeeServiceTest() {
                verify(employeeService).delete(id);
            }

            @Test
            @DisplayName("should return a status of 404 (Not Found)")
            void verifyHttpStatusCodeIsNotFound() {
                responseSpec.expectStatus().isNotFound();
            }

            @Test
            @DisplayName("should not return a resource representation in the response entity-body")
            public void verifyNoBodyTest() {
                responseSpec.expectBody().isEmpty();
            }
        }
    }
}