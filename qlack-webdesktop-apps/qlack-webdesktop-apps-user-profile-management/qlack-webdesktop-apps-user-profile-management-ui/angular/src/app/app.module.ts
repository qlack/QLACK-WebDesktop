import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {UserProfileComponent} from './user-profile/user-profile.component';
import {MatDividerModule} from '@angular/material/divider';
import {MatCardModule} from '@angular/material/card';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {FlexModule} from '@angular/flex-layout';
import {MatButtonModule} from '@angular/material/button';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {MatDialogModule} from '@angular/material/dialog';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatInputModule} from '@angular/material/input';
import {MaterialFileInputModule} from 'ngx-material-file-input';
import {MatIconModule} from '@angular/material/icon';
import {TranslateLoader, TranslateModule, TranslateService} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {AppConstants} from './app.constants';
import {NoSecurityProfileComponent} from './no-security-profile/no-security-profile.component';
import {QngPubsubModule, QngPubsubService} from '@qlack/qng-pubsub';
import {QPubSub} from '@qlack/qpubsub';


export function HttpLoaderFactory(http: HttpClient) {
  let contextPath = window.location.pathname;
  return new TranslateHttpLoader(http, `${contextPath}` + AppConstants.API_ROOT + "/translations?lang=", "");
}

export function translationsServiceFactory(qPubSubService: QngPubsubService, translate: TranslateService) {
  return () => new Promise((resolve) => {
    qPubSubService.init('client-' + Math.floor(Math.random() * 9000), false);
    qPubSubService.publish('QDefaultLanguageRequest', '');
    qPubSubService.subscribe('QDefaultLanguageResponse', (message: QPubSub.Message) => {
      translate.setDefaultLang(message.msg);
      resolve();
    });
  });
}

@NgModule({
  declarations: [
    AppComponent,
    UserProfileComponent,
    NoSecurityProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatDividerModule,
    MatCardModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    FlexModule,
    MatButtonModule,
    HttpClientModule,
    MatDialogModule,
    MatSnackBarModule,
    FormsModule,
    MatInputModule,
    MaterialFileInputModule,
    MatIconModule,
    QngPubsubModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
  ],
  providers: [
    TranslateService,
    {
      provide: APP_INITIALIZER,
      useFactory: translationsServiceFactory,
      deps: [QngPubsubService, TranslateService],
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
