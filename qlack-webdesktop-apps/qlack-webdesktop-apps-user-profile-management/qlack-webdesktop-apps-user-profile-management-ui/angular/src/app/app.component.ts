import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'user-profile-management';

  constructor(private translate: TranslateService) {
    sessionStorage.setItem('userId', '4d53243e-8122-4047-8722-938b5cb81d0c');
    translate.setDefaultLang('en');
  }
}
