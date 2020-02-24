package com.eurodyn.qlack.webdesktop.applications.management.controller;

import com.eurodyn.qlack.webdesktop.applications.management.dto.WdApplicationManagementDTO;
import com.eurodyn.qlack.webdesktop.applications.management.service.ApplicationsService;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;
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
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationsController {

  @Autowired
  private ApplicationsService applicationsService;

  /**
   * Returns all the QLACK Web Desktop applications.
   *
   * @return a list containing all the applications
   */
  @GetMapping()
  public Page<WdApplicationDTO> getApplications() {
    return applicationsService.getApplications();
  }


  /**
   * Returns an application by given id.
   * @param id the application's id
   * @return a single Web Desktop application.
   */
  @GetMapping(path = "{id}")
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
   * Get a signle Web Desktop application by application Name.
   *
   * @param name the application's name.
   * @return a signle Web Desktop application.
   */
  @GetMapping(path = "/name")
  public WdApplication getApplicationByApplicationName(@RequestParam String name) {
    return applicationsService.findApplicationByName(name);
  }

  /**
   * Saves a new wd application or updates an existing one.
   *
   * @return the response entity.
   */
  @PostMapping()
  public ResponseEntity updateApplication(
      @Valid @RequestBody WdApplicationManagementDTO wdApplicationManagementDTO) {
    return applicationsService.updateApplication(wdApplicationManagementDTO);
  }

  /**
   * Saves a new wd application or updates an existing one.
   *
   * @return the response entity.
   */
  @PostMapping(value = "/upload")
  public void uploadApplication(@RequestParam("file") MultipartFile file) {
    applicationsService.saveApplicationFromYaml(file);
  }
}
