package com.pintailconsultingllc.webflux.demo;

public class TestSupport {
    // Mongo settings
    public static final String DOCKER_NAME_MONGO = "mongo:5.0.3";
    public static final int MONGO_EXPOSED_PORT = 27017;
    public static final String PROPERTY_SPRING_DATA_MONGODB_URI = "spring.data.mongodb.uri";

    // Keycloak settings
    public static final String DOCKER_NAME_KEYCLOAK = "mihaibob/keycloak:15.0.1";
    public static final String KEYCLOAK_REALM_IMPORT_JSON = "keycloak.json";
    public static final String KEYCLOAK_ADMIN_USERNAME = "admin";
    public static final String KEYCLOAK_ADMIN_PASSWORD = "admin";
    public static final String KEYCLOAK_AUTH_SERVER_URL_REGISTRY_KEY = "keycloak.auth-server-url";
    public static final String KEYCLOAK_AUTH_URI = "/auth";
    public static final String KEYCLOAK_REALM = "example";
    public static final String KEYCLOAK_CLIENT_ID = "example-client";


    // JUnit 5 Jupiter tags
    public static final String UNIT_TEST = "UnitTest";
    public static final String INTEGRATION_TEST = "IntegrationTest";
}
