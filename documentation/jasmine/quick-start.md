# Jasmine quick start

[Jasmine](https://jasmine.github.io/) is a behavior-driven development (BDD) testing library that developers use to create specifications (_aka_ tests) for JavaScript and Typescript code bases. This page details how to use the Jasmine domain-specific language (DSL) to create communicative specifications/tests for your JavaScript and Typescript projects.

## Structuring specifications/tests

### The `describe` function

Everthing in Jasmine begins with the [`describe`](https://jasmine.github.io/api/edge/global.html#describe), which _describes_ a test suite or group. A Jasmine spec file will have a top-level `describe` that contains all the test suites and tests for the system under test (SUT). The `describe` can be used in a nested fashion, so you can group tests together that share a common context. In the code snippet below, we have a spec file that contains a single, top-level `describe`.

```typescript
describe('SystemUnderTest', () => {});
```

The string parameter contains a message describing the test suite. As you nest `describe` suites, this message can be tweaked to make your test suites communicate context for the specific suite. As written, this suite contains no tests.


### Referencing the system under test

Next, we need to keep a reference to the system under test or the SUT. This is usually an object. This next code snippet shows how to keep a reference to the SUT by declaring a local variable for it.

```typescript
describe('SystemUnderTest', () => {

    let sut: SystemUnderTest;

});
```

### Setup functions for Jasmine specifications

If we want to set up an individual specification, we can use the `beforeEach` function from Jasmine's DSL to do just that. This next code snippet shows how to use the `beforeEach` function within our test suite, creating a new `SystemUnderTest` instance for each specification/test.

```typescript
describe('SystemUnderTest', () => {

    let sut: SystemUnderTest;

    beforeEach(() => {
        // Use this for setting up the system under test and any test doubles.
        sut = new SystemUnderTest();
    });
});
```

### Creating nested suites

Hierarchical specification suites is a good way to structure your spec files. The `describe` function is meant to be used multiple times within a spec file to create suites for methods or functions. By doing this, you can optimize your test context and customize how a suite is setup and torn down. This next code snippet shows a nested suite for a method on the type `SystemUnderTest`.

```typescript
describe('SystemUnderTest', () => {

    let sut: SystemUnderTest;

    beforeEach(() => {
        // Use this for setting up the system under test and any test doubles.
        sut = new SystemUnderTest();
    });

    describe('doSomething method', () => {

    });
});
```

I highly recommend structuring your spec files with multiple `describe`s to build a hierarchical context for each suite. It will make your spec files easier to read and understand. It will also allow you to reuse context setup and keep your specs DRY. Setup and teardown functions can be specified in the nested `describe` code blocks:

```typescript
describe('SystemUnderTest', () => {

    let sut: SystemUnderTest;

    beforeEach(() => {
        // Use this for setting up the system under test and any test doubles.
        sut = new SystemUnderTest();
    });

    describe('doSomething method', () => {

        let result: Result;     

        beforeEach(() => {
            // Use this for setting up context for this set of examples.
            // Execute SUT here also, capturing direct output as result.
            result = sut.doSomething();
        });
    });
});
```

In the previous code snippet, I declared another local variable for the `result`. This variable will capture the direct output from the execution of the `doSomething` method on the `sut` object. I execute the SUT in the `beforeEach` code block. This is an idiom I typically follow--execute the SUT in the setup of the tests. Again, just trying to keep my tests DRY.

### The first specification

A specification is a test of verification and in Jasmine, we use the `it` function to specify a specification. This next code snippet shows a single specification that is verifying that the result from the `doSomething` invocation is successful. Notice that I created a constant `Result` object, named `expected`, that represents the successful result state that I want to verify the actual `result` to. This is another idiom that I use in my specifications/tests. 

```typescript
describe('SystemUnderTest', () => {

    let sut: SystemUnderTest;

    beforeEach(() => {
        // Use this for setting up the system under test and any test doubles.
        sut = new SystemUnderTest();
    });

    describe('doSomething method', () => {

        let result: Result;  
        const expected = new Result(true);    

        beforeEach(() => {
            // Use this for setting up context for this set of examples.
            // Execute SUT here also, capturing direct output as result.
            result = sut.doSomething();
        });

        it('should return a successful result', () => {
            expect(result).toEqual(expected);
        });
    });
});
```

