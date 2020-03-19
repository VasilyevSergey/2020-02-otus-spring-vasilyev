package ru.otus.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Класс с настройками
 */
@Component
public class Settings {
    private Locale locale;
    private String pathToLocalCSV;

    public Settings(@Value("${language.tag}") String languageTag,
                    @Value("${qna.filepath}") String pathToCSV) {
        this.locale = Locale.forLanguageTag(languageTag);
        this.pathToLocalCSV = getPathToLocalCSVByPathAndLanguageTag(pathToCSV, languageTag);
    }

    private String getPathToLocalCSVByPathAndLanguageTag(String pathToCSV, String languageTag) {
        if (languageTag.equals("")) {
            return pathToCSV;
        } else {
            return pathToCSV.replace(".csv", String.format("_%s.csv", languageTag.replace("-", "_")));
        }
    }

    public Locale getLocale() {
        return locale;
    }

    public String getPathToLocalCSV() {
        return pathToLocalCSV;
    }
}
