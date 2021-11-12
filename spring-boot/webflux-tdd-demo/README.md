# Spring WebFlux/Project Reactor TDD Demonstration

## Prerequisites 

- Java 11 JDK 

## Configuring the Java environment and IntelliJ IDEA project

1. Install a Java 11 JDK. I use [SDKMAN](https://sdkman.io/), so this is super easy: `sdk install java 11.0.11.hs-adpt`
2. Use this Java 11 JDK in the current shell environment: `sdk use java 11.0.11.hs-adpt`
3. Run the Gradle `test` task: `./gradlew test`
4. Open IntelliJ IDEA in the current directory (requires that the IntelliJ IDEA shell scripts have been installed): `idea .`

### Gradle VM in IntelliJ IDEA

Make sure you set the *Gradle VM* in your IntelliJ IDEA project to Java 11 JDK. 

![Gradle preferences](./documentation/images/gradle-preferences.png)


## Running unit tests

The `UnitTest` tag is used on unit test suites to tag them as unit tests. You can use this tag to run a subset of JUnit 5 Jupiter tests in this project. 

![Unit tests Run Configuration in IntelliJ IDEA](./documentation/images/unit-tests-run-configuration.png)
