package com.pintailconsultingllc.testcontainers.demo.controllers;

import com.pintailconsultingllc.testcontainers.demo.dtos.CompanyDTO;
import com.pintailconsultingllc.testcontainers.demo.entities.Company;
import com.pintailconsultingllc.testcontainers.demo.repositories.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.pintailconsultingllc.testcontainers.demo.TestSupport.UNIT_TEST;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CompanyController unit tests")
@Tag(UNIT_TEST)
class CompanyControllerTests {

    @Mock
    CompanyRepository companyRepositoryMock;

    @InjectMocks
    CompanyController controller;

    @Nested
    @DisplayName("findAll method")
    class FindAllTests {
        private ResponseEntity<List<CompanyDTO>> responseEntity;
        private List<CompanyDTO> companyDtos;
        private List<Company> companyList;

        @BeforeEach
        public void doBeforeEachTest() {
            companyList = new ArrayList<>();
            companyList.add(new Company());
            companyList.add(new Company());
            companyList.add(new Company());
            companyDtos = companyList.stream().map(CompanyDTO::from).collect(Collectors.toList());
            when(companyRepositoryMock.findAll()).thenReturn(companyList);
            responseEntity = controller.findAll();
        }

        @Test
        @DisplayName("should return a response entity with a status of 200 (OK)")
        void verifyResponseEntityTest() {
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("should return an array of companies in the response entity")
        void verifyResponseBodyTest() {
            assertAll(
                    () -> assertEquals(companyDtos.size(), Objects.requireNonNull(responseEntity.getBody()).size()),
                    () -> assertEquals(companyDtos.get(0), Objects.requireNonNull(responseEntity.getBody()).get(0)),
                    () -> assertEquals(companyDtos.get(1), Objects.requireNonNull(responseEntity.getBody()).get(1)),
                    () -> assertEquals(companyDtos.get(2), Objects.requireNonNull(responseEntity.getBody()).get(2))
            );
        }

        @Test
        @DisplayName("should invoke CompanyRepository.findAll")
        void verifyFindAllInvocationTest() {
            verify(companyRepositoryMock).findAll();
        }
    }
}
