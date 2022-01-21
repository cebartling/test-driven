import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { Observable } from 'rxjs';
import { RiderService } from '../rider.service';
import { Rider, RiderImpl } from '../../types/rider';
import { Gender } from '../../types/gender';

describe('RiderService', () => {
  let service: RiderService;
  let httpTestingController: HttpTestingController;

  const expectedRider = {
    id: 'bb54c76f-3c78-40e3-808b-75dec4986c0e',
    givenName: 'James',
    surname: 'Morris',
    birthDate: new Date('1986-03-12'),
    gender: Gender.Male,
  } as Rider;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [RiderService],
    });
    service = TestBed.inject(RiderService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  describe('getRiders', () => {
    let riders$: Observable<Rider[]>;
    let captured: Rider[];
    let capturedError: Error;
    const expectedUrl = '/api/riders';

    beforeEach((done: DoneFn) => {
      riders$ = service.getRiders();
      riders$.subscribe(
        (data: Rider[]) => {
          captured = data;
          done();
        },
        (error) => {
          capturedError = error;
          done();
        }
      );
      httpTestingController.expectOne(expectedUrl).flush([expectedRider]);
    });

    it('should return the appropriate response', () => {
      const expectedRiders = [new RiderImpl(expectedRider)] as Rider[];
      expect(captured).toEqual(expectedRiders);
    });

    it('should not throw an error', () => {
      expect(capturedError).toBeUndefined();
    });
  });

  describe('getRider', () => {
    let rider$: Observable<Rider>;
    let captured: Rider;
    let capturedError: Error;

    const expectedUrl = `/api/riders/${expectedRider.id}`;

    beforeEach((done: DoneFn) => {
      rider$ = service.getRider(expectedRider.id);
      rider$.subscribe(
        (data: Rider) => {
          captured = data;
          done();
        },
        (error) => {
          capturedError = error;
          done();
        }
      );
      httpTestingController.expectOne(expectedUrl).flush(expectedRider);
    });

    it('should return the appropriate response', () => {
      expect(captured).toEqual(new RiderImpl(expectedRider));
    });

    it('should not throw an error', () => {
      expect(capturedError).toBeUndefined();
    });
  });
});
