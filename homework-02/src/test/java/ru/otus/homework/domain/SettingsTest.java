package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Settings")
class SettingsTest {

    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        Settings settings = new Settings("ru-RU", "pathToCSV");

        assertEquals("pathToCSV", settings.getPathToCSV());
        assertEquals("ru-RU", settings.getLanguageTag());
    }
}
