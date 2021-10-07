# Using HttpClientTestingModule and HttpTestingController

## Introduction

Angular provides the `HttpClientTestingModule` and the `HttpTestingController` for controlling interactions with `HttpClient` methods in your Angular component and service Jasmine speciifcations. If you plan to use this support, you will be required to use the Angular `TestBed` to control dependency injection within your specification suite.

## Example

The following is an example of using the `HttpClientTestingModule` and the `HttpTestingController` to control the interaction with `HttpClient.get` method in a Angular service Jasmine speciifcation suite. A few things to note:

- The `HttpClientTestingModule` must be included in the `imports` array when configuring the testing module.
- The `HttpTestingController` instance can be accessed from the `TestBed` by injecting in into the specification suite: `httpTestingController = TestBed.inject(HttpTestingController);`
  - In earlier versions of Angular, `httpTestingController = TestBed.get(HttpTestingController);` was valid. The `get` function has be deprecated.
- Use an `afterEach` block and call `httpTestingController.verify();` to verify no other interactions with the mocked `HttpClient` injected into the service by the `TestBed`. 
- The `HttpTestingController` uses a combined expectation and verification of interaction syntax, which can be confusing. Thus, you have to call the SUT and then call the `HttpTestingController` instance to verify the call is made and to return the response to the caller (e.g. `httpTestingController.expectOne(expectedUrl).flush(featureCollection);`). This _idiom_ can be confusing when you are used to how Jasmine spies work, where the expectation is set independently of the interaction verification on the spy.
- In the example below, a done callback (`done: DoneFn`) is passed as a parameter to the `beforeEach` call. This is a Jasmine tool for asynchronous tests. The done callback is used to signal to Jasmine that a block of code is finished (_aka_ done). The same thing can be accomplished with `async`/`await` in the `beforeEach` call.

### Jasmine specification suite for the `EarthquakeDataService`

```typescript
import { DateTime } from 'luxon';
import { EarthquakeDataService } from '../earthquake-data.service';
import { FeatureCollection } from '../../models/earthquake/feature-collection';
import { featureCollection } from '../../__tests__/data/feature-collection-test-data';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

describe('EarthquakeDataService', () => {
  let service: EarthquakeDataService;

  describe('query', () => {
    describe('using HttpClientTestingModule', () => {
      let httpTestingController: HttpTestingController;

      beforeEach(() => {
        TestBed.configureTestingModule({
          imports: [HttpClientTestingModule],
          providers: [EarthquakeDataService],
        });
        service = TestBed.inject(EarthquakeDataService);
        httpTestingController = TestBed.inject(HttpTestingController);
      });

      afterEach(() => {
        httpTestingController.verify();
      });

      let captured: FeatureCollection;
      let capturedError: Error;
      const expectedUrl =
        'https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2021-08-01&endtime=2021-08-15';
      const startDateTime = DateTime.fromObject({ year: 2021, month: 8, day: 1 });
      const endDateTime = DateTime.fromObject({ year: 2021, month: 8, day: 15 });

      beforeEach((done: DoneFn) => {
        service.query(startDateTime, endDateTime).subscribe(
          (data) => {
            captured = data;
            done();
          },
          (error) => {
            capturedError = error;
            done();
          }
        );
        httpTestingController.expectOne(expectedUrl).flush(featureCollection);
      });

      it('should return the appropriate response', () => {
        expect(captured).toEqual(featureCollection);
      });

      it('should not throw an error', () => {
        expect(capturedError).toBeUndefined();
      });
    });
  });
});
```


### System under test

The system under test (SUT) is provided here for clarity and understanding the Jasmine specifications above.

```typescript
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DateTime } from 'luxon';
import { FeatureCollection } from '../models/earthquake/feature-collection';

const DATE_FORMAT = 'yyyy-MM-dd';
const FORMAT_GEOJSON = 'geojson';

@Injectable({
  providedIn: 'root',
})
export class EarthquakeDataService {
  constructor(private httpClient: HttpClient) {}

  /**
   * Query earthquake.usgs.gov for earthquake features.
   *
   * @param startDateTime
   * @param endDateTime
   * @return An Observable of type FeatureCollection.
   */
  query(startDateTime: DateTime, endDateTime: DateTime): Observable<FeatureCollection> {
    const params = new HttpParams()
      .set('format', FORMAT_GEOJSON)
      .set('starttime', startDateTime.toFormat(DATE_FORMAT))
      .set('endtime', endDateTime.toFormat(DATE_FORMAT));
    return this.httpClient.get<FeatureCollection>('https://earthquake.usgs.gov/fdsnws/event/1/query', { params });
  }
}
```

