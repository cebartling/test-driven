import {cleanup, getByText, render, RenderResult} from '@testing-library/svelte';
import type {Profile} from '../../models/Profile';
import ProfileView from '../ProfileView.svelte';

describe('ProfileEditor.svelte component', () => {
  let renderResult: RenderResult;
  const profile = {
    id: "78b6c7b2-6c30-4604-b7cd-56e6cecaae83",
    emailAddress: "jasper.shaw@example.com",
    givenName: "Jasper",
    surname: "Shawn"
  } as Profile;

  beforeEach(() => {
    global.fetch = jest.fn().mockResolvedValue({
      json: () => Promise.resolve(profile)
    });
    renderResult = render(ProfileView);
  });

  afterEach(() => {
    cleanup();
  })

  describe('component rendered tree', () => {
    it('should render the ProfileEditor component', () => {
      const {getByText} = renderResult;
      expect(getByText(/Email address/)).toBeInTheDocument();
      expect(getByText(/Given name/)).toBeInTheDocument();
      expect(getByText(/Surname/)).toBeInTheDocument();
    });

    it('should invoke fetch to get the profile', () => {
      expect(global.fetch).toHaveBeenCalledWith(`/api/profiles/${renderResult.component.profileId}`)
    });
  });
});
