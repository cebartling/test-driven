# Testcontainers Java Demo

## Overview of the project

This Java project demonstrates the use of [Testcontainers](https://www.testcontainers.org/) to facilitate integration testing.
Testcontainers is a Java library that supports JUnit 4/5 and Spock tests, providing lightweight, throwaway instances of common databases, Selenium web browsers, or anything else that can run in a Docker container.

This example is pretty simple; a single PostgreSQL database is the only external dependency that is managed by Testcontainers within JUnit 5 integration tests.

## Development

This project uses Java 11 SDK. The easiest way to manage your Java SDK versions is to use [SDKMAN](https://sdkman.io/).
This project uses [Gradle](https://gradle.org/) as the project build tool. The easiest way to exercise the tests in this project is to run them from the command line via `gradlew`, the Gradle wrapper. 

Use `./gradlew build` from the project's root directory to build the project and run all the tests. This will run both unit and integration tests.





