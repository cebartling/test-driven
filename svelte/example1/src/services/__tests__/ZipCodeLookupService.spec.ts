import {lookupZipCode} from '../ZipCodeLookupService';

describe('ZipCodeLookupService', () => {
  describe('lookupZipCode function', () => {
    it('should return a Promise<string>', async () => {
      const actual = await lookupZipCode();
      expect(actual).toEqual("something");
    });
  });
});
