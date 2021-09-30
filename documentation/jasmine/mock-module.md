# Mocking modules with Jasmine

## Introduction

There will be times that you want to mock a function or class method from a package that has been imported into the system under test and the only way to get access to that function or class method is via the module import. This recipe demonstrates how to do that when using ES6-style `import * as ...` syntax.

## Example

### System under test

Given the following class that represents a timestamp and uses `luxon` under the covers.

[Link to source file in GitHub](https://github.com/cebartling/test-driven/blob/main/angular/example1/src/app/models/core/application-date.ts)

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
    this.dateTime = DateTime.now().toUTC();
  }

  /**
   * Creates a new ApplicationDate that represents the current timestamp with the timezone set to UTC.
   */
  public static now(): ApplicationDate {
    return new ApplicationDate();
  }

  /**
   * Output this ApplicationDate in ISO8601 format.
   */
  public toISO8601Format() {
    const isoOptions = {
      includeOffset: true,
      includePrefix: false,
      suppressMilliseconds: false,
      suppressSeconds: false,
      format: 'extended',
    } as ToISOTimeOptions;
    return this.dateTime.toISO(isoOptions);
  }
}
```

### Jasmine specifications

We can use `import * as luxonExports from 'luxon'` to import all of the `export`s from the `luxon` module as properties of an object (in this case, the object is called `luxonExports`). That object give you access to the exported functions, classes and objects that can then be stubbed, mocked or spied in your spec/test. In the example below, the Luxon `DateTime.now()` static method is stubbed to return a hard-coded `DateTime` instance that is created in this specification.

[Link to source file in GitHub](https://github.com/cebartling/test-driven/blob/main/angular/example1/src/app/models/core/__tests__/application-date.spec.ts)

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

