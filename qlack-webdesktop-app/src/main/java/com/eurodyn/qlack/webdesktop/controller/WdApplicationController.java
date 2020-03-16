package com.eurodyn.qlack.webdesktop.controller;

import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
import com.eurodyn.qlack.webdesktop.service.UserDetailsService;
import java.util.List;
import java.util.Map;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides Web Desktop application REST service endpoints.
 *
 * @author European Dynamics SA.
 */
@Log
@RestController
@RequestMapping("/apps")
public class WdApplicationController {

  private WdApplicationService wdApplicationService;

  @Autowired
  public WdApplicationController(WdApplicationService wdApplicationService) {
    this.wdApplicationService = wdApplicationService;
  }

  /**
   * Get all the active Web Desktop applications filtered
   *
   * @return a list of the active Web Desktop applications
   */
  @GetMapping(path = "/")
  public List<WdApplicationDTO> getFilteredActiveApplications() {
    return wdApplicationService.findAllActiveApplicationsFilterGroupName();
  }

  /**
   * Get all translations for a specific locale,groupby group title which is the applicationName
   * from .yaml configuration file Every App has its own group.
   *
   * @param lang the language locale
   * @return a list of translations for a specific locale
   */
  @GetMapping("/translations")
  public Map<String, Map<String, String>> getTranslations(@RequestParam String lang) {
    return wdApplicationService.findTranslationsForLocale(lang);
  }

  /**
   * Get a single Web Desktop application by id.
   *
   * @param id the application's id.
   * @return a signle Web Desktop application.
   */
  @GetMapping("{id}")
  public WdApplicationDTO getApplicationById(@PathVariable String id) {
    return wdApplicationService.findApplicationById(id);
  }

  @GetMapping("/app/{name}")
  public WdApplicationDTO getApplicationByName(@PathVariable String name) {
    return wdApplicationService.findApplicationDTOByName(name);
  }

}
