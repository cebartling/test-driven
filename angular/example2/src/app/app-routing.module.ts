import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { RacesViewComponent } from './views/races-view/races-view.component';
import { RidersViewComponent } from './views/riders-view/riders-view.component';
import { WelcomeViewComponent } from './views/welcome-view/welcome-view.component';

const routes: Routes = [
    { path: 'riders', component: RidersViewComponent },
    { path: 'races', component: RacesViewComponent },
    { path: '**', component: WelcomeViewComponent },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {}
