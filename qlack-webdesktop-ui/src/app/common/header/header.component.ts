import { Component, OnInit } from '@angular/core';
import { Observable } from "rxjs";
import { WidgetService } from "./../../widget.service";
import { Widget } from "./../../widget";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  widgets: Observable<Widget[]>;

  constructor(private widgetService: WidgetService) { }

  ngOnInit() {
    this.widgets = this.widgetService.getActiveApplications();
  }

}
