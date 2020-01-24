import {Component, OnInit} from '@angular/core';
import {LanguageDto} from '../dto/language-dto';
import {LanguageService} from '../services/language-service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-language-create',
  templateUrl: './language-create.component.html',
  styleUrls: ['./language-create.component.scss']
})
export class LanguageCreateComponent implements OnInit {

  languages: LanguageDto[] = [];
  myForm: FormGroup;

  constructor(private languageService: LanguageService, private _snackBar: MatSnackBar,
              private fb: FormBuilder) {
  }

  ngOnInit() {
    this.languageService.getLanguages(true).subscribe(languageList => {
      languageList.forEach((language) => {
        this.languages.push(language);
      });
    });
    this.myForm = this.fb.group({
      id: [''],
      name: [''],
      locale: [''],
      active: [''],
    });
  }

  save() {
    this.languageService.createLanguage(this.myForm).subscribe(success => {
      this._snackBar.open(localStorage.getItem('changesTranslated'), localStorage.getItem('savedTranslated'), {
        duration: 2000, verticalPosition: 'top'
      });
    }, Error => {
      this._snackBar.open(localStorage.getItem('errorTranslated'), localStorage.getItem('errorMessageTranslated'), {
        duration: 2000, verticalPosition: 'top'
      });
    });
  }
}
