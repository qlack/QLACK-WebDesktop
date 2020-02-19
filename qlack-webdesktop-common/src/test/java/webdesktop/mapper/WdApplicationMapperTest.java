package webdesktop.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.mapper.WdApplicationMapperImpl;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import webdesktop.InitTestValues;

import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class WdApplicationMapperTest {

  @InjectMocks
  private WdApplicationMapperImpl wdApplicationMapperImpl;

  private InitTestValues initTestValues;
  private WdApplication wdApplication;
  private WdApplicationDTO wdApplicationDTO;
  private List<WdApplication> wdApplications;
  private List<WdApplicationDTO> wdApplicationDTOS;


  @Before
  public void init() {
    initTestValues = new InitTestValues();
    wdApplication = initTestValues.createWdApplication();
    wdApplicationDTO = initTestValues.createWdApplicationDTO();
    wdApplications = initTestValues.createWdApplications();
    wdApplicationDTOS = initTestValues.createWdApplicationsDTO();
  }

  @Test
  public void mapToDTOTest() {
    WdApplicationDTO wdApplicationDTO = wdApplicationMapperImpl.mapToDTO(wdApplication);
    assertEquals(wdApplication.getId(), wdApplicationDTO.getId());
    assertEquals(wdApplication.getApplicationName(), wdApplicationDTO.getApplicationName());
  }

  @Test
  public void mapToEntityTest() {
    WdApplication wdApplication = wdApplicationMapperImpl.mapToEntity(wdApplicationDTO);
    assertEquals(wdApplicationDTO.getId(), wdApplication.getId());
    assertEquals(wdApplicationDTO.getApplicationName(), wdApplication.getApplicationName());
  }

  @Test
  public void mapToDTOListTest() {
    List<WdApplicationDTO> wdApplicationDTOS = wdApplicationMapperImpl.mapToDTO(wdApplications);
    assertEquals(wdApplicationDTOS.get(0).getId(), wdApplications.get(0).getId());
    assertEquals(wdApplicationDTOS.get(0).getApplicationName(), wdApplications.get(0).getApplicationName());

  }

  @Test
  public void mapToEntityListTest() {
    List<WdApplication> wdApplications = wdApplicationMapperImpl.mapToEntity(wdApplicationDTOS);
    assertEquals(wdApplications.get(0).getId(), wdApplicationDTOS.get(0).getId());
    assertEquals(wdApplications.get(0).getApplicationName(), wdApplicationDTOS.get(0).getApplicationName());
  }

  @Test
  public void mapTest() {
    Long date = wdApplicationMapperImpl.map(new Date());
    assertNotNull(date);
  }

}
