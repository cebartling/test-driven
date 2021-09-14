import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileViewComponent } from '../profile-view.component';

describe('ProfileViewComponent', () => {
  let component: ProfileViewComponent;
  let fixture: ComponentFixture<ProfileViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfileViewComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileViewComponent);
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
        targetSelector = compiled.querySelector('#profile-heading');
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
