package com.eurodyn.qlack.webdesktop.applications.management.service;

import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserGroupDTO;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.webdesktop.applications.management.dto.UserManagementDTO;
import com.eurodyn.qlack.webdesktop.common.service.ProfileManagerService;
import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
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
public class UserManagementService {

  @Autowired
  private final UserService userService;
  @Autowired
  private WdApplicationService wdApplicationService;
  @Autowired
  ProfileManagerService profileManagerService;

  /**
   * Retrieves all users based on predicate and pagination information.
   *
   * @param predicate Boolean typed expressions to build and search for.
   * @param pageable pagination information.
   * @return a responded object of type UserDTO.
   */
  public ResponseEntity<Page<UserDTO>> findAll(Predicate predicate,
      Pageable pageable) {
    UserDTO userId = new UserDTO();
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (("sso").equalsIgnoreCase(profileManagerService.getActiveProfile())
        && principal instanceof DefaultOAuth2User){
      String userName = ((DefaultOAuth2User) principal).getName();
      userId = userService.getUserByName(userName);
    }
    if (userId.isSuperadmin()) {
      Page<UserDTO> userDTOS = userService.findAll(predicate, pageable);
      return ResponseEntity.ok(userDTOS);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Updates a user's userGroups. That means add or remove userGroups.
   *
   * @param userManagementDTO the object to be updated.
   * @return the response entity.
   */
  public ResponseEntity saveAll(UserManagementDTO userManagementDTO) {
    if (CollectionUtils.isNotEmpty(userManagementDTO.getGroupsAdded())) {
      userService.addUserGroups(userManagementDTO.getGroupsAdded(), userManagementDTO.getId());
    }
    if (CollectionUtils.isNotEmpty(userManagementDTO.getGroupsRemoved())) {
      userService.removeUserGroups(userManagementDTO.getGroupsRemoved(), userManagementDTO.getId());
    }
    return ResponseEntity.status(HttpStatus.OK).body(userManagementDTO);
  }

  /**
   * Retrieves a user by name.
   *
   * @param name the name to search for.
   * @return if the user exists return the user, else return null.
   */
  public ResponseEntity findUserByName(String name) {
    UserDTO userDTO = userService.getUserByName(name);
    return userDTO != null ? ResponseEntity.status(HttpStatus.OK).body(userDTO)
        : ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
  }

  /**
   * Retrieves the list of userGroups that belong to a user.
   *
   * @param userId the user's id to retrieve the userGroups.
   * @return a list containing all the userGroups that belong to a user.
   */
  public List<UserGroupDTO> findUserGroupsIds(String userId) {
    return wdApplicationService.findUserGroupsIds(userId);
  }
}
