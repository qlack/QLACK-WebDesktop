import {Component} from '@angular/core';
import {Widget} from "../../widget";


@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent {

    widgets: Widget[] = new Array();


    constructor() {
    }


    removeApp(widget: Widget) {
        const index = this.widgets.indexOf(widget, 0);
        if (index > -1) {
            this.widgets.splice(index, 1);
        }
    }

    setApp(widget: Widget) {
        if (this.widgets.includes(widget)) {
            if (widget.multipleInstances) {
                this.widgets.push(widget);
            }
        } else {
            this.widgets.push(widget);
        }


    }
}
