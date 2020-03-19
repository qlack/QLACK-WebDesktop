import {Injectable} from '@angular/core';
import {CrudService} from './crud.service';
import {HttpClient} from '@angular/common/http';
import {QFormsService} from '@eurodyn/forms';
import {AppConstants} from '../app.constants';
import {Observable} from 'rxjs';
import {LanguageDto} from '../dto/language-dto';
import {FormGroup} from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class LanguageService extends CrudService<LanguageDto> {
  private resource = `languages`;

  constructor(http: HttpClient, qForms: QFormsService) {
    super(http, 'languages', qForms);
  }

  getLanguages(includeInactive: boolean): Observable<any> {

    return this.http.get(`${this.contextPath}` + `${AppConstants.API_ROOT}/${this.resource}/${includeInactive}`);
  }

  updateLanguages(languages: Object[]): Observable<any> {
    return this.http.post(`${this.contextPath}` + `${AppConstants.API_ROOT}/${this.resource}/update`,
      JSON.stringify(languages),
      {headers: {'Content-Type': 'application/json'}});
  }

  createLanguage(form: FormGroup): Observable<any> {
    return this.qForms.uploadForm(this.http, form,
      `${this.contextPath}` + AppConstants.API_ROOT + `/${this.resource}/create`, false);
  }
}
