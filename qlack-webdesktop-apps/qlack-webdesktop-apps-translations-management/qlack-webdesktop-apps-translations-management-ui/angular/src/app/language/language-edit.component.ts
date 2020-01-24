import {Component, OnInit, ViewChild} from '@angular/core';
import {LanguageService} from '../services/language-service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {LanguageDto} from '../dto/language-dto';

@Component({
  selector: 'app-language-edit',
  templateUrl: './language-edit.component.html',
  styleUrls: ['./language-edit.component.scss']
})
export class LanguageEditComponent implements OnInit {
  languages: LanguageDto[] = [];
  @ViewChild('form', {static: true}) form: any;

  constructor(private languageService: LanguageService, private _snackBar: MatSnackBar) {
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
      this._snackBar.open(localStorage.getItem('changesTranslated'), localStorage.getItem('savedTranslated'), {
        duration: 2000, verticalPosition: 'top'
      });
    }, Error => {
      this._snackBar.open(localStorage.getItem('errorTranslated'), localStorage.getItem('errorMessageTranslated'), {
        duration: 2000, verticalPosition: 'top'
      });
    });
    this.form.control.markAsPristine();

  }

}
