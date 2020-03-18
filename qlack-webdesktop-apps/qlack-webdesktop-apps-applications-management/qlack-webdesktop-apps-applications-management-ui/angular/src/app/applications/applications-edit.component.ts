import {Component, OnInit, ViewEncapsulation} from '@angular/core';
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
  componentTitle = 'newApp';
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
      id: ['0'],
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

    // Fill-in the form with data if editing an existing item.
    if (this.isEdit) {
      this.applicationsService.get(this.id).subscribe(onNext => {
        this.form.disable();
        this.updateApplicationContent(onNext.details, 'title');
        this.updateApplicationContent(onNext.details, 'description');
        this.form.patchValue(onNext.details);
        this.form.controls['active'].enable();
        this.applicationsService.isSsoEnabled().subscribe(isSso => {
          if (isSso){
            this.form.controls['restrictAccess'].enable();
          }
        });
        this.form.controls['addedOn'].setValue(new Date(this.form.get('addedOn').value));
        this.form.controls['lastDeployedOn'].setValue(new Date(this.form.get('lastDeployedOn').value));
        this.isDisabled = this.form.controls['restrictAccess'].value;

        //prepare session storage to handle user and usergroup permissions
        sessionStorage.setItem('usersAdded', JSON.stringify(onNext.usersAdded));
        sessionStorage.setItem('usersRemoved', JSON.stringify(onNext.usersRemoved));
        sessionStorage.setItem('groupsAdded', JSON.stringify(onNext.groupsAdded));
        sessionStorage.setItem('groupsRemoved', JSON.stringify(onNext.groupsRemoved));
        sessionStorage.setItem('users', JSON.stringify(onNext.users));
        sessionStorage.setItem('userGroups', JSON.stringify(onNext.userGroups));
      });
    }
    this.data.isNavBarVisible(false);
  }

  isNavBarVisible(value: boolean) {
    this.data.isNavBarVisible(value);
  }

  updateApplicationContent(application, property) {
    this.translateService.get(application.applicationName + '.' + property).subscribe(
      (titleTranslated: string) => application[property] = titleTranslated);
  }

  //handle permissions before save or update
  handlePermissions(){
    let usersAdded;
    let usersRemoved;
    let groupsAdded;
    let groupsRemoved;
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
    sessionStorage.clear();
  }

  submit() {
    this.form.controls['addedOn'].setValue(new Date(this.form.get('addedOn').value).valueOf());
    this.form.controls['lastDeployedOn'].setValue(new Date(this.form.get('lastDeployedOn').value).valueOf());

    this.handlePermissions();

    if (this.isEdit){
      this.update();
    } else {
      this.save();
    }

    this.form.controls['addedOn'].setValue(new Date(this.form.get('addedOn').value));
    this.form.controls['lastDeployedOn'].setValue(new Date(this.form.get('lastDeployedOn').value));
  }

  save(){
    this.applicationsService.save(this.qForms.cleanupForm(this.form)).subscribe(
      (response) => {
        this.utilityService.popupSuccessAction(
          this.getMessageTranslations('management-app-ui.success') + "!", this.getMessageTranslations('management-app-ui.dismiss'));
        this.router.navigate(["/"]).then(() =>window.location.reload());
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
  }

  update() {
    this.applicationsService.update(this.qForms.cleanupForm(this.form), this.id).subscribe(
      (response) => {
        this.utilityService.popupSuccessAction(
          this.getMessageTranslations('management-app-ui.success') + "!", this.getMessageTranslations('management-app-ui.dismiss'));
        this.router.navigate(["/"]);
        this.data.isNavBarVisible(true);
      }, error => {

        if (error.status == 400) {
          this.utilityService.popupError(this.getMessageTranslations('management-app-ui.error'));
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

  checkNewOrEdit() {
    this.componentTitle = "newApp"
    if (this.isEdit) {
      this.componentTitle = "editApp";
    }
  }

  toggle($event: MatSlideToggleChange) {
      this.isDisabled = $event.checked;
  }

  isSsoEnabled(){
    return this.applicationsService.isSsoEnabled().subscribe();
  }

}
