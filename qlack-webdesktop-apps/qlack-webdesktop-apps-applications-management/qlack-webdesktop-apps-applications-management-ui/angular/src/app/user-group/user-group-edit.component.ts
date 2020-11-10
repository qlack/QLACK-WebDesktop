import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ApplicationsService} from "../services/applications.service";
import {ActivatedRoute, Router} from "@angular/router";
import {QFormsService} from "@eurodyn/forms";
import {MatDialog} from "@angular/material/dialog";
import {TranslateService} from "@ngx-translate/core";
import {UtilityService} from "../services/utility.service";
import {QLACKTypescriptFormValidationService} from "@qlack/form-validation";
import {UserGroupService} from "../services/user-group.service";
import {OkCancelModalComponent} from "../shared/component/display/ok-cancel-modal/ok-cancel-modal.component";
import {User} from "../dto/user";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-user-group-edit',
  templateUrl: './user-group-edit.component.html',
  styleUrls: ['./user-group-edit.component.scss']
})
export class UserGroupEditComponent implements OnInit {

  form: FormGroup;
  id: string;
  isEdit = false;
  selector: any;
  usersForm: FormGroup;
  users: User[] = [];
  usersAdded: string[] = [];
  usersRemoved: string[] = [];
  usersInitList: User[] = [];
  displayedColumns: string[] = ['profilepic', 'username', 'firstname', 'lastname', 'action'];
  dataSource: MatTableDataSource<User> = new MatTableDataSource<User>();
  options: any[];
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  private isUsersListChanged = false;

  constructor(private fb: FormBuilder, private applicationsService: ApplicationsService,
              private route: ActivatedRoute,
              private qForms: QFormsService, private router: Router, private dialog: MatDialog,
              private translateService: TranslateService, private utilityService: UtilityService,
              private validationService: QLACKTypescriptFormValidationService, private userGroupService: UserGroupService,
              private userService: UserService) {
    this.usersForm = this.fb.group({
      user: [{value: '', disabled: false}]
    });
  }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
    this.isEdit = (this.id !== '0');
    // Setup the form.
    this.form = this.fb.group({
      id: ['0'],
      userGroupName: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      description: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      usersAdded: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      usersRemoved: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
    });
    // Fill-in the form with data if editing an existing item.
    if (this.isEdit) {
      this.userGroupService.get(this.id).subscribe(onNext => {
        this.form.patchValue(onNext);
        this.form.controls['userGroupName'].setValue(onNext.name);
      });
    }

    this.usersForm.valueChanges.subscribe(term => {
      if (term.user && term != '' && term.user.length > 1) {
        this.userGroupService.search(term.user, "users").subscribe(
          data => {
            this.options = data;
          });
      }
    });

    this.dataSource._updateChangeSubscription();
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
    let filterValue = this.usersForm.value;
    // Convert FormGroup to a query string to pass as a filter.
    this.userGroupService.getUsersByGroupId(
      this.qForms.makeQueryString(
        this.fb.group({username: [filterValue.username]}), null, false, page,
        size, sort, sortDirection), this.id)
    .subscribe(onNext => {
      this.dataSource.data = onNext.content;
      this.users = onNext.content;
      this.usersInitList = this.users.slice();
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
    this.form.controls['usersAdded'].setValue(this.usersAdded);
    this.form.controls['usersRemoved'].setValue(this.usersRemoved);
    this.userGroupService.save(this.qForms.cleanupForm(this.form)).subscribe(
      (response) => {
        this.utilityService.popupSuccessAction(
          this.createOrUpdateMessage(),
          this.getMessageTranslations('management-app-ui.dismiss'));
        this.router.navigate(["/usergroup"]);
      }, error => {

        if (error.status == 400) {
          let validationErrors = error.error;
          //inserts proper translations
          validationErrors.forEach(validationError => {
            validationError.code = 'management-app-ui.' + validationError.code;
          });
          if (error.error) {
            this.validationService.validateForm(this.form, validationErrors, this.translateService);
          } else {
            this.utilityService.popupError(this.getMessageTranslations('management-app-ui.error'));
          }
        } else {
          if (error.error === "alreadyExistsCode") {
            this.utilityService.popupError(
              this.getMessageTranslations('management-app-ui.the') + " " + this.getMessageTranslations(
              'management-app-ui.title') +
              " " + this.getMessageTranslations('management-app-ui.alreadyExistsCode'));
          } else {
            this.utilityService.popupError(error.error);
          }
        }
      });
  }

  delete() {
    this.dialog.open(OkCancelModalComponent, {
      data: {
        title: this.getMessageTranslations('management-app-ui.deleteGroup'),
        question: this.getMessageTranslations('management-app-ui.deleteQuestion'),
        buttons: {
          ok: true, cancel: true, reload: false
        }
      }
    }).afterClosed().subscribe(result => {
      if (result) {
        this.userGroupService.delete(this.id).subscribe(onNext => {
          this.router.navigate(["/usergroup"]);
          this.utilityService.popupSuccessAction(
            this.getMessageTranslations('management-app-ui.deleteSuccess'),
            this.getMessageTranslations('management-app-ui.dismiss'));
        });
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

  addUser() {
    let filterValue = this.usersForm.value;
    this.userService.findUserByName(filterValue.user).subscribe(
      (user) => {
        if (!user) {
          this.utilityService.popupError(this.getMessageTranslations('management-app-ui.notFound'));
        } else if (this.itemExists(this.users, user.id)) {
          this.utilityService.popupError(this.getMessageTranslations('management-app-ui.alreadyExistsCode'));
        } else {
          this.users.push(user);
          //filtering, add user only if user has never been removed and added again.
          if (!this.itemExists(this.usersInitList, user.id)) {
            this.usersAdded.push(user.id);
          }
          //filtering, if user has been removed and then added again remove him from usersRemoved.
          this.usersRemoved = this.filterList(this.usersRemoved, user.id);
          this.isUsersListChanged = true;
          this.dataSource.data = this.users;
        }
        //cleans up input text
        this.usersForm.controls['user'].reset();
        this.options = [];
      });
  }

  filterList(list: string[], data: string): string[] {
    return list.filter(value => value != data);
  }

  //check if user exists
  itemExists(list, userId): any {
    return list.find(item => item.id === userId);
  }

  removeUser(row_obj) {
    this.users = this.users.filter((value, key) => {
      return value.id != row_obj.id;
    });
    this.dataSource.data = this.users;
    this.isUsersListChanged = true;
    //filtering, if user has been added and removed, remove him from usersAdded.
    this.usersAdded = this.filterList(this.usersAdded, row_obj.id);
    //filtering, add user only if user has never been removed and added again.
    if (this.itemExists(this.usersInitList, row_obj.id)) {
      this.usersRemoved.push(row_obj.id);
    }
  }

  changePage() {
    this.fetchData(this.paginator.pageIndex, this.paginator.pageSize, this.sort.active,
      this.sort.start);
  }
}
