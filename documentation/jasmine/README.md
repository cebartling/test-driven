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

### Mocking modules

#### Example

Given the following class that represents a timestamp and uses `luxon` under the covers.

```typescript
import { DateTime, ToISOTimeOptions } from 'luxon';

export class ApplicationDate {
  private dateTime: DateTime;

  /**
   * Use static factory method to create the appropriate instance of this class.
   *
   * @private
   */
  private constructor() {
    this.dateTime = DateTime.now();
  }

  /**
   * Creates a new ApplicationDate that represents the current timestamp.
   */
  public static now(): ApplicationDate {
    return new ApplicationDate();
  }

  /**
   * Output this ApplicationDate in ISO8601 format with the timezone set to UTC.
   */
  public toISO8601Format() {
    const isoOptions = {
      includeOffset: true,
      includePrefix: false,
      suppressMilliseconds: false,
      suppressSeconds: false,
      format: 'extended',
    } as ToISOTimeOptions;
    return this.dateTime.toUTC().toISO(isoOptions);
  }
}
```

We can use `import * as luxonExports from 'luxon'` to imports all exports from the `luxon` module as properties of an object (in this case, the object is called `luxonExports`). That object can then be stubbed, mocked or spied in your spec/test. In the example below, the Luxon `DateTime.now()` static method is stubbed to return a hard-coded `DateTime` instance that is created in this specification.


```typescript
import { ApplicationDate } from '../application-date';
import * as luxonExports from 'luxon';

describe('ApplicationDate class', () => {
  let applicationDate: ApplicationDate;

  describe('now static method', () => {
    beforeEach(() => {
      const hardCodedDateTime = luxonExports.DateTime.utc(2020, 9, 1, 0, 0, 0, 0);
      spyOn(luxonExports.DateTime, 'now').and.returnValue(hardCodedDateTime);
      applicationDate = ApplicationDate.now();
    });

    it('should create a new ApplicationDate', () => {
      expect(applicationDate).toBeDefined();
    });

    it('should represent the current time and date', () => {
      const expected = '2020-09-01T00:00:00.000Z';
      expect(applicationDate.toISO8601Format()).toEqual(expected);
    });
  });
});
```





### Mocking global variables


