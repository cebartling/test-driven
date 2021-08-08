import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DateTime } from 'luxon';

export interface EarthquakeDataService {
  query(startDateTime: DateTime, endDateTime: DateTime): Observable<any>;
}

const DATE_FORMAT = 'yyyy-MM-dd';
const FORMAT_GEOJSON = 'geojson';

@Injectable({
  providedIn: 'root',
})
export class EarthquakeDataServiceImpl implements EarthquakeDataService {
  constructor(private httpClient: HttpClient) {}

  query(startDateTime: DateTime, endDateTime: DateTime): Observable<any> {
    const params = new HttpParams();
    params.set('format', FORMAT_GEOJSON);
    params.set('starttime', startDateTime.toFormat(DATE_FORMAT));
    params.set('endtime', endDateTime.toFormat(DATE_FORMAT));
    return this.httpClient.get('https://earthquake.usgs.gov/fdsnws/event/1/query', { params });
  }
}
