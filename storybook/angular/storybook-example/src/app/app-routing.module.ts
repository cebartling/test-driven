import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RaceListingViewComponent } from './views/race-listing-view/race-listing-view.component';
import { WelcomeViewComponent } from './views/welcome-view/welcome-view.component';

const routes: Routes = [
  { path: 'races', component: RaceListingViewComponent },
  // { path: 'race/{}', component: RaceViewComponent },
  // { path: 'race/{}/results', component: RaceResultsViewComponent },
  { path: '**', component: WelcomeViewComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
