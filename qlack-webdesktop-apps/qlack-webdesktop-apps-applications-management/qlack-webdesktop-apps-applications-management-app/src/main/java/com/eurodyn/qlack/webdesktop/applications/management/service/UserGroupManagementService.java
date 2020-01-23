package com.eurodyn.qlack.webdesktop.applications.management.service;

import com.eurodyn.qlack.common.exception.QAlreadyExistsException;
import com.eurodyn.qlack.fuse.aaa.dto.UserGroupDTO;
import com.eurodyn.qlack.fuse.aaa.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGroupManagementService {

  @Autowired
  UserGroupService userGroupService;

  public void upload(UserGroupDTO userGroupDTO) {
    if (isNewGroup(userGroupDTO) && existsByGroupName(userGroupDTO.getName())) {
      throw new QAlreadyExistsException("Group name is already in use.");
    }

    if (isNewGroup(userGroupDTO)) {
      userGroupDTO.setId(null);
      userGroupService.createGroup(userGroupDTO);
    } else {
      userGroupService.updateGroup(userGroupDTO);
    }
  }

  private boolean isNewGroup(UserGroupDTO userGroupDTO) {
    return userGroupDTO.getId().equals("0");
  }

  private boolean existsByGroupName(String groupName){
    return userGroupService.getGroupByName(groupName, false) != null ? true : false;
  }
}
