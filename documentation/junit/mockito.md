# Mockito and JUnit

Mockito is the most popular test double library for Java-based environments. The following documentation 

## JUnit 4

The `MockitoJUnitRunner` to instruct Mockito to initialize our test doubles annotated with `@Mock` or `@Spy` and to create and wire up the SUT using the `@InjectMocks` annotation. Using `MockitoJUnitRunner` also gives you automatic validation of framework usage, as well as an automatic `initMocks()`.

```java
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MyTestSuite {
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
@DisplayName("MyTestSuite unit tests")
class MyTestSuite {
    ...
}
```


## Mocks and Dependency Injection

### `@Mock`


### `@InjectMocks`

