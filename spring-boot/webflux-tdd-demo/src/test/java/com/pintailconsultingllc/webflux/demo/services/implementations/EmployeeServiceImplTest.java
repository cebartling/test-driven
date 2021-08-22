package com.pintailconsultingllc.webflux.demo.services.implementations;

import com.pintailconsultingllc.webflux.demo.TestSupport;
import com.pintailconsultingllc.webflux.demo.dtos.EmployeeDTO;
import com.pintailconsultingllc.webflux.demo.entities.Employee;
import com.pintailconsultingllc.webflux.demo.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmployeeServiceImpl unit tests")
@Tag(TestSupport.UNIT_TEST)
class EmployeeServiceImplTest {

    @Mock
    EmployeeRepository employeeRepositoryMock;

    @InjectMocks
    EmployeeServiceImpl service;

    @Nested
    @DisplayName("create method")
    class CreateSpecifications {
        EmployeeDTO employeeDTO;
        Mono<Employee> actual;
        Employee expectedEmployee;

        @BeforeEach
        public void doBeforeEachTest() {
            employeeDTO = EmployeeDTO.builder().name("Joe Smith").salary(45000).build();
            expectedEmployee = new Employee(employeeDTO);
            expectedEmployee.setId(3468);
            when(employeeRepositoryMock.save(any(Employee.class))).thenReturn(Mono.just(expectedEmployee));
            actual = service.create(employeeDTO);
        }

        @Test
        @DisplayName("should invoke EmployeeRepository.create")
        public void verifyRepositoryCreateInvocationTest() {
            verify(employeeRepositoryMock).save(any(Employee.class));
        }

        @Test
        @DisplayName("returns an Employee Mono")
        public void ReturnsEmployeeMonoTest() {
            assertEquals(actual.block(), expectedEmployee);
        }

    }
}