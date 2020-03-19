import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {UserDto} from "../../dto/user-dto";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {FormBuilder, FormGroup} from "@angular/forms";
import {UserGroupService} from "../../services/user-group.service";
import {QFormsService, QPageableReply} from "@eurodyn/forms";
import {UtilityService} from "../../services/utility.service";
import {UserService} from "../../services/user.service";
import {TranslateService} from "@ngx-translate/core";
import {ActivatedRoute} from "@angular/router";
import {ApplicationsService} from "../../services/applications.service";

@Component({
  selector: 'app-permissions',
  templateUrl: './permissions.component.html',
  styleUrls: ['./permissions.component.scss']
})
export class PermissionsComponent implements OnInit {

  id: string;
  form: FormGroup;
  options: any[];
  usersForm: FormGroup;
  usersAdded: string[] = [];
  usersRemoved: string[] = [];
  usersInitList: UserDto[] = [];
  groupForm: FormGroup;
  groupsAdded: string[] = [];
  groupsRemoved: string[] = [];
  groupsInitList: any[];
  displayedColumns: string[] = ['name', 'description', 'action'];
  dataSource: MatTableDataSource<UserDto> = new MatTableDataSource<UserDto>();
  optionsUserGroup: any[];
  dataSourceUserGroup: MatTableDataSource<UserDto> = new MatTableDataSource<UserDto>();
  displayedColumnsUsergroup: string[] = ['profilepic', 'username', 'lastname', 'action'];
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  private isUsersListChanged = false;
  private isGroupListChanged = false;
  private usersStorage: any;
  private userGroupsStorage: any;

  constructor(private fb: FormBuilder, private userGroupService: UserGroupService,
              private qForms: QFormsService, private utilityService: UtilityService,
              private userService: UserService, private translateService: TranslateService,
              private route: ActivatedRoute, private applicationsService: ApplicationsService) {
    this.usersForm = this.fb.group({
      user: [{value: '', disabled: false}]
    });
    this.groupForm = this.fb.group({
      name: [{value: '', disabled: false}]
    });
  }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');

    this.usersForm.valueChanges.debounceTime(500).subscribe(term => {
      if (term.user && term != '' && term.user.length > 1) {
        this.userGroupService.search(term.user, "users").subscribe(
          data => {
            this.options = data;
          });
      }
    });

    this.groupForm.valueChanges.debounceTime(500).subscribe(term => {
      if (term.name && term != '' && term.name.length > 1) {
        this.userService.search(term.name, "usergroup").subscribe(
          data => {
            this.optionsUserGroup = data;
          });
      }
    });
  }

  ngAfterViewInit(): void {
    // Initial fetch of data.
    setTimeout(() => {
      this.fetchData(0, this.paginator.pageSize, this.sort.active, this.sort.start);
    });

    // Each time the sorting changes, reset the page number.
    this.sort.sortChange.subscribe(onNext => {
      this.paginator.pageIndex = 0;
      this.fetchData(0, this.paginator.pageSize, onNext.active, onNext.direction);
    });
  }

  fetchData(page: number, size: number, sort: string, sortDirection: string) {
    try {
      this.usersStorage = JSON.parse(sessionStorage.getItem('users'));
      this.userGroupsStorage = JSON.parse(sessionStorage.getItem('userGroups'));
    } catch (e) {
      console.log(e)
    }

    if (!this.usersStorage) {
      this.usersStorage = new QPageableReply();
      this.usersStorage.content = [];
    }
    if (!this.userGroupsStorage) {
      this.userGroupsStorage = new QPageableReply();
    }

    if (this.usersStorage.content) {
      this.dataSource.data = this.usersStorage.content;
      this.usersInitList = this.usersStorage.content.slice();
      this.paginator.length = this.usersStorage.content.totalElements;
      this.dataSource.sort = this.sort;
    }

    if (this.userGroupsStorage.content) {
      this.dataSourceUserGroup.data = this.userGroupsStorage.content;
      this.groupsInitList = this.userGroupsStorage.content.slice();
      this.paginator.length = this.userGroupsStorage.totalElements;
      this.dataSourceUserGroup.sort = this.sort;
    }
  }

  removeUser(row_obj) {
    this.usersStorage.content = this.usersStorage.content.filter((value, key) => {
      return value.id != row_obj.id;
    });
    sessionStorage.setItem('users', JSON.stringify(this.usersStorage));
    this.dataSource.data = this.usersStorage.content;
    this.isUsersListChanged = true;
    //filtering, if user has been added and removed, remove him from usersAdded.
    this.usersAdded = this.filterList(this.usersAdded, row_obj.id);
    sessionStorage.setItem('usersAdded', JSON.stringify(this.usersAdded));
    //filtering, add user only if user has never been removed and added again.
    if (this.itemExists(this.usersInitList, row_obj.id, "id")) {
      this.usersRemoved.push(row_obj.id);
      sessionStorage.setItem('usersRemoved', JSON.stringify(this.usersRemoved));
    }
  }

  removeGroup(row_obj) {
    this.userGroupsStorage.content = this.userGroupsStorage.content.filter((value, key) => {
      return value.id != row_obj.id;
    });
    sessionStorage.setItem('userGroups', JSON.stringify(this.userGroupsStorage));
    this.dataSourceUserGroup.data = this.userGroupsStorage.content;
    this.isGroupListChanged = true;
    //filtering, if user has been added and removed, remove him from usersAdded.
    this.groupsAdded = this.filterList(this.groupsAdded, row_obj.id);
    sessionStorage.setItem('groupsAdded', JSON.stringify(this.groupsAdded));
    //filtering, add user only if user has never been removed and added again.
    if (this.itemExists(this.groupsInitList, row_obj.id, "id")) {
      this.groupsRemoved.push(row_obj.id);
      sessionStorage.setItem('groupsRemoved', JSON.stringify(this.groupsRemoved));
    }
  }

  filterList(list: string[], data: string): string[] {
    return list.filter(value => value != data);
  }

  //check if user exists
  itemExists(list, groupId, prop): any {
    return list.find(item => {
      if (item[prop]) {
        return item[prop] === groupId;
      }
    });
  }

  changePage() {
    this.fetchData(this.paginator.pageIndex, this.paginator.pageSize, this.sort.active,
      this.sort.start);
  }

  addUser() {
    let filterValue = this.usersForm.value;
    this.userService.findUserByName(filterValue.user).subscribe((user) => {
      if (!user) {
        this.utilityService.popupError(this.getMessageTranslations('management-app-ui.notFound'));
      } else if (this.usersStorage.content && this.itemExists(this.usersStorage.content, user.id, "id")) {
        this.utilityService.popupError(this.getMessageTranslations('management-app-ui.alreadyExistsCode'));
      } else {
        this.usersStorage.content.push(user);
        sessionStorage.setItem('users', JSON.stringify(this.usersStorage));
        //filtering, add user only if user has never been removed and added again.
        if (!this.itemExists(this.usersInitList, user.id, "id")) {
          this.usersAdded.push(user.id);
          sessionStorage.setItem('usersAdded', JSON.stringify(this.usersAdded));
        }
        //filtering, if user has been removed and then added again remove him from usersRemoved.
        this.usersRemoved = this.filterList(this.usersRemoved, user.id);
        sessionStorage.setItem('usersRemoved', JSON.stringify(this.usersRemoved));
        this.isUsersListChanged = true;
        this.dataSource.data = this.usersStorage.content;
      }
      //cleans up input text
      this.usersForm.controls['user'].reset();
      this.options = [];
    });
  }

  addGroup() {
    let filterValue = this.groupForm.value;
    this.userService.findGroupByName(filterValue.name).subscribe(group => {
      if (!group) {
        this.utilityService.popupError(this.getMessageTranslations('management-app-ui.notFound'));
      } else if (this.userGroupsStorage && this.itemExists(this.userGroupsStorage.content, filterValue.name, "name")) {
        this.utilityService.popupError(this.getMessageTranslations('management-app-ui.alreadyExistsCode'));
      } else {
        this.userGroupsStorage.content.push(group);
        sessionStorage.setItem('userGroups', JSON.stringify(this.userGroupsStorage));
        //filtering, add user only if user has never been removed and added again.
        if (!this.itemExists(this.groupsInitList, filterValue.name, "name")) {
          this.groupsAdded.push(group.id);
          sessionStorage.setItem('groupsAdded', JSON.stringify(this.groupsAdded));
        }
        //filtering, if user has been removed and then added again remove him from usersRemoved.
        this.groupsRemoved = this.filterList(this.groupsRemoved, group.id);
        sessionStorage.setItem('groupsRemoved', JSON.stringify(this.groupsRemoved));
        this.isGroupListChanged = true;
        this.dataSourceUserGroup.data = this.userGroupsStorage.content;
      }
      //cleans up input text
      this.groupForm.controls['name'].reset();
      this.optionsUserGroup = [];
    });
  }

  //translate snack bar messages
  getMessageTranslations(message: string) {
    let translatedMessage = null;
    this.translateService.get(message).subscribe((translation: string) => {
      translatedMessage = translation;
    });
    return translatedMessage;
  }

}
