# Mockito and JUnit

Mockito is the most popular test double library for Java-based environments.

## JUnit 4

### `MockitoJUnitRunner`

The `MockitoJUnitRunner` to instruct Mockito to initialize our test doubles annotated with `@Mock` or `@Spy` and to create and wire up the SUT using the `@InjectMocks` annotation. `MockitoJUnitRunner` gives you automatic validation of framework usage, as well as an automatic `initMocks()`.



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



## Mocks and Dependency Injection


