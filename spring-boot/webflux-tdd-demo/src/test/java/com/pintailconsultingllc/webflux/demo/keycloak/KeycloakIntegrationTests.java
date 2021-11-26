package com.pintailconsultingllc.webflux.demo.keycloak;

import com.pintailconsultingllc.webflux.demo.TestSupport;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.pintailconsultingllc.webflux.demo.TestSupport.DOCKER_NAME_KEYCLOAK;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers(disabledWithoutDocker = true)
@Tag(TestSupport.INTEGRATION_TEST)
class KeycloakIntegrationTests {

    @Container
    static KeycloakContainer keycloakContainer = new KeycloakContainer(DOCKER_NAME_KEYCLOAK)
            .withRealmImportFile("keycloak-realm.json")
            .withAdminUsername("admin")
            .withAdminPassword("admin");

    @DynamicPropertySource
    public static void registerKeycloakProperties(DynamicPropertyRegistry registry) {
        registry.add("keycloak.auth-server-url", keycloakContainer::getAuthServerUrl);
    }

    @Test
    @DisplayName("verify that Keycloak is running")
    void VerifyKeycloakIsRunningTest() {
        assertTrue(keycloakContainer.isRunning());
    }

    @Test
    @DisplayName("verify that Keycloak is created")
    void VerifyKeycloakIsCreatedTest() {
        assertTrue(keycloakContainer.isCreated());
    }

    @Test
    @DisplayName("verify that Keycloak is healthy")
    void VerifyKeycloakIsHealthyTest() {
        assertTrue(keycloakContainer.isHealthy());
    }

//    @Test
//    @DisplayName("verify that Keycloak is healthy")
//    void VerifyKeycloakIsHealthyTest() {
//        Keycloak keycloakAdminClient = KeycloakBuilder.builder()
//                .serverUrl(keycloakContainer.getAuthServerUrl())
//                .realm("example")
//                .clientId("example-client")
//                .username("admin")
//                .password("admin")
//                .build();
//
//        ServerInfoRepresentation serverInfo = keycloakAdminClient.serverInfo().getInfo();
//        assertThat(serverInfo, notNullValue());
//        assertThat(serverInfo.getSystemInfo().getVersion(), equalTo("14.0.0"));
//    }
}
