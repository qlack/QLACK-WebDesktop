import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SidebarComponent } from './common/sidebar/sidebar.component';


const routes: Routes = [
  { path:  '', redirectTo:  'apps', pathMatch:  'full'},
  {path: 'apps', component: SidebarComponent }
  // {path: 'login', component: LoginComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
