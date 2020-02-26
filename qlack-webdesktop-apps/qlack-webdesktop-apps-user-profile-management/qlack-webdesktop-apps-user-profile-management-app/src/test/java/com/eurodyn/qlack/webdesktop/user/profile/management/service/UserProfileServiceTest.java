package com.eurodyn.qlack.webdesktop.user.profile.management.service;

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
import com.eurodyn.qlack.fuse.lexicon.dto.LanguageDTO;
import com.eurodyn.qlack.fuse.lexicon.service.KeyService;
import com.eurodyn.qlack.fuse.lexicon.service.LanguageService;
import com.eurodyn.qlack.webdesktop.user.profile.management.InitTestValues;
import com.eurodyn.qlack.webdesktop.user.profile.management.dto.UserDetailsDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RunWith(MockitoJUnitRunner.class)
public class UserProfileServiceTest {

  private static final String TRANSLATIONS_GROUP = "user-profile-management-ui";
  @InjectMocks
  private UserProfileService userProfileService;
  @Mock
  private UserService userService;
  @Mock
  private LanguageService languageService;
  @Mock
  private UserDetailsDTO userDetailsDTO;
  @Mock
  private KeyService keyService;
  @Mock
  private Map<String, String> mockedTranslations;
  @Mock
  private List<LanguageDTO> mockedLanguages;
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

  private InitTestValues initTestValues;
  private Set<UserAttributeDTO> userAttributeDTOS;
  private UserAttributeDTO userAttributeDTO;

  @Before
  public void onInit() {
    initTestValues = new InitTestValues();
    userAttributeDTOS = new HashSet<>(initTestValues.createUserAttributesDTO());
    userAttributeDTO = initTestValues.createUserAttributeDTO();
  }

  @Test
  public void findTranslationsTest() {
    when(keyService.getTranslationsForGroupNameAndLocale(TRANSLATIONS_GROUP, "en")).thenReturn(mockedTranslations);
    Map<String, String> translations = userProfileService.findTranslationsForLocale("en");
    assertEquals(mockedTranslations, translations);
  }

  @Test
  public void findLanguagesTest() {
    when(languageService.getLanguages(true)).thenReturn(mockedLanguages);
    List<LanguageDTO> languages = userProfileService.findLanguages(true);
    assertEquals(mockedLanguages, languages);
  }

  @Test
  public void saveDetailsSuccessTest() throws IOException {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(defaultOAuth2User);
    when(defaultOAuth2User.getName()).thenReturn("username");
    when(userService.getUserByName("username")).thenReturn(userDTO);
    MockMultipartFile profileImage = new MockMultipartFile("data", "profileImage.jpg",
        "text/plain", "profileImage content".getBytes());
    MockMultipartFile backgroundImage = new MockMultipartFile("data", "backgroundImage.jpg",
        "text/plain", "backgroundImage content".getBytes());
    userProfileService.saveDetails(userDetailsDTO, profileImage, backgroundImage,false);
    verify(userService, times(1)).getUserByName(anyString());
    verify(userService, times(3)).updateAttribute(any(), anyBoolean());
  }

  @Test
  public void saveOnlyDefaultLanguageUserAttributeTest() throws IOException {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(defaultOAuth2User);
    when(defaultOAuth2User.getName()).thenReturn("username");
    when(userService.getUserByName("username")).thenReturn(userDTO);
    MockMultipartFile profileImage = null;
    MockMultipartFile backgroundImage = null;
    userProfileService.saveDetails(userDetailsDTO, profileImage, backgroundImage,false);
    verify(userService, times(1)).getUserByName(anyString());
    verify(userService, times(1)).updateAttribute(any(), anyBoolean());
  }

  @Test
  public void saveDetailsFailTest() throws IOException {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);
    MockMultipartFile profileImage = new MockMultipartFile("data", "profileImage.jpg",
        "text/plain", "profileImage content".getBytes());
    MockMultipartFile backgroundImage = new MockMultipartFile("data", "backgroundImage.jpg",
        "text/plain", "backgroundImage content".getBytes());
    userProfileService.saveDetails(userDetailsDTO, profileImage, backgroundImage,false);
    verify(userService, times(0)).getUserByName(anyString());
    verify(userService, times(0)).updateAttribute(any(), anyBoolean());
  }
  @Test
  public void saveDetailsDeleteBackgroundImage() throws IOException {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(defaultOAuth2User);
    when(defaultOAuth2User.getName()).thenReturn("username");
    when(userService.getUserByName("username")).thenReturn(userDTO);
    userProfileService.saveDetails(userDetailsDTO, null, null,true);
    verify(userService, times(1)).getUserByName(anyString());
    verify(userService, times(2)).updateAttribute(any(), anyBoolean());
  }

  @Test
  public void findUserDetailsSuccessTest() {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(defaultOAuth2User);
    when(defaultOAuth2User.getName()).thenReturn("username");
    when(userService.getUserByName("username")).thenReturn(userDTO);
    when(userDTO.getUserAttributes()).thenReturn(userAttributeDTOS);
    Map<String, UserAttributeDTO> result = userProfileService.findUserDetails();
    assertNotNull(result);
    assertEquals(2, result.size());
    verify(userService, times(1)).getUserByName(anyString());
    verify(userDTO, times(1)).getUserAttributes();

  }

  @Test
  public void findUserDetailsFailTest() {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);
    Map<String, UserAttributeDTO> result = userProfileService.findUserDetails();
    assertNull(result);
  }

  @Test
  public void saveAttributeOnNewObjectTest() {
    userProfileService.saveAttribute(anyString(), anyString(), new byte[0], "data");
    verify(userService, times(1)).getAttribute(anyString(), anyString());
    verify(userService, times(1)).updateAttribute(any(), anyBoolean());
  }

  @Test
  public void saveAttributeOnExistingObjectTest() {
    when(userService.getAttribute(anyString(), anyString())).thenReturn(userAttributeDTO);
    userProfileService.saveAttribute(anyString(), anyString(), new byte[0], "data");
    verify(userService, times(1)).getAttribute(anyString(), anyString());
    verify(userService, times(1)).updateAttribute(any(), anyBoolean());
  }

}
