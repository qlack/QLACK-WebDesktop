import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {UsersRoutingModule} from './users-routing.module';
import {UsersComponent} from './users.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSelectModule} from '@angular/material/select';
import {MatSortModule} from '@angular/material/sort';
import {MatTableModule} from '@angular/material/table';
import {QFormsModule} from '@eurodyn/forms';
import {ReactiveFormsModule} from '@angular/forms';
import {DisplayModule} from '../shared/component/display/display.module';
import {MatTooltipModule} from '@angular/material/tooltip';
import {TranslateModule} from "@ngx-translate/core";
import {UsersEditComponent} from './users-edit.component';
import {MatAutocompleteModule} from "@angular/material/autocomplete";

@NgModule({
  declarations: [
    UsersComponent,
    UsersEditComponent,
  ],
  exports: [
    UsersComponent
  ],
  imports: [
    CommonModule,
    UsersRoutingModule,
    FlexLayoutModule,
    MatButtonModule,
    MatCardModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule,
    MatSelectModule,
    MatTableModule,
    QFormsModule,
    ReactiveFormsModule,
    MatSortModule,
    MatIconModule,
    DisplayModule,
    MatTooltipModule,
    TranslateModule,
    MatAutocompleteModule
  ],
})
export class UsersModule {
}
