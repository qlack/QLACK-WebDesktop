package com.eurodyn.qlack.webdesktop.applications.management.controller;

import com.eurodyn.qlack.fuse.aaa.criteria.UserSearchCriteria;
import com.eurodyn.qlack.fuse.aaa.criteria.UserSearchCriteria.UserSearchCriteriaBuilder;
import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserGroupDTO;
import com.eurodyn.qlack.fuse.aaa.model.User;
import com.eurodyn.qlack.fuse.aaa.service.UserGroupService;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.util.querydsl.EmptyPredicateCheck;
import com.eurodyn.qlack.webdesktop.applications.management.dto.UserManagementDTO;
import com.eurodyn.qlack.webdesktop.applications.management.service.UserManagementService;
import com.querydsl.core.types.Predicate;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserManagementController {

  @Autowired
  private final UserService userService;
  @Autowired
  private final UserGroupService userGroupService;

  @Autowired
  private final UserManagementService userManagementService;

  /**
   * Retrieves all users based on predicate and pagination information.
   *
   * @param predicate Boolean typed expressions to build and search for.
   * @param pageable pagination information.
   * @return a responded object of type UserDTO.
   */
  @GetMapping
  @EmptyPredicateCheck
  public ResponseEntity<Page<UserDTO>> findAll(@QuerydslPredicate(root = User.class) Predicate predicate,
      Pageable pageable) {
    return userManagementService.findAll(predicate, pageable);
  }

  /**
   * Retrieves all userGroups that belongs to a user.
   *
   * @param userId the user id to search for userGroups.
   * @return a page object containing users.
   */
  @GetMapping(path = "/groups/{userId}")
  public Page findGroupsByUserId(@PathVariable String userId) {
    return new PageImpl(userManagementService.findUserGroupsIds(userId));
  }

  /**
   * Search for users based on username string.
   *
   * @param term the string to search for among users.
   * @return one or more objects that contains the term.
   */
  @GetMapping(path = "/search/{term}")
  public Page findUsersByTerm(@PathVariable String term) {
    UserSearchCriteria userSearchCriteria = UserSearchCriteriaBuilder
        .createCriteria().withUsernameLike("%" + term + "%").build();
    return new PageImpl<>((List<UserDTO>) userService.findUsers(userSearchCriteria));
  }

  /**
   * Retrieves a single user based on user id.
   *
   * @param userId the user id to search for.
   * @return the userDTO that has been found.
   */
  @GetMapping("{userId}")
  public UserDTO findUserById(@Valid @PathVariable String userId) {
    return userService.getUserById(userId);
  }

  /**
   * Retrieves a single user based on username.
   *
   * @param name the user name to search for.
   * @return the response entity.
   */
  @GetMapping("/username/{name}")
  public ResponseEntity findUserByName(@PathVariable String name) {
    return userManagementService.findUserByName(name);
  }

  /**
   * Retrieves a single userGroup based on group name.
   *
   * @param name the userGroup name to search for.
   * @return the UserGroupDTO that has been found.
   */
  @GetMapping("/groupname/{name}")
  public UserGroupDTO findGroupByName(@PathVariable String name) {
    return userGroupService.getGroupByName(name, true);
  }

  /**
   * Updates user's groups.
   *
   * @param userManagementDTO the object to be updated.
   * @return a response entity.
   */
  @PostMapping
  public ResponseEntity upload(@Valid @RequestBody UserManagementDTO userManagementDTO) {
    return userManagementService.saveAll(userManagementDTO);
  }
}
