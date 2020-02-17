package com.eurodyn.qlack.webdesktop.user.profile.management.controller;

import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.LanguageDTO;
import com.eurodyn.qlack.webdesktop.user.profile.management.dto.UserDetailsDTO;
import com.eurodyn.qlack.webdesktop.user.profile.management.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class UserProfileController {

  private UserProfileService userProfileService;

  @Autowired
  public UserProfileController(
      UserProfileService userProfileService) {
    this.userProfileService = userProfileService;
  }

  @GetMapping("/languages/{includeInactive}")
  public List<LanguageDTO> getLanguages(@PathVariable boolean includeInactive) {
    return userProfileService.findLanguages(includeInactive);
  }

  @GetMapping("/translations")
  public Map<String, String> getTranslations(@RequestParam String lang) {
    return userProfileService.findTranslationsForLocale(lang);
  }

  @PostMapping("/user/details/save")
  public void saveUserDetails(@ModelAttribute UserDetailsDTO userDetailsDTO,
      @RequestParam(value = "profileImage", required =
          false) MultipartFile profileImage,
      @RequestParam(value = "backgroundImage", required = false) MultipartFile backgroundImage) throws IOException {
    userProfileService.saveDetails(userDetailsDTO, profileImage, backgroundImage);
  }

  @GetMapping("/user/details")
  public Map<String, UserAttributeDTO> getUserDetails() {
    return userProfileService.findUserDetails();
  }
}