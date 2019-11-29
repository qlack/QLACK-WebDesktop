import {Component, EventEmitter, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Widget} from "../../widget";
import {WidgetService} from "../../widget.service";
import {TranslateService} from '@ngx-translate/core';

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

  @Output() onAppClick = new EventEmitter();

  constructor(private widgetService: WidgetService, private translate: TranslateService) {
  }

  ngOnInit() {
    this.widgetService.getActiveApplications().subscribe(applicationsList => {
      applicationsList.forEach((application, index) => {

        this.translate.get(application.applicationName + '.title').subscribe(
            (titleTranslated: string) => {
              application.applicationTitle = titleTranslated;
            }).add(() => {
          this.translate.get(this.webDesktopUiLexiconGroup + '.' + application.groupName).subscribe(
              (groupTranslated: string) => {
                application.groupTranslated = groupTranslated;
                this.widgets.push(application);
              }).add(() => {

            if (index == (applicationsList.length - 1)) {
              this.widgets = this.widgets.sort((a, b) => (a.groupTranslated.toLowerCase() > b.groupTranslated.toLowerCase()) ? 1 : -1);
              const groups = [...new Set(this.widgets.map(w => w.groupTranslated))];

              groups.forEach(group => {
                this.sortedWidgets.push(this.widgets.filter(w => w.groupTranslated === group));
              });

              this.sortedWidgets.forEach((widthArray, index) => {
                this.sortedWidgets[index] = widthArray.sort((a, b) => {
                  return a.applicationName.toLowerCase().localeCompare(b.applicationName.toLowerCase());
                })
              })
            }
          });
        });
      })
    });
  }
}