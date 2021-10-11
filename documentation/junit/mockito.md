# Mockito and JUnit

Mockito is the most popular test double library for Java-based environments. The following documentation 

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

