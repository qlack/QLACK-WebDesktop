package com.eurodyn.qlack.webdesktop.common.util;

import org.springframework.stereotype.Component;

@Component
public class StringUtils {

  /**
   * Checks if provided {@param s} is not null or empty
   *
   * @param s the provided {@link String}
   * @return true if it is false otherwise
   */
  public boolean isNotNullOrEmpty(String s) {
    return s != null && !s.isEmpty();
  }

}
