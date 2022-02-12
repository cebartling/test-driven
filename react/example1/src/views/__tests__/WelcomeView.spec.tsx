import {render, screen} from '@testing-library/react';
import '@testing-library/jest-dom';
import WelcomeView from "../WelcomeView";
import React from 'react';

describe('WelcomeView', () => {
  beforeEach(() => {
    render(<WelcomeView />);
  });

  it('should blah', () => {
    expect(screen.getByTestId('welcome-view-container')).toHaveTextContent('Welcome')
    // expect(screen.getByRole('button')).toBeDisabled()
  });
});
