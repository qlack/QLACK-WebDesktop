import {Component, OnInit} from '@angular/core';
import {LanguageService} from '../services/language-service';
import {LanguageDto} from '../dto/language-dto';
import {UtilityService} from '../services/utility.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {QngPubsubService} from '@qlack/qng-pubsub';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-no-security-profile',
  templateUrl: './no-security-profile.component.html',
  styleUrls: ['./no-security-profile.component.scss']
})
export class NoSecurityProfileComponent implements OnInit {

  languages: LanguageDto[] = [];
  myForm: FormGroup;
  defaultLanguage: string = "en";
  errorMessage: string;
  successMessage: string;
  dismissMessage: string;

  constructor(private languageService: LanguageService, private utilityService: UtilityService,
              private qPubSubService: QngPubsubService, private fb: FormBuilder, private translate: TranslateService) {
  }

  ngOnInit(): void {
    if (sessionStorage.getItem('defaultLanguage') != null) {
      this.defaultLanguage = sessionStorage.getItem('defaultLanguage');
    }
    this.languageService.getLanguages(false).subscribe(languageList => {
      languageList.forEach((language) => {
        this.languages.push(language);
      });
    });
    this.myForm = this.fb.group({
      defaultLanguage: [this.defaultLanguage]
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


  save() {
    sessionStorage.setItem('defaultLanguage', this.myForm.get('defaultLanguage').value);
    this.utilityService.popupSuccessAction(this.successMessage, this.dismissMessage);
    this.qPubSubService.publish('QRefreshPage', '');
    this.myForm.markAsPristine();
  }


}
