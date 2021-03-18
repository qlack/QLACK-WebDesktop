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

  getActiveApplicationByName(applicationName: string): Observable<any> {
    return this.http.get('/api/application/activeByName/' + applicationName);
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

  getSystemDefaultLanguage(): Observable<any> {
    return this.http.get('/api/user/systemLanguage',
        {responseType: 'text'});
  }

  logout(url: string): Observable<any> {
    return this.http.get(url, {observe: 'response', responseType: 'text'});
  }

  terminateSession(): Observable<any> {
    return this.http.post('/api/session/terminate', null);
  }

  initSession(): Observable<any> {
    return this.http.post('/api/session/init', null);
  }
}
