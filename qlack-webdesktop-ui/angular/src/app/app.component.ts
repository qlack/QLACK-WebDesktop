import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {QngPubsubService} from '@qlack/qng-pubsub';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {
  title = 'webDesktop';

  constructor(private translate: TranslateService, private qPubSubService: QngPubsubService) {

    //TODO call user profile and get his/hers preferred language
    translate.setDefaultLang('en');
  }

  useLanguage(language: string) {
    this.translate.use(language);
  }
}
