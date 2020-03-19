import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {TranslationService} from './services/translation.service';
import {Title} from '@angular/platform-browser';

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

  constructor(private translate: TranslateService, private translationService: TranslationService, private titleService: Title) {

    translate.get('translations-management-ui.tabTitle').subscribe((title: string) => {
      titleService.setTitle(title);
    });
    translate.get([
      'translations-management-ui.saved',
      'translations-management-ui.error',
    ])
    .subscribe(translation => {
      localStorage.setItem('saved', translation['translations-management-ui.saved']);
      localStorage.setItem('error', translation['translations-management-ui.error']);
    });
  }

}
