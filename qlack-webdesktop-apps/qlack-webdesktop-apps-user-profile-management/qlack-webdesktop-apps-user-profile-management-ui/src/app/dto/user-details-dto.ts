import {BaseDto} from './base-dto';

export class UserDetailsDto extends BaseDto  {

  defaultLanguage: string;
  firstName: string;
  lastName: string;
  profileImage: any[];
  backgroundImage: any[];
}
