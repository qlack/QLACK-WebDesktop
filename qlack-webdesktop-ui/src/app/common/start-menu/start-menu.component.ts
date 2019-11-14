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

  @Output() onAppClick = new EventEmitter();

  constructor(private widgetService: WidgetService, private translate: TranslateService) {
  }

  ngOnInit() {
    this.widgetService.getActiveApplications().subscribe(applicationsList => {
      applicationsList.forEach(application => {
        this.translate.get(application.applicationName + '.title').subscribe((titleTranslated: string) => {
          application.applicationTitle = titleTranslated;
          this.widgets.push(application);
        });
      })
    });
  }
}
