package com.eurodyn.qlack.webdesktop.common.dto;

import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserGroupDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class WdApplicationManagementDTO extends WdApplicationDTO {

  private Collection<String> usersAdded;
  private Collection<String> usersRemoved;
  private Collection<String> groupsAdded;
  private Collection<String> groupsRemoved;

  private Iterable<UserDTO> users;
  private Iterable<UserGroupDTO> userGroups;

  private WdApplicationDTO details;

}
