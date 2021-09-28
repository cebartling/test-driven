# Jasmine 

## Common testing patterns

### Structuring tests

```typescript
describe('System under test', () => {

    let sut: SystemUnderTest;

    beforeEach(() => {
        // Use this for setting up the system under test and any test doubles.
        sut = new SystemUnderTest();
    });

    describe('function or method under test', () => {
        // Replace T with actual type.
        let result: T;     

        beforeEach(() => {
            // Use this for setting up context for this set of examples.
            // Execute SUT here also, capturing direct output as result.
            result = sut.doSomething();
        });

        it('should verify something, either direct or indirect', () => {
            expect(result).toEqual();
        });
    });
});
```


## Test double patterns

- [Mocking a module via ES6 import](./mock-module.md)
