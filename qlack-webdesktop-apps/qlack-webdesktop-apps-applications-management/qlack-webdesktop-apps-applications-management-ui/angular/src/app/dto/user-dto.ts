import {BaseDto} from './base-dto';

export class UserDto extends BaseDto {
  email: string;
  password: string;
  username: string;
  firstname: string;
  lastname: string;
  status: number;
  roles: string;
  profilepic: string;
  userAttributes: [{
    name: string;
    data: string;
  }
  ];
}
