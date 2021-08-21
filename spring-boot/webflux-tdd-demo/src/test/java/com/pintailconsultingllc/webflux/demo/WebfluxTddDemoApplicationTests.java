package com.pintailconsultingllc.webflux.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Tag("UnitTest")
@DisplayName("WebfluxTddDemoApplication unit tests")
class WebfluxTddDemoApplicationTests {

    @Test
    @DisplayName("application context should load")
    void contextLoads() {
    }
}
