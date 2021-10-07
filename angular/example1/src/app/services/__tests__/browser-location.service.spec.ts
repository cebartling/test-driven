import { TestBed } from '@angular/core/testing';
import { BrowserWindowService } from '../browser-window.service';
import * as windowAccessExports from '../../util/window-access';

describe('BrowserWindowService', () => {
  let service: BrowserWindowService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BrowserWindowService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getWindow', () => {
    let actual: any;
    const windowStub = { location: { href: 'https://www.foobar.com' } };

    beforeEach(() => {
      spyOnProperty(windowAccessExports, 'obj', 'get').and.returnValue({ windowAccess: () => windowStub });
      actual = service.getWindow();
    });

    it('should return the global window object', () => {
      expect(actual).toEqual(windowStub);
    });
  });
});
