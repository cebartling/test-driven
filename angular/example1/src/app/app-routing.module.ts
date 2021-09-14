import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WelcomeViewComponent } from './views/welcome-view/welcome-view.component';
import { MapViewComponent } from './views/map-view/map-view.component';
import { ProfileViewComponent } from './views/profile-view/profile-view.component';

export const PATH_WELCOME = 'welcome';
export const PATH_MAP = 'map';
export const PATH_PROFILE = 'profile';
export const PATH_WILDCARD = '**';

export const routes: Routes = [
  { path: PATH_WELCOME, component: WelcomeViewComponent },
  { path: PATH_MAP, component: MapViewComponent },
  { path: PATH_PROFILE, component: ProfileViewComponent },
  { path: PATH_WILDCARD, component: WelcomeViewComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
