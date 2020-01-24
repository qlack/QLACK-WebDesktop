import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {MatTableDataSource} from '@angular/material/table';
import {KeyDto} from '../dto/key-dto';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';
import {Router} from '@angular/router';
import {QFormsService} from '@eurodyn/forms';
import 'rxjs/add/operator/debounceTime';
import {TranslationService} from '../services/translation.service';
import {LanguageService} from '../services/language-service';
import {LanguageDto} from '../dto/language-dto';

@Component({
  selector: 'app-translation',
  templateUrl: './translations.component.html',
  styleUrls: ['./translations.component.scss']
})
export class TranslationsComponent implements OnInit, AfterViewInit {

  languages: LanguageDto[] = [];
  displayedColumns = ['groupId', 'name'];
  dataSource: MatTableDataSource<KeyDto> = new MatTableDataSource<KeyDto>();
  filterForm: FormGroup;

  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(private fb: FormBuilder, private router: Router, private translationService: TranslationService,
              private qForms: QFormsService, private languageService: LanguageService) {
    this.filterForm = this.fb.group({
      name: [''],
      translations: ['']
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
    const filterValue = this.filterForm.value;

    // Convert FormGroup to a query string to pass as a filter.
    this.translationService.getAll(
      this.qForms.makeQueryString(
        this.fb.group({name: [filterValue.name], translations: [filterValue.translations]}), null, false, page,
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

  ngOnInit() {
    this.languageService.getLanguages(false).subscribe(languageList => {
      languageList.forEach(language => {
        this.languages.push(language);
        this.displayedColumns.push('translations.' + language.locale);
      });
    });
    this.filterForm.valueChanges.debounceTime(500).subscribe(onNext => {
      this.fetchData(this.paginator.pageIndex, this.paginator.pageSize, this.sort.active,
        this.sort.start);
    });

  }

  setData(element: any, languages: LanguageDto[]) {
    if (element == null) {
      localStorage.setItem('key', JSON.stringify(new KeyDto()));
    } else {
      localStorage.setItem('key', JSON.stringify(element));
    }
    localStorage.setItem('languages', JSON.stringify(languages));
  }
}
