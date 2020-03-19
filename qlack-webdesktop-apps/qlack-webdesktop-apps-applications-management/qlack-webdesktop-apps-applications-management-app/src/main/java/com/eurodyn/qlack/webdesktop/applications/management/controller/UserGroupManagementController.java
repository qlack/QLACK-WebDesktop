package com.eurodyn.qlack.webdesktop.applications.management.controller;

import com.eurodyn.qlack.fuse.aaa.criteria.UserGroupSearchCriteria;
import com.eurodyn.qlack.fuse.aaa.criteria.UserGroupSearchCriteria.UserGroupSearchCriteriaBuilder;
import com.eurodyn.qlack.fuse.aaa.criteria.UserSearchCriteria;
import com.eurodyn.qlack.fuse.aaa.criteria.UserSearchCriteria.UserSearchCriteriaBuilder;
import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserGroupDTO;
import com.eurodyn.qlack.fuse.aaa.model.UserGroup;
import com.eurodyn.qlack.fuse.aaa.service.UserGroupService;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.util.querydsl.EmptyPredicateCheck;
import com.eurodyn.qlack.webdesktop.applications.management.dto.UserGroupManagementDTO;
import com.eurodyn.qlack.webdesktop.applications.management.service.UserGroupManagementService;
import com.querydsl.core.types.Predicate;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;


@Validated
@RestController
@RequestMapping("/api/usergroup")
@RequiredArgsConstructor
public class UserGroupManagementController {

  @Autowired
  private final UserGroupService userGroupService;
  @Autowired
  private final UserService userService;
  @Autowired
  private final UserGroupManagementService userGroupManagementService;

  /**
   * Retrieves all user's groups based on predicate and pagination information.
   *
   * @param predicate Boolean typed expressions to build and search for.
   * @param pageable pagination information.
   * @return a responded object of type UserGroupDTO.
   */
  @GetMapping
  @EmptyPredicateCheck
  public ResponseEntity<Page<UserGroupDTO>> findAll(@QuerydslPredicate(root = UserGroup.class) Predicate predicate,
      Pageable pageable) {
    return userGroupManagementService.findAll(predicate, pageable);
  }

  /**
   * Retrieves all users that are members of a group.
   *
   * @param groupId the groud id to search for users.
   * @return a page object of type UserGroupDTO containing group users.
   */
  @GetMapping(path = "/users/{groupId}")
  public Page findUsersByGroupId(@PathVariable String groupId) {
    UserSearchCriteria userSearchCriteria = UserSearchCriteriaBuilder.createCriteria()
        .withGroupIdIn(
            Collections.singleton(groupId)).build();
    return new PageImpl<>((List<UserDTO>) userService.findUsers(userSearchCriteria));
  }

  /**
   * Search for userGroup name based on a string.
   *
   * @param term the string to search for among userGroups.
   * @return one or more objects that contains the term.
   */
  @GetMapping(path = "/search/{term}")
  public Page findGroupsByTerm(@PathVariable String term) {
    UserGroupSearchCriteria userGroupSearchCriteria = UserGroupSearchCriteriaBuilder
        .createCriteria().withNameLike("%" + term + "%").build();
    return new PageImpl<>(
        (List<UserGroupDTO>) userGroupService.findGroups(userGroupSearchCriteria));
  }

  /**
   * Retrieves a single userGroup based on group id.
   *
   * @param groupId the group id to search for.
   * @return the userGroupDTO that has been found.
   */
  @GetMapping(path = "{groupId}")
  public UserGroupDTO findGroupById(@PathVariable String groupId) {
    return userGroupService.getGroupByID(groupId, false);
  }

  /**
   * Retrieves all userGroups.
   *
   * @return a response entity containing all userGroups.
   */
  @GetMapping("/groups")
  public ResponseEntity findAllGroups() {
    return userGroupManagementService.findAllGroups();
  }

  /**
   * Creates/updates a userGroup.
   *
   * @param userGroupManagementDTO the object to be inserted/updated.
   * @return a response entity.
   */
  @PostMapping
  public ResponseEntity upload(@Valid @RequestBody UserGroupManagementDTO userGroupManagementDTO) {
    return userGroupManagementService.upload(userGroupManagementDTO);
  }

  /**
   * Deletes a userGroup based on userGroup id.
   *
   * @param userGroupId the userGroup id to be deleted.
   */
  @DeleteMapping("{userGroupId}")
  public void delete(@Valid @PathVariable String userGroupId) {
    userGroupService.deleteGroup(userGroupId);
  }
}
