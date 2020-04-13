package com.eurodyn.qlack.webdesktop.applications.management.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.eurodyn.qlack.fuse.aaa.service.UserGroupService;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.webdesktop.applications.management.service.UserGroupManagementService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(PowerMockRunner.class)
public class UserGroupManagementControllerTest {

  private MockMvc mockMvc;
  @Mock
  private UserService userService;
  @Mock
  private UserGroupService userGroupService;
  @Mock
  private UserGroupManagementService userGroupManagementService;
  private String resource = "/api/usergroup";

  @Before
  public void init() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(
        new UserGroupManagementController(userGroupService, userService,
            userGroupManagementService))
        .build();
  }

  @Test
  public void findUsersByGroupIdTest() throws Exception {
    mockMvc.perform(get(resource + "/users/{groupId}", "groupId")
        .accept(MediaType.ALL))
        .andExpect(status().isOk());
  }

  @Test
  public void findGroupsByTermTest() throws Exception {
    mockMvc.perform(get(resource + "/search/{term}", "term")
        .accept(MediaType.ALL))
        .andExpect(status().isOk());
  }

  @Test
  public void findGroupByIdTest() throws Exception {
    mockMvc.perform(get(resource + "/{groupId}", "groupId")
        .accept(MediaType.ALL))
        .andExpect(status().isOk());
  }

  @Test
  public void findAllGroupsTest() throws Exception {
    mockMvc.perform(get(resource + "/groups")
        .accept(MediaType.ALL))
        .andExpect(status().isOk());
  }

  @Test
  public void uploadTest() throws Exception {
    JSONObject json = new JSONObject();
    json.put("userGroupName", "userGroup");
    mockMvc.perform(MockMvcRequestBuilders.post(resource)
        .content(json.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void deleteTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete(resource + "/{userGroupId}", "userGroupId")
        .accept(MediaType.ALL))
        .andExpect(status().isOk());
  }
}
