package com.eurodyn.qlack.webdesktop.applications.management.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eurodyn.qlack.fuse.aaa.dto.ResourceDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.service.OperationService;
import com.eurodyn.qlack.fuse.aaa.service.ResourceService;
import com.eurodyn.qlack.fuse.aaa.service.UserGroupService;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.fuse.crypto.service.CryptoDigestService;
import com.eurodyn.qlack.webdesktop.applications.management.dto.WdApplicationManagementDTO;
import com.eurodyn.qlack.webdesktop.applications.management.util.ProcessLexiconUtil;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.mapper.WdApplicationMapper;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;
import com.eurodyn.qlack.webdesktop.common.repository.WdApplicationRepository;
import com.eurodyn.qlack.webdesktop.common.service.ProfileManagerService;
import com.eurodyn.qlack.webdesktop.common.service.ResourceWdApplicationService;
import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceTest {

  @InjectMocks
  private ApplicationsService applicationsService;

  @Mock
  private WdApplicationService wdApplicationService;
  @Mock
  private ProcessLexiconUtil processLexiconUtil;
  @Mock
  private ResourceWdApplicationService resourceWdApplicationService;
  @Mock
  private ResourceService resourceService;
  @Mock
  private WdApplicationRepository wdApplicationRepository;
  @Mock
  private CryptoDigestService cryptoDigestService;
  @Mock
  private WdApplicationMapper wdApplicationMapper;
  @Mock
  private OperationService operationService;
  @Mock
  private UserService userService;
  @Mock
  private UserGroupService userGroupService;
  @Mock
  private DefaultOAuth2User defaultOAuth2User;
  @Mock
  private Authentication authentication;
  @Mock
  private SecurityContext securityContext;
  @Mock
  private Object principal;
  @Mock
  private ProfileManagerService profileManagerService;
  private WdApplicationManagementDTO wdApplicationManagementDTO;
  private WdApplication wdApplication;
  private WdApplicationDTO wdApplicationDTO;
  private ResourceDTO resourceDTO;
  private UserDTO userDTO;
  private Set<String> usersOperationDTO;
  private Collection<String> usersList;
  private Set<UserAttributeDTO> userAttributeDTOS;

  @Before
  public void onInit() {
    wdApplication = new WdApplication();
    wdApplication.setApplicationName("eurodyn");

    wdApplicationDTO = new WdApplicationDTO();
    wdApplicationDTO.setId(UUID.randomUUID().toString());

    wdApplicationManagementDTO = new WdApplicationManagementDTO();
    wdApplicationManagementDTO.setId(UUID.randomUUID().toString());

    resourceDTO = new ResourceDTO();
    resourceDTO.setId("resourceId");
    resourceDTO.setObjectId("objectId");

    usersOperationDTO = new HashSet<>();
    usersOperationDTO.add("view");
    usersOperationDTO.add("operation");

    usersList = new ArrayList<>();
    usersList.add("user");

    userAttributeDTOS = new HashSet<>(createUserAttributesDTO());

    userDTO = new UserDTO();
    userDTO.setUsername("userName");
    userDTO.setUserAttributes(userAttributeDTOS);
  }

  private List<UserAttributeDTO> createUserAttributesDTO() {
    List<UserAttributeDTO> userAttributesDTO = new ArrayList<>();
    userAttributesDTO.add(this.createUserAttributeDTO());

    UserAttributeDTO userAttributeDTO = new UserAttributeDTO();
    userAttributeDTO.setId("ef682d4c-be43-4a33-8262-8af497816277");
    userAttributeDTO.setName("company");
    userAttributeDTO.setData("European Dynamics");
    userAttributeDTO.setContentType("text");

    userAttributesDTO.add(userAttributeDTO);

    return userAttributesDTO;
  }

  private UserAttributeDTO createUserAttributeDTO() {
    UserAttributeDTO userAttributeDTO = new UserAttributeDTO();
    userAttributeDTO.setId("dca76ec3-0423-4a17-8287-afd311697dbf");
    userAttributeDTO.setName("fullName");
    userAttributeDTO.setData("FirstName LastName");
    userAttributeDTO.setContentType("text");

    return userAttributeDTO;
  }

  @Test
  public void getApplicationsTest() {
//    when(securityContext.getAuthentication()).thenReturn(authentication);
//    SecurityContextHolder.setContext(securityContext);
//    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
//        .thenReturn(defaultOAuth2User);
    //no sso profile is activated
    applicationsService.getApplications();
//    verify(wdApplicationService, times(0)).findAllApplications();

//    //if user is not superAdmin a 404 request is return, so none interactions with any service.
//    when(profileManagerService.getActiveProfile()).thenReturn("sso");
//    when(userService.getUserByName(any())).thenReturn(userDTO);
//    applicationsService.getApplications();
//    verify(wdApplicationService, times(0)).findAllApplications();

//    userDTO.setSuperadmin(true);
//    applicationsService.getApplications();
    verify(wdApplicationService, times(1)).findAllApplications();
  }

  @Test
  public void getApplicationByIdNullResourceTest() {
    applicationsService.getApplicationById(wdApplicationManagementDTO.getId());
    verify(resourceService, times(1)).getResourceByObjectId(any());
  }

  @Test
  public void getApplicationByIdWithResourceTest() {
    when(resourceService.getResourceByObjectId(any())).thenReturn(resourceDTO);
    applicationsService.getApplicationById(wdApplicationManagementDTO.getId());
    verify(resourceService, times(1)).getResourceByObjectId(any());
    verify(userService, times(1)).findUsers(any());
    verify(userGroupService, times(1)).findGroups(any());
  }

  @Test
  public void getTranslationsTest() {
    applicationsService.getTranslations("en");
    verify(wdApplicationService, times(1)).findTranslationsForLocale(any());
  }


  @Test
  public void saveTest() {
    wdApplicationDTO.setApplicationName("applicationName");
    wdApplicationManagementDTO.setDetails(wdApplicationDTO);
    applicationsService.save(wdApplicationManagementDTO);
    verify(wdApplicationService, times(2)).findApplicationByName(any());
  }

  @Test
  public void saveAlreadyExistsCodeTest() {
    wdApplicationDTO.setApplicationName("applicationName");
    wdApplication.setApplicationName("applicationName");
    wdApplicationManagementDTO.setDetails(wdApplicationDTO);
    when(wdApplicationService.findApplicationByName(any())).thenReturn(wdApplication);
    ResponseEntity response = applicationsService
        .save(wdApplicationManagementDTO);
    assertEquals("alreadyExistsCode", response.getBody().toString());
    verify(wdApplicationService, times(1)).findApplicationByName(any());
  }

  @Test
  public void saveAlreadyExistsCodeNotEqualsTest() {
    wdApplicationDTO.setApplicationName("applicationName");
    wdApplication.setApplicationName("otherName");
    wdApplicationManagementDTO.setDetails(wdApplicationDTO);
    when(wdApplicationService.findApplicationByName(any())).thenReturn(wdApplication);
    applicationsService.save(wdApplicationManagementDTO);
    verify(wdApplicationService, times(2)).findApplicationByName(any());
  }

  @Test
  public void saveUpdatePermissionsTest() {
    wdApplicationDTO.setApplicationName("applicationName");
    wdApplicationDTO.setRestrictAccess(true);
    wdApplicationManagementDTO.setDetails(wdApplicationDTO);
    applicationsService.save(wdApplicationManagementDTO);
    verify(wdApplicationService, times(2)).findApplicationByName(any());
  }

  @Test
  public void updateApplicationUpdatePermissionsWithCollectionsTest() {
    wdApplicationDTO.setApplicationName("applicationName");
    wdApplicationManagementDTO.setDetails(wdApplicationDTO);
    wdApplicationManagementDTO.getDetails().setRestrictAccess(true);
    wdApplicationManagementDTO.setUsersAdded(usersList);
    wdApplicationManagementDTO.setUsersRemoved(usersList);
    wdApplicationManagementDTO.setGroupsAdded(usersList);
    wdApplicationManagementDTO.setGroupsRemoved(usersList);
    wdApplication.setRestrictAccess(true);
    when(wdApplicationService.findApplicationByName(any())).thenReturn(wdApplication);
    applicationsService.save(wdApplicationManagementDTO);
    verify(wdApplicationService, times(2)).findApplicationByName(any());
  }

  @Test
  public void updateTest() {
    wdApplicationDTO.setRestrictAccess(false);
    wdApplication.setRestrictAccess(false);
    wdApplicationManagementDTO.setDetails(wdApplicationDTO);
    when(wdApplicationRepository.fetchById(any())).thenReturn(wdApplication);
    when(resourceService.getResourceByObjectId(any())).thenReturn(resourceDTO);
    applicationsService.update(wdApplicationManagementDTO);

    wdApplicationDTO.setRestrictAccess(true);
    wdApplication.setRestrictAccess(false);
    applicationsService.update(wdApplicationManagementDTO);

    wdApplicationDTO.setRestrictAccess(true);
    wdApplication.setRestrictAccess(true);
    applicationsService.update(wdApplicationManagementDTO);

    verify(wdApplicationRepository, times(3)).fetchById(any());
    verify(wdApplicationService, times(3)).saveApplication(any());
    verify(resourceService, times(3)).getResourceByObjectId(any());
  }

  @Test
  public void updateApplicationRemoveAllPermissionsTest() {
    wdApplicationDTO.setRestrictAccess(false);
    wdApplication.setRestrictAccess(true);
    wdApplicationManagementDTO.setDetails(wdApplicationDTO);
    when(wdApplicationRepository.fetchById(any())).thenReturn(wdApplication);
    when(resourceService.getResourceByObjectId(any())).thenReturn(resourceDTO);
    when(operationService.getAllowedUsersForOperation(any(), any(), anyBoolean()))
        .thenReturn(usersOperationDTO);
    when(operationService.getAllowedGroupsForOperation(any(), any(), anyBoolean()))
        .thenReturn(usersOperationDTO);

    applicationsService.update(wdApplicationManagementDTO);
    verify(wdApplicationRepository, times(1)).fetchById(any());
    verify(wdApplicationService, times(1)).saveApplication(any());
    verify(resourceService, times(1)).getResourceByObjectId(any());
    verify(operationService, times(1)).getAllowedUsersForOperation(any(), any(), anyBoolean());
    verify(operationService, times(1)).getAllowedGroupsForOperation(any(), any(), anyBoolean());
  }


  @Test
  public void saveApplicationFromYamlTest() throws IOException {
    File file = new File(this.getClass().getResource("/configurationTest.yaml").getFile());
    MockMultipartFile multipartFile = new MockMultipartFile("data", "configurationTest.yaml",
        "text/plain", Files.toByteArray(file));
    applicationsService.saveApplicationFromYaml(multipartFile);
    verify(wdApplicationRepository, times(1)).findByApplicationName(any());
    verify(processLexiconUtil, times(1)).createLexiconValues(any(), any());
  }

  @Test
  public void saveApplicationFromYamlExistingWdAppTest() throws IOException {
    File file = new File(this.getClass().getResource("/configurationTest.yaml").getFile());
    MockMultipartFile multipartFile = new MockMultipartFile("data", "configurationTest.yaml",
        "text/plain", Files.toByteArray(file));
    wdApplication.setChecksum("checksum");
    when(wdApplicationRepository.findByApplicationName(any())).thenReturn(wdApplication);
    applicationsService.saveApplicationFromYaml(multipartFile);
    verify(wdApplicationRepository, times(1)).findByApplicationName(any());
    verify(processLexiconUtil, times(1)).createLexiconValues(any(), any());
  }

  @Test
  public void saveApplicationFromYamlExistingWdAppisEditedByUITest() throws IOException {
    File file = new File(this.getClass().getResource("/configurationTest.yaml").getFile());
    MockMultipartFile multipartFile = new MockMultipartFile("data", "configurationTest.yaml",
        "text/plain", Files.toByteArray(file));
    wdApplication.setChecksum("checksum");
    wdApplication.setEditedByUI(true);
    when(wdApplicationRepository.findByApplicationName(any())).thenReturn(wdApplication);
    applicationsService.saveApplicationFromYaml(multipartFile);
    verify(wdApplicationRepository, times(1)).findByApplicationName(any());
    verify(processLexiconUtil, times(1)).createLexiconValues(any(), any());
  }

  @Test
  public void findUserAttributeByNameNullTest() {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .thenReturn(defaultOAuth2User);
    when(userService.getUserByName(any())).thenReturn(userDTO);

    assertNull(applicationsService.findUserAttributeByName("email"));
    verify(userService, times(1)).getUserByName(any());
  }

  @Test
  public void findUserAttributeByNameTest() {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .thenReturn(defaultOAuth2User);
    when(userService.getUserByName(any())).thenReturn(userDTO);

    UserAttributeDTO result = applicationsService.findUserAttributeByName("company");
    assertEquals(userDTO.getAttribute("company").get().getName(), result.getName());
    verify(userService, times(1)).getUserByName(any());
  }

  @Test
  public void findUserAttributeByNameFailTest() {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .thenReturn(principal);

    assertNull(applicationsService.findUserAttributeByName("company"));
    verify(userService, times(0)).getUserByName(any());
  }
}
