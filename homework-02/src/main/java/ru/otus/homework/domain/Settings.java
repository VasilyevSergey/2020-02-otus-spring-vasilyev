package ru.otus.homework.domain;

/**
 * Класс с настройками
 */
public class Settings {
    private String languageTag;
    private String pathToCSV;

    public Settings(String languageTag,
                    String pathToCSV) {
        this.languageTag = languageTag;
        this.pathToCSV = pathToCSV;
    }

    public String getLanguageTag() {
        return languageTag;
    }

    public String getPathToCSV() {
        return pathToCSV;
    }
}
