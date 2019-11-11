import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'sortBy'
})
export class SortByPipe implements PipeTransform {

  transform(collection: Array<any>, property: string): Array<any>{
    if (!collection) {
      return null;
    }

    return collection.sort((a, b) => {
          if (a[property] > b[property]) return 1
          else if (a[property] < b[property]) return -1
          else return 0
        });
  }
}
