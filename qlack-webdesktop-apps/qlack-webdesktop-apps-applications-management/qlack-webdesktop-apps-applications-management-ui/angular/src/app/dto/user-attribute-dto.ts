import {Deserializable} from './deserializable';

export class UserAttribute implements Deserializable {
  name: string;
  data: string;
  bindata: Blob;

  deserialize(input: any): this {
    return Object.assign(this, input);
  }
}
