import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable()
export class DataService {

  private messageSource = new BehaviorSubject(true);
  currentMessage = this.messageSource.asObservable();

  private permissionsEditedSubject = new BehaviorSubject(false);
  permissionsEdited = this.permissionsEditedSubject.asObservable();

  constructor() {
  }

  isNavBarVisible(message: boolean) {
    this.messageSource.next(message)
  }

  editPermissions(value: boolean){
    this.permissionsEditedSubject.next(value);
  }

}
