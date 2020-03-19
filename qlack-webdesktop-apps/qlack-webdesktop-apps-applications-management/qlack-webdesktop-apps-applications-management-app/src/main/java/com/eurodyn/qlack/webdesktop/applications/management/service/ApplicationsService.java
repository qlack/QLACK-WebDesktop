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
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationManagementDTO;
import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This Service method contains all the actions related to the applications. It uses the related Service of the QLACK
 * WebDesktop Service.
 *
 * @author EUROPEAN DYNAMICS SA
 */
@Log
@Service
@RequiredArgsConstructor
public class ApplicationsService {

  private WdApplicationService wdApplicationService;
  private ResourceService resourceService;
  private OperationService operationService;
  private UserService userService;
  private UserGroupService userGroupService;

  @Autowired
  public ApplicationsService(WdApplicationService wdApplicationService,
      ResourceService resourceService, OperationService operationService,
      UserService userService, UserGroupService userGroupService) {
    this.wdApplicationService = wdApplicationService;
    this.resourceService = resourceService;
    this.operationService = operationService;
    this.userService = userService;
    this.userGroupService = userGroupService;
  }

  /**
   * This method returns all the QLACK Web Desktop applications.
   *
   * @return a responded entity containing all the applications.
   */
  public ResponseEntity<Page<WdApplicationDTO>> getApplications() {
    //    UserDTO userId = new UserDTO();
    //    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //    if (("sso").equalsIgnoreCase(profileManagerService.getActiveProfile())
    //        && principal instanceof DefaultOAuth2User){
    //      String userName = ((DefaultOAuth2User) principal).getName();
    //      userId = userService.getUserByName(userName);
    //    }
    //    if (userId.isSuperadmin()) {
    //      Page<WdApplicationDTO> wdApplicationDTOS = new PageImpl<>(wdApplicationService.findAllApplications());
    //      return ResponseEntity.ok(wdApplicationDTOS);
    //    } else {
    //      return ResponseEntity.notFound().build();
    //    }
    Page<WdApplicationDTO> wdApplicationDTOS = new PageImpl<>(
        wdApplicationService.findAllApplications());
    return ResponseEntity.ok(wdApplicationDTOS);
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
