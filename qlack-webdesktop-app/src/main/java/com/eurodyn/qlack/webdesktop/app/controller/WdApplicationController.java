package com.eurodyn.qlack.webdesktop.app.controller;

import com.eurodyn.qlack.webdesktop.app.service.ApplicationsService;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationManagementDTO;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;
import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Provides Web Desktop application REST service endpoints.
 *
 * @author European Dynamics SA.
 */
@Log
@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
public class WdApplicationController {

  private final WdApplicationService wdApplicationService;

  private final ApplicationsService applicationsService;


  /**
   * Get all the active Web Desktop applications filtered
   *
   * @return a list of the active Web Desktop applications
   */
  @GetMapping("/")
  public List<WdApplicationDTO> getFilteredActiveApplications() {
    return wdApplicationService.findAllActiveApplicationsFilterGroupName();
  }

  /**
   * Get a single Web Desktop application by id.
   *
   * @param id the application's id.
   * @return a signle Web Desktop application.
   */
  @GetMapping("/{id}")
  public WdApplicationDTO getApplicationById(@PathVariable String id) {
    return wdApplicationService.findApplicationById(id);
  }

  @GetMapping("/byName/{name}")
  public WdApplicationDTO getApplicationByName(@PathVariable String name) {
    return wdApplicationService.findApplicationDTOByName(name);
  }

  @GetMapping("/activeByName/{name}")
  public WdApplicationDTO getActiveApplicationByName(@PathVariable String name) {
    return wdApplicationService.findActiveApplicationDTOByName(name);
  }

  /**
   * Saves a new wd application.
   *
   * @return the response entity.
   */
  @PostMapping("/save")
  @SuppressWarnings({"java:S3740"})
  public ResponseEntity save(
      @Valid @RequestBody WdApplicationManagementDTO wdApplicationManagementDTO) {
    return applicationsService.save(wdApplicationManagementDTO);
  }

  /**
   * Updates a wd application.
   *
   * @return the response entity.
   */
  @PostMapping("/update/{id}")
  public ResponseEntity<WdApplication> update(
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

}
