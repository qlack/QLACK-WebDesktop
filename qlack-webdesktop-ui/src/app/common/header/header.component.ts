import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {Widget} from "../../widget";
import {WidgetService} from "../../widget.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {

  widgets: Observable<Widget[]>;
  openApp:boolean;
  //need fixes
  activeApps:string[]=["initValue"];

  constructor(private widgetService: WidgetService) { }

  ngOnInit() {
    this.widgets = this.widgetService.getActiveApplications();
  }

  removeApp(appTitle:string){
    const index = this.activeApps.indexOf(appTitle, 0);
    if (index > -1) {
      this.activeApps.splice(index, 1);
    }
  }
  setApp(appTitle:string){
    this.activeApps.push(appTitle);
  }
}
