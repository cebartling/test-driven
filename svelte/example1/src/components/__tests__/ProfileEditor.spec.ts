import {cleanup, fireEvent, render} from '@testing-library/svelte'
import type {RenderResult} from '@testing-library/svelte'
import ProfileEditor from '../ProfileEditor.svelte';
import type {Profile} from '../../models/Profile';
import {profileService} from '../../stores/ProfileStore';

// https://jestjs.io/docs/mock-functions#mocking-modules
// https://jestjs.io/docs/manual-mocks
// Mock the module using the manual mocks found in '../../stores/__mocks__/ProfileStore'
jest.mock('../../stores/ProfileStore');

describe('ProfileEditor.svelte component', () => {
  let renderResult: RenderResult;
  const profile = {
    id: "78b6c7b2-6c30-4604-b7cd-56e6cecaae83",
    emailAddress: "jasper.shaw@example.com",
    givenName: "Jasper",
    surname: "Shawn"
  } as Profile;

  beforeEach(() => {
    // @ts-ignore
    window.MyNamespace = {
      showAlert: jest.fn()
    };
    renderResult = render(ProfileEditor, {props: {profile}});
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
        buttonElement = renderResult.container.querySelector('button')
      });

      it('should be present in the DOM', () => {
        expect(buttonElement).toBeInTheDocument();
      });

      describe('onclick event handler', () => {

        beforeEach(async () => {
          await fireEvent.click(buttonElement);
        });

        it('should invoke profileService.saveProfile function', () => {
          expect(profileService.saveProfile).toHaveBeenCalledWith(profile);
        });

        it('should invoke window.MyNamespace.showAlert function', () => {
          // @ts-ignore
          expect(window.MyNamespace.showAlert).toHaveBeenCalledWith('The profile was saved!');
        });
      });
    });
  });
});
