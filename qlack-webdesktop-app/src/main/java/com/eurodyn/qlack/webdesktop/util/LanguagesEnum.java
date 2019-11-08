package com.eurodyn.qlack.webdesktop.util;

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

    private String languageName;

    LanguagesEnum(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageName(){
        return languageName;
    }
}
