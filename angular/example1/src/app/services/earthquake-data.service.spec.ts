import { EarthquakeDataService, EarthquakeDataServiceImpl } from './earthquake-data.service';
import { DateTime } from 'luxon';
import { of } from 'rxjs';
import { HttpParams } from '@angular/common/http';

const queryGeoJson = {
  type: 'FeatureCollection',
  metadata: {
    generated: 1628449424000,
    url: 'https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2021-08-01&endtime=2021-08-07',
    title: 'USGS Earthquakes',
    status: 200,
    api: '1.12.1',
    count: 2231,
  },
  features: [
    {
      type: 'Feature',
      properties: {
        mag: 2.49,
        place: '11 km S of Fern Forest, Hawaii',
        time: 1628294284770,
        updated: 1628300043040,
        tz: null,
        url: 'https://earthquake.usgs.gov/earthquakes/eventpage/hv72627442',
        detail: 'https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=hv72627442&format=geojson',
        felt: null,
        cdi: null,
        mmi: null,
        alert: null,
        status: 'reviewed',
        tsunami: 0,
        sig: 95,
        net: 'hv',
        code: '72627442',
        ids: ',us6000f31s,hv72627442,',
        sources: ',us,hv,',
        types: ',origin,phase-data,',
        nst: 35,
        dmin: null,
        rms: 0.09,
        gap: 105,
        magType: 'ml',
        type: 'earthquake',
        title: 'M 2.5 - 11 km S of Fern Forest, Hawaii',
      },
      geometry: {
        type: 'Point',
        coordinates: [-155.144166666667, 19.3645, 2.8],
      },
      id: 'hv72627442',
    },
  ],
  bbox: [-179.9283, -59.6238, -3.37, 179.9019, 69.5405, 590.57],
};

describe('EarthquakeDataService', () => {
  let httpClientSpy: { get: jasmine.Spy };
  let service: EarthquakeDataService;

  beforeEach(() => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
    service = new EarthquakeDataServiceImpl(httpClientSpy as any);
  });

  describe('query', () => {
    let captured: any;
    const expectedUrl = 'https://earthquake.usgs.gov/fdsnws/event/1/query';
    const startDateTime = DateTime.fromObject({ year: 2021, month: 8, day: 1 });
    const endDateTime = DateTime.fromObject({ year: 2021, month: 8, day: 15 });
    const observable = of(queryGeoJson);

    beforeEach((done: DoneFn) => {
      httpClientSpy.get.and.returnValue(observable);
      service.query(startDateTime, endDateTime).subscribe((data) => {
        captured = data;
        done();
      }, done.fail);
    });

    it('should return the appropriate response', () => {
      expect(captured).toEqual(queryGeoJson);
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
