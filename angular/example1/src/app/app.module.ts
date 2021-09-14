import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MapViewComponent } from './views/map-view/map-view.component';
import { WelcomeViewComponent } from './views/welcome-view/welcome-view.component';
import { MapComponent } from './components/map/map.component';
import { HttpClientModule } from '@angular/common/http';
import { ProfileViewComponent } from './views/profile-view/profile-view.component';

@NgModule({
  declarations: [AppComponent, MapViewComponent, WelcomeViewComponent, MapComponent, ProfileViewComponent],
  imports: [BrowserModule, AppRoutingModule, LeafletModule, HttpClientModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
