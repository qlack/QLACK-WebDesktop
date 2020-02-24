import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserGroupComponent} from "./user-group.component";
import {MatTableModule} from "@angular/material/table";
import {RouterModule} from "@angular/router";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatCardModule} from "@angular/material/card";
import {UserGroupRoutingModule} from "./user-group-routing.module";
import {MatSortModule} from "@angular/material/sort";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatButtonModule} from "@angular/material/button";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatSelectModule} from "@angular/material/select";
import {QFormsModule} from "@eurodyn/forms";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatIconModule} from "@angular/material/icon";
import {DisplayModule} from "../shared/component/display/display.module";
import {MatTooltipModule} from "@angular/material/tooltip";
import {TranslateModule} from "@ngx-translate/core";
import {UserGroupEditComponent} from './user-group-edit.component';
import {MatDividerModule} from "@angular/material/divider";
import {ApplicationsModule} from "../applications/applications.module";
import {UsersModule} from "../users/users.module";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatAutocompleteModule} from "@angular/material/autocomplete";


@NgModule({
  declarations: [
    UserGroupComponent,
    UserGroupEditComponent,
  ],
  imports: [
    CommonModule,
    MatTableModule,
    RouterModule,
    MatPaginatorModule,
    MatCardModule,
    UserGroupRoutingModule,
    MatSortModule,
    FlexLayoutModule,
    MatButtonModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    QFormsModule,
    ReactiveFormsModule,
    MatIconModule,
    DisplayModule,
    MatTooltipModule,
    TranslateModule,
    MatDividerModule,
    ApplicationsModule,
    UsersModule,
    MatCheckboxModule,
    FormsModule,
    MatAutocompleteModule
  ],
  exports: [
    UserGroupEditComponent
  ]
})
export class UserGroupModule { }
