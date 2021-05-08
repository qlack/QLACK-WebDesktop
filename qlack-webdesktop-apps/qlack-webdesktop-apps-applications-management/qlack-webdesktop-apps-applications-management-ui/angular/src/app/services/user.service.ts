import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AppConstants} from '../app.constants';
import {User} from '../dto/user';
import {CrudService} from './crud.service';
import {QFormsService, QPageableReply} from '@eurodyn/forms';
import {FormGroup} from '@angular/forms';
import {UserList} from '../dto/user-list-dto';
import {map} from 'rxjs/operators';

/**
 * A service providing functionality for the user of the application, including authentication,
 * authorisation and session management.
 */
@Injectable({
  providedIn: 'root'
})
export class UserService extends CrudService<User> {
  private resource = `users`;

  constructor(http: HttpClient, qForms: QFormsService) {
    super(http, 'users', qForms);
  }

  // Save user
  save(user: User) {
    return this.http.post(`${this.contextPath}` + AppConstants.API_ROOT + `/${this.resource}`, JSON.stringify(user),
      {headers: {'Content-Type': 'application/json'}});
  }

  upload(form: FormGroup) {
    return this.qForms.uploadForm(this.http, form,
      `${this.contextPath}` + `${AppConstants.API_ROOT}/${this.resource}/upload`, false);
  }

  findUserByName(name: string): Observable<User> {
    return this.http.get(`${this.contextPath}` + `${AppConstants.API_ROOT}/${this.endpoint}/username/${name}`).pipe(
      map(res => new User().deserialize(res)));
  }

  findGroupByName(name: string): Observable<any> {
    return this.http.get(`${this.contextPath}` + `${AppConstants.API_ROOT}/${this.endpoint}/groupname/${name}`);
  }

  getById(id: string): Observable<User> {
    return this.http.get<User>(`${this.contextPath}` + `${AppConstants.API_ROOT}/users/` + id)
    .pipe(
      map(res => new User().deserialize(res)));
  }

  getGroupsByUserId(queryString?: string, userId?: string): Observable<any> {
    return this.http.get<QPageableReply<any>>(
      `${this.contextPath}` + `${AppConstants.API_ROOT}/${this.endpoint}/groups/` + userId + `?${queryString}`);
  }

  getAllUsers(queryString?: string): Observable<UserList> {
    if (queryString) {
      return this.http.get<UserList>(
        `${this.contextPath}` + `${AppConstants.API_ROOT}/${this.endpoint}` + `?${queryString}`).pipe(
        map(res => new UserList().deserialize(res))
      );
    } else {
      return this.http.get<UserList>(`${this.contextPath}` + `/${this.endpoint}`).pipe(
        map(res => new UserList().deserialize(res))
      );
    }
  }

  searchUsers(term: string): Observable<UserList> {
    return this.http.get<UserList>(`${this.contextPath}` + `${AppConstants.API_ROOT}/users/search/` + term)
    .pipe(
      map(res => new UserList().deserialize(res)));
  }
}
