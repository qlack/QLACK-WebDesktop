import {Directive, Input} from '@angular/core';

@Directive({
  selector: 'img[default]',
  host: {
    '(error)': 'updateUrl()',
    '[src]': 'src'
  }
})
export class ImagePreloadDirective {
  @Input() src:string;
  @Input() default:string;

  constructor() { }

  updateUrl() {
    this.src = this.default;
  }
}
