import {cleanup, fireEvent, render, RenderResult} from '@testing-library/svelte'
import ProfileEditor from '../ProfileEditor.svelte';
import type {Profile} from '../../models/Profile';


describe('ProfileEditor.svelte component', () => {
  let renderedComponent: RenderResult;
  const props = {
    profile: {
      id: "78b6c7b2-6c30-4604-b7cd-56e6cecaae83",
      emailAddress: "jasper.shaw@example.com",
      givenName: "Jasper",
      surname: "Shawn"
    } as Profile
  };

  beforeEach(() => {
    renderedComponent = render(ProfileEditor, {props});
  });

  afterEach(() => {
    cleanup();
  })

  describe('rendered component tree', () => {
    describe('email address input field', () => {
      let inputElement: HTMLInputElement;

      beforeEach(() => {
        inputElement = renderedComponent.container.querySelector('input#emailFormControlInput')
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
        inputElement = renderedComponent.container.querySelector('input#givenNameFormControlInput')
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
        inputElement = renderedComponent.container.querySelector('input#surnameFormControlInput')
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
        global.fetch = jest.fn().mockResolvedValue(response);
        buttonElement = renderedComponent.container.querySelector('button')
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


  // describe('handleOnSubmit function', () => {
  //   const expectedResult = [
  //     {} as ZipCodeLookupResult,
  //     {} as ZipCodeLookupResult,
  //     {} as ZipCodeLookupResult
  //   ];
  //   const expectedConfig = {};
  //
  //   beforeEach(async () => {
  //     jest.spyOn(axios, 'get').mockResolvedValue(expectedResult);
  //     const {getByTestId} = renderedComponent;
  //     const form = getByTestId('zip-code-lookup-form')
  //     await fireEvent.submit(form);
  //   });
  //
  //   it('should invoke axios.get to fetch zip code information', () => {
  //     expect(axios.get).toHaveBeenCalledWith(`/zipCodes?zipCode=${props.zipCode}`, expectedConfig);
  //     expect(renderedComponent.component.zipCodeLookupResults).toEqual(expectedResult);
  //   });
  // });
});
