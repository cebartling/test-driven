# Spring WebFlux

## Introduction

Spring Framework version 5.0 introduced an asynchronous/non-blocking HTTP client with a fluent functional style API reactive web framework called **Spring WebFlux**. This API includes `WebClient`, the new reactive HTTP client. This documentation details how to write JUnit 5 tests using the `SpringExtension` and `@WebFluxTest` support in Spring Framework 5 and Spring Boot 2.


## Example

### Configuring JUnit 5 unit tests for Spring WebFlux

Spring provides special testing support for WebFlux. In our example, we are using JUnit 5 and Spring Boot 2.x. The following code changes need to be added to the test suite source code.

1. The test suite class `EmployeeControllerTest` is annotated with `@ExtendWith(SpringExtension.class)`. 
    - The `SpringExtension.class` is a special JUnit 5 Extension API implementation that Spring Framework provides to integrates the _Spring TestContext Framework_ into JUnit 5's Jupiter programming model. More information about Spring's testing support can be found [here](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html).
1. The test suite class `EmployeeControllerTest` is annotated with `@WebFluxTest(controllers = EmployeeController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class})`. 
    - The `@WebFluxTest` annotation is be used for Spring WebFlux unit testing that focuses only on Spring WebFlux components. 
        - Using this annotation will disable full auto-configuration and instead apply only configuration relevant to WebFlux tests (i.e. `@Controller`, `@ControllerAdvice`, `@JsonComponent`, `Converter`/`GenericConverter`, and `WebFluxConfigurer` beans but not `@Component`, `@Service` or `@Repository` beans).
        - By default, tests annotated with `@WebFluxTest` will also auto-configure a `WebTestClient`. For more fine-grained control of WebTestClient the `@AutoConfigureWebTestClient` annotation can be used.
        - Typically `@WebFluxTest` is used in combination with `@MockBean` or `@Import` to create any collaborators required by your `@Controller` beans.

```java
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class,
        excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
@DisplayName("EmployeeController unit tests")
class EmployeeControllerTest {
    ...
}
```

### Injecting the `WebTestClient` instance

As mentioned above, an instance is auto-configured for you to inject into your unit tests. This is accomplished with the use of the `@Autowired` annotation on an instance variable with a declared type of `WebTestClient`. The Spring TestContext Framework will inject an instance by matching the variable's type.


```java
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class,
        excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
@DisplayName("EmployeeController unit tests")
class EmployeeControllerTest {

    @Autowired
    WebTestClient webTestClient;

    ...
}
```

### Injecting other dependencies into the SUT

Use the `@MockBean` annotation in your test suite class to declare mock dependencies. The Spring TestContext Framework will handle creating mock instances of these dependency types and injecting them into your SUT. A best practice with Spring Framework is to use _constructor-based dependency injection_ over other forms of injection. 

```java
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class,
        excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
@Tag(TestSupport.UNIT_TEST)
@DisplayName("EmployeeController unit tests")
class EmployeeControllerTest {

    @MockBean
    EmployeeRepository employeeRepository;

    @MockBean
    EmployeeService employeeService;

    @Autowired
    WebTestClient webTestClient;

    ...
}
```


### Complete solution

The complete test and SUT can be found in this repository: 

- [JUnit 5 controller test suite demonstrating Spring WebFlux testing](https://github.com/cebartling/test-driven/blob/main/spring-boot/webflux-tdd-demo/src/test/java/com/pintailconsultingllc/webflux/demo/controllers/EmployeeControllerTest.java)
- [System under test (SUT)](https://github.com/cebartling/test-driven/blob/main/spring-boot/webflux-tdd-demo/src/main/java/com/pintailconsultingllc/webflux/demo/controllers/EmployeeController.java)

