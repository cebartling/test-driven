import {cleanup, fireEvent, render, RenderResult} from '@testing-library/svelte'
import axios from "axios";
import ZipCodeLookup from "../ZipCodeLookup.svelte";
import type {ZipCodeLookupResult} from "../../models/ZipCodeLookupResult";

describe('ZipCodeLookup.svelte component', () => {
  let renderedComponent: RenderResult;
  const props = {zipCode: '55379'};

  beforeEach(() => {
    renderedComponent = render(ZipCodeLookup, {props});
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

    it('should invoke axios.get to fetch zip code information', () => {
      expect(axios.get).toHaveBeenCalledWith(`/zipCodes?zipCode=${props.zipCode}`, expectedConfig);
      expect(renderedComponent.component.zipCodeLookupResults).toEqual(expectedResult);
    });
  });
});
