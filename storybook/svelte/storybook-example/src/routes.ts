import HomeView from "./views/HomeView.svelte";
import RacesView from "./views/RacesView.svelte";

const routes = [
  {name: '/', component: HomeView},
  {name: '/races', component: RacesView},
]

export {routes}
