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
  @Input() width?: number;
  @Input() height?: number;
  @Input() minHeight?: number;
  @Input() minWidth?: number;
  @Input() iconImageSrc?: string;
  @Input() appUrl?: string;



   widgetPosition = {x: 500, y: 400};
  // 42 is the height of the div of image icon
   widgetMinimizedPosition = {x: 0, y: -42};
  safeIconImageSrc;
  safeAppUrl;
  isMinimized = false;
  isSmallScreen = false;
  isMaximized = false;
  widgetCurrentPosition = {x: 500, y: 400};
  xPosition?: string;
  yPosition?: string;
  tempWidth:number;
  tempHeight:number;
  widgetWidthPercent?: string;
  widgetHeightPercent?: string;
  displayIframe: boolean = false;
  zIndex?: number = 2;
  static zIndexCounter?: number = 3;

  constructor(breakpointObserver: BreakpointObserver,
              private sanitizer: DomSanitizer, ) {
    breakpointObserver.observe([
      '(max-width: 200px)',
      Breakpoints.XSmall,
      Breakpoints.Web
    ]).subscribe(result => {
      if (result.matches) {
        if (result.breakpoints['(max-width: 200px)']) {
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

       // this.yPosition = this.yPosition || `${Math.floor(Math.random() * 40) + 20}%`;
       // this.xPosition = this.xPosition || `${Math.floor(Math.random() * 30) + 15}%`;
        this.onOpen.emit(event.isActive.currentValue);
      }
    }
  }

  dragStart(event) {
    this.zIndex=WidgetComponent.zIndexCounter++;
    this.displayIframe=false;
    this.onDragStart.emit(event);
  }

  dragEnd(event: CdkDragEnd) {
    this.displayIframe=true;
    this.widgetCurrentPosition=event.source.getFreeDragPosition();
    this.onDragEnd.emit(event);
  }

  dragMoved(event) {
    this.onDragMoved.emit(event);
  }


  dragReleased(event) {
    this.onDragReleased.emit(event);
  }

  close(event) {
    this.isActive = false;
    this.onClose.emit(event);
  }

  widgetClicked(event) {
    if(!this.isMaximized){
      this.width= this.tempWidth ;
      this.height= this.tempHeight ;
      this.zIndex=WidgetComponent.zIndexCounter++;
      this.widgetPosition=this.widgetCurrentPosition;


    }
    else{

      this.xPosition="0px";

      this.yPosition="0px";
      this.widgetPosition = {x:  0 , y: 60};
      this.zIndex=WidgetComponent.zIndexCounter++;
      this.widgetWidthPercent="198";
      this.widgetHeightPercent="100";

    }

    this.isDraggable=true;
    this.isMinimized = !this.isMinimized;
    this.onWidgetClicked.emit(event);
  }


  widgetMinimized(event){
    if(!this.isMaximized){
      this.tempWidth=this.width;
      this.tempHeight=this.height;
    }
    this.xPosition=undefined;
    this.yPosition=undefined;
    this.zIndex=WidgetComponent.zIndexCounter++;


    this.widgetPosition= this.widgetMinimizedPosition;
    this.isDraggable=false;
    this.isMinimized = !this.isMinimized;
    this.onMinimize.emit(event);
  }

  widgetMaximized(event){


    if(this.isMaximized){
      this.xPosition=undefined;

      this.yPosition=undefined;
      this.widgetPosition = this.widgetCurrentPosition;
      this.zIndex=WidgetComponent.zIndexCounter++;
      this.width= this.tempWidth ;
      this.height= this.tempHeight ;

    }else{
      this.xPosition="0px";

      this.yPosition="0px";
      this.widgetPosition = {x:  0 , y: 60};
      this.zIndex=WidgetComponent.zIndexCounter++;
      this.tempWidth=this.width;
      this.tempHeight=this.height;
      this.widgetWidthPercent="198";
      this.widgetHeightPercent="100";


    }


    this.isMaximized = !this.isMaximized;
    this.onMaximize.emit(event);
  }


  //needs better implementation
  onResized(event: ResizedEvent) {
    this.displayIframe = false;
    setTimeout(()=>{
      this.displayIframe = true;
    }, 1000);
    this.width = event.newWidth;
    this.height = event.newHeight;

  }


}


