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
  isSsoProfile: boolean;

  constructor(private translate: TranslateService, private userProfileService: UserProfileService, private titleService: Title) {
    this.userProfileService.getActiveProfile().subscribe(isSsoProfile => {
      this.isSsoProfile = isSsoProfile;
    });

    translate.get('tabTitle').subscribe((title: string) => {
      titleService.setTitle(title);
    });
  }
}
