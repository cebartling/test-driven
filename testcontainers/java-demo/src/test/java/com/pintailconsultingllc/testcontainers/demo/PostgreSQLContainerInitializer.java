package com.pintailconsultingllc.testcontainers.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLContainerInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger log = LoggerFactory.getLogger(PostgreSQLContainerInitializer.class);

    private static PostgreSQLContainer sqlContainer = new PostgreSQLContainer("postgres:latest");

    static {
        log.info("Starting PostgreSQL container...");
        sqlContainer.start();
    }

    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        log.info("Initializing PostgreSQL container...");
        log.info(String.format("Docker image name: %s", sqlContainer.getDockerImageName()));
        log.info(String.format("Docker container ID: %s", sqlContainer.getContainerId()));
        log.info(String.format("Docker container name: %s", sqlContainer.getContainerName()));
        TestPropertyValues.of(
                "spring.datasource.url=" + sqlContainer.getJdbcUrl(),
                "spring.datasource.username=" + sqlContainer.getUsername(),
                "spring.datasource.password=" + sqlContainer.getPassword()
        ).applyTo(configurableApplicationContext.getEnvironment());
    }
}
