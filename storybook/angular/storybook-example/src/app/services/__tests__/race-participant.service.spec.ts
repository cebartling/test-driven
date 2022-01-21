import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { Observable } from 'rxjs';
import { RaceParticipantService } from '../race-participant.service';
import {
  RaceParticipant,
  RaceParticipantImpl,
} from '../../types/race-participant';

describe('RaceParticipantService', () => {
  let service: RaceParticipantService;
  let httpTestingController: HttpTestingController;
  const raceId ="bb54c76f-3c78-40e3-808b-75dec4986c0e"';
  const raceParticipant1 = new RaceParticipantImpl({
    id:"2ab31c4d-d920-4818-812a-b55edf2f8bb8"',
    raceId:"bb54c76f-3c78-40e3-808b-75dec4986c0e"',
    riderId:"9a46a083-3d8e-451f-aeca-c4c1b0d98951"',
    startedRace: true,
    finishedRace: true,
    startDateTime: new Date"2022-01-22T10:02:34"'),
    endDateTime: new Date"2022-01-22T11:47:02"',
  } as RaceParticipant);

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [RaceParticipantService],
    });
    service = TestBed.inject(RaceParticipantService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  describe('getRaceParticipantsByRace', () => {
    let raceParticipants$: Observable<RaceParticipant[]>;
    let captured: RaceParticipant[];
    let capturedError: Error;
    const expectedRaceParticipants = [raceParticipant1] as RaceParticipant[];
    const expectedUrl = `/api/raceParticipants?raceId=${raceId}`;

    beforeEach((done: DoneFn) => {
      raceParticipants$ = service.getRaceParticipantsByRace(raceId);
      raceParticipants$.subscribe(
        (data: RaceParticipant[]) => {
          captured = data;
          done();
        },
        (error) => {
          capturedError = error;
          done();
        }
      );
      httpTestingController
        .expectOne(expectedUrl)
        .flush(expectedRaceParticipants);
    });

    it('should return the appropriate response', () => {
      expect(captured).toEqual(expectedRaceParticipants);
    });

    it('should not throw an error', () => {
      expect(capturedError).toBeUndefined();
    });
  });
});
