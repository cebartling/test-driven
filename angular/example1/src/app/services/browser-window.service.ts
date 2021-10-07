import { Injectable } from '@angular/core';
import { obj } from '../util/window-access';

@Injectable({
  providedIn: 'root',
})
export class BrowserWindowService {
  constructor() {}

  /**
   * Get access to the global window object.
   */
  getWindow(): Window {
    return obj.windowAccess();
  }
}
