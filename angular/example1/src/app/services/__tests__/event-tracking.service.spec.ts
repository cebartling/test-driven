import { TestBed } from '@angular/core/testing';
import { EventTrackingService } from '../event-tracking.service';

describe('EventTrackingService', () => {
  let service: EventTrackingService;
  let globalStyleEventTracking: any;

  beforeEach(() => {
    globalStyleEventTracking = {
      startTracking: (message: string) => {},
    };
    (window as any).globalStyleEventTracking = globalStyleEventTracking;
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventTrackingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('trackEvent', () => {
    const eventName = 'foobarEvent';

    beforeEach(() => {
      spyOn(globalStyleEventTracking, 'startTracking');
      service.trackEvent(eventName);
    });

    it('should invoke globalStyleEventTracking.startTracking', () => {
      expect(globalStyleEventTracking.startTracking).toHaveBeenCalledWith(eventName);
    });
  });
});
