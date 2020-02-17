import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {ApplicationDto} from '../dto/application-dto';
import {ApplicationsService} from '../services/applications.service';
import 'rxjs/add/operator/debounceTime';
import {BaseComponent} from '../shared/component/base-component';
import {QFormsService} from '@eurodyn/forms';
import {TranslateService} from '@ngx-translate/core';
import {MatDialog} from "@angular/material/dialog";
import {FileuploadComponent} from "../fileupload/fileupload.component";

@Component({
  selector: 'app-applications',
  templateUrl: './applications.component.html',
  styleUrls: ['./applications.component.scss']
})
export class ApplicationsComponent extends BaseComponent implements OnInit, AfterViewInit {
  displayedColumns = ['iconsmall', 'title', 'description', 'version'];
  dataSource: MatTableDataSource<ApplicationDto> = new MatTableDataSource<ApplicationDto>();
  filterForm: FormGroup;
  enabled = [this.constants.USER_STATUS.ENABLED];
  // References to sorting and pagination.
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  private loadFileUpload = false;

  constructor(private fb: FormBuilder, private router: Router, private applicationsService: ApplicationsService,
              private qForms: QFormsService, private translateService: TranslateService, private dialog: MatDialog) {
    super();
    this.filterForm = this.fb.group({
      title: [''],
      description: ['']
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
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit() {
    // Listen for filter changes to fetch new data.
    this.filterForm.valueChanges.debounceTime(500).subscribe(onNext => {
      this.fetchData(this.paginator.pageIndex, this.paginator.pageSize, this.sort.active,
        this.sort.start);
    });
  }

  fetchData(page: number, size: number, sort: string, sortDirection: string) {
    let filterValue = this.filterForm.value;
    // Convert FormGroup to a query string to pass as a filter.
    this.applicationsService.getAll(
      this.qForms.makeQueryString(
        this.fb.group({title: [filterValue.title], description: [filterValue.description]}), null, false, page,
        size, sort, sortDirection))
    .subscribe(onNext => {
      this.dataSource.data = onNext.content;
      this.updateApplicationContent(onNext.content, 'title');
      this.updateApplicationContent(onNext.content, 'description');
      this.paginator.length = onNext.totalElements;
      this.dataSource.sort = this.sort;
    });
  }

  //insert translations to datasource
  updateApplicationContent(content, property) {
    content.forEach((application, index) => {
      this.translateService.get(application.applicationName + '.' + property).subscribe(
        (titleTranslated: string) => {
          application[property] = titleTranslated;
        });
    });
  }

  geStatusQueryString(status) {
    let statusString;
    if (status.length > 1) {
      statusString = status[0];
      for (let index = 1; index < status.length; index++) {
        statusString += '&status=' + status[index];
      }
    } else {
      statusString = status[0];
    }
    return statusString;
  }

  changePage() {
    this.fetchData(this.paginator.pageIndex, this.paginator.pageSize, this.sort.active,
      this.sort.start);
  }

  clearFilter() {
    this.filterForm.reset();
  }

  getFileSrc(profilepic: any) {
    if (!profilepic) {
      return '../assets/img/default.png';
    }
  }

  fileUpload() {
    this.loadFileUpload = true;
    const dialogRef = this.dialog.open(FileuploadComponent, {
      width: '400px'
    });
  }
}
