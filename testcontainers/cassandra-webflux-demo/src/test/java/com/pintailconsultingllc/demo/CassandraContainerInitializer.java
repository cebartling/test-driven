package com.pintailconsultingllc.demo;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
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
    private static final String KEYSPACE_NAME = "demo";
    private static final String DATA_CENTER = "datacenter1";
    private static final String SCHEMA_ACTION = "create_if_not_exists";
    private static final int EXPOSED_PORT = 9042;
    private static final String CREATE_KEYSPACE_QUERY =
            String.format("CREATE KEYSPACE IF NOT EXISTS %s WITH replication = {'class':'SimpleStrategy','replication_factor':'1'};",
                    KEYSPACE_NAME);


    private static CassandraContainer cassandraContainer = (CassandraContainer) new CassandraContainer(DockerImageName.parse("cassandra:3.11.2"))
            .withExposedPorts(EXPOSED_PORT);

    static {
        log.info("Starting Cassandra container...");
        cassandraContainer.start();
    }

    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        log.info("Initializing Cassandra container...");
        log.info(String.format("Docker image name: %s", cassandraContainer.getDockerImageName()));
        log.info(String.format("Docker container ID: %s", cassandraContainer.getContainerId()));
        log.info(String.format("Docker container name: %s", cassandraContainer.getContainerName()));
        TestPropertyValues.of(
                String.format("spring.data.cassandra.keyspace-name=%s", KEYSPACE_NAME),
                String.format("spring.data.cassandra.contact-points=%s", cassandraContainer.getContainerIpAddress()),
                String.format("spring.data.cassandra.port=%s", cassandraContainer.getMappedPort(EXPOSED_PORT)),
                String.format("spring.data.cassandra.local-datacenter=%s", DATA_CENTER),
                String.format("spring.data.cassandra.schema-action=%s", SCHEMA_ACTION)
        ).applyTo(configurableApplicationContext.getEnvironment());

        Cluster cluster = cassandraContainer.getCluster();

        try (Session session = cluster.connect()) {
            session.execute(CREATE_KEYSPACE_QUERY);

            List<KeyspaceMetadata> keyspaces = session.getCluster().getMetadata().getKeyspaces();
            List<KeyspaceMetadata> filteredKeyspaces = keyspaces
                    .stream()
                    .filter(km -> km.getName().equals(KEYSPACE_NAME))
                    .collect(Collectors.toList());

            log.info(String.format("Number of %s keyspaces: %d", KEYSPACE_NAME, filteredKeyspaces.size()));
        }
    }
}
