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
import {ResizeBorderModule} from 'resize-border';
import {HeaderComponent} from './common/header/header.component';
import {AngularResizedEventModule} from 'angular-resize-event';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {WidgetService} from "./widget.service";
import {StartMenuComponent} from "./common/start-menu/start-menu.component";
import {GroupByPipe} from './group-by.pipe';
import {SortByPipe} from './sort-by.pipe';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, "/apps/translations?lang=", "");
}

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    WidgetComponent,
    StartMenuComponent,
    GroupByPipe,
    SortByPipe
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
    ResizeBorderModule,
    DragDropModule,
    MatIconModule,
    MatMenuModule,
    AngularResizedEventModule,
    HttpClientModule,
    MatDividerModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],
  providers: [WidgetService],
  bootstrap: [AppComponent],
  entryComponents: [WidgetComponent]

})
export class AppModule {
}
