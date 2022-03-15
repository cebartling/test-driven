package com.pintailconsultingllc.demo;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ApplicationContextInitializer implementation for managing the Cassandra Testcontainers container.
 */
public class CassandraContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final Logger log = LoggerFactory.getLogger(CassandraContainerInitializer.class);

    private static CassandraContainer cassandraContainer = new CassandraContainer(DockerImageName.parse("cassandra:3.11.2"));

    static {
        log.info("Starting Cassandra container...");
        cassandraContainer.start();
    }

    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        log.info("Initializing Cassandra container...");
        log.info(String.format("Docker image name: %s", cassandraContainer.getDockerImageName()));
        log.info(String.format("Docker container ID: %s", cassandraContainer.getContainerId()));
        log.info(String.format("Docker container name: %s", cassandraContainer.getContainerName()));
//        TestPropertyValues.of(
//                String.format("spring.datasource.url=%s", cassandraContainer.getJdbcUrl()),
//                String.format("spring.datasource.username=%s", cassandraContainer.getUsername()),
//                String.format("spring.datasource.password=%s", cassandraContainer.getPassword())
//        ).applyTo(configurableApplicationContext.getEnvironment());

        Cluster cluster = cassandraContainer.getCluster();

        try (Session session = cluster.connect()) {
            final String createKeyspaceQuery = "CREATE KEYSPACE IF NOT EXISTS test WITH replication = {'class':'SimpleStrategy','replication_factor':'1'};";
            session.execute(createKeyspaceQuery);

            List<KeyspaceMetadata> keyspaces = session.getCluster().getMetadata().getKeyspaces();
            List<KeyspaceMetadata> filteredKeyspaces = keyspaces
                    .stream()
                    .filter(km -> km.getName().equals("test"))
                    .collect(Collectors.toList());

            log.info(String.format("Number of test keyspaces: %d", filteredKeyspaces.size()));
        }
    }
}
