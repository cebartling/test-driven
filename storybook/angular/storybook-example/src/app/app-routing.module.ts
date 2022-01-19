import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RaceListingViewComponent } from './views/race-listing-view/race-listing-view.component';
import { WelcomeViewComponent } from './views/welcome-view/welcome-view.component';
import { RaceViewComponent } from './views/race-view/race-view.component';

const routes: Routes = [
  { path: 'races', component: RaceListingViewComponent },
  { path: 'race/:id', component: RaceViewComponent },
  // { path: 'race/:id/results', component: RaceResultsViewComponent },
  { path: '**', component: WelcomeViewComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
