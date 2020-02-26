import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {CrudService} from './crud.service';
import {QFormsService} from '@eurodyn/forms';
import {KeyDto} from '../dto/key-dto';
import {AppConstants} from '../app.constants';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class TranslationService extends CrudService<KeyDto> {
  private resource = `key`;

  constructor(http: HttpClient, qForms: QFormsService) {
    super(http, 'key', qForms);
  }

  updateTranslationsForKey(key: KeyDto) {
    return this.http.post(`${AppConstants.API_ROOT}/${this.resource}/update`, key,
      {headers: {'Content-Type': 'application/json'}});
  }
  getUserAttributeByName(attributeName: string): Observable<any> {
    return this.http.get(AppConstants.API_ROOT + '/user/attributes/'+ attributeName);
  }
}
