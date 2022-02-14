import { render, screen } from '@testing-library/react';
import RaceDetailView from '../RaceDetailView';

describe('RaceDetailView', () => {
  beforeEach(() => {
    render(<RaceDetailView />);
  });

  it('should render appropriately', () => {
    expect(screen.getByTestId('race-detail-view-container')).toHaveTextContent('Race');
  });
});
