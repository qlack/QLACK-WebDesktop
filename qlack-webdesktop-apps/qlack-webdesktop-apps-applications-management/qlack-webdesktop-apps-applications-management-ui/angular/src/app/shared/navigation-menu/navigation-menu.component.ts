import {Component, OnInit} from '@angular/core';
import {DataService} from "../../services/data.service";
import {ApplicationsService} from "../../services/applications.service";

@Component({
  selector: 'app-navigation-menu',
  templateUrl: './navigation-menu.component.html'
})
export class NavigationMenuComponent implements OnInit {

  navLinks: { path: string, label: string }[] = [
    {path: '/applications', label: 'header'},
    {path: '/users', label: 'users'},
    {path: '/usergroup', label: 'groups'}
  ];

  isVisible: boolean;
  isSsoActive: boolean;

  constructor(private data: DataService, private applicationService: ApplicationsService) {
  }

  ngOnInit() {
    this.data.currentMessage.subscribe(isVisible => this.isVisible = isVisible);

    this.applicationService.isSsoEnabled().subscribe(isSso => this.isSsoActive = isSso)
  }

}
