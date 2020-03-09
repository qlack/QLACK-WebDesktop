import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {TranslationService} from './services/translation.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {
  title = 'translationsManagement';
  navLinks: { path: string, label: string }[] = [
    {path: '/languages', label: 'languages'},
    {path: '/translations', label: 'translations'}
  ];

  constructor(private translate: TranslateService, private translationService: TranslationService) {

    this.translationService.getUserAttributeByName("defaultLanguage").subscribe(attr => {
      if(attr != null){
        if (attr.data != null){
          translate.setDefaultLang(attr.data);
        }

      }
      else{
        translate.setDefaultLang("en");
      }
    },error => { translate.setDefaultLang("en");});

    translate.get([
      'translations-management-ui.changes',
      'translations-management-ui.saved',
      'translations-management-ui.error',
      'translations-management-ui.errorMessage',
    ])
    .subscribe(translation => {
      localStorage.setItem('changesTranslated', translation['translations-management-ui.changes']);
      localStorage.setItem('savedTranslated', translation['translations-management-ui.saved']);
      localStorage.setItem('errorTranslated', translation['translations-management-ui.error']);
      localStorage.setItem('errorMessageTranslated', translation['translations-management-ui.errorMessage']);
    });
  }

}
