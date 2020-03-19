import {Injectable} from '@angular/core';
import {CrudService} from "./crud.service";
import {HttpClient} from "@angular/common/http";
import {QFormsService, QPageableReply} from "@eurodyn/forms";
import {UserGroupDto} from "../dto/user-group-dto";
import {Observable} from "rxjs";
import {AppConstants} from "../app.constants";

@Injectable({
  providedIn: 'root'
})
export class UserGroupService extends CrudService<UserGroupDto> {

  constructor(http: HttpClient, qForms: QFormsService) {
    super(http, 'usergroup', qForms);
  }

  getUsersByGroupId(queryString?: string, groupId?: string): Observable<any> {
    return this.http.get<QPageableReply<any>>(
      `${this.contextPath}` + `${AppConstants.API_ROOT}/${this.endpoint}/users/` + groupId + `?${queryString}`);
  }

}
