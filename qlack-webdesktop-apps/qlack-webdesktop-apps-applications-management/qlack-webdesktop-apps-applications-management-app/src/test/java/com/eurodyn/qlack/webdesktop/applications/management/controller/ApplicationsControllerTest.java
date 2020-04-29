package com.eurodyn.qlack.webdesktop.applications.management.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.eurodyn.qlack.webdesktop.applications.management.service.ApplicationsService;
import com.eurodyn.qlack.webdesktop.common.service.ActiveProfileService;
import com.eurodyn.qlack.webdesktop.common.service.ProfileManagerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationsControllerTest {

  @Autowired
  private MockMvc mvc;
  @Mock
  private ApplicationsService applicationsService;
  @Mock
  private ProfileManagerService profileManagerService;
  @Mock
  private ActiveProfileService activeProfileService;

  @Before
  public void setup() {
    this.mvc = MockMvcBuilders
        .standaloneSetup(new ApplicationsController(applicationsService, profileManagerService, activeProfileService))
        .build();
  }

  @Test
  public void getApplicationsTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/applications")
        .accept(MediaType.ALL))
        .andExpect(status().isOk());
  }

  @Test
  public void getApplicationByIdTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/applications/{id}", "id")
        .accept(MediaType.ALL))
        .andExpect(status().isOk());
  }

  @Test
  public void getTranslationsTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/translations")
        .param("lang", "en")
        .accept(MediaType.ALL))
        .andExpect(status().isOk());
  }

  @Test
  public void getUserAttributeByNameTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/user/attributes/{attributeName}", "name")
        .accept(MediaType.ALL))
        .andExpect(status().isOk());
  }

  @Test
  public void getActiveProfileTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/activeProfile")
        .accept(MediaType.ALL))
        .andExpect(status().isOk());
  }


}
