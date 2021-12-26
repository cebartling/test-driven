import type {Profile} from '../../models/Profile';
import {ProfileServices} from '../ProfileServices';

describe('ProfileServices', () => {

  describe('updateProfile static method', () => {
    const profile = {
      id: "78b6c7b2-6c30-4604-b7cd-56e6cecaae83",
      emailAddress: "jasper.shaw@example.com",
      givenName: "Jasper",
      surname: "Shawn"
    } as Profile;

    const requestInit = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(profile)
    } as RequestInit;

    const url = `/api/profiles/${profile.id}`;
    let result;

    beforeEach(() => {
      const response = {ok: true} as Response;
      // We are working in the JSDOM world, so global.fetch is not defined. So define it!
      global.fetch = jest.fn().mockResolvedValue(response);
      result = ProfileServices.updateProfile(profile);
    });

    afterEach(() => {
      // @ts-ignore
      global.fetch.mockClear();
      delete global.fetch;
    });

    it('should return a promise which emits a single Response', async () => {
      let response: Response = await result;
      expect(response.ok).toBeTruthy();
    });

    it('should invoke fetch API to update profile information in backend system', () => {
      expect(global.fetch).toHaveBeenCalledWith(url, requestInit);
    });
  });
});
