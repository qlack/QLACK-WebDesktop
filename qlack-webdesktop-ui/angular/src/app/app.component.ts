import {Component} from '@angular/core';
import {QngPubsubService} from '@qlack/qng-pubsub';
import {WebdesktopService} from './webdesktop.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'webDesktop';
  backgroundImage: any[];
  background: any;

  constructor(private qPubSubService: QngPubsubService, private webDesktopService: WebdesktopService) {
    this.webDesktopService.getUserAttributeByName("backgroundImage").subscribe(attribute => {
      if (attribute != null) {
        this.backgroundImage = attribute.bindata;
      }
      else{
         this.background='linear-gradient(45deg, #43c6ac, #2f2a9a)'
      }
    });

  }

}
