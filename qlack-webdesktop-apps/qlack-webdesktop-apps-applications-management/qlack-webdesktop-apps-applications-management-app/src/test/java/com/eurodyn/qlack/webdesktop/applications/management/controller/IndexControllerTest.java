package com.eurodyn.qlack.webdesktop.applications.management.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FileCopyUtils.class)
public class IndexControllerTest {

  private MockMvc mockMvc;

  private ResourceLoader resourceLoader = PowerMockito.mock(ResourceLoader.class);

  private Resource mockResource = PowerMockito.mock(Resource.class);


  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders
        .standaloneSetup(new IndexController(resourceLoader)).build();
    PowerMockito.mockStatic(FileCopyUtils.class);
  }

  @Test
  public void getConfigurationSuccessTest() throws Exception {
    String mockFile = "This is my file ";
    InputStream inputStream = new ByteArrayInputStream(mockFile.getBytes());
    when(mockResource.getInputStream()).thenReturn(inputStream);
    when(resourceLoader.getResource(anyString())).thenReturn(mockResource);

    mockMvc.perform(get("/webdesktop/applications/management/configuration")
        .accept(MediaType.ALL))
        .andExpect(status().isOk());
  }

  @Test
  public void getConfigurationNotFoundTest() throws Exception {

    when(resourceLoader.getResource(anyString())).thenReturn(mockResource);
    when(FileCopyUtils.copyToByteArray(mockResource.getInputStream())).thenThrow(new IOException());
    mockMvc.perform(get("/webdesktop/applications/management/configuration")
        .accept(MediaType.ALL))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getLogoSuccessTest() throws Exception {
    String mockFile = "This is an image ";
    InputStream inputStream = new ByteArrayInputStream(mockFile.getBytes());
    when(mockResource.getInputStream()).thenReturn(inputStream);
    when(resourceLoader.getResource(anyString())).thenReturn(mockResource);
    mockMvc.perform(get("/webdesktop/applications/management/logo/icon")
        .accept(MediaType.IMAGE_PNG_VALUE))
        .andExpect(status().isOk());
  }

  @Test
  public void getLogoNotFoundTest() throws Exception {
    when(resourceLoader.getResource(anyString())).thenReturn(mockResource);
    when(FileCopyUtils.copyToByteArray(mockResource.getInputStream())).thenThrow(new IOException());
    mockMvc.perform(get("/webdesktop/applications/management/logo/icon")
        .accept(MediaType.IMAGE_PNG_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getSmallLogoSuccessTest() throws Exception {
    String mockFile = "This is a small image ";
    InputStream inputStream = new ByteArrayInputStream(mockFile.getBytes());
    when(mockResource.getInputStream()).thenReturn(inputStream);
    when(resourceLoader.getResource(anyString())).thenReturn(mockResource);
    mockMvc.perform(get("/webdesktop/applications/management/logo/icon_small")
        .accept(MediaType.IMAGE_PNG_VALUE))
        .andExpect(status().isOk());
  }

  @Test
  public void getSmallLogoNotFoundTest() throws Exception {
    when(resourceLoader.getResource(anyString())).thenReturn(mockResource);
    when(FileCopyUtils.copyToByteArray(mockResource.getInputStream())).thenThrow(new IOException());
    mockMvc.perform(get("/webdesktop/applications/management/logo/icon_small")
        .accept(MediaType.IMAGE_PNG_VALUE))
        .andExpect(status().isNotFound());
  }

}
