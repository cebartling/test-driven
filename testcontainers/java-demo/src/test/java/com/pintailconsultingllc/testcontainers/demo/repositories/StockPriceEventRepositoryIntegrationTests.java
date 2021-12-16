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