package com.eurodyn.qlack.webdesktop.user.profile.management.service;

import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.fuse.lexicon.dto.LanguageDTO;
import com.eurodyn.qlack.fuse.lexicon.service.KeyService;
import com.eurodyn.qlack.fuse.lexicon.service.LanguageService;
import com.eurodyn.qlack.webdesktop.user.profile.management.dto.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserProfileService {

  private static final String TRANSLATIONS_GROUP = "user-profile-management-ui";
  private KeyService keyService;
  private LanguageService languageService;
  private UserService userService;

  @Autowired
  public UserProfileService(KeyService keyService, LanguageService languageService,
      UserService userService) {
    this.keyService = keyService;
    this.languageService = languageService;
    this.userService = userService;
  }

  public Map<String, String> findTranslationsForLocale(String locale) {
    return keyService.getTranslationsForGroupNameAndLocale(TRANSLATIONS_GROUP, locale);
  }

  public List<LanguageDTO> findLanguages(boolean includeInactive) {
    return languageService.getLanguages(includeInactive);
  }

  public void saveDetails(UserDetailsDTO userDetailsDTO, MultipartFile profileImage, MultipartFile backgroundImage)
      throws IOException {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof DefaultOAuth2User) {
      String userName = ((DefaultOAuth2User) principal).getName();
      String userId = userService.getUserByName(userName).getId();

      saveAttribute("defaultLanguage",userId,null,userDetailsDTO.getDefaultLanguage());
      if(profileImage != null) {
        saveAttribute("profileImage", userId, profileImage.getBytes(), null);
      }
      if(backgroundImage != null) {
        saveAttribute("backgroundImage", userId, backgroundImage.getBytes(), null);
      }
    }
  }

  public Map<String, UserAttributeDTO> findUserDetails() {
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

  public void saveAttribute(String attributeName,String userId,byte[] binData,String data){
    UserAttributeDTO userAttributeDTO = userService.getAttribute(userId, attributeName);
    if (userAttributeDTO == null) {
      userAttributeDTO = new UserAttributeDTO();
      userAttributeDTO.setName(attributeName);
      userAttributeDTO.setBindata(binData);
      userAttributeDTO.setData(data);
      userAttributeDTO.setUserId(userId);
      userService.updateAttribute(userAttributeDTO, true);
    } else {
      userAttributeDTO.setBindata(binData);
      userAttributeDTO.setData(data);
      userService.updateAttribute(userAttributeDTO, false);
    }
  }

}
