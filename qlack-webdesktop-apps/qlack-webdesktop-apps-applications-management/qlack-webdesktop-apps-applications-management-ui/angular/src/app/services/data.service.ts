import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class DataService {

  private messageSource = new BehaviorSubject(true);
  currentMessage = this.messageSource.asObservable();

  constructor() { }

  isNavBarVisible(message: boolean) {
    this.messageSource.next(message)
  }

}
