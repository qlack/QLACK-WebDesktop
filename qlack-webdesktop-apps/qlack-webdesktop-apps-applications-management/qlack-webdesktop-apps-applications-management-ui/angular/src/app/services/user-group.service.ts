import {Injectable} from '@angular/core';
import {CrudService} from "./crud.service";
import {HttpClient} from "@angular/common/http";
import {QFormsService} from "@eurodyn/forms";
import {UserGroupDto} from "../dto/user-group-dto";
import {Observable} from "rxjs";
import {AppConstants} from "../app.constants";

@Injectable({
  providedIn: 'root'
})
export class UserGroupService extends CrudService<UserGroupDto>{
  private resource = `usergroup`;

  constructor(http: HttpClient, qForms: QFormsService) { super(http, 'usergroup', qForms);}
}
