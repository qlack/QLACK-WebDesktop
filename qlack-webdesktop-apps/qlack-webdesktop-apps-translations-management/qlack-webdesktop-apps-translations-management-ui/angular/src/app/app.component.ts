import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'translationsManagement';
  navLinks: { path: string, label: string }[] = [
    {path: '/languages', label: 'languages'},
    {path: '/translations', label: 'translations'}
  ];

  constructor(private translate: TranslateService) {

    // TODO call user profile and get his/hers preferred language
    translate.setDefaultLang('el');

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
