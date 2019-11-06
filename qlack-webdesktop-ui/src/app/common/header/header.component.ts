import { Component, ViewChild, ViewContainerRef, ComponentFactoryResolver, ComponentRef, ComponentFactory} from '@angular/core';
import {Widget} from "../../widget";
import {WidgetComponent} from "../../widget/widget.component";


@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent {


    activeWidgetComponents: any[] = new Array() ;
    tempWidgetComponent: any;
    static widgetId:number=0;

    @ViewChild('widgetcontainer', {  static: true ,read: ViewContainerRef }) entry: ViewContainerRef;

    constructor(private resolver: ComponentFactoryResolver) {
    }


    initWidget(widget: Widget) {



            if (this.activeWidgetIndex(widget)!= -1) {
                const index=this.activeWidgetIndex(widget);
                if (this.activeWidgetComponents[index].instance.multipleInstances) {

                    this.createWidget(widget);

                }else if(this.activeWidgetComponents[index].instance.isMinimized){
                    this.activeWidgetComponents[index].instance.widgetMinimized();
                }
            }
            else {
                 this.createWidget(widget);
            }

    }

    destroyWidget(id){
        console.log(id+" id parameter");

        for(let i=0; i<this.activeWidgetComponents.length; i++){

            console.log(this.activeWidgetComponents[i]+"  this.activeWidgetComponents[i]");
            console.log( this.activeWidgetComponents[i].instance.Id +"  this.activeWidgetComponents[i].instance.widgetID");
           if (this.activeWidgetComponents[i].instance.Id === id) {
               this.activeWidgetComponents[i].destroy();
               this.activeWidgetComponents.splice(i, 1);


           }
        }


    }

    activeWidgetIndex(widget: Widget) {
        for (let i = 0; i < this.activeWidgetComponents.length; i++) {

            if (this.activeWidgetComponents[i].instance.appUrl == widget.appIndex) {
                return i;

            }


        }
        return -1;
    }


    createWidget(widget: Widget){
        const factory = this.resolver.resolveComponentFactory(WidgetComponent);
        this.tempWidgetComponent = this.entry.createComponent(factory);
        console.log(this.activeWidgetComponents);
        this.tempWidgetComponent.instance.isActive = true;
        this.tempWidgetComponent.instance.isDraggable = widget.draggable;
        this.tempWidgetComponent.instance.isMinimizable = widget.minimizable;
        this.tempWidgetComponent.instance.isMaximizable = widget.maximizable;
        this.tempWidgetComponent.instance.isClosable = widget.closable;
        this.tempWidgetComponent.instance.isResizable = widget.resizable;
        this.tempWidgetComponent.instance.height = widget.height;
        this.tempWidgetComponent.instance.width = widget.width;
        this.tempWidgetComponent.instance.minWidth = widget.minWidth;
        this.tempWidgetComponent.instance.minHeight = widget.minHeight;
        this.tempWidgetComponent.instance.showTitle = widget.showTitle;
        this.tempWidgetComponent.instance.multipleInstances = widget.multipleInstances;
        this.tempWidgetComponent.instance.iconImageSrc = widget.icon;
        this.tempWidgetComponent.instance.title = widget.titleKey;
        this.tempWidgetComponent.instance.appUrl = widget.appIndex;
        this.tempWidgetComponent.instance.Id = HeaderComponent.widgetId++;
        this.tempWidgetComponent.instance.onClose.subscribe(id => {
            this.destroyWidget(id);
        });

        this.activeWidgetComponents.push(this.tempWidgetComponent);

    }

}
