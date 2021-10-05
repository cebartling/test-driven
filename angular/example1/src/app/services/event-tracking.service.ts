import { Injectable } from '@angular/core';

// This just makes Typescript aware of the global variable type
declare var globalStyleEventTracking: any;

/**
 * This Angular service demonstrates using a global variable within Typescript
 * and declaring it and using it here in this service.
 *
 * Global variables are bad, but sometimes, when integrating older JavaScript,
 * it might be the only way to accomplish the integration without proliferating
 * that global code all over.
 */
@Injectable({
  providedIn: 'root',
})
export class EventTrackingService {
  constructor() {}

  /**
   * Records an event, delegating to the old-style global event tracking
   * system that we describe above.
   *
   * @param eventName
   */
  trackEvent(eventName: string): void {
    if (
      typeof globalStyleEventTracking !== 'undefined' &&
      globalStyleEventTracking &&
      typeof globalStyleEventTracking?.startTracking === 'function'
    ) {
      globalStyleEventTracking.startTracking(eventName);
    }
  }
}
