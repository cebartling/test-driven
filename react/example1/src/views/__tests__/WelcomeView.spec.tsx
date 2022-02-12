import '@testing-library/jest-dom';
import { render, screen } from '@testing-library/react';
import WelcomeView from '../WelcomeView';

describe('WelcomeView', () => {
  beforeEach(() => {
    render(<WelcomeView />);
  });

  it('should render appropriately', () => {
    expect(screen.getByTestId('welcome-view-container')).toHaveTextContent('Welcome');
  });
});
