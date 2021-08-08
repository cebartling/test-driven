import { DateTime } from 'luxon';
import { of } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { EarthquakeDataService } from './earthquake-data.service';
import { FeatureCollection } from '../models/earthquake/FeatureCollection';
import { featureCollection } from '../__tests__/data/feature-collection';

describe('EarthquakeDataService', () => {
  let httpClientSpy: { get: jasmine.Spy };
  let service: EarthquakeDataService;

  beforeEach(() => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
    service = new EarthquakeDataService(httpClientSpy as any);
  });

  describe('query', () => {
    let captured: FeatureCollection;
    const expectedUrl = 'https://earthquake.usgs.gov/fdsnws/event/1/query';
    const startDateTime = DateTime.fromObject({ year: 2021, month: 8, day: 1 });
    const endDateTime = DateTime.fromObject({ year: 2021, month: 8, day: 15 });
    const featureCollection$ = of(featureCollection);

    beforeEach((done: DoneFn) => {
      httpClientSpy.get.and.returnValue(featureCollection$);
      service.query(startDateTime, endDateTime).subscribe((data) => {
        captured = data;
        done();
      }, done.fail);
    });

    it('should return the appropriate response', () => {
      expect(captured).toEqual(featureCollection);
    });

    it('should invoke get on the HttpClient', () => {
      const params = new HttpParams();
      params.set('format', 'geojson');
      params.set('starttime', startDateTime.toFormat('yyyy-MM-dd'));
      params.set('endtime', endDateTime.toFormat('yyyy-MM-dd'));
      const expectedOptions = { params };
      expect(httpClientSpy.get).toHaveBeenCalledWith(expectedUrl, expectedOptions);
    });
  });
});
