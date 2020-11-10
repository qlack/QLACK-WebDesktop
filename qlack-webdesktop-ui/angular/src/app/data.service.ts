import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable()
export class DataService {

  private applications = new BehaviorSubject([]);
  activeApplications = this.applications.asObservable();

  constructor() {
  }

  initApplications(applications: any[]) {
    this.applications.next(applications);
  }

}
