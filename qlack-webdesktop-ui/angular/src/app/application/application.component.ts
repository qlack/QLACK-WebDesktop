import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from "@angular/core";
import {DomSanitizer} from '@angular/platform-browser';
import {CdkDragEnd} from '@angular/cdk/drag-drop';
import {ResizeEvent} from 'angular-resizable-element';

@Component({
  selector: 'app-application',
  templateUrl: './application.component.html',
  styleUrls: ['./application.component.scss']
})
export class ApplicationComponent implements OnChanges, OnInit {

  static zIndexCounter?: number = 3;
  @Output() onOpen = new EventEmitter();
  @Output() onClose = new EventEmitter();
  @Output() onMinimize = new EventEmitter();
  @Output() onMaximize = new EventEmitter();
  @Output() onDragStart = new EventEmitter();
  @Output() onDragEnd = new EventEmitter();
  @Output() onDragMoved = new EventEmitter();
  @Output() onDragReleased = new EventEmitter();
  @Output() onApplicationClicked = new EventEmitter();
  @Input() isActive: boolean = true;
  @Input() isDraggable: boolean = true;
  @Input() isMinimizable: boolean = true;
  @Input() isMaximizable: boolean = true;
  @Input() isClosable: boolean = true;
  @Input() isResizable: boolean;
  @Input() width?: number;
  @Input() height?: number;
  @Input() minHeight?: number;
  @Input() minWidth?: number;
  @Input() Id?: number;
  @Input() iconImageSrc?: string;
  @Input() proxyAppUrl?: string;
  @Input() showTitle: boolean = false;
  @Input() multipleInstances: boolean = false;
  @Input() applicationName?: string;
  @Input() iconSmallSrc?: string;

  // 42 is the height of the div of image icon
  applicationMinimizedPosition = {x: 0, y: -42};
  safeIconImageSrc;
  safeSmallIconImageSrc;
  safeAppUrl;
  isMinimized = false;
  isSmallScreen = false;
  isMaximized = false;
  applicationPosition = {x: 0, y: 150};
  applicationCurrentPosition = {x: 0, y: 150};
  xPosition?: string = '42px';
  yPosition?: string;
  tempWidth: number;
  tempHeight: number;
  displayIframe: boolean = true;
  zIndex?: number = 2;
  initDraggableValue: boolean;

  constructor(private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.safeIconImageSrc = this.sanitizer.bypassSecurityTrustResourceUrl(
        this.iconImageSrc);
    this.safeSmallIconImageSrc = this.sanitizer.bypassSecurityTrustResourceUrl(
        this.iconSmallSrc);
    this.safeAppUrl = this.sanitizer.bypassSecurityTrustResourceUrl(this.proxyAppUrl);
    this.initDraggableValue = this.isDraggable;
    this.zIndex = this.zIndex = ++ApplicationComponent.zIndexCounter;
  }

  ngOnChanges(event: SimpleChanges) {
    if (event.isActive) {
      if (event.isActive.currentValue == true) {
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
    this.applicationCurrentPosition = event.source.getFreeDragPosition();
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

  applicationClicked() {
    if (!this.isMaximized) {
      this.xPosition = '42px';
      this.width = this.tempWidth;
      this.height = this.tempHeight;
      this.zIndex = ApplicationComponent.zIndexCounter++;
      this.applicationPosition = this.applicationCurrentPosition;
      this.isDraggable = this.initDraggableValue;
    } else {
      this.xPosition = "0px";
      this.yPosition = "0px";
      this.applicationPosition = {x: 0, y: 41};
      this.zIndex = ApplicationComponent.zIndexCounter++;
      this.isDraggable = false;
    }

    this.isMinimized = !this.isMinimized;
    this.onApplicationClicked.emit();
  }

  applicationMinimized() {
    if (!this.isMaximized) {
      this.tempWidth = this.width;
      this.tempHeight = this.height;
    }
    this.xPosition = undefined;
    this.yPosition = undefined;
    this.zIndex = ApplicationComponent.zIndexCounter++;

    this.applicationPosition = this.applicationMinimizedPosition;
    this.isDraggable = false;
    this.isMinimized = !this.isMinimized;
    this.onMinimize.emit();
  }

  applicationMaximized(event) {
    if (this.isMaximized) {
      this.xPosition = '42px';
      this.yPosition = undefined;
      this.applicationPosition = this.applicationCurrentPosition;
      this.zIndex = ApplicationComponent.zIndexCounter++;
      this.width = this.tempWidth;
      this.height = this.tempHeight;
      this.isDraggable = this.initDraggableValue;
    } else {
      this.xPosition = "0px";
      this.yPosition = "0px";
      this.applicationPosition = {x: 0, y: 41};
      this.zIndex = ApplicationComponent.zIndexCounter++;
      this.tempWidth = this.width;
      this.tempHeight = this.height;
      this.isDraggable = false;
    }

    this.isMaximized = !this.isMaximized;
    this.onMaximize.emit(event);
  }

  zIndexPlusOne() {
    if (!this.isMaximized) {
      this.zIndex = ApplicationComponent.zIndexCounter++;
    }
  }

  onResizeEnd($event: ResizeEvent) {
    this.displayIframe = true;
  }

  onResizeStart($event: ResizeEvent) {
    this.displayIframe = false;

  }

  onResize($event: ResizeEvent) {
    this.width = $event.rectangle.width;
    this.height = $event.rectangle.height;
  }

}
