import {BaseDto} from './base-dto';

export class UserGroupDto extends BaseDto {
  name: string;
  userGroupName: string;
  description: string;
  usersAdded: [];
  usersRemoved: [];
}
