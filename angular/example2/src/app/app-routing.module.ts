import { RouterModule, Routes } from '@angular/router';
import { ExperimentsViewComponent } from './views/experiments-view/experiments-view.component';
import { NgModule } from '@angular/core';

const routes: Routes = [{ path: '**', component: ExperimentsViewComponent }];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {}
