import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { BrowserModule } from '@angular/platform-browser';
import { EditRaceViewComponent } from './views/edit-race-view/edit-race-view.component';
import { EditRiderViewComponent } from './views/edit-rider-view/edit-rider-view.component';
import { NgModule } from '@angular/core';
import { RaceDetailsViewComponent } from './views/race-details-view/race-details-view.component';
import { RacesViewComponent } from './views/races-view/races-view.component';
import { RiderDetailViewComponent } from './views/rider-detail-view/rider-detail-view.component';
import { RidersViewComponent } from './views/riders-view/riders-view.component';
import { WelcomeViewComponent } from './views/welcome-view/welcome-view.component';

@NgModule({
    declarations: [
        AppComponent,
        WelcomeViewComponent,
        RacesViewComponent,
        RaceDetailsViewComponent,
        EditRaceViewComponent,
        RidersViewComponent,
        RiderDetailViewComponent,
        EditRiderViewComponent,
    ],
    imports: [BrowserModule, AppRoutingModule],
    providers: [],
    bootstrap: [AppComponent],
})
export class AppModule {}
