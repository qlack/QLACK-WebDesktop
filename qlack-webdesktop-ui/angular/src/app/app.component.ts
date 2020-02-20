import {Component, Renderer2} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {QngPubsubService} from '@qlack/qng-pubsub';
import {WebdesktopService} from './webdesktop.service';
import {UserDetailsDto} from './dto/user-details-dto';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'webDesktop';
  userDetailsDto: any = new UserDetailsDto();

  constructor(private translate: TranslateService, private qPubSubService: QngPubsubService, private webDesktopService: WebdesktopService) {
    this.webDesktopService.getUserAttributes().subscribe(userAttributeList => {
      if (userAttributeList.firstName != null) {
        this.userDetailsDto.firstName = userAttributeList.firstName.data;
      }
      if (userAttributeList.lastName != null) {
        this.userDetailsDto.lastName = userAttributeList.lastName.data;
      }
      if (userAttributeList.defaultLanguage != null) {
        this.userDetailsDto.defaultLanguage = userAttributeList.defaultLanguage.data;
      } else {
        this.userDetailsDto.defaultLanguage = 'en';
      }
      if (userAttributeList.profileImage != null) {
        this.userDetailsDto.profileImage = userAttributeList.profileImage.bindata;
      }
      if (userAttributeList.backgroundImage != null) {
        this.userDetailsDto.backgroundImage = userAttributeList.backgroundImage.bindata;
      }
    });
    //TODO call user profile and get his/hers preferred language
    translate.setDefaultLang(this.userDetailsDto.defaultLanguage);

  }

}
