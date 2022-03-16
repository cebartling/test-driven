package com.pintailconsultingllc.demo;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.DockerClientFactory;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class DockerTestSupport {

    @BeforeAll
    public static void doBeforeTestSuite() {
        assumeTrue(DockerClientFactory.instance().isDockerAvailable(), "Docker is not available for integration testing!");
    }
}
