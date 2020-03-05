import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ApplicationsComponent} from './applications.component';
import {ApplicationsEditComponent} from './applications-edit.component';
import {FileuploadComponent} from "../fileupload/fileupload.component";
import {PermissionsComponent} from "./permissions/permissions.component";

const routes: Routes = [
  {path: '', component: ApplicationsComponent},
  {path: ':id', component: ApplicationsEditComponent,
    children: [
      {path: "permissions", component: PermissionsComponent}
  ]},
  {path: 'upload', component: FileuploadComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationsRoutingModule {
}
