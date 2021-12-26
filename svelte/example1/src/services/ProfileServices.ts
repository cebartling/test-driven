import type {Profile} from "../models/Profile";

export function updateProfile(profile: Profile): Promise<Response> {
  const requestInit = {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(profile)
  } as RequestInit;
  return fetch(`/api/profiles/${profile.id}`, requestInit);
}
