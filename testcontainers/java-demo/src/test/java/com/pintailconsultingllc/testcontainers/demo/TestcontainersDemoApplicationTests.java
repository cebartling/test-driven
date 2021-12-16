package com.pintailconsultingllc.testcontainers.demo;

import com.pintailconsultingllc.testcontainers.demo.controllers.CompanyController;
import com.pintailconsultingllc.testcontainers.demo.repositories.CompanyRepository;
import com.pintailconsultingllc.testcontainers.demo.repositories.StockPriceEventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {PostgreSQLContainerInitializer.class})
@DisplayName("TestcontainersDemoApplication integration tests")
@Tag(TestSupport.INTEGRATION_TEST)
class TestcontainersDemoApplicationTests {

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	StockPriceEventRepository stockPriceEventRepository;

	@Autowired
	CompanyController companyController;

	@Test
	@DisplayName("verify that the application context loads and auto-wires components")
	void contextLoads() {
		assertAll(
				() -> assertNotNull(companyController),
				() -> assertNotNull(companyRepository),
				() -> assertNotNull(stockPriceEventRepository)
		);
	}

}
