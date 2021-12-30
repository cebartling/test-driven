import {cleanup, fireEvent, render, RenderResult} from '@testing-library/svelte'
import ProfileEditor from '../ProfileEditor.svelte';
import type {Profile} from '../../models/Profile';
import {ProfileServices} from '../../services/ProfileServices';
import {tick} from "svelte";

describe('ProfileEditor.svelte component', () => {
  let renderResult: RenderResult;
  const profile = {
    id: "78b6c7b2-6c30-4604-b7cd-56e6cecaae83",
    emailAddress: "jasper.shaw@example.com",
    givenName: "Jasper",
    surname: "Shawn"
  } as Profile;
  const props = {profile};

  beforeEach(() => {
    renderResult = render(ProfileEditor, {props});
  });

  afterEach(() => {
    cleanup();
  })

  describe('rendered component tree', () => {
    describe('email address input field', () => {
      let inputElement: HTMLInputElement;
      let errorMessageElement: HTMLDivElement;

      beforeEach(() => {
        inputElement = renderResult.container.querySelector('input#emailAddressFormControlInput');
        errorMessageElement = renderResult.container.querySelector('div#emailAddressErrorMessage');
      });

      it('should be present in the DOM', () => {
        expect(inputElement).toBeInTheDocument();
      });

      describe('when the form is initially rendered', () => {
        it('should be initially bound to the profile.emailAddress property', () => {
          expect(inputElement.value).toBe(profile.emailAddress);
        });

        it('should not display an error message', () => {
          expect(errorMessageElement.textContent).toBe('');
        });
      });

      // describe('when the form control value is empty', () => {
      //   let buttonElement: HTMLButtonElement;
      //
      //   beforeEach(() => {
      //     buttonElement = renderResult.container.querySelector('button');
      //   });
      //
      //   it('should display an error message', () => {
      //     const { form, errors, isValid } = renderResult.component;
      //     form.set({emailAddress: ''});
      //     expect(errors).toBeDefined();
      //     // expect(errorMessageElement.textContent).toBe('The email address is a required field!');
      //   });
      // });
    });

    describe('given name input field', () => {
      let inputElement: HTMLInputElement;

      beforeEach(() => {
        inputElement = renderResult.container.querySelector('input#givenNameFormControlInput')
      });

      it('should be present in the DOM', () => {
        expect(inputElement).toBeInTheDocument();
      });

      it('should be initially bound to the profile.givenName property', () => {
        expect(inputElement.value).toBe(profile.givenName);
      });
    });

    describe('surname input field', () => {
      let inputElement: HTMLInputElement;

      beforeEach(() => {
        inputElement = renderResult.container.querySelector('input#surnameFormControlInput')
      });

      it('should be present in the DOM', () => {
        expect(inputElement).toBeInTheDocument();
      });

      it('should be initially bound to the profile.surname property', () => {
        expect(inputElement.value).toBe(profile.surname);
      });
    });

    describe('save button', () => {
      let buttonElement: HTMLButtonElement;

      beforeEach(() => {
        buttonElement = renderResult.container.querySelector('button');
      });

      it('should be present in the DOM', () => {
        expect(buttonElement).toBeInTheDocument();
      });
    });

    describe('profile form', () => {
      let formElement: HTMLFormElement;

      beforeEach(() => {
        const response = {ok: true} as Response;
        ProfileServices.updateProfile = jest.fn().mockResolvedValue(response);
        formElement = renderResult.container.querySelector('form#profileForm');
      });

      it('should be present in the DOM', () => {
        expect(formElement).toBeInTheDocument();
      });
    });
  });

  describe('exported behavior', () => {
    describe('doOnSubmit', () => {
      const updatedValues = {
        emailAddress: "jasper.shaw@example.com",
        givenName: "Jasper",
        surname: "Shawn"
      };
      const updatedProfile = {
        ...profile,
        emailAddress: updatedValues.emailAddress,
        givenName: updatedValues.givenName,
        surname: updatedValues.surname
      }


      describe('when the response returns successful status code', () => {
        beforeEach(async () => {
          const response = {ok: true} as Response;
          ProfileServices.updateProfile = jest.fn().mockResolvedValue(response);
          await renderResult.component.doOnSubmit(updatedValues);
        });

        it('should invoke ProfileServices.updateProfile', () => {
          expect(ProfileServices.updateProfile).toHaveBeenCalledWith(updatedProfile);
        });
      });

      describe('when the response returns unsuccessful status code', () => {
        let error: Error;

        beforeEach(async () => {
          const response = {ok: false, status: 400} as Response;
          ProfileServices.updateProfile = jest.fn().mockResolvedValue(response);
          try {
            await renderResult.component.doOnSubmit(updatedValues);
            fail('Should have thrown an error');
          } catch (e) {
            error = e;
          }
        });


        it('should invoke ProfileServices.updateProfile', async () => {
          expect(ProfileServices.updateProfile).toHaveBeenCalledWith(updatedProfile);
        });

        it('should throw an error with an appropriate message', async () => {
          expect(error.message).toBe('Error updating profile. Status code: 400');
        });
      });
    });
  });
});
