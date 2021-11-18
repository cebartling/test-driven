package com.pintailconsultingllc.webflux.demo;

import com.pintailconsultingllc.webflux.demo.repositories.DepartmentRepository;
import com.pintailconsultingllc.webflux.demo.repositories.EmployeeRepository;
import com.pintailconsultingllc.webflux.demo.services.DepartmentService;
import com.pintailconsultingllc.webflux.demo.services.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
//@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration")
@Tag("UnitTest")
@DisplayName("WebfluxTddDemoApplication unit tests")
class WebfluxTddDemoApplicationTests {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    EmployeeService employeeService;

    @Test
    @DisplayName("application context should load")
    void contextLoads() {
        assertAll(
                () -> assertNotNull(departmentRepository),
                () -> assertNotNull(employeeRepository),
                () -> assertNotNull(departmentService),
                () -> assertNotNull(employeeService)
        );
    }
}
