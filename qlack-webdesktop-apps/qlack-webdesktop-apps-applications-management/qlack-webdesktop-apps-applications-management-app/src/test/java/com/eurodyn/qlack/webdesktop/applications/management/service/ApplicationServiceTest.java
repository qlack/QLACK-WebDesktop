package com.eurodyn.qlack.webdesktop.applications.management.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
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
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationManagementDTO;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;
import com.eurodyn.qlack.webdesktop.common.repository.WdApplicationRepository;
import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
import com.eurodyn.qlack.webdesktop.common.util.ProcessLexiconUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceTest {

  @InjectMocks
  private ApplicationsService applicationsService;

  @Mock
  private WdApplicationService wdApplicationService;
  @Mock
  private ProcessLexiconUtil processLexiconUtil;
  @Mock
  private ResourceService resourceService;
  @Mock
  private WdApplicationRepository wdApplicationRepository;
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
