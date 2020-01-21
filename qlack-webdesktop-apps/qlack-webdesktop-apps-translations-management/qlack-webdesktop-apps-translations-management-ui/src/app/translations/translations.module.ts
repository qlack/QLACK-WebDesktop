import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {TranslationsRootingModule} from './translations-rooting.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import {QFormsModule} from '@eurodyn/forms';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatInputModule,
  MatPaginatorModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatSortModule,
  MatTableModule
} from '@angular/material';

import {TranslationsComponent} from './translations.component';
import {TranslationsEditComponent} from './translations-edit.component';
import {MatSelectModule} from '@angular/material/select';


@NgModule({
  declarations: [

    TranslationsComponent,
    TranslationsEditComponent
  ],
  imports: [
    CommonModule,
    TranslationsRootingModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatInputModule,
    MatFormFieldModule,
    MatCardModule,
    MatButtonModule,
    QFormsModule,
    HttpClientModule,
    MatSlideToggleModule,
    FormsModule,
    MatSnackBarModule,
    TranslateModule,
    MatSelectModule

  ],

})
export class TranslationsModule {
}
