import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {UserProfileService} from './services/user-profile.service';
import {Title} from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'user-profile-management';
  isSsoProfile: boolean ;
  constructor(private translate: TranslateService,private userProfileService: UserProfileService,private titleService: Title ) {
    this.userProfileService.getActiveProfile().subscribe( isSsoProfile =>{
      this.isSsoProfile = isSsoProfile;
    });
    if(this.isSsoProfile){
      this.userProfileService.getUserAttributeByName("defaultLanguage").subscribe(attr => {
        if(attr != null){
          if (attr.data != null){
            translate.setDefaultLang(attr.data);
          }
        }
        else{
          translate.setDefaultLang("en");
        }
      },error => { translate.setDefaultLang("en");});
    }else {
      if (sessionStorage.getItem('defaultLanguage') != null){
        translate.setDefaultLang(sessionStorage.getItem('defaultLanguage'));
      }else {
        translate.setDefaultLang("en");
      }
    }
    translate.get('tabTitle').subscribe((title: string) => {
      titleService.setTitle(title);
    });
  }
}
