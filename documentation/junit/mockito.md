# Mockito and JUnit

Mockito is the most popular test double library for Java-based environments. The following documentation demonstrates how to use the library with JUnit 4 and JUnit 5.

## JUnit 4

The `MockitoJUnitRunner` to instruct Mockito to initialize our test doubles annotated with `@Mock` or `@Spy` and to create and wire up the SUT using the `@InjectMocks` annotation. Using `MockitoJUnitRunner` also gives you automatic validation of framework usage, as well as an automatic `initMocks()`.

```java
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTestSuite {
    ...
}
```

Make sure you use the right MockitoJUnitRunner class. It was moved, as described in this [post](https://www.baeldung.com/mockito-deprecated-mockitojunitrunner).


## JUnit 5

JUnit 5 introduced an extension model, with the purpose of extending the behavior of test classes or methods and promoting reuse across multiple tests.JUnit 5 simplifies the extension mechanism by introducing a single concept: the Extension API. JUnit 5 extensions are related to a certain event in the execution of a test, referred to as an _extension point_. When a test event is reached, the JUnit engine calls any registered extensions for that event.

Five main types of extension points can be used:

1. test instance post-processing
1. conditional test execution
1. life-cycle callbacks
1. parameter resolution
1. exception handling

More information regarding the JUnit 5 Extension API can be found [here](https://www.baeldung.com/junit-5-extensions). 

Mockito now provides a JUnit 5 extension that works with this Extension API, named `org.mockito.junit.jupiter.MockitoExtension`. Use the `@ExtendWith()` JUnit 5 annotation to specify the Mockito extension should be used for the test suite.

```java
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmployeeService unit tests")
class EmployeeServiceTestSuite {
    ...
}
```


## Mocks and Dependency Injection

### `@Mock` and `@InjectMocks` 

Mockito works the exactly the same way whether you are using JUnit 4 and the `MockitoJUnitRunner` or JUnit 5 and the `MockitoExtension`. In the code snippet below, I have omitted the class level annotation for the runner or extension and just focused on creating a mock `EmployeeRepository` instance and the system under test (SUT), `EmployeeServiceImpl`. Annotating a local variable with `@Mock` will automatically create a mock implementation of that type. Prefer to use interface types here. One or more mocks may be specified this way. Finally, the system under test implementation type is created with the `@InjectMocks` annotation. The `@InjectMocks` annotation handles constructor dependency injection, using the mocks created in the test and matching my type. 

```java
import org.mockito.InjectMocks;
import org.mockito.Mock;

class EmployeeServiceTestSuite {

    @Mock
    EmployeeRepository employeeRepositoryMock;

    @InjectMocks
    EmployeeServiceImpl service;

    ...
}
```

In the example above, the SUT is the `service` variable annotated with the `@InjectMocks` annotation. I do have to specify the implementation type here, `EmployeeServiceImpl`, so that Mockito can instantiate the concrete class and determine which mocked dependencies to use when calling the constructor. All of the dependencies that are meant to be injected can be annotated with the `@Mock` annotation and you should strive to use interface types when specifying your dependencies. Your SUT implementation should not know about specific implementation types.


## Mock expectations and verification

### Setting mock expectations

Mockito mocks must have their interaction expectations set before exercising the SUT. I like to do this in a setup method for the JUnit test (`EmployeeServiceImplTest$UpdateSpecifications.doBeforeEachTest`). In the example below, I have two such interaction expectations that I am configuring: 

```java
@BeforeEach
public void doBeforeEachTest() {
    employeeDTO = EmployeeDTO.builder().id(ID).name("Joe Smith").salary(45000).build();
    expectedEmployee = new Employee(employeeDTO);
    when(employeeRepositoryMock.findById(ID)).thenReturn(Mono.just(expectedEmployee));
    when(employeeRepositoryMock.save(any(Employee.class))).thenReturn(Mono.just(expectedEmployee));
    employeeMono = service.update(employeeDTO.getId(), employeeDTO);
    StepVerifier.create(employeeMono)
            .consumeNextWith(employee -> actualEmployee = employee)
            .verifyComplete();
}
```

The mock interaction expectations use the static `when` or `doNothing` methods from Mockito to configure the interaction expectations on the `employeeRepositoryMock` mock object. Use argument matchers to specify the arguments for those interactions. In the first example, I am using strict argument matching for the `findById` interaction. In the second interaction, I am using a more lenient `any(Class)` argument matcher. More information on argument matchers can be found [here](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#3).


### Verifying mock interactions

After exercising the SUT, you can verify the interactions with collaborators by introspecting the mocked implementations. This is typically done in the test itself. The following two tests demonstrate verifying the mock interaction expectations were met by exercising the SUT. Again, use the appropriate argument matchers when verifying the interactions with the mocked collaborator.

```java
@Test
@DisplayName("should invoke EmployeeRepository.findById")
void verifyRepositoryFindByIdInvocationTest() {
    verify(employeeRepositoryMock).findById(ID);
}

@Test
@DisplayName("should invoke EmployeeRepository.save")
void verifyRepositorySaveInvocationTest() {
    verify(employeeRepositoryMock).save(any(Employee.class));
}
```

### Completed example

[Link to the test suite in GitHub](https://github.com/cebartling/test-driven/blob/main/spring-boot/webflux-tdd-demo/src/test/java/com/pintailconsultingllc/webflux/demo/services/implementations/EmployeeServiceImplTest.java)


```java
import com.pintailconsultingllc.webflux.demo.TestSupport;
import com.pintailconsultingllc.webflux.demo.dtos.EmployeeDTO;
import com.pintailconsultingllc.webflux.demo.entities.Employee;
import com.pintailconsultingllc.webflux.demo.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmployeeServiceImpl unit tests")
@Tag(TestSupport.UNIT_TEST)
class EmployeeServiceImplTest {

    public static final int ID = 3468;

    @Mock
    EmployeeRepository employeeRepositoryMock;

    @InjectMocks
    EmployeeServiceImpl service;

    @Nested
    @DisplayName("update method")
    class UpdateSpecifications {
        EmployeeDTO employeeDTO;
        Mono<Employee> employeeMono;
        Employee expectedEmployee;
        Employee actualEmployee;

        @BeforeEach
        public void doBeforeEachTest() {
            employeeDTO = EmployeeDTO.builder().id(ID).name("Joe Smith").salary(45000).build();
            expectedEmployee = new Employee(employeeDTO);
            when(employeeRepositoryMock.findById(ID)).thenReturn(Mono.just(expectedEmployee));
            when(employeeRepositoryMock.save(any(Employee.class))).thenReturn(Mono.just(expectedEmployee));
            employeeMono = service.update(employeeDTO.getId(), employeeDTO);
            StepVerifier.create(employeeMono)
                    .consumeNextWith(employee -> actualEmployee = employee)
                    .verifyComplete();
        }

        @Test
        @DisplayName("should invoke EmployeeRepository.findById")
        void verifyRepositoryFindByIdInvocationTest() {
            verify(employeeRepositoryMock).findById(ID);
        }

        @Test
        @DisplayName("should invoke EmployeeRepository.save")
        void verifyRepositorySaveInvocationTest() {
            verify(employeeRepositoryMock).save(any(Employee.class));
        }

        @Test
        @DisplayName("returns a Mono containing the updated Employee instance")
        void ReturnsEmployeeMonoTest() {
            assertEquals(actualEmployee, expectedEmployee);
        }
    }
}
```
