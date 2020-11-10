import {Component, OnInit, ViewChild} from '@angular/core';
import {LanguageService} from '../services/language-service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {LanguageDto} from '../dto/language-dto';
import {UtilityService} from '../services/utility.service';

@Component({
  selector: 'app-language-edit',
  templateUrl: './language-edit.component.html',
  styleUrls: ['./language-edit.component.scss']
})
export class LanguageEditComponent implements OnInit {
  languages: LanguageDto[] = [];
  systemLanguage: string;
  disabledSystemLanguage: LanguageDto;
  @ViewChild('form', {static: true}) form: any;


  constructor(private languageService: LanguageService, private _snackBar: MatSnackBar, private utilityService: UtilityService) {
  }

  ngOnInit() {
    this.systemLanguage = sessionStorage.getItem('systemLanguage');
    this.languageService.getLanguages(true).subscribe(languageList => {
      languageList.forEach((language) => {
        if (this.isSystemLanguage(language.locale)) {
          this.disabledSystemLanguage = language;
        } else {
          this.languages.push(language);
        }

      });
    });
  }

  save() {
    this.languageService.updateLanguages(this.languages).subscribe(success => {
      this.utilityService.popupSuccessAction(localStorage.getItem('success'), localStorage.getItem('dismiss'))
    }, Error => {
      this.utilityService.popupError(localStorage.getItem('error'));
    });
    this.form.control.markAsPristine();

  }

  isSystemLanguage(locale: string) {
    return locale == this.systemLanguage;
  }
}
