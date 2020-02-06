package com.eurodyn.qlack.webdesktop.user.profile.management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
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
  @JsonProperty(access = Access.READ_ONLY)
  private String firstName;
  @JsonProperty(access = Access.READ_ONLY)
  private String lastName;
  private String defaultLanguage;

}
