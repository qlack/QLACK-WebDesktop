package com.eurodyn.qlack.webdesktop.translations.management.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.fuse.lexicon.dto.GroupDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.KeyDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.LanguageDTO;
import com.eurodyn.qlack.fuse.lexicon.model.Data;
import com.eurodyn.qlack.fuse.lexicon.model.Key;
import com.eurodyn.qlack.fuse.lexicon.repository.DataRepository;
import com.eurodyn.qlack.fuse.lexicon.repository.KeyRepository;
import com.eurodyn.qlack.fuse.lexicon.service.GroupService;
import com.eurodyn.qlack.fuse.lexicon.service.KeyService;
import com.eurodyn.qlack.fuse.lexicon.service.LanguageService;
import com.eurodyn.qlack.webdesktop.translations.management.InitTestValues;
import com.eurodyn.qlack.webdesktop.translations.management.dto.TmKeyDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class TranslationsServiceTest {

  @InjectMocks
  private TranslationsService translationsService;

  @Mock
  private LanguageService languageService;

  @Mock
  private KeyService keyService;
  @Mock
  private Map<String, Map<String, String>> mockGroupedTranslations;
  @Mock
  private Map<String, String> mockedTranslations;
  @Mock
  private LanguageDTO mockedLanguageDTO;
  @Mock
  private DataRepository dataRepository;
  @Mock
  private GroupService groupService;
  @Mock
  private KeyRepository keyRepository;

  @Mock
  private DefaultOAuth2User defaultOAuth2User;
  @Mock
  private Authentication authentication;
  @Mock
  private SecurityContext securityContext;
  @Mock
  private UserDTO userDTO;
  @Mock
  private Object principal;

  @Mock
  private UserService userService;

  private List<KeyDTO> keysDto;
  private List<Key> keys;
  private List<LanguageDTO> languagesDTO;
  private Data data;
  private InitTestValues initTestValues;
  private List<String> pageableValues;
  private Set<GroupDTO> groupDTOS;
  private Set<UserAttributeDTO> userAttributeDTOS;
  private UserAttributeDTO userAttributeDTO;

  @Before
  public void onInit() {
    initTestValues = new InitTestValues();
    keys = initTestValues.createKeys();
    keysDto = initTestValues.createKeysDTO();
    data = initTestValues.createData();
    languagesDTO = initTestValues.createLanguagesDTO();
    pageableValues = new ArrayList<>(Arrays.asList("value", "asc"));
    groupDTOS = new HashSet<>(initTestValues.createGroupsDTO());
    userAttributeDTOS = new HashSet<>(initTestValues.createUserAttributesDTO());
    userAttributeDTO = initTestValues.createUserAttributeDTO();
  }

  @Test
  public void createLanguageWithSourceLanguageIdTest() {
    when(mockedLanguageDTO.getId()).thenReturn("id");
    when(keyRepository.findAll()).thenReturn(keys);
    when(dataRepository.findByKeyIdAndLanguageId(any(), any())).thenReturn(data);
    translationsService.createLanguage(mockedLanguageDTO);
    verify(keyService, times(1)).updateTranslationsForLanguage(any(), any());
  }

  @Test
  public void createLanguageWithoutSourceLanguageIdTest() {
    when(languageService.createLanguageIfNotExists(mockedLanguageDTO)).thenReturn("id");
    translationsService.createLanguage(mockedLanguageDTO);
    verify(keyService, times(0)).updateTranslationsForLanguage(any(), any());
  }

  @Test
  public void createLanguageWithoutTranslationsTest() {
    when(mockedLanguageDTO.getId()).thenReturn("id");
    when(keyRepository.findAll()).thenReturn(keys);
    when(dataRepository.findByKeyIdAndLanguageId(any(), any())).thenReturn(null);
    translationsService.createLanguage(mockedLanguageDTO);
    verify(keyService, times(1)).updateTranslationsForLanguage(any(), any());
  }


  @Test
  public void updateLanguagesTest() {
    translationsService.updateLanguages(languagesDTO);
    verify(languageService, times(1)).activateLanguage(any());
    verify(languageService, times(1)).deactivateLanguage(any());
  }

  @Test
  public void findLanguagesTest() {
    when(languageService.getLanguages(true)).thenReturn(languagesDTO);
    List<LanguageDTO> languages = translationsService.findLanguages(true);
    assertEquals(languagesDTO, languages);
  }

  @Test
  public void findTranslationsForLocaleTest() {
    when(keyService.getTranslationsForLocaleGroupByGroupTitle("en")).thenReturn(mockGroupedTranslations);
    Map<String, Map<String, String>> translations = translationsService.findTranslationsForLocale("en");
    assertEquals(mockGroupedTranslations, translations);
  }

  @Test
  public void updateTranslationsForKeyTest() {
    translationsService.updateTranslationsForKey("keyId", mockedTranslations);
    verify(keyService, times(1)).updateTranslationsForKeyByLocale(any(), any());

  }

  @Test
  public void findPagesForAllKeysTest() {
    when(keyService.findKeys(any(), anyBoolean())).thenReturn(keysDto);
    when(groupService.getGroups()).thenReturn(groupDTOS);
    Page<TmKeyDTO> tmKeyDTOS = translationsService.findPagesForAllKeys("0", "10", pageableValues);
    assertNotNull(tmKeyDTOS);
    assertEquals(1, tmKeyDTOS.getTotalPages());
    assertEquals(10, tmKeyDTOS.getSize());
    assertEquals(2, tmKeyDTOS.getContent().size());

  }

  @Test
  public void getTranslationPassTest() {
    when(dataRepository.findByKeyIdAndLanguageId(any(), any())).thenReturn(data);
    String result = translationsService.getTranslation(any(), any());
    assertNotNull(result);
  }

  @Test
  public void getTranslationFailTest() {
    when(dataRepository.findByKeyIdAndLanguageId(any(), any())).thenReturn(null);
    String result = translationsService.getTranslation(any(), any());
    assertNull(result);
  }

  @Test
  public void findUserAttributeByNameSuccessTest() {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(defaultOAuth2User);
    when(defaultOAuth2User.getName()).thenReturn("username");
    when(userService.getUserByName("username")).thenReturn(userDTO);
    when(userDTO.getUserAttributes()).thenReturn(userAttributeDTOS);
    UserAttributeDTO result = translationsService.findUserAttributeByName("company");
    assertNotNull(result);
    verify(userService, times(1)).getUserByName(anyString());
    verify(userDTO, times(1)).getUserAttributes();

  }

  @Test
  public void findUserAttributeByNameWithWrongNameTest() {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(defaultOAuth2User);
    when(defaultOAuth2User.getName()).thenReturn("username");
    when(userService.getUserByName("username")).thenReturn(userDTO);
    when(userDTO.getUserAttributes()).thenReturn(userAttributeDTOS);
    UserAttributeDTO result = translationsService.findUserAttributeByName("wrong name");
    assertNull(result);
    verify(userService, times(1)).getUserByName(anyString());
    verify(userDTO, times(1)).getUserAttributes();

  }

  @Test
  public void findUserAttributeByNameFailTest() {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);

    UserAttributeDTO result = translationsService.findUserAttributeByName("attributeName");
    assertNull(result);

  }


}
