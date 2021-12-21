import {cleanup, fireEvent, render, RenderResult} from '@testing-library/svelte'
import ZipCodeLookup from "../ZipCodeLookup.svelte";
import type {ZipCodeLookupResult} from "../../models/ZipCodeLookupResult";
import axios from "axios";

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


  describe('handleOnSubmit function', () => {
    const expectedResult = [
      {} as ZipCodeLookupResult,
      {} as ZipCodeLookupResult,
      {} as ZipCodeLookupResult
    ];
    const expectedConfig = {};

    beforeEach(async () => {
      jest.spyOn(axios, 'get').mockResolvedValue(expectedResult);
      const {getByTestId} = renderedComponent;
      const form = getByTestId('zip-code-lookup-form')
      await fireEvent.submit(form);
    });

    it('should invoke axios.get', () => {
      expect(axios.get).toHaveBeenCalledWith('/zipCodes?zipCode=55379', expectedConfig);
      expect(renderedComponent.component.zipCodeLookupResults).toEqual(expectedResult);
    });
  });
});
