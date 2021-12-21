import ZipCodeLookupView from './views/ZipCodeLookupView.svelte';
import HomeView from "./views/HomeView.svelte";

const routes = [
  {    name: '/',    component: HomeView },
  {    name: '/zipcodelookup',    component: ZipCodeLookupView },
  // { name: 'login', component: Login, layout: PublicLayout },
]

export { routes }
