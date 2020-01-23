package com.eurodyn.qlack.webdesktop.applications.management.controller;

import com.eurodyn.qlack.fuse.aaa.dto.UserGroupDTO;
import com.eurodyn.qlack.fuse.aaa.service.UserGroupService;
import com.eurodyn.qlack.webdesktop.applications.management.service.UserGroupManagementService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usergroup")
@RequiredArgsConstructor
public class UserGroupManagementController {

  @Autowired
  private final UserGroupService userGroupService;
  @Autowired
  private final UserGroupManagementService userGroupManagementService;

  @GetMapping
  public Page<UserGroupDTO> findAll() {
    return new PageImpl<>(userGroupService.listGroups());
  }

  @GetMapping(path = "{groupId}")
  public UserGroupDTO findGroupById(@PathVariable  String groupId ){
    return userGroupService.getGroupByID(groupId, false);
  }

  @PostMapping
  public void upload(@Valid @RequestBody UserGroupDTO userGroupDTO) {
    userGroupManagementService.upload(userGroupDTO);
  }

  @DeleteMapping("{userGroupId}")
  public void delete(@Valid @PathVariable String userGroupId) {
    userGroupService.deleteGroup(userGroupId);
  }
}
