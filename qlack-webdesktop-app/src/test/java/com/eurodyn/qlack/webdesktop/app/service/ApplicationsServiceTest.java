package com.eurodyn.qlack.webdesktop.app.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eurodyn.qlack.fuse.aaa.dto.ResourceDTO;
import com.eurodyn.qlack.fuse.aaa.service.OperationService;
import com.eurodyn.qlack.fuse.aaa.service.ResourceService;
import com.eurodyn.qlack.fuse.crypto.service.CryptoDigestService;
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
import com.google.common.io.Files;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationsServiceTest {

  @InjectMocks
  private ApplicationsService applicationsService;

  @Mock
  private WdApplicationService wdApplicationService;

  @Mock
  private WdApplicationRepository wdApplicationRepository;

  @Mock
  private ProcessLexiconUtil processLexiconUtil;

  @Mock
  private WdApplicationMapper mapper;

  @Mock
  private ResourceWdApplicationService resourceWdApplicationService;

  @Mock
  private OperationService operationService;

  @Mock
  private ResourceService resourceService;

  @Mock
  private CryptoDigestService cryptoDigestService;

  @Mock
  private ProfileManagerService profileManagerService;

  @Mock
  private StringUtils stringUtils;

  private WdApplicationManagementDTO wdApplicationManagementDTO;
  private WdApplication wdApplication;
  private WdApplicationDTO wdApplicationDTO;
  private Collection<String> usersList;
  private ResourceDTO resourceDTO;
  private Set<String> usersOperationDTO;

  @Before
  public void onInit() {
    wdApplication = new WdApplication();
    wdApplication.setApplicationName("eurodyn");

    wdApplicationDTO = new WdApplicationDTO();
    wdApplicationDTO.setId(UUID.randomUUID().toString());

    wdApplicationManagementDTO = new WdApplicationManagementDTO();
    wdApplicationManagementDTO.setId(UUID.randomUUID().toString());

    usersList = new ArrayList<>();
    usersList.add("user");

    usersOperationDTO = new HashSet<>();
    usersOperationDTO.add("view");
    usersOperationDTO.add("operation");

    resourceDTO = new ResourceDTO();
    resourceDTO.setId("resourceId");
    resourceDTO.setObjectId("objectId");
  }

  @Test
  public void saveTest() {
    wdApplicationDTO.setApplicationName("applicationName");
    wdApplicationManagementDTO.setDetails(wdApplicationDTO);
    when(wdApplicationService
        .findApplicationByName(anyString())).thenReturn(wdApplication);
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
    when(wdApplicationService.findApplicationByName(anyString())).thenReturn(wdApplication);
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
    when(wdApplicationRepository.save(any())).thenReturn(wdApplication);
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
    when(wdApplicationRepository.save(any())).thenReturn(wdApplication);
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
    when(wdApplicationRepository.save(any())).thenReturn(wdApplication);
    applicationsService.saveApplicationFromYaml(multipartFile);
    verify(wdApplicationRepository, times(1)).findByApplicationName(any());
    verify(processLexiconUtil, times(1)).createLexiconValues(any(), any());
  }

}
