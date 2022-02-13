import { useParams } from 'react-router-dom';

export default function RaceDetailView() {
  const params = useParams();

  return (
    <div data-testid="race-detail-view-container">
      <h1>Race {params.raceId}</h1>
    </div>
  );
}
