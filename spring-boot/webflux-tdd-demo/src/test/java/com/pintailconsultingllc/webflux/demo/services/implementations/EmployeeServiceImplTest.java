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
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmployeeServiceImpl unit tests")
@Tag(TestSupport.UNIT_TEST)
class EmployeeServiceImplTest {

    public static final int ID = 3468;
    @Mock
    EmployeeRepository employeeRepositoryMock;

    @InjectMocks
    EmployeeServiceImpl service;

    @Nested
    @DisplayName("create method")
    class CreateSpecifications {
        EmployeeDTO employeeDTO;
        Mono<Employee> employeeMono;
        Employee expectedEmployee;
        Employee actualEmployee;

        @BeforeEach
        public void doBeforeEachTest() {
            employeeDTO = EmployeeDTO.builder().name("Joe Smith").salary(45000).build();
            expectedEmployee = new Employee(employeeDTO);
            expectedEmployee.setId(3468);
            when(employeeRepositoryMock.save(any(Employee.class))).thenReturn(Mono.just(expectedEmployee));
            employeeMono = service.create(employeeDTO);
            StepVerifier.create(employeeMono)
                    .consumeNextWith(employee -> actualEmployee = employee)
                    .verifyComplete();
        }

        @Test
        @DisplayName("should invoke EmployeeRepository.save")
        public void verifyRepositorySaveInvocationTest() {
            verify(employeeRepositoryMock).save(any(Employee.class));
        }

        @Test
        @DisplayName("returns the newly created Employee instance")
        public void ReturnsEmployeeMonoTest() {
            assertEquals(actualEmployee, expectedEmployee);
        }
    }

    @Nested
    @DisplayName("update method")
    class UpdateSpecifications {
        EmployeeDTO employeeDTO;
        Mono<Employee> employeeMono;
        Employee expectedEmployee;
        Employee actualEmployee;

        @BeforeEach
        public void doBeforeEachTest() {
            employeeDTO = EmployeeDTO.builder().id(ID).name("Joe Smith").salary(45000).build();
            expectedEmployee = new Employee(employeeDTO);
            when(employeeRepositoryMock.findById(ID)).thenReturn(Mono.just(expectedEmployee));
            when(employeeRepositoryMock.save(any(Employee.class))).thenReturn(Mono.just(expectedEmployee));
            employeeMono = service.update(employeeDTO.getId(), employeeDTO);
            StepVerifier.create(employeeMono)
                    .consumeNextWith(employee -> actualEmployee = employee)
                    .verifyComplete();
        }

        @Test
        @DisplayName("should invoke EmployeeRepository.findById")
        public void verifyRepositoryFindByIdInvocationTest() {
            verify(employeeRepositoryMock).findById(ID);
        }

        @Test
        @DisplayName("should invoke EmployeeRepository.save")
        public void verifyRepositorySaveInvocationTest() {
            verify(employeeRepositoryMock).save(any(Employee.class));
        }

        @Test
        @DisplayName("returns a Mono containing the updated Employee instance")
        public void ReturnsEmployeeMonoTest() {
            assertEquals(actualEmployee, expectedEmployee);
        }
    }

    @Nested
    @DisplayName("delete method")
    class DeleteSpecifications {
        Employee expectedEmployee;
        Employee expectedDeletedEmployee;
        Employee actualEmployee;

        @BeforeEach
        public void doBeforeEachTest() {
            expectedEmployee = Employee.builder().id(ID).name("Joe Smith").salary(45000).deleted(false).build();
            expectedDeletedEmployee = Employee.builder().id(ID).name("Joe Smith").salary(45000).deleted(false).build();
            when(employeeRepositoryMock.findById(ID)).thenReturn(Mono.just(expectedEmployee));
            when(employeeRepositoryMock.save(any(Employee.class))).thenReturn(Mono.just(expectedDeletedEmployee));
            Mono<Employee> employeeMono = service.delete(ID);
            StepVerifier.create(employeeMono)
                    .consumeNextWith(employee -> actualEmployee = employee)
                    .verifyComplete();
        }

        @Test
        @DisplayName("should invoke EmployeeRepository.findById")
        public void verifyRepositoryFindByIdInvocationTest() {
            verify(employeeRepositoryMock).findById(ID);
        }

        @Test
        @DisplayName("should invoke EmployeeRepository.save")
        public void verifyRepositorySaveInvocationTest() {
            verify(employeeRepositoryMock).save(any(Employee.class));
        }

        @Test
        @DisplayName("returns the soft-deleted Employee instance")
        public void ReturnsEmployeeTest() {
            assertEquals(actualEmployee, expectedDeletedEmployee);
        }
    }
}