package com.eurodyn.qlack.webdesktop.applications.management.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.eurodyn.qlack.fuse.aaa.service.UserGroupService;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.webdesktop.applications.management.service.UserManagementService;
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
public class UserManagementControllerTest {

  private MockMvc mockMvc;
  @Mock private UserService userService;
  @Mock private UserGroupService userGroupService;
  @Mock private UserManagementService userManagementService;
  private String resource = "/api/users";

  @Before
  public void init(){
    this.mockMvc = MockMvcBuilders.standaloneSetup(
        new UserManagementController(userService, userGroupService, userManagementService))
        .build();
  }

  @Test
  public void findGroupsByUserIdTest() throws Exception {
    mockMvc.perform(get(resource + "/groups/{userId}", "userId")
        .accept(MediaType.ALL))
        .andExpect(status().isOk());
  }

  @Test
  public void findUsersByTermTest() throws Exception {
    mockMvc.perform(get(resource + "/search/{term}", "term")
        .accept(MediaType.ALL))
        .andExpect(status().isOk());
  }

  @Test
  public void findUserByIdTest() throws Exception {
    mockMvc.perform(get(resource + "/{userId}", "userId")
        .accept(MediaType.ALL))
        .andExpect(status().isOk());
  }

  @Test
  public void findUserByNameTest() throws Exception {
    mockMvc.perform(get(resource + "/username/{name}", "name")
        .accept(MediaType.ALL))
        .andExpect(status().isOk());
  }

  @Test
  public void findGroupByNameTest() throws Exception {
    mockMvc.perform(get(resource + "/groupname/{name}", "name")
        .accept(MediaType.ALL))
        .andExpect(status().isOk());
  }

  @Test
  public void uploadTest() throws Exception {
    JSONObject json = new JSONObject();
    json.put("id", 2);
    mockMvc.perform(MockMvcRequestBuilders.post(resource)
        .content(json.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
