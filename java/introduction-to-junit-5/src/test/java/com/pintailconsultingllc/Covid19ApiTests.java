package com.pintailconsultingllc;

import com.pintailconsultingllc.testing.annotations.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.net.http.HttpResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * This test suite demonstrates two JUnit 5 features: the use of custom composed tags and the use of
 * assumptions in your test suites and tests. The use of the IntegrationTest tag is used in this
 * test suite. There is an assumption being used to check that the RapidApiKey value is set
 * as an environment variable.
 */
@DisplayName("Covid19 API test suite")
public class Covid19ApiTests {

    Covid19Api sut;

    @BeforeEach
    public void doBeforeEachTest() {
        sut = new Covid19Api();
    }

    @Nested
    @DisplayName("integration tests")
    class IntegrationTests {

        HttpResponse<String> httpResponse;

        /**
         * JUnit 5 assumption specified here, checking to ensure that the RapidApiKey
         * environment variable is set.
         */
        void assumptions() {
            // Assumption is that the RapidApiKey is available.
            String rapidApiKey = System.getenv(Covid19Api.ENV_VAR_RAPID_API_KEY);
            assumeTrue(rapidApiKey != null && !rapidApiKey.isEmpty(),
                    "The RapidApiKey is not available as an environment variable.");
        }

        @BeforeEach
        public void doBeforeEachTest() {
            assumptions();
            httpResponse = sut.dataByCountryCode("us");
        }

        /**
         * This test is tagged as an integration test and thus can be grouped with other integration
         * tests.
         */
        @IntegrationTest
        @DisplayName("success")
        public void SuccessfulTest() {
            assertThat(httpResponse.body(), is(notNullValue()));
        }
    }
}
