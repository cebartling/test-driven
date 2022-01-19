import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RaceViewComponent } from './views/race-view/race-view.component';
import { WelcomeViewComponent } from './views/welcome-view/welcome-view.component';
import { RaceResultsViewComponent } from './views/race-results-view/race-results-view.component';
import { RaceListingViewComponent } from './views/race-listing-view/race-listing-view.component';
import { NavigationalHeaderComponent } from './components/navigational-header/navigational-header.component';
import { RaceOverviewCardComponent } from './components/race-overview-card/race-overview-card.component';
import { RaceOverviewListComponent } from './components/race-overview-list/race-overview-list.component';
import { HttpClientModule } from '@angular/common/http';
import { RaceDetailComponent } from './components/race-detail/race-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    RaceViewComponent,
    WelcomeViewComponent,
    RaceResultsViewComponent,
    RaceListingViewComponent,
    NavigationalHeaderComponent,
    RaceOverviewCardComponent,
    RaceOverviewListComponent,
    RaceDetailComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
