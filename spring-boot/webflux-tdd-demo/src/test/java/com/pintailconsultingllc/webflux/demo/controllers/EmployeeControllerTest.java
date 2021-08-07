package com.pintailconsultingllc.webflux.demo.controllers;

import com.pintailconsultingllc.webflux.demo.entities.Employee;
import com.pintailconsultingllc.webflux.demo.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class)
//@Import(EmployeeService.class)
@DisplayName("EmployeeController unit tests")
class EmployeeControllerTest {

    @MockBean
    EmployeeRepository repository;

    @Autowired
    WebTestClient webClient;

    @Nested
    @DisplayName("getEmployeeById specifications")
    class GetEmployeeByIdSpecifications {
        WebTestClient.ResponseSpec responseSpec;

        @BeforeEach
        void doBeforeEachSpec() {
            Employee employee = new Employee();
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
                    .jsonPath("$.name").isNotEmpty()
                    .jsonPath("$.id").isEqualTo(100)
                    .jsonPath("$.name").isEqualTo("Test")
                    .jsonPath("$.salary").isEqualTo(1000);
        }
    }

    @Nested
    @DisplayName("GetAllEmployees specifications")
    class GetAllEmployeesSpecifications {

        @BeforeEach
        void doBeforeEachSpec() {
        }
    }
}