import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'sortBy'
})
export class SortByPipe implements PipeTransform {

  transform(collection: Array<any>): Array<any>{
    if (!collection) {
      return null;
    }

    return collection.sort((a, b) => {
          if (a.titleKey > b.titleKey) return 1
          else if (a.titleKey < b.titleKey) return -1
          else return 0
        });
  }
}
