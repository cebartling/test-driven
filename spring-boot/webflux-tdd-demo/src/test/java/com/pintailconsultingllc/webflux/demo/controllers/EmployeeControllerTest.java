package com.pintailconsultingllc.webflux.demo.controllers;

import com.pintailconsultingllc.webflux.demo.entities.Employee;
import com.pintailconsultingllc.webflux.demo.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class,
        excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
//@Import(EmployeeService.class)
@DisplayName("EmployeeController unit tests")
class EmployeeControllerTest {

    @MockBean
    EmployeeRepository repository;

    @Autowired
    WebTestClient webClient;

    WebTestClient.ResponseSpec responseSpec;

    @Nested
    @DisplayName("getEmployeeById specifications")
    class GetEmployeeByIdSpecifications {
        Employee employee;

        @BeforeEach
        void doBeforeEachSpec() {
            employee = new Employee();
            employee.setId(100);
            employee.setName("Test");
            employee.setSalary(1000);

            when(repository.findById(100)).thenReturn(Mono.just(employee));
            responseSpec = webClient.get().uri("/employees/{id}", 100).exchange();
        }

        @Test
        @DisplayName("should invoke findById method on employee repository")
        void verify_findById_collaboration() {
            verify(repository, times(1)).findById(100);
        }

        @Test
        @DisplayName("should return a status of 200 (OK)")
        void status_is_200() {
            responseSpec.expectStatus().isOk();
        }

        @Test
        @DisplayName("should return an appropriate resource representation for an employee")
        void appropriate_resource_representation() {
            responseSpec.expectBody()
                    .jsonPath("$.id").isEqualTo(employee.getId())
                    .jsonPath("$.name").isEqualTo(employee.getName())
                    .jsonPath("$.salary").isEqualTo(employee.getSalary());
        }
    }

    @Nested
    @DisplayName("GetAllEmployees specifications")
    class GetAllEmployeesSpecifications {
        Employee employee1;
        Employee employee2;

        @BeforeEach
        void doBeforeEachSpec() {
            employee1 = new Employee();
            employee1.setId(100);
            employee1.setName("Test Employee 1");
            employee1.setSalary(1000);
            employee2 = new Employee();
            employee2.setId(101);
            employee2.setName("Test Employee 2");
            employee2.setSalary(2000);

            when(repository.findAll()).thenReturn(Flux.just(employee1, employee2));
            responseSpec = webClient.get().uri("/employees").exchange();
        }

        @Test
        @DisplayName("should invoke findAll method on employee repository")
        void verify_findAll_collaboration() {
            verify(repository, times(1)).findAll();
        }

        @Test
        @DisplayName("should return a status of 200 (OK)")
        void status_is_200() {
            responseSpec.expectStatus().isOk();
        }

        @Test
        @DisplayName("should return an appropriate resource representation for all employees")
        void appropriate_resource_representation() {
            responseSpec.expectBody()
                    .jsonPath("$[0].id").isEqualTo(employee1.getId())
                    .jsonPath("$[0].name").isEqualTo(employee1.getName())
                    .jsonPath("$[0].salary").isEqualTo(employee1.getSalary())
                    .jsonPath("$[1].id").isEqualTo(employee2.getId())
                    .jsonPath("$[1].name").isEqualTo(employee2.getName())
                    .jsonPath("$[1].salary").isEqualTo(employee2.getSalary());
        }
    }
}