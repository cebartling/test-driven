package com.pintailconsultingllc.webflux.demo.services.implementations;

import com.pintailconsultingllc.webflux.demo.TestSupport;
import com.pintailconsultingllc.webflux.demo.dtos.DepartmentDTO;
import com.pintailconsultingllc.webflux.demo.entities.Department;
import com.pintailconsultingllc.webflux.demo.repositories.DepartmentRepository;
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
@DisplayName("DepartmentServiceImpl unit tests")
@Tag(TestSupport.UNIT_TEST)
class DepartmentServiceImplTest {

    @Mock
    DepartmentRepository departmentRepositoryMock;

    @InjectMocks
    DepartmentServiceImpl service;

    DepartmentDTO departmentDTO;
    Department department;

    Mono<Department> actualDepartmentMono;
    Department actualDepartment;

    @BeforeEach
    public void doBeforeEachTest() {
        departmentDTO = DepartmentDTO.builder().name("Finance").build();
        department = Department.builder().id(2000).name("Finance").build();
    }

    @Nested
    @DisplayName("create method")
    class CreateTests {

        @BeforeEach
        public void doBeforeEachTest() {
            when(departmentRepositoryMock.save(any(Department.class))).thenReturn(Mono.just(department));
            actualDepartmentMono = service.create(departmentDTO);
            StepVerifier.create(actualDepartmentMono)
                    .consumeNextWith(department -> actualDepartment = department)
                    .verifyComplete();
        }

        @Test
        @DisplayName("should invoke DepartmentRepository.save to create new department")
        void verifyRepositorySaveTest() {
            verify(departmentRepositoryMock).save(any(Department.class));
        }

        @Test
        @DisplayName("should return a Mono with the new department")
        void verifyDirectOutputTest() {
            assertEquals(actualDepartment, department);
        }
    }

    @Nested
    @DisplayName("update method")
    class UpdateTests {
        @BeforeEach
        public void doBeforeEachTest() {

        }

    }

    @Nested
    @DisplayName("delete method")
    class DeleteTests {
        @BeforeEach
        public void doBeforeEachTest() {

        }

    }
}