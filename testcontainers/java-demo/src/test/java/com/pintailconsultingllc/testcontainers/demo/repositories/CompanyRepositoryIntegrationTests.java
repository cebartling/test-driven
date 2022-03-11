package com.pintailconsultingllc.testcontainers.demo.repositories;

import com.pintailconsultingllc.testcontainers.demo.PostgreSQLContainerInitializer;
import com.pintailconsultingllc.testcontainers.demo.TestSupport;
import com.pintailconsultingllc.testcontainers.demo.entities.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

/**
 * This integration test demonstrates how to exercise a JPA repository. The test is annotated with
 * {@link org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest @DataJpaTest} annotation.
 * That will start a slice of Spring Boot application context, enough to bootstrap JPA and the repository.
 * <p>
 * The {@link PostgreSQLContainerInitializer} component manages the PostgreSQL
 * database via Testcontainers and Docker. This initializer is then configured for the test suite class via the
 * {@link org.springframework.test.context.ContextConfiguration @ContextConfiguration} annotation. Each test is
 * responsible for seeding data into the database for successful execution of the test. Database repository
 * components are used for this data seeding task. Spring will autowire repository components and the JPA
 * entity manager into your tests.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {PostgreSQLContainerInitializer.class})
@DisplayName("CompanyRepository integration tests")
@Tag(TestSupport.INTEGRATION_TEST)
class CompanyRepositoryIntegrationTests {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private CompanyRepository companyRepository;

    @Nested
    @DisplayName("findById method")
    class FindIdTests {
        public static final String COMPANY_NAME = "Foobar";

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

    @Nested
    @DisplayName("save method")
    class SaveTests {
        public static final String COMPANY_NAME = "Barfoo";

        @Test
        @DisplayName("should find a company by its unique generated identifier")
        void verifyFindById() {
            final Company transientCompany = new Company();
            transientCompany.setName(COMPANY_NAME);
            transientCompany.setId(UUID.randomUUID());
            Company persistedCompany = companyRepository.save(transientCompany);

            final Optional<Company> companyOptional = companyRepository.findById(persistedCompany.getId());

            assertAll(
                    () -> assertTrue(companyOptional.isPresent()),
                    () -> assertEquals(persistedCompany.getId(), companyOptional.get().getId()),
                    () -> assertEquals(persistedCompany.getName(), companyOptional.get().getName())
            );
        }
    }
}