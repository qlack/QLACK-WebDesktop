package com.eurodyn.qlack.webdesktop.applications.management.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eurodyn.qlack.fuse.aaa.dto.UserGroupDTO;
import com.eurodyn.qlack.fuse.aaa.service.UserGroupService;
import com.eurodyn.qlack.webdesktop.applications.management.dto.UserGroupManagementDTO;
import com.eurodyn.qlack.webdesktop.applications.management.util.InitTestValues;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserGroupManagementServiceTest {

  @InjectMocks
  private UserGroupManagementService userGroupManagementService;
  @Mock
  private UserGroupService userGroupService;
  @Mock
  private UserGroupDTO userGroupDTO;

  private UserGroupManagementDTO userGroupManagementDTO;
  private InitTestValues initTestValues;
  private Collection<String> userDTOS;


  @Before
  public void onInit() {
    initTestValues = new InitTestValues();
    userGroupManagementDTO = initTestValues.createUserGroupManagementDto();
    userDTOS = initTestValues.createUsersStringList();
  }

  @Test
  public void uploadCreateUserGroupTest() {
    userGroupManagementDTO.setId("0");
    userGroupManagementService.upload(userGroupManagementDTO);
    verify(userGroupService, times(1)).createGroup(any());
    verify(userGroupService, times(0)).updateGroup(any());
  }

  @Test
  public void uploadUpdateUserGroupTest() {
    userGroupManagementService.upload(userGroupManagementDTO);
    verify(userGroupService, times(1)).updateGroup(any());
    verify(userGroupService, times(0)).createGroup(any());
  }

  @Test
  public void uploadAddUsersTest() {
    userGroupManagementDTO.setUsersAdded(userDTOS);
    when(userGroupService.getGroupByName(userGroupManagementDTO.getName(), true))
        .thenReturn(userGroupDTO);
    userGroupManagementService.upload(userGroupManagementDTO);
    verify(userGroupService, times(1)).addUsers(any(), any());
    verify(userGroupService, times(0)).removeUsers(any(), any());
  }

  @Test
  public void uploadRemoveUsersTest() {
    userGroupManagementDTO.setUsersRemoved(userDTOS);
    when(userGroupService.getGroupByName(userGroupManagementDTO.getName(), true))
        .thenReturn(userGroupDTO);
    userGroupManagementService.upload(userGroupManagementDTO);
    verify(userGroupService, times(1)).removeUsers(any(), any());
    verify(userGroupService, times(0)).addUsers(any(), any());
  }

  @Test
  public void findAllGroupsTest() {
    userGroupManagementService.findAllGroups();
    verify(userGroupService, times(1)).listGroups();
  }
}
