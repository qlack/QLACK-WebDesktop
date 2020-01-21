package com.eurodyn.qlack.webdesktop.applications.management.util;

import com.eurodyn.qlack.fuse.lexicon.dto.GroupDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.KeyDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.LanguageDTO;
import com.eurodyn.qlack.fuse.lexicon.service.GroupService;
import com.eurodyn.qlack.fuse.lexicon.service.KeyService;
import com.eurodyn.qlack.fuse.lexicon.service.LanguageService;
import com.eurodyn.qlack.webdesktop.common.dto.LanguageDataDTO;
import com.eurodyn.qlack.webdesktop.common.dto.LexiconDTO;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the yaml uploading and saves the application to database.
 */
@Component
public class ProcessLexiconUtil {

  private KeyService keyService;
  private GroupService groupService;
  private LanguageService languageService;
  private List<LexiconDTO> translationKeys = new ArrayList<>();
  private List<LanguageDataDTO> languageData = new ArrayList<>();
  private static final String titleKey = "title";
  private static final String descriptionKey = "description";

  @Autowired
  public ProcessLexiconUtil(KeyService keyService, LanguageService languageService,
      GroupService groupService) {
    this.keyService = keyService;
    this.groupService = groupService;
    this.languageService = languageService;
  }

  /**
   * This is a helper method sequence so as to create lexicon values. This is a pre-process in order
   * to insert default translations for all available languages.
   *
   * @param wdApplicationDTO the application.
   * @return a list containing translations in lexicon order.
   */
  public List<LexiconDTO> createLexiconList(WdApplicationDTO wdApplicationDTO) {
    translationKeys.add(createLexicon(wdApplicationDTO));
    return translationKeys;
  }

  private LexiconDTO createLexicon(WdApplicationDTO wdApplicationDTO) {
    LexiconDTO lexicon = new LexiconDTO();
    for (LanguageDTO language : languageService
        .getLanguages(false)) {
      lexicon.setLanguageLocale(language.getLocale());
      languageData.add(createLanguageData(titleKey, wdApplicationDTO));
      languageData.add(createLanguageData(descriptionKey, wdApplicationDTO));
      lexicon.setValues(languageData);
    }
    return lexicon;
  }

  private LanguageDataDTO createLanguageData(String key, WdApplicationDTO wdApplicationDTO) {
    LanguageDataDTO languageData = new LanguageDataDTO();
    languageData.setKey(key);
    languageData.setValue(getKeyValue(key, wdApplicationDTO));
    return languageData;
  }

  private String getKeyValue(String key, WdApplicationDTO wdApplication) {
    if ("title".equals(key)) {
      return wdApplication.getTitle();
    } else if ("description".equals(key)) {
      return wdApplication.getDescription();
    }
    return key;
  }

  /**
   * The main method in order to handle translations either from yaml or plain lexicon list.
   *
   * @param translations  the list containing translations.
   * @param wdApplication the relevant webdesktop application.
   */
  public void createLexiconValues(List<LexiconDTO> translations, WdApplicationDTO wdApplication) {

    GroupDTO groupDTO = groupService.getGroupByTitle(wdApplication.getApplicationName());
    String groupId;
    if (groupDTO == null) {

      groupDTO = new GroupDTO();
      groupDTO.setTitle(wdApplication.getApplicationName());
      groupDTO.setDescription("groupDescription");
      groupId = groupService.createGroup(groupDTO);
    } else {
      groupId = groupDTO.getId();
    }

    for (LexiconDTO translation : translations) {
      if (languageService.getLanguageByLocale(translation.getLanguageLocale()) != null) {
        for (LanguageDataDTO data : translation.getValues()) {
          KeyDTO keyDTO = keyService.getKeyByName(data.getKey(), groupId, false);
          if (keyDTO == null) {
            keyDTO = new KeyDTO();
            keyDTO.setGroupId(groupId);
            keyDTO.setName(data.getKey());
            String keyId = keyService.createKey(keyDTO, false);
            keyService
                .updateTranslationByLocale(keyId, translation.getLanguageLocale(), data.getValue());
          } else {

            keyService.updateTranslationByLocale(keyDTO.getId(), translation.getLanguageLocale(),
                data.getValue());
          }
        }
      }
    }
  }
}
