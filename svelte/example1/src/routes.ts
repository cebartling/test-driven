import ZipCodeLookupView from './views/ZipCodeLookupView.svelte';
import HomeView from './views/HomeView.svelte';
import ProfileView from './views/ProfileView.svelte';

const routes = [
  {    name: '/',    component: HomeView },
  {    name: '/profile',    component: ProfileView },
  {    name: '/zipcodelookup',    component: ZipCodeLookupView },
]

export { routes }
