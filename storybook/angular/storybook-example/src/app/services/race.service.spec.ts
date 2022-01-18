import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { Observable } from 'rxjs';
import { RaceService } from './race.service';
import { Race } from '../types/race';

describe('RaceService', () => {
  let service: RaceService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [RaceService],
    });
    service = TestBed.inject(RaceService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  describe('getRaces', () => {
    let races$: Observable<Race[]>;
    let captured: Race[];
    let capturedError: Error;
    const expectedRaces = [
      {
        id: 'bb54c76f-3c78-40e3-808b-75dec4986c0e',
        name: 'Fat Bike Birkie 2022',
        location: 'Seeley',
        state: 'WI',
        startDateTime: new Date('2022-03-12T09:00:00'),
        year: 2022,
        description:
          'The Fat Bike Birkie, presented by Freewheel Bike, is the premier on snow bike event in North America. Riders test their skill and endurance on the professionally groomed American Birkebeiner Ski Trail! THREE events to choose from: The Big Fat 47K, The Half Fat 21K, and the un-timed Fun Fat 10K Tour – there is something for every rider!',
      } as Race,
    ] as Race[];
    const expectedUrl = '/api/races';

    beforeEach((done: DoneFn) => {
      races$ = service.getRaces();
      races$.subscribe(
        (data: Race[]) => {
          captured = data;
          done();
        },
        (error) => {
          capturedError = error;
          done();
        }
      );
      httpTestingController.expectOne(expectedUrl).flush(expectedRaces);
    });

    it('should return the appropriate response', () => {
      expect(captured).toEqual(expectedRaces);
    });

    it('should not throw an error', () => {
      expect(capturedError).toBeUndefined();
    });
  });
});
