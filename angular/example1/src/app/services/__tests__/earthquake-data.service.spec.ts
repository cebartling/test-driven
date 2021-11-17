import { DateTime } from 'luxon';
import { of } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { DATE_FORMAT, EarthquakeDataService, FORMAT_GEOJSON } from '../earthquake-data.service';
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

    describe('using Jasmine spy to mock HttpClient', () => {
      let httpClientSpy: { get: jasmine.Spy };

      beforeEach(() => {
        httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
        TestBed.configureTestingModule({
          providers: [EarthquakeDataService, { provide: HttpClient, useValue: httpClientSpy }],
        });
        service = TestBed.inject(EarthquakeDataService);
      });

      let captured: FeatureCollection;
      let capturedError: Error;
      const expectedUrl = 'https://earthquake.usgs.gov/fdsnws/event/1/query';
      const startDateTime = DateTime.fromObject({ year: 2021, month: 8, day: 1 });
      const endDateTime = DateTime.fromObject({ year: 2021, month: 8, day: 15 });
      const featureCollection$ = of(featureCollection);
      let params: HttpParams;

      beforeEach((done: DoneFn) => {
        params = new HttpParams()
          .set('format', FORMAT_GEOJSON)
          .set('starttime', startDateTime.toFormat(DATE_FORMAT))
          .set('endtime', endDateTime.toFormat(DATE_FORMAT));
        const expectedOptions = { params };
        httpClientSpy.get.withArgs(expectedUrl, expectedOptions).and.returnValue(featureCollection$);
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
      });

      it('should return the appropriate response', () => {
        expect(captured).toEqual(featureCollection);
      });

      it('should not throw an error', () => {
        expect(capturedError).toBeUndefined();
      });

      it('should invoke get on the HttpClient', () => {
        const expectedOptions = { params };
        expect(httpClientSpy.get).toHaveBeenCalledWith(expectedUrl, expectedOptions);
      });
    });
  });
});
