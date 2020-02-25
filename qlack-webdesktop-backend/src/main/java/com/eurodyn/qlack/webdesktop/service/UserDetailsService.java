package com.eurodyn.qlack.webdesktop.service;

import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;

@Service
@Validated
public class UserDetailsService {

  @Autowired
  private UserService userService;

  public Map<String, UserAttributeDTO> findUserAttributes() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof DefaultOAuth2User) {
      String userName = ((DefaultOAuth2User) principal).getName();
      UserDTO userDTO = userService.getUserByName(userName);
      Map<String, UserAttributeDTO> attributeList = new HashMap<>();
      for (UserAttributeDTO attribute : userDTO.getUserAttributes()) {
        attributeList.put(attribute.getName(), attribute);
      }
      return attributeList;
    }
    return null;
  }

  public UserAttributeDTO findUserAttributeByName(String attributeName) {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof DefaultOAuth2User) {
      String userName = ((DefaultOAuth2User) principal).getName();
      UserDTO userDTO = userService.getUserByName(userName);
      for (UserAttributeDTO attribute : userDTO.getUserAttributes()) {
         if(attribute.getName().equalsIgnoreCase(attributeName)){
           return  attribute;
         }
      }
    }
    return null;
  }

}
