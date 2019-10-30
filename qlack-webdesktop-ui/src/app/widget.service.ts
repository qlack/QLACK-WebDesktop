import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable()
export class WidgetService {

  constructor(private http: HttpClient) { }

  getActiveApplications(): Observable<any> {
    return this.http.get('/apps/all');
  }
}
