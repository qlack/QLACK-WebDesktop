package webdesktop.configuration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eurodyn.qlack.fuse.aaa.repository.UserRepository;
import com.eurodyn.qlack.fuse.aaa.service.LdapUserUtil;
import com.eurodyn.qlack.fuse.crypto.service.CryptoDigestService;
import com.eurodyn.qlack.fuse.lexicon.dto.GroupDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.KeyDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.LanguageDTO;
import com.eurodyn.qlack.fuse.lexicon.service.GroupService;
import com.eurodyn.qlack.fuse.lexicon.service.KeyService;
import com.eurodyn.qlack.fuse.lexicon.service.LanguageService;
import com.eurodyn.qlack.webdesktop.common.dto.LexiconDTO;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;
import com.eurodyn.qlack.webdesktop.common.repository.WdApplicationRepository;
import com.eurodyn.qlack.webdesktop.configuration.WdApplicationConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import webdesktop.InitTestValues;

import java.util.List;

@RunWith(PowerMockRunner.class)
public class WdApplicationConfigTest {


  @InjectMocks
  private WdApplicationConfig wdApplicationConfig;
  @Mock
  private WdApplicationRepository wdApplicationRepository;
  @Mock
  private CryptoDigestService cryptoDigestService;
  @Mock
  private DiscoveryClientRouteLocator discoveryClientRouteLocator;
  @Mock
  private LanguageService languageService;
  @Mock
  private GroupService groupService;
  @Mock
  private KeyService keyService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private LdapUserUtil ldapUserUtil;
  @Mock
  private LanguageDTO languageDTO;
  private KeyDTO keyDTO;
  private List<LexiconDTO> translations;
  private WdApplication wdApplication;
  private GroupDTO groupDTO;


  private InitTestValues initTestValues;

  @Before
  public void setup() {
    initTestValues = new InitTestValues();
    wdApplication = initTestValues.createWdApplication();
    groupDTO = initTestValues.createGroupDTO();
    translations = initTestValues.createLexicon();
    keyDTO = initTestValues.createKeyDTO();
  }

  @Test
  public void processLexiconValuesWehnKeyDTODoesNotExistTest() {
    when(groupService.getGroupByTitle(anyString())).thenReturn(groupDTO);
    when(languageService.getLanguageByLocale(anyString())).thenReturn(languageDTO);
    when(keyService.getKeyByName(anyString(), anyString(), anyBoolean())).thenReturn(null);
    when(keyService.createKey(any(), anyBoolean())).thenReturn("keyId");
    when(languageService.getLanguageByLocale(anyString())).thenReturn(languageDTO);
    wdApplicationConfig.processLexiconValues(translations, wdApplication);
    verify(keyService, times(1)).updateTranslationByLocale(anyString(), anyString(), anyString());
  }

  @Test
  public void processLexiconValuesWhenKeyDTOExistsTest() {
    when(groupService.getGroupByTitle(anyString())).thenReturn(groupDTO);
    when(languageService.getLanguageByLocale(anyString())).thenReturn(languageDTO);
    when(keyService.getKeyByName(anyString(), anyString(), anyBoolean())).thenReturn(keyDTO);
    when(languageService.getLanguageByLocale(anyString())).thenReturn(languageDTO);
    wdApplicationConfig.processLexiconValues(translations, wdApplication);
    verify(keyService, times(1)).updateTranslationByLocale(anyString(), anyString(), anyString());
  }

  @Test
  public void processLexiconValuesWithWrongLanguageLocaleTest() {
    when(languageService.getLanguageByLocale(anyString())).thenReturn(null);
    wdApplicationConfig.processLexiconValues(translations, wdApplication);
    verify(keyService, times(0)).updateTranslationByLocale(anyString(), anyString(), anyString());
  }

  @Test
  public void processLexiconValuesAndCreateNewGroupDTOTest() {
    when(groupService.getGroupByTitle(anyString())).thenReturn(null);
    when(groupService.createGroup(any())).thenReturn("groupId");
    when(languageService.getLanguageByLocale(anyString())).thenReturn(languageDTO);
    when(keyService.getKeyByName(anyString(), anyString(), anyBoolean())).thenReturn(keyDTO);
    when(languageService.getLanguageByLocale(anyString())).thenReturn(languageDTO);
    wdApplicationConfig.processLexiconValues(translations, wdApplication);
    verify(keyService, times(1)).updateTranslationByLocale(anyString(), anyString(), anyString());
  }

  @Test
  public void createKeyForAppGroupNameWhenKeyDoesNotExistTest() {
    when(groupService.getGroupByTitle(anyString())).thenReturn(groupDTO);
    when(keyService.getKeyByName(anyString(), anyString(), anyBoolean())).thenReturn(null);
    when(keyService.createKey(any(), anyBoolean())).thenReturn("keyId");
    wdApplicationConfig.createKeyForAppGroupName("appGroupName");
    verify(keyService, times(1)).updateTranslationByLocale(anyString(), anyString(), anyString());
  }

  @Test
  public void createKeyForAppGroupNameWhenKeyAlreadyExistsTest() {
    when(groupService.getGroupByTitle(anyString())).thenReturn(groupDTO);
    when(keyService.getKeyByName(anyString(), anyString(), anyBoolean())).thenReturn(keyDTO);
    when(keyService.createKey(any(), anyBoolean())).thenReturn("keyId");
    wdApplicationConfig.createKeyForAppGroupName("appGroupName");
    verify(keyService, times(0)).updateTranslationByLocale(anyString(), anyString(), anyString());
  }

  @Test
  public void createKeyForAppGroupNameWithNullParamTest() {
    wdApplicationConfig.createKeyForAppGroupName(null);
    verify(keyService, times(0)).updateTranslationByLocale(anyString(), anyString(), anyString());
  }


  @Test
  public void isNotNullOrEmptySuccessTest() {
    boolean result = wdApplicationConfig.isNotNullOrEmpty("a string");
    assertTrue(result);
  }

  @Test
  public void isNotNullOrEmptyWithNullValueTest() {
    boolean result = wdApplicationConfig.isNotNullOrEmpty(null);
    assertFalse(result);
  }

  @Test
  public void isNotNullOrEmptyWithEmptyValueTest() {
    boolean result = wdApplicationConfig.isNotNullOrEmpty("");
    assertFalse(result);
  }

}
