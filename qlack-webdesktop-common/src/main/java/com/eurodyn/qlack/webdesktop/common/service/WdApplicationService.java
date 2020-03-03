package com.eurodyn.qlack.webdesktop.common.service;


import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserGroupDTO;
import com.eurodyn.qlack.fuse.aaa.service.OperationService;
import com.eurodyn.qlack.fuse.aaa.service.UserGroupService;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.fuse.lexicon.dto.GroupDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.KeyDTO;
import com.eurodyn.qlack.fuse.lexicon.service.GroupService;
import com.eurodyn.qlack.fuse.lexicon.service.KeyService;
import com.eurodyn.qlack.fuse.lexicon.service.LanguageService;
import com.eurodyn.qlack.webdesktop.common.dto.LanguageDataDTO;
import com.eurodyn.qlack.webdesktop.common.dto.LexiconDTO;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.mapper.WdApplicationMapper;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;
import com.eurodyn.qlack.webdesktop.common.repository.WdApplicationRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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
  private GroupService groupService;
  private KeyService keyService;
  private UserService userService;
  private OperationService operationService;
  private UserGroupService userGroupService;
  private LanguageService languageService;


  @Autowired
  public WdApplicationService(WdApplicationMapper mapper,
      WdApplicationRepository wdApplicationRepository, GroupService groupService,
      KeyService keyService, UserService userService,
      OperationService operationService, UserGroupService userGroupService, LanguageService languageService) {
    this.mapper = mapper;
    this.wdApplicationRepository = wdApplicationRepository;
    this.groupService = groupService;
    this.keyService = keyService;
    this.userService = userService;
    this.userGroupService = userGroupService;
    this.operationService = operationService;
    this.languageService = languageService;
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
   * Finds all active Web Desktop applications and filters them based on user's permissions.
   *
   * @return a list of filtered {@link WdApplicationDTO}
   */
  public List<WdApplicationDTO> findAllActiveApplicationsFilterGroupName() {
    UserDTO user = null;
    //fetch the data from logged-in user
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof DefaultOAuth2User) {
      String userName = ((DefaultOAuth2User) principal).getName();
      user = userService.getUserByName(userName);
    }
    //find all applications
    List<WdApplication> wdApplicationList = wdApplicationRepository
        .findBySystemAndActiveIsTrue(false);
    //find system applications
    List<WdApplication> wdApplicationListSystem = wdApplicationRepository
        .findBySystemAndActiveIsTrue(true);
    if (user!= null && user.isSuperadmin()){
      wdApplicationList.addAll(wdApplicationListSystem);
    }
    //filter the application list based on user's permissions
    UserDTO finalUser = user;
    List<WdApplication> filteredWdList = wdApplicationList.stream()
        .filter(app -> !app.isRestrictAccess() || getPermissions(app,
            finalUser))
        .collect(Collectors.toList());

    //set groups in applications
    filteredWdList.forEach(wdApplication -> {
      if (wdApplication.getGroupName() == null || "null".equals(wdApplication.getGroupName())) {
        wdApplication.setGroupName("");
      }
      wdApplication.setGroupName(wdApplication.getGroupName().trim());
    });
    return mapper.mapToDTO(filteredWdList);
  }

  private boolean getPermissions(WdApplication app, UserDTO finalUser) {
    operationService.setPrioritisePositive(true);
    return operationService.isPermitted(finalUser.getId(), "view", app.getId());
  }

  /**
   * Retrieves the list of userGroups that belong to a user.
   *
   * @param userId the user's id to retrieve the userGroups.
   * @return a list containing all the userGroups that belong to a user.
   */
  public List<UserGroupDTO> findUserGroupsIds(String userId) {
    List<UserGroupDTO> userGroupList = new ArrayList<>();
    Collection<String> userGroupsIds = userGroupService.getUserGroupsIds(userId);
    userGroupsIds.forEach(userGroupId ->
        userGroupList.add(userGroupService.getGroupByID(userGroupId, true)
        ));
    return userGroupList;
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
  public WdApplication findApplicationByName(String name) {
    return wdApplicationRepository.findByApplicationName(name);
  }

  public WdApplicationDTO findApplicationDTOByName(String name){
    WdApplication wdApplication = wdApplicationRepository.findByApplicationName(name);
    return mapper.mapToDTO(wdApplication);
  }

  /**
   * Saves an application to database
   *
   * @param wdApplication the object to be saved
   */
  public void saveApplication(WdApplication wdApplication) {
    wdApplicationRepository.save(wdApplication);
  }

  public void processLexiconValues(List<LexiconDTO> translations, WdApplication wdApplication){
    GroupDTO groupDTO = groupService.getGroupByTitle(wdApplication.getApplicationName());
    // we need groupId variable to get the generated Id of new group  from database
    String groupId;
    if (groupDTO == null) {

      groupDTO = new GroupDTO();
      groupDTO.setTitle(wdApplication.getApplicationName());
      groupDTO.setDescription("defaultDescription");
      groupId = groupService.createGroup(groupDTO);
    } else {
      groupId = groupDTO.getId();
    }

    for (LexiconDTO translation : translations) {
      if (languageService.getLanguageByLocale(translation.getLanguageLocale()) != null) {
        for (LanguageDataDTO data : translation.getValues()) {
          KeyDTO keyDTO = keyService.getKeyByName(data.getKey(), groupId, false);
          if (keyDTO == null) {
            keyDTO = new KeyDTO();
            keyDTO.setGroupId(groupId);
            keyDTO.setName(data.getKey());
            String keyId = keyService.createKey(keyDTO, false);
            keyService
                .updateTranslationByLocale(keyId, translation.getLanguageLocale(), data.getValue());
          } else {

            keyService.updateTranslationByLocale(keyDTO.getId(), translation.getLanguageLocale(),
                data.getValue());
          }
        }

      }
    }
  }

}
