import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
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
import {WidgetComponent} from './widget/widget.component';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {HeaderComponent} from './common/header/header.component';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {WidgetService} from "./widget.service";
import {StartMenuComponent} from "./common/start-menu/start-menu.component";
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {ResizableModule} from 'angular-resizable-element';
import {QngPubsubModule} from '@qlack/qng-pubsub';
import {MatSnackBarModule} from '@angular/material/snack-bar';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, "/apps/translations?lang=", "");
}

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    WidgetComponent,
    StartMenuComponent,
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
  providers: [WidgetService],
  bootstrap: [AppComponent],
  entryComponents: [WidgetComponent]

})
export class AppModule {
}
