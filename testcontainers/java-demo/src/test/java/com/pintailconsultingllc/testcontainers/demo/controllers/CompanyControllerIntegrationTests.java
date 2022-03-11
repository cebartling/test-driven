package com.pintailconsultingllc.testcontainers.demo.controllers;

import com.pintailconsultingllc.testcontainers.demo.PostgreSQLContainerInitializer;
import com.pintailconsultingllc.testcontainers.demo.TestSupport;
import com.pintailconsultingllc.testcontainers.demo.dtos.CompanyDTO;
import com.pintailconsultingllc.testcontainers.demo.entities.Company;
import com.pintailconsultingllc.testcontainers.demo.repositories.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {PostgreSQLContainerInitializer.class})
@DisplayName("CompanyController integration tests")
@Tag(TestSupport.INTEGRATION_TEST)
public class CompanyControllerIntegrationTests {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private WebApplicationContext webApplicationContext;
//    @Autowired
//    private CompanyRepository companyRepository;

    private MockMvc mockMvc;

//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Autowired
//    EntityManager entityManager;
//

    @BeforeEach
    public void doBeforeEachTest() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Nested
    @DisplayName("web application context tests")
    class WebApplicationContextTests {

        @Test
        @DisplayName("ensure the company controller bean is available")
        void verifyCompanyControllerBean() {
            assertNotNull(webApplicationContext.getBean(CompanyController.class));
        }
    }


//    @Nested
//    @DisplayName("findAll method")
//    class FindAllTests {
//        private ResponseEntity<List<CompanyDTO>> responseEntity;
//        private List<CompanyDTO> companyDtos;
//        private List<Company> companyList;
//
//        @BeforeEach
//        public void doBeforeEachTest() {
//            companyList = new ArrayList<>();
//            for (int i = 0; i < 5; i++) {
//                final Company transientCompany = new Company();
//                final UUID randomUUID = UUID.randomUUID();
//                transientCompany.setName(String.format("Foobar-%s", randomUUID));
//                transientCompany.setId(randomUUID);
//                companyList.add(companyRepository.save(transientCompany));
//            }
//
//            companyDtos = companyList.stream().map(CompanyDTO::from).collect(Collectors.toList());
//            responseEntity = controller.findAll();
//        }

//        @Test
//        @DisplayName("should return a response entity with a status of 200 (OK)")
//        void verifyResponseEntityTest() {
//            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        }
//
//        @Test
//        @DisplayName("should return an array of companies in the response entity")
//        void verifyResponseBodyTest() {
//            assertAll(
//                    () -> assertEquals(companyDtos.size(), Objects.requireNonNull(responseEntity.getBody()).size()),
//                    () -> assertEquals(companyDtos.get(0), Objects.requireNonNull(responseEntity.getBody()).get(0)),
//                    () -> assertEquals(companyDtos.get(1), Objects.requireNonNull(responseEntity.getBody()).get(1)),
//                    () -> assertEquals(companyDtos.get(2), Objects.requireNonNull(responseEntity.getBody()).get(2))
//            );
//        }
//
//        @Test
//        @DisplayName("should invoke CompanyRepository.findAll")
//        void verifyFindAllInvocationTest() {
//            verify(companyRepositoryMock).findAll();
//        }
//    }

}
