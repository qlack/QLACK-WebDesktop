/* tslint:disable:max-line-length */
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {routing} from './app-routing.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import {NgProgressModule} from '@ngx-progressbar/core';
import {AppConstants} from './app.constants';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatListModule} from '@angular/material/list';
import {MatMenuModule} from '@angular/material/menu';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatToolbarModule} from '@angular/material/toolbar';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {QFormsModule} from '@eurodyn/forms';
import {DisplayModule} from './shared/component/display/display.module';
import {OkCancelModalComponent} from './shared/component/display/ok-cancel-modal/ok-cancel-modal.component';
import {TextModalComponent} from './shared/component/display/text-modal/text-modal.component';
import {TranslateLoader, TranslateModule, TranslateService} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {MatPaginatorIntl} from "@angular/material/paginator";
import {MatPaginatorIntlService} from "./mat-paginator-intl.service";
import {MatSelectModule} from "@angular/material/select";
import {MatTabsModule} from "@angular/material/tabs";
import {MatTableModule} from "@angular/material/table";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {NavigationMenuComponent} from './shared/navigation-menu/navigation-menu.component';
import {DataService} from "./services/data.service";

export function HttpLoaderFactory(http: HttpClient) {
  let contextPath = window.location.pathname;
  return new TranslateHttpLoader(http, `${contextPath}` + AppConstants.API_ROOT + "/translations?lang=", "");
}

export function createCustomMatPaginatorIntl(
  translateService: TranslateService
) {
  return new MatPaginatorIntlService(translateService);
}

@NgModule({
  declarations: [
    AppComponent,
    NavigationMenuComponent
  ],
  imports: [
    BrowserModule,
    FlexLayoutModule,
    BrowserAnimationsModule,
    routing,
    HttpClientModule,
    MatMenuModule,
    MatDialogModule,
    MatToolbarModule,
    MatListModule,
    MatCardModule,
    MatIconModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSnackBarModule,
    MatInputModule,
    MatButtonModule,
    DisplayModule,
    NgProgressModule.withConfig({
      trickleSpeed: 500,
      debounceTime: 500,
      meteor: false,
      spinner: false,
      thick: false,
      color: '#50A7D7'
    }),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    MatSelectModule,
    FormsModule,
    MatTabsModule,
    MatTableModule,
    MatCheckboxModule
  ],
  providers: [
    QFormsModule,
    {provide: MatPaginatorIntl, deps: [TranslateService], useFactory: createCustomMatPaginatorIntl},
    DataService
  ],
  bootstrap: [AppComponent],
  entryComponents: [OkCancelModalComponent, TextModalComponent],
})
export class AppModule {
}
