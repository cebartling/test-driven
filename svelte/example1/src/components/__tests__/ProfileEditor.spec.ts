import {cleanup, fireEvent, render, RenderResult} from '@testing-library/svelte'
import ProfileEditor from '../ProfileEditor.svelte';
import type {Profile} from '../../models/Profile';
import {ProfileServices} from '../../services/ProfileServices';

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

      beforeEach(() => {
        inputElement = renderResult.container.querySelector('input#emailFormControlInput')
      });

      it('should be present in the DOM', () => {
        expect(inputElement).toBeInTheDocument();
      });

      it('should be bound to profile.emailAddress property', () => {
        expect(inputElement.value).toBe(profile.emailAddress);
      });
    });

    describe('given name input field', () => {
      let inputElement: HTMLInputElement;

      beforeEach(() => {
        inputElement = renderResult.container.querySelector('input#givenNameFormControlInput')
      });

      it('should be present in the DOM', () => {
        expect(inputElement).toBeInTheDocument();
      });

      it('should be bound to profile.givenName property', () => {
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

      it('should be bound to profile.surname property', () => {
        expect(inputElement.value).toBe(profile.surname);
      });
    });

    describe('save button', () => {
      let buttonElement: HTMLButtonElement;

      beforeEach(() => {
        const response = {ok: true} as Response;
        ProfileServices.updateProfile = jest.fn().mockResolvedValue(response);
        buttonElement = renderResult.container.querySelector('button')
      });

      it('should be present in the DOM', () => {
        expect(buttonElement).toBeInTheDocument();
      });

      it('should have the onclick handler wired up properly', async () => {
        await fireEvent.click(buttonElement);
        expect(ProfileServices.updateProfile).toHaveBeenCalledWith(profile);
      });
    });
  });

  describe('exported behavior', () => {
    describe('handleOnClickSaveButton', () => {
      describe('when the response returns successful status code', () => {
        beforeEach(async () => {
          const response = {ok: true} as Response;
          ProfileServices.updateProfile = jest.fn().mockResolvedValue(response);
          await renderResult.component.handleOnClickSaveButton();
        });

        it('should invoke ProfileServices.updateProfile', () => {
          expect(ProfileServices.updateProfile).toHaveBeenCalledWith(profile);
        });
      });

      describe('when the response returns unsuccessful status code', () => {
        let error: Error;

        beforeEach(async () => {
          const response = {ok: false, status: 400} as Response;
          ProfileServices.updateProfile = jest.fn().mockResolvedValue(response);
          try {
            await renderResult.component.handleOnClickSaveButton();
            fail('Should have thrown an error');
          } catch (e) {
            error = e;
          }
        });


        it('should invoke ProfileServices.updateProfile', async () => {
          expect(ProfileServices.updateProfile).toHaveBeenCalledWith(profile);
        });

        it('should throw an error with an appropriate message', async () => {
          expect(error.message).toBe('Error updating profile. Status code: 400');
        });
      });
    });
  });
});
