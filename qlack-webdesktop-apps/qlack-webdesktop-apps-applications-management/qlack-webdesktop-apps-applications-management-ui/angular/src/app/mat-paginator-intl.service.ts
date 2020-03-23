import {Injectable} from '@angular/core';
import {MatPaginatorIntl} from "@angular/material/paginator";
import {TranslateService} from "@ngx-translate/core";

@Injectable({
  providedIn: 'root'
})
export class MatPaginatorIntlService extends MatPaginatorIntl {

  private rangeLabelIntl: string;


  constructor(private translateService: TranslateService) {
    super();
    this.getTranslations();
    this.translateService.onLangChange.subscribe(() => this.getTranslations());
  }


  getTranslations() {
    this.translateService.get([
      'management-app-ui.itemsPerPage',
      'management-app-ui.rangeLabel',
      'management-app-ui.firstPage',
      'management-app-ui.lastPage',
      'management-app-ui.previousPage',
      'management-app-ui.nextPage'

    ])
    .subscribe(translation => {
      this.itemsPerPageLabel = translation['management-app-ui.itemsPerPage'];
      this.rangeLabelIntl = translation['management-app-ui.rangeLabel'];
      this.firstPageLabel = translation['management-app-ui.firstPage'];
      this.lastPageLabel = translation['management-app-ui.lastPage'];
      this.previousPageLabel = translation['management-app-ui.previousPage'];
      this.nextPageLabel = translation['management-app-ui.nextPage'];
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
