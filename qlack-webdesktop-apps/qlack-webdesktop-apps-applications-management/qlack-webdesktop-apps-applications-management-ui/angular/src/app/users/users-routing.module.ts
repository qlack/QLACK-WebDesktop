import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UsersComponent} from './users.component';
import {UsersEditComponent} from "./users-edit.component";

const routes: Routes = [
  {path: '', component: UsersComponent},
  {path: ':id', component: UsersEditComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoutingModule {
}
