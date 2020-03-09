package com.eurodyn.qlack.webdesktop.applications.management.service;

import com.eurodyn.qlack.fuse.aaa.criteria.UserGroupSearchCriteria;
import com.eurodyn.qlack.fuse.aaa.criteria.UserGroupSearchCriteria.UserGroupSearchCriteriaBuilder;
import com.eurodyn.qlack.fuse.aaa.criteria.UserSearchCriteria;
import com.eurodyn.qlack.fuse.aaa.criteria.UserSearchCriteria.UserSearchCriteriaBuilder;
import com.eurodyn.qlack.fuse.aaa.dto.ResourceDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserGroupDTO;
import com.eurodyn.qlack.fuse.aaa.service.OperationService;
import com.eurodyn.qlack.fuse.aaa.service.ResourceService;
import com.eurodyn.qlack.fuse.aaa.service.UserGroupService;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.fuse.crypto.service.CryptoDigestService;
import com.eurodyn.qlack.webdesktop.applications.management.dto.WdApplicationManagementDTO;
import com.eurodyn.qlack.webdesktop.applications.management.util.ProcessLexiconUtil;
import com.eurodyn.qlack.webdesktop.common.dto.LexiconDTO;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.mapper.WdApplicationMapper;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;
import com.eurodyn.qlack.webdesktop.common.repository.WdApplicationRepository;
import com.eurodyn.qlack.webdesktop.common.service.ResourceWdApplicationService;
import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * This Service method contains all the actions related to the applications. It uses the related
 * Service of the QLACK WebDesktop Service.
 *
 * @author EUROPEAN DYNAMICS SA
 */
@Log
@Service
@RequiredArgsConstructor
public class ApplicationsService {

  private WdApplicationService wdApplicationService;
  private WdApplicationRepository wdApplicationRepository;
  private WdApplicationMapper mapper;
  private CryptoDigestService cryptoDigestService;
  private ProcessLexiconUtil processLexiconUtil;
  private ResourceService resourceService;
  private OperationService operationService;
  private UserService userService;
  private UserGroupService userGroupService;
  private ResourceWdApplicationService resourceWdApplicationService;

  @Autowired
  @SuppressWarnings("squid:S107")
  public ApplicationsService(WdApplicationService wdApplicationService,
      WdApplicationRepository wdApplicationRepository, ProcessLexiconUtil processLexiconUtil,
      CryptoDigestService cryptoDigestService, OperationService operationService,
      WdApplicationMapper mapper, ResourceService resourceService,
      ResourceWdApplicationService resourceWdApplicationService,
      UserService userService, UserGroupService userGroupService) {
    this.wdApplicationService = wdApplicationService;
    this.wdApplicationRepository = wdApplicationRepository;
    this.cryptoDigestService = cryptoDigestService;
    this.processLexiconUtil = processLexiconUtil;
    this.mapper = mapper;
    this.resourceService = resourceService;
    this.operationService = operationService;
    this.resourceWdApplicationService = resourceWdApplicationService;
    this.userGroupService = userGroupService;
    this.userService = userService;
  }

  /**
   * This method returns all the QLACK Web Desktop applications.
   *
   * @return a list containing all the applications
   */
  public Page<WdApplicationDTO> getApplications() {
    return new PageImpl<>(wdApplicationService.findAllApplications());
  }

  /**
   * This method returns a QLACK Web Desktop application by id.
   *
   * @return a single application
   */
  public WdApplicationManagementDTO getApplicationById(String id) {
    WdApplicationManagementDTO wdApplicationManagementDTO = new WdApplicationManagementDTO();
    WdApplicationDTO wdApplicationDTO = wdApplicationService.findApplicationById(id);
    wdApplicationManagementDTO.setDetails(wdApplicationDTO);

    ResourceDTO resourceDTO = resourceService.getResourceByObjectId(id);
    if (resourceDTO != null) {
      Set<String> usersOperationDTO = operationService
          .getAllowedUsersForOperationRemoveSuperAdmin("view", resourceDTO.getObjectId(), false);
      Set<String> userGroupsOperationDTO = operationService
          .getAllowedGroupsForOperation("view", resourceDTO.getObjectId(), false);

      UserSearchCriteria userSearchCriteria = UserSearchCriteriaBuilder
          .createCriteria().withIdIn(usersOperationDTO).build();

      UserGroupSearchCriteria userGroupSearchCriteria = UserGroupSearchCriteriaBuilder
          .createCriteria().withIdIn(userGroupsOperationDTO).build();

      PageImpl<UserDTO> users = new PageImpl<>(
          (List<UserDTO>) userService.findUsers(userSearchCriteria));
      PageImpl<UserGroupDTO> userGroups = new PageImpl<>(
          (List<UserGroupDTO>) userGroupService.findGroups(userGroupSearchCriteria));

      wdApplicationManagementDTO.setUsers(users);
      wdApplicationManagementDTO.setUserGroups(userGroups);
    }
    return wdApplicationManagementDTO;
  }

  /**
   * Finds all translations from all groups for a specific locale
   *
   * @param locale the language locale
   * @return a list of translations for a specific locale
   */
  public Map<String, Map<String, String>> getTranslations(String locale) {
    return wdApplicationService.findTranslationsForLocale(locale);
  }

  /**
   * Saves a new wd application. An extra check must be done, in order to make sure that application
   * Name is unique.
   *
   * @param wdApplicationManagementDTO the application to be saved.
   * @return the responded entity.
   */
  public ResponseEntity save(WdApplicationManagementDTO wdApplicationManagementDTO) {

    WdApplication wdApplicationByName = wdApplicationService.findApplicationByName(
        wdApplicationManagementDTO.getDetails().getApplicationName());

    //if the application is new but the application name already exists.
    if (wdApplicationByName != null && wdApplicationManagementDTO.getDetails().getApplicationName()
        .equals(wdApplicationByName.getApplicationName())) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("alreadyExistsCode");
    }
    wdApplicationByName = mapper.mapToEntity(wdApplicationManagementDTO.getDetails());

    //create translations for all languages by default.
    List<LexiconDTO> lexiconValues = processLexiconUtil
        .createLexiconList(wdApplicationManagementDTO.getDetails());
    processLexiconUtil
        .createLexiconValues(lexiconValues, wdApplicationManagementDTO.getDetails());

    wdApplicationService.saveApplication(wdApplicationByName);

    WdApplication newWdApplication = wdApplicationService
        .findApplicationByName(wdApplicationManagementDTO.getDetails().getApplicationName());

    //after save, every new application must be connected to a resourceId.
    String resourceId = resourceWdApplicationService.createApplicationResource(newWdApplication);

    //if there are permissions along with the new application, save them as well.
    if (wdApplicationManagementDTO.getDetails().isRestrictAccess()) {
      updatePermissions(wdApplicationManagementDTO, resourceId);
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(wdApplicationByName);
  }

  /**
   * Updates an existing application.
   *
   * @param wdApplicationManagementDTO the application to be updated.
   * @return the responded entity.
   */
  public ResponseEntity update(WdApplicationManagementDTO wdApplicationManagementDTO) {

    WdApplication wdApplication = wdApplicationRepository
        .fetchById(wdApplicationManagementDTO.getDetails().getId());
    WdApplicationDTO initWdApplicationDTO = new WdApplicationDTO();
    //hold the status of restrictAccess.
    initWdApplicationDTO.setRestrictAccess(wdApplication.isRestrictAccess());
    //only 2 fields is permitted to be updated by default.
    wdApplication.setActive(wdApplicationManagementDTO.getDetails().isActive());
    wdApplication
        .setRestrictAccess(wdApplicationManagementDTO.getDetails().isRestrictAccess());

    wdApplicationService.saveApplication(wdApplication);

    ResourceDTO resourceDTO = resourceService
        .getResourceByObjectId(wdApplication.getId());
    //In case the new isRestrictAccess is false but the old is true, all permissions must be deleted.
    if (initWdApplicationDTO.isRestrictAccess() && !wdApplicationManagementDTO.getDetails()
        .isRestrictAccess()) {
      removeAllPermissions(resourceDTO);
    }

    if (wdApplicationManagementDTO.getDetails().isRestrictAccess()) {
      updatePermissions(wdApplicationManagementDTO, resourceDTO.getId());
    }

    return ResponseEntity.status(HttpStatus.OK).body(wdApplication);
  }

  private void updatePermissions(WdApplicationManagementDTO wdApplicationManagementDTO,
      String resourceId) {
    Collection<String> usersAdded = wdApplicationManagementDTO.getUsersAdded();
    Collection<String> usersRemoved = wdApplicationManagementDTO.getUsersRemoved();
    Collection<String> userGroupsAdded = wdApplicationManagementDTO.getGroupsAdded();
    Collection<String> userGroupsRemoved = wdApplicationManagementDTO.getGroupsRemoved();
    if (usersRemoved != null) {
      usersRemoved.forEach(userDTO -> operationService
          .removeOperationFromUser(userDTO, "view", resourceId));
    }
    if (userGroupsRemoved != null) {
      userGroupsRemoved.forEach(userGroupDTO -> operationService
          .removeOperationFromGroup(userGroupDTO, "view", resourceId));
    }
    if (usersAdded != null) {
      usersAdded.forEach(userDTO -> operationService
          .addOperationToUser(userDTO, "view", resourceId, false));
    }
    if (userGroupsAdded != null) {
      userGroupsAdded.forEach(userGroupDTO -> operationService
          .addOperationToGroup(userGroupDTO, "view", resourceId, false));
    }
  }

  private void removeAllPermissions(ResourceDTO resourceDTO) {
      Set<String> usersOperationDTO = operationService
          .getAllowedUsersForOperation("view", resourceDTO.getObjectId(), false);
      Set<String> userGroupsOperationDTO = operationService
          .getAllowedGroupsForOperation("view", resourceDTO.getObjectId(), false);

      usersOperationDTO.forEach(
          user -> operationService.removeOperationFromUser(user, "view", resourceDTO.getId()));
      userGroupsOperationDTO.forEach(userGroups -> operationService
          .removeOperationFromGroup(userGroups, "view", resourceDTO.getId()));
  }

  /**
   * Saves a new application in database from yaml file.
   *
   * @param file the yaml file that has been uploaded.
   */
  public void saveApplicationFromYaml(MultipartFile file) {
    try {
      //Map to wdApplication
      ObjectMapper wdMapper = new ObjectMapper(new YAMLFactory());
      WdApplication wdApplication = wdMapper.readValue(file.getInputStream(), WdApplication.class);
      handleWdApplication(file.getInputStream(), wdApplication);

    } catch (IOException e) {
      log.warning(e.getMessage());
    }
  }

  private void handleWdApplication(InputStream is, WdApplication wdApplication) throws IOException {
    WdApplication existingWdApp = wdApplicationRepository
        .findByApplicationName(wdApplication.getApplicationName());
    String sha256 = cryptoDigestService.sha256(is);

    if (existingWdApp == null) {
      processWdApplication(wdApplication, sha256);
      processLexiconUtil
          .createLexiconValues(wdApplication.getLexicon(), this.mapper.mapToDTO(wdApplication));

    } else if (!existingWdApp.getChecksum().equals(sha256)) {

      if (existingWdApp.isEditedByUI()) {
        wdApplication.setActive(existingWdApp.isActive());
        wdApplication.setRestrictAccess(existingWdApp.isRestrictAccess());
      }
      wdApplication.setId(existingWdApp.getId());
      processWdApplication(wdApplication, sha256);
      processLexiconUtil
          .createLexiconValues(wdApplication.getLexicon(), this.mapper.mapToDTO(wdApplication));
    }
  }

  /**
   * Updates the file's SHA-256 checksum, saves the Web Desktop application and registers
   *
   * @param wdApplication The Web Desktop application
   * @param checksum      The file's SHA-256 checksum
   */
  private void processWdApplication(WdApplication wdApplication, String checksum) {
    if (wdApplication != null) {
      wdApplication.setChecksum(checksum);
      wdApplicationRepository.save(wdApplication);
      resourceWdApplicationService.createApplicationResource(wdApplication);
    }
  }

  /**
   * Finds user's attribute by attribute name.
   *
   * @param attributeName the attribute name to search for.
   * @return the responded userAttributeDTO.
   */
  public UserAttributeDTO findUserAttributeByName(String attributeName) {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof DefaultOAuth2User) {
      String userName = ((DefaultOAuth2User) principal).getName();
      UserDTO userDTO = userService.getUserByName(userName);
      for (UserAttributeDTO attribute : userDTO.getUserAttributes()) {
        if (attribute.getName().equalsIgnoreCase(attributeName)) {
          return attribute;
        }
      }
    }
    return null;
  }
}
