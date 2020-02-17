package com.eurodyn.qlack.webdesktop.common.service;


import com.eurodyn.qlack.fuse.lexicon.service.KeyService;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.mapper.WdApplicationMapper;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;
import com.eurodyn.qlack.webdesktop.common.repository.WdApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

/**
 * Provides Qlack Web Desktop application related functionality
 *
 * @author European Dynamics SA.
 */
@Service
@Validated
public class WdApplicationService {

  private WdApplicationMapper mapper;
  private WdApplicationRepository wdApplicationRepository;
  @Autowired
  private KeyService keyService;

  @Autowired
  public WdApplicationService(WdApplicationMapper mapper,
      WdApplicationRepository wdApplicationRepository) {
    this.mapper = mapper;
    this.wdApplicationRepository = wdApplicationRepository;
  }

  /**
   * Finds all active Web Desktop applications
   *
   * @return a list of {@link WdApplicationDTO}
   */
  public List<WdApplicationDTO> findAllActiveApplications() {
    return mapper.mapToDTO(wdApplicationRepository.findByActiveIsTrue());
  }

  /**
   * Finds all Web Desktop applications
   *
   * @return a list of {@link WdApplicationDTO}
   */
  public List<WdApplicationDTO> findAllApplications() {
    return mapper.mapToDTO(wdApplicationRepository.findAll());
  }

  /**
   * Finds all active Web Desktop applications with trimmed group names.
   *
   * @return a list of {@link WdApplicationDTO}
   */
  public List<WdApplicationDTO> findAllActiveApplicationsFilterGroupName() {
    List<WdApplication> wdApplicationList = wdApplicationRepository.findByActiveIsTrue();
    wdApplicationList.forEach(wdApplication -> {
      if (wdApplication.getGroupName() == null || "null".equals(wdApplication.getGroupName())) {
        wdApplication.setGroupName("");
      }
      wdApplication.setGroupName(wdApplication.getGroupName().trim());
    });
    return mapper.mapToDTO(wdApplicationList);
  }

  /**
   * Finds all translations from all groups for a specific locale,groupby group title which is the
   * applicationName from .yaml configuration file Every App has its own group.
   *
   * @param locale the language locale
   * @return a list of translations from all groups for a specific locale
   */
  public Map<String, Map<String, String>> findTranslationsForLocale(String locale) {
    return keyService.getTranslationsForLocaleGroupByGroupTitle(locale);
  }

  /**
   * Finds a Web Desktop application by id.
   *
   * @param id the application id.
   * @return a single web desktop application.
   */
  public WdApplicationDTO findApplicationById(String id) {
    WdApplication wdApplication = wdApplicationRepository.fetchById(id);
    return mapper.mapToDTO(wdApplication);
  }

  /**
   * Finds and returns an application by applicationName field
   *
   * @param name the applicationName field
   * @return the application object
   */
  public WdApplication findApplicationByName(String name){
    return wdApplicationRepository.findByApplicationName(name);
  }

  /**
   * Saves an application to database
   *
   * @param wdApplication the object to be saved
   */
  public void saveApplication(WdApplication wdApplication){
    wdApplicationRepository.save(wdApplication);
  }
}