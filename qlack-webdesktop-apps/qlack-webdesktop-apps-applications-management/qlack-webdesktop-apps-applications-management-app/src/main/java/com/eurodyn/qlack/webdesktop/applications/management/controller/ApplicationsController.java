package com.eurodyn.qlack.webdesktop.applications.management.controller;

import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.webdesktop.applications.management.dto.WdApplicationManagementDTO;
import com.eurodyn.qlack.webdesktop.applications.management.service.ApplicationsService;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

  @Autowired
  public ApplicationsController(ApplicationsService applicationsService) {
    this.applicationsService = applicationsService;
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
   * Saves a new wd application.
   *
   * @return the response entity.
   */
  @PostMapping("/applications")
  public ResponseEntity save(
      @Valid @RequestBody WdApplicationManagementDTO wdApplicationManagementDTO) {
    return applicationsService.save(wdApplicationManagementDTO);
  }

  /**
   * Updates a wd application.
   *
   * @return the response entity.
   */
  @PostMapping("/applications/{id}")
  public ResponseEntity update(
      @Valid @RequestBody WdApplicationManagementDTO wdApplicationManagementDTO) {
    return applicationsService.update(wdApplicationManagementDTO);
  }

  /**
   * Saves a new wd application or updates an existing one through yaml file.
   *
   * @param file the file to retrieve data from.
   */
  @PostMapping(value = "/upload")
  public void uploadApplication(@RequestParam("file") MultipartFile file) {
    applicationsService.saveApplicationFromYaml(file);
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
}
