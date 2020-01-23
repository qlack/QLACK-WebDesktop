import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {ApplicationDto} from '../dto/application-dto';
import {CrudService} from './crud.service';
import {QFormsService} from '@eurodyn/forms';
import {AppConstants} from '../app.constants';

/**
 * A service providing functionality for the user of the application, including authentication,
 * authorisation and session management.
 */
@Injectable({
  providedIn: 'root'
})
export class ApplicationsService extends CrudService<ApplicationDto> {
  private resource = `applications`;

  constructor(http: HttpClient, qForms: QFormsService) {
    super(http, 'applications', qForms);
  }

  // Returns the JWT.
  private static getJwt(): string {
    return localStorage.getItem(AppConstants.JWT_STORAGE_NAME);
  }

  // Logs out the user terminating its session.
  logout(): Observable<any> {
    return this.http.get(AppConstants.API_ROOT + `/${this.resource}/logout`);
  }

  // Save application
  save(application: ApplicationDto) {
    return this.http.post(`${AppConstants.API_ROOT}/${this.resource}`, JSON.stringify(application),
      {headers: {'Content-Type': 'application/json'}, observe: 'response'});
  }

  //get all applications
  getAplications(): Observable<any> {
    return this.http.get(AppConstants.API_ROOT + `/${this.resource}`);
  }

  //edit an application
  getApplicationById(id): Observable<any> {
    return this.http.get(AppConstants.API_ROOT + `/${this.resource}/` + id);
  }

  //application Name validation
  getApplicationByName(applicationName): Observable<any> {
    return this.http.get(AppConstants.API_ROOT + `/${this.resource}` +"/name", {params: new HttpParams().set('name', applicationName)});
  }
}
