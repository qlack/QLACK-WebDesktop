package com.eurodyn.qlack.webdesktop.common.util;

import com.eurodyn.qlack.fuse.lexicon.dto.LanguageDTO;
import com.eurodyn.qlack.fuse.lexicon.service.LanguageService;
import com.eurodyn.qlack.webdesktop.common.dto.LanguageDataDTO;
import com.eurodyn.qlack.webdesktop.common.dto.LexiconDTO;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.mapper.WdApplicationMapper;
import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the yaml uploading and saves the application to database.
 */
@Component
public class ProcessLexiconUtil {

  private LanguageService languageService;
  private WdApplicationService wdApplicationService;
  private WdApplicationMapper wdApplicationMapper;
  private List<LexiconDTO> translationKeys = new ArrayList<>();

  @Autowired
  public ProcessLexiconUtil(LanguageService languageService,
      WdApplicationService wdApplicationService, WdApplicationMapper wdApplicationMapper) {
    this.languageService = languageService;
    this.wdApplicationService = wdApplicationService;
    this.wdApplicationMapper = wdApplicationMapper;
  }

  /**
   * This is a helper method sequence so as to create lexicon values. This is a pre-process in order to insert default
   * translations for all available languages.
   *
   * @param wdApplicationDTO the application.
   * @return a list containing translations in lexicon order.
   */
  public List<LexiconDTO> createLexiconList(WdApplicationDTO wdApplicationDTO) {
    for (LanguageDTO language : languageService
        .getLanguages(false)) {
      translationKeys.add(createLexicon(wdApplicationDTO, language));
    }
    return translationKeys;
  }

  private LexiconDTO createLexicon(WdApplicationDTO wdApplicationDTO, LanguageDTO language) {
    LexiconDTO lexicon = new LexiconDTO();
    List<LanguageDataDTO> languageData = new ArrayList<>();
    lexicon.setLanguageLocale(language.getLocale());
    languageData.add(
        createLanguageData(ProcessLexiconKeys.TITLE.toString().toLowerCase(), wdApplicationDTO));
    languageData.add(createLanguageData(ProcessLexiconKeys.DESCRIPTION.toString().toLowerCase(),
        wdApplicationDTO));
    lexicon.setValues(languageData);
    return lexicon;
  }

  private LanguageDataDTO createLanguageData(String key, WdApplicationDTO wdApplicationDTO) {
    LanguageDataDTO languageDataDTO = new LanguageDataDTO();
    languageDataDTO.setKey(key);
    languageDataDTO.setValue(getKeyValue(key, wdApplicationDTO));
    return languageDataDTO;
  }

  private String getKeyValue(String key, WdApplicationDTO wdApplication) {
    if (ProcessLexiconKeys.TITLE.toString().equalsIgnoreCase(key)) {
      return wdApplication.getTitle();
    } else if (ProcessLexiconKeys.DESCRIPTION.toString().equalsIgnoreCase(key)) {
      return wdApplication.getDescription();
    }
    return key;
  }

  /**
   * The main method in order to handle translations either from yaml or plain lexicon list.
   *
   * @param translations the list containing translations.
   * @param wdApplication the relevant webdesktop application.
   */
  public void createLexiconValues(List<LexiconDTO> translations, WdApplicationDTO wdApplication) {
    wdApplicationService
        .processLexiconValues(translations, wdApplicationMapper.mapToEntity(wdApplication));
  }

}
