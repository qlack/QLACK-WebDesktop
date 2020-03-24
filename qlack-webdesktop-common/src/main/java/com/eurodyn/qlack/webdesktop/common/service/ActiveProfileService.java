package com.eurodyn.qlack.webdesktop.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActiveProfileService {

  private static final String SSO_PROFILE = "sso";

  private ProfileManagerService profileManagerService;

  @Autowired
  public ActiveProfileService(ProfileManagerService profileManagerService) {
    this.profileManagerService = profileManagerService;
  }

  public boolean isSsoActive() {
    return SSO_PROFILE.equalsIgnoreCase(profileManagerService.getActiveProfile());
  }

}
