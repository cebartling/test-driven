import { Route, Routes } from 'react-router-dom';
import WelcomeView from './views/WelcomeView';
import RacesView from './views/RacesView';

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<WelcomeView />} />
      <Route path="races" element={<RacesView />} />
    </Routes>
  );
}
