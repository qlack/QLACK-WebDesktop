package com.eurodyn.qlack.webdesktop.app.controller;

import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.webdesktop.app.service.UserDetailsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Log
@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserDetailsService userDetailsService;

  @Autowired
  public UserController(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @GetMapping("/attributes")
  public Map<String, UserAttributeDTO> getUserAttributes() {
    return userDetailsService.findUserAttributes();
  }

  @GetMapping("/attributes/{attributeName}")
  public UserAttributeDTO getUserAttributeByName(@PathVariable String attributeName) {
    return userDetailsService.findUserAttributeByName(attributeName);
  }

  @GetMapping("/profile")
  public boolean getActiveProfile() {
    return userDetailsService.isSsoProfile();
  }

  @GetMapping("/systemLanguage")
  public String getSystemDefaultLanguage() {
    return userDetailsService.findSystemDefaultLanguage();
  }
}

