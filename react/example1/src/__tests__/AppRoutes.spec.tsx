import { render, screen } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import AppRoutes from '../AppRoutes';

describe('AppRoutes', () => {
  beforeEach(() => {
    const { debug } = render(
      <BrowserRouter>
        <AppRoutes />
      </BrowserRouter>,
    );
    // debug();
  });

  it('should render welcome view by default', () => {
    expect(screen.getByTestId('welcome-view-container')).toHaveTextContent('Welcome');
  });
});
