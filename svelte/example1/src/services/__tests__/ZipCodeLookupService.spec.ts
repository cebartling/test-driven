import axios from 'axios';
import {lookupZipCode} from '../ZipCodeLookupService';
import type {ZipCodeLookupResult} from '../../models/ZipCodeLookupResult';

describe('ZipCodeLookupService', () => {
  describe('lookupZipCode function', () => {
    const zipCode = '55379';
    const expected: ZipCodeLookupResult[] = [];
    const expectedConfig = {};
    let actual;

    beforeEach(async () => {
      jest.spyOn(axios, 'get').mockResolvedValue(expected);
      actual = await lookupZipCode(zipCode);
    });

    it('should return a Promise<ZipCodeLookupResult[]>',  () => {
      expect(actual).toEqual(expected);
    });

    it('should invoke axios.get', () => {
      expect(axios.get).toHaveBeenCalledWith('/zipCodes?zipCode=55379', expectedConfig);
    });
  });
});
