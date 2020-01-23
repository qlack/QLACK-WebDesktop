import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ApplicationsService} from "../services/applications.service";
import {ActivatedRoute, Router} from "@angular/router";
import {QFormsService} from "@eurodyn/forms";
import {MatDialog} from "@angular/material/dialog";
import {TranslateService} from "@ngx-translate/core";
import {UtilityService} from "../services/utility.service";
import {QLACKTypescriptFormValidationService} from "@qlack/form-validation";
import {UserGroupService} from "../services/user-group.service";
import {OkCancelModalComponent} from "../shared/component/display/ok-cancel-modal/ok-cancel-modal.component";

@Component({
  selector: 'app-user-group-edit',
  templateUrl: './user-group-edit.component.html',
  styleUrls: ['./user-group-edit.component.scss']
})
export class UserGroupEditComponent implements OnInit {

  form: FormGroup;
  id: string;
  isEdit = false;

  constructor(private fb: FormBuilder, private applicationsService: ApplicationsService,
              private route: ActivatedRoute,
              private qForms: QFormsService, private router: Router, private dialog: MatDialog,
              private translateService: TranslateService, private utilityService: UtilityService,
              private validationService: QLACKTypescriptFormValidationService, private userGroupService: UserGroupService) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
    this.isEdit = (this.id !== '0');
console.log(this.isEdit)
console.log(this.id)
    // Setup the form.
    this.form = this.fb.group({
      id: ['0'],
      name: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      description: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
    });
    console.log(this.id)
    // Fill-in the form with data if editing an existing item.
    if (this.isEdit) {
      this.userGroupService.get(this.id).subscribe(onNext => {
        this.form.patchValue(onNext);
      });
    }
  }

  save() {
    this.userGroupService.save(this.qForms.cleanupForm(this.form)).subscribe(
      (response) => {
        this.utilityService.popupSuccess(
          this.isEdit ?
            this.getMessageTranslations('management-app-ui.updated') + "!"
            : this.getMessageTranslations('management-app-ui.created') + "!");
        this.router.navigate(["/usergroup"]);
      }, error => {

        if (error.status == 400) {
          let validationErrors = error.error;
          //inserts proper translations
          validationErrors.forEach( validationError => {
            validationError.code = 'management-app-ui.' + validationError.code;
          });
          if (error.error) {
            this.validationService.validateForm(this.form, validationErrors, this.translateService);
          } else {
            this.utilityService.popupError(this.getMessageTranslations('management-app-ui.error'));
          }
        } else {
          if (error.error === "alreadyExistsCode"){
            this.utilityService.popupError(this.getMessageTranslations('management-app-ui.the') + " " + this.getMessageTranslations('management-app-ui.applicationName') +
              " " + this.getMessageTranslations('management-app-ui.alreadyExistsCode'));
          } else {
            this.utilityService.popupError(error.error);
          }
        }
      });
  }

  delete() {
    this.dialog.open(OkCancelModalComponent, {
      data: {
        title: this.getMessageTranslations('management-app-ui.deleteGroup'),
        question: this.getMessageTranslations('management-app-ui.deleteQuestion'),
        buttons: {
          ok: true, cancel: true, reload: false
        }
      }
    }).afterClosed().subscribe(result => {
      if (result) {
        this.userGroupService.delete(this.id).subscribe(onNext => {
          this.utilityService.popupSuccess(this.getMessageTranslations('management-app-ui.deleteSuccess'));
          this.router.navigate(['usergroup']);
        });
      }
    });
  }

  //translate snack bar messages
  getMessageTranslations(message: string) {
    let translatedMessage = null;
    this.translateService.get(message).subscribe((translation: string) => {
      translatedMessage = translation;
    });
    return translatedMessage;
  }
}
