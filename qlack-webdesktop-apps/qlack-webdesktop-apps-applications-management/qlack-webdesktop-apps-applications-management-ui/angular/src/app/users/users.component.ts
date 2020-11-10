import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {QFormsService} from '@eurodyn/forms';
import {UserService} from '../services/user.service';
import 'rxjs/add/operator/debounceTime';
import {BaseComponent} from '../shared/component/base-component';
import {FileService} from '../services/file.service';
import {User} from "../dto/user";
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent extends BaseComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['profilepic', 'username', 'firstname', 'lastname'];
  users: User[];
  filterForm: FormGroup;
  // References to sorting and pagination.
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(private fb: FormBuilder, private router: Router, private userService: UserService,
              private qForms: QFormsService, private fileService: FileService, private sanitizer: DomSanitizer) {
    super();
    this.filterForm = this.fb.group({
      username: ['']
    });
  }

  ngAfterViewInit(): void {
    // Initial fetch of data.
    this.fetchData(0, this.paginator.pageSize, this.sort.active, this.sort.start);

    // Each time the sorting changes, reset the page number.
    this.sort.sortChange.subscribe(data => {
      this.paginator.pageIndex = 0;
      this.fetchData(0, this.paginator.pageSize, data.active, data.direction);
    });
  }

  ngOnInit() {
    // Listen for filter changes to fetch new data with %like% operator.
    this.filterForm.valueChanges.debounceTime(500).subscribe(term => {
      if (term.username && term != '' && term.username.length > 0) {
        this.userService.searchUsers(term.username).subscribe(
          data => {
            this.users = data.content;
            this.paginator.length = data.totalElements;
          });
      } else {
        this.fetchData(this.paginator.pageIndex, this.paginator.pageSize, this.sort.active,
          this.sort.start);
      }
    });
  }

  fetchData(page: number, size: number, sort: string, sortDirection: string) {
    let filterValue = this.filterForm.value;
    // Convert FormGroup to a query string to pass as a filter.
    this.userService.getAllUsers(
      this.qForms.makeQueryString(
        this.fb.group({username: [filterValue.username]}), null, false, page,
        size, sort, sortDirection))
    .subscribe(data => {
      this.users = data.content;
      this.paginator.length = data.totalElements;
    });
  }

  changePage() {
    this.fetchData(this.paginator.pageIndex, this.paginator.pageSize, this.sort.active,
      this.sort.start);
  }

  clearFilter() {
    this.filterForm.reset();
  }

  getFileSrc(profilepic: any) {
    if (profilepic) {
      return this.fileService.getImage(profilepic.id);
    } else {
      return '../assets/img/default.png';
    }
  }
}
