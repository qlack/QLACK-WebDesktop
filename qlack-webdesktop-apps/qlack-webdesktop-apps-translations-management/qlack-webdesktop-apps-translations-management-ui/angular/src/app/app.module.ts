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
import {MatDialogModule} from '@angular/material/dialog';
import {QngPubsubModule, QngPubsubService} from '@qlack/qng-pubsub';
import {QPubSub} from '@qlack/qpubsub';


export function HttpLoaderFactory(http: HttpClient) {
  let contextPath = window.location.pathname;
  return new TranslateHttpLoader(http, `${contextPath}` + AppConstants.API_ROOT + "/translations?lang=", "");
}

export function translationsServiceFactory(qPubSubService: QngPubsubService, translate: TranslateService){
  return () =>  new Promise((resolve) => {
    qPubSubService.init('client-' + Math.floor(Math.random() * 9000), false);
      qPubSubService.publish('QDefaultLanguageRequest', '');
      qPubSubService.subscribe('QDefaultLanguageResponse', (message: QPubSub.Message) => {
        translate.setDefaultLang(message.msg);
        resolve();
  });
  });
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
    MatSelectModule,
    QngPubsubModule

  ],
  providers: [TranslateService,{
    provide: APP_INITIALIZER,
    useFactory: translationsServiceFactory,
    deps: [QngPubsubService, TranslateService],
    multi: true
  }, {provide: MatPaginatorIntl, deps: [TranslateService], useFactory: createCustomMatPaginatorIntl}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
