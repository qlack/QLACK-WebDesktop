import {AppConstants} from '../app.constants';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {QFormsService, QPageableReply} from '@eurodyn/forms';
import {FormGroup} from '@angular/forms';

/**
 * A convenience CRUD service to be extended by concrete services to provide default CRUD methods.
 */
export class CrudService<T> {
  protected contextPath = window.location.pathname;

  constructor(public http: HttpClient, private endpoint: string, public qForms: QFormsService) {
  }

  save(object: T) {
    return this.http.post(`${this.contextPath}` + `${AppConstants.API_ROOT}/${this.endpoint}`, object);
  }

  getAll(queryString?: string): Observable<QPageableReply<T>> {
    if (queryString) {
      return this.http.get<QPageableReply<T>>(`${this.contextPath}` + `${AppConstants.API_ROOT}/${this.endpoint}?${queryString}`);
    } else {
      return this.http.get<QPageableReply<T>>(`${this.contextPath}` + `${AppConstants.API_ROOT}/${this.endpoint}`);
    }
  }

  get(id: any): Observable<T> {
    return this.http.get<T>(`${this.contextPath}` + `${AppConstants.API_ROOT}/${this.endpoint}/${id}`);
  }

  getFirst(): Observable<T> {
    return this.http.get<T>(`${this.contextPath}` + `${AppConstants.API_ROOT}/${this.endpoint}`);
  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${this.contextPath}` + `${AppConstants.API_ROOT}/${this.endpoint}/${id}`);
  }

  deleteAll(): Observable<any> {
    return this.http.delete(`${this.contextPath}` + `${AppConstants.API_ROOT}/${this.endpoint}`);
  }

  upload(form: FormGroup): Observable<any> {
    return this.qForms.uploadForm(this.http, form,
      `${this.contextPath}` + `${AppConstants.API_ROOT}/${this.endpoint}`, false);
  }
}
