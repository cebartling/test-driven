import {lookupZipCode} from '../ZipCodeLookupService';
import axios from "axios";

describe('ZipCodeLookupService', () => {
  describe('lookupZipCode function', () => {
    const zipCode = '55379';
    let actual;
    const expected = '';
    const expectedConfig = {};

    beforeEach(async () => {
      jest.spyOn(axios, 'get').mockResolvedValue(expected);
      actual = await lookupZipCode(zipCode);
    });

    it('should return a Promise<string>',  () => {
      expect(actual).toEqual(expected);
    });

    it('should invoke axios.get', () => {
      expect(axios.get).toHaveBeenCalledWith('/zipCodes?zipCode=55379', expectedConfig);
    });
  });
});
