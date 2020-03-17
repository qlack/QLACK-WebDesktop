import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {BaseComponent} from './shared/component/base-component';
import {Log} from 'ng2-logger/browser';
import {TranslateService} from '@ngx-translate/core';
import {ApplicationsService} from "./services/applications.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
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

  constructor(private applicationService: ApplicationsService, private translate: TranslateService) {
    super();
    this.applicationService.getUserAttributeByName("defaultLanguage").subscribe(attr => {
      if(attr != null){
        translate.setDefaultLang(attr.data);
      }
      else{
        if (sessionStorage.getItem('defaultLanguage') != null) {
          translate.setDefaultLang(sessionStorage.getItem('defaultLanguage'));
        } else {
          translate.setDefaultLang("en");
        }
      }
    });
  }

  toggleSidebar() {
    this.sidebarVisibility = !this.sidebarVisibility;
  }
}
