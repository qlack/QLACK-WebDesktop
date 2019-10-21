import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges, OnInit } from "@angular/core";
import { DomSanitizer } from '@angular/platform-browser';
import { BreakpointObserver, Breakpoints } from "@angular/cdk/layout";


@Component({
  selector: 'app-widget',
  templateUrl: './widget.component.html',
  styleUrls: ['./widget.component.scss']
})
export class WidgetComponent implements OnChanges,OnInit {

  @Output() onOpen = new EventEmitter();
  @Output() onClose = new EventEmitter();
  @Output() onMinimize = new EventEmitter();
  @Output() onDragStart = new EventEmitter();
  @Output() onDragEnd = new EventEmitter();
  @Output() onDragMoved = new EventEmitter();
  @Output() onDragReleased = new EventEmitter();
  @Output() onWidgetDClicked = new EventEmitter();


  @Input() isOpened: boolean = false;
  @Input() isDraggable: boolean = true;
  @Input() isMinimizable: boolean = true;
  @Input() title: string = "Title";
  @Input() xPosition?: string;
  @Input() yPosition?: string;
  @Input() zIndex?: number;
  @Input() iconImageSrc?: string;

  safeIconImageSrc
  isMinimized = false;
  isSmallScreen = false;

  constructor(breakpointObserver: BreakpointObserver,
              private sanitizer: DomSanitizer, ) {
    breakpointObserver.observe([
      '(max-width: 600px)',
      Breakpoints.XSmall,
      Breakpoints.Web
    ]).subscribe(result => {
      if (result.matches) {
        if (result.breakpoints['(max-width: 600px)']) {
          this.isSmallScreen = true;
        }
        else
          this.isSmallScreen = false;
      }
    });
  }

  ngOnInit() {
    this.safeIconImageSrc = this.iconImageSrc && this.sanitizer.bypassSecurityTrustResourceUrl(this.iconImageSrc)
  }

  ngOnChanges(event: SimpleChanges) {
    if (event.isOpened) {
      let widgets: HTMLCollectionOf<Element> = document.getElementsByClassName("widget");
      if (event.isOpened.currentValue == true) {
        this.zIndex = this.zIndex || widgets.length;
        this.yPosition = this.yPosition || `${Math.floor(Math.random() * 40) + 20}%`;
        this.xPosition = this.xPosition || `${Math.floor(Math.random() * 30) + 15}%`;
        this.onOpen.emit(event.isOpened.currentValue);
      }
    }
  }

  dragStart(event) {
    this.onDragStart.emit(event);
  }

  dragEnd(event) {
    this.onDragEnd.emit(event);
  }

  dragMoved(event) {
    this.onDragMoved.emit(event);
  }

  widgetDClicked(event) {
    this.isMinimized = !this.isMinimized;
    this.onWidgetDClicked.emit(event);
  }


  dragReleased(event) {
    this.onDragReleased.emit(event);
  }

  close(event) {
    this.isOpened = false;
    this.onClose.emit(event);
  }

  widgetMinimized(event){
    this.isMinimized = !this.isMinimized;
    this.onMinimize.emit(event);
  }


}


