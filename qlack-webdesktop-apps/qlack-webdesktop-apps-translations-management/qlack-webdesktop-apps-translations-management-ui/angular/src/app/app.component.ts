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
      'translations-management-ui.success',
      'translations-management-ui.dismiss',
      'translations-management-ui.error',
    ])
    .subscribe(translation => {
      localStorage.setItem('success', translation['translations-management-ui.success']);
      localStorage.setItem('error', translation['translations-management-ui.error']);
      localStorage.setItem('dismiss', translation['translations-management-ui.dismiss']);
    });
  }

}
