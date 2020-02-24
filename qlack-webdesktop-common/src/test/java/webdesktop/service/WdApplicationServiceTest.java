package webdesktop.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eurodyn.qlack.fuse.lexicon.dto.GroupDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.KeyDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.LanguageDTO;
import com.eurodyn.qlack.fuse.lexicon.service.GroupService;
import com.eurodyn.qlack.fuse.lexicon.service.KeyService;
import com.eurodyn.qlack.fuse.lexicon.service.LanguageService;
import com.eurodyn.qlack.webdesktop.common.dto.LexiconDTO;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.mapper.WdApplicationMapper;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;
import com.eurodyn.qlack.webdesktop.common.repository.WdApplicationRepository;
import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import webdesktop.InitTestValues;

import java.util.List;
import java.util.Map;

/**
 * Test class for WdApplicationService
 *
 * @author European Dynamics
 */
@RunWith(MockitoJUnitRunner.class)
public class WdApplicationServiceTest {

  @InjectMocks
  private WdApplicationService wdApplicationService;

  @Spy
  private WdApplicationMapper wdApplicationMapper;

  @Mock
  private WdApplicationRepository wdApplicationRepository;
  @Mock
  private LanguageService languageService;
  @Mock
  private GroupService groupService;
  @Mock
  private KeyService keyService;
  @Mock
  private LanguageDTO languageDTO;
  @Mock
  private Map<String, Map<String, String>> mockGroupedTranslations;

  private KeyDTO keyDTO;
  private List<LexiconDTO> translations;
  private GroupDTO groupDTO;
  private InitTestValues initTestValues;
  private WdApplication wdApplication;
  private WdApplicationDTO wdApplicationDTO;
  private List<WdApplication> wdApplications;
  private List<WdApplicationDTO> wdApplicationsDTO;

  @Before
  public void init() {
    initTestValues = new InitTestValues();
    wdApplication = initTestValues.createWdApplication();
    wdApplicationDTO = initTestValues.createWdApplicationDTO();
    wdApplications = initTestValues.createWdApplications();
    wdApplicationsDTO = initTestValues.createWdApplicationsDTO();
    translations = initTestValues.createLexicon();
    keyDTO = initTestValues.createKeyDTO();
    groupDTO = initTestValues.createGroupDTO();
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
    wdApplications.get(0).setGroupName("groupName");
    when(wdApplicationRepository.findByActiveIsTrue()).thenReturn(wdApplications);
    when(wdApplicationMapper.mapToDTO(wdApplications)).thenReturn(wdApplicationsDTO);
    List<WdApplicationDTO> activeAppsListDTO = wdApplicationService
        .findAllActiveApplicationsFilterGroupName();
    wdApplicationsDTO.forEach(wdApplicationDTO1 ->
        assertNull(wdApplicationDTO1.getGroupName()));
    activeAppsListDTO.forEach(wdApplicationDTO ->
        assertTrue(wdApplicationDTO.getGroupName() == null));
  }
  @Test
  public void testfindAllActiveApplicationsFilterGroupNameWithNullGroupName() {
    wdApplications.get(0).setGroupName(null);
    when(wdApplicationRepository.findByActiveIsTrue()).thenReturn(wdApplications);
    when(wdApplicationMapper.mapToDTO(wdApplications)).thenReturn(wdApplicationsDTO);
    List<WdApplicationDTO> activeAppsListDTO = wdApplicationService
        .findAllActiveApplicationsFilterGroupName();
    wdApplicationsDTO.forEach(wdApplicationDTO1 ->
        assertNull(wdApplicationDTO1.getGroupName()));
    activeAppsListDTO.forEach(wdApplicationDTO ->
        assertTrue(wdApplicationDTO.getGroupName() == null));
  }
  @Test
  public void testfindAllActiveApplicationsFilterGroupNameWithNullStringGroupName() {
    wdApplications.get(0).setGroupName("null");
    when(wdApplicationRepository.findByActiveIsTrue()).thenReturn(wdApplications);
    when(wdApplicationMapper.mapToDTO(wdApplications)).thenReturn(wdApplicationsDTO);
    List<WdApplicationDTO> activeAppsListDTO = wdApplicationService
        .findAllActiveApplicationsFilterGroupName();
    wdApplicationsDTO.forEach(wdApplicationDTO1 ->
        assertNull(wdApplicationDTO1.getGroupName()));
    activeAppsListDTO.forEach(wdApplicationDTO ->
        assertTrue(wdApplicationDTO.getGroupName() == null));
  }

  @Test
  public void processLexiconValuesWehnKeyDTODoesNotExistTest() {
    when(groupService.getGroupByTitle(anyString())).thenReturn(groupDTO);
    when(languageService.getLanguageByLocale(anyString())).thenReturn(languageDTO);
    when(keyService.getKeyByName(anyString(), anyString(), anyBoolean())).thenReturn(null);
    when(keyService.createKey(any(), anyBoolean())).thenReturn("keyId");
    when(languageService.getLanguageByLocale(anyString())).thenReturn(languageDTO);
    wdApplicationService.processLexiconValues(translations, wdApplication);
    verify(keyService, times(1)).updateTranslationByLocale(anyString(), anyString(), anyString());
  }

  @Test
  public void processLexiconValuesWhenKeyDTOExistsTest() {
    when(groupService.getGroupByTitle(anyString())).thenReturn(groupDTO);
    when(languageService.getLanguageByLocale(anyString())).thenReturn(languageDTO);
    when(keyService.getKeyByName(anyString(), anyString(), anyBoolean())).thenReturn(keyDTO);
    when(languageService.getLanguageByLocale(anyString())).thenReturn(languageDTO);
    wdApplicationService.processLexiconValues(translations, wdApplication);
    verify(keyService, times(1)).updateTranslationByLocale(anyString(), anyString(), anyString());
  }

  @Test
  public void processLexiconValuesWithWrongLanguageLocaleTest() {
    when(languageService.getLanguageByLocale(anyString())).thenReturn(null);
    wdApplicationService.processLexiconValues(translations, wdApplication);
    verify(keyService, times(0)).updateTranslationByLocale(anyString(), anyString(), anyString());
  }

  @Test
  public void processLexiconValuesAndCreateNewGroupDTOTest() {
    when(groupService.getGroupByTitle(anyString())).thenReturn(null);
    when(groupService.createGroup(any())).thenReturn("groupId");
    when(languageService.getLanguageByLocale(anyString())).thenReturn(languageDTO);
    when(keyService.getKeyByName(anyString(), anyString(), anyBoolean())).thenReturn(keyDTO);
    when(languageService.getLanguageByLocale(anyString())).thenReturn(languageDTO);
    wdApplicationService.processLexiconValues(translations, wdApplication);
    verify(keyService, times(1)).updateTranslationByLocale(anyString(), anyString(), anyString());
  }


  @Test
  public void findApplicationByNameTest() {
    when(wdApplicationRepository.findByApplicationName(anyString())).thenReturn(wdApplication);
     WdApplication wdApplication = wdApplicationService.findApplicationByName(anyString());
    assertNotNull(wdApplication);
  }

  @Test
  public void saveApplicationTest() {
    wdApplicationService.saveApplication(any());
    verify(wdApplicationRepository, times(1)).save(any());
  }

  @Test
  public void findApplicationByIdTest() {
    when(wdApplicationRepository.fetchById(anyString())).thenReturn(wdApplication);
    when(wdApplicationMapper.mapToDTO(wdApplication)).thenReturn(wdApplicationDTO);
     WdApplicationDTO wdApplicationDTO =  wdApplicationService.findApplicationById(anyString());
   assertNotNull(wdApplicationDTO);
  }
  @Test
  public void findAllApplicationsTest() {
    List<WdApplicationDTO> wdApplicationDTOS = wdApplicationService.findAllApplications();
    assertNotNull(wdApplicationDTOS);
  }

  @Test
  public void findTranslationsForLocaleTest() {
    when(keyService.getTranslationsForLocaleGroupByGroupTitle("en")).thenReturn(mockGroupedTranslations);
    Map<String, Map<String, String>> translations = wdApplicationService.findTranslationsForLocale("en");
    assertEquals(mockGroupedTranslations, translations);
  }

}
