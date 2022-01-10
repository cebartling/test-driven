import {get, writable} from 'svelte/store';
import type {Profile} from '../models/Profile';

export const profileId = writable(  "78b6c7b2-6c30-4604-b7cd-56e6cecaae83");
export const profile = writable({} as Profile);

export const fetchProfile = async () => {
  const response = await global.window.fetch(`/api/profiles/${get(profileId)}`, { method: "GET" });
  if (response.ok) {
    const profileData = await response.json();
    profile.set(profileData);
  }
};
