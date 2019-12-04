package com.eurodyn.qlack.webdesktop.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
