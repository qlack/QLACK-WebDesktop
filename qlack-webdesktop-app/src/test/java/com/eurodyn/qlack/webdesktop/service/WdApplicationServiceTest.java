package com.eurodyn.qlack.webdesktop.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.eurodyn.qlack.webdesktop.InitTestValues;
import com.eurodyn.qlack.webdesktop.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.mapper.WdApplicationMapper;
import com.eurodyn.qlack.webdesktop.model.WdApplication;
import com.eurodyn.qlack.webdesktop.repository.WdApplicationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

/**
 * Test class for WdApplicationService
 *
 * @author European Dynamics
 */
@RunWith(MockitoJUnitRunner.class)
public class WdApplicationServiceTest {

  @InjectMocks
  private WdApplicationService wdApplicationService;

  private WdApplicationRepository wdApplicationRepository = mock(WdApplicationRepository.class);

  @Spy
  private WdApplicationMapper wdApplicationMapper;

  private InitTestValues initTestValues;

  private WdApplication wdApplication;
  private WdApplicationDTO wdApplicationDTO;
  private List<WdApplication> wdApplications;
  private List<WdApplicationDTO> wdApplicationsDTO;

  @Before
  public void init() {
    wdApplicationService = new WdApplicationService(wdApplicationMapper, wdApplicationRepository);
    initTestValues = new InitTestValues();
    wdApplication = initTestValues.createWdApplication();
    wdApplicationDTO = initTestValues.createWdApplicationDTO();
    wdApplications = initTestValues.createWdApplications();
    wdApplicationsDTO = initTestValues.createWdApplicationsDTO();
  }

  @Test
  public void testFindActiveApps() {
    when(wdApplicationRepository.findByActiveIsTrue()).thenReturn(wdApplications);
    when(wdApplicationMapper.mapToDTO(wdApplications)).thenReturn(wdApplicationsDTO);
    List<WdApplicationDTO> activeAppsListDTO = wdApplicationService.findAllActiveApplications();
    assertEquals(wdApplicationsDTO, activeAppsListDTO);
  }
}
