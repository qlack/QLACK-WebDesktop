package com.eurodyn.qlack.webdesktop.applications.management.util;

import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserGroupDTO;
import com.eurodyn.qlack.webdesktop.applications.management.dto.UserGroupManagementDTO;
import com.eurodyn.qlack.webdesktop.applications.management.dto.UserManagementDTO;
import java.util.ArrayList;
import java.util.Collection;

public class InitTestValues {

  public UserGroupManagementDTO createUserGroupManagementDto() {
    UserGroupManagementDTO userGroupManagementDTO = new UserGroupManagementDTO();
    userGroupManagementDTO.setId("dca76ec3-0423-4a17-8287-afd311697dbf");
    userGroupManagementDTO.setName("groupName");
    return userGroupManagementDTO;
  }

  public UserManagementDTO createUserManagementDto() {
    UserManagementDTO userManagementDTO = new UserManagementDTO();
    userManagementDTO.setId("dca76ec3-0423-4a17-8287-afd311697dbb");
    userManagementDTO.setUsername("UserName");
    return userManagementDTO;
  }

  public Collection<UserDTO> createUsers() {
    Collection<UserDTO> userDTOS = new ArrayList<>();
    userDTOS.add(createUserDTO());
    return userDTOS;
  }

  public Collection<String> createUsersStringList() {
    Collection<String> usersIds = new ArrayList<>();
    usersIds.add(createUserDTO().getId());
    return usersIds;
  }

  public Collection<String> createUserGroupStringList() {
    Collection<String> userGroupIds = new ArrayList<>();
    userGroupIds.add(createUserGroupDto().getId());
    return userGroupIds;
  }

  public UserDTO createUserDTO() {
    UserDTO userDTO = new UserDTO();
    userDTO.setId("57d30f8d-cf0c-4742-9893-09e2aa08c255");
    userDTO.setUsername("Default User");
    userDTO.setPassword("thisisaverysecurepassword");
    userDTO.setStatus((byte) 1);
    userDTO.setSuperadmin(true);
    userDTO.setExternal(false);
    return userDTO;
  }

  public UserGroupDTO createUserGroupDto() {
    UserGroupDTO userGroupDTO = new UserGroupDTO();
    userGroupDTO.setId("57d30f8d-cf0c-4742-9893-09e2aa08c200");
    userGroupDTO.setName("Default UserGroup Name");
    userGroupDTO.setDescription("This a test userGroup");
    return userGroupDTO;
  }
}
