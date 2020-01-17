import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {QNgPubSubService} from '@qlack/qng-pub-sub';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'webDesktop';

  constructor(private translate: TranslateService, private qPubSubService: QNgPubSubService) {

    //TODO call user profile and get his/hers preferred language
    translate.setDefaultLang('en');
  }

  useLanguage(language: string) {
    this.translate.use(language);
  }
}
