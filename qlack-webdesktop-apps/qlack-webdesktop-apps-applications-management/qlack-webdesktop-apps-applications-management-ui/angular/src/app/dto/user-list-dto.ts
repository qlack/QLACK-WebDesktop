import {User} from './user';
import {Deserializable} from './deserializable';

export class UserList implements Deserializable {
  content: User[];
  totalElements: number;

  deserialize(input: any): this {
    Object.assign(this, input);
    this.content = input.content.map(content => new User().deserialize(content));
    return this;
  }
}
