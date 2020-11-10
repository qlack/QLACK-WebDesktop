import {Deserializable} from './deserializable';
import {UserList} from './user-list-dto';

export class ApplicationDto implements Deserializable {
  id: string
  title: string
  description: string
  version: string
  appUrl: string
  appPath: string
  proxyAppPath: string
  icon: string
  iconSmall: string
  multipleInstances: boolean
  width: number
  minWidth: number
  height: number
  minHeight: number
  resizable: boolean
  minimizable: boolean
  maximizable: boolean
  closable: boolean
  draggable: boolean
  addedOn: number
  lastDeployedOn: number
  system: boolean
  showTitle: boolean
  groupName: string
  applicationName: string
  checksum: string
  active: boolean
  restrictAccess: boolean
  editedByUI: boolean
  usersAdded: [];
  usersRemoved: [];
  groupsAdded: [];
  groupsRemoved: [];
  details: [];
  users: UserList;
  userGroups: [];

  deserialize(input: any): this {
    return Object.assign(this, input);
  }
}
