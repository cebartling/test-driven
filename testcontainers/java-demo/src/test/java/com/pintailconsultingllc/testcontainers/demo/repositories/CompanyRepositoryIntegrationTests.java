package com.pintailconsultingllc.testcontainers.demo.repositories;

import com.pintailconsultingllc.testcontainers.demo.PostgreSQLContainerInitializer;
import com.pintailconsultingllc.testcontainers.demo.TestSupport;
import com.pintailconsultingllc.testcontainers.demo.entities.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {PostgreSQLContainerInitializer.class})
@DisplayName("CompanyRepository integration tests")
@Tag(TestSupport.INTEGRATION_TEST)
class CompanyRepositoryIntegrationTests {

    public static final String COMPANY_NAME = "Foobar";

    @Autowired
    EntityManager entityManager;

    @Autowired
    private CompanyRepository companyRepository;

    Company persistedCompany;

    @BeforeEach
    public void doBeforeEachTest() {
        final Company transientCompany = new Company();
        transientCompany.setName(COMPANY_NAME);
        transientCompany.setId(UUID.randomUUID());
        persistedCompany = companyRepository.save(transientCompany);
    }

    @Test
    @DisplayName("should find a company by its unique generated identifier")
    void verifyFindById() {
        final Optional<Company> companyOptional = companyRepository.findById(persistedCompany.getId());

        assertAll(
                () -> assertTrue(companyOptional.isPresent()),
                () -> assertEquals(persistedCompany.getId(), companyOptional.get().getId()),
                () -> assertEquals(persistedCompany.getName(), companyOptional.get().getName())
        );
    }

}