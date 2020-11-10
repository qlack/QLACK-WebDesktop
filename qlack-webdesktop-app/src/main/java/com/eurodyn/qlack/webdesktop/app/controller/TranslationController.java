package com.eurodyn.qlack.webdesktop.app.controller;

import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/translations")
public class TranslationController {

  private final WdApplicationService wdApplicationService;

  @Autowired
  public TranslationController(WdApplicationService wdApplicationService) {
    this.wdApplicationService = wdApplicationService;
  }

  /**
   * Get all translations for a specific locale,groupby group title which is the applicationName from .yaml
   * configuration file Every App has its own group.
   *
   * @param lang the language locale
   * @return a list of translations for a specific locale
   */
  @GetMapping()
  public Map<String, Map<String, String>> getTranslations(@RequestParam String lang) {
    return wdApplicationService.findTranslationsForLocale(lang);
  }

}
