// Manual mocks: https://jestjs.io/docs/manual-mocks

import {writable} from 'svelte/store';
import type {Profile} from '../../models/Profile';

const expectedProfile = {
  id: "78b6c7b2-6c30-4604-b7cd-56e6cecaae83",
  emailAddress: "jasper/.shaw@example.com",
  givenName: "Jasper",
  surname: "Shawn"
} as Profile;

export const profileId = writable(expectedProfile.id);
export const profile = writable(expectedProfile);

export const fetchProfile = jest.fn();
