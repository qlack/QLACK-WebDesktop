import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {User} from "../dto/user";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {ApplicationsService} from "../services/applications.service";
import {ActivatedRoute, Router} from "@angular/router";
import {QFormsService} from "@eurodyn/forms";
import {MatDialog} from "@angular/material/dialog";
import {TranslateService} from "@ngx-translate/core";
import {UtilityService} from "../services/utility.service";
import {QLACKTypescriptFormValidationService} from "@qlack/form-validation";
import {UserGroupService} from "../services/user-group.service";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-users-edit',
  templateUrl: './users-edit.component.html',
  styleUrls: ['./users-edit.component.scss']
})
export class UsersEditComponent implements OnInit {

  form: FormGroup;
  id: string;
  isEdit = false;
  selector: any;
  groupForm: FormGroup;
  groups: any[];
  groupsAdded: string[] = [];
  groupsRemoved: string[] = [];
  groupsInitList: any[];
  displayedColumns: string[] = ['name', 'description', 'action'];
  dataSource: MatTableDataSource<User> = new MatTableDataSource<User>();
  options: any[];
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  private isGroupListChanged = false;

  constructor(private fb: FormBuilder, private applicationsService: ApplicationsService,
              private route: ActivatedRoute,
              private qForms: QFormsService, private router: Router, private dialog: MatDialog,
              private translateService: TranslateService, private utilityService: UtilityService,
              private validationService: QLACKTypescriptFormValidationService, private userGroupService: UserGroupService,
              private userService: UserService) {
    this.groupForm = this.fb.group({
      name: [{value: '', disabled: false}]
    });
  }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
    this.isEdit = (this.id !== '0');
    // Setup the form.
    this.form = this.fb.group({
      id: ['0'],
      username: [{value: '', disabled: true}, [Validators.maxLength(1024)]],
      firstname: [{value: '', disabled: true}, [Validators.maxLength(1024)]],
      lastname: [{value: '', disabled: true}, [Validators.maxLength(1024)]],
      roles: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      groupsAdded: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      groupsRemoved: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
    });
    // Fill-in the form with data if editing an existing item.
    if (this.isEdit) {
      this.userService.get(this.id).subscribe(onNext => {
        this.form.patchValue(onNext);
      });
    }

    this.groupForm.valueChanges.subscribe(term => {
      if (term.name && term != '' && term.name.length > 1) {
        this.userService.search(term.name, "usergroup").subscribe(
          data => {
            this.options = data;
          });
      }
    });
  }

  ngAfterViewInit(): void {
    // Initial fetch of data.
    this.fetchData(0, this.paginator.pageSize, this.sort.active, this.sort.start);

    // Each time the sorting changes, reset the page number.
    this.sort.sortChange.subscribe(onNext => {
      this.paginator.pageIndex = 0;
      this.fetchData(0, this.paginator.pageSize, onNext.active, onNext.direction);
    });
  }

  fetchData(page: number, size: number, sort: string, sortDirection: string) {
    let filterValue = this.groupForm.value;
    // Convert FormGroup to a query string to pass as a filter.
    this.userService.getGroupsByUserId(
      this.qForms.makeQueryString(
        this.fb.group({group: [filterValue.name]}), null, false, page,
        size, sort, sortDirection), this.id)
    .subscribe(onNext => {
      this.dataSource.data = onNext.content;
      this.groups = onNext.content;
      this.groupsInitList = this.groups.slice();
      this.paginator.length = onNext.totalElements;
      this.dataSource.sort = this.sort;
    });
  }

  createOrUpdateMessage(): string {
    return this.isEdit ?
      this.getMessageTranslations('management-app-ui.updated') + "!"
      : this.getMessageTranslations('management-app-ui.created') + "!";
  }

  save() {
    this.form.controls['groupsAdded'].setValue(this.groupsAdded);
    this.form.controls['groupsRemoved'].setValue(this.groupsRemoved);
    this.userService.save(this.qForms.cleanupForm(this.form)).subscribe(
      (response) => {
        this.utilityService.popupSuccessAction(
          this.createOrUpdateMessage(),
          this.getMessageTranslations('management-app-ui.dismiss'));
        this.router.navigate(["/users"]);
      }, error => {

        if (error.status == 400) {
          this.utilityService.popupError(this.getMessageTranslations('management-app-ui.error'));
        }
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

  addGroup() {
    let filterValue = this.groupForm.value;
    if (this.itemExists(this.groups, filterValue.name, "name")) {
      this.utilityService.popupError(this.getMessageTranslations('management-app-ui.alreadyExistsCode'));
    } else {
      this.userService.findGroupByName(filterValue.name).subscribe(group => {
        if (!group) {
          this.utilityService.popupError(this.getMessageTranslations('management-app-ui.notFound'));
        } else {
          this.groups.push(group);
          //filtering, add user only if user has never been removed and added again.
          if (!this.itemExists(this.groupsInitList, filterValue.name, "name")) {
            this.groupsAdded.push(group.id);
          }
          //filtering, if user has been removed and then added again remove him from usersRemoved.
          this.groupsRemoved = this.filterList(this.groupsRemoved, group.id);
          this.isGroupListChanged = true;
          this.dataSource.data = this.groups;
        }
      });
    }
    //cleans up input text
    this.groupForm.controls['name'].reset();
    this.options = [];
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

  removeGroup(row_obj) {
    this.groups = this.groups.filter((value, key) => {
      return value.id != row_obj.id;
    });
    this.dataSource.data = this.groups;
    this.isGroupListChanged = true;
    //filtering, if user has been added and removed, remove him from usersAdded.
    this.groupsAdded = this.filterList(this.groupsAdded, row_obj.id);
    //filtering, add user only if user has never been removed and added again.
    if (this.itemExists(this.groupsInitList, row_obj.id, "id")) {
      this.groupsRemoved.push(row_obj.id);
    }
  }

  changePage() {
    this.fetchData(this.paginator.pageIndex, this.paginator.pageSize, this.sort.active,
      this.sort.start);
  }
}
