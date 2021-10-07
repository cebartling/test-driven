# Using HttpClientTestingModule and HttpTestingController

## Introduction


## Example

### Jasmine specification suite

```typescript
import {TestBed} from '@angular/core/testing';
import {HttpClient} from '@angular/common/http';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {Observable, of, throwError} from 'rxjs';

import {FoobarService} from '../foobar.service';
import {HttpResponseService} from '../http-response.service';
import {REST_DIGITALCHECKOUT_API_URL} from '../../constants';


describe('FoobarService', () => {
  let service: FoobarService;
  let httpResponseServiceSpy: jasmine.SpyObj<HttpResponseService>;

  beforeEach(() => {
    httpResponseServiceSpy = jasmine.createSpyObj('HttpResponseService', ['handleError']);
  });

  describe('using HttpTestingController for HttpClient interactions', () => {
    let httpTestingController: HttpTestingController;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [
          FoobarService,
          {
            provide: HttpResponseService,
            useValue: httpResponseServiceSpy
          },
        ],
      });
      service = TestBed.inject(FoobarService);
      httpTestingController = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
      httpTestingController.verify();
    });

    describe('generateUUID', () => {
      describe('when successful', () => {
        const expectedUUID = '0cf762ad-3077-43e7-9f9c-2baa98222f5c';

        it('should return an Observable with the UUID', () => {
          service.generateUUID().subscribe((nextValue: any) => {
            expect(nextValue).toEqual(expectedUUID);
          });
          httpTestingController.expectOne(REST_DIGITALCHECKOUT_API_URL).flush(expectedUUID);
        });
      });

      describe('when unsuccessful', () => {
        const expectedErrorMessage = 'Testing error message';

        beforeEach(() => {
          httpResponseServiceSpy.handleError.and.returnValue(throwError(Error(expectedErrorMessage)));
        });

        it('should call the HttpResponseService.handleError function', () => {
          service.generateUUID().subscribe(
            (nextValue) => {
              fail('Next handler should not be invoked!');
            },
            (error) => {
              expect(httpResponseServiceSpy.handleError).toHaveBeenCalledTimes(1);
              expect(error.message).toEqual(expectedErrorMessage);
            },
            () => {
              fail('Complete handler should not be invoked!');
            },
          );
          httpTestingController.expectOne(REST_DIGITALCHECKOUT_API_URL).flush('error', {
            status: 400,
            statusText: 'Bad Request'
          });
        });
      });
    });
  });

  describe('using jasmine Spy for HttpClient interactions', () => {
    let httpClientSpy: jasmine.SpyObj<HttpClient>;

    beforeEach(() => {
      httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
      TestBed.configureTestingModule({
        providers: [
          FoobarService,
          {
            provide: HttpResponseService,
            useValue: httpResponseServiceSpy
          },
          {
            provide: HttpClient,
            useValue: httpClientSpy
          },
        ],
      });
      service = TestBed.inject(FoobarService);
    });

    describe('generateUUID', () => {
      describe('when successful', () => {
        const expectedUUID = '0cf762ad-3077-43e7-9f9c-2baa98222f5c';
        let actual: Observable<any>;

        beforeEach(() => {
          httpClientSpy.get.and.returnValue(of(expectedUUID));
          actual = service.generateUUID();
        });

        it('should return an Observable with a single value, the UUID', async () => {
          expect(actual).toBeInstanceOf(Observable);
          const actualValue = await actual.toPromise();
          expect(actualValue).toEqual(expectedUUID);
        });

        it('should invoke HttpClient.get with appropriate URL and options', () => {
          expect(httpClientSpy.get).toHaveBeenCalledWith(REST_DIGITALCHECKOUT_API_URL, jasmine.anything());
        });
      });

      describe('when unsuccessful', () => {
        const expectedRootErrorMessage = 'Root testing error message';
        const expectedErrorMessage = 'Testing error message';
        let actual: Observable<any>;

        beforeEach(() => {
          httpClientSpy.get.and.returnValue(throwError(expectedRootErrorMessage));
          httpResponseServiceSpy.handleError.and.returnValue(throwError(Error(expectedErrorMessage)));
          actual = service.generateUUID();
        });

        it('should invoke HttpClient.get with appropriate URL and options', () => {
          expect(httpClientSpy.get).toHaveBeenCalledWith(REST_DIGITALCHECKOUT_API_URL, jasmine.anything());
        });

        it('should invoke HttpResponseService.handleError function', () => {
          actual.subscribe(
            (nextValue) => {
              fail('Next handler should not be invoked!');
            },
            (error) => {
              expect(httpResponseServiceSpy.handleError).toHaveBeenCalledTimes(1);
              expect(error.message).toEqual(expectedErrorMessage);
            },
            () => {
              fail('Complete handler should not be invoked!');
            },
          );
        });
      });
    });
  });
});
```


### System under test



