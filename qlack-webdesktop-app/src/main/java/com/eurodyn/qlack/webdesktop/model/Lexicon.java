package com.eurodyn.qlack.webdesktop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lexicon {

  /**
   * the language locale
   */
  private String languageLocale;
  private List<LanguageData> values;

}
