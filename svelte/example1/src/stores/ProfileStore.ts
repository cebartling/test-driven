import {get, writable} from 'svelte/store';
import type {Profile} from '../models/Profile';

export const profileId = writable("78b6c7b2-6c30-4604-b7cd-56e6cecaae83");
export const profile = writable(undefined);

class ProfileService {
  async fetchProfile() {
    const requestInit = {method: "GET"} as RequestInit;
    const response = await fetch(`/api/profiles/${get(profileId)}`, requestInit);
    if (response.ok) {
      const profileData = await response.json();
      profile.set(profileData);
    }
  };

  async saveProfile(updatedProfile: Profile) {
    const requestInit = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(updatedProfile)
    } as RequestInit;
    const response = await fetch(`/api/profiles/${updatedProfile.id}`, requestInit);
    if (response.ok) {
      profileId.set(updatedProfile.id);
      await this.fetchProfile();
    }
    else {
      throw new Error(`Error updating profile. Status code: ${response.status}`)
    }
  };
}

export const profileService = new ProfileService();

