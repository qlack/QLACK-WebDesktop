import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {Widget} from "../../widget";
import {WidgetService} from "../../widget.service";

@Component({
  selector: 'app-start-menu',
  templateUrl: './start-menu.component.html',
  styleUrls: ['./start-menu.component.scss']
})
export class StartMenuComponent implements OnInit {

  widgets: Observable<Widget[]>;

  constructor(private widgetService: WidgetService) { }

  ngOnInit() {
    this.widgets = this.widgetService.getActiveApplications();
  }

}
