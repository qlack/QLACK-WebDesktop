package com.eurodyn.qlack.webdesktop.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Access to the active profile.
 */
@Service
public class ProfileManagerService {

  @Value("${spring.profiles.active:}")
  private String activeProfile;

  /**
   * Retrieves the name of the profile that is currently active.
   *
   * @return a string containing the active-s profiles comma separated. If no profile is active, then an empty string is
   * return.
   */
  public String getActiveProfile() {
    return activeProfile;
  }
}
