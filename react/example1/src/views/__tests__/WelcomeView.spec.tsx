import React from 'react';
import '@testing-library/jest-dom';
import { render, screen } from '@testing-library/react';
import WelcomeView from '../WelcomeView';

describe('WelcomeView', () => {
   beforeEach(() => {
      render(<WelcomeView />);
   });

   it('should blah', () => {
      expect(screen.getByTestId('welcome-view-container')).toHaveTextContent('Welcome');
   });
});
