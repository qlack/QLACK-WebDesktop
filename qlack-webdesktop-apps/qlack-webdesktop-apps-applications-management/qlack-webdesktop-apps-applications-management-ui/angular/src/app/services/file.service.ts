/**
 * A service providing functionality for the user of the application, including authentication,
 * authorisation and session management.
 */
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AppConstants} from '../app.constants';
import {FileDto} from '../dto/file-dto';
import {CrudService} from './crud.service';
import {QFormsService} from '@eurodyn/forms';

@Injectable({
  providedIn: 'root'
})
export class FileService extends CrudService<FileDto>{
  private resource = `file`;

  constructor(http: HttpClient, qForms: QFormsService) {
    super(http, 'file', qForms);
  }

  public upload(formData) {
    return this.http.post(AppConstants.API_ROOT + `/applications/upload`, formData);
  }

  getImage(id: any) {
    return AppConstants.API_SECURED_ROOT + '/' + this.resource +  `/${id}`;
    // return this.http.get(`${AppConstants.API_SECURED_ROOT}/${this.resource}/${id}`, { responseType: 'blob' });
  }
}
