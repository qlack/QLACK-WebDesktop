package com.eurodyn.qlack.webdesktop.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LexiconDTO {

  /**
   * the language locale
   */
  private String languageLocale;
  private List<LanguageDataDTO> values;

}
