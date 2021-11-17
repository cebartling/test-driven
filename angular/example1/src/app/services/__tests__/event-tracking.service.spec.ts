import { TestBed, waitForAsync } from '@angular/core/testing';
import { EventTrackingService } from '../event-tracking.service';

describe('EventTrackingService', () => {
  let service: EventTrackingService;
  let globalStyleEventTracking: any;

  beforeEach(
    waitForAsync(() => {
      TestBed.configureTestingModule({}).compileComponents();
      service = TestBed.inject(EventTrackingService);
    })
  );

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('trackEvent', () => {
    const eventName = 'foobarEvent';

    describe('when globalStyleEventTracking is defined', () => {
      beforeEach(() => {
        globalStyleEventTracking = {
          startTracking: (message: string) => {},
        };
        (window as any).globalStyleEventTracking = globalStyleEventTracking;
        spyOn(globalStyleEventTracking, 'startTracking');
        service.trackEvent(eventName);
      });

      it('should invoke globalStyleEventTracking.startTracking', () => {
        expect(globalStyleEventTracking.startTracking).toHaveBeenCalledWith(eventName);
      });
    });
  });
});
