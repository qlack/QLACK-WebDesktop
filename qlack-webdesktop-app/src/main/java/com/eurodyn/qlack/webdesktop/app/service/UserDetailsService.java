package com.eurodyn.qlack.webdesktop.app.service;

import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.fuse.lexicon.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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
  @Autowired
  private LanguageService languageService;
  private static final String DEFAULT_LANGUAGE = "defaultLanguage";
  @Autowired
  private Environment env;
  @Value("${system.default.language}")
  private String systemDefaultLanguage;

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
        if (attribute.getName().equalsIgnoreCase(attributeName)) {
          if(attributeName.equalsIgnoreCase(DEFAULT_LANGUAGE)){
            if (!languageService.getLanguageByLocale(attribute.getData()).isActive()){
              return  new UserAttributeDTO(DEFAULT_LANGUAGE,systemDefaultLanguage);
            }
          }
          return attribute;
        }
      }
    }
    // if the user has not saved default language as user attribute return the system default language
    if(attributeName.equalsIgnoreCase(DEFAULT_LANGUAGE)){
      return  new UserAttributeDTO(DEFAULT_LANGUAGE,systemDefaultLanguage);
    }
    return null;
  }

  public boolean isSsoProfile() {
    return env.getActiveProfiles().length == 1;
  }

}
