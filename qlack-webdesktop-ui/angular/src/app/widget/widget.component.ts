import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges
} from "@angular/core";
import {DomSanitizer} from '@angular/platform-browser';
import {CdkDragEnd} from '@angular/cdk/drag-drop';
import {ResizeEvent} from 'angular-resizable-element';

@Component({
  selector: 'app-widget',
  templateUrl: './widget.component.html',
  styleUrls: ['./widget.component.scss']
})
export class WidgetComponent implements OnChanges, OnInit {

  static zIndexCounter?: number = 3;
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
  @Input() isResizable: boolean ;
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
  widgetMinimizedPosition = {x: 0, y: -42};
  safeIconImageSrc;
  safeSmallIconImageSrc;
  safeAppUrl;
  isMinimized = false;
  isSmallScreen = false;
  isMaximized = false;
  widgetPosition = {x: 0, y: 150};
  widgetCurrentPosition = {x: 0, y: 150};
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
    this.safeIconImageSrc = this.iconImageSrc && this.sanitizer.bypassSecurityTrustResourceUrl(
        this.iconImageSrc);
    this.safeSmallIconImageSrc = this.iconSmallSrc && this.sanitizer.bypassSecurityTrustResourceUrl(
        this.iconSmallSrc);
    this.safeAppUrl = this.proxyAppUrl && this.sanitizer.bypassSecurityTrustResourceUrl(this.proxyAppUrl);
    this.initDraggableValue = this.isDraggable;
    this.zIndex = this.zIndex = ++WidgetComponent.zIndexCounter;
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
      this.xPosition =  '42px';
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
      this.xPosition =  '42px';
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

  zIndexPlusOne() {
    if (!this.isMaximized) {
      this.zIndex = WidgetComponent.zIndexCounter++;
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
