import { useParams } from 'react-router-dom';

export default function EditRaceView() {
  const params = useParams();

  return (
    <div data-testid="edit-race-view-container">
      <h1>Edit Race {params.raceId}</h1>
    </div>
  );
}
