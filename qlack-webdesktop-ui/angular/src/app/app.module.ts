import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';
import {
  MatButtonModule,
  MatDividerModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatSidenavModule,
  MatToolbarModule,
  MatTooltipModule
} from '@angular/material';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ApplicationComponent} from './application/application.component';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {HeaderComponent} from './common/header/header.component';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {WebdesktopService} from "./webdesktop.service";
import {StartMenuComponent} from "./common/start-menu/start-menu.component";
import {TranslateLoader, TranslateModule, TranslateService} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {ResizableModule} from 'angular-resizable-element';
import {QngPubsubModule} from '@qlack/qng-pubsub';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {ImagePreloadDirective} from './image-preload.directive';
import {CookieService} from 'ngx-cookie-service';
import {DataService} from './data.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, "/api/translations?lang=", "");
}

export function webDesktopServiceFactory(webDesktopService: WebdesktopService, translate: TranslateService): Function {
  return () => webDesktopService.getUserAttributeByName("defaultLanguage").toPromise().then(attribute => {
    if (sessionStorage.getItem('defaultLanguage') != null) {
      translate.setDefaultLang(sessionStorage.getItem('defaultLanguage'));
    } else {
      if (attribute != null) {
        translate.setDefaultLang(attribute.data);
      }
    }
  }).catch((err: any) => Promise.resolve().then(() => translate.setDefaultLang("en")));
}

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    ApplicationComponent,
    StartMenuComponent,
    ImagePreloadDirective,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatListModule,
    MatInputModule,
    MatGridListModule,
    MatTooltipModule,
    DragDropModule,
    MatIconModule,
    MatMenuModule,
    HttpClientModule,
    MatDividerModule,
    QngPubsubModule,
    MatSnackBarModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    ResizableModule
  ],
  providers: [
    WebdesktopService, CookieService, DataService,
    {
      provide: APP_INITIALIZER,
      useFactory: webDesktopServiceFactory,
      deps: [WebdesktopService, TranslateService],
      multi: true
    }
  ],
  bootstrap: [AppComponent],
  entryComponents: [ApplicationComponent]

})
export class AppModule {
}
