import {AppConstants} from '../app.constants';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {QFormsService, QPageableReply} from '@eurodyn/forms';
import {FormGroup} from '@angular/forms';
import {map} from "rxjs/operators";

/**
 * A convenience CRUD service to be extended by concrete services to provide default CRUD methods.
 */
export class CrudService<T> {
  constructor(public http: HttpClient, protected endpoint: string, public qForms: QFormsService) {
  }

  save(object: T) {
    return this.http.post(`${AppConstants.API_ROOT}/${this.endpoint}`, object);
  }

  getAll(queryString?: string): Observable<QPageableReply<T>> {
    if (queryString) {
      return this.http.get<QPageableReply<T>>(`${AppConstants.API_ROOT}/${this.endpoint}` + `?${queryString}`);
    } else {
      return this.http.get<QPageableReply<T>>(`/${this.endpoint}`);
    }
  }

  getAllSorted(queryString?: string): Observable<T[]> {
    if (queryString) {
      return this.http.get<T[]>(`${AppConstants.API_SECURED_ROOT}/${this.endpoint}/sorted?${queryString}`);
    } else {
      return this.http.get<T[]>(`${AppConstants.API_SECURED_ROOT}/${this.endpoint}/sorted`);
    }
  }

  get(id: any): Observable<T> {
    return this.http.get<T>(`${AppConstants.API_ROOT}/${this.endpoint}/${id}`);
  }

  getFirst(): Observable<T> {
    return this.http.get<T>(`${AppConstants.API_SECURED_ROOT}/${this.endpoint}`);
  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${AppConstants.API_ROOT}/${this.endpoint}/${id}`);
  }

  deleteAll(): Observable<any> {
    return this.http.delete(`${AppConstants.API_SECURED_ROOT}/${this.endpoint}`);
  }

  upload(form: FormGroup): Observable<any> {
    return this.qForms.uploadForm(this.http, form,
      AppConstants.API_ROOT + '/apps/', false);
  }

  search(term: string, endpoint: string) {
    return this.http.get<any[]>(`${AppConstants.API_ROOT}/${endpoint}/search/` + term)
    .pipe(
      map(
        (data: any) => {
          return (
            data.length != 0 ? data.content as any[] : [{"Name": "Not Found"} as any]
          );
        }
      ));
  }
}
