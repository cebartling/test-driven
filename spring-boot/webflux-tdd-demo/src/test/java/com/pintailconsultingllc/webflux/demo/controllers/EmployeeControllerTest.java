package com.pintailconsultingllc.webflux.demo.controllers;

import com.pintailconsultingllc.webflux.demo.entities.Employee;
import com.pintailconsultingllc.webflux.demo.repositories.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
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
    private WebTestClient webClient;

    @Test
    @DisplayName("getEmployeeById tests")
    void testGetEmployeeById() {
        Employee employee = new Employee();
        employee.setId(100);
        employee.setName("Test");
        employee.setSalary(1000);

        when(repository.findById(100)).thenReturn(Mono.just(employee));

        webClient.get().uri("/employees/{id}", 100)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.id").isEqualTo(100)
                .jsonPath("$.name").isEqualTo("Test")
                .jsonPath("$.salary").isEqualTo(1000);

        verify(repository, times(1)).findById(100);
    }
}