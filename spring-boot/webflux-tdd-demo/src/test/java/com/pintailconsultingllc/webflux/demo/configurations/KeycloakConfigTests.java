package com.pintailconsultingllc.webflux.demo.configurations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.keycloak.adapters.KeycloakConfigResolver;

import static com.pintailconsultingllc.webflux.demo.TestSupport.UNIT_TEST;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("KeycloakConfig unit tests")
@Tag(UNIT_TEST)
class KeycloakConfigTests {

    @Nested
    @DisplayName("KeycloakConfigResolver method")
    class KeycloakConfigResolverTests {
        KeycloakConfigResolver keycloakConfigResolver;

        @BeforeEach
        public void doBeforeEachTest() {
            keycloakConfigResolver = new KeycloakConfig().KeycloakConfigResolver();
        }

        @Test
        @DisplayName("should create a new instance of the KeycloakConfigResolver class")
        void verifyBeanCreationTest() {
            assertNotNull(keycloakConfigResolver);
        }
    }
}
