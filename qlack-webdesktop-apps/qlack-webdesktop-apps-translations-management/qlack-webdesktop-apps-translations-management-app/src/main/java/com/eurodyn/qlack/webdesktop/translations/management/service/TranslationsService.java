package com.eurodyn.qlack.webdesktop.translations.management.service;

import com.eurodyn.qlack.fuse.lexicon.criteria.KeySearchCriteria;
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
import com.eurodyn.qlack.webdesktop.translations.management.dto.TmKeyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
public class TranslationsService {

  private GroupService groupService;
  private KeyService keyService;
  private LanguageService languageService;
  private KeyRepository keyRepository;
  private DataRepository dataRepository;

  @Autowired
  public TranslationsService(GroupService groupService, KeyService keyService,
    LanguageService languageService, KeyRepository keyRepository,
    DataRepository dataRepository) {
    this.groupService = groupService;
    this.keyService = keyService;
    this.languageService = languageService;
    this.keyRepository = keyRepository;
    this.dataRepository = dataRepository;
  }

  public Page<TmKeyDTO> findPagesForAllKeys(String page, String size,
    List<String> sort) {
    KeySearchCriteria criteria = new KeySearchCriteria();
    criteria.setPageable(
      PageRequest.of(Integer.parseInt(page), Integer.parseInt(size),
        Sort.Direction.fromString(sort.get(1)), sort.get(0)));

    List<KeyDTO> tempKeys = keyService.findKeys(criteria, true);
    Set<GroupDTO> groups = groupService.getGroups();
    List<TmKeyDTO> keys = new ArrayList<>();

    for (KeyDTO tempKey : tempKeys) {
      TmKeyDTO key = new TmKeyDTO();
      key.setId(tempKey.getId());
      key.setGroupId(tempKey.getGroupId());
      key.setName(tempKey.getName());
      key.setTranslations(tempKey.getTranslations());
      for (GroupDTO group : groups) {
        if (group.getId().equalsIgnoreCase(tempKey.getGroupId())) {
          key.setGroupName(group.getTitle());
        }
      }
      keys.add(key);
    }
    return new PageImpl<>(keys, criteria.getPageable(), keyRepository.count());
  }

  public void updateTranslationsForKey(String keyId,
    Map<String, String> translations) {
    keyService.updateTranslationsForKeyByLocale(keyId, translations);
  }

  public List<LanguageDTO> findLanguages(boolean includeInactive) {
    return languageService.getLanguages(includeInactive);
  }

  public void createLanguage(LanguageDTO language) {
    //sourceLanguageId the id of the language that will be used to find the translations for copy
    String sourceLanguageId = language.getId();
    language.setId(null);
    String languageId = languageService.createLanguageIfNotExists(language);
    if (sourceLanguageId != null) {
      Map<String, String> translations = new HashMap<>();
      for (Key key : keyRepository.findAll()) {
        String translation = this.getTranslation(key.getId(), sourceLanguageId);
        if (translation != null) {
          translations.put(key.getId(), translation);
        }
      }
      keyService.updateTranslationsForLanguage(languageId, translations);
    }
  }

  private String getTranslation(String keyId, String languageId) {
    Data data = dataRepository.findByKeyIdAndLanguageId(keyId, languageId);
    return data != null ? data.getValue() : null;
  }

  public void updateLanguages(List<LanguageDTO> languages) {

    for (LanguageDTO language : languages) {
      if (language.isActive()) {
        languageService.activateLanguage(language.getId());
      } else {
        languageService.deactivateLanguage(language.getId());
      }
    }
  }

  public Map<String, Map<String, String>> findTranslationsForLocale(
    String locale) {
    return keyService.getTranslationsForLocaleGroupByGroupTitle(locale);
  }

}
