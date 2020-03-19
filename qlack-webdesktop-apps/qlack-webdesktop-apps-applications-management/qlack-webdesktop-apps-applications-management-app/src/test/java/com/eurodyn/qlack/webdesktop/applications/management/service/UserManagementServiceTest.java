package com.eurodyn.qlack.webdesktop.applications.management.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.webdesktop.applications.management.dto.UserManagementDTO;
import com.eurodyn.qlack.webdesktop.applications.management.util.InitTestValues;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collection;

@RunWith(MockitoJUnitRunner.class)
public class UserManagementServiceTest {

  @InjectMocks
  UserManagementService userManagementService;
  @Mock
  UserService userService;
  private UserManagementDTO userManagementDTO;
  private InitTestValues initTestValues;
  private Collection<String> groupDtos;

  @Before
  public void onInit() {
    initTestValues = new InitTestValues();
    userManagementDTO = initTestValues.createUserManagementDto();
    groupDtos = initTestValues.createUserGroupStringList();
  }

  @Test
  public void saveAllAddUserGroupsTest() {
    userManagementDTO.setGroupsAdded(groupDtos);
    userManagementService.saveAll(userManagementDTO);
    verify(userService, times(1)).addUserGroups(any(), any());
    verify(userService, times(0)).removeUserGroups(any(), any());
  }

  @Test
  public void saveAllRemoveUserGroupsTest() {
    userManagementDTO.setGroupsRemoved(groupDtos);
    userManagementService.saveAll(userManagementDTO);
    verify(userService, times(1)).removeUserGroups(any(), any());
    verify(userService, times(0)).addUserGroups(any(), any());
  }

  @Test
  public void findUserByNameTest() {
    userManagementService.findUserByName(userManagementDTO.getUsername());
    verify(userService, times(1)).getUserByName(userManagementDTO.getUsername());
  }
}
