import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {BaseComponent} from './shared/component/base-component';
import {Log} from 'ng2-logger/browser';
import {TranslateService} from '@ngx-translate/core';
import {ApplicationsService} from "./services/applications.service";
import {Title} from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  encapsulation: ViewEncapsulation.None
})
export class AppComponent extends BaseComponent implements OnInit {

  title = 'qlack-webdesktop-management-app-ui';
  // Controller for sidebar's visibility.
  sidebarVisibility = true;
  //Logger
  private log = Log.create('AppComponent');

  constructor(private applicationService: ApplicationsService, private translate: TranslateService, private titleService: Title) {
    super();
    translate.get('management-app-ui.tabTitle').subscribe((title: string) => {
      titleService.setTitle(title);
    });
  }

  ngOnInit(): void {
    this.log.info('Initialising application.');
  }

  toggleSidebar() {
    this.sidebarVisibility = !this.sidebarVisibility;
  }
}
