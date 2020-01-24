import {BaseDto} from './base-dto';

export class KeyDto extends BaseDto {
  groupName: string;
  groupId: string;
  name: string;
  translations = {};
}
