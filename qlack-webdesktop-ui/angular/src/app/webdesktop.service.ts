import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable()
export class WebdesktopService {

  constructor(private http: HttpClient) {
  }

  getActiveApplications(): Observable<any> {
    return this.http.get('/api/application/');
  }

  getApplicationByName(applicationName: string): Observable<any> {
    return this.http.get('/api/application/byName/' + applicationName);
  }

  getUserAttributes(): Observable<any> {
    return this.http.get('/api/user/attributes');
  }

  getUserAttributeByName(attributeName: string): Observable<any> {
    return this.http.get('/api/user/attributes/' + attributeName);
  }

  getActiveProfile(): Observable<any> {
    return this.http.get('/api/user/profile');
  }

}
