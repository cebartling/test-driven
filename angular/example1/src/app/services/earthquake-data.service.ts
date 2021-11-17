import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DateTime } from 'luxon';
import { FeatureCollection } from '../models/earthquake/feature-collection';

export const DATE_FORMAT = 'yyyy-MM-dd';
export const FORMAT_GEOJSON = 'geojson';

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
