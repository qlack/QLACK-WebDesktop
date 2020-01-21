/**
 * A service providing functionality for the user of the application, including authentication,
 * authorisation and session management.
 */
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AppConstants} from '../app.constants';
import {FileDto} from '../dto/file-dto';
import {CrudService} from './crud.service';
import {QFormsService} from '@eurodyn/forms';
import {Observable} from 'rxjs';
import {FormGroup} from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class UserProfileService extends CrudService<FileDto> {
  private resource = `user`;

  constructor(http: HttpClient, qForms: QFormsService) {
    super(http, 'user', qForms);
  }

  public saveDetails(form: FormGroup) {
    return this.qForms.uploadForm(this.http, form, AppConstants.API_ROOT + `/${this.resource}/details/save`, false);
  }

  getAllDetails(id: any): Observable<any> {
    return this.http.get(AppConstants.API_ROOT + '/' + this.resource + `/details/${id}`);
  }
}
