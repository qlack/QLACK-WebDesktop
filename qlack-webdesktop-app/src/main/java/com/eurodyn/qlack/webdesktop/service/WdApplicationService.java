package com.eurodyn.qlack.webdesktop.service;

import com.eurodyn.qlack.fuse.lexicon.dto.GroupDTO;
import com.eurodyn.qlack.fuse.lexicon.service.GroupService;
import com.eurodyn.qlack.fuse.lexicon.service.KeyService;
import com.eurodyn.qlack.webdesktop.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.mapper.WdApplicationMapper;
import com.eurodyn.qlack.webdesktop.model.WdApplication;
import com.eurodyn.qlack.webdesktop.repository.WdApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
  private GroupService groupService;

  @Autowired
  public WdApplicationService(WdApplicationMapper mapper,
      WdApplicationRepository wdApplicationRepository) {
    this.mapper = mapper;
    this.wdApplicationRepository = wdApplicationRepository;
  }

  /**
   * Finds all active Web Desktop applications
   *
   * @return a list of {@link com.eurodyn.qlack.webdesktop.dto.WdApplicationDTO}
   */
  public List<WdApplicationDTO> findAllActiveApplications() {
    return mapper.mapToDTO(wdApplicationRepository.findByActiveIsTrue());
  }

  /**
   * Finds all active Web Desktop applications with trimmed group names.
   *
   * @return a list of {@link com.eurodyn.qlack.webdesktop.dto.WdApplicationDTO}
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

    Set<GroupDTO> groupDTOSet = groupService.getGroups();
    Map<String, Map<String, String>> translations = new HashMap<>();

    for (GroupDTO groupDTO : groupDTOSet) {
      translations.put(groupDTO.getTitle(),
          keyService.getTranslationsForGroupAndLocale(groupDTO.getId(), locale));
    }

    return translations;
  }

}
