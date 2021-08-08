import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DateTime } from 'luxon';

export interface EarthquakeDataService {
  query(startDateTime: DateTime, endDateTime: DateTime): Observable<any>;
}

@Injectable({
  providedIn: 'root',
})
export class EarthquakeDataServiceImpl implements EarthquakeDataService {
  constructor(private httpClient: HttpClient) {}

  query(startDateTime: DateTime, endDateTime: DateTime): Observable<any> {
    const params = new HttpParams();
    params.set('format', 'geojson');
    params.set('starttime', startDateTime.toFormat('yyyy-MM-dd'));
    params.set('endtime', endDateTime.toFormat('yyyy-MM-dd'));
    return this.httpClient.get('https://earthquake.usgs.gov/fdsnws/event/1/query', { params });
  }
}
