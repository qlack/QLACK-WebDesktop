import { Component, OnInit } from '@angular/core';
import {DataService} from "../../services/data.service";

@Component({
  selector: 'app-navigation-menu',
  templateUrl: './navigation-menu.component.html',
  styleUrls: ['./navigation-menu.component.scss']
})
export class NavigationMenuComponent implements OnInit {

  navLinks: { path: string, label: string }[] = [
    {path: '/applications', label: 'header'},
    {path: '/users', label: 'users'},
    {path: '/usergroup', label: 'groups'}
  ];

  isVisible:boolean;

  constructor(private data: DataService) { }

  ngOnInit() {
    this.data.currentMessage.subscribe(isVisible => this.isVisible = isVisible);
  }

}
