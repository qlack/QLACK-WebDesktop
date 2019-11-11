package com.eurodyn.qlack.webdesktop.model;

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
     *  the language locale
     */
    private String languageLocale;
    /**
     *  the name of the key
     */
    private String key;
    /**
     * the value of the key
     */
    private String value;

}
