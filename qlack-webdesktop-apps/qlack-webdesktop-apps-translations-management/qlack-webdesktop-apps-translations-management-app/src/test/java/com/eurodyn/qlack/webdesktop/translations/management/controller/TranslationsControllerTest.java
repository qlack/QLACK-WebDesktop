package com.eurodyn.qlack.webdesktop.translations.management.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.eurodyn.qlack.fuse.lexicon.dto.LanguageDTO;
import com.eurodyn.qlack.webdesktop.translations.management.InitTestValues;
import com.eurodyn.qlack.webdesktop.translations.management.dto.TmKeyDTO;
import com.eurodyn.qlack.webdesktop.translations.management.service.TranslationsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class TranslationsControllerTest {

  @MockBean
  private TranslationsService translationsService;
  private MockMvc mockMvc;
  private TmKeyDTO tmKeyDto;
  private List<LanguageDTO> languageDTOS;
  private LanguageDTO languageDTO;
  private ObjectMapper mapper;
  private InitTestValues initTestValues;

  @Before
  public void setup() {
    initTestValues = new InitTestValues();
    tmKeyDto = initTestValues.createTmKeyDTO();
    languageDTOS = initTestValues.createLanguagesDTO();
    languageDTO = initTestValues.createEnglishLanguageDTO();
    mapper = new ObjectMapper();
    this.mockMvc = MockMvcBuilders
        .standaloneSetup(new TranslationsController(translationsService)).build();
  }

  @Test
  public void getPagesForAllKeysTest() throws Exception {
    mockMvc.perform(get("/api/key")
        .param("page", "0")
        .param("size", "10")
        .param("sort", new String[]{"value", "asc"})
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void updateTranslationsForKeyTest() throws Exception {
    mockMvc.perform(post("/api/key/update")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(tmKeyDto)))
        .andExpect(status().isOk());
  }

  @Test
  public void updateLanguagesTest() throws Exception {
    mockMvc.perform(post("/api/languages/update")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(languageDTOS)))
        .andExpect(status().isOk());
  }

  @Test
  public void createLanguageTest() throws Exception {
    mockMvc.perform(post("/api/languages/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(languageDTO)))
        .andExpect(status().isOk());
  }

  @Test
  public void getLanguagesTest() throws Exception {
    mockMvc.perform(get("/api/languages/{includeInactive}", true)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void getTranslationsTest() throws Exception {
    mockMvc.perform(get("/api/translations?lang=en")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void getUserAttributeByNameTest() throws Exception {
    mockMvc.perform(get("/api/user/attributes/{attributeName}", "attributeName")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

}
