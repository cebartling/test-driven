package com.pintailconsultingllc.webflux.demo.keycloak;

import com.pintailconsultingllc.webflux.demo.TestSupport;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.info.ServerInfoRepresentation;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.pintailconsultingllc.webflux.demo.TestSupport.DOCKER_NAME_KEYCLOAK;
import static com.pintailconsultingllc.webflux.demo.TestSupport.KEYCLOAK_ADMIN_PASSWORD;
import static com.pintailconsultingllc.webflux.demo.TestSupport.KEYCLOAK_ADMIN_USERNAME;
import static com.pintailconsultingllc.webflux.demo.TestSupport.KEYCLOAK_AUTH_SERVER_URL_REGISTRY_KEY;
import static com.pintailconsultingllc.webflux.demo.TestSupport.KEYCLOAK_AUTH_URI;
import static com.pintailconsultingllc.webflux.demo.TestSupport.KEYCLOAK_CLIENT_ID;
import static com.pintailconsultingllc.webflux.demo.TestSupport.KEYCLOAK_REALM;
import static com.pintailconsultingllc.webflux.demo.TestSupport.KEYCLOAK_REALM_IMPORT_JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers(disabledWithoutDocker = true)
@EnableAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag(TestSupport.INTEGRATION_TEST)
class KeycloakIntegrationTests {

    @Container
    static KeycloakContainer keycloakContainer = new KeycloakContainer(DOCKER_NAME_KEYCLOAK)
            .waitingFor(Wait.forHttp(KEYCLOAK_AUTH_URI).forStatusCode(200))
            .withExposedPorts(8080)
            .withRealmImportFile(KEYCLOAK_REALM_IMPORT_JSON)
            .withAdminUsername(KEYCLOAK_ADMIN_USERNAME)
            .withAdminPassword(KEYCLOAK_ADMIN_PASSWORD);

    private Keycloak keycloakAdminClient;

    @DynamicPropertySource
    public static void registerKeycloakProperties(DynamicPropertyRegistry registry) {
        registry.add(KEYCLOAK_AUTH_SERVER_URL_REGISTRY_KEY, keycloakContainer::getAuthServerUrl);
    }

    @BeforeEach
    public void doBeforeEachTest() {
        System.out.printf("Keycloak auth URL: %s%n", keycloakContainer.getAuthServerUrl());
        final ResteasyClient resteasyClient = new ResteasyClientBuilder().connectionPoolSize(10).build();
        keycloakAdminClient = KeycloakBuilder.builder()
                .serverUrl(keycloakContainer.getAuthServerUrl())
                .grantType(OAuth2Constants.PASSWORD)
                .realm(KEYCLOAK_REALM)
                .clientId(KEYCLOAK_CLIENT_ID)
                .username(KEYCLOAK_ADMIN_USERNAME)
                .password(KEYCLOAK_ADMIN_PASSWORD)
                .resteasyClient(resteasyClient)
                .build();
    }

    @Test
    @DisplayName("verify that Keycloak is running")
    void verifyKeycloakIsRunning() {
        assertTrue(keycloakContainer.isRunning());
    }

    @Test
    @DisplayName("verify that Keycloak is created")
    void verifyKeycloakIsCreated() {
        assertTrue(keycloakContainer.isCreated());
    }

    @Test
    @DisplayName("verify Keycloak server information")
    @Disabled
    void verifyKeycloakServerInfo() {
        ServerInfoRepresentation serverInfo = keycloakAdminClient.serverInfo().getInfo();
        assertThat(serverInfo, notNullValue());
        assertThat(serverInfo.getSystemInfo().getVersion(), equalTo("14.0.0"));
    }

    @Test
    @DisplayName("verify access token from Keycloak admin client")
    @Disabled
    void verifyAccessToken() {
        final AccessTokenResponse accessToken = keycloakAdminClient.tokenManager().getAccessToken();
        assertThat(accessToken, notNullValue());
    }

    @Test
    @DisplayName("verify realm resource from Keycloak admin client")
    void verifyRealmResource() {
        final RealmResource realmResource = keycloakAdminClient.realm(KEYCLOAK_REALM);
        assertThat(realmResource, notNullValue());
    }
}
