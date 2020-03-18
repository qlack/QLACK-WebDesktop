import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';
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
import {TranslationService} from './services/translation.service';
import {MatDialogModule} from '@angular/material/dialog';


export function HttpLoaderFactory(http: HttpClient) {
  let contextPath = window.location.pathname;
  return new TranslateHttpLoader(http, `${contextPath}` + AppConstants.API_ROOT+"/translations?lang=", "");
}

export function translationsServiceFactory(translationService: TranslationService,translate: TranslateService): Function {
  return () => translationService.getUserAttributeByName("defaultLanguage").toPromise().then(attribute => {
    if (attribute != null) {
      if (attribute.data != null) {
        translate.setDefaultLang(attribute.data);
      }
    } else {
      if (sessionStorage.getItem('defaultLanguage') != null) {
        translate.setDefaultLang(sessionStorage.getItem('defaultLanguage'));
      } else {
        translate.setDefaultLang("en");
      }
    }
  }).catch((err: any) => Promise.resolve().then(() => translate.setDefaultLang("en")));
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
    MatDialogModule,
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
  providers: [{
    provide: APP_INITIALIZER,
    useFactory: translationsServiceFactory,
    deps: [TranslationService,TranslateService],
    multi: true
  },{provide: MatPaginatorIntl, deps: [TranslateService], useFactory: createCustomMatPaginatorIntl}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
