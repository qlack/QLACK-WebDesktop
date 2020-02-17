package com.eurodyn.qlack.webdesktop.applications.management.dto;

import com.eurodyn.qlack.fuse.aaa.dto.UserGroupDTO;
import java.util.Collection;

public class UserGroupManagementDTO extends UserGroupDTO {

  private Collection<String> usersAdded;
  private Collection<String> usersRemoved;

  public Collection<String> getUsersAdded() {
    return usersAdded;
  }

  public void setUsersAdded(Collection<String> usersAdded) {
    this.usersAdded = usersAdded;
  }

  public Collection<String> getUsersRemoved() {
    return usersRemoved;
  }

  public void setUsersRemoved(Collection<String> usersRemoved) {
    this.usersRemoved = usersRemoved;
  }

  public UserGroupManagementDTO() {
  }
}