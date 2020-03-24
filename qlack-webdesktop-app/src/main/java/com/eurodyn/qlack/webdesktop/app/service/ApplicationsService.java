package com.eurodyn.qlack.webdesktop.app.service;

import com.eurodyn.qlack.fuse.aaa.dto.ResourceDTO;
import com.eurodyn.qlack.fuse.aaa.service.OperationService;
import com.eurodyn.qlack.fuse.aaa.service.ResourceService;
import com.eurodyn.qlack.fuse.crypto.service.CryptoDigestService;
import com.eurodyn.qlack.webdesktop.common.dto.LexiconDTO;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationManagementDTO;
import com.eurodyn.qlack.webdesktop.common.mapper.WdApplicationMapper;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;
import com.eurodyn.qlack.webdesktop.common.repository.WdApplicationRepository;
import com.eurodyn.qlack.webdesktop.common.service.ProfileManagerService;
import com.eurodyn.qlack.webdesktop.common.service.ResourceWdApplicationService;
import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
import com.eurodyn.qlack.webdesktop.common.util.ProcessLexiconUtil;
import com.eurodyn.qlack.webdesktop.common.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Log
@Service
public class ApplicationsService {

  private final static String SSO_PROFILE = "sso";

  private WdApplicationService wdApplicationService;
  private WdApplicationRepository wdApplicationRepository;
  private ProcessLexiconUtil processLexiconUtil;
  private WdApplicationMapper mapper;
  private ResourceWdApplicationService resourceWdApplicationService;
  private OperationService operationService;
  private ResourceService resourceService;
  private CryptoDigestService cryptoDigestService;
  private ProfileManagerService profileManagerService;
  private ZuulRouteService zuulRouteService;
  private StringUtils stringUtils;

  @Autowired
  @SuppressWarnings({"java:S107"})
  public ApplicationsService(WdApplicationService wdApplicationService,
      WdApplicationRepository wdApplicationRepository,
      ProcessLexiconUtil processLexiconUtil, WdApplicationMapper mapper,
      ResourceWdApplicationService resourceWdApplicationService,
      OperationService operationService, ResourceService resourceService,
      CryptoDigestService cryptoDigestService,
      ProfileManagerService profileManagerService, ZuulRouteService zuulRouteService, StringUtils stringUtils) {
    this.wdApplicationService = wdApplicationService;
    this.wdApplicationRepository = wdApplicationRepository;
    this.processLexiconUtil = processLexiconUtil;
    this.mapper = mapper;
    this.resourceWdApplicationService = resourceWdApplicationService;
    this.operationService = operationService;
    this.resourceService = resourceService;
    this.cryptoDigestService = cryptoDigestService;
    this.profileManagerService = profileManagerService;
    this.zuulRouteService = zuulRouteService;
    this.stringUtils = stringUtils;
  }

  /**
   * Saves a new wd application. An extra check must be done, in order to make sure that application Name is unique.
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

    if (stringUtils.isNotNullOrEmpty(newWdApplication.getProxyAppPath()) && stringUtils
        .isNotNullOrEmpty(newWdApplication.getAppUrl())) {
      zuulRouteService.addRoute(newWdApplication.getProxyAppPath(),
          newWdApplication.getAppUrl() + newWdApplication.getAppPath(), newWdApplication.getId());
      zuulRouteService.refresh();
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
    initWdApplicationDTO.setActive(wdApplication.isActive());
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

    if ((initWdApplicationDTO.isActive() != wdApplicationManagementDTO.getDetails().isActive()) && (
        stringUtils.isNotNullOrEmpty(wdApplication.getProxyAppPath()) && stringUtils
            .isNotNullOrEmpty(wdApplication.getAppUrl()))) {
      if (!wdApplicationManagementDTO.getDetails().isActive()) {
        zuulRouteService.removeRoute(wdApplication.getId());
      } else {
        zuulRouteService.addRoute(wdApplication.getProxyAppPath(),
            wdApplication.getAppUrl() + wdApplication.getAppPath(), wdApplication.getId());
      }
      zuulRouteService.refresh();
    }

    return ResponseEntity.status(HttpStatus.OK).body(wdApplication);
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

    if (!SSO_PROFILE.equalsIgnoreCase(profileManagerService.getActiveProfile())) {
      wdApplication.setRestrictAccess(false);
    }
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
   * @param checksum The file's SHA-256 checksum
   */
  private void processWdApplication(WdApplication wdApplication, String checksum) {
    if (wdApplication != null) {
      wdApplication.setChecksum(checksum);
      wdApplication = wdApplicationRepository.save(wdApplication);
      resourceWdApplicationService.createApplicationResource(wdApplication);

      if (stringUtils.isNotNullOrEmpty(wdApplication.getProxyAppPath()) && stringUtils
          .isNotNullOrEmpty(wdApplication.getAppUrl())) {
        zuulRouteService.addRoute(wdApplication.getProxyAppPath(),
            wdApplication.getAppUrl() + wdApplication.getAppPath(), wdApplication.getId());
        zuulRouteService.refresh();
      }
    }
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

}
