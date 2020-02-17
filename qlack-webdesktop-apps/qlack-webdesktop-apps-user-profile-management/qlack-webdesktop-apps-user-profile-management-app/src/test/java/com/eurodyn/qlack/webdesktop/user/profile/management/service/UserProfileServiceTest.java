package com.eurodyn.qlack.webdesktop.user.profile.management.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.fuse.lexicon.dto.LanguageDTO;
import com.eurodyn.qlack.fuse.lexicon.service.KeyService;
import com.eurodyn.qlack.fuse.lexicon.service.LanguageService;
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
import java.util.List;
import java.util.Map;


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

  @Before
  public void onInit() {
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
  public void saveDetailsTest() throws IOException {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(defaultOAuth2User);
    when(defaultOAuth2User.getName()).thenReturn("username");
    when(userService.getUserByName("username")).thenReturn(userDTO);
    MockMultipartFile profileImage = new MockMultipartFile("data", "profileImage.jpg",
        "text/plain", "profileImage content".getBytes());
    MockMultipartFile backgroundImage = new MockMultipartFile("data", "backgroundImage.jpg",
        "text/plain", "backgroundImage content".getBytes());
    userProfileService.saveDetails(userDetailsDTO, profileImage, backgroundImage);
    verify(userService, times(1)).getUserByName(anyString());
    verify(userService, times(3)).updateAttribute(any(),anyBoolean());
  }
  @Test
  public void findUserDetailsTest(){
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(defaultOAuth2User);
    when(defaultOAuth2User.getName()).thenReturn("username");
    when(userService.getUserByName("username")).thenReturn(userDTO);
    userProfileService.findUserDetails();
    verify(userService, times(1)).getUserByName(anyString());
    verify(userDTO, times(1)).getUserAttributes();
  }
  @Test
  public void saveAttributeTest(){
    userProfileService.saveAttribute(anyString(),anyString(),new byte[0],"data");
    verify(userService, times(1)).getAttribute(anyString(),anyString());
    verify(userService, times(1)).updateAttribute(any(),anyBoolean());
  }
}
