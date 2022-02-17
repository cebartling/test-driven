# Typescript support for Jasmine 

## Introduction

Jasmine has type definitions and you should really use them when using Typescript. The following list is a just a sample of what is provided.

- `const httpSpyObj: jasmine.SpyObj<Http> = ...`
- `spyOn<MyType>(...)`
- `jasmine.createSpy<MyType>(...)`
- `jasmine.createSpyObj<MyType>(...)`
- `spyOnProperty<MyType>(...)`
- `expect<MyResultType>(...).toBe(...)`

To install the type definitions for Jasmine into your project, execute the following:

```
npm install --save-dev @types/jasmine
```


## Example usage

The following example demonstrates how to specify the type for the created `jasmine.SpyObj` object. Specifying the type will constrain the method strings used in the `jasmine.createSpyObj(...)` invocation, ensuring only existing functions on the `HttpClient` type can be spied. Very handy and prevents mistakes made when writing Jasmine specifications.

```typescript
describe('using Jasmine spy to mock HttpClient', () => {
    let httpClientSpyObject: jasmine.SpyObj<HttpClient>;
    let service: MyService;

    beforeEach(() => {
        httpClientSpyObject = jasmine.createSpyObj<HttpClient>('HttpClient', ['get', 'post']);
        TestBed.configureTestingModule({
            providers: [
                MyService, 
                { provide: HttpClient, useValue: httpClientSpyObject }
            ],
        });
        service = TestBed.inject(MyService);
    });

    // The rest of the test suite has been redacted for clarity

});    
```

## References
- [Type definitions for Jasmine](https://github.com/DefinitelyTyped/DefinitelyTyped/blob/master/types/jasmine/index.d.ts)
