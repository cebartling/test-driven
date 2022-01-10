import {tick} from 'svelte';
import {get} from 'svelte/store';
import {profile, profileId, profileService} from '../ProfileStore';
import type {Profile} from '../../models/Profile';

describe('ProfileStore unit tests', () => {
  describe('ProfileService', () => {
    describe('fetchProfile', () => {

      const expectedProfile = {
        id: "78b6c7b2-6c30-4604-b7cd-56e6cecaae83",
        emailAddress: "jasper.shaw@example.com",
        givenName: "Jasper",
        surname: "Shawn"
      } as Profile;

      beforeEach(async () => {
        const response = {
          ok: true,
          json: () => Promise.resolve(expectedProfile)
        };
        global.window.fetch = jest.fn().mockResolvedValue(response);
        // Reset the store as the value caches between tests.
        profile.set(undefined);
        jest.spyOn(profile, 'set');

        // Execute the SUT
        await profileService.fetchProfile();

        // See https://svelte.dev/tutorial/tick for more information on what tick() does.
        await tick();
      });

      it('should invoke the fetch API to retrieve the profile from the backend REST API', () => {
        expect(global.fetch).toHaveBeenCalledWith(`/api/profiles/${get(profileId)}`, {"method": "GET"});
      });

      it('should set the profile store', () => {
        expect(profile.set).toHaveBeenCalledWith(expectedProfile);
      });

      it('should return the appropriate value from the store', () => {
        expect(get(profile)).toEqual(expectedProfile);
      });
    });

    describe('saveProfile', () => {
      const expectedProfile = {
        id: "78b6c7b2-6c30-4604-b7cd-56e6cecaae83",
        emailAddress: "jasper.shaw1@example.com",
        givenName: "Jasper1",
        surname: "Shawn"
      } as Profile;

      const requestInit = {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(expectedProfile)
      } as RequestInit;

      describe('when PUT invocation is successful', () => {
        beforeEach(async () => {
          const response = {
            ok: true,
          };
          global.window.fetch = jest.fn().mockResolvedValue(response);
          // Reset the store as the value caches between tests.
          profileId.set(undefined);
          jest.spyOn(profileService, 'fetchProfile').mockImplementation(async () => {
          });

          // Execute the SUT
          await profileService.saveProfile(expectedProfile);

          // See https://svelte.dev/tutorial/tick for more information on what tick() does.
          await tick();
        });

        it('should invoke the fetch API to update the profile in the backend REST API', () => {
          expect(global.fetch).toHaveBeenCalledWith(`/api/profiles/${expectedProfile.id}`, requestInit);
        });

        it('should set the profileId store with the id of the updated profile', () => {
          expect(get(profileId)).toEqual(expectedProfile.id);
        });

        it('should invoke profileService.fetchProfile function', () => {
          expect(profileService.fetchProfile).toHaveBeenCalled();
        });
      });

      describe('when PUT invocation is unsuccessful', () => {
        let capturedError: Error;

        beforeEach(async () => {
          jest.clearAllMocks();
          const response = {
            ok: false,
            status: '503'
          };
          global.window.fetch = jest.fn().mockResolvedValue(response);
          // Reset the store as the value caches between tests.
          profileId.set(undefined);
          jest.spyOn(profileService, 'fetchProfile').mockImplementation(async () => {
          });

          try {
            // Execute the SUT
            await profileService.saveProfile(expectedProfile);
            fail('An error should have been thrown.');
          } catch (e) {
            capturedError = e;
          }
        });

        it('should invoke the fetch API to update the profile in the backend REST API', () => {
          expect(global.fetch).toHaveBeenCalledWith(`/api/profiles/${expectedProfile.id}`, requestInit);
        });

        it('should not set the profileId store with the id of the updated profile', () => {
          expect(get(profileId)).toBeUndefined();
        });

        it('should not invoke profileService.fetchProfile function', () => {
          expect(profileService.fetchProfile).not.toHaveBeenCalled();
        });

        it('should throw an error', () => {
          expect(capturedError).toBeDefined();
          expect(capturedError.message).toEqual('Error updating profile. Status code: 503');
        });
      });
    });
  });
});


