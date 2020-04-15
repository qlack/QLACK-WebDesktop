package com.eurodyn.qlack.webdesktop.app.configuration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eurodyn.qlack.fuse.aaa.model.User;
import com.eurodyn.qlack.fuse.aaa.repository.UserRepository;
import com.eurodyn.qlack.fuse.aaa.service.LdapUserUtil;
import com.eurodyn.qlack.fuse.crypto.service.CryptoDigestService;
import com.eurodyn.qlack.fuse.lexicon.dto.GroupDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.KeyDTO;
import com.eurodyn.qlack.fuse.lexicon.service.GroupService;
import com.eurodyn.qlack.fuse.lexicon.service.KeyService;
import com.eurodyn.qlack.webdesktop.app.InitTestValues;
import com.eurodyn.qlack.webdesktop.app.service.ZuulRouteService;
import com.eurodyn.qlack.webdesktop.common.dto.LexiconDTO;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;
import com.eurodyn.qlack.webdesktop.common.repository.WdApplicationRepository;
import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
import com.eurodyn.qlack.webdesktop.common.util.StringUtils;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.ApplicationArguments;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(PowerMockRunner.class)
public class WdApplicationConfigTest {

  @InjectMocks
  private WdApplicationConfig wdApplicationConfig;
  @Mock
  private WdApplicationRepository wdApplicationRepository;
  @Mock
  private CryptoDigestService cryptoDigestService;
  @Mock
  private GroupService groupService;
  @Mock
  private ApplicationArguments args;
  @Mock
  private KeyService keyService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private LdapUserUtil ldapUserUtil;
  @Mock
  private WdApplicationService wdApplicationService;
  @Mock
  private StringUtils stringUtils;
  @Mock
  private ZuulRouteService zuulRouteService;

  private List<LexiconDTO> translations;
  private KeyDTO keyDTO;
  private GroupDTO groupDTO;
  private User user;
  private List<String> urls;
  private List<WdApplication> wdApplications;

  private InitTestValues initTestValues;

  @Before
  public void setup() {
    initTestValues = new InitTestValues();
    translations = initTestValues.createLexicon();
    groupDTO = initTestValues.createGroupDTO();
    keyDTO = initTestValues.createKeyDTO();
    user = initTestValues.createUser();
    urls = initTestValues.createUrls();
    wdApplications = initTestValues.createWdApplications();
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
  public void runWitExistingAdminUserAndWdAdminPropertyNullTest() {
    when(userRepository.findByUsername(anyString())).thenReturn(user);
    wdApplicationConfig.run(args);
    verify(userRepository, times(0)).save(user);
  }

  @Test
  public void runWitExistingAdminUserAndWdAdminPropertyExistsTest() {
    ReflectionTestUtils.setField(wdApplicationConfig, "wdAdmin", "adminUserName");
    when(userRepository.findByUsername(anyString())).thenReturn(user);
    wdApplicationConfig.run(args);
    verify(userRepository, times(0)).save(user);
  }

  @Test
  public void runWithoutExistingAdminUserAndWdAdminPropertyExistsTest() {
    ReflectionTestUtils.setField(wdApplicationConfig, "wdAdmin", "adminUserName");
    when(userRepository.findByUsername(anyString())).thenReturn(null);
    wdApplicationConfig.run(args);
    verify(userRepository, times(1)).save(any());
  }

  @Test
  public void runWhenCouldNotSyncAdminWithAAATest() {
    ReflectionTestUtils.setField(wdApplicationConfig, "wdAdmin", "adminUserName");
    when(userRepository.findByUsername(anyString())).thenReturn(null);
    when(ldapUserUtil.syncUserWithAAA(anyString())).thenReturn(null);
    wdApplicationConfig.run(args);
    verify(userRepository, times(0)).save(user);
  }

  @Test
  public void runWithValidUrlArgsTest() {
    ReflectionTestUtils.setField(wdApplicationConfig, "wdAdmin", "adminUserName");
    when(userRepository.findByUsername(anyString())).thenReturn(null);
    when(args.containsOption(anyString())).thenReturn(true);
    when(args.getOptionValues(anyString())).thenReturn(urls);
    when(wdApplicationRepository.findByActiveIsTrue()).thenReturn(wdApplications);
    wdApplicationConfig.run(args);
    verify(userRepository, times(1)).save(any());
    verify(wdApplicationRepository, times(1)).findByActiveIsTrue();
  }

  @Test
  public void processLexiconValuesTest() {
    when(groupService.getGroupByTitle(anyString())).thenReturn(groupDTO);
    when(keyService.getKeyByName(anyString(), anyString(), anyBoolean())).thenReturn(null);
    when(keyService.createKey(any(), anyBoolean())).thenReturn("keyId");
    wdApplicationConfig.processLexiconValues(translations, wdApplications.get(0));
    verify(wdApplicationService, times(1)).processLexiconValues(any(), any());
    verify(keyService, times(1)).updateTranslationByLocale(anyString(), anyString(), anyString());
  }
}
