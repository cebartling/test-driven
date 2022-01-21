import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  RaceParticipant,
  RaceParticipantImpl,
} from '../types/race-participant';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class RaceParticipantService {
  constructor(private httpClient: HttpClient) {}

  getRaceParticipantsByRace(raceId: string): Observable<RaceParticipant[]> {
    return this.httpClient
      .get<RaceParticipant[]>(`/api/raceParticipants?raceId=${raceId}`)
      .pipe(
        map((raceParticipants: RaceParticipant[]) => {
          return raceParticipants.map((raceParticipant: RaceParticipant) => {
            return new RaceParticipantImpl(raceParticipant);
          });
        })
      );
  }
}
