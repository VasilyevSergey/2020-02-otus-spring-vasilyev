package com.otus.homework.config;

import lombok.Getter;

import java.util.Locale;

/**
 * Класс с настройками
 */

@Getter
public class Settings {
    private final Locale locale;
    private final String pathToLocalCSV;

    public Settings(Props props) {
        this.locale = Locale.forLanguageTag(props.getLanguageTag());
        this.pathToLocalCSV = getPathToLocalCSVByPathAndLanguageTag(props.getPathToCSV(), props.getLanguageTag());
    }

    private String getPathToLocalCSVByPathAndLanguageTag(String pathToCSV, String languageTag) {
        if (languageTag.equals("")) {
            return pathToCSV;
        } else {
            return pathToCSV.replace(".csv", String.format("_%s.csv", languageTag.replace("-", "_")));
        }
    }
}
