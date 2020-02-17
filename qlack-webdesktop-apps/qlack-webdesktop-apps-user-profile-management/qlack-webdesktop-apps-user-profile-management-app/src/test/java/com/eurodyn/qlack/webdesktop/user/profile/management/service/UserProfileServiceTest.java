package com.eurodyn.qlack.webdesktop.user.profile.management.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
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
  private  LanguageService languageService;
  @Mock
  private UserDetailsDTO userDetailsDTO;
  @Mock
  private KeyService keyService;
  @Mock
  private Map<String,String> mockedTranslations;
  @Mock
  private List<LanguageDTO> mockedLanguages;

  @Before
  public void onInit() {
    userProfileService = new UserProfileService(keyService,languageService,userService);
    userDetailsDTO = new UserDetailsDTO();
    userDetailsDTO.setDefaultLanguage("en");
  }

  @Test
  public void findTranslationsTest() {
    when(keyService.getTranslationsForGroupNameAndLocale(TRANSLATIONS_GROUP,"en")).thenReturn(mockedTranslations);
    Map<String, String> translations = userProfileService.findTranslationsForLocale("en");
    assertEquals(mockedTranslations,translations);
  }
  @Test
  public void findLanguagesTest() {
    when(languageService.getLanguages(true)).thenReturn(mockedLanguages);
    List<LanguageDTO> languages = userProfileService.findLanguages(true);
    assertEquals(mockedLanguages,languages);
  }
//  @Test
//  public void saveDetailsTest() throws IOException {
//    MockMultipartFile profileImage = new MockMultipartFile("data", "profileImage.jpg",
//        "text/plain", "profileImage content".getBytes());
//    MockMultipartFile backgroundImage = new MockMultipartFile("data", "backgroundImage.jpg",
//        "text/plain", "backgroundImage content".getBytes());
//    userProfileService.saveDetails(userDetailsDTO,profileImage,backgroundImage);
//    verify(userProfileService, times(1)).saveDetails(userDetailsDTO,profileImage,backgroundImage);
//  }
}
