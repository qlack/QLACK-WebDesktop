import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {BaseComponent} from './shared/component/base-component';
import {Log} from 'ng2-logger/browser';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent extends BaseComponent implements OnInit {

  title = 'qlack-webdesktop-management-app-ui';
  //Logger
  private log = Log.create('AppComponent');

  // Controller for sidebar's visibility.
  sidebarVisibility = true;

  ngOnInit(): void {
    this.log.info('Initialising application.');
  }

  constructor(private router: Router, private dialog: MatDialog,
              private translate: TranslateService) {
    super();
    translate.setDefaultLang('el');
    translate.use('el');
  }

  toggleSidebar() {
    this.sidebarVisibility = !this.sidebarVisibility;
  }
}
