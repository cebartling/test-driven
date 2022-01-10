import {cleanup, getByText, render, RenderResult} from '@testing-library/svelte';
import ProfileView from '../ProfileView.svelte';
import {fetchProfile} from '../../stores/ProfileStore';

// https://jestjs.io/docs/mock-functions#mocking-modules
// https://jestjs.io/docs/manual-mocks
// Mock the module using the manual mocks found in '../../stores/__mocks__/ProfileStore'
jest.mock('../../stores/ProfileStore');

describe('ProfileView.svelte component', () => {
  let renderResult: RenderResult;

  beforeEach(() => {
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

    it('should invoke the fetchProfile function', () => {
      // This is a spy defined in '../../stores/__mocks__/ProfileStore'
      expect(fetchProfile).toHaveBeenCalled();
    });
  });
});
