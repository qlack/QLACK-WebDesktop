import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserGroupComponent} from './user-group.component';
import {UserGroupEditComponent} from "./user-group-edit.component";


const routes: Routes = [
  {path: '', component: UserGroupComponent},
  {path: ':id', component: UserGroupEditComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserGroupRoutingModule {
}
