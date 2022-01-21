import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RaceParticipant } from '../types/race-participant';

@Injectable({
  providedIn: 'root',
})
export class RaceParticipantService {
  constructor(private httpClient: HttpClient) {}

  getRaceParticipantsByRace(raceId: string): Observable<RaceParticipant[]> {
    return this.httpClient.get<RaceParticipant[]>(
      `/api/raceParticipants?raceId=${raceId}`
    );
  }
}
