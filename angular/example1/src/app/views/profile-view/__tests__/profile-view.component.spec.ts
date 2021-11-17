import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ProfileViewComponent } from '../profile-view.component';
import { FormBuilder, Validators } from '@angular/forms';

describe('ProfileViewComponent', () => {
  let component: ProfileViewComponent;
  let fixture: ComponentFixture<ProfileViewComponent>;

  beforeEach(
    waitForAsync(() => {
      TestBed.configureTestingModule({
        declarations: [ProfileViewComponent],
        providers: [FormBuilder],
      }).compileComponents();
    })
  );

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

      describe('disabled attribute', () => {
        const testCases = [
          {
            firstName: 'Johnny',
            lastName: 'Smith',
            username: 'jsmith',
            expectedDisabledState: false,
            describeMessage: 'when form is valid',
            message: 'the disabled attribute should be false',
          },
          {
            firstName: null,
            lastName: 'Smith',
            username: 'jsmith',
            expectedDisabledState: true,
            describeMessage: 'when form is invalid (firstName is null)',
            message: 'the disabled attribute should be true',
          },
          {
            firstName: 'Johnny',
            lastName: null,
            username: 'jsmith',
            expectedDisabledState: true,
            describeMessage: 'when form is invalid (lastName is null)',
            message: 'the disabled attribute should be true',
          },
          {
            firstName: 'Johnny',
            lastName: 'Smith',
            username: null,
            expectedDisabledState: true,
            describeMessage: 'when form is invalid (username is null)',
            message: 'the disabled attribute should be true',
          },
        ];

        testCases.forEach((testCase) => {
          describe(testCase.describeMessage, () => {
            beforeEach(() => {
              component.profileFormGroup.controls.firstName.setValue(testCase.firstName);
              component.profileFormGroup.controls.lastName.setValue(testCase.lastName);
              component.profileFormGroup.controls.username.setValue(testCase.username);
              fixture.detectChanges();
              submitButton = fixture.debugElement.nativeElement.querySelector('#saveProfileButton');
            });

            it(testCase.message, () => {
              expect(submitButton.disabled).toEqual(testCase.expectedDisabledState);
            });
          });
        });
      });
    });
  });
});
