import {Component, OnInit} from '@angular/core';
import {LanguageDto} from '../dto/language-dto';
import {LanguageService} from '../services/language-service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FormBuilder, FormGroup} from '@angular/forms';
import {UtilityService} from '../services/utility.service';

@Component({
  selector: 'app-language-create',
  templateUrl: './language-create.component.html',
  styleUrls: ['./language-create.component.scss']
})
export class LanguageCreateComponent implements OnInit {

  languages: LanguageDto[] = [];
  myForm: FormGroup;

  constructor(private languageService: LanguageService, private _snackBar: MatSnackBar,
              private fb: FormBuilder, private utilityService: UtilityService) {
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
      this.utilityService.popupSuccessAction(localStorage.getItem('success'), localStorage.getItem('dismiss'))
    }, Error => {
      this.utilityService.popupError(localStorage.getItem('error'));
    });
  }
}
