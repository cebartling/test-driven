import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Race } from '../types/race';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class RaceService {
  constructor(private httpClient: HttpClient) {}

  getRaces(): Observable<Race[]> {
    return this.httpClient.get<Race[]>('/api/races');
  }

  getRace(id: string): Observable<Race> {
    return this.httpClient.get<Race>(`/api/races/${id}`);
  }
}
