package com.eurodyn.qlack.webdesktop.translations.management.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class IndexControllerTest {

  private MockMvc mockMvc;
  @MockBean
  private IndexController indexController;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders
        .standaloneSetup(indexController).build();
  }

  @Test
  public void getConfigurationSuccessTest() throws Exception {

    mockMvc.perform(get("/webdesktop/translations/management/configuration")
        .accept(MediaType.ALL))
        .andExpect(status().isOk());
  }

  @Test
  public void getConfigurationNotFoundTest() throws Exception {
    mockMvc.perform(get("/webdesktop/translations/management/test")
        .accept(MediaType.ALL))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getLogoSuccessTest() throws Exception {
    mockMvc.perform(get("/webdesktop/translations/management/logo/icon")
        .accept(MediaType.IMAGE_PNG_VALUE))
        .andExpect(status().isOk());
  }

  @Test
  public void getLogoNotFoundTest() throws Exception {
    mockMvc.perform(get("/webdesktop/translations/management/logo")
        .accept(MediaType.IMAGE_PNG_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getSmallLogoSuccessTest() throws Exception {
    mockMvc.perform(get("/webdesktop/translations/management/logo/icon_small")
        .accept(MediaType.IMAGE_PNG_VALUE))
        .andExpect(status().isOk());
  }

  @Test
  public void getSmallLogoNotFoundTest() throws Exception {
    mockMvc.perform(get("/webdesktop/translations/management/logo")
        .accept(MediaType.IMAGE_PNG_VALUE))
        .andExpect(status().isNotFound());
  }
}
