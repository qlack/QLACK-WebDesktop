package com.eurodyn.qlack.webdesktop.applications.management.controller;

import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.webdesktop.applications.management.service.ApplicationsService;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationManagementDTO;
import com.eurodyn.qlack.webdesktop.common.service.ActiveProfileService;
import com.eurodyn.qlack.webdesktop.common.service.ProfileManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * This Controller class contains all the endpoints related to the applications actions.
 *
 * @author EUROPEAN DYNAMICS SA
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplicationsController {

  private ApplicationsService applicationsService;
  private ProfileManagerService profileManagerService;
  private ActiveProfileService activeProfileService;

  @Autowired
  public ApplicationsController(
      ApplicationsService applicationsService,
      ProfileManagerService profileManagerService,
      ActiveProfileService activeProfileService) {
    this.applicationsService = applicationsService;
    this.profileManagerService = profileManagerService;
    this.activeProfileService = activeProfileService;
  }

  /**
   * Returns all the QLACK Web Desktop applications.
   *
   * @return a responded entity containing all the applications
   */
  @GetMapping("/applications")
  public ResponseEntity<Page<WdApplicationDTO>> getApplications() {
    return applicationsService.getApplications();
  }


  /**
   * Returns an application by given id.
   *
   * @param id the application's id
   * @return a single Web Desktop application.
   */
  @GetMapping(path = "/applications/{id}")
  public WdApplicationManagementDTO getApplicationById(@PathVariable String id) {
    return applicationsService.getApplicationById(id);
  }

  /**
   * This method returns all the QLACK Web Desktop translations.
   *
   * @return a list containing all the translations
   */
  @GetMapping("/translations")
  public Map<String, Map<String, String>> getTranslations(
      @RequestParam String lang) {
    return applicationsService.getTranslations(lang);
  }

  /**
   * Retrieves user's attributes based on attribute name.
   *
   * @param attributeName the attribute to search for.
   * @return the responded userAttributeDTO.
   */
  @GetMapping("/user/attributes/{attributeName}")
  public UserAttributeDTO getUserAttributeByName(@PathVariable String attributeName) {
    return applicationsService.findUserAttributeByName(attributeName);
  }

  @GetMapping(path = "/activeProfile")
  public boolean getActiveProfile() {
    return activeProfileService.isSsoActive();
  }

}
