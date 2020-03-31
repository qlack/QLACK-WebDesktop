package com.eurodyn.qlack.webdesktop.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.eurodyn.qlack.webdesktop.app.service.ApplicationsService;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationManagementDTO;
import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
public class WdApplicationControllerTest {

  @MockBean
  private WdApplicationService wdApplicationService;
  @MockBean
  private ApplicationsService applicationsService;
  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders
        .standaloneSetup(new WdApplicationController(wdApplicationService, applicationsService))
        .build();
  }

  @Test
  public void getFilteredActiveApplicationsTest() throws Exception {
    mockMvc.perform(get("/api/application/")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void getTranslationsTest() throws Exception {
    mockMvc.perform(get("/api/application/translations")
        .accept(MediaType.APPLICATION_JSON)
        .param("lang", "en"))
        .andExpect(status().isOk());
  }

  @Test
  public void getApplicationByIdTest() throws Exception {
    mockMvc.perform(get("/api/application/{id}", "1")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void getApplicationByNameTest() throws Exception {
    mockMvc.perform(get("/api/application/byName/{name}", "applicationName")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void saveApplicationInvalidObjectTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/api/application/save")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(new WdApplicationManagementDTO()))
        .accept(MediaType.ALL))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateApplicationInvalidObjectTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/api/application/update/{id}", "id")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(new WdApplicationManagementDTO()))
        .accept(MediaType.ALL))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void uploadApplicationTest() throws Exception {
    File file = new File(this.getClass().getResource("/configurationTest.yaml").getFile());
    MockMultipartFile multipartFile = new MockMultipartFile("file", "configurationTest.yaml",
        "text/plain", Files.toByteArray(file));
    mockMvc.perform(MockMvcRequestBuilders.multipart("/api/application/upload")
        .file(multipartFile)
        .accept(MediaType.MULTIPART_FORM_DATA_VALUE))
        .andExpect(status().isOk());
  }

}
