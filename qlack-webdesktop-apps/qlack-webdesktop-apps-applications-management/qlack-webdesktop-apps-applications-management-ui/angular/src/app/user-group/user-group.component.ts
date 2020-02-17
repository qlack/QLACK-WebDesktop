import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {QFormsService} from '@eurodyn/forms';
import {UserService} from '../services/user.service';
import 'rxjs/add/operator/debounceTime';
import {BaseComponent} from '../shared/component/base-component';
import {FileService} from '../services/file.service';
import {UserGroupService} from "../services/user-group.service";
import {UserGroupDto} from "../dto/user-group-dto";

@Component({
  selector: 'app-user-group',
  templateUrl: './user-group.component.html',
  styleUrls: ['./user-group.component.scss']
})
export class UserGroupComponent extends BaseComponent implements OnInit, AfterViewInit {
  displayedColumns = ['name', 'description'];
  dataSource: MatTableDataSource<UserGroupDto> = new MatTableDataSource<UserGroupDto>();
  filterForm: FormGroup;
  // References to sorting and pagination.
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(private fb: FormBuilder, private router: Router, private userService: UserService,
              private userGroupService: UserGroupService,
              private qForms: QFormsService, private fileService: FileService) {
    super();
    this.filterForm = this.fb.group({
      name: ['']
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

  ngOnInit() {
    // Listen for filter changes to fetch new data.
    this.filterForm.valueChanges.debounceTime(500).subscribe(term => {
      if (term.name && term != '' && term.name.length > 0) {
        this.userGroupService.search(term.name, "usergroup").subscribe(
          data => {
            this.dataSource.data = data;
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
    this.userGroupService.getAll(
      this.qForms.makeQueryString(
        this.fb.group({name: [filterValue.name]}), null, false, page,
        size, sort, sortDirection))
    .subscribe(onNext => {
      this.dataSource.data = onNext.content;
      this.paginator.length = onNext.totalElements;
    });
  }

  changePage() {
    this.fetchData(this.paginator.pageIndex, this.paginator.pageSize, this.sort.active,
      this.sort.start);
  }

  clearFilter() {
    this.filterForm.reset();
  }
}
