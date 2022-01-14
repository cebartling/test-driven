import {TestBed} from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {Observable} from "rxjs";
import {RaceService} from './race.service';
import {Race} from "../types/race";

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

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getRaces', () => {
    let races$: Observable<Race[]>
    let captured: Race[];
    let capturedError: Error;
    const expectedRaces = [] as Race[];
    const expectedUrl = 'http://localhost:3000/races';

    beforeEach( (done: DoneFn) => {
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
