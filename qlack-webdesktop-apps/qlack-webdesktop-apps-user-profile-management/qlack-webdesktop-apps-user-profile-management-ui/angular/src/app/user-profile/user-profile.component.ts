import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {UtilityService} from '../services/utility.service';
import {UserProfileService} from '../services/user-profile.service';
import {LanguageService} from '../services/language-service';
import {LanguageDto} from '../dto/language-dto';
import {UserDetailsDto} from '../dto/user-details-dto';
import {FormBuilder, FormGroup} from '@angular/forms';
import {QFormsService} from '@eurodyn/forms';
import {TranslateService} from '@ngx-translate/core';
import {QngPubsubService} from '@qlack/qng-pubsub';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  previewProfileImageURL: string;
  previewBackgroundImageURL: string;
  languages: LanguageDto[] = [];
  userDetailsDto: any = new UserDetailsDto();
  myForm: FormGroup;
  errorMessage: string;
  successMessage: string;
  dismissMessage: string;
  systemLanguage: string;

  constructor(private userProfileService: UserProfileService, private dialog: MatDialog,
              private fb: FormBuilder, private qForms: QFormsService,
              private utilityService: UtilityService, private languageService: LanguageService,
              private translate: TranslateService, private qPubSubService: QngPubsubService) {
  }

  ngOnInit(): void {

    this.systemLanguage = sessionStorage.getItem('systemLanguage');
    this.languageService.getLanguages(false).subscribe(languageList => {
      languageList.forEach((language) => {
        this.languages.push(language);
      });
    });
    this.myForm = this.fb.group({
      id: [''],
      firstName: [{value: '', disabled: true}],
      lastName: [{value: '', disabled: true}],
      defaultLanguage: [''],
      profileImage: [undefined],
      backgroundImage: [undefined],
      deleteBackgroundImage: [false]
    });

    this.userProfileService.getUserAttributes().subscribe(userAttributeList => {
      if (userAttributeList.firstName != null) {
        this.userDetailsDto.firstName = userAttributeList.firstName.data;
      }
      if (userAttributeList.lastName != null) {
        this.userDetailsDto.lastName = userAttributeList.lastName.data;
      }
      if (userAttributeList.defaultLanguage != null) {
        this.userDetailsDto.defaultLanguage = userAttributeList.defaultLanguage.data;
      } else {
        this.userDetailsDto.defaultLanguage = 'en';
      }
      if (userAttributeList.profileImage != null) {
        this.userDetailsDto.profileImage = userAttributeList.profileImage.bindata;
      }
      if (userAttributeList.backgroundImage != null) {
        this.userDetailsDto.backgroundImage = userAttributeList.backgroundImage.bindata;
      }
      this.myForm.patchValue(this.userDetailsDto);
    });
    this.translate.get([
      'dismiss',
      'success',
      'error'
    ])
    .subscribe(translation => {
      this.errorMessage = translation['error'];
      this.successMessage = translation['success'];
      this.dismissMessage = translation['dismiss'];
    });
  }

  showProfileImagePreview(event) {
    const file = (event.target as HTMLInputElement).files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      this.previewProfileImageURL = reader.result as string;
    };
    this.myForm.patchValue({
      profileImage: file
    });
    this.myForm.get('profileImage').updateValueAndValidity();
  }

  showBackgroundImagePreview(event) {
    const file = (event.target as HTMLInputElement).files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      this.previewBackgroundImageURL = reader.result as string;
    };
    this.myForm.patchValue({
      backgroundImage: file
    });
    this.myForm.get('backgroundImage').updateValueAndValidity();
  }

  save() {

    this.userProfileService.saveDetails(this.myForm).toPromise().then(onNext => {
      this.utilityService.popupSuccessAction(this.successMessage,this.dismissMessage);
      this.clearBackgroundImagePreview();
      this.qPubSubService.publish('QRefreshPage', '');
      this.myForm.markAsPristine();
    }, onError => {
      this.utilityService.popupError(this.errorMessage);
    });
  }

  clearBackgroundImagePreview() {
    this.myForm.patchValue({
      backgroundImage: undefined
    });
    this.myForm.get('backgroundImage').updateValueAndValidity();
    this.previewBackgroundImageURL = null;
  }

  clearProfileImagePreview() {
    this.myForm.patchValue({
      profileImage: undefined
    });
    this.myForm.get('profileImage').updateValueAndValidity();
    this.previewProfileImageURL = null;
  }

  deleteBackgroundImage() {
    this.userDetailsDto.backgroundImage = null;
    this.myForm.patchValue({
      deleteBackgroundImage: true
    });
    this.myForm.get('deleteBackgroundImage').updateValueAndValidity();

  }
  isSystemLanguage(){
    return this.translate.getDefaultLang() == this.systemLanguage;
  }
}
