import {Component, ComponentFactoryResolver, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {Application} from "../../application";
import {ApplicationComponent} from "../../application/application.component";
import {WebdesktopService} from '../../webdesktop.service';
import {CookieService} from 'ngx-cookie-service';
import {DataService} from '../../data.service';
import * as uuid from 'uuid';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  static applicationId: number = 0;
  activeApplicationsComponents: any[] = [];
  tempApplicationComponent: any;
  profileImage: any[];
  userProfileApplicationName: string = 'User Profile Management';
  userProfileApplication: Application;
  @ViewChild('applicationContainer', {static: true, read: ViewContainerRef}) entry: ViewContainerRef;
  isSsoProfile: boolean;
  activeApplications: any[] = [];

  constructor(private resolver: ComponentFactoryResolver, private webDesktopService: WebdesktopService, private cookieService: CookieService,
              private dataService: DataService) {

  }

  ngOnInit() {
    this.webDesktopService.getActiveProfile().subscribe(isSsoProfile => {
      this.isSsoProfile = isSsoProfile;
    });
    this.webDesktopService.getUserAttributeByName("profileImage").subscribe(attribute => {
      if (attribute != null) {
        this.profileImage = attribute.bindata;
      }
    });
    this.webDesktopService.getActiveApplicationByName(this.userProfileApplicationName).subscribe(application => {
      if (application != null) {
        this.userProfileApplication = application;
      }
    });

    this.dataService.activeApplications.subscribe(apps => this.activeApplications = apps);

    this.webDesktopService.initSession().subscribe();
  }

  initApplication(application: Application) {
    if (this.activeApplicationIndex(application) != -1) {
      const index = this.activeApplicationIndex(application);
      if (this.activeApplicationsComponents[index].instance.multipleInstances) {
        this.createApplication(application);
      } else if (this.activeApplicationsComponents[index].instance.isMinimized) {
        this.activeApplicationsComponents[index].instance.showApplication();
      }
    } else {
      this.createApplication(application);
    }
  }

  destroyApplication(id) {
    for (let i = 0; i < this.activeApplicationsComponents.length; i++) {
      if (this.activeApplicationsComponents[i].instance.Id === id) {
        this.activeApplicationsComponents[i].destroy();
        this.activeApplicationsComponents.splice(i, 1);
      }
    }
  }

  activeApplicationIndex(application: Application) {
    for (let i = 0; i < this.activeApplicationsComponents.length; i++) {
      if (this.activeApplicationsComponents[i].instance.applicationName == application.applicationName) {
        return i;
      }
    }
    return -1;
  }

  createApplication(application: Application) {
    const factory = this.resolver.resolveComponentFactory(ApplicationComponent);
    this.tempApplicationComponent = this.entry.createComponent(factory);
    this.tempApplicationComponent.instance.isActive = true;
    this.tempApplicationComponent.instance.isDraggable = application.draggable;
    this.tempApplicationComponent.instance.isMinimizable = application.minimizable;
    this.tempApplicationComponent.instance.isMaximizable = application.maximizable;
    this.tempApplicationComponent.instance.isClosable = application.closable;
    this.tempApplicationComponent.instance.isResizable = application.resizable;
    this.tempApplicationComponent.instance.height = application.height;
    this.tempApplicationComponent.instance.width = application.width;
    this.tempApplicationComponent.instance.minWidth = application.minWidth;
    this.tempApplicationComponent.instance.minHeight = application.minHeight;
    this.tempApplicationComponent.instance.showTitle = application.showTitle;
    this.tempApplicationComponent.instance.multipleInstances = application.multipleInstances;
    this.tempApplicationComponent.instance.iconImageSrc = application.icon;
    this.tempApplicationComponent.instance.applicationName = application.applicationName;

    if (application.proxyAppPath == null || application.proxyAppPath == "") {
      this.tempApplicationComponent.instance.indexPageUrl = application.appUrl + application.appPath;
    } else {
      this.tempApplicationComponent.instance.indexPageUrl = application.proxyAppPath;
    }
    this.tempApplicationComponent.instance.Id = HeaderComponent.applicationId++;
    this.tempApplicationComponent.instance.iconSmallSrc = application.iconSmall;
    this.tempApplicationComponent.instance.onClose.subscribe(id => {
      this.destroyApplication(id);
    });
    this.activeApplicationsComponents.push(this.tempApplicationComponent);
  }

  openUserProfileApplication() {
    if (this.userProfileApplication) {
      this.initApplication(this.userProfileApplication);
    }
  }

  logout() {

    this.webDesktopService.terminateSession().subscribe();

    this.activeApplications.forEach((application) => {
      if (application.proxyAppPath) {
        this.webDesktopService.logout(application.proxyAppPath + 'logout').subscribe();
      }
    });

    this.webDesktopService.logout('/logout').subscribe(res => {
      this.cookieService.delete('JSESSIONID');
      window.location.href = res.url;
    });
  }

}
