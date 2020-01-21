package com.eurodyn.qlack.webdesktop.applications.management.controller;

import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.model.User;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.webdesktop.applications.management.service.UserManagementService;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserManagementController {

  @Autowired
  private final UserManagementService userManagementService;
  @Autowired
  private final UserService userService;

  @GetMapping
  public Page<UserDTO> findAll(@QuerydslPredicate(root = User.class) Predicate predicate,
      Pageable pageable) {
    return userService.findAll(predicate, pageable);
  }

//  @GetMapping("{userId}")
//  public UserDTO find(@Valid @PathVariable String userId) {
//    return userManagementService.findById(userId);
//  }
//
//  @DeleteMapping("{userId}")
//  public void delete(@Valid @PathVariable String userId) {
//    userManagementService.deleteById(userId);
//  }
}
