import {Injectable} from '@angular/core';
import {CrudService} from "./crud.service";
import {HttpClient} from "@angular/common/http";
import {QFormsService} from "@eurodyn/forms";
import {UserGroupDto} from "../dto/user-group-dto";
import {Observable} from "rxjs";
import {AppConstants} from "../app.constants";
import {UserList} from '../dto/user-list-dto';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserGroupService extends CrudService<UserGroupDto> {

  constructor(http: HttpClient, qForms: QFormsService) {
    super(http, 'usergroup', qForms);
  }

  getUsersByGroupId(queryString?: string, groupId?: string): Observable<UserList> {
    return this.http.get<UserList>(
      `${this.contextPath}` + `${AppConstants.API_ROOT}/${this.endpoint}/users/` + groupId + `?${queryString}`)
    .pipe(
      map(res => new UserList().deserialize(res)));
  }

}
