## Test-Driven Development with JUnit 5
### Christopher Bartling

---

## JUnit 5

- JUnit 5 is the next generation of JUnit. 
- The goal is to create an up-to-date foundation for developer-side testing on the JVM. 
- This includes focusing on Java 8 and above, as well as enabling many different styles of testing.

---

## What is JUnit 5?

- JUnit 5 is composed of several different modules from three different sub-projects.
1. JUnit Platform: The JUnit Platform serves as a foundation for launching testing frameworks on the JVM. It also defines the `TestEngine` API for developing a testing framework that runs on the platform. Furthermore, the platform provides a `ConsoleLauncher` to launch the platform from the command line and a JUnit 4-based Runner for running any `TestEngine` on the platform in a JUnit 4 based environment. First-class support for the JUnit Platform also exists in popular IDEs (see IntelliJ IDEA, Eclipse, NetBeans, and Visual Studio Code) and build tools (see Gradle, Maven, and Ant).
1. JUnit Jupiter: JUnit Jupiter is the combination of the new programming model and extension model for writing tests and extensions in JUnit 5. The Jupiter sub-project provides a `TestEngine` for running Jupiter-based tests on the platform.
1. JUnit Vintage: JUnit Vintage provides a `TestEngine` for running JUnit 3 and JUnit 4 based tests on the platform.


---

## 

```java
package example.util;

import org.junit.jupiter.api.Test;
import example.util.Calculator;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTests {

    private final Calculator calculator = new Calculator();

    @Test
    void addition() {
        assertEquals(2, calculator.add(1, 1));
    }

}
```
---

## @Test annotation

- Denotes that a method is a test method. 
- Unlike JUnit 4’s `@Test` annotation, this annotation does not declare any attributes, since test extensions in JUnit Jupiter operate based on their own dedicated annotations. 

---

## @ParameterizedTest annotation

- Parameterized tests make it possible to run a test multiple times with different arguments. 
- They are declared just like regular `@Test` methods but use the `@ParameterizedTest` annotation instead. 
- In addition, you must declare at least one source that will provide the arguments for each invocation and then consume the arguments in the test method.

---

## Nested tests

- Test classes with a large number of test cases can easily become unmaintainable. 
- JUnit Jupiter offers the concept of "nested tests" to avoid the need for breaking up a test class into multiple classes that group functionally-similar test cases. 
- In this scenario, you will learn about the annotation `@Nested` that drives nested tests.



---

## Title

- Point one
- Point two
- Point three



