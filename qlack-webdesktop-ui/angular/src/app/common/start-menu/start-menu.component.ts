import {Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Application} from "../../application";
import {WebdesktopService} from "../../webdesktop.service";
import {TranslateService} from '@ngx-translate/core';
import {QngPubsubService} from '@qlack/qng-pubsub';
import {QPubSub} from '@qlack/qpubsub';
import {MatSnackBar} from '@angular/material/snack-bar';
import {UserDetailsDto} from "../../dto/user-details-dto";
import {DataService} from '../../data.service';

@Component({
  selector: 'app-start-menu',
  templateUrl: './start-menu.component.html',
  styleUrls: ['./start-menu.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class StartMenuComponent implements OnInit {

  applications: Application[] = [];
  webDesktopUiLexiconGroup: string = 'webdesktop-ui';
  sortedApplications = new Array<Application[]>();
  columns = 4;
  @Output() onAppClick = new EventEmitter();
  @Input() isSsoProfile;
  userDetailsDto: any = new UserDetailsDto();
  private allowedOrigins: string[] = [];
  private timeOutCancel: boolean = false;
  refreshPageMessage: string;
  cancel: string;

  constructor(private webDesktopService: WebdesktopService, private translate: TranslateService, private qPubSubService: QngPubsubService,
              private _snackBar: MatSnackBar, private dataService: DataService) {
    this.webDesktopService.getUserAttributes().subscribe(userAttributeList => {
      if (userAttributeList != null) {
        if (userAttributeList.firstName != null) {
          this.userDetailsDto.firstName = userAttributeList.firstName.data;
        }
        if (userAttributeList.lastName != null) {
          this.userDetailsDto.lastName = userAttributeList.lastName.data;
        }
        if (userAttributeList.defaultLanguage != null) {
          this.userDetailsDto.defaultLanguage = userAttributeList.defaultLanguage.data;
        } else {
          this.userDetailsDto.defaultLanguage = 'en';
        }
        if (userAttributeList.profileImage != null) {
          this.userDetailsDto.profileImage = userAttributeList.profileImage.bindata;
        }
        if (userAttributeList.backgroundImage != null) {
          this.userDetailsDto.backgroundImage = userAttributeList.backgroundImage.bindata;
        }
      }
    });
  }

  ngOnInit() {

    this.translate.get([
      'webdesktop-ui.refreshPageMessage',
      'webdesktop-ui.cancel'
    ])
    .subscribe(translation => {
      this.refreshPageMessage = translation['webdesktop-ui.refreshPageMessage'];
      this.cancel = translation['webdesktop-ui.cancel'];
    });

    this.webDesktopService.getActiveApplications().subscribe(applicationsList => {
      this.dataService.initApplications(applicationsList);

      applicationsList.forEach((application, index) => {

        // Add application url to the list of allowed origins for QPubSub
        if (application.appUrl != null) {
          this.allowedOrigins.push(application.appUrl);
        }

        this.translate.get(application.applicationName + '.title').subscribe(
            (titleTranslated: string) => {
              application.applicationTitle = titleTranslated;
            }).add(() => {
          this.translate.get(this.webDesktopUiLexiconGroup + '.' + application.groupName).subscribe(
              (groupTranslated: string) => {
                if (groupTranslated == "webdesktop-ui.") {
                  application.groupTranslated = "";
                } else {
                  application.groupTranslated = groupTranslated;
                }
                this.applications.push(application);
              }).add(() => {

            if (index == (applicationsList.length - 1)) {
              this.applications.sort(
                  (a, b) => !a.groupTranslated ? 1 : (!b.groupTranslated ? -1 : (a.groupTranslated.toLowerCase().localeCompare(
                      b.groupTranslated.toLowerCase()))));
              const groups = [...new Set(this.applications.map(w => w.groupTranslated))];

              groups.forEach(group => {
                this.sortedApplications.push(this.applications.filter(w => w.groupTranslated === group));
              });
              this.sortedApplications.forEach((widthArray, i) => {
                this.onCreateMenuColumns(widthArray.length);
                widthArray.sort((a, b) => {
                  return a.applicationName.toLowerCase().localeCompare(b.applicationName.toLowerCase());
                });
                this.sortedApplications[i] = widthArray;
              })
            }
          });
        });
      });

      // Add current host to QPubSub allowed origins
      this.allowedOrigins.push(window.location.origin);
      // Initialize QPubSub
      this.qPubSubService.init('server', true, this.allowedOrigins);
      this.qPubSubService.setLogActive(false);

      // Subscribe to a predefined topic to read messages from WebDesktop
      this.qPubSubService.subscribe('QNotifications', (message: QPubSub.Message) => {
        this._snackBar.open(message.msg, 'Close', {
          duration: 3000
        });
      });
      this.qPubSubService.subscribe('QRefreshPage', (message: QPubSub.Message) => {
        const snackBarMatSnackBarRef =  this._snackBar.open(this.refreshPageMessage, this.cancel, {
          duration: 10000,
          verticalPosition: 'top',
          panelClass: 'bg-green'
        });
        snackBarMatSnackBarRef.afterDismissed().subscribe(() => {
          if(!this.timeOutCancel ){
            window.location.reload();
          }else{
            this.timeOutCancel = false;
          }
        });
        snackBarMatSnackBarRef.onAction().subscribe(() => {
          this.timeOutCancel = true;
        });

      });


      this.qPubSubService.subscribe('QDefaultLanguageRequest', () => {
        this.qPubSubService.publish('QDefaultLanguageResponse', this.translate.getDefaultLang());
      });

      if (this.isSsoProfile) {
        // Subscribe to retrieve User Information requests and send the information back
        this.qPubSubService.subscribe('QUserInformationRequest', () => {
          this.qPubSubService.publish('QUserInformationResponse', this.userDetailsDto);
        });
      }
    });
  }

  onCreateMenuColumns(sortedApplications: number) {
    if (sortedApplications > 4) {
      this.columns = 5;
    }
  }

}
