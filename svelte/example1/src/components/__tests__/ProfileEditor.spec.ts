import {cleanup, fireEvent, render, RenderResult} from '@testing-library/svelte'
import ProfileEditor from '../ProfileEditor.svelte';
import type {Profile} from '../../models/Profile';

describe('ProfileEditor.svelte component', () => {
  let renderResult: RenderResult;
  const props = {
    profile: {
      id: "78b6c7b2-6c30-4604-b7cd-56e6cecaae83",
      emailAddress: "jasper.shaw@example.com",
      givenName: "Jasper",
      surname: "Shawn"
    } as Profile
  };

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
        expect(inputElement.value).toBe(props.profile.emailAddress);
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
        expect(inputElement.value).toBe(props.profile.givenName);
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
        expect(inputElement.value).toBe(props.profile.surname);
      });
    });

    describe('save button', () => {
      let buttonElement: HTMLButtonElement;

      beforeEach(() => {
        const response = {ok: true} as Response;
        // We are working in the JSDOM world, so global.fetch is not defined. So define it!
        global.fetch = jest.fn().mockResolvedValue(response);
        buttonElement = renderResult.container.querySelector('button')
      });

      it('should be present in the DOM', () => {
        expect(buttonElement).toBeInTheDocument();
      });

      it('should have the onclick handler wired up properly', async () => {
        await fireEvent.click(buttonElement);
        expect(global.fetch).toHaveBeenCalled();
      });
    });
  });

  describe('exported behavior', () => {
    describe('handleOnClickSaveButton', () => {
      const requestInit = {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(props.profile)
      } as RequestInit;
      const url = `/api/profiles/${props.profile.id}`;

      describe('when the response returns successful status code', () => {
        beforeEach(async () => {
          const response = {ok: true} as Response;
          // We are working in the JSDOM world, so global.fetch is not defined. So define it!
          global.fetch = jest.fn().mockResolvedValue(response);
          await renderResult.component.handleOnClickSaveButton();
        });

        it('should invoke fetch API to update profile information in backend system', () => {
          expect(global.fetch).toHaveBeenCalledWith(url, requestInit);
        });
      });

      describe('when the response returns unsuccessful status code', () => {
        beforeEach(() => {
          const response = {ok: false, status: 400} as Response;
          // We are working in the JSDOM world, so global.fetch is not defined. So define it!
          global.fetch = jest.fn().mockResolvedValue(response);
        });

        it('should invoke fetch API to update profile information in backend system', async () => {
          try {
            await renderResult.component.handleOnClickSaveButton();
            fail('Should have thrown an error');
          } catch (e) {
            expect(global.fetch).toHaveBeenCalledWith(url, requestInit);
          }
        });

        it('should throw an error', async () => {
          try {
            await renderResult.component.handleOnClickSaveButton();
            fail('Should have thrown an error');
          } catch (e) {
            expect(e.message).toBe('Error updating profile. Status code: 400');
          }
        });
      });
    });
  });
});
