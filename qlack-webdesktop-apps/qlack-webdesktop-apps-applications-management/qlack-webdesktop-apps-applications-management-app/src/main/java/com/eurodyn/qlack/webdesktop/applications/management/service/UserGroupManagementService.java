package com.eurodyn.qlack.webdesktop.applications.management.service;

import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserGroupDTO;
import com.eurodyn.qlack.fuse.aaa.service.UserGroupService;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.webdesktop.applications.management.dto.UserGroupManagementDTO;
import com.eurodyn.qlack.webdesktop.common.service.ProfileManagerService;
import com.querydsl.core.types.Predicate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGroupManagementService {

  @Autowired
  UserGroupService userGroupService;
  @Autowired
  UserService userService;
  @Autowired
  ProfileManagerService profileManagerService;

  /**
   * Retrieves all user's groups based on predicate and pagination information.
   *
   * @param predicate Boolean typed expressions to build and search for.
   * @param pageable pagination information.
   * @return a responded object of type UserGroupDTO.
   */
  public ResponseEntity<Page<UserGroupDTO>> findAll(Predicate predicate,
      Pageable pageable) {
    UserDTO userId = new UserDTO();
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (("sso").equalsIgnoreCase(profileManagerService.getActiveProfile())
        && principal instanceof DefaultOAuth2User){
      String userName = ((DefaultOAuth2User) principal).getName();
      userId = userService.getUserByName(userName);
    }
    if (userId.isSuperadmin()) {
      Page<UserGroupDTO> userGroupDTOS = userGroupService.findAll(predicate, pageable);
      return ResponseEntity.ok(userGroupDTOS);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Creates or updates a userGroupManagementDTO to database.
   *
   * @param userGroupManagementDTO the object to be created/updated.
   * @return the response entity.
   */
  public ResponseEntity upload(UserGroupManagementDTO userGroupManagementDTO) {
    if (isNewGroup(userGroupManagementDTO) && existsByGroupName(userGroupManagementDTO.getName())) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("alreadyExistsCode");
    }

    if (isNewGroup(userGroupManagementDTO)) {
      userGroupManagementDTO.setId(null);
      userGroupService.createGroup(userGroupManagementDTO);
    } else {
      userGroupService.updateGroup(userGroupManagementDTO);
    }

    UserGroupDTO newUserGroupDTO = userGroupService
        .getGroupByName(userGroupManagementDTO.getName(), true);
    if (CollectionUtils.isNotEmpty(userGroupManagementDTO.getUsersAdded())) {
      userGroupService.addUsers(userGroupManagementDTO.getUsersAdded(), newUserGroupDTO.getId());
    }
    if (CollectionUtils.isNotEmpty(userGroupManagementDTO.getUsersRemoved())) {
      userGroupService
          .removeUsers(userGroupManagementDTO.getUsersRemoved(), newUserGroupDTO.getId());
    }

    return ResponseEntity.status(HttpStatus.OK).body(userGroupManagementDTO);
  }

  /**
   * Retrieves a list with all userGroups.
   *
   * @return if there are any userGroups return them in a list, else return null.
   */
  public ResponseEntity findAllGroups() {
    List<UserGroupDTO> groupDTO = userGroupService.listGroups();
    return groupDTO != null ? ResponseEntity.status(HttpStatus.OK).body(groupDTO)
        : ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
  }

  private boolean isNewGroup(UserGroupManagementDTO userGroupManagementDTO) {
    return userGroupManagementDTO.getId().equals("0");
  }

  private boolean existsByGroupName(String groupName) {
    return userGroupService.getGroupByName(groupName, true) != null;
  }
}
