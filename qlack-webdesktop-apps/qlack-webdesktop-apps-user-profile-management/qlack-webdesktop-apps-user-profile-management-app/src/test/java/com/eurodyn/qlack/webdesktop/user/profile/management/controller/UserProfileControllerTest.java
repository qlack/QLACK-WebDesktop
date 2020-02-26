package com.eurodyn.qlack.webdesktop.user.profile.management.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.eurodyn.qlack.webdesktop.user.profile.management.dto.UserDetailsDTO;
import com.eurodyn.qlack.webdesktop.user.profile.management.service.UserProfileService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserProfileControllerTest {

  @MockBean
  private UserProfileService userProfileService;
  private MockMvc mockMvc;
  @Mock
  private UserDetailsDTO userDetailsDTO;
  @Mock
  private MockMultipartFile profileImage;
  @Mock
  private MockMultipartFile backgroundImage;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders
        .standaloneSetup(new UserProfileController(userProfileService)).build();
  }

  @Test
  public void getLanguagesTest() throws Exception {
    mockMvc.perform(get("/api/languages/{includeInactive}", true)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void getTranslationsTest() throws Exception {
    mockMvc.perform(get("/api/translations?lang=en")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void saveUserAttributesTest() throws Exception {
    mockMvc.perform(post("/api/user/attributes/save")
        .content(String.valueOf(userDetailsDTO))
        .content("false")
        .param("profileImage", String.valueOf(profileImage))
        .param("backgroundImage", String.valueOf(backgroundImage))
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
  public void getUserAttributeByNameTest() throws Exception {
    mockMvc.perform(get("/api/user/attributes/{attributeName}","attributeName")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

}
