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

  describe('constructor', () => {
    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should create a FormGroup', () => {
      expect(component.profileFormGroup).toBeDefined();
    });

    it('should create firstName form control within the FormGroup', () => {
      expect(component.profileFormGroup.controls['firstName']).toBeDefined();
    });

    it('should create lastName form control within the FormGroup', () => {
      expect(component.profileFormGroup.controls['lastName']).toBeDefined();
    });

    it('should create username form control within the FormGroup', () => {
      expect(component.profileFormGroup.controls['username']).toBeDefined();
    });
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
