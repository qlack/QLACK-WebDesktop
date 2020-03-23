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
  @ViewChild('form', {static: true}) form: any;

  constructor(private languageService: LanguageService, private _snackBar: MatSnackBar, private utilityService: UtilityService) {
  }

  ngOnInit() {

    this.languageService.getLanguages(true).subscribe(languageList => {
      languageList.forEach((language, index) => {
        this.languages.push(language);
      });
    });
  }

  save() {
    this.languageService.updateLanguages(this.languages).subscribe(success => {
      this.utilityService.popupSuccessAction(localStorage.getItem('success'),localStorage.getItem('dismiss'))
    }, Error => {
      this.utilityService.popupError(localStorage.getItem('error'));
    });
    this.form.control.markAsPristine();

  }

}
