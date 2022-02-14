import { Route, Routes } from 'react-router-dom';
import WelcomeView from './views/WelcomeView';
import RacesView from './views/RacesView';
import RaceDetailView from './views/RaceDetailView';
import NewRaceView from './views/NewRaceView';
import EditRaceView from './views/EditRaceView';

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<WelcomeView />} />
      <Route path="races">
        <Route path="new" element={<NewRaceView />} />
        <Route path=":raceId" element={<RaceDetailView />} />
        <Route path=":raceId/edit" element={<EditRaceView />} />
        <Route index element={<RacesView />} />
      </Route>
    </Routes>
  );
}
