import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {UserProfileService} from './services/user-profile.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'user-profile-management';

  constructor(private translate: TranslateService,private userProfileService: UserProfileService ) {
    this.userProfileService.getUserAttributeByName("defaultLanguage").subscribe(attr => {
     if(attr != null){
       translate.setDefaultLang(attr.data);
     }
     else{
       translate.setDefaultLang("en");
     }
    });

  }
}
