import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ApplicationsRoutingModule} from './applications-routing.module';
import {ApplicationsComponent} from './applications.component';
import {ApplicationsEditComponent} from './applications-edit.component';
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
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {QFormsModule} from '@eurodyn/forms';
import {DisplayModule} from '../shared/component/display/display.module';
import {TranslateModule} from '@ngx-translate/core';
import {FileuploadComponent} from "../fileupload/fileupload.component";
import {MatDividerModule} from "@angular/material/divider";
import {MatTooltipModule} from "@angular/material/tooltip";
import {QLACKFormValidationModule} from "@qlack/form-validation";

@NgModule({
  declarations: [
    ApplicationsComponent,
    ApplicationsEditComponent,
    FileuploadComponent
  ],
  imports: [
    CommonModule,
    ApplicationsRoutingModule,
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
    MatSlideToggleModule,
    TranslateModule,
    MatDividerModule,
    FormsModule,
    MatTooltipModule,
    QLACKFormValidationModule
  ]
})
export class ApplicationsModule {
}
