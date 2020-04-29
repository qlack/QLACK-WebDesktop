package com.eurodyn.qlack.webdesktop.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.eurodyn.qlack.webdesktop.app.controller.UserController;
import com.eurodyn.qlack.webdesktop.app.service.UserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

  @MockBean
  private UserDetailsService userDetailsService;
  private MockMvc mockMvc;


  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders
        .standaloneSetup(new UserController(userDetailsService))
        .build();
  }

  @Test
  public void getUserAttributeByNameTest() throws Exception {
    mockMvc.perform(get("/api/user/attributes/{attributeName}", "attributeName")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void getUserAttributesTest() throws Exception {
    mockMvc.perform(get("/api/user/attributes")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
  @Test
  public void getActiveProfileTest() throws Exception {
    mockMvc.perform(get("/api/user/profile")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void getSystemDefaultLanguageTest() throws Exception {
    mockMvc.perform(get("/api/user/systemLanguage")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

}
