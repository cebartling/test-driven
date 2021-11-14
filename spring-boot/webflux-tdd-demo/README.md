# Spring WebFlux/Project Reactor TDD Demonstration

## Prerequisites 

- Java 11 SDK 
- Docker

## Configuring the Java environment and IntelliJ IDEA project

1. Install a Java 11 JDK. I use [SDKMAN](https://sdkman.io/), so this is super easy: `sdk install java 11.0.11.hs-adpt`
2. Use this Java 11 JDK in the current shell environment: `sdk use java 11.0.11.hs-adpt`
3. Run the Gradle `test` task: `./gradlew test`
4. Open IntelliJ IDEA in the current directory (requires that the IntelliJ IDEA shell scripts have been installed): `idea .`

### Gradle VM in IntelliJ IDEA

Make sure you set the *Gradle VM* in your IntelliJ IDEA project to Java 11 JDK. 

![Gradle preferences](./documentation/images/gradle-preferences.png)


## Tests

- The `UnitTest` tag is used on unit test suites to tag them as unit tests. You can use this tag to run a subset of JUnit 5 Jupiter tests in this project.
- The `IntegrationTest` tag is used on integration test suites to tag them as integration tests. You can use this tag to run a subset of JUnit 5 Jupiter tests in this project.

![Unit tests Run Configuration in IntelliJ IDEA](./documentation/images/unit-tests-run-configuration.png)

The Gradle build is also configured to use tags:
- `./gradlew test` will only run unit tests.
- `./gradlew integrationTest` will only run integration tests.

## Integration testing

- This project uses Testcontainers to control Mongo DB using Docker. 
- Testcontainers is a Java library that supports JUnit tests, providing lightweight, throwaway instances of common databases, Selenium web browsers, or anything else that can run in a Docker container.

### Repository integration tests

- All of the repositories extend the `ReactiveMongoRepository` interface. 
- Testcontainers support is enabled by the `@Testcontainers` annotation. 



```java
@Testcontainers
@ExtendWith(SpringExtension.class)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@Tag(TestSupport.INTEGRATION_TEST)
@DisplayName("EmployeeRepository integration tests")
class EmployeeRepositoryIntegrationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse(DOCKER_NAME_MONGO))
            .withExposedPorts(27017);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    EmployeeRepository employeeRepository;

    ...
}
```

