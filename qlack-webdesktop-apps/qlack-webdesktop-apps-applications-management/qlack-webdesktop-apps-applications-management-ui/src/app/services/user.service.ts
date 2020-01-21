import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AppConstants} from '../app.constants';
import {UserDto} from '../dto/user-dto';
import {CrudService} from './crud.service';
import {QFormsService} from '@eurodyn/forms';
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

  // Returns the JWT.
  private static getJwt(): string {
    return localStorage.getItem(AppConstants.JWT_STORAGE_NAME);
  }

  // Logs out the user terminating its session.
  logout(): Observable<any> {
    return this.http.get(AppConstants.API_ROOT + `/${this.resource}/logout`);
  }

  // Save user
  save(user: UserDto) {
    return this.http.post(AppConstants.API_SECURED_ROOT + `/${this.resource}`, JSON.stringify(user),
      {headers: {'Content-Type': 'application/json'}});
  }

  upload(form: FormGroup) {
    return this.qForms.uploadForm(this.http, form,
      `${AppConstants.API_SECURED_ROOT}/${this.resource}/upload`, false);
  }

}
