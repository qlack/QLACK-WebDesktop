import {Component, EventEmitter, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Widget} from "../../widget";
import {WidgetService} from "../../widget.service";
import {TranslateService} from '@ngx-translate/core';
import {QNgPubSubService} from '@qlack/qng-pub-sub';

@Component({
  selector: 'app-start-menu',
  templateUrl: './start-menu.component.html',
  styleUrls: ['./start-menu.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class StartMenuComponent implements OnInit {

  widgets: Widget[] = [];
  webDesktopUiLexiconGroup: string = 'webdesktop-ui';
  sortedWidgets = new Array<Widget[]>();
  columns = 4;
  private allowedOrigins: string[] = [];

  @Output() onAppClick = new EventEmitter();

  constructor(private widgetService: WidgetService, private translate: TranslateService, private qPubSubService: QNgPubSubService) {
  }

  ngOnInit() {
    this.widgetService.getActiveApplications().subscribe(applicationsList => {

      applicationsList.forEach((application, index) => {

        // Add application url to the list of allowed origins for QPubSub
        this.allowedOrigins.push(application.appIndex);

        this.translate.get(application.applicationName + '.title').subscribe(
            (titleTranslated: string) => {
              application.applicationTitle = titleTranslated;
            }).add(() => {
          this.translate.get(this.webDesktopUiLexiconGroup + '.' + application.groupName).subscribe(
              (groupTranslated: string) => {
                if (groupTranslated == "webdesktop-ui."){
                  application.groupTranslated = "";
                } else {
                  application.groupTranslated = groupTranslated;
                }
                this.widgets.push(application);
              }).add(() => {

            if (index == (applicationsList.length - 1)) {
              this.widgets = this.widgets.sort((a, b) => !a.groupTranslated ? 1 : (!b.groupTranslated ? -1 : (a.groupTranslated.toLowerCase().localeCompare(b.groupTranslated.toLowerCase()))));
              const groups = [...new Set(this.widgets.map(w => w.groupTranslated))];

              groups.forEach(group => {
                this.sortedWidgets.push(this.widgets.filter(w => w.groupTranslated === group));
              });
              this.sortedWidgets.forEach((widthArray, index) => {
                this.onCreateMenuColumns(widthArray.length);
                this.sortedWidgets[index] = widthArray.sort((a, b) => {
                  return a.applicationName.toLowerCase().localeCompare(b.applicationName.toLowerCase());
                })
              })
            }
          });
        });
      })

      // Add current host to QPubSub allowed origins
      this.allowedOrigins.push(window.location.origin);
      // Initialize QPubSub
      this.qPubSubService.init('server', true, this.allowedOrigins);
      this.qPubSubService.setLogActive(false);
    });
  }

  onCreateMenuColumns(sortedWidgets : number) {
    if (sortedWidgets > 4) {
      this.columns = 5;
    }
  }
}