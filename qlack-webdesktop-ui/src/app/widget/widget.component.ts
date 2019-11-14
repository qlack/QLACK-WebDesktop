import {Component, Input, Output, EventEmitter, OnChanges, SimpleChanges, OnInit} from "@angular/core";
import {DomSanitizer} from '@angular/platform-browser';
import {CdkDragEnd} from '@angular/cdk/drag-drop';
import {ResizedEvent} from 'angular-resize-event';

@Component({
  selector: 'app-widget',
  templateUrl: './widget.component.html',
  styleUrls: ['./widget.component.scss']
})
export class WidgetComponent implements OnChanges, OnInit {

  @Output() onOpen = new EventEmitter();
  @Output() onClose = new EventEmitter();
  @Output() onMinimize = new EventEmitter();
  @Output() onMaximize = new EventEmitter();
  @Output() onDragStart = new EventEmitter();
  @Output() onDragEnd = new EventEmitter();
  @Output() onDragMoved = new EventEmitter();
  @Output() onDragReleased = new EventEmitter();
  @Output() onWidgetClicked = new EventEmitter();

  @Input() isActive: boolean = true;
  @Input() isDraggable: boolean = true;
  @Input() isMinimizable: boolean = true;
  @Input() isMaximizable: boolean = true;
  @Input() isClosable: boolean = true;
  @Input() isResizable: boolean = true;
  @Input() width?: number;
  @Input() height?: number;
  @Input() minHeight?: number;
  @Input() minWidth?: number;
  @Input() Id?: number;
  @Input() iconImageSrc?: string;
  @Input() appUrl?: string;
  @Input() showTitle: boolean = false;
  @Input() multipleInstances: boolean = false;
  @Input() applicationName?: string;

  widgetPosition = {x: 0, y: 150};
  // 42 is the height of the div of image icon
  widgetMinimizedPosition = {x: 0, y: -42};
  safeIconImageSrc;
  safeAppUrl;
  isMinimized = false;
  isSmallScreen = false;
  isMaximized = false;
  widgetCurrentPosition = {x: 0, y: 150};
  xPosition?: string;
  yPosition?: string;
  tempWidth: number;
  tempHeight: number;
  displayIframe: boolean = false;
  zIndex?: number = 2;
  static zIndexCounter?: number = 3;
  initDraggableValue: boolean;

  constructor(private sanitizer: DomSanitizer,) {
  }

  ngOnInit() {
    this.safeIconImageSrc = this.iconImageSrc && this.sanitizer.bypassSecurityTrustResourceUrl(this.iconImageSrc)
    this.safeAppUrl = this.appUrl && this.sanitizer.bypassSecurityTrustResourceUrl(this.appUrl)
    this.initDraggableValue = this.isDraggable;
    this.zIndex = this.zIndex = ++WidgetComponent.zIndexCounter;
  }

  ngOnChanges(event: SimpleChanges) {
    if (event.isActive) {
      let widgets: HTMLCollectionOf<Element> = document.getElementsByClassName("widget");
      if (event.isActive.currentValue == true) {

        // this.yPosition = this.yPosition || `${Math.floor(Math.random() * 40) + 20}%`;
        // this.xPosition = this.xPosition || `${Math.floor(Math.random() * 30) + 15}%`;
        this.onOpen.emit(event.isActive.currentValue);
      }
    }
  }

  dragStart(event) {
    this.displayIframe = false;
    this.onDragStart.emit(event);
  }

  dragEnd(event: CdkDragEnd) {
    this.displayIframe = true;
    this.widgetCurrentPosition = event.source.getFreeDragPosition();
    this.onDragEnd.emit(event);
  }

  dragMoved(event) {
    this.onDragMoved.emit(event);
  }

  dragReleased(event) {
    this.onDragReleased.emit(event);
  }

  close() {
    this.isActive = false;
    this.onClose.emit(this.Id);
  }

  widgetClicked() {
    if (!this.isMaximized) {
      this.width = this.tempWidth;
      this.height = this.tempHeight;
      this.zIndex = WidgetComponent.zIndexCounter++;
      this.widgetPosition = this.widgetCurrentPosition;
      this.isDraggable = this.initDraggableValue;
    } else {
      this.xPosition = "0px";
      this.yPosition = "0px";
      this.widgetPosition = {x: 0, y: 41};
      this.zIndex = WidgetComponent.zIndexCounter++;
      this.isDraggable = false;
    }

    this.isMinimized = !this.isMinimized;
    this.onWidgetClicked.emit();
  }

  widgetMinimized() {
    if (!this.isMaximized) {
      this.tempWidth = this.width;
      this.tempHeight = this.height;
    }
    this.xPosition = undefined;
    this.yPosition = undefined;
    this.zIndex = WidgetComponent.zIndexCounter++;

    this.widgetPosition = this.widgetMinimizedPosition;
    this.isDraggable = false;
    this.isMinimized = !this.isMinimized;
    this.onMinimize.emit();
  }

  widgetMaximized(event) {
    if (this.isMaximized) {
      this.xPosition = undefined;
      this.yPosition = undefined;
      this.widgetPosition = this.widgetCurrentPosition;
      this.zIndex = WidgetComponent.zIndexCounter++;
      this.width = this.tempWidth;
      this.height = this.tempHeight;
      this.isDraggable = this.initDraggableValue;
    } else {
      this.xPosition = "0px";
      this.yPosition = "0px";
      this.widgetPosition = {x: 0, y: 41};
      this.zIndex = WidgetComponent.zIndexCounter++;
      this.tempWidth = this.width;
      this.tempHeight = this.height;
      this.isDraggable = false;
    }

    this.isMaximized = !this.isMaximized;
    this.onMaximize.emit(event);
  }

  onResized(event: ResizedEvent) {
    this.displayIframe = false;
    setTimeout(() => {
      this.displayIframe = true;
    }, 1000);
    this.width = event.newWidth;
    this.height = event.newHeight;
  }

  zIndexPlusOne() {
    if (!this.isMaximized) {
      this.zIndex = WidgetComponent.zIndexCounter++;
    }
  }
}
