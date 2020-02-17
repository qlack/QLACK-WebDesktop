package com.eurodyn.qlack.webdesktop.applications.management.dto;

import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import java.util.Collection;

public class UserManagementDTO extends UserDTO {

  private Collection<String> groupsAdded;
  private Collection<String> groupsRemoved;

  public Collection<String> getGroupsAdded() {
    return groupsAdded;
  }

  public void setGroupsAdded(Collection<String> groupsAdded) {
    this.groupsAdded = groupsAdded;
  }

  public Collection<String> getGroupsRemoved() {
    return groupsRemoved;
  }

  public void setGroupsRemoved(Collection<String> groupsRemoved) {
    this.groupsRemoved = groupsRemoved;
  }
}
