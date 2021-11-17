import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { WelcomeViewComponent } from '../welcome-view.component';

describe('WelcomeViewComponent', () => {
  let component: WelcomeViewComponent;
  let fixture: ComponentFixture<WelcomeViewComponent>;

  beforeEach(
    waitForAsync(() => {
      TestBed.configureTestingModule({
        declarations: [WelcomeViewComponent],
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(WelcomeViewComponent);
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

    describe('heading', () => {
      let targetSelector: Element | null;

      beforeEach(() => {
        targetSelector = compiled.querySelector('.welcome-heading');
      });

      it('should be present', () => {
        expect(targetSelector).not.toBeNull();
      });

      it('should contain the appropriate text content', () => {
        expect(targetSelector?.textContent).toBe('Welcome!');
      });
    });
  });
});
