import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges, OnInit } from "@angular/core";
import { DomSanitizer } from '@angular/platform-browser';
import { BreakpointObserver, Breakpoints } from "@angular/cdk/layout";
import { CdkDragEnd } from '@angular/cdk/drag-drop';
import { ResizedEvent } from 'angular-resize-event';


@Component({
  selector: 'app-widget',
  templateUrl: './widget.component.html',
  styleUrls: ['./widget.component.scss']
})
export class WidgetComponent implements OnChanges,OnInit {

  @Output() onOpen = new EventEmitter();
  @Output() onClose = new EventEmitter();
  @Output() onMinimize = new EventEmitter();
  @Output() onMaximize = new EventEmitter();
  @Output() onDragStart = new EventEmitter();
  @Output() onDragEnd = new EventEmitter();
  @Output() onDragMoved = new EventEmitter();
  @Output() onDragReleased = new EventEmitter();
  @Output() onWidgetClicked = new EventEmitter();


  @Input() isActive: boolean = false;
  @Input() isDraggable: boolean = true;
  @Input() isMinimizable: boolean = true;
  @Input() isMaximizable: boolean = false;
  @Input() isClosable: boolean = false;
  @Input() isResizable: boolean = true;
  @Input() title: string = "Title";
  @Input() xPosition?: string;
  @Input() yPosition?: string;
  @Input() width?: number;
  @Input() height?: number;
  @Input() minHeight?: string;
  @Input() zIndex?: number;
  @Input() iconImageSrc?: string;
  @Input() appUrl?: string;
  @Input() widgetWidthPercent?: string;
  @Input() widgetHeightPercent?: string;
  @Input() widgetPosition = {x: 0, y: 0};

  safeIconImageSrc;
  safeAppUrl;
  isMinimized = false;
  isSmallScreen = false;
  isMaximized = false;
  widgetCurrentPosition;
  tempWidth:number;
  tempHeight:number;


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
    this.safeAppUrl = this.appUrl && this.sanitizer.bypassSecurityTrustResourceUrl(this.appUrl)
  }

  ngOnChanges(event: SimpleChanges) {
    if (event.isActive) {
      let widgets: HTMLCollectionOf<Element> = document.getElementsByClassName("widget");
      if (event.isActive.currentValue == true) {
        this.zIndex = this.zIndex || widgets.length;
        this.yPosition = this.yPosition || `${Math.floor(Math.random() * 40) + 20}%`;
        this.xPosition = this.xPosition || `${Math.floor(Math.random() * 30) + 15}%`;
        this.onOpen.emit(event.isActive.currentValue);
      }
    }
  }

  dragStart(event) {
    this.onDragStart.emit(event);
  }

  dragEnd(event: CdkDragEnd) {
    this.widgetCurrentPosition=event.source.getFreeDragPosition();
    this.onDragEnd.emit(event);
  }

  dragMoved(event) {
    this.onDragMoved.emit(event);
  }

  widgetClicked(event) {
    this.isMinimized = !this.isMinimized;
    this.onWidgetClicked.emit(event);
  }


  dragReleased(event) {
    this.onDragReleased.emit(event);
  }

  close(event) {
    this.isActive = false;
    this.onClose.emit(event);
  }

  widgetMinimized(event){
    this.isMinimized = !this.isMinimized;
    this.onMinimize.emit(event);
  }

  widgetMaximized(event){


    if(this.isMaximized){

      this.widgetPosition = this.widgetCurrentPosition;
      this.zIndex=2;
      this.widgetWidthPercent= this.tempWidth.toString() ;
      this.widgetHeightPercent= this.tempHeight.toString() ;
    }else{
      this.widgetPosition = {x:  -parseInt(this.yPosition) , y: -parseInt(this.xPosition)};
      this.zIndex=9
      this.tempWidth=this.width;
      this.tempHeight=this.height;
      //TODO width must have 100 or 98 value
      this.widgetWidthPercent="198";
      this.widgetHeightPercent="100";


    }


    this.isMaximized = !this.isMaximized;
    this.onMaximize.emit(event);
  }


  onResized(event: ResizedEvent) {

    this.width = event.newWidth;
    this.height = event.newHeight;
  }


}


