import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AppConstants} from '../app.constants';
import {UserDto} from '../dto/user-dto';
import {CrudService} from './crud.service';
import {QFormsService, QPageableReply} from '@eurodyn/forms';
import {FormGroup} from '@angular/forms';

/**
 * A service providing functionality for the user of the application, including authentication,
 * authorisation and session management.
 */
@Injectable({
  providedIn: 'root'
})
export class UserService extends CrudService<UserDto> {
  private resource = `users`;

  constructor(http: HttpClient, qForms: QFormsService) {
    super(http, 'users', qForms);
  }

  // Save user
  save(user: UserDto) {
    return this.http.post(`${this.contextPath}` + AppConstants.API_ROOT + `/${this.resource}`, JSON.stringify(user),
      {headers: {'Content-Type': 'application/json'}});
  }

  upload(form: FormGroup) {
    return this.qForms.uploadForm(this.http, form,
      `${this.contextPath}` + `${AppConstants.API_ROOT}/${this.resource}/upload`, false);
  }

  findUserByName(name: string): Observable<any> {
    return this.http.get(`${this.contextPath}` + `${AppConstants.API_ROOT}/${this.endpoint}/username/${name}`);
  }

  findGroupByName(name: string): Observable<any> {
    return this.http.get(`${this.contextPath}` + `${AppConstants.API_ROOT}/${this.endpoint}/groupname/${name}`);
  }

  getGroupsByUserId(queryString?: string, userId?: string): Observable<any> {
    return this.http.get<QPageableReply<any>>(`${this.contextPath}` + `${AppConstants.API_ROOT}/${this.endpoint}/groups/` + userId + `?${queryString}`);
  }
}
