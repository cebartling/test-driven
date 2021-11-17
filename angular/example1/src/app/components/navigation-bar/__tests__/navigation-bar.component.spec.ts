import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NavigationBarComponent } from '../navigation-bar.component';

describe('NavigationBarComponent', () => {
  let component: NavigationBarComponent;
  let fixture: ComponentFixture<NavigationBarComponent>;

  beforeEach(
    waitForAsync(() => {
      TestBed.configureTestingModule({
        declarations: [NavigationBarComponent],
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(NavigationBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('template rendering', () => {
    let compiled: HTMLElement;

    beforeEach(() => {
      compiled = fixture.nativeElement as HTMLElement;
    });

    describe('welcome navigation link', () => {
      let targetSelector: Element | null;

      beforeEach(() => {
        targetSelector = compiled.querySelector('#welcome-nav-link');
      });

      it('should be present', () => {
        expect(targetSelector).not.toBeNull();
      });

      it('should contain the appropriate text content', () => {
        expect(targetSelector?.textContent).toBe('Welcome');
      });
    });

    describe('map navigation link', () => {
      let targetSelector: Element | null;

      beforeEach(() => {
        targetSelector = compiled.querySelector('#map-nav-link');
      });

      it('should be present', () => {
        expect(targetSelector).not.toBeNull();
      });

      it('should contain the appropriate text content', () => {
        expect(targetSelector?.textContent).toBe('Map');
      });
    });

    describe('profile navigation link', () => {
      let targetSelector: Element | null;

      beforeEach(() => {
        targetSelector = compiled.querySelector('#profile-nav-link');
      });

      it('should be present', () => {
        expect(targetSelector).not.toBeNull();
      });

      it('should contain the appropriate text content', () => {
        expect(targetSelector?.textContent).toBe('Profile');
      });
    });
  });
});
