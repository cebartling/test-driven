import {tick} from 'svelte';
import {get} from 'svelte/store';
import {fetchProfile, profile, profileId} from '../ProfileStore';
import type {Profile} from '../../models/Profile';

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
    await fetchProfile();

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
