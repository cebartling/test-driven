import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {WelcomeViewComponent} from "./views/welcome-view/welcome-view.component";
import {MapViewComponent} from "./views/map-view/map-view.component";

const routes: Routes = [
  {path: 'welcome', component: WelcomeViewComponent},
  {path: 'map', component: MapViewComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
