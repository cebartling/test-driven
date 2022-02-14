import { useQuery } from '@apollo/client';
import { ALL_RACES_QUERY } from '../graphql/queries/AllRacesQuery';

export default function RacesView() {
  const { loading, error, data } = useQuery(ALL_RACES_QUERY);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error :(</p>;

  return (
    <div data-testid="races-view-container">
      <h1>Races</h1>
      {JSON.stringify(data)}
    </div>
  );
}
