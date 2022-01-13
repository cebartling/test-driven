import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RaceViewComponent } from './views/race-view/race-view.component';
import { WelcomeViewComponent } from './views/welcome-view/welcome-view.component';
import { RaceResultsViewComponent } from './views/race-results-view/race-results-view.component';
import { RaceListingViewComponent } from './views/race-listing-view/race-listing-view.component';
import { NavigationalHeaderComponent } from './components/navigational-header/navigational-header.component';

@NgModule({
  declarations: [
    AppComponent,
    RaceViewComponent,
    WelcomeViewComponent,
    RaceResultsViewComponent,
    RaceListingViewComponent,
    NavigationalHeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
