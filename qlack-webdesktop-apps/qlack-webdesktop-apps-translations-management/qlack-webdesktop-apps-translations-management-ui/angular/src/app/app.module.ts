import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FlexLayoutModule} from '@angular/flex-layout';
import {QFormsModule} from '@eurodyn/forms';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatPaginatorModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatSortModule,
  MatTableModule
} from '@angular/material';
import {LanguageEditComponent} from './language/language-edit.component';
import {TranslateLoader, TranslateModule, TranslateService} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {LanguageCreateComponent} from './language/language-create.component';
import {MatPaginatorIntl} from '@angular/material/paginator';
import {MyMatPaginatorIntl} from './my-mat-paginator-Intl ';
import {MatTabsModule} from '@angular/material/tabs';
import {MatSelectModule} from '@angular/material/select';
import {AppConstants} from './app.constants';


export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, AppConstants.API_ROOT+ '/translations?lang=', '');
}

export function createCustomMatPaginatorIntl(
  translateService: TranslateService
) {
  return new MyMatPaginatorIntl(translateService);
}

@NgModule({
  declarations: [
    AppComponent,
    LanguageEditComponent,
    LanguageCreateComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    QFormsModule,
    HttpClientModule,
    MatSlideToggleModule,
    FormsModule,
    MatSnackBarModule,
    MatListModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    MatTabsModule,
    MatSelectModule

  ],
  providers: [{provide: MatPaginatorIntl, deps: [TranslateService], useFactory: createCustomMatPaginatorIntl}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
