import { Component, OnInit } from '@angular/core';
import {LanguageService} from '../services/language-service';
import {LanguageDto} from '../dto/language-dto';
import {UtilityService} from '../services/utility.service';
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-no-security-profile',
  templateUrl: './no-security-profile.component.html',
  styleUrls: ['./no-security-profile.component.scss']
})
export class NoSecurityProfileComponent implements OnInit {

  languages: LanguageDto[] = [];
  myForm: FormGroup;
  defaultLanguage:string ="en";
  constructor(private languageService: LanguageService, private utilityService: UtilityService, private fb: FormBuilder) {
  }

  ngOnInit(): void {
     if(sessionStorage.getItem('defaultLanguage') != null){
       this.defaultLanguage =sessionStorage.getItem('defaultLanguage');
     }
    this.languageService.getLanguages(false).subscribe(languageList => {
      languageList.forEach((language) => {
        this.languages.push(language);
      });
    });
    this.myForm = this.fb.group({
      defaultLanguage: [this.defaultLanguage]
    });

  }


  save() {
     sessionStorage.setItem('defaultLanguage',this.myForm.get('defaultLanguage').value);
    this.utilityService.popupSuccess('Saved!');
    this.myForm.markAsPristine();
  }


}
