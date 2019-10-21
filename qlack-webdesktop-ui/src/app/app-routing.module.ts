import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ImageListComponent} from "./image-list/image-list.component";


const routes: Routes = [
  { path:  '', redirectTo:  'apps', pathMatch:  'full'},
  {path: 'apps', component: ImageListComponent }
  // {path: 'login', component: LoginComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
