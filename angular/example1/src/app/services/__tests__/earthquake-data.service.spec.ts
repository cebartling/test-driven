import { DateTime } from 'luxon';
import { of } from 'rxjs';
import { HttpParams } from '@angular/common/http';
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

    describe('using Jasmine spy to mock HttpClient', () => {
      let httpClientSpy: { get: jasmine.Spy };

      beforeEach(() => {
        httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
        service = new EarthquakeDataService(httpClientSpy as any);
      });

      let captured: FeatureCollection;
      let capturedError: Error;
      const expectedUrl = 'https://earthquake.usgs.gov/fdsnws/event/1/query';
      const startDateTime = DateTime.fromObject({ year: 2021, month: 8, day: 1 });
      const endDateTime = DateTime.fromObject({ year: 2021, month: 8, day: 15 });
      const featureCollection$ = of(featureCollection);

      beforeEach((done: DoneFn) => {
        httpClientSpy.get.and.returnValue(featureCollection$);
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
        const params = new HttpParams()
          .set('format', 'geojson')
          .set('starttime', startDateTime.toFormat('yyyy-MM-dd'))
          .set('endtime', endDateTime.toFormat('yyyy-MM-dd'));
        const expectedOptions = { params };
        expect(httpClientSpy.get).toHaveBeenCalledWith(expectedUrl, expectedOptions);
      });
    });
  });
});
