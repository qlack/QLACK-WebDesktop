package com.eurodyn.qlack.webdesktop.applications.management.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;
import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Map;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceTest {

  @Mock private ApplicationsService applicationsService;
  @Mock private WdApplicationService wdApplicationService;
  @Mock private WdApplicationDTO wdApplicationDTO;
  private WdApplication wdApplication;

  @Before
  public void onInit(){
    wdApplication = new WdApplication();
    wdApplication.setApplicationName("eurodyn");

    wdApplicationDTO = new WdApplicationDTO();
    wdApplicationDTO.setId(UUID.randomUUID().toString());
  }

  @Test
  public void getApplicationByIdTest(){
    when(applicationsService.getApplicationById(anyString())).thenReturn(wdApplicationDTO);
    WdApplicationDTO wdApplicationNew = applicationsService
        .getApplicationById(wdApplicationDTO.getId());
    assertNotNull(wdApplicationNew);
  }

  @Test
  public void getTranslationsTest(){
    Map<String, Map<String, String>> translations = applicationsService.getTranslations("en");
    assertNotNull(translations);
  }

  @Test
  public void findApplicationByNameTest(){
    when(applicationsService.findApplicationByName(anyString())).thenReturn(wdApplication);
    WdApplication wdApplicationNew = applicationsService.findApplicationByName(wdApplication.getApplicationName());
    assertNotNull(wdApplicationNew);
  }

  @Test
  public void saveApplicationFromYamlTest(){
    MockMultipartFile file = new MockMultipartFile("data", "usermanagement.wd.application.yaml",
        "text/plain", "usermanagement.wd.application.yaml".getBytes());
    applicationsService.saveApplicationFromYaml(file);
    verify(applicationsService, times(1)).saveApplicationFromYaml(file);
  }
}
