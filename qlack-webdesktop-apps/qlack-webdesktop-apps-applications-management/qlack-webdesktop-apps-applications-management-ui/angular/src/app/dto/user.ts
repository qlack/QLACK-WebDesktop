import {BaseDto} from './base-dto';
import {UserAttribute} from './user-attribute-dto';
import {Deserializable} from './deserializable';

export class User extends BaseDto implements Deserializable {
  username: string;
  status: number;
  userAttributes: UserAttribute[];

  deserialize(input: any): this {
    Object.assign(this, input);
    this.userAttributes = input.userAttributes.map(userAttribute => new UserAttribute().deserialize(userAttribute));
    return this;
  }

  findAttribute(name: string): string {
    const userAttribute: UserAttribute = this.userAttributes.find(userAttribute => userAttribute.name === name);
    if (userAttribute) {
      return userAttribute.data
    } else {
      return "";
    }
  }

  findImage(): Blob {
    const image: UserAttribute = this.userAttributes.find(userAttribute => userAttribute.name === "profileImage");
    if (image) {
      return image.bindata
    } else {
      return null;
    }
  }
}
