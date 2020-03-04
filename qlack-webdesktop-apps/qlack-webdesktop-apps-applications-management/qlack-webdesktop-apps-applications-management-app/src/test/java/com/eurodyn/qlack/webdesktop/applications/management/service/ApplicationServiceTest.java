package com.eurodyn.qlack.webdesktop.applications.management.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eurodyn.qlack.fuse.aaa.dto.ResourceDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserGroupDTO;
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
import com.eurodyn.qlack.webdesktop.common.service.ResourceWdApplicationService;
import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
import com.google.common.io.Files;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Map;
import java.util.UUID;
import org.springframework.test.context.TestExecutionListeners;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceTest {

  @InjectMocks
  private ApplicationsService applicationsService;

  @Mock private WdApplicationService wdApplicationService;
  @Mock private ProcessLexiconUtil processLexiconUtil;
  @Mock private ResourceWdApplicationService resourceWdApplicationService;
  @Mock private WdApplicationDTO wdApplicationDTO;
  @Mock private ResourceService resourceService;
  @Mock private WdApplicationRepository wdApplicationRepository;
  @Mock private CryptoDigestService cryptoDigestService;
  @Mock private WdApplicationMapper wdApplicationMapper;
  @Mock private OperationService operationService;
  @Mock private UserService userService;
  @Mock private UserGroupService userGroupService;
  private WdApplicationManagementDTO wdApplicationManagementDTO;
  private WdApplication wdApplication;
  private ResourceDTO resourceDTO;
  private Set<String> usersOperationDTO;
  private Collection<String> usersList;

  @Before
  public void onInit(){
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
  }

  @Test
  public void getApplicationsTest(){
    applicationsService.getApplications();
    verify(wdApplicationService, times(1)).findAllApplications();
  }

  @Test
  public void getApplicationByIdNullResourceTest(){
    applicationsService.getApplicationById(wdApplicationManagementDTO.getId());
    verify(resourceService, times(1)).getResourceByObjectId(any());
  }

  @Test
  public void getApplicationByIdWithResourceTest(){
    when(resourceService.getResourceByObjectId(any())).thenReturn(resourceDTO);
    applicationsService.getApplicationById(wdApplicationManagementDTO.getId());
    verify(resourceService, times(1)).getResourceByObjectId(any());
    verify(userService, times(1)).findUsers(any());
    verify(userGroupService, times(1)).findGroups(any());
  }

  @Test
  public void getTranslationsTest(){
    applicationsService.getTranslations("en");
    verify(wdApplicationService, times(1)).findTranslationsForLocale(any());
  }

  @Test
  public void findApplicationByNameTest(){
    applicationsService.findApplicationByName(wdApplication.getApplicationName());
    verify(wdApplicationService, times(1)).findApplicationByName(any());
  }

  @Test
  public void updateApplicationTest(){
    wdApplicationDTO.setApplicationName("applicationName");
    wdApplicationManagementDTO.setDetails(wdApplicationDTO);
    applicationsService.updateApplication(wdApplicationManagementDTO);
    verify(wdApplicationService, times(2)).findApplicationByName(any());
  }

  @Test
  public void updateApplicationAlreadyExistsCodeTest(){
    wdApplicationDTO.setId(null);
    wdApplicationDTO.setApplicationName("applicationName");
    wdApplication.setApplicationName("applicationName");
    wdApplicationManagementDTO.setDetails(wdApplicationDTO);
    when(wdApplicationService.findApplicationByName(any())).thenReturn(wdApplication);
    ResponseEntity response = applicationsService
        .updateApplication(wdApplicationManagementDTO);
    assertEquals("alreadyExistsCode", response.getBody().toString());
    verify(wdApplicationService, times(1)).findApplicationByName(any());
  }

  @Test
  public void updateApplicationOnlyTwoFieldsTest(){
    wdApplicationDTO.setApplicationName("applicationName");
    wdApplication.setId("wdApplicationId");
    wdApplicationManagementDTO.setDetails(wdApplicationDTO);
    when(wdApplicationService.findApplicationByName(any())).thenReturn(wdApplication);
    applicationsService.updateApplication(wdApplicationManagementDTO);
    verify(wdApplicationService, times(2)).findApplicationByName(any());
  }

  @Test
  public void updateApplicationCreateLexiconTest(){
    wdApplicationManagementDTO.setId(null);
    wdApplicationDTO.setId(null);
    wdApplicationDTO.setApplicationName("applicationName");
    wdApplicationManagementDTO.setDetails(wdApplicationDTO);
    wdApplicationManagementDTO.getDetails().setRestrictAccess(true);
    when(wdApplicationService.findApplicationByName(any())).thenReturn(wdApplication);
    applicationsService.updateApplication(wdApplicationManagementDTO);
    verify(wdApplicationService, times(2)).findApplicationByName(any());
  }

  @Test
  public void updateApplicationRemoveAllPermissionsTest(){
    wdApplicationManagementDTO.setId(null);
    wdApplicationDTO.setId(null);
    wdApplicationDTO.setApplicationName("applicationName");
    wdApplicationManagementDTO.setDetails(wdApplicationDTO);
    wdApplication.setRestrictAccess(true);
    when(wdApplicationService.findApplicationByName(any())).thenReturn(wdApplication);
    when(resourceService.getResourceByObjectId(any())).thenReturn(resourceDTO);
    when(operationService.getAllowedUsersForOperation(any(), any(), anyBoolean())).thenReturn(usersOperationDTO);
    when(operationService.getAllowedGroupsForOperation(any(), any(), anyBoolean())).thenReturn(usersOperationDTO);
    applicationsService.updateApplication(wdApplicationManagementDTO);
    verify(wdApplicationService, times(2)).findApplicationByName(any());
  }

  @Test
  public void updateApplicationUpdatePermissionsTest(){
    wdApplicationDTO.setApplicationName("applicationName");
    wdApplicationManagementDTO.setDetails(wdApplicationDTO);
    wdApplicationManagementDTO.getDetails().setRestrictAccess(true);
    wdApplication.setRestrictAccess(true);
    when(wdApplicationService.findApplicationByName(any())).thenReturn(wdApplication);
    when(resourceService.getResourceByObjectId(any())).thenReturn(resourceDTO);
    applicationsService.updateApplication(wdApplicationManagementDTO);
    verify(wdApplicationService, times(2)).findApplicationByName(any());
  }

  @Test
  public void updateApplicationUpdatePermissionsWithCollectionsTest(){
    wdApplicationDTO.setApplicationName("applicationName");
    wdApplicationManagementDTO.setDetails(wdApplicationDTO);
    wdApplicationManagementDTO.getDetails().setRestrictAccess(true);
    wdApplicationManagementDTO.setUsersAdded(usersList);
    wdApplicationManagementDTO.setUsersRemoved(usersList);
    wdApplicationManagementDTO.setGroupsAdded(usersList);
    wdApplicationManagementDTO.setGroupsRemoved(usersList);
    wdApplication.setRestrictAccess(true);
    when(wdApplicationService.findApplicationByName(any())).thenReturn(wdApplication);
    when(resourceService.getResourceByObjectId(any())).thenReturn(resourceDTO);
    applicationsService.updateApplication(wdApplicationManagementDTO);
    verify(wdApplicationService, times(2)).findApplicationByName(any());
  }

  @Test
  public void saveApplicationFromYamlTest() throws IOException {
    ClassLoader classLoader = new ApplicationServiceTest().getClass().getClassLoader();
    File file = new File(classLoader.getResource("configurationTest.yaml").getFile());
    MockMultipartFile multipartFile = new MockMultipartFile("data", "configurationTest.yaml",
        "text/plain", Files.toByteArray(file));
    applicationsService.saveApplicationFromYaml(multipartFile);
    verify(wdApplicationRepository, times(1)).findByApplicationName(any());
    verify(processLexiconUtil, times(1)).createLexiconValues(any(), any());
  }

  @Test
  public void saveApplicationFromYamlExistingWdAppTest() throws IOException {
    ClassLoader classLoader = new ApplicationServiceTest().getClass().getClassLoader();
    File file = new File(classLoader.getResource("configurationTest.yaml").getFile());
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
    ClassLoader classLoader = new ApplicationServiceTest().getClass().getClassLoader();
    File file = new File(classLoader.getResource("configurationTest.yaml").getFile());
    MockMultipartFile multipartFile = new MockMultipartFile("data", "configurationTest.yaml",
        "text/plain", Files.toByteArray(file));
    wdApplication.setChecksum("checksum");
    wdApplication.setEditedByUI(true);
    when(wdApplicationRepository.findByApplicationName(any())).thenReturn(wdApplication);
    applicationsService.saveApplicationFromYaml(multipartFile);
    verify(wdApplicationRepository, times(1)).findByApplicationName(any());
    verify(processLexiconUtil, times(1)).createLexiconValues(any(), any());
  }
}
