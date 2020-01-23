package com.eurodyn.qlack.webdesktop.applications.management.controller;

import com.eurodyn.qlack.fuse.aaa.criteria.UserSearchCriteria;
import com.eurodyn.qlack.fuse.aaa.criteria.UserSearchCriteria.UserSearchCriteriaBuilder;
import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.webdesktop.applications.management.service.UserManagementService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserManagementController {

  @Autowired
  private final UserService userService;

  @GetMapping
  public Page<UserDTO> findAll() {
    UserSearchCriteria userSearchCriteria = UserSearchCriteriaBuilder.createCriteria().build();
    return new PageImpl<>((List<UserDTO>) userService.findUsers(userSearchCriteria));
  }

  @GetMapping("{userId}")
  public UserDTO find(@Valid @PathVariable String userId) {
    return userService.getUserById(userId);
  }
}
