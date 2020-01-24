import {BaseDto} from './base-dto';

export class LanguageDto extends BaseDto {
  name: string;
  locale: string;
  active: boolean;
}
