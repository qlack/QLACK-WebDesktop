package webdesktop.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.mapper.WdApplicationMapper;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;
import com.eurodyn.qlack.webdesktop.common.repository.WdApplicationRepository;
import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import webdesktop.InitTestValues;

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

  @Test
  public void testfindAllActiveApplicationsFilterGroupName() {
    wdApplications.get(0).setGroupName(" ");
    when(wdApplicationRepository.findByActiveIsTrue()).thenReturn(wdApplications);
    when(wdApplicationMapper.mapToDTO(wdApplications)).thenReturn(wdApplicationsDTO);
    List<WdApplicationDTO> activeAppsListDTO = wdApplicationService
        .findAllActiveApplicationsFilterGroupName();
    wdApplicationsDTO.forEach(wdApplicationDTO1 ->
        assertNull(wdApplicationDTO1.getGroupName()));
    activeAppsListDTO.forEach(wdApplicationDTO ->
        assertTrue(wdApplicationDTO.getGroupName() == null));
  }
}
