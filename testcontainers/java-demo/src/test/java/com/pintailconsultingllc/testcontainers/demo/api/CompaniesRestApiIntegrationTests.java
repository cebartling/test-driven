package com.pintailconsultingllc.testcontainers.demo.api;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {PostgreSQLContainerInitializer.class})
@DisplayName("/api/companies REST API integration tests")
@Tag(TestSupport.INTEGRATION_TEST)
public class CompaniesRestApiIntegrationTests {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    private CompanyRepository companyRepository;

    @Nested
    @DisplayName("findAll method")
    class FindAllTests {
        ResponseEntity<List<CompanyDTO>> responseEntity;
        List<CompanyDTO> companyDtos;
        List<Company> companyList;


        @BeforeEach
        public void doBeforeEachTest() throws URISyntaxException {
            companyList = new ArrayList<>();
            IntStream.range(0, 5).mapToObj(i -> new Company()).forEach(transientCompany -> {
                final UUID randomUUID = UUID.randomUUID();
                transientCompany.setName(String.format("Foobar-%s", randomUUID));
                transientCompany.setId(randomUUID);
                companyList.add(companyRepository.save(transientCompany));
            });
            companyDtos = companyList.stream().map(CompanyDTO::from).collect(Collectors.toList());
            final URI url = new URI(String.format("http://localhost:%d/java-testcontainers-demo/api/companies", randomServerPort));
            ParameterizedTypeReference<List<CompanyDTO>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
            };
            responseEntity = testRestTemplate.exchange(url, HttpMethod.GET, null, parameterizedTypeReference);
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
                    () -> assertEquals(companyDtos.get(2), Objects.requireNonNull(responseEntity.getBody()).get(2)),
                    () -> assertEquals(companyDtos.get(3), Objects.requireNonNull(responseEntity.getBody()).get(3)),
                    () -> assertEquals(companyDtos.get(4), Objects.requireNonNull(responseEntity.getBody()).get(4))
            );
        }
    }

}
