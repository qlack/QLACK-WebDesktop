package com.eurodyn.qlack.webdesktop.user.profile.management.service;

import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.fuse.lexicon.dto.LanguageDTO;
import com.eurodyn.qlack.fuse.lexicon.service.KeyService;
import com.eurodyn.qlack.fuse.lexicon.service.LanguageService;
import com.eurodyn.qlack.webdesktop.user.profile.management.dto.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

    UserAttributeDTO defaultLanguageAttributeDTO = userService.getAttribute(userDetailsDTO.getId(), "defaultLanguage");
    if (defaultLanguageAttributeDTO == null) {
      defaultLanguageAttributeDTO = new UserAttributeDTO();
      defaultLanguageAttributeDTO.setName("defaultLanguage");
      defaultLanguageAttributeDTO.setData(userDetailsDTO.getDefaultLanguage());
      defaultLanguageAttributeDTO.setUserId(userDetailsDTO.getId());
      userService.updateAttribute(defaultLanguageAttributeDTO, true);
    } else {
      defaultLanguageAttributeDTO.setData(userDetailsDTO.getDefaultLanguage());
      userService.updateAttribute(defaultLanguageAttributeDTO, false);
    }
    UserAttributeDTO firstNameAttributeDTO = userService.getAttribute(userDetailsDTO.getId(), "firstName");
    if (firstNameAttributeDTO == null) {
      firstNameAttributeDTO = new UserAttributeDTO();
      firstNameAttributeDTO.setName("firstName");
      firstNameAttributeDTO.setData(userDetailsDTO.getFirstName());
      firstNameAttributeDTO.setUserId(userDetailsDTO.getId());
      userService.updateAttribute(firstNameAttributeDTO, true);
    } else {
      firstNameAttributeDTO.setData(userDetailsDTO.getFirstName());
      userService.updateAttribute(firstNameAttributeDTO, false);
    }
    UserAttributeDTO lastNameAttributeDTO = userService.getAttribute(userDetailsDTO.getId(), "lastName");
    if (lastNameAttributeDTO == null) {
      lastNameAttributeDTO = new UserAttributeDTO();
      lastNameAttributeDTO.setName("lastName");
      lastNameAttributeDTO.setData(userDetailsDTO.getLastName());
      lastNameAttributeDTO.setUserId(userDetailsDTO.getId());
      userService.updateAttribute(lastNameAttributeDTO, true);
    } else {
      lastNameAttributeDTO.setData(userDetailsDTO.getLastName());
      userService.updateAttribute(lastNameAttributeDTO, false);
    }
    if (profileImage != null) {
      UserAttributeDTO profileImageAttributeDTO = userService.getAttribute(userDetailsDTO.getId(), "profileImage");
      if (profileImageAttributeDTO == null) {
        profileImageAttributeDTO = new UserAttributeDTO();
        profileImageAttributeDTO.setName("profileImage");
        profileImageAttributeDTO.setBindata(profileImage.getBytes());
        profileImageAttributeDTO.setContentType(profileImage.getContentType());
        profileImageAttributeDTO.setData(profileImage.getName());
        profileImageAttributeDTO.setUserId(userDetailsDTO.getId());
        userService.updateAttribute(profileImageAttributeDTO, true);
      } else {
        profileImageAttributeDTO.setBindata(profileImage.getBytes());
        userService.updateAttribute(profileImageAttributeDTO, false);
      }
    }
    if (backgroundImage != null) {
      UserAttributeDTO backgroundImageAttributeDTO = userService
          .getAttribute(userDetailsDTO.getId(), "backgroundImage");
      if (backgroundImageAttributeDTO == null) {
        backgroundImageAttributeDTO = new UserAttributeDTO();
        backgroundImageAttributeDTO.setName("backgroundImage");
        backgroundImageAttributeDTO.setBindata(backgroundImage.getBytes());
        backgroundImageAttributeDTO.setContentType(backgroundImage.getContentType());
        backgroundImageAttributeDTO.setData(backgroundImage.getName());
        backgroundImageAttributeDTO.setUserId(userDetailsDTO.getId());
        userService.updateAttribute(backgroundImageAttributeDTO, true);
      } else {
        backgroundImageAttributeDTO.setBindata(backgroundImage.getBytes());
        userService.updateAttribute(backgroundImageAttributeDTO, false);
      }
    }
  }

  public Map<String, UserAttributeDTO> findUserDetails(String userId) {

    UserDTO userDTO = userService.getUserById(userId);
    Map<String, UserAttributeDTO> attributeList = new HashMap<>();
    for (UserAttributeDTO attribute : userDTO.getUserAttributes()) {
      attributeList.put(attribute.getName(), attribute);
    }
    return attributeList;
  }

}
