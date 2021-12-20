import {cleanup, fireEvent, render, RenderResult} from '@testing-library/svelte'
import ZipCodeLookup from "../ZipCodeLookup.svelte";
import * as zipCodeLookupServiceExports from '../../services/ZipCodeLookupService';
import type {ZipCodeLookupResult} from "../../models/ZipCodeLookupResult";
import SpyInstance = jest.SpyInstance;

describe('ZipCodeLookup.svelte component', () => {
  let renderedComponent: RenderResult;

  beforeEach(() => {
    renderedComponent = render(ZipCodeLookup, {props: {zipCode: '55379'}});
  });

  afterEach(() => {
    cleanup();
  })

  describe('rendered component tree', () => {

    it('should contain a textual input field', () => {
      expect(renderedComponent.container).toContainHTML("<input type=\"text\">");
    });

    it('should contain a button', () => {
      expect(renderedComponent.container.querySelector("button")).toBeInTheDocument();
    });
  });


  // describe('handleOnSubmit function', () => {
  //   const expectedResult = [] as ZipCodeLookupResult[];
  //   let lookupZipCodeSpyInstance: SpyInstance;
  //
  //   beforeEach(async () => {
  //     lookupZipCodeSpyInstance = jest.spyOn(zipCodeLookupServiceExports, 'lookupZipCode')
  //       .mockResolvedValue(expectedResult);
  //     const {getByTestId} = renderedComponent;
  //     const button = getByTestId('zip-code-lookup-submit-button')
  //     await fireEvent.click(button);
  //   });
  //
  //   it('should invoke lookupZipCode function from ZipCodeLookupService', () => {
  //     expect(lookupZipCodeSpyInstance).toHaveBeenCalledTimes(1);
  //   });
  // });
});
