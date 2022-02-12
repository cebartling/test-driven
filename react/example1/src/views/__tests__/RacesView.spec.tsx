import { render, screen } from '@testing-library/react';
import RacesView from '../RacesView';

describe('RacesView', () => {
  beforeEach(() => {
    render(<RacesView />);
  });

  it('should render appropriately', () => {
    expect(screen.getByTestId('races-view-container')).toHaveTextContent('Races');
  });
});
