package com.otus.homework.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Класс с настройками
 */

@Data
@Component
public class Settings {
    private final Locale locale;
    private final String pathToLocalCSV;

    @Autowired
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
