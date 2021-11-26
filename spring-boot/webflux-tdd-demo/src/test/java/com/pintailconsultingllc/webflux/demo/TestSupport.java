package com.pintailconsultingllc.webflux.demo;

public class TestSupport {
    public static final String DOCKER_NAME_MONGO = "mongo:5.0.3";
    public static final String DOCKER_NAME_KEYCLOAK = "mihaibob/keycloak:15.0.1";

    public static final int MONGO_EXPOSED_PORT = 27017;
    public static final String PROPERTY_SPRING_DATA_MONGODB_URI = "spring.data.mongodb.uri";

    public static final String UNIT_TEST = "UnitTest";
    public static final String INTEGRATION_TEST = "IntegrationTest";
}
