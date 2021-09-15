import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileViewComponent } from '../profile-view.component';
import { FormBuilder, Validators } from '@angular/forms';

describe('ProfileViewComponent', () => {
  let component: ProfileViewComponent;
  let fixture: ComponentFixture<ProfileViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfileViewComponent],
      providers: [FormBuilder],
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

    describe('firstName', () => {
      it('should create form control within the FormGroup', () => {
        expect(component.profileFormGroup.controls['firstName']).toBeDefined();
      });

      it('should configure a required validator for the form control', () => {
        expect(component.profileFormGroup.controls['firstName'].hasValidator(Validators.required)).toBeTruthy();
      });
    });

    describe('lastName', () => {
      it('should create form control within the FormGroup', () => {
        expect(component.profileFormGroup.controls['lastName']).toBeDefined();
      });

      it('should configure a required validator for the form control', () => {
        expect(component.profileFormGroup.controls['lastName'].hasValidator(Validators.required)).toBeTruthy();
      });
    });

    describe('username', () => {
      it('should create form control within the FormGroup', () => {
        expect(component.profileFormGroup.controls['username']).toBeDefined();
      });

      it('should configure a required validator for the form control', () => {
        expect(component.profileFormGroup.controls['username'].hasValidator(Validators.required)).toBeTruthy();
      });
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

    describe('Save button', () => {
      let submitButton: any;

      describe('when the form is invalid', () => {
        beforeEach(() => {
          component.profileFormGroup.controls.firstName.setValue(null);
          component.profileFormGroup.controls.lastName.setValue('Smith');
          component.profileFormGroup.controls.username.setValue('jsmith');
          fixture.detectChanges();
          submitButton = fixture.debugElement.nativeElement.querySelector('#saveProfileButton');
        });

        it('the disabled attribute should be true', () => {
          expect(submitButton.disabled).toBeTruthy();
        });
      });

      describe('when the form is valid', () => {
        beforeEach(() => {
          component.profileFormGroup.controls.firstName.setValue('Joe');
          component.profileFormGroup.controls.lastName.setValue('Smith');
          component.profileFormGroup.controls.username.setValue('jsmith');
          fixture.detectChanges();
          submitButton = fixture.debugElement.nativeElement.querySelector('#saveProfileButton');
        });

        it('the disabled attribute should be false', () => {
          expect(submitButton.disabled).toBeFalsy();
        });
      });
    });
  });
});
