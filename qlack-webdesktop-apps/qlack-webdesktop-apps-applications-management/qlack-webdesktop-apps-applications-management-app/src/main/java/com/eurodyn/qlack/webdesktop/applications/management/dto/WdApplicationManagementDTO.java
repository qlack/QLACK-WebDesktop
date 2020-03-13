package com.eurodyn.qlack.webdesktop.applications.management.dto;

import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserGroupDTO;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

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
