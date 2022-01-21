import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Rider, RiderImpl } from '../types/rider';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class RiderService {
  constructor(private httpClient: HttpClient) {}

  getRiders(): Observable<Rider[]> {
    return this.httpClient.get<Rider[]>('/api/riders').pipe(
      map((riders: Rider[]) => {
        return riders.map((rider: Rider) => {
          return new RiderImpl(rider);
        });
      })
    );
  }

  getRider(id: string): Observable<Rider> {
    return this.httpClient.get<Rider>(`/api/riders/${id}`).pipe(
      map((rider: Rider) => {
        return new RiderImpl(rider);
      })
    );
  }
}
