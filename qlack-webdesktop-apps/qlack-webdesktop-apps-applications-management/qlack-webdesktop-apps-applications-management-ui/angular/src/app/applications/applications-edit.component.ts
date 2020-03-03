import {
  Component,
  OnInit,
  ViewEncapsulation
} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {QFormsService} from '@eurodyn/forms';
import {MatDialog} from '@angular/material/dialog';
import {ApplicationsService} from '../services/applications.service';
import {TranslateService} from '@ngx-translate/core';
import {UtilityService} from "../services/utility.service";
import {QLACKTypescriptFormValidationService} from "@qlack/form-validation";
import {DataService} from "../services/data.service";
import {MatSlideToggleChange} from "@angular/material/slide-toggle";

@Component({
  selector: 'app-applications-edit',
  templateUrl: './applications-edit.component.html',
  styleUrls: ['./applications-edit.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ApplicationsEditComponent implements OnInit {
  form: FormGroup;
  id: string;
  isEdit = false;
  componentTitle = 'editApp';
  isVisible: boolean;
  isDisabled: boolean;
  usersAdded: string[];
  usersRemoved: string[];
  groupsAdded: string[];
  groupsRemoved: string[];

  constructor(private fb: FormBuilder, private applicationsService: ApplicationsService,
              private route: ActivatedRoute,
              private qForms: QFormsService, private router: Router, private dialog: MatDialog,
              private translateService: TranslateService, private utilityService: UtilityService,
              private validationService: QLACKTypescriptFormValidationService, private data: DataService) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
    this.isEdit = (this.id !== '0');

    // Setup the form.
    this.checkNewOrEdit();
    this.form = this.fb.group({
      id: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      title: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      description: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      version: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      appUrl: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      proxyPath: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      proxyAppUrl: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      icon: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      iconSmall: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      multipleInstances: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      width: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      minWidth: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      height: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      minHeight: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      resizable: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      minimizable: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      closable: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      draggable: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      addedOn: [{value: new Date(), disabled: true}, [Validators.maxLength(1024)]],
      lastDeployedOn: [{value: new Date(), disabled: true}, [Validators.maxLength(1024)]],
      system: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      showTitle: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      groupName: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      applicationName: [{value: '', disabled: false}, [Validators.maxLength(1024)]],
      checksum: [{value: ' ', disabled: false}, [Validators.maxLength(1024)]],
      editedByUI: [{value: true, disabled: false}],

      active: [{value: '', disabled: false}],
      restrictAccess: [{value: '', disabled: false}],
    });

    this.data.isNavBarVisible(false);
  }

  ngAfterViewInit(): void {
    // Setup the form.
    if (this.componentTitle === 'editApp') {
      this.applicationsService.getApplicationById(this.id).subscribe(application => {
        this.form.disable();
        this.updateApplicationContent(application.details, 'title');
        this.updateApplicationContent(application.details, 'description');
        for (var appProperty in application.details) {
          if (this.form.controls[appProperty]) {
            this.form.controls[appProperty].setValue(application.details[appProperty]);
          }
        }
        this.form.controls['active'].enable();
        this.form.controls['restrictAccess'].enable();
        this.form.controls['addedOn'].setValue(new Date(this.form.get('addedOn').value));
        this.form.controls['lastDeployedOn'].setValue(new Date(this.form.get('lastDeployedOn').value));
        this.isDisabled = this.form.controls['restrictAccess'].value;

        sessionStorage.setItem('usersAdded', JSON.stringify(application.usersAdded));
        sessionStorage.setItem('usersRemoved', JSON.stringify(application.usersRemoved));
        sessionStorage.setItem('groupsAdded', JSON.stringify(application.groupsAdded));
        sessionStorage.setItem('groupsRemoved', JSON.stringify(application.groupsRemoved));
        sessionStorage.setItem('users', JSON.stringify(application.users));
        sessionStorage.setItem('userGroups', JSON.stringify(application.userGroups));
      });
    }
  }

  isNavBarVisible(value: boolean) {
    this.data.isNavBarVisible(value);
  }

  updateApplicationContent(application, property) {
    this.translateService.get(application.applicationName + '.' + property).subscribe(
      (titleTranslated: string) => {
        application[property] = titleTranslated;
      });
  }

  save() {
    let usersAdded;
    let usersRemoved;
    let groupsAdded;
    let groupsRemoved;
    this.form.controls['addedOn'].setValue(new Date(this.form.get('addedOn').value).valueOf());
    this.form.controls['lastDeployedOn'].setValue(new Date(this.form.get('lastDeployedOn').value).valueOf());

    try {
      usersAdded = JSON.parse(sessionStorage.getItem('usersAdded'));
      usersRemoved = JSON.parse(sessionStorage.getItem('usersRemoved'));
      groupsAdded = JSON.parse(sessionStorage.getItem('groupsAdded'));
      groupsRemoved = JSON.parse(sessionStorage.getItem('groupsRemoved'));
    } catch (e) {
      console.log(e)
    }

    this.form.addControl('details', new FormControl({value: this.qForms.cleanupForm(this.form), disabled: false}));
    this.form.addControl('usersAdded', new FormControl({value: usersAdded, disabled: false}));
    this.form.addControl('usersRemoved', new FormControl({value: usersRemoved, disabled: false}));
    this.form.addControl('groupsAdded', new FormControl({value: groupsAdded, disabled: false}));
    this.form.addControl('groupsRemoved', new FormControl({value: groupsRemoved, disabled: false}));


    this.applicationsService.save(this.qForms.cleanupForm(this.form)).subscribe(
      (response) => {
        this.utilityService.popupSuccessAction(
          this.getMessageTranslations('management-app-ui.success') + "!", this.getMessageTranslations('management-app-ui.dismiss'));
        this.router.navigate(["/"]).then( () => {
          if (this.componentTitle === 'newApp') {
            window.location.reload();
          }
        });
        this.data.isNavBarVisible(true);
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
    this.form.controls['addedOn'].setValue(new Date(this.form.get('addedOn').value));
    this.form.controls['lastDeployedOn'].setValue(new Date(this.form.get('lastDeployedOn').value));
    sessionStorage.clear();
  }

  //translate snack bar messages
  getMessageTranslations(message: string) {
    let translatedMessage = null;
    this.translateService.get(message).subscribe((translation: string) => {
      translatedMessage = translation;
    });
    return translatedMessage;
  }

  checkNewOrEdit() {
    this.componentTitle = "editApp";
    if (this.router.url.toLowerCase().endsWith('new')) {
      this.componentTitle = "newApp";
    }
  }

  goBack() {
    this.router.navigate(["/applications"]).then( () => this.data.isNavBarVisible(true));
  }

  toggle($event: MatSlideToggleChange) {
      this.isDisabled = $event.checked;
  }
}
