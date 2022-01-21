import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Rider } from '../types/rider';

@Injectable({
  providedIn: 'root',
})
export class RiderService {
  constructor(private httpClient: HttpClient) {}

  getRiders(): Observable<Rider[]> {
    return this.httpClient.get<Rider[]>('/api/riders');
  }

  getRider(id: string): Observable<Rider> {
    return this.httpClient.get<Rider>(`/api/riders/${id}`);
  }
}
