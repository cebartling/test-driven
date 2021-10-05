# Testing with Global Variables

## Introduction

There might be times when you have to integrate an older piece of JavaScript into your Angular application and will need to use a reference to a global variable. The global variable is declared in your Typescript code using `declare var` syntax to appease the type system. The global variable will usually be loaded via a `<script>` tag and attached to the `window` object so it can be referenced throughout the JavaScript runtime in global fashion. Given that information, it's fairly simple to control the global variable within your Jasmine specifications.

## Example

In this example, we have a older event tracking system that is attached to the `window` object as `globalStyleEventTracking`. We have a declaration for that global variable, using `declare var globalStyleEventTracking: any;`. That global variable declaration just satisfies the Typescript typing system. 

### The Jasmine specification

The Jasmine specification can create a stub test double that can be used to stand in for the real `globalStyleEventTracking` object. We then spy the method of that object that will be used in the system under test. Finally, the `globalStyleEventTracking` stub object that we created and placed a spy proxy on can then be attached to the global browser `window` object. The `(window as any)` syntax is used to avoid a tslint warning.

```typescript
import { TestBed } from '@angular/core/testing';
import { EventTrackingService } from '../event-tracking.service';

describe('EventTrackingService', () => {
  let service: EventTrackingService;
  let globalStyleEventTracking: any;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventTrackingService);
  });

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
```

### The system under test

The system under test in this example is an Angular service that acts as a wrapper for the older style event tracking system that is accessed as a global variable. Using this technique, the `EventTrackingService` can participate in the dependency injection system of Angular and the rest of the Angular application is protected from the the older style event tracking system implementation. The `EventTrackingService` is acting as an adapter, adapting the older style event tracking system implementation for use in the more modern Angular implementation.


```typescript
import { Injectable } from '@angular/core';

declare var globalStyleEventTracking: any;

/**
 * This Angular service demonstrates using a global variable within Typescript
 * and declaring it and using it here in this service.
 *
 * Global variables are bad, but sometimes, when integrating older JavaScript,
 * it might be the only way to accomplish the integration without proliferating
 * that global variable access all over.
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
```
