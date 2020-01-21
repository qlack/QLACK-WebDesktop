package com.eurodyn.qlack.webdesktop.user.profile.management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO {

  private String id;
  private String firstName;
  private String lastName;
  private String defaultLanguage;

}
