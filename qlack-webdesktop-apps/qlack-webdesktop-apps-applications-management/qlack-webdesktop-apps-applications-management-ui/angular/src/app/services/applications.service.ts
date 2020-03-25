import {HttpClient} from '@angular/common/http';
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

  private webDesktopHost = window.location.origin;

  constructor(http: HttpClient, qForms: QFormsService) {
    super(http, 'applications', qForms);
  }

  // Save application
  save(application: ApplicationDto) {
    return this.http.post(`${this.webDesktopHost}api/application/`,
      JSON.stringify(application),
      {headers: {'Content-Type': 'application/json'}, observe: 'response'});
  }

  // Save application
  update(application: ApplicationDto, id: any) {
    return this.http.post(`${this.webDesktopHost}api/application/${id}`,
      JSON.stringify(application),
      {headers: {'Content-Type': 'application/json'}, observe: 'response'});
  }

  getUserAttributeByName(attributeName: string): Observable<any> {
    return this.http.get(`${this.contextPath}` + AppConstants.API_ROOT + '/user/attributes/' + attributeName);
  }

  isSsoEnabled(): Observable<any> {
    return this.http.get(`${this.contextPath}` + `${AppConstants.API_ROOT}/activeProfile`);
  }
}

