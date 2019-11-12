package com.eurodyn.qlack.webdesktop.util;

/**
 *  supported languages
 */
public enum LanguagesEnum {

    EN("English"),
    EL("Greek"),
    FR("French"),
    DE("German"),
    DA("Danish"),
    IT("Italian"),
    ES("Spanish"),
    PT("Portuguese"),
    SV("Swedish"),
    HR("Croatian");

    /**
     *  the language name
     */
    private String languageName;


    LanguagesEnum(String languageName) {
        this.languageName = languageName;
    }

    /**
     * @return the language name
     */
    public String getLanguageName(){
        return languageName;
    }
}
