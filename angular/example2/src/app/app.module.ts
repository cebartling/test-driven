import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { BrowserModule } from '@angular/platform-browser';
import { ExperimentsViewComponent } from './views/experiments-view/experiments-view.component';
import { NgModule } from '@angular/core';

@NgModule({
    declarations: [AppComponent, ExperimentsViewComponent],
    imports: [BrowserModule, AppRoutingModule],
    providers: [],
    bootstrap: [AppComponent],
})
export class AppModule {}
