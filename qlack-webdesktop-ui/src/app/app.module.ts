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
import { SidebarComponent } from './common/sidebar/sidebar.component';
import { HeaderComponent } from './common/header/header.component';


@NgModule({
  declarations: [
    AppComponent,
    WidgetComponent,
    SidebarComponent,
    HeaderComponent,
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
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
