import {MatPaginatorIntl} from '@angular/material';
import {TranslateService} from '@ngx-translate/core';

export class MyMatPaginatorIntl extends MatPaginatorIntl {

  private rangeLabelIntl: string;


  constructor(private translateService: TranslateService) {
    super();
    this.getTranslations();
    this.translateService.onLangChange.subscribe(() => this.getTranslations());
  }


  getTranslations() {
    this.translateService.get([
      'translations-management-ui.itemsPerPage',
      'translations-management-ui.rangeLabel',
      'translations-management-ui.firstPage',
      'translations-management-ui.lastPage',
      'translations-management-ui.previousPage',
      'translations-management-ui.nextPage'

    ])
    .subscribe(translation => {
      this.itemsPerPageLabel = translation['translations-management-ui.itemsPerPage'];
      this.rangeLabelIntl = translation['translations-management-ui.rangeLabel'];
      this.firstPageLabel = translation['translations-management-ui.firstPage'];
      this.lastPageLabel = translation['translations-management-ui.lastPage'];
      this.previousPageLabel = translation['translations-management-ui.previousPage'];
      this.nextPageLabel = translation['translations-management-ui.nextPage'];
      this.changes.next();
    });
  }


  getRangeLabel = (page, pageSize, length) => {
    length = Math.max(length, 0);
    const startIndex = page * pageSize;
    const endIndex = startIndex < length ?
      Math.min(startIndex + pageSize, length) :
      startIndex + pageSize;
    return `${startIndex + 1} - ${endIndex} ` + this.rangeLabelIntl + ` ${length}`;
  }
}

