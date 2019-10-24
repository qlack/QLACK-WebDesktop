import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MatToolbarModule, MatButtonModule, MatSidenavModule, MatListModule } from '@angular/material';
import { MatInputModule, MatGridListModule, MatIconModule, MatMenuModule, MatTooltipModule } from '@angular/material';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { WidgetComponent } from './widget/widget.component';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {ResizeBorderModule} from 'resize-border';
import { HeaderComponent } from './common/header/header.component';
import { AngularResizedEventModule } from 'angular-resize-event';
import { StartMenuComponent } from './common/start-menu/start-menu.component';


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
    ResizeBorderModule,
    DragDropModule,
    MatIconModule,
    MatMenuModule,
    AngularResizedEventModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
