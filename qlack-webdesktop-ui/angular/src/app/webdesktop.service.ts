import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable()
export class WebdesktopService {

  constructor(private http: HttpClient) {
  }

  getActiveApplications(): Observable<any> {
    return this.http.get('/apps/');
  }

  getApplicationByName(applicationName: string): Observable<any> {
    return this.http.get('/apps/app/'+applicationName);
  }

  getUserAttributes(): Observable<any> {
    return this.http.get('/user/attributes');
  }

  getUserAttributeByName(attributeName: string): Observable<any> {
    return this.http.get('/user/attributes/' + attributeName);
  }
  getActiveProfile(): Observable<any> {
    return this.http.get('/user/profile');
  }

}
