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

Our SUT looks like this now: 

```typescript
export class SystemUnderTest {

    constructor() {}

    public doSomething(): Result {
        return new Result(true);
    }
}
```

A spec suite is meant to contain one or more specifications/tests. I like to keep my specifications limited to one expectation or verification per specification. This ensures that the specification stays small and focused and clearly communicates what the specification is verifying. This idiom also allows all the verifications and expectations to be run, regardless of verification failures or errors. Grouping a number of verifications into a single specification can have the side effect of missing verifications when a prior verification in the specification fails.

## Working with collaborators

Rarely do we have functions that do a single thing without involving some collaboration with other functions or object methods. Thus, we need to be able to write code that uses collaborators, either functions or object that have their own responsibility. Functions can be imported and used directly in our own code. Angular uses dependency injection to inject collaborators through object constructors. I'll demonstrate both mechanisms here.

### Collaboration wiring through module importing

To demonstrate module importing collaboration, let's use the Luxon library as our collaborator. Luxon is a date/time library. We want to add the current UTC timestamp to our `Result`. This next code snippet shows the specification suite for testing the collaboration of Luxon in our SUT:

```typescript
import * as luxonExports from 'luxon';

describe('SystemUnderTest', () => {

    let sut: SystemUnderTest;

    beforeEach(() => {
        // Use this for setting up the system under test and any test doubles.
        sut = new SystemUnderTest();
    });

    describe('doSomething method', () => {

        let result: Result;  
        const hardCodedDateTime = luxonExports.DateTime.utc(2020, 9, 1, 0, 0, 0, 0);
        const expected = new Result(true, hardCodeDateTime);    

        beforeEach(() => {
            // Spy on the now function from the exported DateTime type. 
            // Return our own object from the specification suite context.
            jasmine.spyOn(luxonExports.DateTime, 'now').and.returnValue(hardCodedDateTime);
            // Execute SUT here also, capturing direct output as result.
            result = sut.doSomething();
        });

        it('should return a successful result', () => {
            expect(result).toEqual(expected);
        });

        it('should create a new timestamp via DateTime.now()', () => {
            expect(luxonExports.DateTime.now).toHaveBeenCalled();
        });

        it('should have a valid timestamp', () => {
            expect(result.timestamp).toEqual(expected.timestamp);
        });
    });
});
```

In this version of the specification suite, we are obtaining an object, named `luxonExports`, from the import of the `luxon` library containing all the exports from that library. We now have the opportunity to use Jasmine spies (via the `spyOn` function) to intercept invocations on these module exported functions. I am spying the static `now` method of the `DateTime` type, intercepting the invocation and supplying my own return value, `hardCodedDateTime`, which I control within the specification suite. I can then verify that invocation via the spying that I set up in the `beforeEach` block. The second specification in the spec suite above verifies that the spied function was indeed called. Finally, in this example, the newly minted timestamp is used to create the `Result` object and thus it can be verified as part of the direct output of the execution of the SUT.

The SUT now looks like this:

```typescript
import { DateTime } from 'luxon';

export class SystemUnderTest {

    constructor() {}

    public doSomething(): Result {
        return new Result(true, DateTime.now().toUTC());
    }
}
```

Using **ES6 imports** and getting access to all the exports from a module using the `import * as ....` syntax is very powerful test isolation technique. Remember, unit tests _test individual units_, so it's important to be able to isolate your units as best you can.


### Collaboration wiring through constructor dependency injection

Angular takes another approach to wiring up collaborators--**dependency injection**. Dependency injection is a form of dependency inversion, one of the SOLID design principles. The concept behind dependency inversion is that a class does not instantiate its collaborators. It either finds them through some sort of the keyed repository or it has an assembler that injects the dependencies into the class instance. Angular uses the injection method of dependency inversion to wire collaborators together, using the constructor to pass collaborators into a new instance. More information on Angular's dependency injection mechanism can be found [here](https://angular.io/guide/dependency-injection).

Let's use constructor dependency injection in our example and show how it can help us to easily isolate our units for unit testing. In this example, we have a type `FoobarService` that will have an instance injected into our `SystemUnderTest` instance during instantiation. This next code snippet shows the specification suite for testing this collaboration with our SUT:

```typescript
import * as luxonExports from 'luxon';

describe('SystemUnderTest', () => {

    let sut: SystemUnderTest;
    let foobarServiceMock: any;

    beforeEach(() => {
        // Create the FoobarService test double
        foobarServiceMock = jasmine.createSpyObject('foobarService', ['getPayload']);
        // Create the SUT, injecting the test double into the new instance via the constructor.
        sut = new SystemUnderTest(foobarServiceMock as FoobarService);
    });

    describe('doSomething method', () => {

        let result: Result;  
        const hardCodedDateTime = luxonExports.DateTime.utc(2020, 9, 1, 0, 0, 0, 0);
        const expectedPayload = [{id: 1}, {id: 13}];
        const expected = new Result(true, hardCodeDateTime);    

        beforeEach(() => {
            // Spy on the now function from the exported DateTime type. 
            // Return our own object from the specification suite context.
            jasmine.spyOn(luxonExports.DateTime, 'now').and.returnValue(hardCodedDateTime);
            foobarService.getPayload.and.returnValue(Promise.resolve(expectedPayload));
            // Execute SUT here also, capturing direct output as result.
            result = sut.doSomething();
        });

        it('should return a successful result', () => {
            expect(result).toEqual(expected);
        });

        it('should create a new timestamp via DateTime.now()', () => {
            expect(luxonExports.DateTime.now).toHaveBeenCalled();
        });

        it('should invoke FoobarService.getPayload method', () => {
            expect(foobarServiceMock.getPayload).toHaveBeenCalled();
        });

        it('should have a valid timestamp', () => {
            expect(result.timestamp).toEqual(expected.timestamp);
        });

        it('should have a valid payload', () => {
            expect(result.payload).toEqual(expectedPayload);
        });
    });
});
```

The SUT now looks like this:

```typescript
import { DateTime } from 'luxon';
import FoobarService from '../services/foobar.service';

export class SystemUnderTest {

    constructor(private foobarService: FoobarService) {}

    public async doSomething(): Result {
        const payload = await this.foobarService.getPayload();
        return new Result(true, DateTime.now().toUTC(), payload);
    }
}
```

In this example, I am 