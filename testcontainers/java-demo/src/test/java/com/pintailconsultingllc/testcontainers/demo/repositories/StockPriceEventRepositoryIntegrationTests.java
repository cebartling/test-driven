package com.pintailconsultingllc.testcontainers.demo.repositories;

import com.pintailconsultingllc.testcontainers.demo.PostgreSQLContainerInitializer;
import com.pintailconsultingllc.testcontainers.demo.TestSupport;
import com.pintailconsultingllc.testcontainers.demo.entities.StockPriceEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import java.time.ZonedDateTime;
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
@DisplayName("StockPriceEventRepository integration tests")
@Tag(TestSupport.INTEGRATION_TEST)
class StockPriceEventRepositoryIntegrationTests {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private StockPriceEventRepository stockPriceEventRepository;

    StockPriceEvent persistedStockPriceEvent;

    @BeforeEach
    public void doBeforeEachTest() {
        final StockPriceEvent stockPriceEvent = new StockPriceEvent();
        stockPriceEvent.setId(UUID.randomUUID());
        stockPriceEvent.setEventTimestamp(ZonedDateTime.now());
        stockPriceEvent.setSharePriceInCents(3544);
        persistedStockPriceEvent = stockPriceEventRepository.save(stockPriceEvent);
    }

    @Test
    @DisplayName("should find a stock price event by its unique generated identifier")
    void verifyFindById() {
        final Optional<StockPriceEvent> stockPriceEventOptional = stockPriceEventRepository.findById(persistedStockPriceEvent.getId());
        assertAll(
                () -> assertTrue(stockPriceEventOptional.isPresent()),
                () -> assertEquals(persistedStockPriceEvent.getId(), stockPriceEventOptional.get().getId()),
                () -> assertEquals(persistedStockPriceEvent.getSharePriceInCents(),
                        stockPriceEventOptional.get().getSharePriceInCents()),
                () -> assertEquals(persistedStockPriceEvent.getEventTimestamp(),
                        stockPriceEventOptional.get().getEventTimestamp())
        );
    }

}